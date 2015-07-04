package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.GoodsListService;
import com.bionic.fishtrading.bo.UserService;
import com.bionic.fishtrading.model.GoodsListItem;
import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.ParcelOrderItem;

@Named
@Scope("session")
public class CartBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1758003879452100201L;
	private GoodsListItem modalItem;
	private ParcelOrder parcelOrder;
	private double weight;
	@Inject
	GoodsListService gls;
	@Inject
	UserService userService;

	public GoodsListItem getModalItem() {
		return modalItem;
	}

	public void setModalItem(GoodsListItem modalItem) {
		this.modalItem = modalItem;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ParcelOrder getParcelOrder() {
		return parcelOrder;
	}

	public void setParcelOrder(ParcelOrder parcelOrder) {
		this.parcelOrder = parcelOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CartBean() {

	}

	@PostConstruct
	public void init() {
		parcelOrder = new ParcelOrder();
		parcelOrder.setParcelOrderItems(new ArrayList<ParcelOrderItem>());
	}

	public void addToCart() {
		try {
			removeFromCart(modalItem.getId() + "");
			parcelOrder.getParcelOrderItems().add(
					new ParcelOrderItem(userService
							.getColdStoreItemById(modalItem.getId()), weight,
							modalItem.getPrice()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		weight = 0;

	}

	public void removeFromCart(String id) {
		for (ParcelOrderItem item : new ArrayList<ParcelOrderItem>(
				parcelOrder.getParcelOrderItems())) {
			if (id.equals(item.getColdStoreItem().getId() + "")) {
				parcelOrder.getParcelOrderItems().remove(item);
			}
		}
	}

	public String confirmOrder(String email, String password) {
		try {
			parcelOrder.setUser(userService.auth(email, password));
			double sum = 0;
			for (ParcelOrderItem item : parcelOrder.getParcelOrderItems()) {
				sum += item.getWeight() * item.getPrice();
				item.setParcelOrder(parcelOrder);
			}
			parcelOrder.setSumToPay(sum);
			parcelOrder.setStatus(0);
			userService.addParcelOrder(parcelOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return "cart?faces-redirect=true";
		}
		init();
		return "index?faces-redirect=true";
	}

	public void validateWeightRange(FacesContext context,
			UIComponent component, Object value) throws ValidatorException {
		double enteredWeight = (Double) value;
		if ((enteredWeight <= 0) || (enteredWeight > modalItem.getWeight())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Double value range.", "Value should be between 0 and "
							+ modalItem.getWeight());
			throw new ValidatorException(msg);
		}
	}

	public void setModal(String id) {
		for (GoodsListItem item : gls.getItemsForSale()) {
			if (id.equals(item.getId() + "")) {
				modalItem = item;
			}
		}
	}

	public void resetWeight() {
		weight = 0;
	}

	public String getWeightInTheCart() {
		for (ParcelOrderItem item : parcelOrder.getParcelOrderItems()) {
			if ((modalItem.getId() + "").equals(item.getColdStoreItem().getId()
					+ "")) {
				return item.getWeight() + "";
			}
		}
		return "0.0";
	}

	public String getWeightInTheCart(String id) {
		for (ParcelOrderItem item : parcelOrder.getParcelOrderItems()) {
			if (id.equals(item.getColdStoreItem().getId() + "")) {
				return item.getWeight() + "";
			}
		}
		return "0.0";
	}

	public String getItemsCount() {
		return parcelOrder.getParcelOrderItems().size() + "";
	}

	public String getCardTotal() {
		double sum = 0;
		for (ParcelOrderItem item : parcelOrder.getParcelOrderItems()) {
			sum += item.getWeight() * item.getPrice();
		}
		return sum + "";
	}

	public String getCardTotalWeight() {
		double sum = 0;
		for (ParcelOrderItem item : parcelOrder.getParcelOrderItems()) {
			sum += item.getWeight();
		}
		return sum + "";
	}
}
