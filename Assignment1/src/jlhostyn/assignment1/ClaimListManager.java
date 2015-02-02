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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

/*
 * A manager for the ClaimListController. This implements sharedPreferences and persistence 
 * based on Student Picker 8 video by Abram Hindle (see README for video url)
 */
public class ClaimListManager {
	static final String preffile = "ClaimList";
	static final String clKey = "claimList";
	
	Context context;
	
	private static ClaimListManager claimListManager = null;
	
	//constructor
	public static void initManager(Context context) {
		if (claimListManager == null) {
			if (context==null) {
				throw new RuntimeException("Missing context for claimListManager");
			}
			claimListManager = new ClaimListManager(context);
		}
	}
	
	//ClaimListManager getter
	public static ClaimListManager getManager() {
		if (claimListManager == null) {
			throw new RuntimeException("Did not init manager");
		}
		return claimListManager;
	}
	
	//ClaimListManager context setter
	public ClaimListManager(Context context) {
		this.context = context;
	}

	//loads from sharedPreference
	public ClaimList loadClaimList() throws ClassNotFoundException, IOException {
		SharedPreferences settings = context.getSharedPreferences(preffile, Context.MODE_PRIVATE);
		String claimListData = settings.getString(clKey, "");
		if (claimListData.equals("")) {
			return new ClaimList();
		}
		else 
			return claimListFromString(claimListData);
	}
	
	//converts encoded string to the ClaimList object
	public static ClaimList claimListFromString(String claimListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(claimListData,Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (ClaimList)oi.readObject();
	}
	
	//converts ClaimList object to encoded string
	public static String claimListToString(ClaimList cl) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(cl);
		oo.close();
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}

	//saves to sharedPreference
	public void saveClaimList(ClaimList cl) throws IOException {
		SharedPreferences settings = context.getSharedPreferences(preffile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(clKey, claimListToString(cl));
		editor.commit();
	}
	
}
