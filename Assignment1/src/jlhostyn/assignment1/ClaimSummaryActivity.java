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

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/*
 * This activity displays the summary of the claim it was clicked from
 */
public class ClaimSummaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_summary);
		
		ClaimListManager.initManager(this.getApplicationContext());
		
		Bundle extras = getIntent().getExtras();
		final int id = (int) extras.getLong("id");
		
		//sets the TextViews to the different parts attributes of the ClaimList
		Claim claim = ClaimListController.getClaimList().getClaim(id);
		TextView name = (TextView) findViewById(R.id.nameSumTextView);
		TextView dateRange = (TextView) findViewById(R.id.dateRangeSumTextView);
		TextView description = (TextView) findViewById(R.id.descriptionSumTextView);
		TextView canSummary = (TextView) findViewById(R.id.canSumTextView);
		TextView usdSummary = (TextView) findViewById(R.id.usdSumTextView);
		TextView eurSummary = (TextView) findViewById(R.id.eurSumTextView);
		TextView gbpSummary = (TextView) findViewById(R.id.gbpSumTextView);
		TextView expenseSummary = (TextView) findViewById(R.id.expensesSumListTextView);
		name.setText("Name: " + claim.getClaimName());
		dateRange.setText("Date Range: " + claim.getClaimStartDateString() 
				+ " - " + claim.getClaimEndDateString());
		if(claim.getClaimDescription() == null) {
			description.setText("Description: Not Entered");
		}
		else {
			description.setText("Discription: \"" + claim.getClaimDescription() + "\"");
		}
		ArrayList<Double> expenseTotal = claim.getExpenses();
		canSummary.setText("CAN: $" + expenseTotal.get(0));
		usdSummary.setText("USD: $" + expenseTotal.get(1));
		eurSummary.setText("EUR: €" + expenseTotal.get(2));
		gbpSummary.setText("GBP: £" + expenseTotal.get(3));
		expenseSummary.setText(claim.getExpenseString());
		//http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android 2015-02-01
		expenseSummary.setMovementMethod(new ScrollingMovementMethod());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_summary, menu);
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
}
