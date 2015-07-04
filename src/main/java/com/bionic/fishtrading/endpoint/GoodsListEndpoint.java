package com.bionic.fishtrading.endpoint;

import java.util.List;

import javax.jws.WebService;

import com.bionic.fishtrading.model.GoodsListItem;

@WebService
public interface GoodsListEndpoint {
	public List<GoodsListItem> getAll();
}
