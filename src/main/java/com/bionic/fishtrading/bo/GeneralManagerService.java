package com.bionic.fishtrading.bo;

import java.util.List;

import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.FishType;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;
import com.bionic.fishtrading.model.User;

public interface GeneralManagerService {
	// Parcels
	public void addParcel(Parcel parcel);

	void updateParcel(Parcel parcel);

	public Parcel getParcelById(int id);

	public List<Parcel> getAllParcels();

	public List<Parcel> getUnarrivedParcels();

	public List<Parcel> getArrivedParcels();

	// FishTypes
	public void addFishType(FishType ft);

	public void updateFishType(FishType ft);

	public FishType getFishTypeById(int id);

	public List<FishType> getAllFishTypes();

	// ColdStoreItems
	public void updateColdStoreItem(ColdStoreItem item);

	public ColdStoreItem getColdStoreItemById(int id);

	public List<ColdStoreItem> getArrivedColdStoreItems();

	public void writeOffItem(int id);

	// Report
	public List<ReportByDateItem> getParcelOrdersByDate();

	public List<ReportByDateItem> getColdStoreItemsByDate();

	public List<ReportByFishTypeItem> getParcelItemsByFishType();

	public List<ReportByFishTypeItem> getColdStoreItemsByFishType();

	public List<ReportByDateItem> getParcelOrdersByDate(java.util.Date start,
			java.util.Date end);

	public List<ReportByDateItem> getColdStoreItemsByDate(java.util.Date start,
			java.util.Date end);

	public List<ReportByFishTypeItem> getParcelItemsByFishType(
			java.util.Date start, java.util.Date end);

	public List<ReportByFishTypeItem> getColdStoreItemsByFishType(
			java.util.Date start, java.util.Date end);

	//

	public List<ColdStoreItem> getAllItemsForWriteOff();

	public List<ColdStoreItem> getAllItemsForWriteOffByColdStoreItemId(int id);

	public void confirmColdStoreItemWriteOff(int id);

	public void declineColdStoreItemWriteOff(int id);

	public List<User> getAllUsers();

	public User getUserById(int id);

	public List<ParcelOrder> getAllParcelOrders();

	public ParcelOrder getParcelOrderById(int id);

	public void cancelParcelOrder(int id);

	public List<Parcel> getParcelsByDate(java.sql.Date date);

	public List<ColdStoreItem> getWrittenOffItemsByDate(java.sql.Date date);

	public List<ColdStoreItem> getParcelItemsByFishType(int fishTypeId);

	public List<User> getAllCustomers();
}
