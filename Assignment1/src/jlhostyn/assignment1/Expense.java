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
import java.util.GregorianCalendar;

@SuppressLint("SimpleDateFormat") public class Expense implements Serializable {
	
	/**
	 * Expense Serializable ID used to implement sharedPreferences and persistence 
	 * based on Student Picker 8 video by Abram Hindle (see README for video url)
	 */
	private static final long serialVersionUID = -3165142911748995056L;
	protected String expenseName;
	protected GregorianCalendar expenseDate;
	protected String expenseCategory;
	protected String expenseDescription;
	protected double expenseCost;
	protected String expenseCurrency; 
	
	//description and currency aren't specified
	public Expense(String name, GregorianCalendar date, double cost, 
			String currency) {
		this.expenseName = name;
		this.expenseDate = date;
		this.expenseCost = cost;
		this.expenseCurrency = currency;
		this.expenseCategory = null;
		this.expenseDescription = null;
	}
	
	//everything is specified
	public Expense(String name, GregorianCalendar date, double cost,
			String currency, String description, String category) {
		this.expenseName = name;
		this.expenseDate = date;
		this.expenseCost = cost;
		this.expenseCurrency = currency;
		this.expenseCategory = category;
		this.expenseDescription = description;
	}
	
	//i=0, description is specified. i=1 category is specified
	public Expense(String name, GregorianCalendar date, double cost,
			String currency, String desOrCat, int i ) {
		this.expenseName = name;
		this.expenseDate = date;
		this.expenseCost = cost;
		this.expenseCurrency = currency;
		if (i==0) {
			this.expenseCategory = null;
			this.expenseDescription = desOrCat;
		}
		else if (i==1) {
			this.expenseCategory = desOrCat;
			this.expenseDescription = null;
		}
		else {
			this.expenseCategory = null;
			this.expenseDescription = null;
		}
	}
	
	public String getExpenseName() {
		return expenseName;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public GregorianCalendar getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(GregorianCalendar expenseDate) {
		this.expenseDate = expenseDate;
	}
	
	public String getExpenseDateString() {
		GregorianCalendar g = this.expenseDate;
		String date;
		//http://stackoverflow.com/questions/24741696/convert-java-gregorian-calendar-to-string 2015-01-02
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		date = formatter.format(g.getTime());
		return date;
	}

	public String getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(String expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public String getExpenseDescription() {
		return expenseDescription;
	}

	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}

	public double getExpenseCost() {
		return expenseCost;
	}

	public void setExpenseCost(double expenseCost) {
		this.expenseCost = expenseCost;
	}

	public String getExpenseCurrency() {
		return expenseCurrency;
	}

	public void setExpenseCurrency(String expenseCurrency) {
		this.expenseCurrency = expenseCurrency;
	}
	
	//replaces Object version of toString()
	public String toString() {
		return "Name: " + this.expenseName + "\nDate: "
				+ this.getExpenseDateString() + "\nCost: " + this.expenseCost 
						+ " " + this.expenseCurrency;
	}

	//returns a string version of expense cost
	public String getExpenseCostString() {
		return Double.toString(this.expenseCost);
	}
	
	//returns the position of the category variable that the expense contains
	//based on a predefined list
	public int getCategoryInt() {
		if(this.expenseCategory == null){
			return 0;
		}
		else if(this.expenseCategory.equals("--Choose Category (Optional)--")){
			return 0;
		}
		else if(this.expenseCategory.equals("Air Fare")) {
			return 1;
		}
		else if(this.expenseCategory.equals("Ground Transport")) {
			return 2;
		}
		else if(this.expenseCategory.equals("Vehicle Rental")) {
			return 3;
		}
		else if(this.expenseCategory.equals("Fuel")) {
			return 4;
		}
		else if(this.expenseCategory.equals("Parking")) {
			return 5;
		}
		else if(this.expenseCategory.equals("Registration")) {
			return 6;
		}
		else if(this.expenseCategory.equals("Accomodation")) {
			return 7;
		}
		else if(this.expenseCategory.equals("Meal")) {
			return 8;
		}
		else
			return -1;
	}

	//returns the position of the currency variable that the expense contains
	//based on a predefined list
	public int getCurrencyInt() {
		if(this.expenseCurrency.equals("--Please Choose (Required)--")){
			return 0;
		}
		else if(this.expenseCurrency.equals("CAD")) {
			return 1;
		}
		else if(this.expenseCurrency.equals("USD")) {
			return 2;
		}
		else if(this.expenseCurrency.equals("EUR")) {
			return 3;
		}
		else if(this.expenseCurrency.equals("GBP")) {
			return 4;
		}
		else
			return -1;
	}

}
