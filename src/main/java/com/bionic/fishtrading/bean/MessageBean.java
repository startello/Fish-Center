package com.bionic.fishtrading.bean;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

@Named
@Scope("request")
public class MessageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3035612873228741836L;
	private final String required = "Required Field.";
	private final String range = "Out of range.";
	private final String negative = "Could not be negative.";
	private final String positive = "Value should be above zero.";

	public String getPositive() {
		return positive;
	}

	public String getRequired() {
		return required;
	}

	public String getRange() {
		return range;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNegative() {
		return negative;
	}

}
