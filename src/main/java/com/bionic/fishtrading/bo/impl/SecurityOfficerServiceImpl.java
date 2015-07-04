package com.bionic.fishtrading.bo.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.bionic.fishtrading.bo.SecurityOfficerService;
import com.bionic.fishtrading.dao.UserDao;
import com.bionic.fishtrading.model.User;

@Named
public class SecurityOfficerServiceImpl implements SecurityOfficerService,
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2482222285086601223L;
	@Inject
	UserDao userDao;

	@Override
	@Transactional
	public void add(User sm) {
		userDao.add(sm);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User getById(int id) {
		return userDao.getById(id);
	}

	@Override
	@Transactional
	public void deactivate(int id) {
		userDao.setStatus(id, 1);
	}

	@Override
	public User auth(String email, String password) {
		return userDao.auth(email, password);
	}

	@Override
	@Transactional
	public void activate(int id) {
		userDao.setStatus(id, 0);
	}

	@Override
	public List<User> getAllByPosition(int position) {
		return userDao.getAllByPosition(position);
	}

	@Override
	@Transactional
	public void update(User sm) {
		userDao.update(sm);
	}

}
