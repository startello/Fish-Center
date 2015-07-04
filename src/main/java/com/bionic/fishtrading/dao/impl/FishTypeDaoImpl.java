package com.bionic.fishtrading.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.fishtrading.dao.FishTypeDao;
import com.bionic.fishtrading.model.FishType;

@Repository
public class FishTypeDaoImpl implements FishTypeDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4099509197676751340L;
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(FishType fishType) {
		em.persist(fishType);
	}

	@Override
	public void update(FishType fishType) {
		em.merge(fishType);
	}

	@Override
	public FishType getById(int id) {
		return em.find(FishType.class, id);
	}

	@Override
	public List<FishType> getByName(String name) {
		TypedQuery<FishType> query = em.createQuery(
				"SELECT ft FROM FishType ft WHERE ft.name LIKE '%" + name
						+ "%'", FishType.class);
		return query.getResultList();
	}

	@Override
	public List<FishType> getAll() {
		TypedQuery<FishType> query = em.createQuery(
				"SELECT ft FROM FishType ft", FishType.class);
		return query.getResultList();

	}

}
