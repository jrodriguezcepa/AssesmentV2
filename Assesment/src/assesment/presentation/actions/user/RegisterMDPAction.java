/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class RegisterMDPAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        DynaActionForm createData = (DynaActionForm) createForm;
        String code = createData.getString("code");
        int action = Integer.parseInt(createData.getString("action"));
        if(code == null || code.trim().length() == 0) {
            return mapping.findForward("error");
        }

        String user = "";
        for(int i = 0; i < code.length(); i++) {
        	String s = String.valueOf(code.charAt(i));
        	if(Util.isNumber(s)) 
        		user += s;
        }
        
        UserSessionData userSessionData = sys.getUserSessionData();
        if(user == null || !(user.length() >= 7 && user.length() <= 8)) {
        	session.setAttribute("Msg", "Formato o largo de C.I. incorrecto");
        	return mapping.findForward("error");
        }else {
        	Connection connDC = (SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://18.229.182.37:5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
        	Statement stDC = connDC.createStatement();
	
        	ResultSet set = stDC.executeQuery("SELECT d.id, d.firstname, d.lastname, d1.resourcekey FROM drivers d JOIN divorgitemlevel1s d1 on d1.id = d.divorg1 WHERE d.corporation = 50 AND TRIM(d.corporationid) = '"+code+"'");
	    	UserData userData = new UserData();
	    	 
	    	String login = "charla_"+AssesmentData.MDP_CHARLA+"_"+user;
	    	if(sys.getUserReportFacade().userExist(login, userSessionData)) {
	    		session.setAttribute("Msg", "Usuario ya registrado");
	            return mapping.findForward("error");
	        }else {
	        	userData.setLoginName(login);
		        userData.setPassword(login);
		        Calendar c = Calendar.getInstance();
		        c.add(Calendar.YEAR, -30);
		        userData.setBirthDate(c.getTime());
		        userData.setLanguage("es");
		        userData.setEmail("cepasafedrive@gmail.com");
		        userData.setCountry(31);
		        userData.setRole(UserData.SYSTEMACCESS);
		        userData.setExtraData(user);
	        	 if(set.next()) {
	        		 userData.setDatacenter(set.getInt(1));
	        		 userData.setFirstName(set.getString(2));
	        		 userData.setLastName(set.getString(3));
	        		 userData.setExtraData2(set.getString(4));
	        	 }else {
	 		        if(action == 0) {
	 		        	return mapping.findForward("register");
		        	 }else {
		        		 String v = createData.getString("firstName");
		        		 if(Util.empty(v)) {
		        			 session.setAttribute("Msg", "Ingrese Nombre");
		        			 return mapping.findForward("register");
		        		 }
		        		 userData.setFirstName(v);
		        		 v = createData.getString("lastName");
		        		 if(Util.empty(v)) {
		        			 session.setAttribute("Msg", "Ingrese Apellido");
		        			 return mapping.findForward("register");
		        		 }
		        		 userData.setLastName(v);
		        		 v = createData.getString("company");
		        		 if(Util.empty(v)) {
		        			 session.setAttribute("Msg", "Seleccione Contratista");
		        			 return mapping.findForward("register");
		        		 }
		        		 userData.setExtraData2(v);
		        	 }
		        }
		        sys.getUserABMFacade().userCreate(userData, AssesmentData.MDP_CHARLA, userSessionData);
		        session.setAttribute("registereduser", login);
	        	connDC.close();
	        }
	   	 	return mapping.findForward("next");
        }
    }
}

