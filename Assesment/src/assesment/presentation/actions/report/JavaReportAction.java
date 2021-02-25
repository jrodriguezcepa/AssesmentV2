package assesment.presentation.actions.report;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.LineaJJ;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UsuarioCharlaUPM;
import assesment.persistence.administration.tables.AssessmentUserData;
import assesment.presentation.translator.web.util.AbstractAction;

public class JavaReportAction  extends AbstractAction {

    public static final int CORRECT = 0;
    public static final int INCORRECT = 1;
    

    public static final int TEXT = 1;
    public static final int DATE = 2;
    public static final int EXCLUDED_OPTIONS = 3;
    public static final int LIST = 4;
    public static final int KILOMETERS = 6;
    public static final int OPTIONAL_DATE = 7;
    public static final int INCLUDED_OPTIONS = 8;
    public static final int EMAIL = 9;
    public static final int COUNTRY = 10;
    public static final int TEXTAREA = 11;
    public static final int BIRTHDATE = 12;

	public static final String FDM_SERVER = "18.229.182.37";

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
		try {
	        HttpSession session = request.getSession();
	        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
	        DynaActionForm javaForm = (DynaActionForm)form;
			switch(Integer.parseInt(javaForm.getString("type"))) {
				case 1:
					openPepsicoReport(response);
					break;
				case 2:
					openJJReport(response,new Integer(javaForm.getString("assessment1")));
					break;
				case 3:
					openQuestionReport(response,sys,new Integer(javaForm.getString("assessment2")));
					break;
				case 4:
					Calendar c1 = Calendar.getInstance();
					c1.set(Calendar.DATE, Integer.parseInt(javaForm.getString("fromDay")));
					c1.set(Calendar.MONTH, Integer.parseInt(javaForm.getString("fromMonth"))-1);
					c1.set(Calendar.YEAR, Integer.parseInt(javaForm.getString("fromYear")));
					Calendar c2 = Calendar.getInstance();
					c2.set(Calendar.DATE, Integer.parseInt(javaForm.getString("toDay")));
					c2.set(Calendar.MONTH, Integer.parseInt(javaForm.getString("toMonth"))-1);
					c2.set(Calendar.YEAR, Integer.parseInt(javaForm.getString("toYear")));
					if(Integer.parseInt(javaForm.getString("typeBasf")) == 1) {
						openBasfReport(response,c1,c2,sys);
					}else {
						openExtendedBasfReport(response,c1,c2,sys);
					}
					break;
				case 5:
					openMDPReport(response);
					break;
				case 6:
					openANTPReport(response);
					break;
				case 7:
					openANTPPNSVReport(response);
					break;
				case 8:
					openTotalAnswers(new Integer(javaForm.getString("assessment3")),new Integer(javaForm.getString("typeResult")),sys,response);
					break;
				case 9:
					openANTPPNSVReport2(response);
					break;
				case 10:
					openCharlaUPMReport(response, sys);
					break;
				case 11:
					openTimacReport(response, sys);
					break;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}
    
    private void openPepsicoReport(HttpServletResponse response) throws Exception {
    	
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=pepsico.xls");


        Class.forName("org.postgresql.Driver");
        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("Pepsico", 0);
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        Statement st2 = conn1.createStatement();
        
        Integer assesmentId = AssesmentData.PEPSICO_CEPA_SYSTEM;
        
        int column = 0;
        s.addCell(new Label(column,0,  "Nombre")); 
        column++;
        s.addCell(new Label(column,0,  "Apellido")); 
        column++;
        s.addCell(new Label(column,0,  "Sucursal")); 
        column++;
        s.addCell(new Label(column,0,  "Status")); 
        column++;
        s.addCell(new Label(column,0,  "Ultimo Acceso")); 
        column++;
        
        ResultSet setHeader = st.executeQuery("SELECT gm.text " +
        		"FROM modules m " +
        		"JOIN questions q ON q.module = m.id " +
        		"JOIN generalmessages gm ON gm.labelkey = q.key " +
        		"WHERE assesment = "+assesmentId+
        		" AND gm.language = 'es'" +
        		"ORDER BY moduleorder,questionorder");
        while(setHeader.next()) {
            s.addCell(new Label(column,0,  setHeader.getString("text"))); 
            column++;
        }

        int[][] modules = new int[4][2];
        ResultSet setModules = st.executeQuery("select m.id,count(*) " +
        		"from modules m " +
        		"join questions q on q.module = m.id " +
        		"WHERE assesment = "+assesmentId+
        		" GROUP BY m.id,m.moduleorder " +
        		"ORDER BY moduleorder");
        int index = 0;
        int total = 0;
        while(setModules.next()) {
        	modules[index][0] = setModules.getInt(1);
        	modules[index][1] = setModules.getInt(2);
        	total += modules[index][1];
        	index++;
        }
        
        int[][] personal = new int[modules[0][1]][2];
        ResultSet set = st.executeQuery("SELECT q.id,q.type FROM questions q, generalmessages gm WHERE q.key = gm.labelkey AND " +
        		"module = "+modules[0][0]+" AND gm.language = 'es' ORDER BY questionorder");
        index = 0;
    	while(set.next()) {
        	personal[index][0] = set.getInt("id");
        	personal[index][1] = set.getInt("type");
        	index++;
        }

        Hashtable cc = new Hashtable();
        cc.put("31","datatype.country.uy"); 
        cc.put("32","datatype.country.br");
        cc.put("33","datatype.country.ar"); 
        cc.put("34","datatype.country.py"); 
        cc.put("37","datatype.country.ec"); 
        cc.put("38","datatype.country.bo"); 
        cc.put("39","datatype.country.ve"); 
        cc.put("40","datatype.country.us"); 
        cc.put("41","datatype.country.ca"); 
        cc.put("42","datatype.country.mx"); 
        cc.put("43","datatype.country.fr"); 
        cc.put("44","datatype.country.it"); 
        cc.put("45","datatype.country.de"); 
        cc.put("46","datatype.country.ru"); 
        cc.put("48","datatype.country.ag"); 
        cc.put("49","datatype.country.eg"); 
        cc.put("51","datatype.country.in"); 
        cc.put("52","datatype.country.jp"); 
        cc.put("53","datatype.country.be"); 
        cc.put("54","datatype.country.cl"); 
        cc.put("55","datatype.country.co"); 
        cc.put("56","datatype.country.cr"); 
        cc.put("57","datatype.country.do"); 
        cc.put("58","datatype.country.gr"); 
        cc.put("59","datatype.country.gt"); 
        cc.put("60","datatype.country.ha"); 
        cc.put("61","datatype.country.hn"); 
        cc.put("62","datatype.country.ni"); 
        cc.put("63","datatype.country.pa"); 
        cc.put("64","datatype.country.pe"); 
        cc.put("65","datatype.country.ph"); 
        cc.put("66","datatype.country.pr"); 
        cc.put("67","datatype.country.sv"); 
        cc.put("68","datatype.country.th"); 
        cc.put("69","datatype.country.tt"); 
        cc.put("70","datatype.country.bb"); 
        cc.put("71","datatype.country.cu"); 
        cc.put("72","datatype.country.gp"); 
        cc.put("73","datatype.country.ma"); 
        cc.put("74","datatype.country.at"); 
        cc.put("75","datatype.country.au"); 
        cc.put("76","datatype.country.bs"); 
        cc.put("77","datatype.country.cz"); 
        cc.put("78","datatype.country.dk"); 
        cc.put("79","datatype.country.ee"); 
        cc.put("80","datatype.country.fi"); 
        cc.put("81","datatype.country.hu"); 
        cc.put("82","datatype.country.id"); 
        cc.put("83","datatype.country.ie"); 
        cc.put("84","datatype.country.il"); 
        cc.put("85","datatype.country.ja"); 
        cc.put("86","datatype.country.lv"); 
        cc.put("87","datatype.country.lt"); 
        cc.put("88","datatype.country.my"); 
        cc.put("89","datatype.country.nl"); 
        cc.put("90","datatype.country.nz"); 
        cc.put("91","datatype.country.no"); 
        cc.put("93","datatype.country.pk"); 
        cc.put("94","datatype.country.pl"); 
        cc.put("95","datatype.country.pt"); 
        cc.put("96","datatype.country.sa"); 
        cc.put("97","datatype.country.hk"); 
        cc.put("98","datatype.country.sg"); 
        cc.put("99","datatype.country.sk"); 
        cc.put("100","datatype.country.za"); 
        cc.put("101","datatype.country.kr"); 
        cc.put("102","datatype.country.se"); 
        cc.put("103","datatype.country.ch"); 
        cc.put("104","datatype.country.tr"); 
        cc.put("106","datatype.country.ae"); 
        cc.put("107","datatype.country.vn"); 
        cc.put("153","datatype.country.cn"); 
        cc.put("154","datatype.country.bd"); 
        cc.put("156","datatype.country.lk"); 
        cc.put("157","datatype.country.tw"); 
        cc.put("159","datatype.country.bg"); 
        cc.put("160","datatype.country.es"); 
        cc.put("161","datatype.country.gb"); 
        cc.put("163","datatype.country.hr"); 
        cc.put("164","datatype.country.jo"); 
        cc.put("165","datatype.country.ke"); 
        cc.put("167","datatype.country.ro"); 
        cc.put("168","datatype.country.ua"); 
        cc.put("170","datatype.country.bz"); 
        cc.put("171","datatype.country.bm");
        cc.put("172","datatype.country.ba"); 
        cc.put("173","datatype.country.al"); 
        cc.put("174","datatype.country.dm"); 
        cc.put("175","datatype.country.lb"); 
        cc.put("176","datatype.country.lu"); 
        cc.put("177","datatype.country.si"); 
        cc.put("179","datatype.country.gf"); 

        ResultSet setUsers = st.executeQuery("SELECT DISTINCT u.loginname,u.firstname,u.lastname,u.extradata,ua.enddate FROM users u" +
        		" JOIN userassesments ua ON u.loginname = ua.loginname " +
        		" WHERE ua.assesment = " + assesmentId+" ORDER BY u.firstname,u.lastname"); // cantidad total de usuarios

        int row = 0;
    	while(setUsers.next()) {
    		row++;
    		column = 0;
        	String loginname = setUsers.getString("loginname"); 
        	Date enddate = setUsers.getDate("enddate");

        	s.addCell(new Label(column,row, setUsers.getString("firstname")));
        	column++;
        	s.addCell(new Label(column,row, setUsers.getString("lastname")));
        	column++;
        	s.addCell(new Label(column,row, setUsers.getString("extradata")));
        	column++;
        
        	ResultSet setStatus = st2.executeQuery("select count(*) from useranswers where assesment = "+assesmentId+" and loginname = '"+loginname+"'");
        	setStatus.next();
        	int answers = setStatus.getInt(1);
        	
        	if(answers == 0) {
        		s.addCell(new Label(column,row, "No comenzó"));
        		column++;
        		s.addCell(new Label(column,row, "----"));
            	column++;
        		for(int i = 0; i < total; i++) {
        			s.addCell(new Label(column,row, "----"));
	            	column++;
        		}
        	}else {
        		Calendar c = Calendar.getInstance();
        		if(enddate == null) {
        			ResultSet setAccess = st2.executeQuery("select max(logindate) from userslogins where loginname = '"+loginname+"'");
        			setAccess.next();
        			c.setTimeInMillis(setAccess.getLong(1));
        		}else {
        			c.setTime(enddate);            			
        		}
        		if(answers < total) {
        			s.addCell(new Label(column,row, "Incompleto"));
        		}else {
        			s.addCell(new Label(column,row, "Completo"));
        		}
            	column++;
        		s.addCell(new Label(column,row, c.get(Calendar.DATE)+"/"+String.valueOf(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)));
            	column++;
        		for(int i = 0; i < personal.length; i++) {
        			switch(personal[i][1]) {
        			
        				case TEXT: case EMAIL:// Texto Simple            				
            				set = st2.executeQuery("SELECT ad.text FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = " + assesmentId + " " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+personal[i][0]);
            				if(set.next()) {
            					s.addCell(new Label(column,row, new String(set.getString("text"))));
        		            	column++;
            				}
            				break;
        			
        				case KILOMETERS:
            				set = st2.executeQuery("SELECT ad.text,ad.distance FROM useranswers ua " +
                			"JOIN answerdata ad ON ua.answer = ad.id " +
                			"WHERE ua.assesment = " + assesmentId + " " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+personal[i][0]);
            				if(set.next()) {
            					String line = set.getString("text");
            					if(set.getInt("distance") == 0) {
            						line += " kms";
            					}else {
            						line += " millas";
            					}
            					s.addCell(new Label(column,row, line));
        		            	column++;
            				}
            				break;
        				
        				case DATE: case BIRTHDATE: case OPTIONAL_DATE:
        				// Fechas
        					set = st2.executeQuery("SELECT ad.date FROM useranswers ua " +
        						"JOIN answerdata ad ON ua.answer = ad.id " +
        						"WHERE ua.assesment = " + assesmentId + " "+
        						"AND ua.loginname = '"+loginname+"' " +
        						"AND ad.question = "+personal[i][0]);
        				
        					if(set.next()) {
        						String fecha = (set.getString("date") == null)?"":set.getString("date");
        						s.addCell(new Label(column,row, fecha));
        		            	column++;
        					}
        					break;
        				
        				case EXCLUDED_OPTIONS: case LIST:
            				// Texto con traducciones
            				set = st2.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN answers a ON a.id = ad.answer " +
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = " + assesmentId + " "+
    						"AND gm.language = 'es' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+personal[i][0]);
            				if(set.next()) {
            					s.addCell(new Label(column,row, set.getString("text")));
        		            	column++;
            				}
            				break;
        			
            			case INCLUDED_OPTIONS:
            				// Texto con traducciones y multipleopcion
            				set = st2.executeQuery("SELECT gm.text FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"JOIN multioptions m ON ad.id = m.id "+
    						"JOIN answers a ON a.id = m.answer "+
    						"JOIN generalmessages gm ON gm.labelkey = a.key " +
    						"WHERE ua.assesment = " + assesmentId + " "+
    						"AND gm.language = 'es' " +
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+personal[i][0]);
            				String ss = "";
            				while(set.next()) {
            					ss += set.getString("text")+"-";
            				}
            				if(ss.length() > 2) {
            					ss = ss.substring(0, ss.length()-2);
            				}
            				s.addCell(new Label(column,row, ss));
    		            	column++;
            				break;
            			case COUNTRY:
            				set = st2.executeQuery("SELECT ad.country FROM useranswers ua " +
    						"JOIN answerdata ad ON ua.answer = ad.id " +
    						"WHERE ua.assesment = " + assesmentId + " "+
                			"AND ua.loginname = '"+loginname+"' " +
                			"AND ad.question = "+personal[i][0]);
            				if(set.next()) {
            					ResultSet set2 = st2.executeQuery("SELECT text FROM generalmessages WHERE language = 'es' AND labelkey = '" + cc.get(set.getString("country")) +"'");
                    			if(set2.next()) {
                    				s.addCell(new Label(column,row, set2.getString("text")));
            		            	column++;
                    			}
            				}
            				break;
            			default:
            				System.out.println("ERROR "+personal[i][0]);
            		}
            	}
        

        		for (int i = 1; i < modules.length; i++) {		

        			ResultSet set2 = st2.executeQuery("SELECT q.id,a.type FROM useranswers ua " +
	        			"JOIN answerdata ad ON ua.answer = ad.id " +
	        			"JOIN questions q ON q.id = ad.question " +
	        			"JOIN answers a ON a.id = ad.answer " +
	        			"WHERE ua.assesment = " + assesmentId + " "+
	        			"AND ua.loginname = '"+loginname+"'" +
	        			"AND q.module = " +modules[i][0]+" " +
	        			"ORDER BY q.questionorder");
	        	
        			boolean done = false;
        			while(set2.next()) {
        				done = true;
    	        		if(set2.getInt("type") == CORRECT) {
    	        			s.addCell(new Label(column,row, "CORRECTA"));
    	        		}else {
    	        			s.addCell(new Label(column,row, "INCORRECTA"));
    	        		}
		            	column++;
    	        	}
        		
        			if(!done) {
                		for(int j = 0; j < modules[i][1]; j++) {
                			s.addCell(new Label(column,row, "----"));
    		            	column++;
                		}
        			}
        		}
        	}
        }

        w.write();
        w.close();

    }

    private void openJJReport(HttpServletResponse response, Integer assessment) throws Exception {
    	
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=assessment_jj.xls");


        Class.forName("org.postgresql.Driver");
        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("JJ", 0);
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        Statement st2 = conn1.createStatement();
	
        String sql = "SELECT u.loginname,u.firstname,u.lastname,u.vehicle,u.extradata," +
		"ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6," +
		"a.type,COUNT(*) AS count " +
		"FROM users u " +
		"JOIN userassesments ua ON ua.loginname = u.loginname " +
		"JOIN useranswers uan ON uan.loginname = u.loginname " +
		"JOIN answerdata ad ON ad.id = uan.answer " +
		"JOIN questions q ON q.id = ad.question " +
		"JOIN answers a ON a.id = ad.answer " +
		"WHERE ua.assesment = " + assessment + " " +
		"AND uan.assesment = " + assessment + " "+
		"AND q.testtype = " + QuestionData.TEST_QUESTION + " " +
		"GROUP BY u.loginname,u.firstname,u.lastname,u.vehicle," +
		"u.extradata,ua.psiresult1,ua.psiresult2,ua.psiresult3," +
		"ua.psiresult4,ua.psiresult5,ua.psiresult6,a.type " +
		"UNION ("+
		"SELECT DISTINCT u.loginname,u.firstname,u.lastname,u.vehicle,u.extradata,"+
		"ua.psiresult1,ua.psiresult2,ua.psiresult3,ua.psiresult4,ua.psiresult5,ua.psiresult6,0,0 "+
		"FROM users u "+ 
		"JOIN userassesments ua ON ua.loginname = u.loginname "+ 
		"JOIN useranswers uans ON uans.loginname = ua.loginname "+
		"WHERE ua.assesment = " + assessment +" "+
		"AND uans.assesment = " + assessment + " "+
		"AND u.loginname NOT IN (SELECT DISTINCT uan.loginname "+ 
		"FROM useranswers uan  "+
		"JOIN answerdata ad ON uan.answer = ad.id "+ 
		"JOIN questions q ON q.id = ad.question  "+
		"WHERE uan.assesment = " + assessment + " AND q.testtype = " + QuestionData.TEST_QUESTION + ")) ORDER BY loginname ";
		ResultSet set = st.executeQuery(sql);
		
		Hashtable values = new Hashtable();
		while(set.next()) {
			String login = set.getString("loginname");
			if(values.containsKey(login)) {
				((LineaJJ)values.get(login)).addQuestions(set);
			}else {
				values.put(login,new LineaJJ(set));
			}
		}
		
		sql = "SELECT DISTINCT u.loginname,ant.tag FROM answerdata ad " +
				"JOIN useranswers ua ON ad.id = ua.answer " +
				"JOIN answertags ant ON ad.answer = ant.answer " +
				"JOIN assesmenttags ast ON ant.tag = ast.tag " +
				"JOIN users u ON u.loginname = ua.loginname " +
				"WHERE ua.assesment = " + assessment+" "+
				"AND ast.assesment = " + assessment+" "+
				"GROUP BY u.loginname,ant.tag " +
				"HAVING sum(ant.value) > 0 " +
				"ORDER BY loginname,tag";
		set = st.executeQuery(sql);
		while(set.next()) {
			String login = set.getString("loginname");
			if(values.containsKey(login)) {
				((LineaJJ)values.get(login)).addLesson(set.getString("tag"));
			}
		}
		
		Enumeration<LineaJJ> en = values.elements();
		Collection<LineaJJ> list = new LinkedList<LineaJJ>();
		while (en.hasMoreElements()) {
			LineaJJ lineaJJ = (LineaJJ) en.nextElement();
			list.add(lineaJJ);
		}
		
        s.addCell(new Label(0,0,  "CHAMPION")); 
        s.addCell(new Label(1,0,  "NOMBRES")); 
        s.addCell(new Label(2,0,  "APELLIDOS")); 
        s.addCell(new Label(3,0,  "OP. GROUP")); 
        s.addCell(new Label(4,0,  "COMPORTA MIENTO (%)")); 
        s.addCell(new Label(5,0,  "CUESTIONARIO (%)"));
        s.addCell(new Label(6,0,  "LECCIONES"));

        int row = 1;
		Collections.sort((List) list);
		Iterator<LineaJJ> it = list.iterator();
		while (it.hasNext()) {
			LineaJJ lineaJJ = (LineaJJ) it.next();
	        s.addCell(new Label(0,row,lineaJJ.getSupervisor())); 
	        s.addCell(new Label(1,row,lineaJJ.getFirstName())); 
	        s.addCell(new Label(2,row,lineaJJ.getLastName())); 
	        s.addCell(new Label(3,row,lineaJJ.getOp_group())); 
	        s.addCell(new Label(4,row,lineaJJ.getPsi())); 
	        s.addCell(new Label(5,row,lineaJJ.getTest()));
	        s.addCell(new Label(6,row,lineaJJ.getLessons()));
	        row++;
		}
		
		w.write();
		w.close();
	
	}
    
    private void openQuestionReport(HttpServletResponse response, AssesmentAccess sys, Integer assessment) throws Exception {
    	Text messages = sys.getText();
    	String language = sys.getUserSessionData().getLenguage();
    	
    	response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=preguntas.xls");


        Class.forName("org.postgresql.Driver");
        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("Preguntas", 0);
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        
        String sql = "select gm3.text,gm1.text,'---','"+messages.getText("generic.messages.no")+"','---',m.moduleorder,q.questionorder,1 as answerorder "+
    			"from questions q "+
    			"join modules m on m.id = q.module "+
    			"join generalmessages gm1 on gm1.labelkey = q.key "+
    			"join generalmessages gm3 on gm3.labelkey = m.key "+
    			"where m.assesment = "+assessment+" "+
    			"and gm1.language = '"+language+"' "+
    			"and gm3.language = '"+language+"' "+
    			"and q.id not in (select distinct question from answers) "+
    			"union (select gm3.text,gm1.text,gm2.text, "+
    			"CASE WHEN q.testtype = 0 "+
    			"THEN '"+messages.getText("generic.messages.no")+"' "+
    			"ELSE '"+messages.getText("generic.messages.yes")+"' "+
    			"END as questiontype, "+
    			"CASE WHEN a.type = 0 "+
    			"THEN '"+messages.getText("question.result.correct")+"' "+
    			"ELSE '"+messages.getText("question.result.incorrect")+"' "+
    			"END as result,m.moduleorder,q.questionorder,a.answerorder "+
    			"from answers a "+
    			"join questions q on q.id = a.question "+
    			"join modules m on m.id = q.module "+
    			"join generalmessages gm1 on gm1.labelkey = q.key "+
    			"join generalmessages gm2 on gm2.labelkey = a.key "+
    			"join generalmessages gm3 on gm3.labelkey = m.key "+
    			"where m.assesment = "+assessment+" "+
    			"and gm1.language = '"+language+"' "+
    			"and gm2.language = '"+language+"' "+
    			"and gm3.language = '"+language+"') "+
    			"order by moduleorder,questionorder,answerorder";
        
        s.addCell(new Label(0,0,  messages.getText("generic.module").toUpperCase()));
        s.addCell(new Label(1,0,  messages.getText("generic.question").toUpperCase()));
        s.addCell(new Label(2,0,  messages.getText("question.resultreport.answer").toUpperCase()));
        s.addCell(new Label(3,0,  messages.getText("question.data.testtype").toUpperCase()));
        s.addCell(new Label(4,0,  messages.getText("report.advancejj.result").toUpperCase()));
		ResultSet set = st.executeQuery(sql);
		int index = 1;
		while(set.next()) {
			for(int i = 1; i <= 5; i++) {
				s.addCell(new Label(i-1,index,  set.getString(i)));
			}
			index++;
		}
		w.write();
		w.close();
    }

    private void openCandidatosReport(HttpServletResponse response, AssesmentAccess sys) throws Exception {
    	Text messages = sys.getText();
    	String language = sys.getUserSessionData().getLenguage();
    	
    	response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=candidatos.xls");

        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("Candidatos", 0);

        s.addCell(new Label(0,0,  "NOMBRE"));
        s.addCell(new Label(1,0,  "APELLIDO"));
        s.addCell(new Label(2,0,  "PRODUCTO"));
        s.addCell(new Label(3,0,  "CEDIS"));
        s.addCell(new Label(4,0,  "ESTADO DE LA CUENTA"));
        s.addCell(new Label(5,0,  "ÚLTIMO ACCESO"));

        String[][] values = sys.getUserReportFacade().getCandidatos(sys.getUserSessionData());
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
        		String text = (values[i][j] == null) ? "---" : values[i][j];
				s.addCell(new Label(j,i+1,  text));
			}
		}
		w.write();
		w.close();
    }
    
    private void openBasfReport(HttpServletResponse response, Calendar c1, Calendar c2, AssesmentAccess sys) throws Exception {
        Class.forName("org.postgresql.Driver");

      	response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=basf.xls");

        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("BASF", 0);

        s.addCell(new Label(0,0,  "FECHA"));
        s.addCell(new Label(1,0,  "CANTIDAD"));
        
        c2.add(Calendar.DATE, 1);
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        
        String query = "select date_trunc('day',ua.enddate) as fecha,count(*) " +
    			" from userassesments ua " +
    			" join users u on ua.loginname = u.loginname " +
    			" where assesment in (select id from assesments where corporation = 42 and id != 81) " +
    			" and enddate is not null " +
    			" and enddate < " + formatDate(c2)+
    			" and enddate >= "+ formatDate(c1)+
    			" group by fecha " +
    			" order by fecha";
		ResultSet set = st.executeQuery(query);
		int index = 1;
		int total = 0;
		while(set.next()) {
			s.addCell(new Label(0,index,  formatDate(set.getDate(1))));
			int cantidad = set.getInt(2);
			s.addCell(new Label(1,index,  String.valueOf(cantidad)));
			total += cantidad;
			index++;
		}
        s.addCell(new Label(0,index,  "TOTAL"));
        s.addCell(new Label(1,index,  String.valueOf(total)));
        
		w.write();
		w.close();
   }
    
    private void openExtendedBasfReport(HttpServletResponse response, Calendar c1, Calendar c2, AssesmentAccess sys) throws Exception {
        Class.forName("org.postgresql.Driver");

      	response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=basf.xls");

        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("BASF", 0);

        
        c2.add(Calendar.DATE, 1);
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        
        String query = "select u.loginname,u.firstname,u.lastname,date_trunc('day',ua.enddate) as fecha,u.extradata,u.extradata2,u.extradata3,ua.assesment,a.type,count(*),u.vehicle "+
        		" from userassesments ua "+
				" join users u on ua.loginname = u.loginname "+
				" join useranswers uas on (uas.assesment = ua.assesment and uas.loginname = ua.loginname) "+ 
				" join answerdata ad on ad.id = uas.answer "+
				" join questions q on q.id = ad.question "+
				" join answers a on a.id = ad.answer "+
				" where ua.assesment in (select id from assesments where corporation = 42 and id != 81) and enddate is not null "+
				" and enddate < " + formatDate(c2)+" "+
				" and enddate >= "+ formatDate(c1)+" "+
				" and q.testtype = "+QuestionData.TEST_QUESTION+
				" group by u.loginname,firstname,lastname,fecha,u.extradata,u.extradata2,u.extradata3,ua.assesment,a.type,u.vehicle "+
				" order by fecha,loginname,firstname,lastname,assesment,a.type ";
		ResultSet set = st.executeQuery(query);
		int index = 1;
		int total = 0;
		String lastLogin = "";
		int lastAssesment = 0;
		double correct = 0;
		double incorrect = 0;
		String lastDate = "";
		while(set.next()) {
			if(lastAssesment == 0) {
				
				s.addCell(new Label(0,index,  "Data"));
				s.addCell(new Label(1,index,  formatDate(set.getDate(4))));
				index++;
				
		        s.addCell(new Label(0,index,  "Motorista"));
		        s.addCell(new Label(1,index,  "Número de Identidade / RG"));
		        s.addCell(new Label(2,index,  "CPF"));
		        s.addCell(new Label(3,index,  "Placa do caminhão"));
		        s.addCell(new Label(4,index,  "Nome da transportadora"));
		        s.addCell(new Label(5,index,  "Resultado"));
				index++;

		        s.addCell(new Label(0,index,  set.getString(2)+" "+set.getString(3)));
				s.addCell(new Label(1,index,  set.getString(5)));
				s.addCell(new Label(2,index,  set.getString(6)));
				s.addCell(new Label(3,index,  set.getString(11)));
				s.addCell(new Label(4,index,  set.getString(7)));
				if(set.getInt(9) == AnswerData.CORRECT) {
					correct = set.getDouble(10);
				}else {
					incorrect = set.getDouble(10);
				}
			}else {
				if(lastAssesment != set.getInt(8) || !lastLogin.equals(set.getString(1))) {
					
					total++;

					String result = ((correct * 100.0) / (correct + incorrect) >= 80) ? "APTO" : "NAO APTO";
					s.addCell(new Label(5,index,  result));
					correct = 0;
					incorrect = 0;
					index++;

					if(!lastDate.equals(formatDate(set.getDate(4)))) {
						s.addCell(new Label(0,index,  "Data"));
						s.addCell(new Label(1,index,  formatDate(set.getDate(4))));
						index++;

				        s.addCell(new Label(0,index,  "Motorista"));
				        s.addCell(new Label(1,index,  "Número de Identidade / RG"));
				        s.addCell(new Label(2,index,  "CPF"));
				        s.addCell(new Label(3,index,  "Placa do caminhão"));
				        s.addCell(new Label(4,index,  "Nome da transportadora"));
				        s.addCell(new Label(5,index,  "Resultado"));
						index++;
					}

					s.addCell(new Label(0,index,  set.getString(2)+" "+set.getString(3)));
					s.addCell(new Label(1,index,  set.getString(5)));
					s.addCell(new Label(2,index,  set.getString(6)));
					s.addCell(new Label(3,index,  set.getString(11)));
					s.addCell(new Label(4,index,  set.getString(7)));
					if(set.getInt(9) == AnswerData.CORRECT) {
						correct = set.getDouble(10);
					}else {
						incorrect = set.getDouble(10);
					}
				}else {
					if(set.getInt(9) == AnswerData.CORRECT) {
						correct = set.getDouble(10);
					}else {
						incorrect = set.getDouble(10);
					}
				}
			}
			lastAssesment = set.getInt(8);
			lastLogin = set.getString(1);
			lastDate = formatDate(set.getDate(4));
		}
		if(lastAssesment != 0) {
			total++;
			String result = ((correct * 100.0) / (correct + incorrect) >= 80) ? "APTO" : "NAO APTO";
			s.addCell(new Label(5,index,  result));
			index++;
		}
        s.addCell(new Label(0,index,  "TOTAL"));
        s.addCell(new Label(1,index,  String.valueOf(total)));
        
		w.write();
		w.close();
   }

    private String formatDate(Date date) {
    	if(date == null)
    		return null;
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	return c.get(Calendar.DATE)+"/"+String.valueOf(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR);
	}

	private String formatDate(Calendar c) {
		if(c == null)
			return null;
    	return "'"+c.get(Calendar.YEAR)+"-"+String.valueOf(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+" 00:00:00'";
    }

    private void openMDPReport(HttpServletResponse response) throws Exception {
    	
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=mdp.xls");


        Class.forName("org.postgresql.Driver");
        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet s = w.createSheet("MDP", 0);
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        Statement st2 = conn1.createStatement();
        
        Integer assesmentId = AssesmentData.MDP_2015;
        
        int column = 0;
        s.addCell(new Label(column,0,  "Nombre")); 
        column++;
        s.addCell(new Label(column,0,  "Apellido")); 
        column++;
        s.addCell(new Label(column,0,  "Contratista")); 
        column++;
        s.addCell(new Label(column,0,  "Estado")); 
        column++;
        
        ResultSet setUsers = st.executeQuery("SELECT DISTINCT u.loginname,u.firstname,u.lastname,u.extradata,ua.enddate FROM users u" +
        		" JOIN userassesments ua ON u.loginname = ua.loginname " +
        		" WHERE ua.assesment = " + assesmentId+" ORDER BY enddate"); // cantidad total de usuarios

        int row = 0;
    	while(setUsers.next()) {
    		row++;
    		column = 0;
        	String loginname = setUsers.getString("loginname"); 
        	Date enddate = setUsers.getDate("enddate");

        	s.addCell(new Label(column,row, setUsers.getString("firstname")));
        	column++;
        	s.addCell(new Label(column,row, setUsers.getString("lastname")));
        	column++;
        	s.addCell(new Label(column,row, setUsers.getString("extradata")));
        	column++;
        	if(enddate != null) {
            	s.addCell(new Label(column,row, "Finalizado"));
        	}else {
            	ResultSet setStatus = st2.executeQuery("select count(*) from useranswers where assesment = "+assesmentId+" and loginname = '"+loginname+"'");
            	setStatus.next();
            	int answers = setStatus.getInt(1);
            	
            	if(answers == 0) {
            		s.addCell(new Label(column,row, "No comenzó"));
            	}else {
           			s.addCell(new Label(column,row, "Incompleto"));
            	}
        	}
       }
        w.write();
        w.close();
    }

    private void openANTPReport(HttpServletResponse response) throws Exception {
    	
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=rsmm.xls");


        Class.forName("org.postgresql.Driver");

        ArrayList<ArrayList<Object>> values = new ArrayList<ArrayList<Object>>();
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        Statement st2 = conn1.createStatement();
        
        Integer assesmentId = AssesmentData.ANTP_MEXICO_RSMM;
        
        ArrayList<Object> row1 = new ArrayList<Object>();
        ArrayList<Object> row2 = new ArrayList<Object>();
        String[] columns = {"Usuario","Nombre","Apellido","Email","Estado","Fecha Fin"};
    	row1.add(new String[]{"Usuario","0","5",String.valueOf(HSSFColor.GREY_25_PERCENT.index)});
        for(int i = 0; i < columns.length; i++) {
        	row2.add(new Object[]{columns[i],new Short(HSSFColor.GREY_25_PERCENT.index)});
        }
        int[] moduleSizes = {7,5,4,2,2};
        String[] moduleNames = {"Compromiso","Información","Aprendizaje Organizacional","Comunicación","Involucramiento"};
        
        int column = 6;
        for(int i = 0; i < moduleSizes.length; i++) {
        	if(i % 2 == 0) {
        		row1.add(new String[]{moduleNames[i],String.valueOf(column),String.valueOf(column+moduleSizes[i]),String.valueOf(HSSFColor.GREY_40_PERCENT.index)});
        	}else {
        		row1.add(new String[]{moduleNames[i],String.valueOf(column),String.valueOf(column+moduleSizes[i]),String.valueOf(HSSFColor.GREY_80_PERCENT.index)});        		
        	}
        	column += moduleSizes[i]+1;
        	for(int j = 1; j <= moduleSizes[i]; j++) {
            	if(i % 2 == 0) {
            		row2.add(new Object[]{"Pregunta "+j,new Short(HSSFColor.GREY_40_PERCENT.index)});
            	}else {
            		row2.add(new Object[]{"Pregunta "+j,new Short(HSSFColor.GREY_80_PERCENT.index)});
            	}
        	}
        	row2.add(new Object[]{"Promedio",new Short(HSSFColor.GREY_50_PERCENT.index)});
        }
        
    	row1.add(new Object[]{"",new Short(HSSFColor.GREY_50_PERCENT.index)});
    	row2.add(new Object[]{"Promedio Total",new Short(HSSFColor.GREY_50_PERCENT.index)});
    	
        values.add(row1);
        values.add(row2);
        
        ResultSet setUsers = st.executeQuery("SELECT u.loginname, u.firstname, u.lastname, u.email, ua.enddate, min(uas.answer), COUNT(*) " +
        		"FROM users u " +
        		"JOIN userassesments ua ON u.loginname = ua.loginname " +
        		"LEFT JOIN useranswers uas ON u.loginname = uas.loginname " +
        		"WHERE ua.assesment = "+assesmentId+" " +
        		"GROUP BY u.loginname, u.firstname, u.lastname, u.email, ua.enddate"); // cantidad total de usuarios

        HashMap<String, ANTPUser> users = new HashMap<String, ANTPUser>();
    	while(setUsers.next()) {
    		users.put(setUsers.getString(1), new ANTPUser(setUsers));
    	}
    	
    	ResultSet setAnswers = st.executeQuery("SELECT ua.loginname, m.moduleorder, q.questionorder, a.answerorder FROM useranswers ua " +
    			"JOIN answerdata ad ON ad.id = ua.answer " +
    			"JOIN answers a ON a.id = ad.answer " +
    			"JOIN questions q ON q.id = a.question " +
    			"JOIN modules m ON m.id = q.module " +
    			"WHERE ua.assesment = "+assesmentId);
    	while(setAnswers.next()) {
    		users.get(setAnswers.getString(1)).addResult(setAnswers.getInt(2), setAnswers.getInt(3), setAnswers.getInt(4));
    	}
    	
    	Collection<ANTPUser> list = new LinkedList<ANTPUser>();
    	list.addAll(users.values());
    	Collections.sort((List)list);
    	Iterator<ANTPUser> it = list.iterator();
    	
    	while(it.hasNext()) {
            ArrayList<Object> row = new ArrayList<Object>();
    		Object[] v = it.next().getLine();
    		for(int i = 0; i < v.length; i++) {
    			row.add(v[i]);
    		}
    		values.add(row);
    	}
    	
    	ExcelGenerator.generatorObjectXLS(values, "RSMM", response.getOutputStream());
    }

    private void openANTPPNSVReport(HttpServletResponse response) throws Exception {
    	
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=pnsv.xls");


        Class.forName("org.postgresql.Driver");

        ArrayList<ArrayList<Object>> values = new ArrayList<ArrayList<Object>>();
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        Statement st2 = conn1.createStatement();
        
        Integer assesmentId = AssesmentData.ANTP_MEXICO_PNSV;
        
        ArrayList<Object> row1 = new ArrayList<Object>();
        ArrayList<Object> row2 = new ArrayList<Object>();
        String[] columns = {"USUARIO","EMPRESA","FECHA EVALUACIÓN","EVALUADOR","STATUS"};
    	row1.add(new String[]{"CEPA Cuestionario","0","4",String.valueOf(HSSFColor.GREY_25_PERCENT.index)});
        for(int i = 0; i < columns.length; i++) {
        	row2.add(new Object[]{columns[i],new Short(HSSFColor.GREY_25_PERCENT.index)});
        }
        int[] moduleSizes = {81,5,3,7,1,1,12};
        String[] moduleNames = {"Preguntas CEPA","Nivel de Conocimiento - Responsable SV","Nivel de Conocimiento - Responsable Mantenimiento","Nivel de Conocimiento - Conductor","Nivel de Conocimiento - Responsable Programa Salud","Nivel de Conocimiento - Responsable Carga / Transp","Implantación en Sitio"};
        
        int column = 5;
        for(int i = 0; i < moduleSizes.length; i++) {
        	if(i % 2 == 0) {
        		row1.add(new String[]{moduleNames[i],String.valueOf(column),String.valueOf(column+moduleSizes[i]-1),String.valueOf(HSSFColor.GREY_40_PERCENT.index)});
        	}else {
        		row1.add(new String[]{moduleNames[i],String.valueOf(column),String.valueOf(column+moduleSizes[i]-1),String.valueOf(HSSFColor.GREY_80_PERCENT.index)});        		
        	}
        	column += moduleSizes[i];
        	for(int j = 1; j <= moduleSizes[i]; j++) {
            	if(i % 2 == 0) {
            		row2.add(new Object[]{"P "+j,new Short(HSSFColor.GREY_40_PERCENT.index)});
            	}else {
            		row2.add(new Object[]{"P "+j,new Short(HSSFColor.GREY_80_PERCENT.index)});
            	}
        	}
        	//row2.add(new Object[]{"Promedio",new Short(HSSFColor.GREY_50_PERCENT.index)});
        }
        
    	
        values.add(row1);
        values.add(row2);
        
        ResultSet setUsers = st.executeQuery("select ua.loginname, text, date, enddate, q.id AS qId " +
        		"from useranswers ua " +
        		"join userassesments uas on ua.assesment = uas.assesment and ua.loginname = uas.loginname "+
        		"join answerdata ad on ad.id = ua.answer " +
        		"join questions q on q.id = ad.question " +
        		"where ua.assesment = 344 " +
        		"and q.module = 1597 " +
        		"order by ua.loginname,questionorder"); // cantidad total de usuarios

        HashMap<String, ANTPUser2> users = new HashMap<String, ANTPUser2>();
    	while(setUsers.next()) {
    		String user = setUsers.getString(1);
    		if(!users.containsKey(user)) {
    			users.put(user, new ANTPUser2(setUsers, 81));
    		}
    		users.get(user).setAnswer(setUsers);
    	}

    	ResultSet setAnswers = st.executeQuery("SELECT ua.loginname, m.moduleorder, q.questionorder, a.answerorder FROM useranswers ua " +
    			"JOIN answerdata ad ON ad.id = ua.answer " +
    			"JOIN answers a ON a.id = ad.answer " +
    			"JOIN questions q ON q.id = a.question " +
    			"JOIN modules m ON m.id = q.module " +
    			"WHERE ua.assesment = "+assesmentId+" AND m.moduleorder IN (2,3,4) " +
    					"order by loginname, moduleorder, questionorder ");
    	while(setAnswers.next()) {
    		int index = ((setAnswers.getInt(2)-2)*27)+setAnswers.getInt(3);
    		users.get(setAnswers.getString(1)).addResult(1,index,setAnswers.getInt(4));
    	}

    	setAnswers = st.executeQuery("SELECT ua.loginname, m.moduleorder, q.questionorder, a.answerorder FROM useranswers ua " +
    			"JOIN answerdata ad ON ad.id = ua.answer " +
    			"JOIN answers a ON a.id = ad.answer " +
    			"JOIN questions q ON q.id = a.question " +
    			"JOIN modules m ON m.id = q.module " +
    			"WHERE ua.assesment = "+assesmentId+" AND m.moduleorder > 4");
    	while(setAnswers.next()) {
    		users.get(setAnswers.getString(1)).addResult(setAnswers.getInt(2)-3, setAnswers.getInt(3), setAnswers.getInt(4));
    	}

    	Collection<ANTPUser2> list = new LinkedList<ANTPUser2>();
    	list.addAll(users.values());
    	Collections.sort((List)list);
    	Iterator<ANTPUser2> it = list.iterator();
    	
    	while(it.hasNext()) {
            ArrayList<Object> row = new ArrayList<Object>();
    		Object[] v = it.next().getLine();
    		for(int i = 0; i < v.length; i++) {
    			row.add(v[i]);
    		}
    		values.add(row);
    	}
    	
    	ExcelGenerator.generatorObjectXLS(values, "ANTP - PNSV", response.getOutputStream());
    }

    private void openANTPPNSVReport2(HttpServletResponse response) throws Exception {
    	
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "inline; filename=pnsv.xls");


        Class.forName("org.postgresql.Driver");

        ArrayList<ArrayList<Object>> values = new ArrayList<ArrayList<Object>>();
        
        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        
        Statement st = conn1.createStatement();
        Statement st2 = conn1.createStatement();
        
        Integer assesmentId = AssesmentData.ANTP_MEXICO_PNSV_2;
        
        ArrayList<Object> row1 = new ArrayList<Object>();
        ArrayList<Object> row2 = new ArrayList<Object>();
        String[] columns = {"USUARIO","EMPRESA","FECHA EVALUACIÓN","EVALUADOR","STATUS"};
    	row1.add(new String[]{"CEPA Cuestionario","0","4",String.valueOf(HSSFColor.GREY_25_PERCENT.index)});
        for(int i = 0; i < columns.length; i++) {
        	row2.add(new Object[]{columns[i],new Short(HSSFColor.GREY_25_PERCENT.index)});
        }
        int[] moduleSizes = {133,5,3,7,1,1,12};
        String[] moduleNames = {"Preguntas CEPA","Nivel de Conocimiento - Responsable SV","Nivel de Conocimiento - Responsable Mantenimiento","Nivel de Conocimiento - Conductor","Nivel de Conocimiento - Responsable Programa Salud","Nivel de Conocimiento - Responsable Carga / Transp","Implantación en Sitio"};
        
        int column = 5;
        for(int i = 0; i < moduleSizes.length; i++) {
        	if(i % 2 == 0) {
        		row1.add(new String[]{moduleNames[i],String.valueOf(column),String.valueOf(column+moduleSizes[i]-1),String.valueOf(HSSFColor.GREY_40_PERCENT.index)});
        	}else {
        		row1.add(new String[]{moduleNames[i],String.valueOf(column),String.valueOf(column+moduleSizes[i]-1),String.valueOf(HSSFColor.GREY_80_PERCENT.index)});        		
        	}
        	column += moduleSizes[i];
        	for(int j = 1; j <= moduleSizes[i]; j++) {
            	if(i % 2 == 0) {
            		row2.add(new Object[]{"P "+j,new Short(HSSFColor.GREY_40_PERCENT.index)});
            	}else {
            		row2.add(new Object[]{"P "+j,new Short(HSSFColor.GREY_80_PERCENT.index)});
            	}
        	}
        	//row2.add(new Object[]{"Promedio",new Short(HSSFColor.GREY_50_PERCENT.index)});
        }
        
    	
        values.add(row1);
        values.add(row2);
        
        ResultSet setUsers = st.executeQuery("select ua.loginname, text, date, enddate, q.id AS qId " +
        		"from useranswers ua " +
        		"join userassesments uas on ua.assesment = uas.assesment and ua.loginname = uas.loginname "+
        		"join answerdata ad on ad.id = ua.answer " +
        		"join questions q on q.id = ad.question " +
        		"where ua.assesment = 470 " +
        		"and q.module = 1936 " +
        		"order by ua.loginname,questionorder"); // cantidad total de usuarios

        HashMap<String, ANTPUser2> users = new HashMap<String, ANTPUser2>();
    	while(setUsers.next()) {
    		String user = setUsers.getString(1);
    		if(!users.containsKey(user)) {
    			users.put(user, new ANTPUser2(setUsers, 133));
    		}
    		users.get(user).setAnswer(setUsers);
    	}

    	ResultSet setAnswers = st.executeQuery("SELECT ua.loginname, m.moduleorder, q.questionorder, a.answerorder FROM useranswers ua " +
    			"JOIN answerdata ad ON ad.id = ua.answer " +
    			"JOIN answers a ON a.id = ad.answer " +
    			"JOIN questions q ON q.id = a.question " +
    			"JOIN modules m ON m.id = q.module " +
    			"WHERE ua.assesment = "+assesmentId+" AND m.moduleorder IN (2,3,4,5) " +
    					"order by loginname, moduleorder, questionorder ");
    	while(setAnswers.next()) {
    		int index = ((setAnswers.getInt(2)-2)*33)+setAnswers.getInt(3);
    		users.get(setAnswers.getString(1)).addResult(1,index,setAnswers.getInt(4));
    	}

    	setAnswers = st.executeQuery("SELECT ua.loginname, m.moduleorder, q.questionorder, a.answerorder FROM useranswers ua " +
    			"JOIN answerdata ad ON ad.id = ua.answer " +
    			"JOIN answers a ON a.id = ad.answer " +
    			"JOIN questions q ON q.id = a.question " +
    			"JOIN modules m ON m.id = q.module " +
    			"WHERE ua.assesment = "+assesmentId+" AND m.moduleorder > 5");
    	while(setAnswers.next()) {
    		users.get(setAnswers.getString(1)).addResult(setAnswers.getInt(2)-4, setAnswers.getInt(3), setAnswers.getInt(4));
    	}

    	Collection<ANTPUser2> list = new LinkedList<ANTPUser2>();
    	list.addAll(users.values());
    	Collections.sort((List)list);
    	Iterator<ANTPUser2> it = list.iterator();
    	
    	while(it.hasNext()) {
            ArrayList<Object> row = new ArrayList<Object>();
    		Object[] v = it.next().getLine();
    		for(int i = 0; i < v.length; i++) {
    			row.add(v[i]);
    		}
    		values.add(row);
    	}
    	
    	ExcelGenerator.generatorObjectXLS(values, "ANTP - PNSV 2", response.getOutputStream());
    }

    private void openTotalAnswers(Integer assessment, Integer reportType, AssesmentAccess sys, HttpServletResponse response) throws Exception {
    	
		try {

	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename=Respuestas.xls");
	        
	        Class.forName("org.postgresql.Driver");
	        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet s = w.createSheet("Resumen", 0);

	        UserSessionData userSessionData = sys.getUserSessionData();
	        Text messages = sys.getText();

			AssesmentData data = sys.getAssesmentReportFacade().findAssesment(assessment,sys.getUserSessionData());

			s.addCell(new Label(0, 0, messages.getText("user.data.nickname"))); 
	        s.addCell(new Label(1, 0, messages.getText("user.data.firstname"))); 
	        s.addCell(new Label(2, 0,  messages.getText("user.data.lastname"))); 
	        s.addCell(new Label(3, 0,  messages.getText("user.data.mail"))); 
	        if(data.isWebinar()) {
		        s.addCell(new Label(4, 0,  messages.getText("report.users.webinarcode")));
		        s.addCell(new Label(5, 0,  messages.getText("assesment.status.ended")));
	        }else {
	        	s.addCell(new Label(4, 0,  messages.getText("assesment.status.ended")));
	        }
	        
	        Collection answers = sys.getQuestionReportFacade().getTotalAnswers(assessment, messages, userSessionData);
	        Iterator it = answers.iterator();
	        int row = 1;
	        while(it.hasNext()) {
	        	AssessmentUserData user = (AssessmentUserData) it.next();
		        s.addCell(new Label(0, row,  user.getLoginname())); 
		        s.addCell(new Label(1, row,  user.getFirstname())); 
		        s.addCell(new Label(2, row,  user.getLastname())); 
		        s.addCell(new Label(3, row,  user.getEmail())); 
		        if(data.isWebinar()) {
			        s.addCell(new Label(4, row,  user.getExtraData3()));
			        s.addCell(new Label(5, row,  (user.getEndDate() == null) ? "---" : Util.formatDate(user.getEndDate())));
		        }else {
		        	s.addCell(new Label(4, row,  (user.getEndDate() == null) ? "---" : Util.formatDate(user.getEndDate())));
		        }
		        row++;
	        }
	        
			Iterator moduleIt = data.getModuleIterator();
			int pages = 1;
			while(moduleIt.hasNext()) {
				ModuleData module = (ModuleData) moduleIt.next();
		        s = w.createSheet(messages.getText(module.getKey()), pages);
		        s.addCell(new Label(0, 0, messages.getText("user.data.nickname"))); 
		        
		        QuestionData[] ids = new QuestionData[module.getQuestionSize()];
		        Iterator questionIt = module.getQuestionIterator();
		        int column = 1;
		        while(questionIt.hasNext()) {
		        	QuestionData question = (QuestionData)questionIt.next();
		        	ids[column-1] = question;
		        	if(question.getType().intValue() == QuestionData.YOU_TUBE_VIDEO || question.getType().intValue() == QuestionData.VIDEO) {
		        		s.addCell(new Label(column, 0, messages.getText("question.type.video")));
		        	} else {
		        		s.addCell(new Label(column, 0, messages.getText(question.getKey())));
		        	}
			        column++;
		        }
		        
		        row = 1;
		        it = answers.iterator();
		        while(it.hasNext()) {
		        	AssessmentUserData user = (AssessmentUserData) it.next();
			        s.addCell(new Label(0, row,  user.getLoginname())); 
			        for(int i = 0; i < ids.length; i++) {
			        	s.addCell(new Label(i+1, row, user.getQuestionAnswer(ids[i], reportType, messages)));
			        }
			        row++;
		        }
				pages++;
			}
        
			w.write();
			w.close();
	        
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
	 private void openCharlaUPMReport(HttpServletResponse response, AssesmentAccess sys) throws Exception {
	    	
	     response.setHeader("Content-Type", "application/vnd.ms-excel");
	     response.setHeader("Content-Disposition", "inline; filename=CharlasUPMReport.xls");
	     Text messages=sys.getText();
	
	     Class.forName("org.postgresql.Driver");

	     Connection connFDM = (SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://"+FDM_SERVER+":5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
	     Statement stFDM = connFDM.createStatement();

	     ResultSet setFDM = stFDM.executeQuery("SELECT d.id, firstname, lastname, corporationid, d1.resourcekey "
	     		+ "FROM drivers d "
	     		+ "JOIN divorgitemlevel1s d1 ON d1.id = d.divorg1 "
	     		+ "WHERE d.id IN (70281, 71073, 325931, 142972, 171559, 280543, 187476, 70314, 70457, 71927, 160360, 124441, 163275, 70421, 164244, 70310, 158475, 200215, 70428, 120806, 72000, 70537, 115311, 202871, 70282, 161589, 321358, 218309, 248100, 326237, 165176, 325397, 115154, 247860, 125551, 247705, 276235, 276283, 247986, 143386, 71935, 71074, 71963, 119549, 326777, 171803, 161148, 327152, 327162, 70302, 281373, 171982, 209051, 70518, 142613, 125845, 308374, 141234, 143338, 215934, 182142, 167077, 327339, 327341, 195748, 125806, 71946, 204378, 145895, 120597, 219447, 206961, 209652, 71403, 323367, 209332, 71394, 145262, 248249, 157636, 197019, 169973, 199115, 140053, 146736, 167621, 248905, 220032, 249107, 249174, 150814, 145882, 229056, 71941, 323984, 146508, 71193, 175767, 13260222, 126075, 146722, 220240, 219584, 141072, 71286, 236109, 70505, 71851, 219516, 237655, 312768, 171217, 204377, 324074, 324081, 253221, 327798, 328628, 175805, 200880, 227425, 70275, 276540, 13422410, 276573, 252274, 329174, 276564, 72020, 277472, 277482, 277669, 277796, 13469802, 202660, 202727, 205095, 212152, 247683, 170696, 160143, 167133, 253058, 253571, 253568, 253554, 125182, 254571, 140681, 254270, 210536, 212153, 202662, 254599, 252417, 186829, 121525, 246756, 141232, 255906, 256058, 121526, 121279, 123668, 125503, 110528, 155815, 211928, 185017, 171731, 256751, 256746, 140807, 186831, 257583, 257738, 145955, 258362, 115177, 171746, 271798, 261030, 227417, 255180, 240345, 237272, 263021, 157594, 302041, 273208, 115405, 70523, 273900, 266773, 302455, 274136, 70478, 275562, 275658, 277969, 203022, 126210, 71216, 278969, 146323, 279188, 276256, 329267, 281327, 255156, 256601, 329481, 329587, 280308, 280545, 161671, 296193, 251340, 161943, 187896, 282167, 215677, 282573, 248520, 212482, 166654, 283528, 161295, 283537, 284205, 284294, 284575, 255945, 206221, 171088, 286835, 286833, 287047, 288107, 71750, 115179, 70640, 287459, 253277, 330604, 330605, 330640, 330694, 330709, 156065, 290191, 214340, 290700, 266386, 71192, 254327, 124415, 218937, 124908, 215993, 117587, 115778, 70515, 71396, 72009, 156060, 71961, 70467, 70540, 71405, 120969, 257813, 70507, 268222, 71425, 204373, 250798, 145886, 291745, 292110, 292395, 116027, 70350, 168058, 214823, 121624, 240701, 296677, 296683, 296558, 296553, 331051, 331147, 70344, 331156, 297019, 275672, 172129, 93420, 303672, 303685, 146013, 146983, 286839, 303753, 303838, 303840, 174825, 332258, 332483, 274259, 144364, 329678, 333003, 187268, 71195, 208118, 228212, 71783, 71247, 166780, 71473, 71728, 175771, 13338279, 210625, 70441, 115078, 70391, 291120, 213471, 120920, 210829, 125746, 274555, 71291, 70370, 125863, 291476, 142928, 305238, 213576, 215152, 180409, 71971, 267192, 70638, 213425, 278523, 146025, 252415, 159945, 207905, 126912, 125058, 204838, 249759, 198533, 167022, 126472, 70425, 214372, 305449, 178617, 203280, 328261, 115313, 296049, 335141, 286493, 13399250, 115429, 121199, 125868, 253142, 178858, 162988, 140082, 308237, 302126, 70424, 198673, 13422412, 274646, 203515, 168006, 273230, 155514, 70401, 175768, 209653, 251997, 124026, 127072, 301461, 210264, 115909, 202872, 296096, 70544, 220238, 71949, 70633, 180728, 146015, 210830, 13313267, 141264, 96472, 119914, 143588, 154939, 140728, 70573, 186838, 155246, 202939, 206204, 141094, 71249, 227418, 71383, 146491, 207698, 126746, 276318, 115375, 115479, 70309, 146739, 273418, 290642, 70353, 70520, 71246, 292225, 70280, 273739, 181814, 70542, 146866, 70308, 70459, 102082, 237034, 70442, 217822, 70445, 70312, 70476, 70426, 208230, 214875, 212412, 215443, 140452, 146024, 70639, 182048, 164175, 70389, 250224, 179673, 70559, 257020, 286686, 70473, 212150, 167538, 93396, 71190, 247002, 115279, 212154, 70276, 210802, 253561, 120681, 202884, 71968, 204376, 238882, 203514, 184521, 237653, 163273, 240374, 314445, 168228, 315304, 315485, 71475, 253123, 315873, 301598, 316180, 186840, 315558, 257113, 317231, 317414, 317294, 70324, 158542, 157727, 70325, 184559, 121163, 125747, 70313, 261162, 279862, 278894, 124043, 70553, 252881, 164523, 251181, 71966, 209651, 253565, 161509, 124045, 70402, 125889, 161299, 70501, 125545, 117583, 210801, 156002, 287413, 238146, 211923, 180411, 146682, 218938, 209095, 70635, 296130, 317848, 317978, 317986, 318049, 318050, 318042, 318218, 318493, 318490, 318794, 318788, 318811, 251542, 254571, 261158, 115179, 287465, 184521, 272070, 219447, 120912)");
	     
	     
	     Collection<UsuarioCharlaUPM> finalList = new LinkedList<UsuarioCharlaUPM>();
	     
	     HashMap<Integer, UsuarioCharlaUPM> values = new HashMap<Integer, UsuarioCharlaUPM>();
	     while(setFDM.next()) {
	    	 values.put(setFDM.getInt(1), new UsuarioCharlaUPM(setFDM.getString(2), setFDM.getString(3), setFDM.getString(4), setFDM.getString(5)));
	     }
	     
	     setFDM.close();
	     connFDM.close();
	     
	     Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
	     
	     Statement st = conn1.createStatement();
		 
	     ResultSet setUsers = st.executeQuery("SELECT extradata, firstname, lastname, extradata2, brithdate + interval '30 year', enddate, uar.correct, uar.incorrect, uar2.correct, uar2.incorrect, u.datacenter "+
 								   	  "FROM userassesments ua "+
 								      "JOIN users u ON u.loginname = ua.loginname "+ 
 								      "LEFT JOIN userassesmentresults uar ON uar.login = ua.loginname AND uar.assesment = ua.assesment "+
 								      "LEFT JOIN userassesmentresults uar2 ON uar2.login = ua.loginname AND uar2.assesment = ua.assesment + 1 "+
 								      "WHERE ua.assesment = "+ AssesmentData.UPM_CHARLA);
	 	 while(setUsers.next()) {
	 		 boolean doNew = true;
		     if(setUsers.getString(11)!=null) {
		    	 Integer idDataCenter=Integer.parseInt(setUsers.getString(11));
			     if(values.containsKey(idDataCenter)) {
			    	 UsuarioCharlaUPM usuario = values.get(idDataCenter);
			    	 usuario.setRegister(setUsers.getTimestamp(5));
			    	 usuario.setEndDate(setUsers.getTimestamp(6));
			    	 usuario.setCorrect1(setUsers.getInt(7));
			    	 usuario.setIncorrect1(setUsers.getInt(8));
			    	 usuario.setCorrect2(setUsers.getInt(9));
			    	 usuario.setIncorrect2(setUsers.getInt(10));
			    	 doNew = false;
			     }
		     }
		     if(doNew) {
		    	 finalList.add(new UsuarioCharlaUPM(setUsers.getString(2), setUsers.getString(3), setUsers.getString(1), setUsers.getString(4), setUsers.getTimestamp(5), setUsers.getTimestamp(6), setUsers.getInt(7), setUsers.getInt(8), setUsers.getInt(9), setUsers.getInt(10)));
		     }
	 	 }	     
	     
	 	 finalList.addAll(values.values());
	 	 
	     ArrayList<Integer> driversAssesment =new ArrayList(); 
	     ArrayList<Object> row1 = new ArrayList<Object>();
	     ArrayList<Object> row2 = new ArrayList<Object>();
		 String[] columns = {"CI","NOMBRES","APELLIDOS","CONTRATISTA","REGISTRO","FINALIZACIÓN","CORRECTAS TEST 1","ICORRECTAS TEST 1","CORRECTAS TEST 2","ICORRECTAS TEST 2"};

	     row1.add(new String[]{"CHARLAS UPM","0","10",String.valueOf(HSSFColor.GREY_25_PERCENT.index)});
	     for(int i = 0; i < columns.length; i++) {
	     	row2.add(new Object[]{columns[i],new Short(HSSFColor.GREY_25_PERCENT.index)});
	     }

	     ArrayList<ArrayList<Object>> valuesXLS = new ArrayList<ArrayList<Object>>();
	     
	     valuesXLS.add(row1);
	     valuesXLS.add(row2);

	     Collections.sort((List)finalList);
	     Iterator<UsuarioCharlaUPM> it = finalList.iterator();
	 	 while(it.hasNext()) {
		     valuesXLS.add(it.next().getLine());
	 	 }
	 	
	 	 ExcelGenerator.generatorObjectXLS(valuesXLS, "Charlas UPM report", response.getOutputStream());
	 }
	 
	 private void openTimacReport(HttpServletResponse response, AssesmentAccess sys) throws Exception {
	    	
	     response.setHeader("Content-Type", "application/vnd.ms-excel");
	     response.setHeader("Content-Disposition", "inline; filename=TimacReport.xls");
	     Text messages=sys.getText();
	
	     Class.forName("org.postgresql.Driver");
	
	     ArrayList<ArrayList<Object>> values = new ArrayList<ArrayList<Object>>();
	     
	     Connection conn1 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
	     
	     Statement st = conn1.createStatement();
	     Statement st2 = conn1.createStatement();
	     
	     String idAssesment1="1051";
	     String idAssesment2="1052";
	     
	     ArrayList<Object> row1 = new ArrayList<Object>();
	     ArrayList<Object> row2 = new ArrayList<Object>();
		 String[] columns = {"USUARIO","NOMBRES","APELLIDOS","EMAIL", "EXTRA 1", "EXTRA 2", "FECHA REGISTRO", messages.getText("assessment1051.name"), messages.getText("assessment1052.name")};

	     for(int i = 0; i < columns.length; i++) {
	     	row2.add(new Object[]{columns[i],new Short(HSSFColor.GREY_25_PERCENT.index)});
	     }

	 	
	     values.add(row1);
	     values.add(row2);
	    		 
	     ResultSet setUsers = st.executeQuery("SELECT DISTINCT u.loginname, u.firstname, u.lastname,u.email, u.extradata, u.extradata2, u.startdate, ua.enddate, ua2.enddate "
	     		+ "FROM users u "+
				  "LEFT JOIN userassesments ua ON ua.loginname=u.loginname "+
				  "LEFT JOIN userassesments ua2 ON ua2.loginname=u.loginname "+
				  "WHERE ua.assesment="+idAssesment1+" AND ua2.assesment="+idAssesment2+
				  " ORDER BY startdate");
 
	     String[] users = new String[4];
 		 Calendar c = Calendar.getInstance();
	 	 while(setUsers.next()) {
		     ArrayList<Object> row = new ArrayList<Object>();
	 		 for(int i = 1; i <= 6; i++)
	 			row.add(setUsers.getString(i));
	 		 if(setUsers.getDate(7) != null) {
	 			 c.setTime(setUsers.getDate(7));
		 		 if(c.get(Calendar.YEAR) >= 2020)		 
		 			 row.add(formatDate(c));
		 		 else
		 			row.add(null);
	 		 }else
		 			row.add(null);
	 		 row.add(status(setUsers.getString(8)));
	 		 row.add(status(setUsers.getString(9)));
	 		 values.add(row);
	 	}

	 	ExcelGenerator.generatorObjectXLS(values, "Timac report", response.getOutputStream());
	 }
	 
	 public String status(String enddate) {
	 	if(enddate!=null) {
	 		return "Finalizado";
	 	}
	 	else return "No iniciado";
	 }

}