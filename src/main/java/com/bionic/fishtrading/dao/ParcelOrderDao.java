package com.bionic.fishtrading.dao;

import java.util.List;

import com.bionic.fishtrading.model.ParcelOrder;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;

public interface ParcelOrderDao {
	public void add(ParcelOrder parcelOrder) throws Exception;

	public void setStatus(int id, int status);

	public ParcelOrder getById(int id);

	public List<ParcelOrder> getAll();

	public List<ParcelOrder> getByStatus(int status);

	public List<ParcelOrder> getAllThatNeedsPayment();

	public List<ParcelOrder> getByFishType(int fishTypeId);

	public List<ReportByDateItem> getReportByDate();

	public List<ParcelOrder> getByUserId(int id);

	public void update(ParcelOrder order);

	public List<ReportByFishTypeItem> getReportByFishType();

	public List<ReportByDateItem> getReportByDate(java.util.Date start,
			java.util.Date end);

	public List<ReportByFishTypeItem> getReportByFishType(java.util.Date start,
			java.util.Date end);
}
