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

public class AnswerUtilElearningPackBayerMX extends AnswerUtil {

    public AnswerUtilElearningPackBayerMX() {
        super();
    }

    public void newFeedback(AssesmentAccess sys,AssesmentData assesment, String login, String email, boolean doFeedback, String redirect) throws Exception {
        UsReportFacade userReport = sys.getUserReportFacade();
        UserSessionData userSessionData = sys.getUserSessionData();
        assesment.presentation.actions.report.Util util = new assesment.presentation.actions.report.Util();
        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
            UserData user = userReport.findUserByPrimaryKey(login,userSessionData);
            Collection<String> files = new LinkedList<String>();
            
            String name = user.getFirstName()+" "+user.getLastName();
            email = user.getEmail();
            boolean green = userReport.isResultGreen(login, assesment.getId(), userSessionData);
          /*  
            String fileName = AssesmentData.FLASH_PATH+"/reports/Reporte_eLearningPack_"+"_"+AssesmentData.replaceChars(name)+".pdf";
            util.createTotalReport(fileName,user,assesment,sys);
            files.add(fileName);
            */
            Collection<String> feedbacks = new LinkedList<String>();
            if(green) {
            	/*fileName = AssesmentData.FLASH_PATH+"/reports/Certificado_eLearningPack_"+"_"+AssesmentData.replaceChars(name)+".pdf";
                util.createCertificate(fileName,user,assesment,sys);
                files.add(fileName);*/
            } else {
            	if(assesment.getId().intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_V2) {
            		sys.getUserABMFacade().associateAssesment(login, new Integer(AssesmentData.BAYERMX_ELEARNINGPACK_V2_REPETICION), userSessionData);
            	}else if(assesment.getId().intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2) {
            		sys.getUserABMFacade().associateAssesment(login, new Integer(AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2_REPETICION), userSessionData);
            	}else if(assesment.getId().intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_MONITORES) {
            		sys.getUserABMFacade().associateAssesment(login, new Integer(AssesmentData.BAYERMX_ELEARNINGPACK_MONITORES_REPETICION), userSessionData);
            	}
            }
/*
		    Iterator it = sys.getAssesmentReportFacade().getAssesmentFeedback(assesment.getId(),userSessionData).iterator();
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
		                	sender.sendImage(email, FROM_NAME, senderAddress[0], senderAddress[1], emailTitle, files, getBody(name, green, assesment.getId()), AssesmentData.FLASH_PATH+"/images/logo_road_safety.png", feedbacks);
		                	sended = true;
		                }
		        	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
		        }
			}*/
        }
    }

	private String getBody(String name, boolean green, int id) {
		String body = "<html>" +
		"	<head>" +
		"	</head>" +
		"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
		"		<table>" +
		"			<tr>" +
		"				<td>" +
		"					<span style='font-family:Verdana; font-size:10;'><b>Estimado/a " +Util.formatHTML(name)+",</b></span>"+
		"				</td>" +
		"			</tr>";
		body +=	"<tr><td>&nbsp;</tr></tr>" ;
		if(green) {
			body +=	"			<tr>" +
			"				<td>" +
			"					Adjuntamos reporte de evaluaci&oacute;n del eLearning Pack 2016, <b>felicitaciones su calificaci&oacute;n fue mayor al 80% por lo que su entrenamiento ha sido aprobado</b> " +
			"					 y tendr&aacute; el beneficio de una reducci&oacute;n de 5pts en su CRC (Calificaci&oacute;n de Riesgo del Conductor). " +
			"				</td>" +
			"			</tr>";
		} else {
			if(id == AssesmentData.BAYERMX_ELEARNINGPACK_V2 || id == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2) {
				body +=	"			<tr>" +
				"				<td>" +
				"					Adjuntamos reporte de evaluaci&oacute;n del eLearning Pack 2016, <b>su calificaci&oacute;n fue menor al 80% y tiene habilitada una segunda oportunidad para realizarlo con &eacute;xito.</b><br> " +
				"					Por favor, vuelva a ingresar a <a href='https://www.cepada.com'>www.cepada.com</a> <b>con el mismo usuario/contrase&ntilde;a</b> y aproveche al m&aacute;ximo la oportunidad, le recordamos que la fecha l&iacute;mite para realizarlo es el d&iacute;a 16 de Septiembre a las 5:00 pm " +
				"				</td>" +
				"			</tr>";
			} else {
				body +=	"			<tr>" +
				"				<td>" +
				"					Se adjunta su reporte de evaluaci&oacute;n del eLearning Pack 2016, <b>su calificaci&oacute;n nuevamente fue menor al 80% usted tendr&aacute; otra oportunidad para completar la actividad con &eacute;xito.</b><br> " +
				"					En breve recibir&aacute; un nuevo usuario y clave desde el correo electrónico indesa@cepasafedrive.com, le recordamos que la fecha l&iacute;mite para realizarlo es el d&iacute;a 16 de Septiembre a las 5:00 pm " +
				"				</td>" +
				"			</tr>";
			}
		}
		body +=	"<tr><td>&nbsp;</tr></tr>" +
		"<tr><td>Atte.<br> Road Safety Program</tr></tr>" +
		"		<tr>" +
		"				<td>" +
		"					<img src=\"cid:figura1\" alt=\"\">" +
		"				</td>" +
		"			</tr>" +
		"		</table>" +
		"	</body>" +
		"</html>";

		return body;
	}
}
