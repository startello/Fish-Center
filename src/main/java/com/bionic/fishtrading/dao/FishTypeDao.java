package com.bionic.fishtrading.dao;

import java.util.List;

import com.bionic.fishtrading.model.FishType;

public interface FishTypeDao {
	public void add(FishType fishType);

	public void update(FishType fishType);

	public FishType getById(int id);

	public List<FishType> getByName(String name);

	public List<FishType> getAll();
}
