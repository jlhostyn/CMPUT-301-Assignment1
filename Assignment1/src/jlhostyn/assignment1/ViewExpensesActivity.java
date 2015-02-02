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
import java.util.Collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/*
 * This activity lists the Expense items of a Claim object from the ClaimList of
 * ClaimListController and allows the user to start adding an Expense or click on 
 * existing Expense Objects and interact with an alert dialog to decide what to do
 * with them.
 */
public class ViewExpensesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_list);
		
		ClaimListManager.initManager(this.getApplicationContext());
		
		Toast.makeText(this, "Listing Expenses",Toast.LENGTH_SHORT).show();
		TextView claim = (TextView) findViewById(R.id.claimExpensesTextView);
		Bundle extras = getIntent().getExtras();
		int id = (int) extras.getLong("id");
		claim.setText("Expenses for " + ClaimListController.getClaimList()
				.getClaim(id).getClaimName());
		
		ListView listView =(ListView) findViewById(R.id.expensesListView);
        Collection<Expense> expenses = 
        		ClaimListController.getClaimList().getClaim(id).getExpenseList(); 
        final ArrayList<Expense> list = new ArrayList<Expense>(expenses);
        final ArrayAdapter<Expense> expenseAdapter = new ArrayAdapter<Expense>(this,
        		android.R.layout.simple_list_item_1, list);
        listView.setAdapter(expenseAdapter);
        
        // observer
        ClaimListController.getClaimList().addListener(new Listener() {
			@Override
			public void update() {
				Bundle extras = getIntent().getExtras();
				final int id = (int) extras.getLong("id");
				Collection<Expense> expenses2;
				list.clear();
				try { 
					expenses2 = 
						ClaimListController.getClaimList().getClaim(id).getExpenseList();
				}
				catch (Exception e) {
					expenses2 = new ArrayList<Expense>();
				}
				list.addAll(expenses2);
				expenseAdapter.notifyDataSetChanged();
				
			}
		});
        
        // on expense click an alert dialog is made displaying a menu of edit or delete the claim
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, final long expensePos) {
				
				//http://stackoverflow.com/questions/4671428/how-can-i-add-a-third-button-to-an-android-alert-dialog 2015-02-01
				//http://stackoverflow.com/questions/8227820/alert-dialog-two-buttons 2015-02-01
				AlertDialog.Builder builder = new AlertDialog.Builder(ViewExpensesActivity.this);
				builder.setMessage("Expense Options")
				   .setCancelable(false)
				   .setNegativeButton("Edit Expense", new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int i) {
				    	//edit expense
				    	Bundle extras = getIntent().getExtras();
				   		long claimPos = extras.getLong("id");
				   		Intent intent = new Intent(ViewExpensesActivity.this, AddExpenseActivity.class);
				   		intent.putExtra("claimPos", claimPos);
				   		intent.putExtra("expensePos", expensePos);
				   		intent.putExtra("id", claimPos);
				   		startActivity(intent);
				    	   
				       }
				   })
				   .setNeutralButton("Delete Expense", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int i) {
						Bundle extras = getIntent().getExtras();
						int claimPos = (int) extras.getLong("id");
						//delete correct expense
						ClaimListController.getClaimList().removeExpense(claimPos, (int)expensePos);
					}  
				   })
				   
				   //leave the dialog
				   .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int i) {
				           dialog.cancel();
				       }
				   });
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_espense, menu);
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
	
	//adds an expense if user clicks button
	public void addExpensePress(View v) {
		Bundle extras = getIntent().getExtras();
		long id = extras.getLong("id");
		Intent intent = new Intent(ViewExpensesActivity.this, AddExpenseActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
	
}