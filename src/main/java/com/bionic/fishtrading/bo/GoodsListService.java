package com.bionic.fishtrading.bo;

import java.util.List;

import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.GoodsListItem;

public interface GoodsListService {
	public List<GoodsListItem> getItemsForSale();

	public ColdStoreItem getColdStoreItemById(int id);
}
