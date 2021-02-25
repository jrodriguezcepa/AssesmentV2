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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.persistence.util.ExcelGenerator;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

import com.csvreader.CsvReader;

public class UserCreateFileAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

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
        
        String ending = createData.getExtra();

        int columnCount = Integer.parseInt(createData.getColumnCount());
        int[] varHeaders = new int[columnCount-3];
        Collection<String> columnsV = new LinkedList<String>();
        for(int i = 0; i < varHeaders.length; i++) {
        	String v = createData.getColumn(i+4);
        	if(columnsV.contains(v)) {
    		    session.setAttribute("Msg", messages.getText("Columna repetida"));
                return mapping.findForward("back");
        	}else {
        		columnsV.add(v);
        		varHeaders[i] = Integer.parseInt(v);
        	}
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
        
		Collection<UserData> list = new LinkedList<UserData>();
		try {
			list = getUsersFromFile(fileName,createData,varHeaders,sys);
		} catch (Exception e) {
		    session.setAttribute("Msg", e.getMessage());
            return mapping.findForward("back");
		}

		Collection<UserData> result = null;
		String role = createData.getRole();
		if(role.equals(SecurityConstants.ACCESS_TO_SYSTEM) || role.equals(SecurityConstants.MULTI_ASSESSMENT)) {
			Collection<String> assesments = new LinkedList<String>();
			if(role.equals(SecurityConstants.ACCESS_TO_SYSTEM)) {
				String assesment = createData.getAssesment();
		        if(Util.empty(assesment)) {
		            session.setAttribute("Msg", messages.getText("user.corporations.emptyassesment"));
		            return mapping.findForward("back");
		        }
		        assesments.add(assesment);
			}else {
				String[] assessmentStr = createData.getAssesments();
				if(assessmentStr == null || assessmentStr.length == 0) {
		            session.setAttribute("Msg", messages.getText("user.corporations.emptyassesment"));
		            return mapping.findForward("back");
				}
				for(int i = 0; i < assessmentStr.length; i++)
			        assesments.add(assessmentStr[i]);
			}
			result = sys.getUserABMFacade().createUsersFromFile(assesments, role, list, ending, sys.getUserSessionData());
		} else {
			String group = createData.getGroup();
	        if(Util.empty(group)) {
	            session.setAttribute("Msg", messages.getText("user.corporations.emptygroup"));
	            return mapping.findForward("back");
	        }
			result = sys.getUserABMFacade().createUsersFromFile(new Integer(group), list, ending, sys.getUserSessionData());
		}
		
        ArrayList<ArrayList<Object>> sheetResume = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> headerResume = new ArrayList<Object>();
    	headerResume.add(messages.getText("user.data.firstname"));
		headerResume.add(messages.getText("user.data.mail"));
		headerResume.add(messages.getText("user.data.nickname"));
		headerResume.add(messages.getText("user.data.pass"));
		sheetResume.add(headerResume);
		
		Iterator<UserData> iter = result.iterator();
		while(iter.hasNext()) {
			UserData user = iter.next();
			ArrayList<Object> line = new ArrayList<Object>();
			line.add(user.getFirstName()+" "+user.getLastName());
			line.add(user.getEmail());
			line.add(user.getLoginName());
			line.add(user.getPassword());
			
			sheetResume.add(line);
    	}
    	
//		response.setHeader("Content-Type", "application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "inline; filename=\"Driver.xls" + "\"");

//		ExcelGenerator.generatorObjectXLS(sheetResume,messages.getText("generic.messages.resume"),response.getOutputStream());

		sys.setValue(sheetResume);
		return mapping.findForward("success");
    }

	private Collection<UserData> getUsersFromFile(String fileName, UserCreateFileForm createData, int[] varHeaders, AssesmentAccess sys) throws Exception {
		CsvReader reader = new CsvReader(SecurityConstants.FILES_WAR_DIRECTORY + fileName);
		reader.setDelimiter(';');
		Collection<UserData> users = new LinkedList<UserData>();

		String language = createData.getLanguage();
		Integer country = new Integer(createData.getCountry());
        String ending = createData.getExtra();

		reader.readHeaders();

		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -30);

		int userFormat = Integer.parseInt(createData.getUserFormat());
		int line = 2;
		while (reader.readRecord()) {
			String firstName = reader.get(0).trim();
			if(Util.empty(firstName)) {
				throw new Exception("Linea "+line+": nombre vacío.");
			}
			String lastName = reader.get(1).trim();
			if(Util.empty(firstName)) {
				throw new Exception("Linea "+line+": apellido vacío.");
			}
			String email = reader.get(2).trim().toLowerCase();
			String login = null;  
			switch(userFormat) {
				case 1:
					if(Util.empty(email)) {
						throw new Exception("Linea "+line+": email vacío.");
					}
					StringTokenizer tokenizer = new StringTokenizer(email,"@");
					if(tokenizer.hasMoreTokens()) {
						login = tokenizer.nextToken();
					}
					break;
				case 2:
					tokenizer = new StringTokenizer(lastName);
					if(tokenizer.hasMoreTokens()) {
						login = tokenizer.nextToken();
						if(login.length()<=3 && tokenizer.hasMoreTokens()) {
							login += tokenizer.nextToken();
						}
					}
					login = firstName.substring(0,1)+"."+login;
					break;
				case 3:
					tokenizer = new StringTokenizer(lastName);
					if(tokenizer.hasMoreTokens()) {
						login = tokenizer.nextToken();
						if(login.length()<=3 && tokenizer.hasMoreTokens()) {
							login += tokenizer.nextToken();
						}
					}
					tokenizer = new StringTokenizer(firstName);
					login = tokenizer.nextToken()+"."+login;
					break;
			}
			if(Util.empty(login)) {
				throw new Exception("Linea "+line+": no se puede armar el usuario.");
			}else {
				if(!Util.empty(ending))
					login += "."+ending;
				UserData data = new UserData();
				data.setRole(SecurityConstants.ACCESS_TO_SYSTEM);
				data.setLoginName(replaceChars(login.toLowerCase()));
				data.setFirstName(firstName);
				data.setLastName(lastName);
				data.setEmail(email);
				data.setPassword(getPassword());
				Date birth = c.getTime();
				data.setBirthDate(birth);
				data.setSex(new Integer(UserData.MALE));
				data.setCountry(country);
				data.setNationality(country);
				data.setLanguage(language);
				users.add(data);
				for(int i = 3; i < varHeaders.length+3; i++) {
					String value = reader.get(i);
					switch(varHeaders[i-3]) {
						case 1:
							Date birthDate = formatDate(value);
							if(birthDate != null)
								data.setBirthDate(birthDate);
							break;
						case 2:
							if(value.equalsIgnoreCase("f"))
								data.setSex(new Integer(UserData.FEMALE));
							if(value.equalsIgnoreCase("m"))
								data.setSex(new Integer(UserData.MALE));
							break;
						case 3:
							data.setExtraData(value);
							break;
						case 4:
							data.setExtraData2(value);
							break;
						case 5:
							data.setExtraData3(value);
							break;
						case 6:
							if(Util.isNumber(value))
								data.setDatacenter(new Integer(value));
							break;
					}
				}
			}
			line++;
		}
		return users;
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

	private static String getPassword() {
		String randomCharacterSet = "123456789";
		int x = (int) Math.round(Math.random() * (randomCharacterSet.length() - 1));
		String result = String.valueOf(randomCharacterSet.charAt(x));
		randomCharacterSet = "0123456789";
		for (int i = 1; i < 6; i++) {
			x = (int) Math.round(Math.random() * (randomCharacterSet.length() - 1));
			result += randomCharacterSet.charAt(x);
		}
		return result;
	}
	
}
