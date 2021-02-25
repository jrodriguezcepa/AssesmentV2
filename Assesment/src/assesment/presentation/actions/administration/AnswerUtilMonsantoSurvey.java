/**
 * Created on 20-nov-2008
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.administration;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.assesment.FeedbackData;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilMonsantoSurvey extends AnswerUtil {

    public AnswerUtilMonsantoSurvey() {
        super();
    }

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            
            String name = user.getFirstName()+" "+user.getLastName();
            String emailTitle = "Driver Assessment - Monsanto Survey Brasil 2016 - "+name;
            Collection<String> files = new LinkedList<String>();
            String fileName = AssesmentData.FLASH_PATH+"/reports/Reporte_MSBR2016_"+AssesmentData.replaceChars(name)+".pdf";
            util.createPersonalDataReport(sys, fileName, assesment, login);
            files.add(fileName);

            Collection<String> feedbacks = new LinkedList<String>();
			if(doFeedback) {
			    Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
			    if(it.hasNext()) {
			    	email = ((FeedbackData)it.next()).getEmail();
				    while(it.hasNext()) {
				        feedbacks.add(((FeedbackData)it.next()).getEmail());
				    }
			        MailSender sender = new MailSender();
					if(!Util.empty(email)) {
				        boolean sended = false;
				        int count = 10;
				        while(!sended && count > 0) {
				        	try {
				                String[] senderAddress = sender.getAvailableMailAddress();
				                if(!Util.empty(senderAddress[0])) {
				                	sender.send(email,new LinkedList<String>(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,"",files,feedbacks);
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
