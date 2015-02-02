/* 
Travel Expense App: A travel expense tacking android application
    Copyright (C) 2015  Jared Hostyn jlhostyn@ualberta.ca

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package jlhostyn.assignment1;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/*
 * The Claim object. This represents a claim for a travel expense and has
 * it's own list of expenses that are attributed that the individual claim
 */
@SuppressLint("SimpleDateFormat") public class Claim implements Serializable {

	/**
	 * Claim Serializable ID used to implement sharedPreferences and persistence 
	 * based on Student Picker 8 video by Abram Hindle (see README for video url)
	 */
	private static final long serialVersionUID = -1610498091533600783L;
	//attributes of claim
	protected String claimName;
	protected GregorianCalendar claimStartDate;
	protected GregorianCalendar claimEndDate;
	protected String claimDescription;
	protected ArrayList<Expense> expenseList;
	
	//Claim constructor
	public Claim(String name, GregorianCalendar startDate, GregorianCalendar endDate) {
		this.claimName = name;
		this.claimStartDate = startDate;
		this.claimEndDate = endDate;
		this.claimDescription = null;
		this.expenseList = new ArrayList<Expense>();
	}
	
	//Claim constructor
	public Claim(String name, GregorianCalendar startDate, GregorianCalendar endDate, String description) {
		this.claimName = name;
		this.claimStartDate = startDate;
		this.claimEndDate = endDate;
		this.claimDescription = description;
		this.expenseList = new ArrayList<Expense>();
	}

	//name attribute getter
	public String getClaimName() {
		return this.claimName;
	}

	//start date attribute getter
	public GregorianCalendar getClaimStartDate() {
		return this.claimStartDate;
	}
	
	//start date attribute getter returns start date in String form yyyy-mm-dd
	public String getClaimStartDateString() {
		GregorianCalendar g = this.claimStartDate;
		String startDate;
		//http://stackoverflow.com/questions/24741696/convert-java-gregorian-calendar-to-string 2015-02-01
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		startDate = formatter.format(g.getTime());
		return startDate;
	}

	//end date attribute getter
	public GregorianCalendar getClaimEndDate() {
		return this.claimEndDate;
	}
	
	//end date attribute getter returns end date in String form yyyy-mm-dd
	public String getClaimEndDateString() {
		GregorianCalendar g = this.claimEndDate;
		String endDate;
		//http://stackoverflow.com/questions/24741696/convert-java-gregorian-calendar-to-string 2015-02-01
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		endDate = formatter.format(g.getTime());
		return endDate;
	}
	
	//description attribute getter
	public String getClaimDescription(){
		return this.claimDescription;
	}
	
	//name setter
	public void setName(String claimName) {
		this.claimName = claimName;
	}
	
	//start date setter
	public void setStartDate(GregorianCalendar claimStartDate) {
		this.claimStartDate = claimStartDate;
	}

	//end date setter
	public void setEndDate(GregorianCalendar claimEndDate) {
		this.claimEndDate = claimEndDate;
	}

	//description setter
	public void setDescription(String claimDescription) {
		this.claimDescription = claimDescription;
	}
	
	//adds an expense to the expenseList ArrayList
	public void addExpense(Expense expense){
		this.expenseList.add(expense);
	}
	
	//removes an expense from the expenseList ArrayList based on position in the ArrayList
	public void removeExpense(int expensePos){
		this.expenseList.remove(expensePos);
	}
	
	//expense list attribute getter
	public ArrayList<Expense> getExpenseList() {
		return this.expenseList;
	}
	
	//expense list attribute setter
	public void setExpenseList(ArrayList<Expense> expenses) {
		this.expenseList = expenses;
	}
	
	//adds an expense to the expenseList ArrayList and replaces existing expense present
	public void setExpense(int i, Expense expense) {
		this.expenseList.set(i, expense);
	}
	
	//gets the individual claim at a specified position in the array
	public Expense getExpense(int i) {
		return this.expenseList.get(i);
	}
	
	//return array list, in the form CAN(0),USD(1),EUR(2),GBP(3), of the total currency amounts.
	public ArrayList<Double> getExpenses() {
		double CAD = 0.00;
		double USD = 0.00;
		double EUR = 0.00;
		double GBP = 0.00;
		ArrayList<Double> currencies = new ArrayList<Double>();
		for(int i=0;i<expenseList.size();i++) {
			if (expenseList.get(i).getExpenseCurrency().equals("CAD")) {
				CAD = CAD + expenseList.get(i).getExpenseCost();
			}
			else if (expenseList.get(i).getExpenseCurrency().equals("USD")) {
				USD = USD + expenseList.get(i).getExpenseCost();
			}
			else if (expenseList.get(i).getExpenseCurrency().equals("EUR")) {
				EUR = EUR + expenseList.get(i).getExpenseCost();
			}
			else if (expenseList.get(i).getExpenseCurrency().equals("GBP")) {
				GBP = GBP + expenseList.get(i).getExpenseCost();
			}
		}
		currencies.add(CAD);
		currencies.add(USD);
		currencies.add(EUR);
		currencies.add(GBP);
		return currencies;
	}
	
	//how claim is presented in a ListView
	public String toString() {
		return "Name: " + claimName + "\nStart Date: " + getClaimStartDateString();	
	}

	//resets the entire expenseList and resets it
	public void removeExpenses() {
		this.expenseList = new ArrayList<Expense>();
	}
	
	//makes a huge String of every expense for emailing/displaying purposes
	public String getExpenseString() {
		String expenseString = "";
		for(int i=0;i<expenseList.size();i++) {
			expenseString = expenseString + "\nExpense Name: " + expenseList.get(i).getExpenseName();
			expenseString = expenseString + "\nDate: " + expenseList.get(i).getExpenseDateString();
			expenseString = expenseString + "\nCost: " + 
						expenseList.get(i).getExpenseCostString() 
								+ " " + expenseList.get(i).getExpenseCurrency();
			expenseString = expenseString + "\nDescription: \"" 
								+ expenseList.get(i).getExpenseDescription() + "\"";
			expenseString = expenseString + "\nCategory: " + expenseList.get(i).getExpenseCategory();
			expenseString = expenseString + "\n";
		}
		return expenseString;
	}
	
	// used to implement sharedPreferences and persistence 
	public boolean equals(Object compareClaim) {
		if ((compareClaim != null) && 
				compareClaim.getClass()==this.getClass()) {
			return this.equals((Claim)compareClaim);
		}
		else
			return false;
	}
	
	// used to implement sharedPreferences and persistence 
	public boolean equals(Claim compareClaim) {
		if (compareClaim==null) {
			return false;
		}
		return getClaimName().equals(compareClaim.getClaimName());	
	}
	public int hashCode() {
		return ("Claim"+getClaimName()).hashCode();
	}
}
