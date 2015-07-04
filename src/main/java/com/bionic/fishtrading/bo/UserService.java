package com.bionic.fishtrading.bo;

import java.util.List;

import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.User;

public interface UserService {
	// Users
	public void addUser(User user);

	public void updateUser(User user);

	public User auth(String email, String password);

	// Parcel Order
	public void addParcelOrder(ParcelOrder parcelOrder) throws Exception;

	public void cancelParcelOrder(int id);

	public List<ParcelOrder> getParcelOrdersByUserId(int id);

	// Cold Store Item
	public ColdStoreItem getColdStoreItemById(int id);
}
