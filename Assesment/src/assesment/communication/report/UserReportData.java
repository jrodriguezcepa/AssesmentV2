package assesment.communication.report;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;

public class UserReportData {

	public static final int YELLOW = 1;
	public static final int GREEN = 2;
	public static final int RED = 3;

	private String login;
	private String firstName;
	private String lastName;
	private String email;
	private Date endDate;
	private int correct;
	private int incorrect;
	private int color;
	
	private double valuePsi1;
	private double valuePsi2;
	
	private boolean notstarted;
	private HashMap<Integer, String> wrtAnswers;
	
	private double secondUser = 0.0;
	
	public UserReportData() {
	}

	public UserReportData(String login, String firstName, String lastName, String email, Date endDate) {
		this(login, firstName, lastName, email, endDate, false);
	}
	
	public UserReportData(String login, String firstName, String lastName, String email, Date endDate, boolean notstarted) {
		this.login = login;
		this.firstName = (firstName == null) ? "" : firstName.replaceAll("'", "´");
		this.lastName = (lastName == null) ? "" : lastName.replaceAll("'", "´");
		this.email = email;
		this.endDate = endDate;
		correct = 0;
		incorrect = 0;
		this.notstarted = notstarted;
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
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getIncorrect() {
		return incorrect;
	}
	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}
	
	public void addResult(int type,int value) {
		if(type == AnswerData.CORRECT) {
			correct += value;
		}else {
			incorrect += value;
		}
	}

	public double getCorrectValue() {
		return new Integer(correct).doubleValue();
	}

	public String getLine(int index, AssesmentData assessment, QuestionData[] wrtQuestions, Text messages) {
		String link1 = (endDate == null) ? login : "<a href=\"reportu.jsp?u="+login+"&a="+assessment.getId()+"\">"+login+"</a>";
		String line = "data.setCell("+index+", 0, '"+login.toLowerCase()+"', '"+link1+"', {style: 'color:black;'});"; 
		line += "data.setCell("+index+", 1, '"+firstName.toLowerCase()+"', '"+firstName+"', {style: 'color:black;'});"; 
		line += "data.setCell("+index+", 2, '"+lastName.toLowerCase()+"', '"+lastName+"', {style: 'color:black;'});";
		int column = 3;
		if(assessment.isShowEmailWRT()) {
			String emailValue = (email == null) ? "--" : email;  
			line += "data.setCell("+index+", "+column+", '"+emailValue.toLowerCase()+"', '"+emailValue+"', {style: 'color:black;'});";
			column++;
		}
		for(int i = 0; i < wrtQuestions.length && wrtQuestions[i] != null; i++) {
			if(wrtAnswers != null && wrtAnswers.containsKey(wrtQuestions[i].getId())){
				line += "data.setCell("+index+", "+column+", '"+messages.getText(wrtAnswers.get(wrtQuestions[i].getId())).toLowerCase()+"', '"+messages.getText(wrtAnswers.get(wrtQuestions[i].getId()))+"', {style: 'color:black;'});";
			}else {
				line += "data.setCell("+index+", "+column+", '---', '---', {style: 'color:black;'});";
			}
			column++;
		}
		line += "data.setCell("+index+", "+column+", "+correct+", '"+correct+"', {style: 'color:black;'});";
		column++;
		line += "data.setCell("+index+", "+column+", "+incorrect+", '"+incorrect+"', {style: 'color:black;'});";
		column++;
		if(assessment.getId().intValue() == AssesmentData.UPL_NEWHIRE || assessment.getId().intValue() == AssesmentData.AGROBIOLOGICA_NEWHIRE) {
			line += getSecondStyle(index,column,messages);
			column++;
		}
		line += getStyle(index,column,messages);
		column++;
		String dateStr = formatDate(endDate);
		String[] dateArray = getDate(endDate);
		if(assessment.isPsitest()) {
			line += getStylePsi(index,column,messages);
			column++;
		}
		line += "data.setCell("+index+", "+column+", new Date("+dateArray[0]+", "+dateArray[1]+", "+dateArray[2]+"), '"+dateStr+"', {style: 'color:black;'});";
		column++;
		String linkR = (endDate == null) ? "---" : "<a href=javascript:generateReport(\""+login+"\",1)><img src=\"./imgs/pdf.jpg\" width=\"20px;\"></a>";
		line += "data.setCell("+index+", "+column+", 'X', '"+linkR+"', {style: 'color:black;'});";
		column++;
		if(assessment.isCertificate()) {
			double percent = (correct + incorrect == 0) ? 0.0 :  correct * 100.0 / (correct + incorrect);
			String linkC = (endDate == null || percent < assessment.getGreen()) ? "---" : "<a href=javascript:generateReport(\""+login+"\",2)><img src=\"./imgs/pdf.jpg\" width=\"20px;\"></a>";
			line += "data.setCell("+index+", "+column+", 'X', '"+linkC+"', {style: 'color:black;'});";
		}
		return line;
	}

	public String getStyle(int index, int column, Text messages) {
		String colorStr = "color:black; ";
		if(notstarted) {
			return "data.setCell("+index+", "+column+", '"+messages.getText("report.users.total.notstarted")+"', '"+messages.getText("report.users.total.notstarted")+"',{style: '"+colorStr+"'});";			
		}else {
			String level = messages.getText("generic.report.pending");
			switch(color) {
				case RED:
					level = messages.getText("generic.report.lowlevel");
					colorStr = "background-color:red; color:white;";
					break;
				case GREEN:
					level = messages.getText("generic.report.highlevel");
					colorStr = "background-color:green; color:white;";
					break;
				case YELLOW:
					level = messages.getText("generic.report.meddiumlevel");
					colorStr = "background-color:yellow; color:gray;";
					break;
			}
			return "data.setCell("+index+", "+column+", '"+level+"', '"+level+"',{style: '"+colorStr+"'});";
		}
	}


	public String getSecondStyle(int index, int column, Text messages) {
		String colorStr = "color:black; ";
		if(endDate == null) {
			return "data.setCell("+index+", "+column+", '"+messages.getText("report.users.total.notstarted")+"', '"+messages.getText("report.users.total.notstarted")+"',{style: '"+colorStr+"'});";			
		}else {
			String level = messages.getText("generic.report.pending");
			if(secondUser < 60) {
				level = messages.getText("generic.report.lowlevel");
				colorStr = "background-color:red; color:white;";
			} else if(secondUser < 80) {
				level = messages.getText("generic.report.meddiumlevel");
				colorStr = "background-color:yellow; color:gray;";
			}else {
				level = messages.getText("generic.report.highlevel");
				colorStr = "background-color:green; color:white;";
			}
			return "data.setCell("+index+", "+column+", '"+level+"', '"+level+"',{style: '"+colorStr+"'});";
		}
	}

	public String getLevel(Text messages) {
		if(notstarted) {
			return "---";			
		}else {
			switch(color) {
				case RED:
					return messages.getText("generic.report.lowlevel");
				case GREEN:
					return messages.getText("generic.report.highlevel");
				case YELLOW:
					return messages.getText("generic.report.meddiumlevel");
			}
			return messages.getText("generic.report.pending");
		}
	}

	public String getStylePsi(int index, int column, Text messages) {
		if(notstarted) {
			return "data.setCell("+index+", "+column+", '"+messages.getText("report.users.total.notstarted")+"', '"+messages.getText("report.users.total.notstarted")+"',{style: 'color:black;'});";			
		}else {
			if(endDate == null) {
				return "data.setCell("+index+", "+column+", '---', '---',{style: 'color:black;'});";
			}
			double value = (valuePsi1 + valuePsi2) / 6.0;
			if(value < 3) {
				return "data.setCell("+index+", "+column+", '"+messages.getText("question.result.green")+"', '"+messages.getText("question.result.green")+"',{style: 'background-color:green; color:white;'});";
	  		}else if(value < 4) {
				return "data.setCell("+index+", "+column+", '"+messages.getText("question.result.yellow")+"', '"+messages.getText("question.result.yellow")+"',{style: 'background-color:yellow; color:gray;'});";
	  		}else {
				return "data.setCell("+index+", "+column+", '"+messages.getText("question.result.red")+"', '"+messages.getText("question.result.red")+"',{style: 'background-color:red; color:white;'});";
	  		}
		}
	}

	public String getLevelPsi(Text messages) {
		if(notstarted) {
			return "---";			
		}else {
			if(endDate == null) {
				return "---";			
			}
			double value = (valuePsi1 + valuePsi2) / 6.0;
			if(value < 3) {
				return messages.getText("question.result.green");
	  		}else if(value < 4) {
				return messages.getText("question.result.yellow");
	  		}else {
				return messages.getText("question.result.red");
	  		}
		}
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setPSI(Object[] data) {
		valuePsi1 = 0;
		valuePsi2 = 0;
		if(data[8] != null) {
			for(int i = 8; i < 14; i++) {
				if(i < 11) {
					valuePsi1 += ((Integer)data[i]).doubleValue();
				}else {
					valuePsi2 += ((Integer)data[i]).doubleValue();
				}
			}
		}
	}

	public double getValuePsi1() {
		return valuePsi1;
	}

	public double getValuePsi2() {
		return valuePsi2;
	}

	public void setValuePsi1(double valuePsi1) {
		this.valuePsi1 = valuePsi1;
	}

	public void setValuePsi2(double valuePsi2) {
		this.valuePsi2 = valuePsi2;
	}

	public double getPsiPercent(int type) {
		switch(type) {
			case AssessmentReportData.ATITUDE:
				return Math.round(valuePsi1 * 20.0 / 3.0);
			case AssessmentReportData.STRESS:
				return Math.round(valuePsi2 * 20.0  / 3.0);
			default:
				return Math.round((valuePsi1 + valuePsi2)  * 20.0 / 6.0);
		}
	}

	public String getPsiLevel(int type) {
		double value = getPsiPercent(type);
		if(value < 60.0) {
			return "question.result.green";
  		}else if(value < 80.0) {
			return "question.result.yellow";
  		}else {
			return "question.result.red";
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
				return String.valueOf(correct);
			case 4:
				return String.valueOf(incorrect);
			case 5:
				switch(color) {
					case RED:
						return messages.getText("generic.report.lowlevel");
					case GREEN:
						return messages.getText("generic.report.highlevel");
					case YELLOW:
						return messages.getText("generic.report.meddiumlevel");
					default:
						return messages.getText("generic.report.pending");					
				}
			case 6:
				if(endDate == null) {
					return messages.getText("generic.report.pending");					
				}
				double value = (valuePsi1 + valuePsi2) / 6.0;
				if(value < 3) {
					return messages.getText("generic.report.highlevel");
		  		}else if(value < 4) {
					return messages.getText("generic.report.meddiumlevel");
		  		}else {
					return messages.getText("generic.report.lowlevel");
		  		}
		}
		return null;
	}

    public String formatDate(Date date) {
        if(date == null) {
            return "---";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
    }

    public String[] getDate(Date date) {
    	String[] s = {"1900","0","1"};
        if(date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            s[2] = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            s[1] = String.valueOf(calendar.get(Calendar.MONTH));
            s[0] = String.valueOf(calendar.get(Calendar.YEAR));
        }
        return s;
    }

    public String getEndDateStr() {
		return formatDate(endDate);
	}

	public void setWRTAnswers(HashMap<Integer, String> wrtAnswers) {
		this.wrtAnswers = wrtAnswers;
	}

	public HashMap<Integer, String> getWrtAnswers() {
		return wrtAnswers;
	}

	public void setWrtAnswers(HashMap<Integer, String> wrtAnswers) {
		this.wrtAnswers = wrtAnswers;
	}

	public void addSecondUser(Integer correct, Integer incorrect) {
		secondUser = correct * 100.0 / (correct + incorrect);
	}

}
