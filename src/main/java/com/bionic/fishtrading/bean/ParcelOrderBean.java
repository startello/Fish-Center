package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.AccountantService;
import com.bionic.fishtrading.bo.ColdStoreManagerService;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.Payment;

@Named
@Scope("session")
public class ParcelOrderBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4516419994782619261L;
	private ParcelOrder parcelOrder;
	private Payment payment;
	private ParcelOrder confirmOrder;

	@Inject
	private AccountantService as;
	@Inject
	ColdStoreManagerService csms;

	public ParcelOrderBean() {

	}

	@PostConstruct
	public void init() {
		payment = new Payment();
		confirmOrder = new ParcelOrder();
		parcelOrder = new ParcelOrder();
	}

	public ParcelOrder getParcelOrder() {
		return parcelOrder;
	}

	public void setParcelOrder(ParcelOrder parcelOrder) {
		this.parcelOrder = parcelOrder;
	}

	public ParcelOrder getConfirmOrder() {
		return confirmOrder;
	}

	public void setConfirmOrder(ParcelOrder confirmOrder) {
		this.confirmOrder = confirmOrder;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String addPayments(String id) {
		init();
		parcelOrder = as.getParcelOrderById(Integer.parseInt(id));
		return "addPayments.xhtml?faces-redirect=true";
	}

	public String cancel() {
		init();
		return "accountant.xhtml?faces-redirect=true";
	}

	public void addPayment() {
		payment.setPaymentParcelOrder(parcelOrder);
		payment.setCreationDate(java.sql.Date.valueOf(LocalDate.now()));
		parcelOrder.getPayments().add(payment);
		payment = new Payment();
	}

	public String save() {
		as.updateParcelOrder(parcelOrder);
		init();
		return "accountant.xhtml?faces-redirect=true";
	}

	public String returnMoney() {
		parcelOrder.setStatus(5);
		as.updateParcelOrder(parcelOrder);
		init();
		return "accountant.xhtml?faces-redirect=true";
	}

	public String getPayed() {
		double sum = 0;
		for (Payment item : parcelOrder.getPayments()) {
			sum += item.getSumPayed();
		}
		parcelOrder.setSumPayed(sum);
		return sum + "";
	}

	public String commitShipment() {
		confirmOrder.setShipmentDate(java.sql.Date.valueOf(LocalDate.now()));
		as.updateParcelOrder(confirmOrder);
		csms.commitShipment(confirmOrder.getId());
		return "coldstore?faces-redirect=true";
	}

	public void shipmentModal(String id) {
		confirmOrder = csms.getParcelOrderById(Integer.parseInt(id));
	}
}
