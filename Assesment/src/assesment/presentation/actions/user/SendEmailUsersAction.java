/*
 * Created on 10-may-2017
 *
 */
package assesment.presentation.actions.user;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.util.MailSender;
import assesment.persistence.user.tables.UserPassword;
import assesment.presentation.translator.web.util.AbstractAction;

public class SendEmailUsersAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        DynaActionForm createData = (DynaActionForm) createForm;
        String stringList = (String) createData.get("users");
        Collection<String> users = stringToCollection(stringList);
        Iterator<String> iterator = users.iterator();
        while(iterator.hasNext()) {
        	String dataUser = iterator.next();
        	UserPassword usPass = dataToVariables(dataUser);
        	MailSender sender = new MailSender();
            String emailTitle = "usuario creado"; 
    		String body = "Se ha creado un nuevo usuario con usuario: "+usPass.getLoginName()+" \n password: "+usPass.getPassword();
    		String[] senderAddress = sender.getAvailableMailAddress();
    		sender.send(usPass.getEmail(), "CEPA - Driver Assessment", senderAddress[0], senderAddress[1], emailTitle, body);

    		usPass.setMailSended(true);
            sys.getUserABMFacade().createUserPassword(usPass);
        }

        return mapping.findForward("success");
    }

    private Collection<String> stringToCollection(String stringList){
        String[] stringArray=stringList.split("<");
        Collection<String> col=new LinkedList<String>();
        
        for(int i=0; i < stringArray.length; i++){
            col.add(stringArray[i]);
        }
        
        return col;
    }

    private UserPassword dataToVariables(String dataUser){
    	UserPassword userPassword = new UserPassword();
    	
    	String[] stringArray = dataUser.split("/");
        userPassword.setLoginName(stringArray[0]);
        userPassword.setEmail(stringArray[1]);
        userPassword.setPassword(stringArray[2]);
        userPassword.setFirstname(stringArray[3]);
        userPassword.setLastname(stringArray[4]);
        
        return userPassword;
    }
}
