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

import java.io.IOException;

/*
 * This controller has the one static ClaimList of the program that will be the only ClaimList edited
 * and displayed.
 */
public class ClaimListController {
	// Lazy Singleton constructor 
	private static ClaimList claimListSingleton = null;
	static public ClaimList getClaimList() {
		if (claimListSingleton == null) {
			try {
				//ClaimListManager used to implement sharedPreferences and persistence 
				//based on Student Picker 8 video by Abram Hindle (see README for video url)
				claimListSingleton = ClaimListManager.getManager().loadClaimList();
				claimListSingleton.addListener(new Listener() {
					@Override
					public void update() {
						saveClaimList();
					}
				});
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
			}
		}
		return claimListSingleton;
	}
	
	//implements sharedPreferences and persistence from Student Picker 8 video 
	//(see README for video url)
	static public void saveClaimList() {
		try {
			ClaimListManager.getManager().saveClaimList(getClaimList());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
		}
	}
	
	//return the static ClaimList
	public void sortClaims() {
		claimListSingleton.sortClaims();
	}
	
	//delegation - adds a claim to the static ClaimList
	public void addClaim(Claim claim) {
		getClaimList().addClaim(claim);
	}
	
	//delegation - adds a expense to a specified claim in the static ClaimList
	public void addExpenseToClaim(int i, Expense e) {
		getClaimList().addExpenseToClaim(i, e);
	}
	
}
