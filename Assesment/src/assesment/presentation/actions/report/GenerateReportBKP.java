package assesment.presentation.actions.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

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
import assesment.ReporteANTP;
import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.report.ErrorReportDataSource;
import assesment.communication.report.ErrorReportDataSourceBKP;
import assesment.communication.report.LogisticaReportDataSource;
import assesment.communication.report.ModuleReportDataSource;
import assesment.communication.report.ModuleSurveyReportDataSource;
import assesment.communication.report.PsiReportDataSource;
import assesment.communication.report.TotalReportDataSource;
import assesment.communication.security.SecurityConstants;
import assesment.communication.util.MailSender;

public class GenerateReportBKP {

	private AssesmentAccess sys;
	private String sendId;
	
	public GenerateReportBKP() {
	}


	public GenerateReportBKP(AssesmentAccess sys, String user,int assessment,  HashMap messagesbkp, int type) {
		try {
			this.sys = sys;
	    	Class.forName("org.postgresql.Driver");
	    	Connection connDA = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");

	        Statement st0 = connDA.createStatement();
	        Statement st1 = connDA.createStatement();
	        Statement st2 = connDA.createStatement();

	        Text messages = sys.getText();
	        
	        ResultSet set = st1.executeQuery("select firstname, lastname, language, email, datacenter from users where loginname = '"+user+"'");
	    	set.next();
	    	String userName = set.getString(1).trim().toUpperCase()+" "+set.getString(2).trim().toUpperCase();
	    	String language = set.getString(3);    	

	    	set = st1.executeQuery("select a.name, c.name, count(*), c.id, a.psitest, a.reportfeedback,a.certificate,a.certificateimage"+language+",a.green,a.yellow,a.dcactivity,c.dc5id from assesmentsbkp a join corporations c on c.id = a.corporation join modulesbkp m on m.assesment = a.id where a.id = "+assessment+" group by a.name, c.name, c.id, a.psitest, a.reportfeedback,a.certificate,a.certificateimage"+language+",a.green,a.yellow,a.dcactivity,c.dc5id");
	    	set.next();
	    	String assessmentName = (String)messagesbkp.get(set.getString(1));
	    	int moduleCount = set.getInt(3);
	    	String corporationId = set.getString(4);
	    	boolean psi = set.getBoolean(5);
	    	boolean reportFeedback = set.getBoolean(6);
	    	boolean certificate = set.getBoolean(7);
	    	String certificateImage = set.getString(8);
	    	if(certificateImage == null) {
	    		certificateImage = "certificate_default_"+language+".jpg";
	    	}
	    	int green = set.getInt(9);
	    	int yellow = set.getInt(10);
	    	String dcActivity = set.getString(11);
	    	String dcId = set.getString(12);
            
        	String sql = "select a.type, count(*), ua.enddate, ua.elearningredirect " +
        			"from userassesmentsbkp ua, useranswersbkp uas, answerdatabkp ad, questionsbkp q, answersbkp a " +
        			"where ua.loginname = '"+user+"' " +
        			"and ua.assesment = " + assessment + " " +
        			"and uas.assesment = ua.assesment " +
        			"and uas.loginname = ua.loginname " +
        			"and ad.id = uas.answer " +
        			"and q.id = ad.question " +
        			"and a.id = ad.answer " +
        			"and q.testtype = 1 " +
        			"group by a.type, ua.enddate, ua.elearningredirect";
        	int[] values = {0,0};
        	set = st1.executeQuery(sql);
        	String redirect = null;
        	Calendar enddate = Calendar.getInstance();
        	while(set.next()) {
        		values[set.getInt(1)] = set.getInt(2);
        		enddate.setTime(set.getDate(3));
        		redirect = set.getString(4);
        	}
        	
        	sendId = parseString(assessmentName+"_"+userName);


	        if(type == 1) {
	        	String fileName = AssesmentData.REPORT_PATH + "DA_Report_"+sendId+".pdf";
	        	createTotalReport(enddate, messages, messagesbkp, st1, fileName, user, new Integer(assessment), assessmentName, userName, moduleCount, psi, corporationId);
	        }else {
	        	String fileName = AssesmentData.REPORT_PATH + "DA_Certificate_"+sendId+".pdf";
	        	createCertificate(enddate, messages,  fileName, user, assessment, userName, certificateImage);
	        }

	        st0.close();
	        st1.close();
	        st2.close();
	        
	        connDA.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    public FileOutputStream createLogisticReport(Calendar enddate, Text messages, Statement st, String fileName,String user, Integer assessment, String assessmentName, String userName) throws Exception {
    	
    	//JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\LogisticaReport.jrxml", "C:\\flash\\jasper\\LogisticaReport.jasper");
    	
    	String[][] answers = sys.getQuestionReportFacade().getCompleteAnswers(assessment,user,sys.getUserSessionData());
        HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		if(assessmentName.toLowerCase().contains("elearning")) {
			params.put("header", AssesmentData.FLASH_PATH+"/images/headerEP.jpg");
		}else {
			params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		}
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("corporacion", messages.getText("generic.corporation") + ": " + assessmentName);
		if(assessment.intValue() == AssesmentData.BAYER_MEXICO || assessment.intValue() == AssesmentData.BAYER_MEXICO_NUEVOS_EMPLEADOS) {
			String answerTxt = (assessment.intValue() == AssesmentData.BAYER_MEXICO) ? getUserAnswer(user,19028,st) : getUserAnswer(user,19027,st);
			if(answerTxt == null) {
				params.put("nombre", messages.getText("generic.assesment") + ": " + assessmentName);
			}else {
				params.put("nombre", messages.getText("module812.question19028.text") + ": " + messages.getText(answerTxt));
			}
		}else {
			if(assessmentName.toLowerCase().contains("elearning")) {
				params.put("nombre", messages.getText("generic.activity") + ": " + assessmentName);
			}else {
				params.put("nombre", messages.getText("generic.assesment") + ": " + assessmentName);
			}
		}
		params.put("usuario", messages.getText("user.data.nickname") + ": " + userName);
		params.put("fecha", dateToString(messages,enddate,sys.getUserSessionData().getLenguage()));
		params.put("titulo_resultado", messages.getText("Respuestas"));

        String principalFileName = AssesmentData.JASPER_PATH+"LogisticaReport.jasper";
		FileInputStream finalInput = new FileInputStream(new File(principalFileName));
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(finalInput);
        FileOutputStream out = new FileOutputStream(new File(fileName));
        
//        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
 //       params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        LogisticaReportDataSource totalDataSource = new LogisticaReportDataSource(answers,messages);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, totalDataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

    public FileOutputStream createSurveyReport(Calendar enddate, Text messages, Statement st, String fileName,String user, Integer assessment, String assessmentName, String userName) throws Exception {
    	
    	//JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\SurveyReport.jrxml", "C:\\flash\\jasper\\SurveyReport.jasper");
    	//JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\AnswersReport.jrxml", "C:\\flash\\jasper\\AnswersReport.jasper");
    	
    	String[][] answers = sys.getQuestionReportFacade().getCompleteAnswers(assessment,user,sys.getUserSessionData());
        HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		if(assessmentName.toLowerCase().contains("elearning")) {
			params.put("header", AssesmentData.FLASH_PATH+"/images/headerEP.jpg");
		}else {
			params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		}
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("title", messages.getText("generic.data.results"));
		params.put("corporacion", messages.getText("generic.corporation") + ": " + assessmentName);
		if(assessment.intValue() == AssesmentData.BAYER_MEXICO || assessment.intValue() == AssesmentData.BAYER_MEXICO_NUEVOS_EMPLEADOS) {
			String answerTxt = (assessment.intValue() == AssesmentData.BAYER_MEXICO) ? getUserAnswer(user,19028,st) : getUserAnswer(user,19027,st);
			if(answerTxt == null) {
				params.put("nombre", messages.getText("generic.assesment") + ": " + assessmentName);
			}else {
				params.put("nombre", messages.getText("module812.question19028.text") + ": " + messages.getText(answerTxt));
			}
		}else {
			if(assessmentName.toLowerCase().contains("elearning")) {
				params.put("nombre", messages.getText("generic.activity") + ": " + assessmentName);
			}else {
				params.put("nombre", messages.getText("generic.assesment") + ": " + assessmentName);
			}
		}
		params.put("usuario", messages.getText("user.data.nickname") + ": " + userName);
		params.put("fecha", dateToString(messages,enddate,sys.getUserSessionData().getLenguage()));
		params.put("titulo_resultado", messages.getText("Respuestas"));
		params.put("modules", " "+messages.getText("assesment.data.modules"));
		params.put("yes", messages.getText("generic.messages.yes"));
		params.put("partial", messages.getText("generic.messages.partial"));
		params.put("no", messages.getText("generic.messages.no"));
		params.put("points", messages.getText("generic.messages.points"));

		Collection results = sys.getQuestionReportFacade().getSurveyResult(user,sys.getUserSessionData());
        ModuleSurveyReportDataSource totalDataSource = new ModuleSurveyReportDataSource(results, messages);
		
		double totalPoints = totalDataSource.getTotalPoints();
        params.put("total", " Total"); 
        params.put("totalPoints", new Double(totalPoints)); 
		params.put("totalYes", new Integer(totalDataSource.getTotalYes())); 
		params.put("totalNo", new Integer(totalDataSource.getTotalNo()));
		params.put("totalPartial", new Integer(totalDataSource.getTotalPartial()));
		params.put("linea", AssesmentData.FLASH_PATH+"/images/linea.png");
		params.put("marcador", AssesmentData.FLASH_PATH+"/images/marcador.png");
		params.put("result", totalDataSource.getTotalImage());
		params.put("percent", totalDataSource.getTotalPercent());

		String principalFileName = AssesmentData.JASPER_PATH+"SurveyReport.jasper";
		FileInputStream finalInput = new FileInputStream(new File(principalFileName));
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(finalInput);
        FileOutputStream out = new FileOutputStream(new File(fileName));
        
//        JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
 //       params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

        LogisticaReportDataSource answerDataSource = new LogisticaReportDataSource(answers,messages);
        FileInputStream input =  new FileInputStream(new File(AssesmentData.JASPER_PATH +  "AnswersReport.jasper"));
        JasperReport moduleSubreport = (JasperReport)JRLoader.loadObject(input);
		params.put("subreport", moduleSubreport);
		params.put("subdatasource", answerDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, totalDataSource);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
        exporter.exportReport();       
        exporter.reset();
        
        return out;
    }

    public FileOutputStream createTotalReport(Calendar enddate, Text messages, HashMap messagesbkp,Statement st, String fileName,String user, Integer assessment, String assessmentName, String userName, int moduleCount, boolean psi, String corporationId) throws Exception {
    	
    	//JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\TotalReport1.jrxml", "C:\\flash\\jasper\\TotalReport.jasper");
    	//JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\ModuleSubreport5.jrxml", "C:\\flash\\jasper\\ModuleSubreport5.jasper");
    	
        HashMap map = findUsersResults(st, assessment, user);

        String[][] modules = new String[moduleCount][2];
		ResultSet set = st.executeQuery("SELECT id, text FROM modulesbkp m join generalmessagesbkp gm on gm.labelkey = m.key WHERE gm.language = '"+ sys.getUserSessionData().getLenguage() +"' AND assesment = "+assessment);
		int index = 0;
		while(set.next()) {
			modules[index][0] = set.getString(1);
			modules[index][1] = set.getString(2);
			index++;
		}
		
        String subreportName = (moduleCount > 4) ? "ModuleSubreport5.jasper" : "ModuleSubreport.jasper";
        FileInputStream input =  new FileInputStream(new File(AssesmentData.JASPER_PATH +  subreportName));
        JasperReport moduleSubreport = (JasperReport)JRLoader.loadObject(input);
        ModuleReportDataSource moduleDataSource = new ModuleReportDataSource(map.size());
        Integer correctas = new Integer(0);
        Integer incorrectas = new Integer(0);
        for(int i = 0; i < moduleCount; i++) {
        	if(map.containsKey(new Integer(modules[i][0]))) {
        		Object[] data = (Object[])map.get(new Integer(modules[i][0]));
        		incorrectas += (Integer)data[1];
        		correctas += (Integer)data[2];
        		moduleDataSource.addModule(modules[i][1], (Integer)data[2], (Integer)data[1]);
        	}
        }
        
        
        TotalReportDataSource totalDataSource = new TotalReportDataSource(correctas,incorrectas,messages);
        
        HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("logo_empresa", AssesmentData.FLASH_PATH+"/images/logo"+corporationId+".png");
		params.put("logo_cepa", AssesmentData.FLASH_PATH+"/images/logo_cepa.jpg");
		if(assessmentName.toLowerCase().contains("elearning")) {
			params.put("header", AssesmentData.FLASH_PATH+"/images/headerEP.jpg");
		}else {
			params.put("header", AssesmentData.FLASH_PATH+"/images/header.jpg");
		}
		params.put("footer", AssesmentData.FLASH_PATH+"/images/footer.jpg");
		params.put("corporacion", messages.getText("generic.corporation") + ": " + assessmentName);
		if(assessment.intValue() == AssesmentData.BAYER_MEXICO || assessment.intValue() == AssesmentData.BAYER_MEXICO_NUEVOS_EMPLEADOS) {
			String answerTxt = (assessment.intValue() == AssesmentData.BAYER_MEXICO) ? getUserAnswer(user,19028,st) : getUserAnswer(user,19027,st);
			if(answerTxt == null) {
				params.put("nombre", messages.getText("generic.assesment") + ": " + assessmentName);
			}else {
				params.put("nombre", messages.getText("module812.question19028.text") + ": " + messages.getText(answerTxt));
			}
		}else {
			if(assessmentName.toLowerCase().contains("elearning")) {
				params.put("nombre", messages.getText("generic.activity") + ": " + assessmentName);
			}else {
				params.put("nombre", messages.getText("generic.assesment") + ": " + assessmentName);
			}
		}
		params.put("usuario", messages.getText("user.data.nickname") + ": " + userName);
		params.put("fecha", dateToString(messages,enddate,sys.getUserSessionData().getLenguage()));
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

		String[][] data = getWrongAnswers(st,assessment,user);

		int indexPSI = 3;
        if(data.length > 0) {
            FileInputStream input2 = new FileInputStream(new File(AssesmentData.JASPER_PATH +  "ErrorSubreport.jasper"));
            JasperReport errorSubreport = (JasperReport)JRLoader.loadObject(input2);
            ErrorReportDataSourceBKP errorDatasource = new ErrorReportDataSourceBKP(data,messagesbkp);
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
        
        if(psi) {
        	if(assessment == AssesmentData.BAYER_MEXICO) {
    	        //JasperCompileManager.compileReportToFile("C:\\Proyectos JAVA\\Assesment\\src\\assesment\\presentation\\actions\\report\\jrxml\\PsiSubreportBayer.jrxml", "C:\\Proyectos JAVA\\Assesment\\src\\assesment\\presentation\\actions\\report\\jasper\\PsiSubreportBayer.jasper");
		        FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreportBayer.jasper");
		        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
		        Integer[] values = getUserPsicoResult(st,user,assessment);
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
        		JasperCompileManager.compileReportToFile(AssesmentData.JASPER_PATH +  "PsiSubreport.jrxml", AssesmentData.JASPER_PATH +  "PsiSubreport.jasper");
	        	FileInputStream input3 = new FileInputStream(AssesmentData.JASPER_PATH +  "PsiSubreport.jasper");
		        JasperReport psiSubreport = (JasperReport)JRLoader.loadObject(input3);
		        Integer[] values = getUserPsicoResult(st,user,assessment);
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
        if(moduleCount == 1) {
        	principalFileName = AssesmentData.JASPER_PATH+"TotalReport1.jasper";
        }
        if(moduleCount > 4) {
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

    public FileOutputStream createCertificate(Calendar enddate,Text messages, String fileName,String user, Integer assessmentId, String userName, String certificateImage) throws Exception {
    	HashMap<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("User",userName);
        parameters.put("DateText",dateToString(messages,enddate,sys.getUserSessionData().getLenguage()));
        
        String reportFileName = "";
        if(certificateImage != null) {
	        if(assessmentId == AssesmentData.LIMAGRAIN_ELEARNINGPACK) {
	   			parameters.put("Background",AssesmentData.FLASH_PATH+"/images/"+certificateImage);
	    		reportFileName = "certificadoLimagrain.jasper";
	        }else if(assessmentId == AssesmentData.FRESENIUS || assessmentId == AssesmentData.FRESENIUS_INTELIGENCIAEMCIONAL || assessmentId == AssesmentData.FRESENIUS_EBTWPLUS) {
//	        	JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\certificadoFresenius.jrxml", "C:\\flash\\jasper\\certificadoFresenius.jasper");
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/"+certificateImage);
	        	reportFileName = "certificadoFresenius.jasper";
	        }else if(assessmentId == AssesmentData.COTRAM_EBTWPLUS) {
	        	//JasperCompileManager.compileReportToFile("C:\\flash\\jasper\\certificadoCotram.jrxml", "C:\\flash\\jasper\\certificadoCotram.jasper");
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/"+certificateImage);
	        	reportFileName = "certificadoCotram.jasper";
	        } else {
	   			parameters.put("Background",AssesmentData.FLASH_PATH+"/images/"+certificateImage);
	    		reportFileName = "certificadoMonsantoBR.jasper";
	        }
        }else {
	        if(assessmentId == AssesmentData.ALLERGAN_2) {
	        	parameters.put("Background",AssesmentData.FLASH_PATH+"/images/certificateAllergan.jpg");
	        	reportFileName = "certificadoMonsantoBR.jasper";
	        	
	        }else if(isJJ(assessmentId)) {
	        	String reference = messages.getText("report.userresult.reference")+": "+userName;
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

	private Integer[] getUserPsicoResult(Statement st, String user, Integer assessment) throws Exception {
		Integer[] values = new Integer[6];
        String sql = "SELECT psiresult1,psiresult2,psiresult3," +
            "psiresult4,psiresult5,psiresult6 FROM userassesmentsbkp " +
            "WHERE assesment = "+String.valueOf(assessment) +
            " AND loginname = '"+user+"' "+
            "AND psiresult1 IS NOT NULL";
        ResultSet set = st.executeQuery(sql);
        set.next();
        for(int i = 0; i < 6; i++) {
            values[i] = new Integer(set.getInt(i+1)); 
        }
        return values;   
	}

	private String[][] getWrongAnswers(Statement st, Integer assessment,	String user) throws Exception {
        String sql = "SELECT m.key AS module,q.key AS question," +
                "a.key AS answered,a2.key AS correct " +
                "FROM useranswersbkp ua " +
                "JOIN answerdatabkp ad ON ua.answer = ad.id " +
                "JOIN answersbkp a ON ad.answer = a.id " +
                "JOIN questionsbkp q ON a.question = q.id " +
                "JOIN answersbkp a2 ON a2.question = q.id " +
                "JOIN modulesbkp m ON q.module = m.id " +
                "WHERE ua.loginname = '"+user+"' "+
                "AND ua.assesment = "+String.valueOf(assessment)+
                " AND a.type = 1 "+
                " AND a2.type = 0 "+
                " AND q.testtype = 1 "+
                " ORDER BY m.moduleorder,q.questionorder";
        ResultSet set = st.executeQuery(sql);
        Collection questionResults = new LinkedList();
        while(set.next()) {
            String[] data = new String[4];
            for(int j = 0; j < 4; j++) {
                data[j] = set.getString(j+1);
            }
            questionResults.add(data);
        }
        return (String[][])questionResults.toArray(new String[0][0]);
	}

	private HashMap findUsersResults(Statement st, Integer assessment, String user) throws Exception {
		HashMap hash = new HashMap();
        String queryStr = "SELECT module,m.key,a.type,count(*) AS count " +
                "FROM useranswersbkp AS ua " +
                "JOIN answerdatabkp AS ad ON ua.answer = ad.id " +
                "JOIN questionsbkp AS q ON ad.question = q.id " +
                "JOIN answersbkp AS a ON ad.answer = a.id " +
                "JOIN modulesbkp AS m ON q.module = m.id " +
                "WHERE ua.loginname = '"+user+"' " +
                "AND ua.assesment = "+String.valueOf(assessment)+" "+
                "AND q.testtype = 1 " +
                "GROUP BY module,m.key,a.type " +
                "ORDER BY module,m.key,a.type ";
        ResultSet set = st.executeQuery(queryStr);
        boolean result = false;
        while(set.next()) {
        	result = true;
            Integer moduleId = set.getInt(1);
            Object[] dataResult =  {set.getString(2),0,0};
            if(hash.containsKey(moduleId)) {
                dataResult =  (Object[])hash.get(moduleId);
            }else {
                hash.put(moduleId,dataResult);
            }
            if(set.getInt(3) == 1) {
                dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + set.getInt(4));
            }else {
                dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + set.getInt(4));
            }
        }
        if(!result) {
            queryStr = "SELECT module,m.key,0,count(*) AS count " +
                    "FROM useranswersbkp AS ua " +
                    "JOIN answerdatabkp AS ad ON ua.answer = ad.id " +
                    "JOIN questionsbkp AS q ON ad.question = q.id " +
                    "JOIN modulesbkp AS m ON q.module = m.id " +
                    "WHERE ua.loginname = '"+user+"' " +
                    "AND ua.assesment = "+String.valueOf(assessment)+" "+
                    "GROUP BY module,m.key " +
                    "ORDER BY module,m.key ";
            set = st.executeQuery(queryStr);
            while(set.next()) {
                Integer moduleId = set.getInt(1);
                Object[] dataResult =  {set.getString(2),0,0};
                if(hash.containsKey(moduleId)) {
                    dataResult =  (Object[])hash.get(moduleId);
                }else {
                    hash.put(moduleId,dataResult);
                }
                if(set.getInt(3) == 1) {
                    dataResult[1] = new Integer(((Integer)dataResult[1]).intValue() + set.getInt(4));
                }else {
                    dataResult[2] = new Integer(((Integer)dataResult[2]).intValue() + set.getInt(4));
                }
            }
        }
		return hash;
	}

    public String dateToString(Text messages, Calendar date, String language) {
        StringBuffer buffer = new StringBuffer();
        if(language.equals("en")) {
            buffer.append(messages.getText("generic.month."+String.valueOf(date.get(Calendar.MONTH)+1))).append(" ").append(date.get(Calendar.DATE)).append(", ").append(" ").append(date.get(Calendar.YEAR));
        }else {
            buffer.append(date.get(Calendar.DATE)).append(" ").append(messages.getText("generic.messages.of")).append(" ").append(messages.getText("generic.month."+String.valueOf(date.get(Calendar.MONTH)+1))).append(" ").append(messages.getText("generic.messages.of")).append(" ").append(date.get(Calendar.YEAR));
        }
        return buffer.toString();
    }

	public boolean isJJ(int assessment) {
		return (assessment == AssesmentData.JJ || assessment == AssesmentData.JJ_2 || assessment == AssesmentData.JJ_3 || assessment == AssesmentData.JJ_4 || 
				assessment == AssesmentData.JJ_5 || assessment == AssesmentData.JJ_6 || assessment == AssesmentData.JJ_7 || assessment == AssesmentData.JJ_8);
	}

	public void sendError(Statement st1, Statement st2,String login, String userName) throws Exception,RemoteException {
        MailSender sender = new MailSender();
        boolean sended = false;
        int count = 10;
        String body = "ERROR en usuario: "+login+"("+userName+")";
        while(!sended && count > 0) {
        	try {
                /*String[] senderAddress = sender.getAvailableMailAddress(st1,st2);
                if(!empty(senderAddress[0])) {
           			sender.send("jrodriguez@cepasafedrive.com",new LinkedList<String>(),"CEPA - Driver Assessment",senderAddress[0],senderAddress[1],"ERROR envio de reportes Driver Assessment",body,new LinkedList(),new LinkedList());
                	sended = true;
                }*/
        	}catch (Exception e) {
				e.printStackTrace();
			}
            count--;
        }

	}

	public void sendEmail(Statement st1, Statement st2, Text messages, int assessment, String assessmentName, String login, String email, boolean doFeedback, String userName, Collection<String> files, String redirect, Collection<String> feedbacks, boolean green) throws Exception,RemoteException {
    	boolean isElearningPackBYRMX = (assessment == AssesmentData.BAYERMX_ELEARNINGPACK_V2 || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_V2_REPETICION || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2 || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2_REPETICION);
        MailSender sender = new MailSender();
        String emailTitle = messages.getText("assessment.reports") + " - "+ assessmentName + " - "+ userName;
		if(files.size() > 0) {
		    String body = getBody(messages, userName, redirect, assessment, green);
		    if(email == null) {
	            String sql = "SELECT q.key,ad.text " +
	            		"FROM useranswersbkp ua " +
	            		"JOIN answerdatabkp ad ON ua.answer = ad.id " +
	            		"JOIN questionsbkp q ON q.id = ad.question " +
	            		"WHERE ua.loginname = '"+login+"' "+
	            		"AND ua.assesment = "+String.valueOf(assessment)+
	            		" AND q.type = 9";
	            ResultSet setEmail = st1.executeQuery(sql);
	            if(setEmail.next()) {
	            	email = setEmail.getString(2);
	            }
			}
			if(!empty(email)) {
		        boolean sended = false;
		        int count = 10;
		        while(!sended && count > 0) {
		        	try {
		                /*String[] senderAddress = sender.getAvailableMailAddress(st1,st2);
		                if(!empty(senderAddress[0])) {
		            		if(isElearningPackBYRMX) {
                            	sender.sendImage(email, "CEPASAFEDRIVE", senderAddress[0], senderAddress[1], "Resultados eLeaning Pack - "+userName, files, body, AssesmentData.FLASH_PATH+"/images/logo_road_safety.png", feedbacks);
                    		} else if(assessmentName.toLowerCase().contains("elearning")){
                    			sender.send(email,new LinkedList<String>(),"CEPASAFEDRIVE",senderAddress[0],senderAddress[1],assessmentName + " - "+ userName,body,files,feedbacks);
                    		} else {
                    			sender.send(email,new LinkedList<String>(),"CEPA - Driver Assessment",senderAddress[0],senderAddress[1],emailTitle,body,files,feedbacks);
                    		}
		                	sended = true;
		                }*/
		        	}catch (Exception e) {
						e.printStackTrace();
					}
		            count--;
		        }
			}
		}
	}

    private String getBody(Text messages, String name, String redirect, int assessment, boolean green) {
    	String body;
    	switch(assessment) {
	         case AssesmentData.JJ: case AssesmentData.JJ_2: case AssesmentData.JJ_3: case AssesmentData.JJ_4:  case AssesmentData.JJ_5: case AssesmentData.JJ_6: case AssesmentData.JJ_7: case AssesmentData.JJ_8:
	            body = "<table>";
	            body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
	            body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
	            body += "<tr><td><table width='100%'><tr>";            
	            body += "<td><b>"+messages.getText("assesment.reporthtml.footermessage1")+"</b></td>";
	            body += "</tr>";
	    	    if(redirect != null) {
	    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
	    	    	body += "<tr><td>&nbsp;</td></tr>";
	    	    	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
	    	    	body += "<tr><td>&nbsp;</td></tr>";
	    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
	    	    	body += "<tr><td>&nbsp;</td></tr>";
	    	    }
	            body += "</table></td></tr>";                   
	            body += "<tr><td></td><tr>";
	            body += "</table>";
	            break;
	         case AssesmentData.BAYERMX_ELEARNINGPACK_MONITORES: case AssesmentData.BAYERMX_ELEARNINGPACK_MONITORES_REPETICION:
	     		body = "<html>" +
 				"	<head>" +
 				"	</head>" +
 				"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
 				"		<table>" +
 				"			<tr>" +
 				"				<td>" +
 				"					<span style='font-family:Verdana; font-size:10;'><b>Estimado/a " +formatHTML(name)+",</b></span>"+
 				"				</td>" +
 				"			</tr>";
 				body +=	"<tr><td>&nbsp;</tr></tr>" ;
 				if(green) {
 					body +=	"			<tr>" +
 					"				<td>" +
 					"					Adjuntamos reporte de evaluaci&oacute;n del eLearning Pack 2017, <b>felicitaciones su calificaci&oacute;n fue mayor al 80% por lo que su entrenamiento ha sido aprobado</b> " +
 					"					 y tendr&aacute; el beneficio de una reducci&oacute;n de 5pts en su CRC (Calificaci&oacute;n de Riesgo del Conductor). " +
 					"				</td>" +
 					"			</tr>";
 				} else {
 					if(assessment == AssesmentData.BAYERMX_ELEARNINGPACK_MONITORES) {
 						body +=	"			<tr>" +
 						"				<td>" +
 						"					Adjuntamos reporte de evaluaci&oacute;n del eLearning Pack 2016, <b>su calificaci&oacute;n fue menor al 80% y tiene habilitada una segunda oportunidad para realizarlo con &eacute;xito.</b><br> " +
 						"					Por favor, vuelva a ingresar a <a href='https://www.cepada.com'>www.cepada.com</a> <b>con el mismo usuario/contrase&ntilde;a</b> y aproveche al m&aacute;ximo la oportunidad. " +
 						"				</td>" +
 						"			</tr>";
 					} else {
 						body +=	"			<tr>" +
 						"				<td>" +
 						"					Se adjunta su reporte de evaluaci&oacute;n del eLearning Pack 2017, <b>su calificaci&oacute;n nuevamente fue menor al 80%.</b><br> " +
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
 				break;	
	         case AssesmentData.BAYERMX_ELEARNINGPACK_V2: case AssesmentData.BAYERMX_ELEARNINGPACK_V2_REPETICION: case AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2: case AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2_REPETICION:
	     		body = "<html>" +
 				"	<head>" +
 				"	</head>" +
 				"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
 				"		<table>" +
 				"			<tr>" +
 				"				<td>" +
 				"					<span style='font-family:Verdana; font-size:10;'><b>Estimado/a " +formatHTML(name)+",</b></span>"+
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
 					if(assessment == AssesmentData.BAYERMX_ELEARNINGPACK_V2 || assessment == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2) {
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
 				break;	
	         default:
	 		    body = "<table>";
				body += "<tr><td>"+messages.getText("feedback.thanksmessage")+"</td><tr>";
			    body += "<tr><td><a href='www.cepasafedrive.com'>www.cepasafedrive.com</a></td><tr>";
	    	    if(redirect != null) {
	    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage4")+"</span></i></td></tr>";
	    	    	body += "<tr><td>&nbsp;</td></tr>";
	    	    	body += "<tr><td><a href=\""+redirect+"\">"+redirect+"</a></td></tr>";
	    	    	body += "<tr><td>&nbsp;</td></tr>";
	    	    	body += "<tr><td><i><span style=\"font-size: 10pt; color: rgb(102, 102, 102); font-family: 'Verdana','sans-serif';\">"+messages.getText("assesment.reporthtml.footermessage5")+"</span></i><a href=\"mailto:indesa@cepasafedrive.com\">indesa@cepasafedrive.com</a></td></tr>";
	    	    	body += "<tr><td>&nbsp;</td></tr>";
	    	    }
			    body += "<tr><td></td><tr>";
			    body += "</table>";
			    break;
    	}
		return body;
	}

	public boolean empty(String label) {
        return label == null || label.trim().length() == 0;
    }

    public String formatHTML(String word) {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); 
            switch(c) {
                case '\r':
                    break;
                case '\n':
                    result.append("<br>");
                    break;
                case '\t':
                    result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    break;
                case 'á':
                    result.append("&aacute;");
                    break;
                case 'é':
                    result.append("&eacute;");
                    break;
                case 'í':
                    result.append("&iacute;");
                    break;
                case 'ó':
                    result.append("&oacute;");
                    break;
                case 'ú':
                    result.append("&uacute;");
                    break;
                case 'ñ':
                    result.append("&ntilde;");
                    break;
                default:
                    result.append(c);
            }
        }
        return result.toString();
    }

    public String formatDate(Calendar calendar) {
        if(calendar == null) {
            return "";
        }
        return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
    }
/*
	private static void linkDC(Statement stDAQuery, Statement stDAUpdate, Statement stDC) throws Exception {
		ResultSet set = stDAQuery.executeQuery("SELECT loginname,firstname, lastname, email FROM users WHERE loginname IN (SELECT loginname FROM userassesments WHERE assesment = 231 and enddate is null) and datacenter is null");
		while(set.next()) {
			String loginname = set.getString(1);
			String driverName = set.getString(2)+" "+set.getString(3);
			String email = set.getString(4);
			
			String id =linkDC(driverName, email, loginname, "83", stDAQuery, stDAUpdate, stDC);
			if(id == null) {
				System.out.println(driverName+";"+email);
			}
		}
	}
*/	
	private String linkDC(String driverName, String email, String loginname, String corporation,Statement stDAQuery, Statement stDAUpdate, Statement stDC) throws Exception {
		if(!empty(driverName) && !empty(email)) {
			ResultSet setDriver = stDC.executeQuery("SELECT id, exclusiondate FROM drivers WHERE LOWER(TRIM(email)) = '"+email.toLowerCase()+"' AND LOWER(TRIM(firstname)) || ' ' || LOWER(TRIM(lastname)) = '"+driverName.toLowerCase()+"' ORDER BY exclusiondate DESC");
			if(setDriver.next()) {
				String id = setDriver.getString(1);
				String date = setDriver.getString(2);
				if(setDriver.next()) {
					String date2 = setDriver.getString(2);
					if(empty(date) && !empty(date2)) {
						stDAUpdate.executeUpdate("UPDATE users SET datacenter = "+id+" WHERE loginname = '"+loginname+"'");
						return id;
					}
					return null;
				}else {
					stDAUpdate.executeUpdate("UPDATE users SET datacenter = "+id+" WHERE loginname = '"+loginname+"'");
					return id;
				}
			}
		}

		if(!empty(driverName)) {
			ResultSet setDriver = stDC.executeQuery("SELECT id, exclusiondate FROM drivers WHERE LOWER(TRIM(firstname)) || ' ' || LOWER(TRIM(lastname)) = '"+driverName.toLowerCase()+"' ORDER BY exclusiondate DESC");
			if(setDriver.next()) {
				String id = setDriver.getString(1);
				String date = setDriver.getString(2);
				if(setDriver.next()) {
					String date2 = setDriver.getString(2);
					if(empty(date) && !empty(date2)) {
						stDAUpdate.executeUpdate("UPDATE users SET datacenter = "+id+" WHERE loginname = '"+loginname+"'");
						return id;
					}
				}else {
					stDAUpdate.executeUpdate("UPDATE users SET datacenter = "+id+" WHERE loginname = '"+loginname+"'");
					return id;
				}
			}
		}

		if(!empty(email)) {
			ResultSet setDriver = stDC.executeQuery("SELECT id, exclusiondate FROM drivers WHERE LOWER(TRIM(email)) = '"+email.toLowerCase()+"' ORDER BY exclusiondate DESC");
			if(setDriver.next()) {
				String id = setDriver.getString(1);
				String date = setDriver.getString(2);
				if(setDriver.next()) {
					String date2 = setDriver.getString(2);
					if(empty(date) && !empty(date2)) {
						stDAUpdate.executeUpdate("UPDATE users SET datacenter = "+id+" WHERE loginname = '"+loginname+"'");
						return id;
					}
				}else {
					stDAUpdate.executeUpdate("UPDATE users SET datacenter = "+id+" WHERE loginname = '"+loginname+"'");
					return id;
				}
			}
		}
		return null;
	}
	
	public String getSendId() {
		return sendId;
	}

    private static String getUserAnswer(String user, int question, Statement st) throws Exception {
		ResultSet set = st.executeQuery("select a.key from answerdatabkp ad join useranswersbkp ua on ua.answer = ad.id join answersbkp a on a.id = ad.answer " +
				"where ad.question = "+question+" and loginname = '"+question+"'");
		if(set.next()) {
			return set.getString(1);
		}
		return null;
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
}
