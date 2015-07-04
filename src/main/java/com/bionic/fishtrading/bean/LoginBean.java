package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.CookieHelper;
import com.bionic.fishtrading.bo.UserService;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.User;

@Named
@Scope("session")
public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4043472034901114677L;
	private User user;
	private String email;
	private String password;
	private boolean remember;
	private static final String OFFICER_EMAIL = "security";
	private static final String OFFICER_PASSWORD = "officer";
	@Inject
	private UserService userService;

	public LoginBean() {

	}

	@PostConstruct
	public void init() {
		user = null;
		email = null;
		password = null;
		try {
			user = userService.auth(CookieHelper.getEncryptedCookie("email")
					.getValue(), CookieHelper.getEncryptedCookie("password")
					.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
		System.out.println(remember);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String logIn() {
		return "index?faces-redirect=true";
	}

	public String getType() {
		return user.getType() + "";
	}

	public void validateLogin(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		UIInput emailComponent = (UIInput) component.getAttributes().get(
				"emailComponent");
		String email = (String) emailComponent.getValue();
		String password = (String) value;
		if (email.equals(OFFICER_EMAIL) && password.equals(OFFICER_PASSWORD)) {
			user = new User("Security", "Officer", email, password, 4);
		} else {
			try {
				user = userService.auth(email, password);
				if (remember) {
					CookieHelper.setEncryptedCookie("email", email, -1);
					CookieHelper.setEncryptedCookie("password", password, -1);
				}
				if (user == null)
					throw new ValidatorException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Invalid crenedetials.",
							"Wrong e-mail or password."));
			} catch (Exception e) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Invalid crenedetials.",
						"Wrong e-mail or password."));
			}
		}
	}

	public String logOut() {
		user = null;
		email = null;
		password = null;
		CookieHelper.clearCookies();
		return "index?faces-redirect=true";
	}

	public List<ParcelOrder> getOrders() {
		if (user != null) {
			return userService.getParcelOrdersByUserId(user.getId());
		}
		return null;
	}

}
