package com.bionic.fishtrading.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.springframework.context.annotation.Scope;

import com.bionic.fishtrading.bo.GeneralManagerService;
import com.bionic.fishtrading.model.ProfitReport;
import com.bionic.fishtrading.model.ReportByDateItem;
import com.bionic.fishtrading.model.ReportByFishTypeItem;

@Named
@Scope("session")
public class IncomeReportBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8626477879286434131L;
	private List<ReportByDateItem> incomeParcels;
	private List<ReportByDateItem> expenseCsi;
	private List<ReportByFishTypeItem> incomeFishParcels;
	private List<ReportByFishTypeItem> expenseFishCsi;
	private LineChartModel dateModel;
	private LineChartModel fishModel;
	private Map<java.util.Date, ProfitReport> profitReportByDateMap = new TreeMap<java.util.Date, ProfitReport>();
	private List<Entry<java.util.Date, ProfitReport>> profitReportByDate = new ArrayList<Entry<java.util.Date, ProfitReport>>();
	private Map<String, ProfitReport> profitReportByFishTypeMap = new TreeMap<String, ProfitReport>();
	private List<Entry<String, ProfitReport>> profitReportByFishType = new ArrayList<Entry<String, ProfitReport>>();
	private java.util.Date startDate;
	private java.util.Date endDate;

	@Inject
	GeneralManagerService gms;

	public IncomeReportBean() {

	}

	@PostConstruct
	public void init() {
		java.util.Date date = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, -1);
		startDate = cal.getTime();
		endDate = date;
		profitReportByDateMap.clear();
		profitReportByDate.clear();
		profitReportByFishTypeMap.clear();
		profitReportByFishType.clear();
		incomeParcels = gms.getParcelOrdersByDate();
		expenseCsi = gms.getColdStoreItemsByDate();
		incomeFishParcels = gms.getParcelItemsByFishType();
		expenseFishCsi = gms.getColdStoreItemsByFishType();
		createDateModel();
		createFishModel();
	}

	public String update() {
		profitReportByDateMap.clear();
		profitReportByDate.clear();
		profitReportByFishTypeMap.clear();
		profitReportByFishType.clear();
		incomeParcels = gms.getParcelOrdersByDate(startDate, endDate);
		expenseCsi = gms.getColdStoreItemsByDate(startDate, endDate);
		incomeFishParcels = gms.getParcelItemsByFishType(startDate, endDate);
		expenseFishCsi = gms.getColdStoreItemsByFishType(startDate, endDate);
		createDateModel();
		createFishModel();
		return "report.xhtml?faces-redirect=true";
	}

	public String reset() {
		init();
		return "report.xhtml?faces-redirect=true";
	}

	private void createDateModel() {
		dateModel = new LineChartModel();
		dateModel.setLegendPosition("e");

		for (ReportByDateItem item : incomeParcels) {

			if (profitReportByDateMap.containsKey(item.getDate())) {
				ProfitReport report = profitReportByDateMap.get(item.getDate());
				report.setIncome(report.getIncome() + item.getSum());
				report.setIncomeOrders(report.getIncomeOrders()
						+ item.getNumberOfOrders());
			} else {
				profitReportByDateMap.put(
						item.getDate(),
						new ProfitReport(item.getSum(), 0, item
								.getNumberOfOrders(), 0));
			}
		}
		for (ReportByDateItem item : expenseCsi) {

			if (profitReportByDateMap.containsKey(item.getDate())) {
				ProfitReport report = profitReportByDateMap.get(item.getDate());
				report.setExpense(report.getExpense() + item.getSum());
				report.setExpenseOrders(report.getExpenseOrders()
						+ item.getNumberOfOrders());
			} else {
				profitReportByDateMap.put(item.getDate(), new ProfitReport(0,
						item.getSum(), 0, item.getNumberOfOrders()));
			}
		}
		profitReportByDate = new ArrayList<Entry<java.util.Date, ProfitReport>>(
				profitReportByDateMap.entrySet());
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Income");
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Expense");
		LineChartSeries series3 = new LineChartSeries();
		series3.setLabel("Profit");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Entry<java.util.Date, ProfitReport> item : profitReportByDate) {
			series1.set(df.format(item.getKey()), item.getValue().getIncome());
			series2.set(df.format(item.getKey()), item.getValue().getExpense());
			series3.set(df.format(item.getKey()), item.getValue().getIncome()
					- item.getValue().getExpense());
		}

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);
		dateModel.addSeries(series3);

		dateModel.setZoom(false);
		dateModel.getAxis(AxisType.Y).setLabel("Sum");
		DateAxis axis = new DateAxis("Dates");
		axis.setTickAngle(-50);
		axis.setMax(df.format(new java.util.Date()));
		axis.setTickFormat("%b %#d, %y");

		dateModel.getAxes().put(AxisType.X, axis);
	}

	private void createFishModel() {
		fishModel = new LineChartModel();
		fishModel.setLegendPosition("e");
		for (ReportByFishTypeItem item : incomeFishParcels) {

			if (profitReportByFishTypeMap.containsKey(item.getFishName())) {
				ProfitReport report = profitReportByFishTypeMap.get(item
						.getFishName());
				report.setIncome(report.getIncome() + item.getSum());
				report.setIncomeOrders(report.getIncomeOrders()
						+ item.getNumberOfOrders());
			} else {
				profitReportByFishTypeMap.put(
						item.getFishName(),
						new ProfitReport(item.getSum(), 0, item
								.getNumberOfOrders(), 0));
			}
		}
		for (ReportByFishTypeItem item : expenseFishCsi) {

			if (profitReportByFishTypeMap.containsKey(item.getFishName())) {
				ProfitReport report = profitReportByFishTypeMap.get(item
						.getFishName());
				report.setExpense(report.getExpense() + item.getSum());
				report.setExpenseOrders(report.getExpenseOrders()
						+ item.getNumberOfOrders());
			} else {
				profitReportByFishTypeMap.put(
						item.getFishName(),
						new ProfitReport(0, item.getSum(), 0, item
								.getNumberOfOrders()));
			}
		}
		profitReportByFishType = new ArrayList<Entry<String, ProfitReport>>(
				profitReportByFishTypeMap.entrySet());
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Income");
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Expense");
		LineChartSeries series3 = new LineChartSeries();
		series3.setLabel("Profit");
		for (Entry<String, ProfitReport> item : profitReportByFishType) {
			series1.set(item.getKey(), item.getValue().getIncome());
			series2.set(item.getKey(), item.getValue().getExpense());
			series3.set(item.getKey(), item.getValue().getIncome()
					- item.getValue().getExpense());
		}

		fishModel.addSeries(series1);
		fishModel.addSeries(series2);
		fishModel.addSeries(series3);

		fishModel.setZoom(false);
		fishModel.getAxis(AxisType.Y).setLabel("Sum");
		CategoryAxis axis = new CategoryAxis("Fish Name");
		axis.setTickAngle(-50);

		fishModel.getAxes().put(AxisType.X, axis);
	}

	public List<ReportByDateItem> getIncomeParcels() {
		return incomeParcels;
	}

	public void setIncomeParcels(List<ReportByDateItem> incomeParcels) {
		this.incomeParcels = incomeParcels;
	}

	public List<ReportByDateItem> getExpenseCsi() {
		return expenseCsi;
	}

	public void setExpenseCsi(List<ReportByDateItem> expenseCsi) {
		this.expenseCsi = expenseCsi;
	}

	public LineChartModel getDateModel() {
		return dateModel;
	}

	public void setDateModel(LineChartModel dateModel) {
		this.dateModel = dateModel;
	}

	public LineChartModel getFishModel() {
		return fishModel;
	}

	public void setFishModel(LineChartModel fishModel) {
		this.fishModel = fishModel;
	}

	public List<ReportByFishTypeItem> getIncomeFishParcels() {
		return incomeFishParcels;
	}

	public void setIncomeFishParcels(
			List<ReportByFishTypeItem> incomeFishParcels) {
		this.incomeFishParcels = incomeFishParcels;
	}

	public List<ReportByFishTypeItem> getExpenseFishCsi() {
		return expenseFishCsi;
	}

	public void setExpenseFishCsi(List<ReportByFishTypeItem> expenseFishCsi) {
		this.expenseFishCsi = expenseFishCsi;
	}

	public List<Entry<java.util.Date, ProfitReport>> getProfitReportByDate() {
		return profitReportByDate;
	}

	public void setProfitReportByDate(
			List<Entry<java.util.Date, ProfitReport>> profitReportByDate) {
		this.profitReportByDate = profitReportByDate;
	}

	public List<Entry<String, ProfitReport>> getProfitReportByFishType() {
		return profitReportByFishType;
	}

	public void setProfitReportByFishType(
			List<Entry<String, ProfitReport>> profitReportByFishType) {
		this.profitReportByFishType = profitReportByFishType;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getIncomeOrdersSumByDate() {
		int sum = 0;
		for (ReportByDateItem item : incomeParcels) {
			sum += item.getNumberOfOrders();
		}
		return sum;
	}

	public int getExpenseOrdersSumByDate() {
		int sum = 0;
		for (ReportByDateItem item : expenseCsi) {
			sum += item.getNumberOfOrders();
		}
		return sum;
	}

	public double getIncomeSumByDate() {
		double sum = 0;
		for (ReportByDateItem item : incomeParcels) {
			sum += item.getSum();
		}
		return ((double) ((int) (sum * 100))) / 100;
	}

	public double getExpenseSumByDate() {
		double sum = 0;
		for (ReportByDateItem item : expenseCsi) {
			sum += item.getSum();
		}
		return ((double) ((int) (sum * 100))) / 100;
	}

	public double getProfitSumByDate() {
		return getIncomeSumByDate() - getExpenseSumByDate();
	}

	public int getIncomeOrdersSumByFishType() {
		int sum = 0;
		for (ReportByFishTypeItem item : incomeFishParcels) {
			sum += item.getNumberOfOrders();
		}
		return sum;
	}

	public int getExpenseOrdersSumByFishType() {
		int sum = 0;
		for (ReportByFishTypeItem item : expenseFishCsi) {
			sum += item.getNumberOfOrders();
		}
		return sum;
	}

	public double getIncomeSumByFishType() {
		double sum = 0;
		for (ReportByFishTypeItem item : incomeFishParcels) {
			sum += item.getSum();
		}
		return ((double) ((int) (sum * 100))) / 100;
	}

	public double getExpenseSumByFishType() {
		double sum = 0;
		for (ReportByFishTypeItem item : expenseFishCsi) {
			sum += item.getSum();
		}
		return ((double) ((int) (sum * 100))) / 100;
	}

	public double getProfitSumByFishType() {
		return getIncomeSumByFishType() - getExpenseSumByFishType();
	}
}
