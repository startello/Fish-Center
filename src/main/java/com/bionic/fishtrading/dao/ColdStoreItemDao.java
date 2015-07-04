package com.bionic.fishtrading.dao;

import java.util.List;

import com.bionic.fishtrading.model.ColdStoreItem;
import com.bionic.fishtrading.model.GoodsListItem;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;

public interface ColdStoreItemDao {
	// public void add(ColdStoreItem csi);

	public void update(ColdStoreItem csi);

	public void setStatus(int id, int status);

	public ColdStoreItem getById(int id);

	// public List<ColdStoreItem> getAll();

	public List<ColdStoreItem> getByStatus(int status);

	public List<ColdStoreItem> getItemsInColdStore();

	public List<GoodsListItem> getItemsForSale();

	public List<ReportByDateItem> getReportByDate();

	public List<ReportByFishTypeItem> getReportByFishType();

	public List<ReportByDateItem> getReportByDate(java.util.Date start,
			java.util.Date end);

	public List<ReportByFishTypeItem> getReportByFishType(java.util.Date start,
			java.util.Date end);
}
