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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/*
 * This activity is activated whenever Expense objects are being added or edited
 * from claims of the main ClaimListController
 * 
 * Called from ViewExpenseActivity to add or edit expenses
 */
@SuppressLint("SimpleDateFormat") public class AddExpenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_expense);
		ClaimListManager.initManager(this.getApplicationContext());
		
		//give both the ClaimList position of the Claim and the position of 
		//the expense in that claims array to editExpense
		Bundle extras = getIntent().getExtras();
		long claimPos = extras.getLong("claimPos", -1);
		if (claimPos != -1) {
			long expensePos = extras.getLong("expensePos");
			editExpense((int)claimPos, (int)expensePos);
		}
	}
	
	//displays previous information about an expense in the EditText text inputs
	private void editExpense(int claimPos, int expensePos) {
		EditText name = (EditText) findViewById(R.id.addExpenseNameText);
		EditText date = (EditText) findViewById(R.id.addExpenseDateText);
		EditText description = (EditText) findViewById(R.id.addExpenseDescriptionText);
		EditText cost = (EditText) findViewById(R.id.addAmountText);
		Spinner category = (Spinner) findViewById(R.id.pickCategorySpinner);
		Spinner currencyType = (Spinner) findViewById(R.id.pickCurrencySpinner);
		name.setText(ClaimListController.getClaimList()
				.getClaim(claimPos).getExpense(expensePos).getExpenseName());
		date.setText(ClaimListController.getClaimList()
				.getClaim(claimPos).getExpense(expensePos).getExpenseDateString());
		description.setText(ClaimListController.getClaimList()
				.getClaim(claimPos).getExpense(expensePos).getExpenseDescription());
		cost.setText(ClaimListController.getClaimList()
				.getClaim(claimPos).getExpense(expensePos).getExpenseCostString());
		category.setSelection(ClaimListController.getClaimList()
				.getClaim(claimPos).getExpense(expensePos).getCategoryInt());
		currencyType.setSelection(ClaimListController.getClaimList()
				.getClaim(claimPos).getExpense(expensePos).getCurrencyInt());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//adds expense or updates existing expense
	public void addExpense(View v) {
		//get the EditText fields
		EditText name = (EditText) findViewById(R.id.addExpenseNameText);
		EditText date = (EditText) findViewById(R.id.addExpenseDateText);
		EditText description = (EditText) findViewById(R.id.addExpenseDescriptionText);
		EditText cost = (EditText) findViewById(R.id.addAmountText);
		Spinner category = (Spinner) findViewById(R.id.pickCategorySpinner);
		Spinner currencyType = (Spinner) findViewById(R.id.pickCurrencySpinner);
		//parse the string input from user
		String nameString = name.getText().toString();
		String dateString = date.getText().toString();
		String descrString = description.getText().toString();
		String costString = cost.getText().toString();
		String categoryString = category.getSelectedItem().toString();
		String currencyTypeString = currencyType.getSelectedItem().toString();
		
		//check multiple user input errors and get them to correct accordingly
		if (nameString.equals("")) {
			name.setError("Name is Required");
			name.requestFocus();
			return;
		}
		if (dateString.equals("")) {
			date.setError("Date is Required");
			date.requestFocus();
			return;
		}
		if (costString.equals("")) {
			cost.setError("Cost is Required");
			cost.requestFocus();
			return;
		}
		if (currencyTypeString.equals("--Please Choose (Required)--")) {
			Toast.makeText(this, "Currency Type is Required", Toast.LENGTH_SHORT).show();
			return;
		}
		//convert string date input to GregorianCalendar
		//http://stackoverflow.com/questions/2331513/convert-a-string-to-a-gregoriancalendar 2015-01-31
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date dateDate;
		try {
			dateDate = df.parse(dateString);
		} catch (ParseException e) {
			Toast.makeText(this, "please make date formats 'yyyy-mm-dd'", 
					Toast.LENGTH_SHORT).show();
			return;
		}
		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.setTime(dateDate);
		
		
		//get claim location in list
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			id = extras.getLong("id");
		}
		if (id == -1) {
			Toast.makeText(this, "expense adding to claim UNSUCCESSFUL",Toast.LENGTH_SHORT).show(); 
			finish();
		}
		//if the Expense object is being edited
		long claimPos = extras.getLong("claimPos", -1);
		if (claimPos != -1) {
			Toast.makeText(this, "Editting Expense", Toast.LENGTH_SHORT).show();
			long expensePos = extras.getLong("expensePos");
			//set new name
			ClaimListController.getClaimList()
					.getClaim((int)claimPos).getExpense((int)expensePos).setExpenseName(nameString);
			//set new date
			ClaimListController.getClaimList()
					.getClaim((int)claimPos).getExpense((int)expensePos).setExpenseDate(cal);
			//set new cost
			ClaimListController.getClaimList()
					.getClaim((int)claimPos).getExpense((int)expensePos).
							setExpenseCost(Double.valueOf(costString));
			//set new currency type
			ClaimListController.getClaimList()
					.getClaim((int)claimPos).getExpense((int)expensePos)
							.setExpenseCurrency(currencyTypeString);
			//set new description
			ClaimListController.getClaimList()
					.getClaim((int)claimPos).getExpense((int)expensePos)
							.setExpenseDescription(descrString);
			//set new category
			ClaimListController.getClaimList()
					.getClaim((int)claimPos).getExpense((int)expensePos)
							.setExpenseCategory(categoryString);
			//notify listeners
			ClaimListController.getClaimList().notifyListeners();
			//go back to ViewExpenseActivity
			finish();
		}
		
		//if neither description or category are filled
		else if (descrString.equals("")
				&& categoryString.equals("--Choose Category (Optional)--")) {
			Expense expense = new Expense(nameString,cal,
					Double.valueOf(costString),currencyTypeString);
			ClaimListController.getClaimList().addExpenseToClaim((int)id,expense);
			finish();
		}
		
		//if description is filled but not category
		else if (!(descrString.equals("")) && 
				categoryString.equals("--Choose Category (Optional)--")) {
			Expense expense = new Expense(nameString,cal,
					Double.valueOf(costString),currencyTypeString,descrString,0);
			ClaimListController.getClaimList().addExpenseToClaim((int)id,expense);
			finish();
		}
		
		//if category is filled but not description
		else if (descrString.equals("") && 
				!(categoryString.equals("--Choose Category (Optional)--"))) {
			Expense expense = new Expense(nameString,cal,
					Double.valueOf(costString),currencyTypeString,categoryString,1);
			ClaimListController.getClaimList().addExpenseToClaim((int)id,expense);
			finish();
		}
		
		//if everything is filled
		else if (!(descrString.equals("")) && 
				!(categoryString.equals("--Choose Category (Optional)--"))) {
			Expense expense = new Expense(nameString,cal,
					Double.valueOf(costString),currencyTypeString,descrString,categoryString);
			ClaimListController.getClaimList().addExpenseToClaim((int)id,expense);
			finish();
		}
		
	}
	
}
