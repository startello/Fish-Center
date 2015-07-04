package com.bionic.fishtrading.bo;

import java.util.List;

import com.bionic.fishtrading.model.User;

public interface SecurityOfficerService {
	public void add(User sm);

	public void update(User sm);

	public List<User> getAll();

	public User getById(int id);

	public void deactivate(int id);

	public User auth(String email, String password);

	public void activate(int id);

	public List<User> getAllByPosition(int position);
}
