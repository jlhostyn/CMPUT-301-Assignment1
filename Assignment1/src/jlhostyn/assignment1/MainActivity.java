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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * This activity is the main display of the app. The ListView of Claim objects from the
 * ClaimListController are displayed here and can be clicked on. Or more claims can be 
 * added.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClaimListManager.initManager(this.getApplicationContext());
        ListView listView =(ListView) findViewById(R.id.claimListView);
        Collection<Claim> claims = ClaimListController.getClaimList().getClaimList(); 
        final ArrayList<Claim> list = new ArrayList<Claim>(claims);
        final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(claimAdapter);
        
        // observer
        ClaimListController.getClaimList().addListener(new Listener() {
			@Override
			public void update() {
				list.clear();
				Collection<Claim> claims = ClaimListController.getClaimList().getClaimList();
				list.addAll(claims);
				claimAdapter.notifyDataSetChanged();
				
			}
		});
        
        //on click listener for claims displayed on the screen
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//http://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android 2015-01-31
				Intent intent = new Intent(MainActivity.this, ClaimMenuActivity.class);
				intent.putExtra("id", id);
		    	startActivity(intent);
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    //called on add claim button click
    public void addClaim(View v) {
    	Intent intent = new Intent(MainActivity.this, AddClaimActivity.class);
    	startActivity(intent);
    }
}
