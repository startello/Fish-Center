package com.bionic.fishtrading.bo.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.fishtrading.bo.UserService;
import com.bionic.fishtrading.dao.ColdStoreItemDao;
import com.bionic.fishtrading.dao.ParcelOrderDao;
import com.bionic.fishtrading.dao.UserDao;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.User;

@Named
public class UserServiceImpl implements UserService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3923386427909653260L;
	@Inject
	private ParcelOrderDao parcelOrderDao;
	@Inject
	private UserDao userDao;
	@Inject
	private ColdStoreItemDao coldStoreItemDao;

	// User
	@Override
	@Transactional
	public void addUser(User user) {
		if (userDao.isEmailUnique(user.getEmail())) {
			user.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
			user.setStatus(0);
			userDao.add(user);
		}
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User auth(String email, String password) {
		return userDao.auth(email, password);
	}

	// Parcel Order
	@Override
	@Transactional
	public void addParcelOrder(ParcelOrder parcelOrder) throws Exception {
		parcelOrderDao.add(parcelOrder);
	}

	@Override
	@Transactional
	public void cancelParcelOrder(int id) {
		parcelOrderDao.setStatus(id, 2);
	}

	@Override
	public List<ParcelOrder> getParcelOrdersByUserId(int id) {
		return parcelOrderDao.getByUserId(id);
	}

	// ColdStoreItem
	@Override
	public ColdStoreItem getColdStoreItemById(int id) {
		return coldStoreItemDao.getById(id);
	}

}
