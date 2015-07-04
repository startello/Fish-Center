package com.bionic.fishtrading.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.fishtrading.dao.ColdStoreItemDao;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.GoodsListItem;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;

@Repository
public class ColdStoreItemDaoImpl implements ColdStoreItemDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 330411165263094510L;
	@PersistenceContext
	private EntityManager em;

	/*
	 * @Override public void add(ColdStoreItem csi) { em.persist(csi); }
	 */

	@Override
	public void update(ColdStoreItem csi) {
		em.merge(csi);
	}

	@Override
	public void setStatus(int id, int status) {
		em.find(ColdStoreItem.class, id).setStatus(status);
	}

	@Override
	public ColdStoreItem getById(int id) {
		return em.find(ColdStoreItem.class, id);
	}

	/*
	 * @Override public List<ColdStoreItem> getAll() { TypedQuery<ColdStoreItem>
	 * query = em.createQuery( "SELECT csi FROM ColdStoreItem csi",
	 * ColdStoreItem.class); return query.getResultList(); }
	 */

	@Override
	public List<ColdStoreItem> getByStatus(int status) {
		TypedQuery<ColdStoreItem> query = em.createQuery(
				"SELECT csi FROM ColdStoreItem csi WHERE csi.status = "
						+ status, ColdStoreItem.class);
		return query.getResultList();
	}

	@Override
	public List<GoodsListItem> getItemsForSale() {
		TypedQuery<GoodsListItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.GoodsListItem(csi.id,  csi.fishType.id, csi.fishType.name, csi.weightLeft, "
								+ "csi.sellingPrice, csi.fishType.description) FROM ColdStoreItem csi WHERE csi.status = 2",
						GoodsListItem.class);
		return query.getResultList();
	}

	@Override
	public List<ReportByDateItem> getReportByDate() {
		TypedQuery<ReportByDateItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByDateItem(csi.creationDate, COUNT(csi), SUM(csi.purchasePrice * csi.weightPurchased))  "
								+ "FROM ColdStoreItem csi WHERE csi.status > 0 GROUP BY csi.creationDate",
						ReportByDateItem.class);
		return query.getResultList();
	}

	@Override
	public List<ReportByFishTypeItem> getReportByFishType() {
		TypedQuery<ReportByFishTypeItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByFishTypeItem(csi.fishType.name, COUNT(csi), SUM(csi.purchasePrice * csi.weightPurchased))  "
								+ "FROM ColdStoreItem csi WHERE csi.status > 0 GROUP BY csi.fishType.name",
						ReportByFishTypeItem.class);
		return query.getResultList();
	}

	@Override
	public List<ColdStoreItem> getItemsInColdStore() {
		TypedQuery<ColdStoreItem> query = em
				.createQuery(
						"SELECT csi FROM ColdStoreItem csi WHERE (csi.status = 1 OR csi.status = 2)",
						ColdStoreItem.class);
		return query.getResultList();
	}

	@Override
	public List<ReportByDateItem> getReportByDate(java.util.Date start,
			java.util.Date end) {
		TypedQuery<ReportByDateItem> query = em
				.createQuery(
						"SELECT new com.bionic.fishtrading.model.ReportByDateItem(csi.creationDate, COUNT(csi), SUM(csi.purchasePrice * csi.weightPurchased))  "
								+ "FROM ColdStoreItem csi WHERE csi.status > 0 AND csi.creationDate BETWEEN :startDate AND :endDate "
								+ "GROUP BY csi.creationDate",
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
						"SELECT new com.bionic.fishtrading.model.ReportByFishTypeItem(csi.fishType.name, COUNT(csi), SUM(csi.purchasePrice * csi.weightPurchased))  "
								+ "FROM ColdStoreItem csi WHERE csi.status > 0 AND csi.creationDate BETWEEN :startDate AND :endDate "
								+ "GROUP BY csi.fishType.name",
						ReportByFishTypeItem.class)
				.setParameter("startDate", start, TemporalType.DATE)
				.setParameter("endDate", end, TemporalType.DATE);
		return query.getResultList();
	}

}
