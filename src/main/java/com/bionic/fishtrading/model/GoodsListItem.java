package com.bionic.fishtrading.model;

import java.io.Serializable;

public class GoodsListItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2002899056650538833L;
	private int id;
	private int fishId;
	private double weight;
	private double price;
	private String description;
	private String fishName;

	public String getFishName() {
		return fishName;
	}

	public void setFishName(String fishName) {
		this.fishName = fishName;
	}

	public GoodsListItem() {
	}

	public GoodsListItem(int id, int fishId, String fishName, double weight,
			double price, String description) {
		this.id = id;
		this.fishId = fishId;
		this.fishName = fishName;
		this.weight = weight;
		this.price = price;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFishId() {
		return fishId;
	}

	public void setFishName(int fishId) {
		this.fishId = fishId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
