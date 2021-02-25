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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.presentation.translator.web.util.AbstractAction;

public class AlRiyadahAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        DynaActionForm createData = (DynaActionForm) createForm;
        String codeStr = createData.getString("code");
		String type = createData.getString("type");

		String code = "";
        for(int i = 0; i < codeStr.length(); i++) {
        	char c = codeStr.charAt(i);
        	if(Character.isLetterOrDigit(c)) {
        		code += String.valueOf(c);
        	}
        }

        if(code == null || code.trim().length() != 6) {
        	session.setAttribute("Msg", "webinar.error1");
            return mapping.findForward("error"+type);
        }
        code = code.trim().toUpperCase();

        Connection connDC = (SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://18.229.182.37:5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
        Statement stDC = connDC.createStatement();

        ResultSet set = stDC.executeQuery("SELECT driver, firstname, lastname FROM cepaactivity ca JOIN activityregistry ar ON ar.activity = ca.activityid JOIN drivers d ON d.id = ar.driver WHERE code = '"+code+"' AND ca.corporation = 110");
        if(set.next()) {

    		String driver = set.getString(1);
    		String firstName = set.getString(2);
    		String lastName = set.getString(3);
    		
    		Integer assessmentId = null;
    		switch(Integer.parseInt(type)) {
    			case 1:
    				assessmentId = AssesmentData.ALRIYADAH_INITIALA;
    				break;
    			case 2:
    				assessmentId = AssesmentData.ALRIYADAH_INITIALB;
    				break;
    			case 3:
    				assessmentId = AssesmentData.ALRIYADAH_FINAL;
    				break;
    		}
    		
    		String userName = "alriyadah_"+type+"_"+driver;
    		String password = new MD5().encriptar(userName);
    		
    		if(!sys.getUserReportFacade().userExist(userName, sys.getUserSessionData())) {
    			UserData userData = new UserData(userName,password,firstName,lastName,"en","cepasafedrive@gmail.com","systemaccess",null);
    			userData.setDatacenter(new Integer(driver));
    			sys.getUserABMFacade().userCreate(userData,assessmentId,sys.getUserSessionData());
    		}
    		session.setAttribute("user", userName);
    		session.setAttribute("password", password);
	        return mapping.findForward("next");
        } else {
            connDC.close();
        	session.setAttribute("Msg", "webinar.error2");
            return mapping.findForward("error"+type);
        }
    }
}

