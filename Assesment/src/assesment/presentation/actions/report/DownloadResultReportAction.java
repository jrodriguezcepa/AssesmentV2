package assesment.presentation.actions.report;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

public class DownloadResultReportAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	    	
	        DynaActionForm actionForm = (DynaActionForm)form;
	        FileInputStream fis = null;
	        File f = null;
        	String loginname = actionForm.getString("login");
        	String assessment = actionForm.getString("assessment");
        	
        	if(assessment != null) {
	        	if(Integer.parseInt(assessment) == AssesmentData.ANTP_MEXICO_RSMM) {
	        		
	                Class.forName("org.postgresql.Driver");
	                Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
	                Statement st = conn1.createStatement();
	                
	        		ResultSet setUsers = st.executeQuery("SELECT u.loginname, u.firstname, u.lastname, u.email, ua.enddate, min(uas.answer), COUNT(*) " +
	                		"FROM users u " +
	                		"JOIN userassesments ua ON u.loginname = ua.loginname " +
	                		"LEFT JOIN useranswers uas ON u.loginname = uas.loginname " +
	                		"WHERE ua.assesment = "+AssesmentData.ANTP_MEXICO_RSMM+" AND u.loginname = '" + loginname + "' " +
	                		"GROUP BY u.loginname, u.firstname, u.lastname, u.email, ua.enddate"); // cantidad total de usuarios
	
	                HashMap<String, ANTPUser> users = new HashMap<String, ANTPUser>();
	                ANTPUser rsmm = null;
	            	if(setUsers.next()) {
	            		rsmm = new ANTPUser(setUsers);
	            	}
	
	            	if(rsmm != null) {
	            		ResultSet setAnswers = st.executeQuery("SELECT ua.loginname, m.moduleorder, q.questionorder, a.answerorder FROM useranswers ua " +
	            			"JOIN answerdata ad ON ad.id = ua.answer " +
	            			"JOIN answers a ON a.id = ad.answer " +
	            			"JOIN questions q ON q.id = a.question " +
	            			"JOIN modules m ON m.id = q.module " +
	            			"WHERE ua.assesment = "+AssesmentData.ANTP_MEXICO_RSMM+" AND ua.loginname = '" + loginname + "'");
		            	while(setAnswers.next()) {
		            		rsmm.addResult(setAnswers.getInt(2), setAnswers.getInt(3), setAnswers.getInt(4));
		            	}
	
		            	Object[] data = rsmm.getLine();
		           	    final ServletOutputStream out = response.getOutputStream();
		           	    response.setHeader("Content-Type", "application/pdf");
		                response.setHeader("Content-Disposition","inline; filename=\"rsmm.pdf\"");           
		    	   	    RsmmReportBTW report = new RsmmReportBTW(sys.getText(), sys.getUserSessionData().getLenguage(), (String)data[2],new Integer((String)data[31]).intValue(),new Integer((String)data[13]).intValue(),new Integer((String)data[19]).intValue(),new Integer((String)data[24]).intValue(),new Integer((String)data[27]).intValue(),new Integer((String)data[30]).intValue());
		                //RsmmReportBTW report = new RsmmReportBTW((String)data[2],1,1,1,1,1,1);
		    	   	    report.executeReport(out, 1);
			            out.flush();
			            out.close();
	            	}
	        	} else if(Integer.parseInt(assessment) == AssesmentData.SAFETY_SURVEY) {
	        		
	        		Collection<Integer> list1 = new LinkedList<Integer>();
	        		list1.add(25067);
	        		list1.add(25068);
	        		list1.add(25072);
	        		list1.add(25074);
	        		list1.add(25075);
	        		list1.add(25076);
	        		list1.add(25079);
	        		list1.add(25080);
	        		list1.add(25082);
	        		list1.add(25085);
	        		list1.add(25094);
	        		list1.add(25096);
	        		list1.add(25101);
	        		list1.add(25102);
	        		list1.add(25103);
	        		list1.add(25104);
	        		list1.add(25105);
	        		list1.add(25106);
	        		list1.add(25107);
	        		list1.add(25108);
	        		list1.add(25109);
	        		list1.add(25110);
	        		list1.add(25113);
	        		list1.add(25116);
	        		list1.add(25124);
	        		list1.add(25125);
	        		list1.add(25126);
	        		list1.add(25127);
	        		list1.add(25129);
	        		list1.add(25131);
	
	        		Collection<Integer> list2 = new LinkedList<Integer>();
	        		list2.add(25070);
	        		list2.add(25071);
	        		list2.add(25077);
	        		list2.add(25081);
	        		list2.add(25083);
	        		list2.add(25087);
	        		list2.add(25097);
	        		list2.add(25117);
	        		list2.add(25118);
	
	        		Class.forName("org.postgresql.Driver");
	                Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
	                Statement st = conn1.createStatement();
	                
	        		ResultSet setUsers = st.executeQuery("select enddate from userassesments where loginname = '" + loginname + "'"); // cantidad total de usuarios
	        		setUsers.next();
	        		Date endDate = setUsers.getDate(1);
	        		
	        		setUsers = st.executeQuery("select text from useranswers ua join answerdata ad on ad.id = ua.answer " +
	        				"where loginname = '" + loginname + "' " +
	        				"and question in (25056, 25154) " +
	        				"order by question");
	        		setUsers.next();
	        		String company = setUsers.getString(1);
	        		setUsers.next();
	        		String responsible = setUsers.getString(1);
	        		
	        		HashMap<Integer, double[]> results = new HashMap<Integer, double[]>();
	        		
	        		ResultSet setAnswers1 = st.executeQuery("select q.module, a.answerorder, count(*) from useranswers ua " +
	        		"join answerdata ad on ad.id = ua.answer " +
	        		"join questions q on ad.question = q.id " +
	        		"join answers a on ad.answer = a.id " +
	        		"where loginname = '" + loginname + "' " +
	        		"and module != 1902 " +
	        		"and answerorder in (1,3,4) " +
	        		"group by q.module, a.answerorder");
	        		while(setAnswers1.next()) {
	        			Integer module = setAnswers1.getInt(1);
	        			if(!results.containsKey(module)) {
	        				results.put(module, new double[]{0.0,0.0,0.0,0.0});
	        			}
	        			int answer = setAnswers1.getInt(2);
	        			int count = setAnswers1.getInt(3);
	        			if(answer != 4)
	        				results.get(module)[answer-1] = count;
	        			if(answer == 1 || answer == 4)
	        				results.get(module)[3] += count;
	        		}
	        		
	        		ResultSet setAnswers2 = st.executeQuery("select q.module, q.id from useranswers ua " +
	        				"join answerdata ad on ad.id = ua.answer " +
	        				"join questions q on ad.question = q.id " +
	        				"join answers a on ad.answer = a.id " +
	        				"where loginname = '" + loginname + "' " +
	        				"and module != 1902 " +
	        				"and answerorder in (2)");
	        		while(setAnswers2.next()) {
	        			Integer module = setAnswers2.getInt(1);
	        			if(!results.containsKey(module)) {
	        				results.put(module, new double[]{0.0,0.0,0.0,0.0});
	        			}
	    				results.get(module)[1] += 1;
	
	    				Integer question = setAnswers2.getInt(2);
	    				if(list1.contains(question)) {
	        				results.get(module)[3] += 0.5;
	    				}
	    				if(list2.contains(question)) {
	        				results.get(module)[3] += 0.75;
	    				}
	        		}
	
	           	    final ServletOutputStream out = response.getOutputStream();
	           	    response.setHeader("Content-Type", "application/pdf");
	                response.setHeader("Content-Disposition","inline; filename=\"survey.pdf\"");           
	    	   	    SurveyReport report = new SurveyReport(sys.getText(), sys.getUserSessionData().getLenguage(), company, responsible, endDate, results);
	    	   	    report.executeReport(out, 1);
		            out.flush();
		            out.close();
	        	} else if(Integer.parseInt(assessment) == AssesmentData.UPL_NEWHIRE) {
	        		
	        		Class.forName("org.postgresql.Driver");
	                Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
	                Statement st = conn1.createStatement();

	                final ServletOutputStream out = response.getOutputStream();
	           	    response.setHeader("Content-Type", "application/pdf");
	                response.setHeader("Content-Disposition","inline; filename=\"newhire.pdf\"");           

	                String[] userData = getUserData(st, new Integer(assessment), loginname, sys.getText(), sys.getUserSessionData().getLenguage());

	    	        Object[][] map = findUsersResults(st, new Integer(assessment), loginname);
	    	        String[][] errors = getWrongAnswers(st, new Integer(assessment), loginname);

	                int[] values = getValue(sys.getQuestionReportFacade().getOptionAnswers(new Integer(assessment), loginname, sys.getUserSessionData()));
    		   	    NewHireReport report = new NewHireReport(sys.getText(), userData, values, map, errors);
    		   	    report.executeReport(out, 1);

	        	} else {
			        int reportType = Integer.parseInt(actionForm.getString("reportType"));
			        String sendId = new GenerateReport(sys, loginname, Integer.parseInt(assessment), reportType).getSendId();
			        
		        	String fileName = (reportType == 1) ? AssesmentData.FLASH_PATH+"/reports/DA_Report_"+sendId+".pdf" : AssesmentData.FLASH_PATH+"/reports/DA_Certificate_"+sendId+".pdf"; 
		        	if(sendId != null) {
			        	f = new File(fileName);
			        	if(f.exists()) {
			        		fis = new FileInputStream(f);
		
					        response.setHeader("Content-Type", "application/pdf");
				        	response.setHeader("Content-Length", String.valueOf(f.length()));
				        	response.setHeader("Content-Disposition", "inline; filename=\"" + f.getName() + "\"");
				
				        	BufferedInputStream input = null;
				
				        	try {
				        	    input = new BufferedInputStream(fis);
				        	    OutputStream output = response.getOutputStream();
				        	    byte[] buffer = new byte[8192];
				        	    for (int length = 0; (length = input.read(buffer)) > 0;) {
				        	        output.write(buffer, 0, length);
				        	    }
				        	} finally {
				        	    //if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
				        	    if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
				        	}
			        	}
		        	}
	        	}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    
    private String[] getUserData(Statement st, Integer assessment, String user, Text messages, String language) throws Exception {
    	String[] data = {"","","",null};
    	String sql = "select enddate, firstname, lastname, a.name, c.name, a.id " + 
    			"from userassesments ua " + 
    			"join users u on u.loginname = ua.loginname " + 
    			"join assesments a on a.id = ua.assesment " + 
    			"left join corporations c on c.id = a.corporation" + 
    			" where u.loginname = '"+user+"' " + 
    			"and a.id = "+assessment;
        ResultSet set = st.executeQuery(sql);
        if(set.next()) {
        	Calendar c = Calendar.getInstance();
        	c.setTime(set.getDate(1));
        	data[0] = Util.dateToString(messages, c, language);
    		data[2] = set.getString(2)+" "+set.getString(3);
    		data[3] = messages.getText(set.getString(4));
    		data[1] = set.getString(5);
        }
        return data;
    }
    
	private Object[][] findUsersResults(Statement st, Integer assessment, String user) throws Exception {
		Collection<Object[]> hash = new LinkedList<Object[]>();
        String queryStr = "SELECT module,m.key,a.type,count(*) AS count,m.moduleorder " +
                "FROM useranswers AS ua " +
                "JOIN answerdata AS ad ON ua.answer = ad.id " +
                "JOIN questions AS q ON ad.question = q.id " +
                "JOIN answers AS a ON ad.answer = a.id " +
                "JOIN modules AS m ON q.module = m.id " +
                "WHERE ua.loginname = '"+user+"' " +
                "AND ua.assesment = "+String.valueOf(assessment)+" "+
                "AND q.testtype = 1 " +
                "GROUP BY module,m.key,a.type,m.moduleorder " +
                "ORDER BY m.moduleorder,m.key,a.type ";
        ResultSet set = st.executeQuery(queryStr);
        int lastId = 0;
        Object[] dataResult = new Object[]{"",0,0};
        while(set.next()) {
            if(lastId != set.getInt(1)) {
            	if(lastId > 0) {
            		hash.add(dataResult);
            	}
            	lastId = set.getInt(1);
            	dataResult =  new Object[]{set.getString(2),0,0};
            }
            if(set.getInt(3) == 1) {
                dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + set.getInt(4));
            }else {
                dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + set.getInt(4));
            }
        }
    	if(lastId > 0) {
    		hash.add(dataResult);
    	}
		return (Object[][])hash.toArray(new Object[0][0]);
	}

	private String[][] getWrongAnswers(Statement st, Integer assessment,String user) throws Exception {
        String sql = "SELECT m.key AS module,q.key AS question," +
                "a.key AS answered,a2.key AS correct " +
                "FROM useranswers ua " +
                "JOIN answerdata ad ON ua.answer = ad.id " +
                "JOIN answers a ON ad.answer = a.id " +
                "JOIN questions q ON a.question = q.id " +
                "JOIN answers a2 ON a2.question = q.id " +
                "JOIN modules m ON q.module = m.id " +
                "WHERE ua.loginname = '"+user+"' "+
                "AND ua.assesment = "+String.valueOf(assessment)+
                " AND a.type = 1 "+
                " AND a2.type = 0 "+
                " AND q.testtype = 1 "+
                " ORDER BY m.moduleorder,q.questionorder";
        ResultSet set = st.executeQuery(sql);
        Collection questionResults = new LinkedList();
        while(set.next()) {
            String[] data = new String[4];
            for(int j = 0; j < 4; j++) {
                data[j] = set.getString(j+1);
            }
            questionResults.add(data);
        }
        return (String[][])questionResults.toArray(new String[0][0]);
	}
	

    private int[] getValue(Integer[][] answers) {
    	int x = 0;
    	int y = 0;
    	for(int i = 0; i < answers.length; i++) {
    		int value = QuestionData.getUPLValue(answers[i][0], answers[i][1]);
    		if(value == -1) {
            	y += answers[i][2].intValue();
    		}else {
    			x += value;
    		}
    	}
		return new int[]{x,y};
	}

}