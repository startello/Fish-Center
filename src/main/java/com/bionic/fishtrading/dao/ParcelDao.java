package com.bionic.fishtrading.dao;

import java.util.List;

import com.bionic.fishtrading.model.Parcel;

public interface ParcelDao {
	public void add(Parcel parcel);

	public void update(Parcel parcel);

	public void setStatus(int id, int status);

	public Parcel getById(int id);

	public List<Parcel> getAll();

	public List<Parcel> getByStatus(int status);
}
