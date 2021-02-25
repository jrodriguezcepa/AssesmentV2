package assesment.presentation.actions.administration;

import java.util.LinkedList;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.util.MailSender;
import assesment.presentation.translator.web.util.Util;

public class AnswerUtilDOWTOJ extends AnswerUtil {

    public AnswerUtilDOWTOJ() {
        super();
    }

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UserSessionData userSessionData = sys.getUserSessionData();
        
        UsReportFacade userReport = sys.getUserReportFacade();
        
        Text messages = sys.getText();
        String body = "<table cellpadding=5 cellspacing=5>";
        String emailTitle = "AVALIACAO DO TREINAMENTO TOJ (DOW)"; 
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
        	String[][] values = sys.getQuestionReportFacade().getCompleteAnswers(assesment.getId(), login, userSessionData);
            MailSender sender = new MailSender();
            if(values != null) {
	            for(int i = 0; i < values.length; i++) {
		            body += "<tr>";
		            body += "<td align='left'><span style='font-family: Helvetica;'><b>"+Util.emailTranslation(messages.getText(values[i][0]))+":</b>&nbsp;";
		            body += Util.emailTranslation(messages.getText(values[i][1]))+"</span></td>";
		            body += "</tr>";
	            }
	            body += "</table>";
	            boolean sended = false;
	            int count = 10;
	            while(!sended && count > 0) {
	            	try {
		                String[] senderAddress = sender.getAvailableMailAddress();
		                if(!Util.empty(senderAddress[0])) {
		                	String emailDOW = (SecurityConstants.isProductionServer()) ? "dow@cepadc.com" : "jrodriguez@cepasafedrive.com";
		                	sender.send(emailDOW,new LinkedList<String>(),FROM_NAME,senderAddress[0],senderAddress[1],emailTitle,body,new LinkedList<String>(),new LinkedList<String>());
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
