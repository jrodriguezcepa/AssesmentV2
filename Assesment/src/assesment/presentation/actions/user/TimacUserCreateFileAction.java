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
import assesment.communication.util.MD5;
import assesment.persistence.util.ExcelGenerator;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

import com.csvreader.CsvReader;

public class TimacUserCreateFileAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

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
			String extraData = reader.get(4).trim();
			String extraData2 = reader.get(5).trim();

			UserData user = new UserData(cpf, new MD5().encriptar(cpf), firstName, lastName, "pt", email, UserData.MULTIASSESSMENT, null);
     		user.setExtraData(extraData);
     		user.setExtraData2(extraData2);

     		users.add(user);
		}

		String[][] result = sys.getUserABMFacade().saveTimacUsers(users, sys.getUserSessionData());
		sys.setValue(result);
		return mapping.findForward("success");
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
