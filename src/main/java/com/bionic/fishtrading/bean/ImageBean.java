package com.bionic.fishtrading.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.model.FishType;

@Named
@Scope("request")
public class ImageBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8743162785560700678L;
	@Inject
	private GeneralManagerService gms;

	public ImageBean() {

	}

	public StreamedContent getImageById() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String itemId = context.getExternalContext()
					.getRequestParameterMap().get("itemId");
			FishType fishType = gms.getFishTypeById(Integer.valueOf(itemId));
			if (fishType.getImage() == null)
				return null;
			return new DefaultStreamedContent(new ByteArrayInputStream(
					fishType.getImage()));
		}
	}

	public boolean hasImage(String id) {
		FishType ft = gms.getFishTypeById(Integer.parseInt(id));
		if (ft == null)
			return false;
		return ft.getImage() != null;
	}
}
