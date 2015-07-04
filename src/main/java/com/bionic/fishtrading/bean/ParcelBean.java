package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.ColdStoreManagerService;
import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.FishType;
import com.bionic.fishtrading.model.Parcel;

@Named
@Scope("session")
public class ParcelBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6994976236225346817L;
	private static final int ARRIVED = 1;
	private Parcel parcel;
	private ColdStoreItem csi;
	private List<FishType> ftList;
	private FishType ft;
	private double price;
	private ColdStoreItem writeOffItem;
	private Map<String, Integer> statuses = new LinkedHashMap<String, Integer>();
	@Inject
	GeneralManagerService gms;
	@Inject
	ColdStoreManagerService csms;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ColdStoreItem getWriteOffItem() {
		return writeOffItem;
	}

	public void setWriteOffItem(ColdStoreItem writeOffItem) {
		this.writeOffItem = writeOffItem;
	}

	public FishType getFt() {
		return ft;
	}

	public void setFt(FishType ft) {
		System.out.println(ft);
		this.ft = ft;
	}

	public List<FishType> getFtList() {
		return ftList;
	}

	public void setFtList(List<FishType> ftList) {
		this.ftList = ftList;
	}

	public ParcelBean() {
		statuses.put("For Sale", 2);
		statuses.put("Unsaled", 1);
	}

	@PostConstruct
	public void init() {
		parcel = new Parcel();
		parcel.setColdStoreItems(new ArrayList<ColdStoreItem>());
		csi = new ColdStoreItem();
		ftList = gms.getAllFishTypes();
		ft = new FishType();
		price = 0;
	}

	public Map<String, Integer> getStatuses() {
		return statuses;
	}

	public void setStatuses(Map<String, Integer> statuses) {
		this.statuses = statuses;
	}

	public Parcel getParcel() {
		return parcel;
	}

	public void setParcel(Parcel parcel) {
		this.parcel = parcel;
	}

	public ColdStoreItem getCsi() {
		return csi;
	}

	public void setCsi(ColdStoreItem csi) {
		this.csi = csi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String saveParcel() {
		try {
			for (ColdStoreItem item : parcel.getColdStoreItems())
				item.setColdStoreItemParcel(parcel);
			gms.addParcel(parcel);
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "general.xhtml?faces-redirect=true";
	}

	public String saveColdStoreItem() {
		try {
			csi.setFishType(ft);
			parcel.getColdStoreItems().add(csi);
			csi = new ColdStoreItem();
			ft = new FishType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addParcel";
	}

	public String removeCsi(String csiItem) {
		for (ColdStoreItem item : new ArrayList<ColdStoreItem>(
				parcel.getColdStoreItems())) {
			if (item.toString().equals(csiItem.toString()))
				parcel.getColdStoreItems().remove(item);
		}
		return null;
	}

	public String editParcel(String id) {
		parcel = gms.getParcelById(Integer.parseInt(id));
		csi = new ColdStoreItem();
		ft = new FishType();
		return "addParcel.xhtml?faces-redirect=true";
	}

	public void editCsi(String id) {
		csi = gms.getColdStoreItemById(Integer.parseInt(id));
	}

	public void saveCsi() {
		gms.updateColdStoreItem(csi);
	}

	public void setFtid(String id) {
		ft = gms.getFishTypeById(Integer.parseInt(id));
	}

	public String getFtid() {
		if (ft == null)
			return "0";
		return ft.getId() + "";
	}

	public String addParcel() {
		init();
		return "addParcel.xhtml?faces-redirect=true";
	}

	public String removeParcel() {
		try {
			if (parcel.getStatus() == 0)
				parcel.setStatus(2);
			gms.updateParcel(parcel);
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "general.xhtml?faces-redirect=true";
	}

	public String clear() {
		init();
		return "general.xhtml?faces-redirect=true";
	}

	public void validateFishType(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		String id = (String) value;
		System.out.println(id);
		for (ColdStoreItem item : parcel.getColdStoreItems()) {
			if (id.equals(item.getFishType().getId() + ""))
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Adding cold store item error.",
						"Item is already in the list."));
		}
	}

	public String confirmParcel(String id) {
		parcel = gms.getParcelById(Integer.parseInt(id));
		return "confirmParcel?faces-redirect=true";
	}

	public String confirm(String id) {
		ColdStoreItem csi = new ColdStoreItem();
		for (ColdStoreItem item : parcel.getColdStoreItems()) {
			if (id.equals(item.getId() + "")) {
				csi = item;
			}
		}
		if (csi.getStatus() != ARRIVED) {
			csi.setStatus(ARRIVED);
			csi.setArrivalDate(java.sql.Date.valueOf(LocalDate.now()));
			csi.setWeightArrived(csi.getWeightPurchased());
			csi.setWeightLeft(csi.getWeightPurchased());
		} else {
			csi.setWeightArrived(0);
			csi.setWeightLeft(0);
			csi.setStatus(0);
		}
		return null;
	}

	public String getRowClasses() {
		StringBuilder rowClasses = new StringBuilder();

		for (ColdStoreItem item : parcel.getColdStoreItems()) {
			if (rowClasses.length() > 0)
				rowClasses.append(",");
			if (item.getStatus() == ARRIVED) {
				rowClasses.append("success");
			} else {
				rowClasses.append("danger");
			}

		}
		return rowClasses.toString();
	}

	public String saveParcelCSM() {
		try {
			gms.updateParcel(parcel);
			init();
		} catch (Exception e) {
		}
		return "coldstore?faces-redirect=true";
	}

	public String clearCSM() {
		init();
		return "coldstore?faces-redirect=true";
	}

	public String requestWriteOff() {
		gms.writeOffItem(writeOffItem.getId());
		return "general?faces-redirect=true";
	}

	public String writeOffCsi() {
		csms.writeOffItem(writeOffItem.getId());
		return "coldstore?faces-redirect=true";
	}

	public void writeOffModal(String id) {
		writeOffItem = gms.getColdStoreItemById(Integer.parseInt(id));
	}

}
