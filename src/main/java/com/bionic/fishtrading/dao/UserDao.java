package com.bionic.fishtrading.dao;

import java.util.List;

import com.bionic.fishtrading.model.User;

public interface UserDao {
	public void add(User u);

	public void update(User u);

	public List<User> getAll();

	public List<User> getAllByPosition(int position);

	public User getById(int id);

	public void setStatus(int id, int status);

	public User auth(String email, String password);

	public boolean isEmailUnique(String email);

	public List<User> getAllCustomers();
}
