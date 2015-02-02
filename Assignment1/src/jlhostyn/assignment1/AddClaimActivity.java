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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
 * This activity is activated whenever Claim objects are being added or edited
 * from the ClaimLists of the main ClaimListController
 * 
 * Called from MainActivity(if new claim being added) or ClaimMenuActivity(if claim being edited)
 */
@SuppressLint("SimpleDateFormat") public class AddClaimActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
		ClaimListManager.initManager(this.getApplicationContext());
		
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			id = extras.getLong("id");
			editClaim((int)id);
		}
	}
	
	//show existing data to be edited in the EditView text boxes
	private void editClaim(int id) {
		Claim claim = ClaimListController.getClaimList().getClaim(id);
		EditText claimName = (EditText) findViewById(R.id.claimNameText);
		EditText startDate = (EditText) findViewById(R.id.startDateText);
		EditText endDate = (EditText) findViewById(R.id.endDateText);
		EditText claimDescription = (EditText) findViewById(R.id.claimDescriptionText);
		claimName.setText(claim.getClaimName());
		startDate.setText(claim.getClaimStartDateString());
		endDate.setText(claim.getClaimEndDateString());
		claimDescription.setText(claim.getClaimDescription());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
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
	
	//adds a new claim or edits an already existing one
	public void addClaimToList(View v) {
		ClaimListController cl = new ClaimListController();
		EditText claimName = (EditText) findViewById(R.id.claimNameText);
		EditText startDate = (EditText) findViewById(R.id.startDateText);
		EditText endDate = (EditText) findViewById(R.id.endDateText);
		EditText claimDescription = (EditText) findViewById(R.id.claimDescriptionText);
		String startDateString = startDate.getText().toString();
		String endDateString = endDate.getText().toString();
		String claimNameString = claimName.getText().toString();
		String claimDescString = claimDescription.getText().toString();
		
		//check multiple user input errors and get them to correct accordingly
		if (claimNameString.equals("")) {
			claimName.setError("Name is Required");
			claimName.requestFocus();
			return;
		}
		if (startDateString.equals("")) {
			startDate.setError("Start Date is Required");
			startDate.requestFocus();
			return;
		}
		if (endDateString.equals("")) {
			endDate.setError("End Date is Required");
			endDate.requestFocus();
			return;
		}
		
		//convert text date input to a GregorianCalandar object
		//http://stackoverflow.com/questions/2331513/convert-a-string-to-a-gregoriancalendar 2015-01-31
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date startD, endD;
		//instead of throwing exception it uses the exception to correct user input
		try {
			startD = df.parse(startDateString);
			endD = df.parse(endDateString);
		} catch (ParseException e) {
			Toast.makeText(this, "please make date formats 'yyyy-mm-dd'", Toast.LENGTH_SHORT).show();
			return;
		}
		GregorianCalendar startCal = (GregorianCalendar) Calendar.getInstance();
		GregorianCalendar endCal = (GregorianCalendar) Calendar.getInstance();
		startCal.setTime(startD);
		endCal.setTime(endD);
		
		//claim is being edited
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			//updates every attribute of claim so it doesn't need to check individually if each
			//claim attribute is changed or not
			id = extras.getLong("id");
			ClaimListController.getClaimList().getClaim((int)id).setDescription(claimDescString);
			ClaimListController.getClaimList().getClaim((int)id).setEndDate(endCal);
			ClaimListController.getClaimList().getClaim((int)id).setStartDate(startCal);
			ClaimListController.getClaimList().getClaim((int)id).setName(claimNameString);
			ClaimListController.getClaimList().sortClaims();
			ClaimListController.getClaimList().notifyListeners();
			
			//goes back to MainActivity
			//http://stackoverflow.com/questions/23826483/go-back-to-specific-activity-from-stack 2015-01-02
			Intent intent = new Intent(AddClaimActivity.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		//full claim being made
		else if (!claimDescString.equals("")) {
			cl.addClaim(new Claim(claimNameString,startCal,endCal, claimDescString));
		}
		//partial claim, no description, being made
		else {
			cl.addClaim(new Claim(claimNameString,startCal,endCal));
		}
		//go back to MainActivity
		finish();
	}
}
