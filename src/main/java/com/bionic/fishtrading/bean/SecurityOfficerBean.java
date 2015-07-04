package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.SecurityOfficerService;
import com.bionic.fishtrading.model.User;

@Named
@Scope("request")
public class SecurityOfficerBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1846750042750975306L;
	private List<User> users;
	private Map<String, Integer> roles = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> statuses = new LinkedHashMap<String, Integer>();
	private String[] rolesArray = new String[] { "Customer", "General Manager",
			"Cold Store Manager", "Accountant" };
	@Inject
	SecurityOfficerService securityOfficerService;

	public SecurityOfficerBean() {
		roles.put(rolesArray[0], 0);
		roles.put(rolesArray[1], 1);
		roles.put(rolesArray[2], 2);
		roles.put(rolesArray[3], 3);
		statuses.put("Enabled", 0);
		statuses.put("Disabled", 1);
	}

	@PostConstruct
	public void init() {
		users = securityOfficerService.getAll();
	}

	public String[] getRolesArray() {
		return rolesArray;
	}

	public void setRolesArray(String[] rolesArray) {
		this.rolesArray = rolesArray;
	}

	public Map<String, Integer> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, Integer> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Map<String, Integer> getStatuses() {
		return statuses;
	}

	public void setStatuses(Map<String, Integer> statuses) {
		this.statuses = statuses;
	}

}
