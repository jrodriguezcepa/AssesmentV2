/*
 * Created on 08-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.question;

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
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.AbstractAction;

public class QuestionGroupAction extends AbstractAction {


    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        session.setAttribute("module",((DynaActionForm)form).getString("module"));
        return mapping.findForward("cancel");
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        
        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();
        
        DynaActionForm questionForm = (DynaActionForm)form;
        Integer moduleId = new Integer(questionForm.getString("module"));
        ModuleData module = sys.getModuleReportFacade().findModule(moduleId,sys.getUserSessionData());
        
        Iterator questionIt = module.getQuestionIterator();
        Collection groups = new LinkedList();
        Collection changes = new LinkedList();
        int last = 0;
        while(questionIt.hasNext()) {
            QuestionData question = (QuestionData)questionIt.next();
            int group = Integer.parseInt(request.getParameter("group"+String.valueOf(question.getId())));
            if(last == group) {
                if(question.getGroup() != group) {
                    Integer[] change = {question.getId(),group};
                    changes.add(change);
                }
            }else if(groups.contains(group)) {
                session.setAttribute("Msg",messages.getText("question.group.error"));
                session.setAttribute("module",questionForm.getString("module"));
                return mapping.findForward("back");
            }else {
                if(question.getGroup() != group) {
                    Integer[] change = {question.getId(),group};
                    changes.add(change);
                }
                if(group != 0) {
                    groups.add(group);
                }
            }
            last = group;
        }
        
        sys.getQuestionABMFacade().updateGroups(changes,sys.getUserSessionData(),QuestionData.NORMAL);
        
        session.setAttribute("module",questionForm.getString("module"));
        return mapping.findForward("view");
    }
    
}