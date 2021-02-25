/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.actions.report.ReportUtil;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserCreateDCAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("home");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

        DynaActionForm createData = (DynaActionForm) createForm;
        String corporation = createData.getString("corporation");
        if(Util.empty(corporation)) {
            session.setAttribute("Msg", messages.getText("user.corporations.emptycorporation"));
            return mapping.findForward("back");
        }
        String assesment = createData.getString("assesment");
        if(Util.empty(assesment)) {
            session.setAttribute("Msg", messages.getText("user.corporations.emptyassesment"));
            return mapping.findForward("back");
        }
        String passwordType = createData.getString("password");
        String passwordText = null;
        if(passwordType.equals("2")) {
            String pass = createData.getString("passwordtext");
            String confirm_pass = createData.getString("rePasswordtext");
            if(Util.empty(pass)) {
                session.setAttribute("Msg", messages.getText("user.corporations.emptypassword"));
                return mapping.findForward("back");
            }
            if(!pass.equals(confirm_pass)) {
                session.setAttribute("Msg", messages.getText("user.corporations.confirmpasswrong"));
                return mapping.findForward("back");
            }
            passwordText = pass;
        }

        Collection list = sys.getUserABMFacade().createUsersFromDC(new Integer(corporation),new Integer(assesment),passwordText,sys.getUserSessionData());
        
        if(createData.getString("generatefile").equals("on")) {
            UserListDataSource dataSource = new UserListDataSource(list);
            
            HashMap parameters = new HashMap();
            parameters.put("Name",messages.getText("user.data.firstname"));
            parameters.put("LastName",messages.getText("user.data.lastname"));
            parameters.put("Email",messages.getText("user.data.mail"));
            parameters.put("User",messages.getText("user.data.nickname"));
            parameters.put("Password",messages.getText("user.data.pass"));
            
            ReportUtil util = new ReportUtil();
            return util.executeReport("jasper/userList.jasper","XLS",parameters,dataSource,session,mapping,response,"userList");
        }
        return mapping.findForward("success");
    }
}

class UserListDataSource implements JRDataSource {

    private Iterator it;
    private UserData userData;
    
    public UserListDataSource(Collection list) {
        it = list.iterator();
    }

    public boolean next() throws JRException {
        if(it.hasNext()) {
            userData = (UserData)it.next();
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("Name")) {
            return userData.getFirstName();
        }
        if(field.getName().equals("LastName")) {
            return userData.getLastName();
        }
        if(field.getName().equals("Email")) {
            return userData.getEmail();
        }
        if(field.getName().equals("User")) {
            return userData.getLoginName();
        }
        if(field.getName().equals("Password")) {
            return userData.getPassword();
        }
        return null;
    }
    
}