package assesment.communication.assesment;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.user.UserData;

public class GroupUsersData {

    private Integer id;    
    private Integer red;
	private Integer yellow;
    private Integer green;
	private Integer pending;
    private Collection<Integer> countries;
    private Collection<UserData> users;
	private Collection<String> divisions;
	private Collection<AssesmentAttributes> assesments;
	private HashMap<String, HashMap<Integer, Object[]>> results;
	public GroupUsersData() {
		
	}
	public GroupUsersData(Integer id, Collection countries, Collection divisions,   HashMap<String, HashMap<Integer, Object[]>> results, Collection assesments) {

		this.id=id;
		this.countries=countries;
		this.divisions=divisions;
		this.results=results;
		this.assesments=assesments;
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
	
	public Integer[] getResultsByDivision(Integer countryId, String division, Integer assesment){
		//finalizado-no finalizado

		Integer[] results = {0,0};
		Iterator it= this.users.iterator();
		
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			if(u.getCountry().equals(countryId)&& u.getExtraData2().equals(division)) {
				if(this.results.containsKey(u.getLoginName())) {
					if(this.results.get(u.getLoginName()).containsKey(assesment)) {
						if(this.results.get(u.getLoginName()).get(assesment)[1]!=null) {
							results[0]++;
						}else {
							results[1]++;
						}
					}
				}
			}
			
		}
		return results;
	}
	public Integer getParticipants(String division, Integer countryId){
		Integer total = 0;
		Iterator it= this.users.iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			if(u.getCountry().equals(countryId)&& u.getExtraData2().equals(division)) {
				total++;
			}
		}
		return total;
	}	
	public Integer getTotalByDivision(String division){
		Integer total = 0;
		Iterator it= this.users.iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			if(u.getExtraData2().equals(division)) {
				total++;
			}
		}
		return total;
	}	
	public Integer getTotalByCountry(Integer country){
		Integer total = 0;
		Iterator it= this.users.iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			if(u.getCountry().equals(country)) {
				total++;
			}
		}
		return total;
	}	
	public Integer getTotal(){
		Integer total = 0;
		Iterator it= this.users.iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
			total++;
		}
		return total;
	}	
	
	public Integer[] getChartValues(UsReportFacade usFacade, Integer assesment, UserSessionData userSessionData) throws RemoteException, Exception{
		Integer[] values = {0,0,0};
		Iterator it= this.users.iterator();
		while (it.hasNext()) {
			UserData u= (UserData)it.next();
				if(this.results.containsKey(u.getLoginName())) {
					if(this.results.get(u.getLoginName()).containsKey(assesment)) {
						if(this.results.get(u.getLoginName()).get(assesment)[1]==null) {
							values[2]++;
						}else {
							if(usFacade.isResultRed(u.getLoginName(), new Integer(assesment), userSessionData)) {
								values[1]++;
							}else {
								values[0]++;
							}
						}
					}
				}
		}
		return values;
	}	
		
	public String[] getAssesmentResult(UsReportFacade usFacade, Integer assesment, String user,UserSessionData userSessionData) throws RemoteException, Exception{
		String[] values = {"",""};
		if(this.results.containsKey(user)) {
			if(this.results.get(user).containsKey(assesment)) {
				if(this.results.get(user).get(assesment)[1]==null) {
					values[1]="generic.report.pending";
				}else {
					if(usFacade.isResultRed(user, new Integer(assesment), userSessionData)) {
						values[0]="background-color:#f03232;color:white";
						values[1]="generic.report.notapprouved";
					}else {
						values[0]="background-color:#29c05b;color:white";
						values[1]="result.approuved";							
					}
				}
			}
		}
		return values;
	}	
				
	public Collection<Integer> getCountries() {
		return countries;
	}
	public void setCountries(Collection<Integer> countries) {
		this.countries = countries;
	}
	public HashMap<String, HashMap<Integer, Object[]>> getResults() {
		return results;
	}
	public void setResults(HashMap<String, HashMap<Integer, Object[]>> results) {
		this.results = results;
	}
	public Collection<AssesmentAttributes> getAssesments() {
		return assesments;
	}
	public void setAssesments(Collection<AssesmentAttributes> assesments) {
		this.assesments = assesments;
	}
	
}

