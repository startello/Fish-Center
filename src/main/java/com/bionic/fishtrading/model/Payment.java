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
public class Payment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5331637532151735771L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "parcelOrderId")
	private ParcelOrder paymentParcelOrder;
	private double sumPayed;
	@Temporal(TemporalType.DATE)
	private java.util.Date creationDate;

	public Payment() {

	}

	public Payment(ParcelOrder paymentParcelOrder, double sumPayed) {
		super();
		this.paymentParcelOrder = paymentParcelOrder;
		this.sumPayed = sumPayed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ParcelOrder getPaymentParcelOrder() {
		return paymentParcelOrder;
	}

	public void setPaymentParcelOrder(ParcelOrder paymentParcelOrder) {
		this.paymentParcelOrder = paymentParcelOrder;
	}

	public double getSumPayed() {
		return sumPayed;
	}

	public void setSumPayed(double sumPayed) {
		this.sumPayed = sumPayed;
	}

	public java.util.Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentParcelOrder="
				+ paymentParcelOrder + ", sumPayed=" + sumPayed
				+ ", creationDate=" + creationDate + "]";
	}

}
