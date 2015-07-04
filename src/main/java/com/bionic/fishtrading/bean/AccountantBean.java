package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.AccountantService;
import com.bionic.fishtrading.model.ParcelOrder;

@Named
@Scope("request")
public class AccountantBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9129546008212675461L;
	private List<ParcelOrder> parcelOrders;
	private List<ParcelOrder> ordersToReturn;
	@Inject
	private AccountantService as;

	public AccountantBean() {

	}

	@PostConstruct
	public void init() {
		parcelOrders = as.getAllParcelOrders();
		ordersToReturn = as.getAllOrdersToReturn();
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

	public List<ParcelOrder> getOrdersToReturn() {
		return ordersToReturn;
	}

	public void setOrdersToReturn(List<ParcelOrder> ordersToReturn) {
		this.ordersToReturn = ordersToReturn;
	}
}
