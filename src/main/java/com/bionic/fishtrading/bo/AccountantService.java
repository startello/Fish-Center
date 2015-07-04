package com.bionic.fishtrading.bo;

import java.util.List;

import com.bionic.fishtrading.model.ParcelOrder;

public interface AccountantService {
	public List<ParcelOrder> getAllParcelOrders();

	public List<ParcelOrder> getAllOrdersToReturn();

	public ParcelOrder getParcelOrderById(int id);

	public void updateParcelOrder(ParcelOrder order);
}
