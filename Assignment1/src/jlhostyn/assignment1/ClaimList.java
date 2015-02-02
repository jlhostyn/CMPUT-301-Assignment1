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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/*
 * The ClaimList object. This represents a list of claims.
 */
public class ClaimList implements Serializable {
	
	/**
	 * ClaimList serialization ID used used to implement sharedPreferences and persistence 
	 * based on Student Picker 8 video by Abram Hindle (see README for video url)
	 */
	private static final long serialVersionUID = -7737579833594811805L;
	
	//attributes of ClaimList
	protected ArrayList<Claim> claimList = null;
	protected transient ArrayList<Listener> listeners = null;
	
	//constructor
	public ClaimList() {
		claimList = new ArrayList<Claim>();
		listeners = new ArrayList<Listener>();
	}
	
	//gets the listeners or creates a new list. For sharedPreferences and persistence
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	
	//adds a claim to the ClaimList
	public void addClaim(Claim claim) {
		claimList.add(claim);
		sortClaims();
		notifyListeners();
	}
	
	//delegation of Claim method
	public void addExpenseToClaim(int i, Expense e) {
		Claim c = claimList.get(i);
		c.addExpense(e);
		claimList.set(i,c);
		sortClaims();
		//c.notifyListeners();
		notifyListeners();
		
	}
	
	//gets listeners to update themselves
	public void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	
	//remove a claim in the ClaimList based on the Claim itself
	public void removeClaim(Claim claim) {
		claimList.remove(claim);
		sortClaims();
		notifyListeners();
	}
	
	//remove a claim in the ClaimList based on position of the claim in the ClaimList
	public void removeClaim(int i) {
		claimList.remove(i);
		sortClaims();
		notifyListeners();
	}
	
	//getter for ClaimList
	public ArrayList<Claim> getClaimList() {
		return claimList;
	}
	
	//setter for ClaimList
	public void setClaimList(ArrayList<Claim> claims) {
		this.claimList = claims;
	}
	
	//adds a Claim to the claimList ArrayList and replaces existing claim present
	public void setClaim(int i, Claim claim) {
		this.claimList.set(i, claim);
		sortClaims();
		notifyListeners();
	}
	
	//returns claim at position in the claimList
	public Claim getClaim(int i) {
		return this.claimList.get(i);
	}
	
	//sorts the claims in the claimList based on their start dates
	public void sortClaims() {
		//http://stackoverflow.com/questions/14049975/how-to-sort-the-dates-from-current-to-old-date-in-android-or-java 2015-01-30
		Comparator<Claim> compareDate = new Comparator<Claim>() {
			public int compare(Claim claim1, Claim claim2) {
				return claim1.getClaimStartDate().compareTo(claim2.getClaimStartDate());
			}
		};
		Collections.sort(claimList, compareDate);
	}

	//adds a listener to the Listener list
	public void addListener(Listener l) {
		getListeners().add(l);
	}

	//removes a listener from the Listener list
	public void removeListener(Listener l) {
		getListeners().remove(l);		
	}
	
	//delegation - removes every expense in a specific claim's expense list 
	public void removeExpenses(int i) {
		this.claimList.get(i).removeExpenses();
		notifyListeners();
	}
	
	//delegation - removes a specific expense from a specific claim
	public void removeExpense(int claimPos, int expensePos) {
		this.claimList.get(claimPos).removeExpense(expensePos);
		notifyListeners();
	}
	
}
