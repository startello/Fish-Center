package com.bionic.fishtrading;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.text.BasicTextEncryptor;

public class CookieHelper {
	private static final String KEY = "1246128612135571";
	private static BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

	static {
		textEncryptor.setPassword(KEY);
	}

	public static void setEncryptedCookie(String name, String value, int expiry) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		Cookie cookie = null;

		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					break;
				}
			}
		}
		try {
			if (cookie != null) {
				cookie.setValue(URLEncoder.encode(textEncryptor.encrypt(value),
						"UTF-8"));
			} else {
				cookie = new Cookie(name, URLEncoder.encode(
						textEncryptor.encrypt(value), "UTF-8"));
				cookie.setPath(request.getContextPath());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cookie.setMaxAge(expiry);
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		response.addCookie(cookie);
	}

	public static Cookie getEncryptedCookie(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		Cookie cookie = null;

		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					try {
						cookie.setValue(textEncryptor.decrypt(URLDecoder
								.decode(cookie.getValue(), "UTF-8")));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return cookie;
				}
			}
		}
		return null;
	}

	public static void setCookie(String name, String value, int expiry) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		Cookie cookie = null;

		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					break;
				}
			}
		}
		try {
			if (cookie != null) {
				cookie.setValue(URLEncoder.encode(value, "UTF-8"));
			} else {
				cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
				cookie.setPath(request.getContextPath());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cookie.setMaxAge(expiry);
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		response.addCookie(cookie);
	}

	public static Cookie getCookie(String name) {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		Cookie cookie = null;

		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					try {
						cookie.setValue(URLDecoder.decode(cookie.getValue(),
								"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					return cookie;
				}
			}
		}
		return null;
	}

	public static void clearCookies() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();

		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				setCookie(userCookies[i].getName(), "", 0);
			}
		}
	}
}
