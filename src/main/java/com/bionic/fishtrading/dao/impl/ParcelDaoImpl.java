package com.bionic.fishtrading.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.fishtrading.dao.ParcelDao;
import com.bionic.fishtrading.model.Parcel;

@Repository
public class ParcelDaoImpl implements ParcelDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8837156812999530354L;
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Parcel parcel) {
		em.persist(parcel);
	}

	@Override
	public void update(Parcel parcel) {
		em.merge(parcel);
	}

	@Override
	public void setStatus(int id, int status) {
		em.find(Parcel.class, status).setStatus(status);
	}

	@Override
	public Parcel getById(int id) {
		return em.find(Parcel.class, id);
	}

	@Override
	public List<Parcel> getAll() {
		TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p",
				Parcel.class);
		return query.getResultList();
	}

	@Override
	public List<Parcel> getByStatus(int status) {
		TypedQuery<Parcel> query = em.createQuery(
				"SELECT p FROM Parcel p WHERE p.status ='" + status + "'",
				Parcel.class);
		return query.getResultList();
	}

}
