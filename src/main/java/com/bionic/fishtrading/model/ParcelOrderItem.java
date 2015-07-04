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
public class ParcelOrderItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4114331983843879757L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "parcelOrderId")
	private ParcelOrder parcelOrder;
	@ManyToOne
	@JoinColumn(name = "coldStoreItemId")
	private ColdStoreItem coldStoreItem;
	private double weight;
	private double price;
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;

	public ParcelOrderItem() {

	}

	public ParcelOrderItem(ColdStoreItem coldStoreItem, double weight,
			double price) {
		this.coldStoreItem = coldStoreItem;
		this.weight = weight;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ColdStoreItem getColdStoreItem() {
		return coldStoreItem;
	}

	public void setColdStoreItem(ColdStoreItem coldStoreItem) {
		this.coldStoreItem = coldStoreItem;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public ParcelOrder getParcelOrder() {
		return parcelOrder;
	}

	public void setParcelOrder(ParcelOrder parcelOrder) {
		this.parcelOrder = parcelOrder;
	}

	@Override
	public String toString() {
		return "ParcelOrderItem [id=" + id + ", weight=" + weight + ", price="
				+ price + ", creationDate=" + creationDate + ", parcelOrder="
				+ parcelOrder + ", coldStoreItem=" + coldStoreItem + "]";
	}

}
