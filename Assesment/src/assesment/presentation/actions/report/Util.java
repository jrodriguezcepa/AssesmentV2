/**
 * Created on 01-abr-2008
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import assesment.business.AssesmentAccess;
import assesment.business.AssesmentAccessRemote;
import assesment.business.administration.user.UsReportFacade;
import assesment.business.assesment.AssesmentReportFacade;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.report.ErrorReportDataSource;
import assesment.communication.report.ModuleReportDataSource;
import assesment.communication.report.NewHirePersonalDataReportDataSource;
import assesment.communication.report.PsiReportDataSource;
import assesment.communication.report.TotalReportDataSource;
import assesment.communication.report.TotalUserResultReportDataSource;
import assesment.communication.report.UserPersonalDataReportDataSource;
import assesment.communication.report.UserPsiReportDataSource;
import assesment.communication.report.UserResultReportDataSource;
import assesment.communication.report.UserWrongAnswerReportDataSource;
import assesment.communication.user.UserData;
import assesment.communication.util.MailSender;

public class Util {

	public static final String FONT_REPORT_NAME = AssesmentData.RSMM_PATH+"/fonts/HelveticaNeue-LightCond.otf";

	public Util() {
        super();
    }
    
    public byte[] read(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        }finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    public FileOutputStream createPsiReport(AssesmentAccess sys,String fileName,AssesmentAttributes assesment, String login) throws Exception {
        
        Text messages = sys.getText();
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(assesment.getId(),sys.getUserSessionData());
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login,sys.getUserSessionData());
        UserPsiReportDataSource dataSource = new UserPsiReportDataSource(sys.getUserReportFacade().getUserPsicoResult(userData.getLoginName(),assesment.getId(),sys.getUserSessionData()),messages);
        
        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(userData.getLoginName(),assesment.getId(),sys.getUserSessionData());
        double average1 = 0;
        double average2 = 0;
        for(int i = 0; i < 3; i++) {
            average1 = average1+values[i];
        }
        for(int i = 3; i < 6; i++) {
            average2 = average2+values[i];
        }
        average1 = average1 / 3.0;
        average2 = average2 / 3.0;

        HashMap parameters = new HashMap();
        parameters.put("Title",messages.getText("report.psiresults.title"));
        parameters.put("average1",new Double(average1));
        parameters.put("average2",new Double(average2));
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Average1Text1",messages.getText("generic.assessment.average1text1"));
        parameters.put("Average1Text2",messages.getText("generic.assessment.average1text2"));
        parameters.put("Average2Text1",messages.getText("generic.assessment.average2text1"));
        parameters.put("Average2Text2",messages.getText("generic.assessment.average2text2"));
        parameters.put("ResultText",messages.getText("generic.assessment.psiresult"));
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesment.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        ReportUtil util = new ReportUtil();

        String report = "userPsiPDFJJ";
        if(AssesmentData.isJJ(assesment.getId().intValue())) {
            parameters.put("ReferenceText",messages.getText("report.userresult.reference")+": "+getReference(userData,sys.getUserReportFacade(),sys.getUserSessionData()));
            if(dataSource.getIndexCount() == 0) {
                parameters.put("allOK","true");
            }
            parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
            parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        }

        FileInputStream input = new FileInputStream(AssesmentData.JASPER_PATH +  report+"_2.jasper");
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input);

        FileOutputStream out = new FileOutputStream(new File(fileName));
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

    public FileOutputStream createUserReport(AssesmentAccess sys,String fileName,AssesmentAttributes assesment, String login) throws Exception {
        
        Text messages = sys.getText();
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(assesment.getId(),sys.getUserSessionData());
        
        UsReportFacade userReport = sys.getUserReportFacade();
        UserData userData = userReport.findUserByPrimaryKey(login,sys.getUserSessionData());
        UserResultReportDataSource moduleDS = userReport.findUsersResult(assesment.getId(),login,sys.getUserSessionData(),messages);
        String subReportName = "userResultModulePDF.jasper"; 
        if(assesment.getId().intValue() == AssesmentData.ABITAB) {
        	subReportName = "userResultModulePDFAbitab.jasper";
        }
        FileInputStream input = new FileInputStream(AssesmentData.JASPER_PATH +  subReportName);
        JasperReport subreport = (JasperReport)JRLoader.loadObject(input);

        TotalUserResultReportDataSource dataSource = new TotalUserResultReportDataSource(moduleDS.getRed(),moduleDS.getGreen(),messages);
        
        HashMap parameters = new HashMap();
        parameters.put("moduleDS",moduleDS);
        parameters.put("subreport",subreport);
        parameters.put("user",login);
        parameters.put("Title",messages.getText("report.userresult.title"));
        parameters.put("Module"," "+messages.getText("report.userresult.module"));
        parameters.put("ModuleTitle",messages.getText("report.userresult.moduletitle"));
        parameters.put("RightText"," "+messages.getText("report.userresult.right"));
        parameters.put("WrongText"," "+messages.getText("report.userresult.wrong"));
        parameters.put("RightC",dataSource.getRightAnswers());
        parameters.put("WrongC",dataSource.getWrongAnswers());
        parameters.put("RightP",dataSource.getRightPercent());
        parameters.put("WrongP",dataSource.getWrongPercent());
        parameters.put("Right"," "+messages.getText("report.userresult.right"));
        parameters.put("Wrong"," "+messages.getText("report.userresult.wrong"));
        parameters.put("Percent",messages.getText("report.userresult.percent"));
        parameters.put("Count",messages.getText("question.resultreport.count"));
        parameters.put("Total",messages.getText("report.generalresult.total"));
        parameters.put("Background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        String report = "userResultPDF.jasper";
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        switch(assesment.getId().intValue()) {
        	case AssesmentData.JJ: case AssesmentData.JJ_2: case AssesmentData.JJ_3: 
        	case AssesmentData.JJ_4: case AssesmentData.JJ_5: case AssesmentData.JJ_6:
        	case AssesmentData.JJ_7: case AssesmentData.JJ_8:
        		report = "userResultPDFJJ.jasper";
        		parameters.put("ReferenceText",messages.getText("report.userresult.reference")+": "+getReference(userData,userReport,sys.getUserSessionData()));
        		parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
        		parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        		break;
        	case AssesmentData.JANSSEN:
        		report = "userResultPDFJJ.jasper";
        		parameters.put("ReferenceText","");
        		parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
        		parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        		break;
        	case AssesmentData.ABITAB:
        		report = "userResultPDFAbitab.jasper";
        		break;
        }
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesment.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        FileInputStream input2 = new FileInputStream(AssesmentData.JASPER_PATH +  report);
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input2);

        FileOutputStream out = new FileOutputStream(new File(fileName));
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

    private String getReference(UserData user, UsReportFacade userReport, UserSessionData userSessionData) throws Exception {
        Integer code = userReport.getUserCode(user.getLoginName(),userSessionData);
        switch(Integer.parseInt(user.getExtraData())) {
            case 4460:
                return "VK"+String.valueOf(3000+code.intValue());
            case 4461:
                return "CN"+String.valueOf(2000+code.intValue());
            case 4462:
                return "MD&D"+String.valueOf(1000+code.intValue());
            case 4463:
                return "Janse"+String.valueOf(4000+code.intValue());
        }
        return null;
    }

    public FileOutputStream createUserErrorReport(AssesmentAccess sys,String fileName,AssesmentAttributes assesment, String login) throws Exception {
        
        Text messages = sys.getText();
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(assesment.getId(),sys.getUserSessionData());
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login,sys.getUserSessionData());

        String[][] data = sys.getQuestionReportFacade().getWrongAnswers(assesment.getId(),login,sys.getUserSessionData());
        UserWrongAnswerReportDataSource moduleDS = new UserWrongAnswerReportDataSource(data,messages);
        
        FileInputStream input = new FileInputStream(AssesmentData.JASPER_PATH +  "errorModulePDF.jasper");
        JasperReport subreport = (JasperReport)JRLoader.loadObject(input);

        HashMap parameters = new HashMap();
        parameters.put("moduleDS",moduleDS);
        parameters.put("subreport",subreport);
        parameters.put("user",login);
        parameters.put("Title",messages.getText("report.usererror.title"));
        parameters.put("ModuleText"," "+messages.getText("report.userresult.module"));
        parameters.put("RedMessage1"," "+messages.getText("report.usererror.redmessage1"));
        parameters.put("RedMessage2",messages.getText("report.usererror.redmessage2"));
        parameters.put("GreenMessage1"," "+messages.getText("report.usererror.greenmessage1"));
        parameters.put("GreenMessage2",messages.getText("report.usererror.greenmessage2"));
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("Background",AssesmentData.FLASH_PATH+"/images/13_1.jpg");
        String report = "userErrorPDF.jasper";
        switch(assesment.getId().intValue()) {
        	case AssesmentData.JJ:  case AssesmentData.JJ_2: case AssesmentData.JJ_3: 
        	case AssesmentData.JJ_4: case AssesmentData.JJ_5: case AssesmentData.JJ_6:
        	case AssesmentData.JJ_7: case AssesmentData.JJ_8:
        		report = "userErrorPDFJJ.jasper";
        		parameters.put("ReferenceText",messages.getText("report.userresult.reference")+": "+getReference(userData,sys.getUserReportFacade(),sys.getUserSessionData()));
        		parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
        		parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        		break;
        	case AssesmentData.JANSSEN:
        		report = "userErrorPDFJJ.jasper";
        		parameters.put("ReferenceText","");
        		parameters.put("FooterText1",messages.getText("assesment.report.footermessage1"));
        		parameters.put("FooterText2",messages.getText("assesment.report.footermessage2"));
        		break;
        	case AssesmentData.ABITAB:
        		report = "userErrorPDFAbitab.jasper";
        		break;
        }
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesment.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        
        FileInputStream input2 = new FileInputStream(AssesmentData.JASPER_PATH +  report);
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input2);

        FileOutputStream out = new FileOutputStream(new File(fileName));
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }
    
    public FileOutputStream createCertificate(String fileName,UserData user, AssesmentAttributes assessment, AssesmentAccess sys) throws Exception {
        Text messages = sys.getText();
        HashMap<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("User",user.getFirstName()+" "+user.getLastName());
        Calendar c = Calendar.getInstance();
        parameters.put("DateText",Util.dateToString(messages,c,sys.getUserSessionData().getLenguage()));
        
        String reportFileName = "";
        int assessmentId = assessment.getId().intValue();
        if(assessment.isCertificate()) {
        	String certificateImage = assessment.getCertificateImage(sys.getUserSessionData().getLenguage());
        	if(certificateImage == null) {
        		certificateImage = "certificate_default_"+sys.getUserSessionData().getLenguage()+".jpg";
        	}
   			parameters.put("Background",AssesmentData.FLASH_PATH+"/images/"+certificateImage);
    		reportFileName = "certificadoMonsantoBR.jasper";
        }else {
	        if(assessmentId == AssesmentData.ALLERGAN_2) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificateAllergan.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        	
	        }else if(AssesmentData.isJJ(assessmentId)) {
	        	String reference = messages.getText("report.userresult.reference")+": "+getReference(user,sys.getUserReportFacade(),sys.getUserSessionData());
	        	parameters.put("Reference",reference);
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_"+sys.getUserSessionData().getLenguage()+".jpg");
	        	reportFileName = "certificadoJJ.jasper";
	        	
	        }else if(assessmentId == AssesmentData.JANSSEN
	        		|| assessmentId == AssesmentData.JANSSEN_2) {
	            	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Janssen.jpg");
	            	reportFileName = "certificadoJJ.jasper";
	            	
	        }else if(assessmentId == AssesmentData.MICHELIN ||
	        		assessmentId == AssesmentData.MICHELIN_2 ||
	        		assessmentId == AssesmentData.MICHELIN_3 || 
	        		assessmentId == AssesmentData.MICHELIN_4 ||
	        		assessmentId == AssesmentData.MICHELIN_5 ||
	        		assessmentId == AssesmentData.MICHELIN_6 ||
	        		assessmentId == AssesmentData.MICHELIN_7 ||
	        		assessmentId == AssesmentData.MICHELIN_8 ||
	        		assessmentId == AssesmentData.MICHELIN_9
	        		) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Michelin.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.MICHELIN_7) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Michelin_sinISO.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.UNILEVER) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_unilever_uy_es.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.SANOFI_BRASIL) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/diploma_pt_sanofi.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.SCHERING) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Schering.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.MONSANTO_LAN) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_MonsantoLAN.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.MAMUT_ANDINO) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_MamutAndino.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.NALCO) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_nalco.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.PEPSICO || assessmentId == AssesmentData.PEPSICO_CANDIDATOS || assessmentId == AssesmentData.PEPSICO_CEPA_SYSTEM) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Pepsico.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.BAYER) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Bayer.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.DNB) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_DNB.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.TRANSMETA) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Transmeta.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.IMESEVI) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_imesevi.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.ASTRAZENECA
	        		|| assessmentId == AssesmentData.ASTRAZENECA_2
	        		 || assessmentId == AssesmentData.ASTRAZENECA_2013) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_AZ_es.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.NYCOMED) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Nycomed.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.ANGLO || assessmentId == AssesmentData.ANGLO_3) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_Anglo.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else if(assessmentId == AssesmentData.DOW) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_DOW.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }else {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificate_default.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        }
        }
    	FileInputStream input = new FileInputStream(AssesmentData.JASPER_PATH +  reportFileName);
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input);
        FileOutputStream out = new FileOutputStream(new File(fileName));
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

    public FileOutputStream createTotalReport(String fileName,UserData user, AssesmentData assessment, AssesmentAccess sys) throws Exception {
    	

        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
    	String[] reportData = sys.getAssesmentReportFacade().findReportData(assessment.getId(),userSessionData);
        
        UsReportFacade usReport = sys.getUserReportFacade();
        HashMap map = usReport.findUsersResults(assessment.getId(), user.getLoginName(), userSessionData);
        
        String subreportName = (assessment.getModules().size() > 4) ? "ModuleSubreport5.jasper" : "ModuleSubreport.jasper";
        FileInputStream input =  new FileInputStream(new File(AssesmentData.JASPER_PATH +  subreportName));
        //JasperCompileManager.compileReportToFile("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jrxml","C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jasper");
  //      FileInputStream input = new FileInputStream("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jasper");
        JasperReport moduleSubreport = (JasperReport)JRLoader.loadObject(input);
        ModuleReportDataSource moduleDataSource = new ModuleReportDataSource(map.size());
        Iterator it = assessment.getModuleIterator();
        Integer correctas = new Integer(0);
        Integer incorrectas = new Integer(0);
        while(it.hasNext()) {
        	ModuleData module = (ModuleData)it.next();
        	if(map.containsKey(module.getId())) {
        		Object[] data = (Object[])map.get(module.getId());
        		incorrectas += (Integer)data[1];
        		correctas += (Integer)data[2];
        		moduleDataSource.addModule(messages.getText(module.getKey()), (Integer)data[2], (Integer)data[1]);
        	}
        }
        
        TotalReportDataSource totalDataSource = new TotalReportDataSource(correctas,incorrectas,messages);
        
        HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assessment.getCorporationId())+".png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.jpg");
		if(messages.getText(assessment.getName()).toLowerCase().contains("elearning")) {
			params.put("header", AssesmentData.FLASH_PATH+"/images/headerEP.jpg");
		}else {
			params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		}
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("corporacion", messages.getText("generic.corporation") + ": " + messages.getText(assessment.getName()));
		params.put("nombre", messages.getText("generic.assesment") + ": " + messages.getText(assessment.getName()) );
		params.put("usuario", messages.getText("user.data.nickname") + ": " + user.getFirstName() + " " + user.getLastName());
		params.put("fecha", Util.dateToString(messages,Calendar.getInstance(),user.getLanguage()));
		params.put("titulo_resultado", messages.getText("generic.user.results").toUpperCase());
		params.put("cantidad", messages.getText("question.resultreport.count").toUpperCase());
		params.put("porcentaje", messages.getText("report.userresult.percent").toUpperCase());
		params.put("correctas", messages.getText("report.userresult.right").toUpperCase());
		params.put("incorrectas", messages.getText("report.userresult.wrong").toUpperCase());
		params.put("cantidad_correctas", correctas);
		params.put("cantidad_incorrectas", incorrectas);
		params.put("porcentaje_correctas", totalDataSource.getPorcentaje_correctas());
		params.put("porcentaje_incorrectas", totalDataSource.getPorcentaje_incorrectas());
		params.put("subreport1", moduleDataSource);
		params.put("sub1", moduleSubreport);
		params.put("resultados_modulo",messages.getText("report.userresult.moduletitle").toUpperCase());
		params.put("total",messages.getText("report.users.total.count"));
		params.put("modulo",messages.getText("generic.module"));

		String[][] data = sys.getQuestionReportFacade().getWrongAnswers(assessment.getId(),user.getLoginName(),userSessionData);

		int indexPSI = 3;
        if(data.length > 0) {
            FileInputStream input2 = new FileInputStream(new File(AssesmentData.JASPER_PATH +  "ErrorSubreport.jasper"));
            JasperReport errorSubreport = (JasperReport)JRLoader.loadObject(input2);
            ErrorReportDataSource errorDatasource = new ErrorReportDataSource(data,messages);
			params.put("subreport2", errorDatasource);
			params.put("sub2", errorSubreport);
			params.put("errores",messages.getText("user.report.errortitle"));
			params.put("imagen_rojo", AssesmentData.FLASH_PATH+"/images/imagen_error.png");
			params.put("imagen_verde", AssesmentData.FLASH_PATH+"/images/imagen_correcto.png");
			params.put("texto_rojo",messages.getText("user.report.redtext"));
			params.put("texto_verde",messages.getText("user.report.greentext"));
        }else {
    		indexPSI = 2;
        }
        
        if(assessment.isPsitest()) {
        	if(assessment.getId().equals(AssesmentData.BAYER_MEXICO)) {
    	        //JasperCompileManager.compileReportToFile("C:\\Proyectos JAVA\\Assesment\\src\\assesment\\presentation\\actions\\report\\jrxml\\PsiSubreportBayer.jrxml", "C:\\Proyectos JAVA\\Assesment\\src\\assesment\\presentation\\actions\\report\\jasper\\PsiSubreportBayer.jasper");
		        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreportBayer.jasper");
		        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
		        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(user.getLoginName(),assessment.getId(),userSessionData);
		        PsiReportDataSource psiDatasource = new PsiReportDataSource(values,messages,"bayer.error.text");
				params.put("psi_titulo",messages.getText("bayer.report.psititle"));
				params.put("psi_texto1",messages.getText("psi.error.title0"));
				params.put("psi_texto2",messages.getText("user.report.psitexto2"));
				params.put("psi_texto21",messages.getText("user.report.psitexto21"));
				params.put("psi_texto7",messages.getText("user.report.psitexto7"));
				params.put("psi_texto71",messages.getText("user.report.psitexto71"));
				params.put("sub"+indexPSI, psiSubreport);
				params.put("subreport"+indexPSI, psiDatasource);
        	}else {
		        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreport.jasper");
		        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
		        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(user.getLoginName(),assessment.getId(),userSessionData);
		        double average1 = 0;
		        double average2 = 0;
		        for(int i = 0; i < 3; i++) {
		            average1 = average1+values[i];
		        }
		        for(int i = 3; i < 6; i++) {
		            average2 = average2+values[i];
		        }
		        average1 = average1 / 3.0;
		        average2 = average2 / 3.0;
		        PsiReportDataSource psiDatasource = new PsiReportDataSource(values,messages);
		
				params.put("psi1",new Double(average1));
				params.put("psi2",new Double(average2));
				params.put("psi_titulo",messages.getText("user.report.psititle"));
				params.put("psi_subtitulo1",messages.getText("generic.assessment.average1text1")+" "+messages.getText("generic.assessment.average1text2"));
				params.put("psi_subtitulo2",messages.getText("generic.assessment.average2text1")+" "+messages.getText("generic.assessment.average2text2"));
				params.put("psi_texto1",messages.getText("psi.error.title0"));
				params.put("psi_texto2",messages.getText("user.report.psitexto2"));
				params.put("psi_texto21",messages.getText("user.report.psitexto21"));
				params.put("psi_texto3",messages.getText("user.report.psitexto3"));
				params.put("psi_texto4",messages.getText("user.report.psitexto4"));
				params.put("psi_texto5",messages.getText("user.report.psitexto5"));
				params.put("psi_texto6",messages.getText("user.report.psitexto6"));
				params.put("psi_texto7",messages.getText("user.report.psitexto7"));
				params.put("psi_texto71",messages.getText("user.report.psitexto71"));
				params.put("sub"+indexPSI, psiSubreport);
				params.put("subreport"+indexPSI, psiDatasource);
        	}
        }

        String principalFileName = AssesmentData.JASPER_PATH+"TotalReport.jasper";
        if(assessment.getModules().size() == 1) {
        	principalFileName = AssesmentData.JASPER_PATH +  "TotalReport1.jasper";
        }
        if(assessment.getModules().size() > 4) {
        	principalFileName = AssesmentData.JASPER_PATH +  "TotalReport5.jasper";
        }
		FileInputStream finalInput = new FileInputStream(new File(principalFileName));
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(finalInput);
        FileOutputStream out = new FileOutputStream(new File(fileName));
        
//        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
 //       params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, totalDataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

 public FileOutputStream createTotalReport(String fileName,UserData user, AssesmentData assessment, AssesmentAccessRemote sys) throws Exception {
    	

        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
    	String[] reportData = sys.getAssesmentReportFacade().findReportData(assessment.getId(),userSessionData);
        
        UsReportFacade usReport = sys.getUserReportFacade();
        HashMap map = usReport.findUsersResults(assessment.getId(), user.getLoginName(), userSessionData);
        
        System.out.println("Production server "+MailSender.isProductionServer());
        String subreportName = (assessment.getModules().size() > 4) ? "ModuleSubreport5.jasper" : "ModuleSubreport.jasper";
        FileInputStream input =  new FileInputStream(new File(AssesmentData.JASPER_PATH +  subreportName));
        //JasperCompileManager.compileReportToFile("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jrxml","C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jasper");
  //      FileInputStream input = new FileInputStream("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ModuleSubreport.jasper");
        JasperReport moduleSubreport = (JasperReport)JRLoader.loadObject(input);
        ModuleReportDataSource moduleDataSource = new ModuleReportDataSource(map.size());
        Iterator it = assessment.getModuleIterator();
        Integer correctas = new Integer(0);
        Integer incorrectas = new Integer(0);
        while(it.hasNext()) {
        	ModuleData module = (ModuleData)it.next();
        	if(map.containsKey(module.getId())) {
        		Object[] data = (Object[])map.get(module.getId());
        		incorrectas += (Integer)data[1];
        		correctas += (Integer)data[2];
        		moduleDataSource.addModule(messages.getText(module.getKey()), (Integer)data[2], (Integer)data[1]);
        	}
        }
        
        TotalReportDataSource totalDataSource = new TotalReportDataSource(correctas,incorrectas,messages);
        
        HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assessment.getCorporationId())+".png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("corporacion", messages.getText("generic.corporation") + ": " + messages.getText(assessment.getName()));
		params.put("nombre", messages.getText("generic.assesment") + ": " + messages.getText(assessment.getName()));
		params.put("usuario", messages.getText("user.data.nickname") + ": " + user.getFirstName() + " " + user.getLastName());
		params.put("fecha", Util.dateToString(messages,Calendar.getInstance(),user.getLanguage()));
		params.put("titulo_resultado", messages.getText("generic.user.results").toUpperCase());
		params.put("cantidad", messages.getText("question.resultreport.count").toUpperCase());
		params.put("porcentaje", messages.getText("report.userresult.percent").toUpperCase());
		params.put("correctas", messages.getText("report.userresult.right").toUpperCase());
		params.put("incorrectas", messages.getText("report.userresult.wrong").toUpperCase());
		params.put("cantidad_correctas", correctas);
		params.put("cantidad_incorrectas", incorrectas);
		params.put("porcentaje_correctas", totalDataSource.getPorcentaje_correctas());
		params.put("porcentaje_incorrectas", totalDataSource.getPorcentaje_incorrectas());
		params.put("subreport1", moduleDataSource);
		params.put("sub1", moduleSubreport);
		params.put("resultados_modulo",messages.getText("report.userresult.moduletitle").toUpperCase());
		params.put("total",messages.getText("report.users.total.count"));
		params.put("modulo",messages.getText("generic.module"));

		String[][] data = sys.getQuestionReportFacade().getWrongAnswers(assessment.getId(),user.getLoginName(),userSessionData);

		int indexPSI = 3;
        if(data.length > 0) {
            FileInputStream input2 = new FileInputStream(new File(AssesmentData.JASPER_PATH +  "ErrorSubreport.jasper"));
            JasperReport errorSubreport = (JasperReport)JRLoader.loadObject(input2);
            ErrorReportDataSource errorDatasource = new ErrorReportDataSource(data,messages);
			params.put("subreport2", errorDatasource);
			params.put("sub2", errorSubreport);
			params.put("errores",messages.getText("user.report.errortitle"));
			params.put("imagen_rojo", AssesmentData.FLASH_PATH+"/images/imagen_error.png");
			params.put("imagen_verde", AssesmentData.FLASH_PATH+"/images/imagen_correcto.png");
			params.put("texto_rojo",messages.getText("user.report.redtext"));
			params.put("texto_verde",messages.getText("user.report.greentext"));
        }else {
    		indexPSI = 2;
        }
        
        if(assessment.isPsitest()) {
        	if(assessment.getId().equals(AssesmentData.BAYER_MEXICO)) {
    	        //JasperCompileManager.compileReportToFile("C:\\Proyectos JAVA\\Assesment\\src\\assesment\\presentation\\actions\\report\\jrxml\\PsiSubreportBayer.jrxml", "C:\\Proyectos JAVA\\Assesment\\src\\assesment\\presentation\\actions\\report\\jasper\\PsiSubreportBayer.jasper");
		        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreportBayer.jasper");
		        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
		        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(user.getLoginName(),assessment.getId(),userSessionData);
		        PsiReportDataSource psiDatasource = new PsiReportDataSource(values,messages,"bayer.error.text");
				params.put("psi_titulo",messages.getText("bayer.report.psititle"));
				params.put("psi_texto1",messages.getText("psi.error.title0"));
				params.put("psi_texto2",messages.getText("user.report.psitexto2"));
				params.put("psi_texto21",messages.getText("user.report.psitexto21"));
				params.put("psi_texto7",messages.getText("user.report.psitexto7"));
				params.put("psi_texto71",messages.getText("user.report.psitexto71"));
				params.put("sub"+indexPSI, psiSubreport);
				params.put("subreport"+indexPSI, psiDatasource);
        	}else {
		        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreport.jasper");
		        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
		        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(user.getLoginName(),assessment.getId(),userSessionData);
		        double average1 = 0;
		        double average2 = 0;
		        for(int i = 0; i < 3; i++) {
		            average1 = average1+values[i];
		        }
		        for(int i = 3; i < 6; i++) {
		            average2 = average2+values[i];
		        }
		        average1 = average1 / 3.0;
		        average2 = average2 / 3.0;
		        PsiReportDataSource psiDatasource = new PsiReportDataSource(values,messages);
		
				params.put("psi1",new Double(average1));
				params.put("psi2",new Double(average2));
				params.put("psi_titulo",messages.getText("user.report.psititle"));
				params.put("psi_subtitulo1",messages.getText("generic.assessment.average1text1")+" "+messages.getText("generic.assessment.average1text2"));
				params.put("psi_subtitulo2",messages.getText("generic.assessment.average2text1")+" "+messages.getText("generic.assessment.average2text2"));
				params.put("psi_texto1",messages.getText("psi.error.title0"));
				params.put("psi_texto2",messages.getText("user.report.psitexto2"));
				params.put("psi_texto21",messages.getText("user.report.psitexto21"));
				params.put("psi_texto3",messages.getText("user.report.psitexto3"));
				params.put("psi_texto4",messages.getText("user.report.psitexto4"));
				params.put("psi_texto5",messages.getText("user.report.psitexto5"));
				params.put("psi_texto6",messages.getText("user.report.psitexto6"));
				params.put("psi_texto7",messages.getText("user.report.psitexto7"));
				params.put("psi_texto71",messages.getText("user.report.psitexto71"));
				params.put("sub"+indexPSI, psiSubreport);
				params.put("subreport"+indexPSI, psiDatasource);
        	}
        }
        
        String principalFileName = AssesmentData.JASPER_PATH+"TotalReport.jasper";
        if(assessment.getModules().size() == 1) {
        	principalFileName = AssesmentData.JASPER_PATH +  "TotalReport1.jasper";
        }
        if(assessment.getModules().size() > 4) {
        	principalFileName = AssesmentData.JASPER_PATH +  "TotalReport5.jasper";
        }
		FileInputStream finalInput = new FileInputStream(principalFileName);
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(finalInput);
        FileOutputStream out = new FileOutputStream(new File(fileName));
        
//        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
 //       params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, totalDataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

 
    public FileOutputStream createNewHireReport(String fileName,UserData user, AssesmentData assessment, int[] resultValues, AssesmentAccess sys) throws Exception {
        UserSessionData userSessionData = sys.getUserSessionData();
        Text messages = sys.getText();
    	String[] reportData = sys.getAssesmentReportFacade().findReportData(assessment.getId(),userSessionData);
        
        UsReportFacade usReport = sys.getUserReportFacade();
        HashMap map = usReport.findUsersResults(assessment.getId(), user.getLoginName(), userSessionData);
        
        Iterator it = assessment.getModuleIterator();
        Integer correctas = new Integer(0);
        Integer incorrectas = new Integer(0);
        while(it.hasNext()) {
        	ModuleData module = (ModuleData)it.next();
        	if(map.containsKey(module.getId())) {
        		Object[] data = (Object[])map.get(module.getId());
        		incorrectas += (Integer)data[1];
        		correctas += (Integer)data[2];
        	}
        }
        
        TotalReportDataSource totalDataSource = new TotalReportDataSource(correctas,incorrectas,messages);
        
        HashMap<String,Object> params = new HashMap<String,Object>();
        
        params.put("titulo_personal", "Driver Profile");
        params.put("semaforo_personal", AssesmentData.FLASH_PATH+"/images/semaforo.png");
        if(resultValues[0] <= 4) {
            params.put("flecha_azul_personal", AssesmentData.FLASH_PATH+"/images/flecha.png");
        } else if(resultValues[0] <= 8) {
            params.put("flecha_naranja_personal", AssesmentData.FLASH_PATH+"/images/flecha.png");
        } else {
            params.put("flecha_roja_personal", AssesmentData.FLASH_PATH+"/images/flecha.png");
        }
        
        params.put("titulo_cuestionario", "Basic Safe Driving Test");
        params.put("semaforo_cuestionario", AssesmentData.FLASH_PATH+"/images/semaforo.png");
        if(resultValues[1] <= 4) {
            params.put("flecha_azul_cuestionario", AssesmentData.FLASH_PATH+"/images/flecha.png");
        } else if(resultValues[1] <= 8) {
            params.put("flecha_naranja_cuestionario", AssesmentData.FLASH_PATH+"/images/flecha.png");
        } else {
            params.put("flecha_roja_cuestionario", AssesmentData.FLASH_PATH+"/images/flecha.png");
        }

        params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assessment.getCorporationId())+".png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("corporacion", messages.getText("generic.corporation") + ": " + assessment.getName());
		params.put("nombre", messages.getText("generic.assesment") + ": " + assessment.getName() );
		params.put("usuario", messages.getText("user.data.nickname") + ": " + user.getFirstName() + " " + user.getLastName());
		params.put("fecha", Util.dateToString(messages,Calendar.getInstance(),user.getLanguage()));
		params.put("titulo_resultado", "Basic Safe Driving Test Results");
		params.put("cantidad", messages.getText("question.resultreport.count").toUpperCase());
		params.put("porcentaje", messages.getText("report.userresult.percent").toUpperCase());
		params.put("correctas", messages.getText("report.userresult.right").toUpperCase());
		params.put("incorrectas", messages.getText("report.userresult.wrong").toUpperCase());
		params.put("cantidad_correctas", correctas);
		params.put("cantidad_incorrectas", incorrectas);
		params.put("porcentaje_correctas", totalDataSource.getPorcentaje_correctas());
		params.put("porcentaje_incorrectas", totalDataSource.getPorcentaje_incorrectas());
		params.put("resultados_modulo",messages.getText("report.userresult.moduletitle").toUpperCase());
		params.put("total",messages.getText("report.users.total.count"));
		params.put("modulo",messages.getText("generic.module"));

		String[][] data = sys.getQuestionReportFacade().getWrongAnswers(assessment.getId(),user.getLoginName(),userSessionData);

		int indexPSI = 2;
        if(data.length > 0) {
            FileInputStream input2 = new FileInputStream(AssesmentData.JASPER_PATH +  "ErrorNewHireSubreport.jasper");
            JasperReport errorSubreport = (JasperReport)JRLoader.loadObject(input2);
    		//JasperCompileManager.compileReportToFile("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ErrorNewHireSubreport.jrxml", "C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jasper/ErrorNewHireSubreport.jasper");
    		//FileInputStream finalInput = new FileInputStream(new File("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/ErrorNewHireSubreport.jrxml"));
            //JasperReport errorSubreport = JasperCompileManager.compileReport(finalInput);

            ErrorReportDataSource errorDatasource = new ErrorReportDataSource(data,messages);
			params.put("subreport1", errorDatasource);
			params.put("sub1", errorSubreport);
			params.put("errores",messages.getText("user.report.errortitle"));
			params.put("imagen_rojo", AssesmentData.FLASH_PATH+"/images/imagen_error_rojo2.png");
			params.put("imagen_verde", AssesmentData.FLASH_PATH+"/images/imagen_correcto-azul.jpg");
			params.put("texto_rojo",messages.getText("user.report.redtext"));
			params.put("texto_verde",messages.getText("user.report.bluetext"));
        }else {
    		indexPSI = 1;
        }
        
        if(assessment.isPsitest()) {
	        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreport.jasper");
	        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
	        Integer[] values = sys.getUserReportFacade().getUserPsicoResult(user.getLoginName(),assessment.getId(),userSessionData);
	        double average1 = 0;
	        double average2 = 0;
	        for(int i = 0; i < 3; i++) {
	            average1 = average1+values[i];
	        }
	        for(int i = 3; i < 6; i++) {
	            average2 = average2+values[i];
	        }
	        average1 = average1 / 3.0;
	        average2 = average2 / 3.0;
	        PsiReportDataSource psiDatasource = new PsiReportDataSource(values,messages);
	
			params.put("psi1",new Double(average1));
			params.put("psi2",new Double(average2));
			params.put("psi_titulo",messages.getText("user.report.psititle"));
			params.put("psi_subtitulo1",messages.getText("generic.assessment.average1text1")+" "+messages.getText("generic.assessment.average1text2"));
			params.put("psi_subtitulo2",messages.getText("generic.assessment.average2text1")+" "+messages.getText("generic.assessment.average2text2"));
			params.put("psi_texto1",messages.getText("psi.error.title0"));
			params.put("psi_texto2",messages.getText("user.report.psitexto2"));
			params.put("psi_texto21",messages.getText("user.report.psitexto21"));
			params.put("psi_texto3",messages.getText("user.report.psitexto3"));
			params.put("psi_texto4",messages.getText("user.report.psitexto4"));
			params.put("psi_texto5",messages.getText("user.report.psitexto5"));
			params.put("psi_texto6",messages.getText("user.report.psitexto6"));
			params.put("psi_texto7",messages.getText("user.report.psitexto7"));
			params.put("psi_texto71",messages.getText("user.report.psitexto71"));
			params.put("sub"+indexPSI, psiSubreport);
			params.put("subreport"+indexPSI, psiDatasource);
        }
        
		//FileInputStream finalInput = new FileInputStream(new File("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/NewHireReport.jrxml"));
        //JasperReport jasperReport = JasperCompileManager.compileReport(finalInput);
		//JasperCompileManager.compileReportToFile("C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jrxml/NewHireReport.jrxml", "C:/Proyectos JAVA/Assesment/src/assesment/presentation/actions/report/jasper/NewHireReport.jasper");
		FileInputStream finalInput = new FileInputStream(AssesmentData.JASPER_PATH +  "NewHireReport.jasper");
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(finalInput);
        
		FileOutputStream out = new FileOutputStream(new File(fileName));
        
//        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
 //       params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, totalDataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

    public static String dateToString(Text messages, Calendar date, String language) {
        StringBuffer buffer = new StringBuffer();
        if(language.equals("en")) {
            buffer.append(messages.getText("generic.month."+String.valueOf(date.get(Calendar.MONTH)+1))).append(" ").append(date.get(Calendar.DATE)).append(", ").append(" ").append(date.get(Calendar.YEAR));
        }else {
            buffer.append(date.get(Calendar.DATE)).append(" ").append(messages.getText("generic.messages.of")).append(" ").append(messages.getText("generic.month."+String.valueOf(date.get(Calendar.MONTH)+1))).append(" ").append(messages.getText("generic.messages.of")).append(" ").append(date.get(Calendar.YEAR));
        }
        return buffer.toString();
    }

    public void createPersonalDataReport(AssesmentAccess sys, String fileName,AssesmentAttributes assesment, String login) throws Exception {
        Text messages = sys.getText();
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login,sys.getUserSessionData());
        String[] reportData = sys.getAssesmentReportFacade().findReportData(assesment.getId(),sys.getUserSessionData());
        
        ModuleData module = sys.getModuleReportFacade().getPersonalDataModule(assesment.getId(),sys.getUserSessionData());
        Collection answers = sys.getUserReportFacade().findModuleResult(userData.getLoginName(),module.getId(),sys.getUserSessionData());
        
        
        HashMap<String,Object> parameters = new HashMap<String,Object>();
        JRDataSource dataSource = new UserPersonalDataReportDataSource(module,answers,messages);
        
        FileInputStream input = new FileInputStream(AssesmentData.JASPER_PATH +  "subPersonalDataPDF.jasper");
        JasperReport subReport = (JasperReport)JRLoader.loadObject(input);
        
        parameters.put("datasource",dataSource);
        parameters.put("subreport",subReport);
        parameters.put("Title",messages.getText("driver.result.personaldata"));
        parameters.put("UserText",messages.getText("report.userresult.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("Logo",AssesmentData.FLASH_PATH+"/images/logo"+String.valueOf(assesment.getCorporationId())+".jpg");
        parameters.put("LogoCEPA",AssesmentData.FLASH_PATH+"/images/logo-cepa.jpg");
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));

        FileInputStream input2 = new FileInputStream(AssesmentData.JASPER_PATH +  "PersonalDataReportPDF.jasper");
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input2);

        FileOutputStream out = new FileOutputStream(new File(fileName));
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
    }

    public void createNewHireReport(AssesmentAccess sys, String fileName, AssesmentAttributes assesment, String login) throws Exception {
        Text messages = sys.getText();
        AssesmentReportFacade assesmentReport = sys.getAssesmentReportFacade();
        
        String[] reportData = sys.getAssesmentReportFacade().findReportData(assesment.getId(),sys.getUserSessionData());
        
        UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(login,sys.getUserSessionData());
        
        String[][] data = sys.getQuestionReportFacade().getWrongAnswers(assesment.getId(),userData.getLoginName(),sys.getUserSessionData());
        Integer total = sys.getAssesmentReportFacade().getAssesmentsQuestionCount(assesment,sys.getUserSessionData(),false);

        Integer newHire = assesmentReport.getNewHireValue(userData.getLoginName(),String.valueOf(assesment.getId()),sys.getUserSessionData());
        
        UserWrongAnswerReportDataSource dataSource3 = new UserWrongAnswerReportDataSource(data,messages);
        FileInputStream input2 = new FileInputStream(AssesmentData.JASPER_PATH +  "newHireModulePDF_"+sys.getUserSessionData().getLenguage()+".jasper");
        JasperReport subreport2 = (JasperReport)JRLoader.loadObject(input2);

        TotalUserResultReportDataSource dataSource2 = new TotalUserResultReportDataSource(dataSource3.getTotal(),total - dataSource3.getTotal(),messages);
        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "newHireSubreport1PDF_"+sys.getUserSessionData().getLenguage()+".jasper");
        JasperReport subreport1 = (JasperReport)JRLoader.loadObject(input3);

        
        String module0Text = "";
        String semaphore = "";
        if(newHire <= 5) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_verde.gif";
            module0Text = "report.newhire.reporttext8";
        }else if(newHire == 6) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_verde.gif";
            module0Text = "report.newhire.reporttext7";
        }else if(newHire == 7) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_verde.gif";
            module0Text = "report.newhire.reporttext6";
        }else if(newHire == 8) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_amarilla.gif";
            module0Text = "report.newhire.reporttext5";
        }else if(newHire == 9) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_amarilla.gif";
            module0Text = "report.newhire.reporttext4";
        }else if(newHire == 10) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_roja.gif";
            module0Text = "report.newhire.reporttext3";
        }else if(newHire == 11) {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_roja.gif";
            module0Text = "report.newhire.reporttext2";
        }else {
            semaphore = AssesmentData.FLASH_PATH+"/images/luz_roja.gif";
            module0Text = "report.newhire.reporttext1";
        }
        
        ModuleData module = sys.getModuleReportFacade().getPersonalDataModule(assesment.getId(),sys.getUserSessionData());
        Collection answers = sys.getUserReportFacade().findModuleResult(userData.getLoginName(),module.getId(),sys.getUserSessionData());
        
        NewHirePersonalDataReportDataSource dataSource1 = new NewHirePersonalDataReportDataSource(module,answers,messages);

        HashMap parameters = new HashMap();
        parameters.put("UserText",messages.getText("report.newhire.user")+": "+userData.getFirstName()+" "+userData.getLastName());
        parameters.put("Date",Util.dateToString(messages,Calendar.getInstance(),sys.getUserSessionData().getLenguage()));
        parameters.put("CorporationText",messages.getText("generic.data.corporation")+": "+reportData[1]);
        parameters.put("AssessmentText",messages.getText("generic.assesment")+": "+reportData[0]);
        
        parameters.put("Module0Text",messages.getText("report.newhire.estimatedrisk")+" "+String.valueOf(newHire));
        parameters.put("subreport1",subreport1);
        parameters.put("dataSource1",dataSource2);
        parameters.put("subreport2",subreport2);
        parameters.put("dataSource2",dataSource3);
        parameters.put("ResultText",messages.getText(module0Text));
        parameters.put("RightC",dataSource2.getRightAnswers());
        parameters.put("WrongC",dataSource2.getWrongAnswers());
        parameters.put("RightP",dataSource2.getRightPercent());
        parameters.put("WrongP",dataSource2.getWrongPercent());
        parameters.put("semaphore",semaphore);
        String image = (assesment.getId().intValue() == AssesmentData.NEW_HIRE) ? AssesmentData.FLASH_PATH+"/images/fondo_blanco.jpg" : AssesmentData.FLASH_PATH+"/images/fondo_blanco_Monsanto.jpg";
        parameters.put("background",image);
        
        FileInputStream input1 = new FileInputStream(AssesmentData.JASPER_PATH +  "TotalnewHireReportPDF_"+sys.getUserSessionData().getLenguage()+".jasper");
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input1);

        FileOutputStream out = new FileOutputStream(new File(fileName));
        
        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
        parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource1);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
    }

    public static String formatDate(Date date) {
        if(date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
    }

	public static String parseString(String word) {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); 
            if(Character.isLetter(c) || Character.isDigit(c)) {
            	result.append(c);
            }else {
            	result.append("_");
            }
        }
        return result.toString();
	}

	public static String getTimacCPF(String code) {
		String v = new String(code);
		while(v.length() < 11)
			v = "0"+v;
		return v;
	}
}
