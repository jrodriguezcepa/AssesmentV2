package assesment.communication.assesment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.user.UserData;

public class GroupUsersData {

    private Integer id;    
    private Integer red;
	private Integer yellow;
    private Integer green;
	private Integer pending;
    private Collection<String> countries;
    private Collection<UserData> users;
	private Collection<String> divisions;
	private HashMap<String, HashMap<Integer, Object[]>> results;
	public GroupUsersData() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRed() {
		return red;
	}
	public void setRed(Integer red) {
		this.red = red;
	}
	public Integer getYellow() {
		return yellow;
	}
	public void setYellow(Integer yellow) {
		this.yellow = yellow;
	}
	public Integer getGreen() {
		return green;
	}
	public void setGreen(Integer green) {
		this.green = green;
	}
	public Integer getPending() {
		return pending;
	}
	public void setPending(Integer pending) {
		this.pending = pending;
	}
	public Collection<UserData> getUsers() {
		return users;
	}
	public void setUsers(Collection<UserData> users) {
		this.users = users;
	}
	public Collection<String> getDivisions() {
		return divisions;
	}
	public void setDivisions(Collection<String> divisions) {
		this.divisions = divisions;
	}
	
	public ArrayList<String> getCountryDivisions(String countryId){
		ArrayList<String> results = new ArrayList<String>();
		Iterator it= users.iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			if(String.valueOf(u.getCountry()).equals(countryId)) {
				if(!results.contains(u.getExtraData2().toUpperCase())) {
					results.add(u.getExtraData2().toUpperCase());
				}
			}
		}
		Collections.sort(results);
		return results;
	}
/*	public HashMap<Integer, Integer[]> getResultsByCountryDivision(String countryId, String division){
		Collection<Integer[]> results = new LinkedList<Integer[]>();
		Iterator it= getCountryDivisions(countryId).iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			if(String.valueOf(u.getCountry()).equals(countryId)) {
				if(!results.contains(u.getExtraData2().toUpperCase())) {
					results.add(u.getExtraData2().toUpperCase());
				}
			}
		}
		return results;
	}*/
	
}

