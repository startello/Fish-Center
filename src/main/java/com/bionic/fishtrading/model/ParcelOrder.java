package com.bionic.fishtrading.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ParcelOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9051003457205144539L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	private String bankAccount;
	private double sumToPay;
	private double sumPayed;
	@Temporal(TemporalType.DATE)
	private java.util.Date shipmentDate;
	private String description;
	private int status;
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;

	@OneToMany(mappedBy = "parcelOrder", cascade = CascadeType.PERSIST)
	private Collection<ParcelOrderItem> parcelOrderItems;
	@OneToMany(mappedBy = "paymentParcelOrder", cascade = CascadeType.PERSIST)
	private Collection<Payment> payments;

	public ParcelOrder() {

	}

	public ParcelOrder(User user, String bankAccount, Date shipmentDate,
			String description, Collection<ParcelOrderItem> parcelOrderItems) {
		this.user = user;
		this.bankAccount = bankAccount;
		this.sumToPay = 0;
		this.sumPayed = 0;
		this.shipmentDate = shipmentDate;
		this.description = description;
		this.status = 0;
		setParcelOrderItems(parcelOrderItems);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getSumToPay() {
		return sumToPay;
	}

	public void setSumToPay(double sumToPay) {
		this.sumToPay = sumToPay;
	}

	public double getSumPayed() {
		return sumPayed;
	}

	public void setSumPayed(double sumPayed) {
		this.sumPayed = sumPayed;
	}

	public java.util.Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(java.util.Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	public Collection<ParcelOrderItem> getParcelOrderItems() {
		return parcelOrderItems;
	}

	public void setParcelOrderItems(Collection<ParcelOrderItem> parcelOrderItems) {
		this.parcelOrderItems = parcelOrderItems;
		for (ParcelOrderItem item : parcelOrderItems) {
			this.sumToPay += item.getPrice() * item.getWeight();
			item.setParcelOrder(this);
		}
	}

	public Collection<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Collection<Payment> payments) {
		this.payments = payments;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Override
	public String toString() {
		return "ParcelOrder [id=" + id + ", user=" + user + ", bankAccount="
				+ bankAccount + ", sumToPay=" + sumToPay + ", sumPayed="
				+ sumPayed + ", shipmentDate=" + shipmentDate
				+ ", description=" + description + ", status=" + status
				+ ", creationDate=" + creationDate + ", parcelOrderItems="
				+ parcelOrderItems + "]";
	}

}
