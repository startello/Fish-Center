package com.bionic.fishtrading.bo.impl;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.dao.ColdStoreItemDao;
import com.bionic.fishtrading.dao.FishTypeDao;
import com.bionic.fishtrading.dao.ParcelDao;
import com.bionic.fishtrading.dao.ParcelOrderDao;
import com.bionic.fishtrading.dao.UserDao;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.FishType;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;
import com.bionic.fishtrading.model.User;

@Named
public class GeneralManagerServiceImpl implements GeneralManagerService,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3984667740511575417L;
	@Inject
	ParcelDao parcelDao;
	@Inject
	FishTypeDao fishTypeDao;
	@Inject
	ColdStoreItemDao coldStoreItemDao;
	@Inject
	UserDao userDao;
	@Inject
	ParcelOrderDao parcelOrderDao;
	private static final int ARRIVED = 1;
	private static final int FORSALE = 2;

	// Parcels
	@Override
	@Transactional
	public void addParcel(Parcel parcel) {
		if (parcel.getColdStoreItems() != null)
			for (ColdStoreItem item : parcel.getColdStoreItems()) {
				item.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
				item.setStatus(0);
			}
		parcel.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		parcel.setStatus(0);
		parcelDao.add(parcel);
	}

	@Override
	@Transactional
	public void updateParcel(Parcel parcel) {
		double sum = 0;
		if (parcel.getColdStoreItems() != null) {
			Collection<ColdStoreItem> items = parcel.getColdStoreItems();
			for (ColdStoreItem item : items) {
				if (item.getCreationDate() == null)
					item.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
				sum += item.getWeightLeft();
				if (item.getWeightLeft() == 0) {
					if (item.getStatus() == 1)
						item.setStatus(2);
				} else {
					item.setStatus(1);
				}
				coldStoreItemDao.update(item);
			}
		}
		if (parcel.getCreationDate() == null)
			parcel.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		if (sum == 0) {
			if (parcel.getStatus() == 1)
				parcel.setStatus(2);
		} else {
			parcel.setStatus(1);
		}
		parcelDao.update(parcel);
	}

	@Override
	public Parcel getParcelById(int id) {
		return parcelDao.getById(id);
	}

	@Override
	public List<Parcel> getAllParcels() {
		return parcelDao.getAll();
	}

	@Override
	public List<Parcel> getUnarrivedParcels() {
		return parcelDao.getByStatus(0);
	}

	@Override
	public List<Parcel> getArrivedParcels() {
		List<Parcel> list = parcelDao.getByStatus(ARRIVED);
		return list;
	}

	// FishTypes
	@Override
	@Transactional
	public void addFishType(FishType ft) {
		ft.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		fishTypeDao.add(ft);
	}

	@Override
	@Transactional
	public void updateFishType(FishType ft) {
		if (ft.getCreationDate() == null)
			ft.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		fishTypeDao.update(ft);
	}

	@Override
	public FishType getFishTypeById(int id) {
		return fishTypeDao.getById(id);
	}

	@Override
	public List<FishType> getAllFishTypes() {
		return fishTypeDao.getAll();
	}

	// ColdStoreItems
	@Override
	public ColdStoreItem getColdStoreItemById(int id) {
		return coldStoreItemDao.getById(id);
	}

	@Override
	@Transactional
	public void updateColdStoreItem(ColdStoreItem item) {
		coldStoreItemDao.update(item);
	}

	@Override
	public List<ColdStoreItem> getArrivedColdStoreItems() {
		List<ColdStoreItem> list = coldStoreItemDao.getByStatus(ARRIVED);
		list.addAll(coldStoreItemDao.getByStatus(FORSALE));
		return list;
	}

	@Override
	@Transactional
	public void writeOffItem(int id) {
		coldStoreItemDao.setStatus(id, 3);
	}

	// Report
	@Override
	public List<ReportByDateItem> getParcelOrdersByDate() {
		return parcelOrderDao.getReportByDate();
	}

	@Override
	public List<ReportByDateItem> getColdStoreItemsByDate() {
		return coldStoreItemDao.getReportByDate();
	}

	@Override
	public List<ReportByFishTypeItem> getParcelItemsByFishType() {
		return parcelOrderDao.getReportByFishType();
	}

	@Override
	public List<ReportByFishTypeItem> getColdStoreItemsByFishType() {
		return coldStoreItemDao.getReportByFishType();
	}

	@Override
	public List<ReportByDateItem> getParcelOrdersByDate(java.util.Date start,
			java.util.Date end) {
		return parcelOrderDao.getReportByDate(start, end);
	}

	@Override
	public List<ReportByDateItem> getColdStoreItemsByDate(java.util.Date start,
			java.util.Date end) {
		return coldStoreItemDao.getReportByDate(start, end);
	}

	@Override
	public List<ReportByFishTypeItem> getParcelItemsByFishType(
			java.util.Date start, java.util.Date end) {
		return parcelOrderDao.getReportByFishType(start, end);
	}

	@Override
	public List<ReportByFishTypeItem> getColdStoreItemsByFishType(
			java.util.Date start, java.util.Date end) {
		return coldStoreItemDao.getReportByFishType(start, end);
	}

	//
	@Override
	public List<User> getAllUsers() {
		return userDao.getAll();
	}

	@Override
	public List<User> getAllCustomers() {
		return userDao.getAllCustomers();
	}

	@Override
	public User getUserById(int id) {
		return userDao.getById(id);
	}

	@Override
	public List<ParcelOrder> getAllParcelOrders() {
		return parcelOrderDao.getAll();
	}

	@Override
	public ParcelOrder getParcelOrderById(int id) {
		return parcelOrderDao.getById(id);
	}

	@Override
	@Transactional
	public void cancelParcelOrder(int id) {
		// ParcelOrder po = parcelOrderDao.getById(id);
		// List<ParcelOrderItem> list = new ArrayList<ParcelOrderItem>(
		// po.getParcelOrderItems());
		parcelOrderDao.setStatus(id, 2);
	}

	@Override
	public List<ColdStoreItem> getAllItemsForWriteOff() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColdStoreItem> getAllItemsForWriteOffByColdStoreItemId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void confirmColdStoreItemWriteOff(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void declineColdStoreItemWriteOff(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ColdStoreItem> getWrittenOffItemsByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColdStoreItem> getParcelItemsByFishType(int fishTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Parcel> getParcelsByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
