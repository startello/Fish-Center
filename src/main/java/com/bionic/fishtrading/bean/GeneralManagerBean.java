package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.FishType;
import com.bionic.fishtrading.model.Parcel;
import com.bionic.fishtrading.model.User;

@Named
@Scope("request")
public class GeneralManagerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2831573748614685577L;

	private List<Parcel> unarrivedParcels;
	private List<ColdStoreItem> arrivedCsi;
	private List<FishType> fishTypes;
	private List<User> customers;

	@Inject
	private GeneralManagerService gms;

	public List<FishType> getFishTypes() {
		return fishTypes;
	}

	public void setFishTypes(List<FishType> fishTypes) {
		this.fishTypes = fishTypes;
	}

	public GeneralManagerBean() {

	}

	@PostConstruct
	public void init() {
		unarrivedParcels = gms.getUnarrivedParcels();
		arrivedCsi = gms.getArrivedColdStoreItems();
		fishTypes = gms.getAllFishTypes();
		customers = gms.getAllCustomers();
	}

	public List<Parcel> getUnarrivedParcels() {
		return unarrivedParcels;
	}

	public void setUnarrivedParcels(List<Parcel> unarrivedParcels) {
		this.unarrivedParcels = unarrivedParcels;
	}

	public List<ColdStoreItem> getArrivedCsi() {
		return arrivedCsi;
	}

	public void setArrivedCsi(List<ColdStoreItem> arrivedCsi) {
		this.arrivedCsi = arrivedCsi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<User> getCustomers() {
		return customers;
	}

	public void setCustomers(List<User> customers) {
		this.customers = customers;
	}

}
