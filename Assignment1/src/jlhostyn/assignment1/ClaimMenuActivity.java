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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/*
 * This activity displays the menu when a Claim is clicked on in the MainActivity ListView
 * and acts accordingly based on what buttons are pressed
 * 
 * goes to ViewExpenseActivity or ClaimSummaryActivity if the user clicks to view/add an expense
 * or to see the Claim summary
 */
public class ClaimMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_claim_menu);
		
		ClaimListManager.initManager(this.getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_menu, menu);
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
	
	//delete claim button pressed. Delete corresponding expenses and claim itself
	public void deleteClaim(View v) {
		Bundle extras = getIntent().getExtras();
		long id = -1;
		if (extras != null) {
		    id = extras.getLong("id");
		}
		if (id != -1) {
			ClaimListController.getClaimList().removeExpenses((int)id);
			ClaimListController.getClaimList().removeClaim((int)id);
		}
		else {
			Toast.makeText(this, "Delete UNSUCCESSFUL",Toast.LENGTH_SHORT).show(); 
		}
		finish();
	}
	
	//edit claim button pressed, passes position to addClaimActivity for editing
	public void editClaim(View v) {
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			id = extras.getLong("id");
		}
		if (id == -1) {
			Toast.makeText(this, "edit UNSUCCESSFUL",Toast.LENGTH_SHORT).show(); 
			finish();
		}
		Intent intent = new Intent(ClaimMenuActivity.this, AddClaimActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
	
	//goes to list expenses activity for the specific claim
	public void listExpenses(View v) {
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			id = extras.getLong("id");
		}
		if (id == -1) {
			Toast.makeText(this, "viewing expenses UNSUCCESSFUL",Toast.LENGTH_SHORT).show(); 
			finish();
		}
		Intent intent = new Intent(ClaimMenuActivity.this, ViewExpensesActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
	
	//emails the claim summary
	public void emailClaim(View v) {
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			id = extras.getLong("id");
		}
		if (id == -1) {
			Toast.makeText(this, "send email UNSUCCESSFUL",Toast.LENGTH_SHORT).show(); 
			finish();
		}
		//create the string that will be the body of the email
		Claim c = ClaimListController.getClaimList().getClaim((int)id);
		ArrayList<Double> expenseTotal = c.getExpenses();
		String body = new String();
		body = body + "\nClaim";
		body = body + "\n----------------";
		body = body + "\nName: " + c.getClaimName();
		body = body + "\nDate Range: " + c.getClaimStartDateString() 
				+ " - " + c.getClaimEndDateString();
		body = body + "\nDiscription: \"" + c.getClaimDescription() + "\"";
		body = body + "\nCAN: $" + expenseTotal.get(0);
		body = body + "\nUSD: $" + expenseTotal.get(1);
		body = body + "\nEUR: €" + expenseTotal.get(2);
		body = body + "\nGBP: £" + expenseTotal.get(3);
		body = body + "\n";
		body = body + "\nClaim Expenses";
		body = body + "\n----------------";
		body = body + c.getExpenseString();
		
		//http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application 2015-02-01
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, "Claim From App");
		i.putExtra(Intent.EXTRA_TEXT   , body);
		try {
		    startActivity(Intent.createChooser(i, "Sending Mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(ClaimMenuActivity.this, 
		    		"No email clients are installed on this device!", Toast.LENGTH_SHORT).show();
		}
	}
	
	//displays the claim summary to the screen
	public void claimSummary(View v) {
		Bundle extras = getIntent().getExtras();
		long id =-1;
		if (extras != null) {
			id = extras.getLong("id");
		}
		if (id == -1) {
			Toast.makeText(this, "viewing summary UNSUCCESSFUL",Toast.LENGTH_SHORT).show(); 
			finish();
		}
		Intent intent = new Intent(ClaimMenuActivity.this, ClaimSummaryActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
	
}
