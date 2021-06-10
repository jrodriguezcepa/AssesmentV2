/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.csvreader.CsvReader;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class TimacUserCreateFileAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

    	actualizarUsuarios(request);
    	/*
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        Collection<UserData> users = new LinkedList<UserData>();
        
        UserCreateFileForm createData = (UserCreateFileForm) createForm;
        FormFile file = createData.getFile();
        if (file == null || file.getFileName() == null || file.getFileName().equals("") || file.getFileSize() == 0){
		    session.setAttribute("Msg", messages.getText("user.corporations.emptyfile"));
            return mapping.findForward("back");
        }
        if (!file.getFileName().toLowerCase().endsWith(".csv")){
		    session.setAttribute("Msg", messages.getText("user.corporations.wrongfile"));
            return mapping.findForward("back");
        }
        
        String directory = SecurityConstants.FILES_WAR_DIRECTORY;
        Calendar c = Calendar.getInstance();
        String fileEnding = sys.getUserSessionData().getFilter().getLoginName()+"_"+c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.MONTH)+c.get(Calendar.YEAR)+"_"+new File(directory).listFiles().length; 
        
        String fileName = "In_"+fileEnding+".csv";
    
        InputStream reportStream = file.getInputStream();		
		FileOutputStream fout;
				
		fout = new FileOutputStream(directory + fileName);
		
		int cc = 0;
		while ((cc=reportStream.read()) != -1){
			fout.write(cc);
		}			
		fout.flush();
		fout.close();		

		
		CsvReader reader = new CsvReader(SecurityConstants.FILES_WAR_DIRECTORY + fileName);
		reader.setDelimiter(';');
		reader.readHeaders();
		int line = 1;
		while(reader.readRecord()) {
			String firstName = reader.get(0).trim();
			if(Util.empty(firstName)) {
				throw new Exception("Linea "+line+": nombre vacío.");
			}
			String lastName = reader.get(1).trim();
			if(Util.empty(firstName)) {
				throw new Exception("Linea "+line+": apellido vacío.");
			}
			String email = reader.get(2).trim().toLowerCase();
			if(!Util.checkEmail(email)) {
				throw new Exception("Linea "+line+": email incorrecto.");
			}
			String cpf = reader.get(3).trim();
			cpf = cpf.replaceAll("\\.", "").replaceAll("-", "");
			if(Util.empty(cpf)) {
				throw new Exception("Linea "+line+": apellido vacío.");
			}
			cpf = Util.getTimacCPF(cpf);
			String extraData = reader.get(4).trim();
			String extraData2 = reader.get(5).trim();

			UserData user = new UserData(cpf, new MD5().encriptar(cpf), firstName, lastName, "pt", email, UserData.MULTIASSESSMENT, null);
     		user.setExtraData(extraData);
     		user.setExtraData2(extraData2);

     		users.add(user);
		}

		String[][] result = sys.getUserABMFacade().saveTimacUsers(users, sys.getUserSessionData());
		sys.setValue(result);*/
		return mapping.findForward("success");
    }

	private void actualizarUsuarios(HttpServletRequest request)  {
		try {
	        Connection conn1 = DriverManager.getConnection("jdbc:postgresql://177.71.248.87:5432/assesment","postgres","pr0v1s0r1A");
	        Statement st = conn1.createStatement();
	        Statement st2 = conn1.createStatement();
	        
	    	Collection<String> list = new LinkedList<String>();
	        HttpSession session = request.getSession();
	        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
	        Text messages = sys.getText();
	
	        CsvReader reader = new CsvReader("C:/Users/Usuario/Desktop/BORRAR/timac.csv");
	        reader.setDelimiter(';');
	            
			reader.readHeaders();
	        
			while (reader.readRecord()) {
							
				String cpf = reader.get("CPF").trim();
				cpf = cpf.replaceAll("\\.", "").replaceAll("-", "");
				
				if(!list.contains(cpf)) {
					list.add(cpf);
				    ResultSet set = st.executeQuery("SELECT u.loginname FROM users u JOIN userassesments ua ON ua.loginname = u.loginname where u.loginname LIKE 'tmc%"+cpf+"' AND enddate is not null AND assesment IN (1051, 1052)");
					if(set.next()) {
						System.out.println("- "+set.getString(1));
					}else {	
						String login = reader.get("Usuário").trim();
						String firstName = reader.get("Nome").trim().toUpperCase();
						String lastName = reader.get("Sobrenome").trim().toUpperCase();
						String email = reader.get("Endereço eletrônico").trim().toLowerCase();
	
						cpf = Util.getTimacCPF(cpf);
						
						Collection<UserData> users = new LinkedList<UserData>();
						UserData user = new UserData(cpf, new MD5().encriptar(cpf), firstName, lastName, "pt", email, UserData.MULTIASSESSMENT, null);
	
			     		users.add(user);
						String[][] result = sys.getUserABMFacade().saveTimacUsers(users, sys.getUserSessionData());
						String newLogin = result[2][0];
	
						ResultSet set2 = st.executeQuery("SELECT extradata, extradata2, assesment, enddate, psiresult1, psiresult2, psiresult3, psiresult4, psiresult5, psiresult6, psiid, psitestid FROM users u JOIN userassesments ua ON ua.loginname = u.loginname WHERE LOWER(u.loginname) LIKE '"+login+"' AND assesment IN (1051,1052)");
						while(set2.next()) {
							st2.execute("UPDATE users SET extradata = '"+set2.getString(1)+"', extradata2 = '"+set2.getString(1)+"' WHERE loginname = '"+newLogin+"'");
							if(set2.getInt(3) == 1052) {
								String s = "UPDATE userassesments SET assesment = 1052";
								if(set2.getDate(4) != null) {
									s += ", enddate = '"+set2.getDate(4)+"'";
								}
								if(set2.getString(11) != null) {
									s += ", psiresult1 = "+set2.getInt(5);
									s += ", psiresult2 = "+set2.getInt(6);
									s += ", psiresult3 = "+set2.getInt(7);
									s += ", psiresult4 = "+set2.getInt(8);
									s += ", psiresult5 = "+set2.getInt(9);
									s += ", psiresult6 = "+set2.getInt(10);
									s += ", psiid = "+set2.getInt(11);
									s += ", psitestid = "+set2.getInt(12);
								}
								s += " WHERE assesment = 1052 AND loginname = '"+newLogin+"'";
								st2.execute(s);
							}
							if(set2.getInt(3) == 1051) {
								String s = "UPDATE userassesments SET assesment = 1051";
								if(set2.getDate(4) != null) {
									s += ", enddate = '"+set2.getDate(4)+"'";
								}
								s += " WHERE assesment = 1051 AND loginname = '"+newLogin+"'";
								st2.execute(s);
							}
						}
						st2.execute("update userpsianswers set loginname = '"+newLogin+"' where loginname = '"+login+"'");
						st2.execute("update useranswers set loginname = '"+newLogin+"' where loginname = '"+login+"'");
						st2.execute("delete from userassesments where loginname = '"+login+"'");
						st2.execute("delete from users where loginname = '"+login+"'");
					}
				}else {
					String login = reader.get("Usuário").trim();
					String firstName = reader.get("Nome").trim().toUpperCase();
					String lastName = reader.get("Sobrenome").trim().toUpperCase();
					String email = reader.get("Endereço eletrônico").trim().toLowerCase();

					cpf = Util.getTimacCPF(cpf);
					
					Collection<UserData> users = new LinkedList<UserData>();
					UserData user = new UserData(cpf, new MD5().encriptar(cpf), firstName, lastName, "pt", email, UserData.MULTIASSESSMENT, null);

		     		users.add(user);
					String[][] result = sys.getUserABMFacade().saveTimacUsers(users, sys.getUserSessionData());
					String newLogin = result[2][0];

					ResultSet set2 = st.executeQuery("SELECT extradata, extradata2, assesment, enddate, psiresult1, psiresult2, psiresult3, psiresult4, psiresult5, psiresult6, psiid, psitestid FROM users u JOIN userassesments ua ON ua.loginname = u.loginname WHERE LOWER(u.loginname) LIKE '"+login+"' AND assesment IN (1051,1052)");
					while(set2.next()) {
						st2.execute("UPDATE users SET extradata = '"+set2.getString(1)+"', extradata2 = '"+set2.getString(1)+"' WHERE loginname = '"+newLogin+"'");
						if(set2.getInt(3) == 1052) {
							String s = "UPDATE userassesments SET assesment = 1052";
							if(set2.getDate(4) != null) {
								s += ", enddate = '"+set2.getDate(4)+"'";
							}
							if(set2.getString(11) != null) {
								s += ", psiresult1 = "+set2.getInt(5);
								s += ", psiresult2 = "+set2.getInt(6);
								s += ", psiresult3 = "+set2.getInt(7);
								s += ", psiresult4 = "+set2.getInt(8);
								s += ", psiresult5 = "+set2.getInt(9);
								s += ", psiresult6 = "+set2.getInt(10);
								s += ", psiid = "+set2.getInt(11);
								s += ", psitestid = "+set2.getInt(12);
							}
							s += " WHERE assesment = 1052 AND loginname = '"+newLogin+"'";
							st2.execute(s);
						}
						if(set2.getInt(3) == 1051) {
							String s = "UPDATE userassesments SET assesment = 1051";
							if(set2.getDate(4) != null) {
								s += ", enddate = '"+set2.getDate(4)+"'";
							}
							s += " WHERE assesment = 1051 AND loginname = '"+newLogin+"'";
							st2.execute(s);
						}
					}
					st2.execute("update userpsianswers set loginname = '"+newLogin+"' where loginname = '"+login+"'");
					st2.execute("update useranswers set loginname = '"+newLogin+"' where loginname = '"+login+"'");
					st2.execute("delete from userassesments where loginname = '"+login+"'");
					st2.execute("delete from users where loginname = '"+login+"'");
				}
			}
			st.close();
			st2.close();
			conn1.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Date formatDate(String value) {
		try {
			Calendar c = Calendar.getInstance();
			StringTokenizer tokDate = new StringTokenizer(value,"/");
			c.set(Calendar.DATE, Integer.parseInt(tokDate.nextToken()));
			c.set(Calendar.MONTH, Integer.parseInt(tokDate.nextToken())-1);
			c.set(Calendar.YEAR, Integer.parseInt(tokDate.nextToken()));
			return c.getTime();
		}catch(Exception e) {
			return null;
		}
	}

	private static String replaceChars(String text) {
		String value = "";
		text.replaceAll("'", "''");
		for (int i = 0; i < text.length(); i++) {
			switch (text.toLowerCase().charAt(i)) {
			case 'á':
			case 'à':
			case 'ã':
				value += "a";
				break;
			case 'é':
			case 'ê':
				value += "e";
				break;
			case 'í':
				value += "i";
				break;
			case 'ó':
				value += "o";
				break;
			case 'ú':
				value += "u";
				break;
			case 'ñ':
				value += "n";
				break;
			case 'ç':
				value += "c";
				break;
			case 'ü':
				value += "u";
				break;
			case '|':
				value += "";
				break;
			case '-':
				value += "";
				break;
			case '_':
				value += "";
				break;
			default:
				value += String.valueOf(text.charAt(i));
			}
		}
		return value;
	}
}
