package com.bionic.fishtrading.bo.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.fishtrading.bo.AccountantService;
import com.bionic.fishtrading.dao.ParcelOrderDao;
import com.bionic.fishtrading.model.ParcelOrder;

@Named
public class AccountantServiceImpl implements AccountantService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1564657026988687864L;
	@Inject
	ParcelOrderDao parcelOrderDao;

	@Override
	public List<ParcelOrder> getAllParcelOrders() {
		List<ParcelOrder> list = parcelOrderDao.getByStatus(0);
		list.addAll(parcelOrderDao.getByStatus(1));
		return list;
	}

	@Override
	public ParcelOrder getParcelOrderById(int id) {
		return parcelOrderDao.getById(id);
	}

	@Override
	@Transactional
	public void updateParcelOrder(ParcelOrder order) {
		parcelOrderDao.update(order);
	}

	@Override
	public List<ParcelOrder> getAllOrdersToReturn() {
		return parcelOrderDao.getByStatus(4);
	}
}
