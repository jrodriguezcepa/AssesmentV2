/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.BindingProvider;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import ws.elearning.assessment.ElearningWSAssessment;
import ws.elearning.assessment.ElearningWSAssessmentService;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.AccessCodeData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.user.UserData;
import assesment.communication.ws.Identification;
import assesment.communication.ws.UserElearning;
import assesment.communication.ws.UserToRegister;
import assesment.presentation.translator.web.util.AbstractAction;

public class eLearningAction extends AbstractAction {

	private final String WEBSERVICE = "http://et.cepasafedrive.com/WebServices2/ElearningWSAssessment?wsdl";
	private final String URL_REDIRECT = "http://et.cepasafedrive.com/loadAssesment.php";

	public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
		return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        DynaActionForm createData = (DynaActionForm) createForm;
        String loginname = createData.getString("user");
        Integer assesmentId = new Integer(AssesmentData.FACEBOOK);
    	Collection lessons = sys.getTagReportFacade().getAssociatedLessons(loginname,assesmentId,sys.getUserSessionData());
        if(lessons.size() > 0){

        	UserData user = sys.getUserReportFacade().findUserByPrimaryKey(loginname, sys.getUserSessionData());
        	UserToRegister userToRegister = new UserToRegister();
        	String sex = "datatype.sex.male";
        	if (user.getSex() == UserData.FEMALE){
        		 sex = "datatype.sex.female";
        	}
        	Format dateFormat;	            	
        	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	
        	
            UserElearning userElearning = new UserElearning(
            		dateFormat.format(user.getBirthDate()),user.getCountry().intValue(),
        			user.getEmail(), user.getFirstName(), user.getLanguage(),
        			user.getLastName(),sex, 1105);
            userToRegister.setUser(userElearning);
            
            // Lecciones que tomará el usuario:
            LinkedList<Integer> idLessons = new LinkedList<Integer>();	       
            idLessons.addAll(lessons);
            userToRegister.setIdLessons(idLessons);
            
            /* Invocacion al WebService */ 
            ElearningWSAssessmentService service = new ElearningWSAssessmentService();
			ElearningWSAssessment port = service.getElearningWSAssessmentPort();	    			
			String endpointURL = WEBSERVICE;	    			
			BindingProvider bp = (BindingProvider)port;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
			String idString = null;
			
			try{
				idString = port.addUserToCourse(userToRegister.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			/* fin Invocación al WebService*/ 
			
			if (idString != null){
    			Identification identification = new Identification(idString);
    			if (identification != null){
    				String redirect = URL_REDIRECT+"?key="+identification.getKey()+"&id="+identification.getId();
    				Boolean eLearningEnabled = new Boolean(true);
    				sys.getUserABMFacade().setRedirect(user.getLoginName(),redirect,
    						new Integer(identification.getId()),
    						eLearningEnabled,
    						assesmentId, sys.getUserSessionData());
    			}
			}else{
				System.out.println("Error al agregar usuario al Elearning:"+user.getLoginName());
			}
        }
        return mapping.findForward("success");
    }
}

