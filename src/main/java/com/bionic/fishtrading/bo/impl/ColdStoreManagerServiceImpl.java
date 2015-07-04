package com.bionic.fishtrading.bo.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.fishtrading.bo.ColdStoreManagerService;
import com.bionic.fishtrading.dao.ColdStoreItemDao;
import com.bionic.fishtrading.dao.ParcelDao;
import com.bionic.fishtrading.dao.ParcelOrderDao;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.ParcelOrder;

@Named
public class ColdStoreManagerServiceImpl implements ColdStoreManagerService,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8384664518741572460L;
	@Inject
	private ParcelDao parcelDao;
	@Inject
	private ColdStoreItemDao coldStoreItemDao;
	@Inject
	private ParcelOrderDao parcelOrderDao;

	private static final int ARRIVED = 1;
	private static final int FORSALE = 2;

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
	public List<Parcel> getUnarrivedParcels() {
		return parcelDao.getByStatus(0);
	}

	@Override
	public List<ParcelOrder> getParcelOrdersReadyForShipment() {
		List<ParcelOrder> list = parcelOrderDao.getByStatus(1);
		list.addAll(parcelOrderDao.getByStatus(2));
		return list;
	}

	@Override
	public List<ColdStoreItem> getColdStoreItems() {
		List<ColdStoreItem> list = coldStoreItemDao.getByStatus(ARRIVED);
		list.addAll(coldStoreItemDao.getByStatus(FORSALE));
		return list;
	}

	@Override
	@Transactional
	public void commitShipment(int id) {
		parcelOrderDao.setStatus(id, 3);
	}

	@Override
	@Transactional
	public void writeOffItem(int id) {
		coldStoreItemDao.setStatus(id, 4);
	}

	@Override
	public Parcel getParcelById(int id) {
		return parcelDao.getById(id);
	}

	@Override
	public ParcelOrder getParcelOrderById(int id) {
		return parcelOrderDao.getById(id);
	}

	@Override
	public ColdStoreItem getColdStoreItemById(int id) {
		return coldStoreItemDao.getById(id);
	}

	@Override
	public List<ColdStoreItem> getColdStoreItemsForWriteOff() {
		return coldStoreItemDao.getByStatus(3);
	}

}
