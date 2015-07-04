package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.GoodsListService;
import com.bionic.fishtrading.model.GoodsListItem;

@Named
@Scope("request")
public class IndexBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7469862925794749475L;
	private List<GoodsListItem> gli;
	@Inject
	GoodsListService gls;

	public IndexBean() {

	}

	@PostConstruct
	public void init() {
		gli = gls.getItemsForSale();
	}

	public List<GoodsListItem> getGli() {
		return gli;
	}

	public void setGli(List<GoodsListItem> gli) {
		this.gli = gli;
	}

}
