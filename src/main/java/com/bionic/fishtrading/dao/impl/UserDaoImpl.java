package com.bionic.fishtrading.dao.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.bionic.fishtrading.dao.UserDao;
import com.bionic.fishtrading.model.User;

@Repository
public class UserDaoImpl implements UserDao, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6747698168999755419L;
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(User u) {
		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.email ='" + u.getEmail() + "'",
				User.class);
		if (query.getResultList().isEmpty()) {
			u.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
			u.setStatus(0);
			em.persist(u);
		}
	}

	@Override
	public List<User> getAll() {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u",
				User.class);
		return query.getResultList();
	}

	@Override
	public User getById(int id) {
		return em.find(User.class, id);
	}

	@Override
	public void setStatus(int id, int status) {
		em.find(User.class, id).setStatus(status);
	}

	@Override
	public User auth(String email, String password) {
		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.email = '" + email
						+ "' AND u.password = '" + password + "'", User.class);
		User u = query.getSingleResult();
		return u.getStatus() == 0 ? u : null;
	}

	@Override
	public List<User> getAllByPosition(int position) {
		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.type = " + position, User.class);
		return query.getResultList();
	}

	@Override
	public void update(User u) {
		if (u.getCreationDate() == null)
			u.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		em.merge(u);
	}

	@Override
	public boolean isEmailUnique(String email) {
		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.email = '" + email + "'",
				User.class);
		return query.getResultList().isEmpty();
	}

	@Override
	public List<User> getAllCustomers() {
		TypedQuery<User> query = em.createQuery(
				"SELECT u FROM User u WHERE u.type = 0", User.class);
		return query.getResultList();
	}
}
