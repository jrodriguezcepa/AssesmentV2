/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.corporation.CediAttributes;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class CediAsociateAssessmentAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("next");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        DynaActionForm createData = (DynaActionForm) createForm;
        Collection users = Util.stringToCollection(createData.getString("list"));
        int type = Integer.parseInt(createData.getString("type"));
    	sys.getUserABMFacade().userCediAsociate(users, type, sys.getUserSessionData());
      
    	if(type>=3) {
            Collection<String> files = new LinkedList<String>();

	        Iterator it = users.iterator();
	        HashMap<String, ArrayList<UserData>> managers = new HashMap<String, ArrayList<UserData>>();
	        while (it.hasNext()) {
	        	String userName = (String)it.next();
	        	UserData user = sys.getUserReportFacade().findUserByPrimaryKey(userName, sys.getUserSessionData());
	        	CediAttributes cedi=sys.getCorporationReportFacade().findCediAttributes(user.getLocation(), sys.getUserSessionData());
	        	StringTokenizer cediManagerT = new StringTokenizer(cedi.getLoginName(), ",");
	        	while(cediManagerT.hasMoreTokens()) {
	        		String cediManager = cediManagerT.nextToken().trim();
		        	ArrayList<UserData> managerUsers = managers.get(cediManager);
		        	if(managerUsers!=null) {
			        	managerUsers.add(user);
		        	}else {
		        		managerUsers = new ArrayList<UserData>();
		        		managerUsers.add(user);
		        	}
		        	managers.put(cediManager, managerUsers);
	        	}
	        }
	        Set<String> keys = managers.keySet();
       
	    	for(String i: keys) {
	        	UserData manager = sys.getUserReportFacade().findUserByPrimaryKey(i, sys.getUserSessionData());
	        	if(manager.getEmail() != null) {
		    		String body = "<table>";
		    	    body += "<tr><td>eBTW asociados: </td><tr>";	 
		    		for (UserData j: managers.get(i)) {
		        	    body += "<tr><td></td><tr>";
		        	    body += "<tr><td>Nombre: "+ j.getFirstName() + " "+j.getLastName() +" </td><tr>";
		        	    body += "<tr><td>Usuario: "+ j.getLoginName() +" </td><tr>";
		        	    body += "<tr><td></td><tr>";
	
		    		}
		    	    body += "</table>";
		    		sendEmail(null,sys,body,"eBTW asociados",manager.getEmail(),files,null);
	        	}
	    		//sendEmail(null,sys,body,"eBTW asociados","crodriguez@cepasafedrive.com",files,null);
	    	}	
    	}
    	return mapping.findForward("next");
     }

    
	protected void sendEmail(UserData user, AssesmentAccess sys, String body, String emailTitle, String email, Collection<String> files, String image) throws Exception {
        MailSender sender = new MailSender();
        Collection toMail = new LinkedList();
        Collection<String> attachments = new LinkedList<String>();
        Collection<String> names = new LinkedList<String>();
        Collection<String> bccMail = new LinkedList<String>();
        Text messages = sys.getText();
        UserSessionData userSessionData = sys.getUserSessionData();
    	if(!Util.empty(email)) {
            boolean sended = false;
            int count = 10;
            while(!sended && count > 0) {
            	try {
	                String[] senderAddress = sender.getAvailableMailAddress();
	                if(!Util.empty(senderAddress[0])) {
	                	sender.sendImageMandrill(toMail,"eBTW+ asociados", "support@cepasafedrive.com",
	            				"eBTW asociados asunto", body, attachments,
	            				 names, "support@cepasafedrive.com", "", bccMail,  null);
	                	//sender.send(email,"CEPA Mobility Care",senderAddress[0],senderAddress[1],emailTitle,body,files);
	                	sended = true;
	                }
            	}catch (Exception e) {
					e.printStackTrace();
				}
	            count--;
            }
    	}
	}


}