package assesment.communication.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;

public class UserMutualReportData {
	
	private String firstName;
	private String lastName;
	private String email;
	private String login;
	private String location;

	private int module1;
	private int module2;
	private int module3;
	private int module4;
	private boolean module1Completed;
	private boolean module2Completed;
	private boolean module3Completed;
	private boolean module4Completed;
	private boolean behaviourCompleted;
	private int correctM1;
	private int incorrectM1;
	private int correctM2;
	private int incorrectM2;
	private int correctM3;
	private int incorrectM3;
	private int correctM4;
	private int incorrectM4;
	private int behaviour;
	private int ranking;
	private String totalColor;
	private String colorM1;
	private String colorM2;
	private String colorM3;
	private String colorM4;
	private String mod1Recommendation;
	private String mod2Recommendation;
	private String mod3Recommendation;
	private String mod4Recommendation;

	
	public UserMutualReportData() {
	}

	public UserMutualReportData(String[] data) {
		this.firstName = (data[0] == null) ? "" : data[0];
		this.lastName = (data[1] == null) ? "" : data[1];
		this.email = (data[2] == null) ? "" : data[2];
		this.login = (data[3] == null) ? "" : data[3];
		this.module1Completed=(data[6]==null)?false:true;
		this.module2Completed=(data[8]==null)?false:true;
		this.module3Completed=(data[10]==null)?false:true;
		this.module4Completed=(data[12]==null)?false:true;
		this.behaviourCompleted=(data[5]==null)?false:true;
		this.location = (data[3] == null) ? "" : data[4];
		this.behaviour= (data[5]==null)?0:Integer.parseInt(data[5]);
		this.correctM1 = (data[6]==null)?0:Integer.parseInt(data[6]);
		this.incorrectM1 = (data[7]==null)?0: Integer.parseInt(data[7]);
		this.correctM2 = (data[8]==null)?0:Integer.parseInt(data[8]);
		this.incorrectM1 = (data[9]==null)?0:Integer.parseInt(data[9]);
		this.correctM3 = (data[10]==null)?0:Integer.parseInt(data[10]);
		this.incorrectM1 =(data[11]==null)?0: Integer.parseInt(data[11]);
		this.correctM4 = (data[12]==null)?0:Integer.parseInt(data[12]);
		this.incorrectM1 = (data[6]==null)?0:Integer.parseInt(data[13]);
		this.module1 = (data[6]==null)?0:((Integer.parseInt(data[6])*100)/(Integer.parseInt(data[6])+Integer.parseInt(data[7])));
		this.module2 =(data[8]==null)?0:((Integer.parseInt(data[8])*100)/(Integer.parseInt(data[8])+Integer.parseInt(data[9])));
		this.module3 =(data[10]==null)?0: (data[10]==null)?0:((Integer.parseInt(data[10])*100)/(Integer.parseInt(data[10])+Integer.parseInt(data[11])));
		this.module4 = (data[12]==null)?0:((Integer.parseInt(data[12])*100)/(Integer.parseInt(data[12])+Integer.parseInt(data[13])));
		this.colorM1 = getModuleColor( (data[6]==null)?0:((Integer.parseInt(data[6])*100)/(Integer.parseInt(data[6])+Integer.parseInt(data[7]))));
		this.colorM2 = getModuleColor((data[8]==null)?0:((Integer.parseInt(data[8])*100)/(Integer.parseInt(data[8])+Integer.parseInt(data[9]))));
		this.colorM3 = getModuleColor((data[10]==null)?0: (data[10]==null)?0:((Integer.parseInt(data[10])*100)/(Integer.parseInt(data[10])+Integer.parseInt(data[11]))));
		this.colorM4 = getModuleColor((data[12]==null)?0:((Integer.parseInt(data[12])*100)/(Integer.parseInt(data[12])+Integer.parseInt(data[13]))));
		this.ranking = ((data[6]==null)?0:((Integer.parseInt(data[6])*100)/(Integer.parseInt(data[6])+Integer.parseInt(data[7]))))
		+ ((data[8]==null)?0:((Integer.parseInt(data[8])*100)/(Integer.parseInt(data[8])+Integer.parseInt(data[9]))))
		+ ((data[10]==null)?0:((Integer.parseInt(data[10])*100)/(Integer.parseInt(data[10])+Integer.parseInt(data[11]))))
		+ ((data[12]==null)?0:((Integer.parseInt(data[12])*100)/(Integer.parseInt(data[12])+Integer.parseInt(data[13]))));
		this.totalColor = getModuleColor(ranking/4);
		this.mod1Recommendation=getTextRecommendation(1, (data[6]==null)?0:((Integer.parseInt(data[6])*100)/(Integer.parseInt(data[6])+Integer.parseInt(data[7]))) );
		this.mod2Recommendation=getTextRecommendation(2,(data[8]==null)?0:((Integer.parseInt(data[8])*100)/(Integer.parseInt(data[8])+Integer.parseInt(data[9]))));
		this.mod3Recommendation=getTextRecommendation(3,(data[10]==null)?0:((Integer.parseInt(data[10])*100)/(Integer.parseInt(data[10])+Integer.parseInt(data[11]))));
		this.mod4Recommendation=getTextRecommendation(4,(data[12]==null)?0:((Integer.parseInt(data[12])*100)/(Integer.parseInt(data[12])+Integer.parseInt(data[13]))));
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getModule1() {
		return module1;
	}
	public void setModule1(int module1) {
		this.module1 = module1;
	}
	public int getModule2() {
		return module2;
	}
	public void setModule2(int module2) {
		this.module2 = module2;
	}	
	public int getModule3() {
		return module3;
	}
	public void setModule3(int module3) {
		this.module3 = module3;
	}	
	public int getModule4() {
		return module4;
	}
	public void setModule4(int module4) {
		this.module4 = module4;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public boolean getModule1Completed() {
		return module1Completed;
	}
	public void setModule1Completed(boolean mod1) {
		this.module1Completed = mod1;
	}
	public boolean getModule2Completed() {
		return module2Completed;
	}
	public void setModule2Completed(boolean mod2) {
		this.module2Completed = mod2;
	}
	public boolean getModule3Completed() {
		return module3Completed;
	}
	public void setModule3Completed(boolean mod3) {
		this.module3Completed = mod3;
	}
	public boolean getModule4Completed() {
		return module4Completed;
	}
	public void setModule4Completed(boolean mod4) {
		this.module4Completed = mod4;
	}
	public boolean getBehaviourCompleted() {
		return behaviourCompleted;
	}
	public void setBehaviourCompleted(boolean behav) {
		this.behaviourCompleted = behav;
	}
	public int getBehaviour() {
		return behaviour;
	}
	public void setBehaviour(int behaviour) {
		this.behaviour = behaviour;
	}
	public String getColorM1() {
		return colorM1;
	}
	public void setColorM1(String colorM1) {
		this.colorM1 = colorM1;
	}
	public String getColorM2() {
		return colorM2;
	}
	public void setColorM2(String colorM2) {
		this.colorM2 = colorM2;
	}
	public void setColorM3(String colorM3) {
		this.colorM3 = colorM3;
	}
	public String getColorM3() {
		return colorM3;
	}
	public void setColorM4(String colorM4) {
		this.colorM4 = colorM4;
	}
	public String getColorM4() {
		return colorM4;
	}
	public void setTotalColor(String color) {
		this.totalColor = color;
	}
	public String getTotalColor() {
		return totalColor;
	}
	public String getMod1Recommendation() {
		return mod1Recommendation;
	}
	public void setMod1Recommendation(String rec) {
		this.mod1Recommendation = rec;
	}
	public String getMod2Recommendation() {
		return mod2Recommendation;
	}
	public void setMod2Recommendation(String rec) {
		this.mod2Recommendation = rec;
	}
	public String getMod3Recommendation() {
		return mod3Recommendation;
	}
	public void setMod3Recommendation(String rec) {
		this.mod3Recommendation = rec;
	}
	public String getMod4Recommendation() {
		return mod4Recommendation;
	}
	public void setMod4Recommendation(String rec) {
		this.mod4Recommendation = rec;
	}
	
	public String getTextRecommendation(Integer module, Integer calification) {
		if(calification>=70) {
			return "question25099.answer83751.text";
		}
		else{
			if(module==1) {
				return "generic.data.apply";
				//return "assesment1613.module4354.name" ;
			}else if(module==2) {
				return "generic.data.apply";
				//return "assesment1613.module4356.name";
			}else if (module==3) {
				return "generic.data.apply";
				//return "assesment1613.module4355.name";
			}else {
				return "generic.data.apply";
				//return "assesment1613.module4357.name";
			}
		}
	}
	
		
	public String getModuleColor(Integer module) {
		if(module>70) {
			return "background-color: rgb(30, 209, 105);";
		}else if(module<=50) {
			return "background-color: rgb(231, 84, 92);";
		}else {
			return "background-color: rgb(250, 246, 31);";
		}
	}
	
	public String getPsiColor() {
		if(this.behaviour<3) {
			return "background-color: rgb(30, 209, 105);";
		}else if(this.behaviour>=4) {
			return "background-color: rgb(231, 84, 92);";
		}else {
			return "background-color: rgb(250, 246, 31);";
		}
	}
	public String getPsiText() {
		if(this.behaviour<3) {
			return "question.result.green";
		}else if(this.behaviour>=4) {
			return "question.result.red";
		}else {
			return "question.result.yellow";
		}
	}
	public void sortCollection(ArrayList<UserMutualReportData> results, String criteria) {
		if(criteria.equals("firstname")){
			Collections.sort(results, new Sortbyfirstname());
		}else if(criteria.equals("lastname")){
			Collections.sort(results, new Sortbylastname());
		}else if(criteria.equals("module1")){
			Collections.sort(results, new SortbyModule1());
		}else if(criteria.equals("module2")){
			Collections.sort(results, new SortbyModule2());
		}else if(criteria.equals("module3")){
			Collections.sort(results, new SortbyModule3());
		}else if(criteria.equals("module4")){
			Collections.sort(results, new SortbyModule4());
		}else if(criteria.equals("ranking")){
			Collections.sort(results, new Sortbyranking());
		}else if(criteria.equals("behaviour")){
			Collections.sort(results, new Sortbybehaviour());
		}
	}
	public String getValue(int i, Text messages) {
		switch(i) {
			case 0:
				return firstName;
			case 1:
				return lastName;
			case 2:
				return email;
			case 3:
				return login;
			case 4:
				return String.valueOf(module1);
			case 5:
				return String.valueOf(module2);
			case 6:
				return String.valueOf(module3);
			case 7:
				return String.valueOf(module4);
			case 8:
				return  messages.getText(getPsiText());
			case 9:
				return String.valueOf(ranking);
			case 10:
				return messages.getText(getMod1Recommendation());
			case 11:
				return messages.getText(getMod2Recommendation());
			case 12:
				return messages.getText(getMod3Recommendation());
			case 13:
				return messages.getText(getMod4Recommendation());
		}
		return null;
	}
}

class Sortbyfirstname implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return a.getFirstName().toLowerCase().compareTo(b.getFirstName().toLowerCase());
	}
}

class Sortbylastname implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return a.getLastName().toLowerCase().compareTo(b.getLastName().toLowerCase());
	}
}

class Sortbyemail implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return a.getEmail().compareTo(b.getEmail());
	}
}

class SortbyModule1 implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return  b.getModule1() - a.getModule1();
	}
}

class SortbyModule2 implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return b.getModule2() - a.getModule2();
	}
}
class SortbyModule3 implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return b.getModule3() - a.getModule3();
	}
}
class SortbyModule4 implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return b.getModule4() - a.getModule4();
	}
}

class Sortbyranking implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return b.getRanking() - a.getRanking();
	}
}

class Sortbybehaviour implements Comparator<UserMutualReportData>
{

	public int compare(UserMutualReportData a, UserMutualReportData b)
	{
	return a.getBehaviour() - b.getBehaviour();
	}
}
