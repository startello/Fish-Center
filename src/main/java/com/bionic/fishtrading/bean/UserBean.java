package com.bionic.fishtrading.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.SecurityOfficerService;
import com.bionic.fishtrading.model.User;

@Named
@Scope("session")
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491416975594537270L;
	private User user;
	@Inject
	SecurityOfficerService securityOfficerService;

	public UserBean() {

	}

	@PostConstruct
	public void init() {
		user = new User();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void edit(String id) {
		user = securityOfficerService.getById(Integer.parseInt(id));
		System.out.println(user);
	}

	public void save() {
		System.out.println(user.getId());
		securityOfficerService.update(user);
	}

	public String savePassword() {
		System.out.println(user.getId());
		securityOfficerService.update(user);
		return "account?faces-redirect=true";
	}

	public void clear() {
		user = new User();
	}

	public void validateOldPassword(FacesContext context,
			UIComponent component, Object value) throws ValidatorException {
		String old = (String) value;
		if (!old.isEmpty()) {
			if (!old.equals(user.getPassword()))
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Old password validation error.", "Wrong password."));
		}
	}

}
