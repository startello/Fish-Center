package com.bionic.fishtrading.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ColdStoreItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2765353361635212220L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "parcelId")
	private Parcel coldStoreItemParcel;
	@ManyToOne
	@JoinColumn(name = "fishTypeId")
	private FishType fishType;
	private double weightPurchased;
	private double weightArrived;
	private double weightLeft;
	private double purchasePrice;
	private double sellingPrice;
	private double storagePrice;
	private int status;
	@Temporal(TemporalType.DATE)
	private java.util.Date arrivalDate;
	@Temporal(TemporalType.DATE)
	private java.util.Date writeOffDate;
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;

	public ColdStoreItem() {

	}

	public ColdStoreItem(FishType fishType, double weightPurchased,
			double purchasePrice, double storagePrice) {
		this.fishType = fishType;
		this.weightPurchased = weightPurchased;
		this.weightArrived = 0;
		this.weightLeft = 0;
		this.purchasePrice = purchasePrice;
		this.sellingPrice = purchasePrice;
		this.storagePrice = storagePrice;
		this.status = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Parcel getColdStoreItemParcel() {
		return coldStoreItemParcel;
	}

	public void setColdStoreItemParcel(Parcel coldStoreItemParcel) {
		this.coldStoreItemParcel = coldStoreItemParcel;
	}

	public FishType getFishType() {
		return fishType;
	}

	public void setFishType(FishType fishType) {
		this.fishType = fishType;
	}

	public double getWeightPurchased() {
		return weightPurchased;
	}

	public void setWeightPurchased(double weightPurchased) {
		this.weightPurchased = weightPurchased;
	}

	public double getWeightArrived() {
		return weightArrived;
	}

	public void setWeightArrived(double weightArrived) {
		this.weightArrived = weightArrived;
	}

	public double getWeightLeft() {
		return weightLeft;
	}

	public void setWeightLeft(double weightLeft) {
		this.weightLeft = weightLeft;
		if (weightLeft <= 0)
			status = 4;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public double getStoragePrice() {
		return storagePrice;
	}

	public void setStoragePrice(double storagePrice) {
		this.storagePrice = storagePrice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.util.Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(java.util.Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public java.util.Date getWriteOffDate() {
		return writeOffDate;
	}

	public void setWriteOffDate(java.util.Date writeOffDate) {
		this.writeOffDate = writeOffDate;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "ColdStoreItem [id=" + id + ", parcel=" + coldStoreItemParcel
				+ ", fishType=" + fishType + ", weightPurchased="
				+ weightPurchased + ", weightArrived=" + weightArrived
				+ ", weightLeft=" + weightLeft + ", purchasePrice="
				+ purchasePrice + ", sellingPrice=" + sellingPrice
				+ ", storagePrice=" + storagePrice + ", status=" + status
				+ ", arrivalDate=" + arrivalDate + ", writeOffDate="
				+ writeOffDate + ", creationDate=" + creationDate + "]";
	}

}
