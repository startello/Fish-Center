package com.bionic.fishtrading.bean;

import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.model.FishType;

@Named
@Scope("session")
public class FishTypeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8918735541750259690L;
	private FishType fishType;
	private Part image;
	@Inject
	private GeneralManagerService gms;

	@PostConstruct
	public void init() {
		fishType = new FishType();
		image = null;
	}

	public FishType getFishType() {
		return fishType;
	}

	public void setFishType(FishType fishType) {
		this.fishType = fishType;
	}

	public Part getImage() {
		return image;
	}

	public void setImage(Part image) {
		this.image = image;

	}

	public String saveFishType() {
		if (image != null)
			try {
				InputStream inputStream = image.getInputStream();
				fishType.setImage(IOUtils.toByteArray(inputStream));
			} catch (Exception e) {
				e.printStackTrace();
			}
		gms.updateFishType(fishType);
		init();
		return "general?faces-redirect=true";
	}

	public void clear() {
		init();
	}

	public void editFishType(String id) {
		fishType = gms.getFishTypeById(Integer.parseInt(id));
	}
}
