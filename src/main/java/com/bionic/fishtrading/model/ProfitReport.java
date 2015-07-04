package com.bionic.fishtrading.model;

public class ProfitReport {
	private double income;
	private double expense;
	private int incomeOrders;
	private int expenseOrders;

	public ProfitReport() {
		income = 0;
		expense = 0;
		incomeOrders = 0;
		expenseOrders = 0;
	}

	public ProfitReport(double income, double expense, int incomeOrders,
			int expenseOrders) {
		this.income = income;
		this.expense = expense;
		this.incomeOrders = incomeOrders;
		this.expenseOrders = expenseOrders;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public int getIncomeOrders() {
		return incomeOrders;
	}

	public void setIncomeOrders(int incomeOrders) {
		this.incomeOrders = incomeOrders;
	}

	public int getExpenseOrders() {
		return expenseOrders;
	}

	public void setExpenseOrders(int expenseOrders) {
		this.expenseOrders = expenseOrders;
	}

}
