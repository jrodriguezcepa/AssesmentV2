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
import assesment.communication.administration.AccessCodeData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.security.SecurityConstants;
import assesment.presentation.translator.web.util.AbstractAction;

public class WebinarAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));

        DynaActionForm createData = (DynaActionForm) createForm;
        String codeStr = createData.getString("code");
        String code = "";
        for(int i = 0; i < codeStr.length(); i++) {
        	char c = codeStr.charAt(i);
        	if(Character.isLetterOrDigit(c)) {
        		code += String.valueOf(c);
        	}
        }
        
        if(code == null || code.trim().length() != 6) {
        	session.setAttribute("Msg", "webinar.error1");
            return mapping.findForward("error");
        }
        code = code.trim().toUpperCase();
        
        boolean server = false;
        Connection connDC = (server || SecurityConstants.isProductionServer()) ? DriverManager.getConnection("jdbc:postgresql://18.229.182.37:5432/datacenter5","postgres","pr0v1s0r1A") : DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
        Statement stDC = connDC.createStatement();

        ResultSet set = stDC.executeQuery("SELECT type, state, corporation FROM cepaactivity WHERE code = '"+code+"'");
        if(set.next()) {
    		String activityType = set.getString(1).toLowerCase();
    		String activityState = set.getString(2).toLowerCase();
    		int corporation = set.getInt(3);
    		if(!activityState.equals("datatype.scheduledactivitystate.prevista")) {
                connDC.close();
            	session.setAttribute("Msg", "webinar.error3");
                return mapping.findForward("error");
    		} else {
	    		session.setAttribute("lng",sys.getUserSessionData().getLenguage());
	    		if(corporation == 108) {
	    			if(activityType.equals("datatype.scheduledactivitytype.teoricobayerpesados")) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BAYER_WEBINARPESADOS));
		        		session.setAttribute("p","97771519f02dab5a6fbc3f9c210020b7");
	    			} else if(activityType.equals("datatype.scheduledactivitytype.teoricobayeragro") || activityType.equals("tablet.form.ebtwplus.onboard")) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BAYER_WEBINARAGRO));
		        		session.setAttribute("p","f1415553382a708f291eed3754b589bd");
	    			} else if(activityType.equals("datatype.scheduledactivitytype.teoricobayerreboque") || activityType.equals("tablet.form.btw1plusonboard")) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BAYER_WEBINARREBOQUE));
		        		session.setAttribute("p","fe88c0a97070cdd04964de5f11ab453f");
	    			} else if(activityType.equals("datatype.scheduledactivitytype.teoricobayerrenovation")) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BAYER_WEBINARRENOVATION));
		        		session.setAttribute("p","65cd9b2a9f3864a5b352a05f51e5bda1");
	    			} else {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BAYER_WEBINARPLUS));
		        		session.setAttribute("p","9fe1fa7b7c70c1a17765ba41734fb41e");
	    			}
	    		} else if(corporation == 4){
		        	if(isBTW2(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.SAFEFLEET_WEBINARPLUS2));
		        		session.setAttribute("p","c00b56fa38f71448376507cbaf9ad9e0");
		        	} else if(isHRD(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.SAFEFLEET_WEBINARHRD));
		        		session.setAttribute("p","f389562427cb0fa9abc8121856adb21e");
		        	} else if(isMonitores(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.SAFEFLEET_WEBINARMONITORS));
		        		session.setAttribute("p","e28c29c4b11799ef64bded2a10ac2c0c");
		        	}else{
		        		session.setAttribute("g", String.valueOf(AssesmentData.SAFEFLEET_WEBINARPLUS));
		        		session.setAttribute("p","f191f888d56163de76d5e4ba455579b3");
		        	}
	    		}else {
		        	if(isPesado(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.PESADOS_WEBINAR));
		        		session.setAttribute("p","592b96e7821e90b057597187b9412635");
		        	} else if(isBTW2(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BTWPLUS2_WEBINAR));
		        		session.setAttribute("p","6e32482c35ee47e1073fcd7ea52022c7");
		        	} else if(isHRD(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.HRD_WEBINAR));
		        		session.setAttribute("p","30bd4f2b4d758a50388e7f3d52216706");
		        	} else if(is4x4(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.WEBINAR_4X4));
		        		session.setAttribute("p","a9ce18229fdcd7dc1a363db7f6c68276");
		        	} else if(isRefresh(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.BTW2_REFRESH_WEBINAR));
		        		session.setAttribute("p","281c8dcf9a15f90bc581fedbc399d5fe");
		        	} else if(isMonitores(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.MONITORES_WEBINAR));
		        		session.setAttribute("p","7188682edca7c522284a66214fbb668b");
		        	} else if(isTritren(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.TRITREN_WEBINAR));
		        		session.setAttribute("p","29573138f81d34ddb6c64976d321faed");
		        	} else if(isMoto(activityType)) {
		        		session.setAttribute("g", String.valueOf(AssesmentData.MOTO_WEBINAR));
		        		session.setAttribute("p","6819ee1417d29913864fdadc708200e8");
		        	}else{
		        		session.setAttribute("g", String.valueOf(AssesmentData.BTWPLUS_WEBINAR));
		        		session.setAttribute("p","424bc7ad54c3c02020fff287374827ae");
		        	}
	    		}
	    		session.setAttribute("c",code);
	    		connDC.close();
	            return mapping.findForward("next");
    		}
        } else {
            connDC.close();
        	session.setAttribute("Msg", "webinar.error2");
            return mapping.findForward("error");
        }
    }

	private boolean isMoto(String activity) {
		return activity.equals("tablet.form.motocycle");
	}

	private boolean isTritren(String activity) {
		return activity.equals("tablet.form.tritren");
	}

	private boolean isMonitores(String activity) {
		return activity.toLowerCase().contains("monitor") || activity.contains("multipli");
	}

	private boolean isPesado(String activity) {
		return activity.contains("heavy") || activity.contains("btw3") || activity.contains("pesado");
	}

	private boolean isBTW2(String activity) {
		return activity.equals("tablet.form.btw2plus.livianos.sinskid") || activity.equals("tablet.form.btw2plusonboard") || activity.equals("tablet.form.btw2pluspista");
	}

	private boolean isHRD(String activity) {
		return activity.contains("hrd");
	}

	private boolean is4x4(String activity) {
		return activity.equals("tablet.form.btwplusagro") || activity.toLowerCase().contains("4x4");
	}

	private boolean isRefresh(String activity) {
		return activity.equals("tablet.form.btw2refreshonboard");
	}
}

