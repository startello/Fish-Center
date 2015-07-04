package com.bionic.fishtrading.dao.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.fishtrading.dao.ParcelOrderDao;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.ParcelOrderItem;
import com.bionic.fishtrading.model.Payment;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;

@Repository
public class ParcelOrderDaoImpl implements ParcelOrderDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1021175003855731929L;
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(ParcelOrder parcelOrder) throws Exception {
		if (parcelOrder.getCreationDate() == null)
			parcelOrder.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		for (ParcelOrderItem item : parcelOrder.getParcelOrderItems()) {
			if (item.getCreationDate() == null)
				item.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		}
		em.persist(parcelOrder);
	}

	@Override
	public void setStatus(int id, int status) {
		em.find(ParcelOrder.class, id).setStatus(status);
	}

	@Override
	public List<ParcelOrder> getAll() {
		TypedQuery<ParcelOrder> query = em.createQuery(
				"SELECT po FROM ParcelOrder po", ParcelOrder.class);
		return query.getResultList();
	}

	@Override
	public List<ParcelOrder> getAllThatNeedsPayment() {
		TypedQuery<ParcelOrder> query = em
				.createQuery(
						"SELECT po FROM ParcelOrder po WHERE po.sumPayed < po.sumToPay",
						ParcelOrder.class);
		return query.getResultList();
	}

	@Override
	public ParcelOrder getById(int id) {
		return em.find(ParcelOrder.class, id);
	}

	@Override
	public List<ParcelOrder> getByFishType(int fishTypeId) {
		TypedQuery<ParcelOrder> query = em.createQuery(
				"SELECT po FROM ParcelOrder po WHERE po.coldStoreItem.fishType.id ="
						+ fishTypeId, ParcelOrder.class);
		return query.getResultList();
	}

	@Override
	public List<ParcelOrder> getByUserId(int id) {
		TypedQuery<ParcelOrder> query = em.createQuery(
				"SELECT po FROM ParcelOrder po WHERE po.user.id = " + id
						+ " ORDER BY po.creationDate DESC", ParcelOrder.class);
		return query.getResultList();
	}

	@Override
	public List<ParcelOrder> getByStatus(int status) {
		TypedQuery<ParcelOrder> query = em.createQuery(
				"SELECT po FROM ParcelOrder po WHERE po.status = " + status,
				ParcelOrder.class);
		return query.getResultList();
	}

	@Override
	public void update(ParcelOrder po) {
		double sum = 0;
		for (Payment item : po.getPayments()) {
			sum += item.getSumPayed();
		}
		po.setSumPayed(sum);
		if (po.getStatus() == 0) {
			if (po.getSumToPay() * po.getUser().getQuota() / 100 <= po
					.getSumPayed()) {
				confirmOrder(po);
			}
		}
		if (po.getStatus() == 1) {
			if (po.getSumToPay() <= po.getSumPayed())
				po.setStatus(2);
		}
		em.merge(po);
	}

	private void confirmOrder(ParcelOrder po) {
		po.setStatus(1);
		for (ParcelOrderItem poi : po.getParcelOrderItems()) {

			ColdStoreItem csi = poi.getColdStoreItem();
			System.out.println(csi);
			csi.setWeightLeft(csi.getWeightLeft() - poi.getWeight());
			if (csi.getWeightLeft() < 0)
				po.setStatus(4);
		}
		if (po.getStatus() == 1) {
			for (ParcelOrderItem poi : po.getParcelOrderItems()) {
				em.merge(poi.getColdStoreItem());
			}
			po.setShipmentDate(java.sql.Date.valueOf(LocalDate.now()));
		}
	}

	@Override
	public List<ReportByDateItem> getReportByDate() {
		TypedQuery<ReportByDateItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByDateItem(poi.creationDate, COUNT(poi), SUM(poi.weight * poi.price)) "
								+ "FROM ParcelOrderItem poi WHERE poi.parcelOrder.status BETWEEN 1 AND 3 GROUP BY poi.creationDate",
						ReportByDateItem.class);
		return query.getResultList();
	}

	@Override
	public List<ReportByFishTypeItem> getReportByFishType() {
		TypedQuery<ReportByFishTypeItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByFishTypeItem(poi.coldStoreItem.fishType.name, COUNT(poi), SUM(poi.weight * poi.price)) "
								+ "FROM ParcelOrderItem poi WHERE poi.parcelOrder.status BETWEEN 1 AND 3 GROUP BY poi.coldStoreItem.fishType.name",
						ReportByFishTypeItem.class);
		return query.getResultList();
	}

	@Override
	public List<ReportByDateItem> getReportByDate(java.util.Date start,
			java.util.Date end) {
		TypedQuery<ReportByDateItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByDateItem(poi.creationDate, COUNT(poi), SUM(poi.weight * poi.price)) "
								+ "FROM ParcelOrderItem poi WHERE poi.parcelOrder.status BETWEEN 1 AND 3 AND poi.creationDate BETWEEN :startDate AND :endDate "
								+ "GROUP BY po.creationDate",
						ReportByDateItem.class)
				.setParameter("startDate", start, TemporalType.DATE)
				.setParameter("endDate", end, TemporalType.DATE);
		return query.getResultList();
	}

	@Override
	public List<ReportByFishTypeItem> getReportByFishType(java.util.Date start,
			java.util.Date end) {
		TypedQuery<ReportByFishTypeItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByFishTypeItem(poi.coldStoreItem.fishType.name, COUNT(poi), SUM(poi.weight * poi.price)) "
								+ "FROM ParcelOrderItem poi WHERE poi.parcelOrder.status BETWEEN 1 AND 3 AND poi.creationDate BETWEEN :startDate AND :endDate "
								+ "GROUP BY poi.coldStoreItem.fishType.name",
						ReportByFishTypeItem.class)
				.setParameter("startDate", start, TemporalType.DATE)
				.setParameter("endDate", end, TemporalType.DATE);
		return query.getResultList();
	}
}
