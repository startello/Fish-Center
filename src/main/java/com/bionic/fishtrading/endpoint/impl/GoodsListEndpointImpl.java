package com.bionic.fishtrading.endpoint.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;

import com.bionic.fishtrading.bo.GoodsListService;
import com.bionic.fishtrading.endpoint.GoodsListEndpoint;
import com.bionic.fishtrading.model.GoodsListItem;

@WebService(endpointInterface = "com.bionic.fishtrading.endpoint.GoodsListEndpoint")
@Named
public class GoodsListEndpointImpl implements GoodsListEndpoint {
	@Inject
	private GoodsListService gls;

	@Override
	public List<GoodsListItem> getAll() {
		return gls.getItemsForSale();
	}
}
