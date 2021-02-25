/**
 * Created on 14-nov-2007
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.UserAnswerData;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.corporation.CorporationData;
import assesment.communication.exception.InvalidDataException;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.communication.user.UserData;
import assesment.communication.util.CountryConstants;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilCoke extends AnswerUtil {

    public AnswerUtilCoke() {
        super();
    }

    public void sendEmail(AssesmentAccess sys, AssesmentData assesment, String login, String email, boolean doFeedback, UserData user, Collection<String> files) throws Exception,RemoteException {
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
        MailSender sender = new MailSender();
        String emailTitle = messages.getText("assessment.reports") + " - "+assesment.getName() + " - "+ user.getFirstName()+" "+user.getLastName();
		if(files.size() > 0) {
		    String body = "<table>";
		    body += "<tr height=15><td></td></tr>" ;
		    body += "<tr>" ;
		    body += "<td>" ;
		    body += "<img src='cid:figura1' alt=''>" ;
		    body += "<br>" ;
		    body += "</td>" ;
		    body += "</tr>";
			body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
		    body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
		    body += "<tr><td></td><tr>";
		    body += "</table>";
			if(email == null)
				email = (!Util.empty(user.getEmail())) ? user.getEmail() : sys.getQuestionReportFacade().getEmailByQuestion(assesment.getId(),login,messages,userSessionData);
			if(!Util.empty(email)) {
		        boolean sended = false;
		        int count = 10;
		        while(!sended && count > 0) {
		        	try {
		                String[] senderAddress = sender.getAvailableMailAddress();
		                if(!Util.empty(senderAddress[0])) {
		                	sender.sendImage(email,FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,files,body, AssesmentData.FLASH_PATH+"/images/cepa_coke_logos.png");
		                	sended = true;
		                }
		        	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
		        }
			}
			
			if(doFeedback) {
			    Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
			    while(it.hasNext()) {
			        FeedbackData feedback = (FeedbackData)it.next();
			        Iterator reports = feedback.getReports().iterator();
			        if(files.size() > 0) {
			            boolean sended = false;
			            int count = 10;
			            while(!sended && count > 0) {
			            	try {
				                String[] senderAddress = sender.getAvailableMailAddress();
				                if(!Util.empty(senderAddress[0])) {
				                	sender.sendImage(feedback.getEmail(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,files,body, AssesmentData.FLASH_PATH+"/images/cepa_coke_logos.png");
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
		}
	}
}
