package com.bionic.fishtrading.model;

import java.io.Serializable;

public class ReportByDateItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5752710737753937544L;
	private java.util.Date date;
	private int numberOfOrders;
	private double sum;

	public ReportByDateItem(java.util.Date date, long numberOfOrders, double sum) {
		this.date = date;
		this.numberOfOrders = (int) numberOfOrders;
		this.sum = sum;
	}

	public ReportByDateItem() {
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public int getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(int numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "ReportByDateItem [date=" + date + ", numberOfOrders="
				+ numberOfOrders + ", sum=" + sum + "]";
	}

}
