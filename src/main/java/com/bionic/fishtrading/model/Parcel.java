package com.bionic.fishtrading.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Parcel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -938536805602515538L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private String shipName;
	private double deliveryPrice;
	private int status;
	@Temporal(TemporalType.DATE)
	private java.util.Date arrivalDate;
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;

	@OneToMany(mappedBy = "coldStoreItemParcel", cascade = CascadeType.PERSIST)
	private Collection<ColdStoreItem> coldStoreItems;

	public Parcel() {

	}

	public Parcel(String name, String description, String shipName,
			double deliveryPrice, Date arrivalDate) {
		this.name = name;
		this.description = description;
		this.shipName = shipName;
		this.deliveryPrice = deliveryPrice;
		this.arrivalDate = arrivalDate;
		this.status = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public double getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
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

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Collection<ColdStoreItem> getColdStoreItems() {
		return coldStoreItems;
	}

	public void setColdStoreItems(Collection<ColdStoreItem> coldStoreItems) {
		this.coldStoreItems = coldStoreItems;
	}

	@Override
	public String toString() {
		return "Parcel [id=" + id + ", name=" + name + ", description="
				+ description + ", shipName=" + shipName + ", deliveryPrice="
				+ deliveryPrice + ", status=" + status + ", arrivalDate="
				+ arrivalDate + ", creationDate=" + creationDate + "]";
	}

}
