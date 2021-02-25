package assesment.communication.report;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;

public class AssessmentReportData {
	
	public static final int ATITUDE = 0;
	public static final int STRESS = 1;
	public static final int TOTAL = 2;
	
	
	private AssesmentData assessment;
	private Integer answerTestCount;
	private int red; 
	private int green; 
	private int yellow;
	private int finished;
	
	private int[][] psiValues;
	
	private Collection<ModuleReportData> modules;
	private Collection<UserReportData> users;
	
	private Collection<UserReportData> notstarted;

	public AssessmentReportData() {
		modules = new LinkedList<ModuleReportData>();
		users = new LinkedList<UserReportData>();
		notstarted = new LinkedList<UserReportData>();
		red = 0;
		green = 0;
		yellow = 0;
		finished = 0;
		psiValues = new int[3][3];
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				psiValues[i][j] = 0; 
	}

	public AssesmentData getAssessment() {
		return assessment;
	}
	
	public void setAssessment(AssesmentData assessment) {
		this.assessment = assessment;
	}
	
	public int getAnswerTestCount() {
		return answerTestCount;
	}

	public void setAnswerTestCount(int answerTestCount) {
		this.answerTestCount = answerTestCount;
	}

	public int getRed() {
		return red;
	}
	
	public void setRed(int red) {
		this.red = red;
	}
	
	public int getGreen() {
		return green;
	}
	
	public void setGreen(int green) {
		this.green = green;
	}
	
	public int getYellow() {
		return yellow;
	}
	
	public void setYellow(int yellow) {
		this.yellow = yellow;
	}
	
	public Collection<UserReportData> getUsers() {
		return users;
	}

	public Collection<UserReportData> getTotalUsers() {
		Collection<UserReportData> totalUsers = new LinkedList<UserReportData>();
		totalUsers.addAll(users);
		totalUsers.addAll(notstarted);
		//Collections.sort((List)totalUsers);
		return totalUsers;
	}

	public void setUsers(Collection<UserReportData> users) {
		this.users = users;
	}
	
	public void addUser(UserReportData user) {
		if(user.getEndDate() != null) {
			finished++;
	        double value = user.getCorrectValue() / answerTestCount.doubleValue()*100;
	        if(value < assessment.getYellow().doubleValue()) {
	        	user.setColor(UserReportData.RED);
	            red++;
	        }else if(value < assessment.getGreen().doubleValue()) {
	        	user.setColor(UserReportData.YELLOW);
	            yellow++;
	        }else {
	        	user.setColor(UserReportData.GREEN);
	            green++;
	        }
	        if(assessment.isPsitest()) {
	        	addValue(ATITUDE,user.getValuePsi1()/3.0);
	        	addValue(STRESS,user.getValuePsi2()/3.0);
	        	addValue(TOTAL,(user.getValuePsi1()+user.getValuePsi2())/6.0);
	        }
		}
		users.add(user);
	}

	public void addNotStartedUser(UserReportData user) {
		notstarted.add(user);
	}
	
	public Collection<ModuleReportData> getModules() {
		return modules;
	}

	public void setModules(Collection<ModuleReportData> modules) {
		Iterator<ModuleReportData> it = modules.iterator();
		while(it.hasNext()) {
			ModuleReportData moduleReportData = it.next();
			moduleReportData.loadColors(assessment.getGreen().doubleValue(),assessment.getYellow().doubleValue());
			this.modules.add(moduleReportData);
		}
	}

	public ModuleReportData getModuleReportData(Integer module) {
		Iterator<ModuleReportData> it = modules.iterator();
		while(it.hasNext()) {
			ModuleReportData moduleReportData = it.next();
			if(moduleReportData.getId().equals(module)) {
				return moduleReportData;
			}
		}
		return null;
	}
	
	public void addValue(int index, double value) {
  		if(value < 3) {
  			psiValues[index][0] = psiValues[index][0]+1;
  		}else if(value < 4) {
  			psiValues[index][1] = psiValues[index][1]+1;
  		}else {
  			psiValues[index][2] = psiValues[index][2]+1;
  		}
	}
	
	public int getPsiValue(int index, int color) {
		return psiValues[index][color];
	}

	public void addModule(ModuleReportData module) {
		modules.add(module);
		green += module.getCorrectValue();
		red += module.getIncorrectValue();
	}

	public int[][] getAdvance() {
		int[][] advance = new int[2][3];
		advance[0][2] = notstarted.size();
		Iterator<UserReportData> it = users.iterator();
		while(it.hasNext()) {
			if(it.next().getEndDate() == null) {
				advance[0][0]++;
			}else {
				advance[0][1]++;
			}
		}
		advance[1][0] = Integer.parseInt(String.valueOf(Math.round(new Integer(advance[0][0]).doubleValue() * 100.0 / new Integer(users.size()+notstarted.size()).doubleValue())));
		advance[1][1] = Integer.parseInt(String.valueOf(Math.round(new Integer(advance[0][1]).doubleValue() * 100.0 / new Integer(users.size()+notstarted.size()).doubleValue())));
		advance[1][2] = 100 - (advance[1][0] + advance[1][1]);
		return advance;
	}
	
	public String[] getResultPercents() {
		String[] values = new String[3];
		values[0] = String.valueOf(Math.round(new Integer(red).doubleValue() * 100.0 / new Integer(finished).doubleValue()));
		values[1] = String.valueOf(Math.round(new Integer(yellow).doubleValue() * 100.0 / new Integer(finished).doubleValue()));
		values[2] = String.valueOf(100 - (Integer.parseInt(values[0]) + Integer.parseInt(values[1])));
		return values;
	}

	public QuestionData[] getWRTQuestions() {
		QuestionData[] values = new QuestionData[assessment.getQuestionCount()];
		int index = 0;
        Iterator it1 = assessment.getModuleIterator();
        while(it1.hasNext()) {
        	Iterator it2 = ((ModuleData)it1.next()).getQuestionIterator();
            while(it2.hasNext()) {
            	QuestionData question = (QuestionData) it2.next();
            	if(question.isWrt()) {
            		values[index] = question;
            		index++;
            	}
            }
        }
        return values;
	}

}
