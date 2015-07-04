package com.bionic.fishtrading.bo.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.bionic.fishtrading.bo.GoodsListService;
import com.bionic.fishtrading.dao.ColdStoreItemDao;
import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.GoodsListItem;

@Named
public class GoodsListServiceImpl implements GoodsListService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6026652292155705586L;
	@Inject
	private ColdStoreItemDao coldStoreItemDao;

	@Override
	public List<GoodsListItem> getItemsForSale() {
		return coldStoreItemDao.getItemsForSale();
	}

	@Override
	public ColdStoreItem getColdStoreItemById(int id) {
		return coldStoreItemDao.getById(id);
	}

}
