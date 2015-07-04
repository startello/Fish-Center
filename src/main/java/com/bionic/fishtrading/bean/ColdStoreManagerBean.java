package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.ColdStoreManagerService;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.ParcelOrder;

@Named
@Scope("request")
public class ColdStoreManagerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7441893746130136719L;

	private List<Parcel> unarrived;
	private List<ColdStoreItem> arrived;
	private List<ColdStoreItem> forWriteOff;
	private List<ParcelOrder> parcelOrders;

	@Inject
	ColdStoreManagerService csms;

	public List<ColdStoreItem> getArrived() {
		return arrived;
	}

	public void setArrived(List<ColdStoreItem> arrived) {
		this.arrived = arrived;
	}

	public List<Parcel> getUnarrived() {
		return unarrived;
	}

	public void setUnarrived(List<Parcel> unarrived) {
		this.unarrived = unarrived;
	}

	public List<ColdStoreItem> getForWriteOff() {
		return forWriteOff;
	}

	public void setForWriteOff(List<ColdStoreItem> forWriteOff) {
		this.forWriteOff = forWriteOff;
	}

	public ColdStoreManagerBean() {

	}

	@PostConstruct
	public void init() {
		unarrived = csms.getUnarrivedParcels();
		arrived = csms.getColdStoreItems();
		forWriteOff = csms.getColdStoreItemsForWriteOff();
		parcelOrders = csms.getParcelOrdersReadyForShipment();
	}

	public List<ParcelOrder> getParcelOrders() {
		return parcelOrders;
	}

	public void setParcelOrders(List<ParcelOrder> parcelOrders) {
		this.parcelOrders = parcelOrders;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
