package com.bionic.fishtrading.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.UserService;
import com.bionic.fishtrading.model.User;

@Named
@Scope("request")
public class RegisterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9059945388895768241L;
	private User user;
	@Inject
	private UserService userService;

	public RegisterBean() {

	}

	@PostConstruct
	public void init() {
		user = new User();
		user.setType(0);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String createNew() {
		try {
			userService.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index?faces-redirect=true";
	}

}
