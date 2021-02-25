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

public class AnswerUtilNewHire2 extends AnswerUtil {

    public AnswerUtilNewHire2() {
        super();
    }

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            int[] values = getValue(sys.getQuestionReportFacade().getOptionAnswers(assesment.getId(), login, userSessionData));
            
            String name = user.getFirstName()+" "+user.getLastName();
            Collection<String> files = new LinkedList<String>();
            if(assesment.isReportFeedback()) {
                String fileName = AssesmentData.FLASH_PATH+"/reports/"+AssesmentData.getFileName(assesment.getId(),userSessionData.getLenguage(),name,1)+".pdf";
                util.createNewHireReport(fileName,user,assesment,values,sys);
                files.add(fileName);
            }

            Collection<String> feedbacks = new LinkedList<String>();
			if(doFeedback) {
			    Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
			    while(it.hasNext()) {
			        feedbacks.add(((FeedbackData)it.next()).getEmail());
			    }
			}
			
            sendEmail(sys, assesment, login, email, doFeedback, user, files, files, redirect, feedbacks);
        }
    }

    private int[] getValue(Integer[][] answers) {
    	int x = 0;
    	int y = 0;
    	for(int i = 0; i < answers.length; i++) {
    		int questionId = answers[i][0].intValue();
    		int answer = answers[i][1].intValue();
	    	switch(questionId) {
	            case 15613: 
	            	switch(answer) {		
	            		case 55092: 
	            			x += 2; 
	            			break;
	            		case 55093: 
	            			x += 1; 
	            			break;
	            		case 55091: 
	            			x += 0; 
	            			break;
	            		case 55094: 
	            			x += 2; 
	            			break;
	            	}
	            	break;
	            case 15614: 
	            	switch(answer) {
	            		case 55103: 
	            			x += 2; 
	            			break;
	            		case 55101: 
	            			x += 2; 
	            			break;
	            		case 55095: 
	            			x += 2; 
	            			break;
	            		case 55099: 
	            			x += 2; 
	            			break;
	            		case 55098: 
	            			x += 1; 
	            			break;
	            		case 55096: 
	            			x += 1; 
	            			break;
	            		case 55102: 
	            			x += 1; 
	            			break;
	            		case 55100: 
	            			x += 1; 
	            			break;
	            		case 55097: 
	            			x += 1; 
	            			break;
	            	}
	            	break;
	            case 15615: 
	            	switch(answer) {
	            		case 55104: 
	            			x += 2; 
	            			break;
	            		case 55106: 
	            			x += 1; 
	            			break;
	            		case 55105: 
	            			x += 0; 
	            			break;
	            	}
	            	break;	
	            case 15616: 
	            	switch(answer) {
	            		case 55107: 
	            			x += 0; 
	            			break;
	            		case 55109: 
	            			x += 1; 
	            			break;
	            		case 55108: 
	            			x += 2; 
	            			break;
	            	}
	            	break;
	            case 15617: 
	            	switch(answer) {
	            		case 55115: 
	            			x += 1; 
	            			break;
	            		case 55112: 
	            			x += 1; 
	            			break;
	            		case 55114: 
	            			x += 1; 
	            			break;
	            		case 55110: 
	            			x += 1; 
	            			break;
	            		case 55113: 
	            			x += 1; 
	            			break;
	            		case 55111: 
	            			x += 1; 
	            			break;
	            		case 55116: 
	            			x += 1; 
	            			break;
	            		case 55117: 
	            			x += 2; 
	            			break;
	            	}
	            	break;
	            case 15618: 
	            	switch(answer) {
	            		case 55119: 
	            			x += 0; 
	            			break;
	            		case 55118: 
	            			x += 1; 
	            			break;
	            	}
	            case 15619: 
	            	switch(answer) {
	            		case 55126: 
	            			x += 0; 
	            			break;
	            		case 55120: 
	            			x += 1; 
	            			break;
	            		case 55121: 
	            			x += 1; 
	            			break;
	            		case 55123: 
	            			x += 2; 
	            			break;
	            		case 55122: 
	            			x += 2; 
	            			break;
	            		case 55125: 
	            			x += 2; 
	            			break;
	            		case 55124: 
	            			x += 2; 
	            			break;
	            	}
	            	break;
	            case 15620: 
	            	switch(answer) {
	            		case 55128: 
	            			x += 0; 
	            			break;
	            		case 55130: 
	            			x += 1; 
	            			break;
	            		case 55129: 
	            			x += 2; 
	            			break;
	            		case 55127: 
	            			x += 2; 
	            			break;
	            	}
	            	break;
	            default:
	            	y += answers[i][2].intValue();
	    	}    
    	}
		return new int[]{x,y};
	}
}
