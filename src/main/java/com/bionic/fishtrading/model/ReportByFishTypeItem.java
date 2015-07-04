package com.bionic.fishtrading.model;

import java.io.Serializable;

public class ReportByFishTypeItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 60605535133627640L;
	private String fishName;
	private int numberOfOrders;
	private double sum;

	public ReportByFishTypeItem(String fishName, long numberOfOrders, double sum) {
		this.fishName = fishName;
		this.numberOfOrders = (int) numberOfOrders;
		this.sum = sum;
	}

	public ReportByFishTypeItem() {
	}

	public String getFishName() {
		return fishName;
	}

	public void setFishName(String fishName) {
		this.fishName = fishName;
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
}
