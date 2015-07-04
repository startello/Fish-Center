package com.bionic.fishtrading.bo;

import java.util.List;

import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.ParcelOrder;

public interface ColdStoreManagerService {
	void updateParcel(Parcel parcel);

	public List<Parcel> getUnarrivedParcels();

	public Parcel getParcelById(int id);

	public List<ColdStoreItem> getColdStoreItems();

	public List<ParcelOrder> getParcelOrdersReadyForShipment();

	public void writeOffItem(int id);

	public void commitShipment(int id);

	public ParcelOrder getParcelOrderById(int id);

	public ColdStoreItem getColdStoreItemById(int id);

	public List<ColdStoreItem> getColdStoreItemsForWriteOff();
}
