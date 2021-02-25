/**
 * Created on 19-oct-2007
 * CEPA
 * DataCenter 5
 */
package assesment.presentation.actions.report;

import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.communication.assesment.AssesmentData;

public class ReportUtil {

    public ReportUtil() {
    }

    public ActionForward executeReport(String file,String output,HashMap parameters,JRDataSource dataSource,HttpSession session,ActionMapping mapping,HttpServletResponse response,String outputFileName) throws Exception {
        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource(file).getFile());
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input);
        return executeReport(jasperReport,output,parameters,dataSource,session,mapping,response,outputFileName);        
    }
        
    public ActionForward executeReport(JasperReport jasperReport,String output,HashMap parameters,JRDataSource dataSource,HttpSession session,ActionMapping mapping,HttpServletResponse response,String outputFileName) throws Exception {
        if(output.equals("HTML")) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            StringWriter writer = new StringWriter();
            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
            exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
            exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
            exporter.exportReport();       
            session.setAttribute("resp",writer.getBuffer().toString());
            return mapping.findForward("report");
        }else if(output.equals("PDF")) {
            ServletOutputStream out = response.getOutputStream();
            
            //JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
            //parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            
      //  	JasperExportManager.exportReportToPdfFile(jasperPrint, "C://newuserreport.pdf");

            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Disposition","inline; filename=\""+outputFileName+".pdf\"");
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();       
            exporter.reset();
            out.flush();
            out.close();
            System.gc();
            return null;
        }else {
            ServletOutputStream out = response.getOutputStream();
            
            JRFileVirtualizer virtualizer = new JRFileVirtualizer(1, AssesmentData.FLASH_PATH+"/tmp");
            parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition","inline; filename=\""+outputFileName+".xls\"");
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();       
            exporter.reset();
            out.flush();
            out.close();
            System.gc();
            return null;
        }
     }

    public String executeReportToString(String file,String output,HashMap parameters,JRDataSource dataSource,HttpSession session,HttpServletResponse response,String outputFileName) throws Exception {
        FileInputStream input = new FileInputStream(QuestionResultAction.class.getResource(file).getFile());
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(input);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if(output.equals("HTML")) {
            StringWriter writer = new StringWriter();
            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
            exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
            exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
            exporter.setParameter(JRHtmlExporterParameter.  IMAGES_URI, "image?image=");
            exporter.exportReport();       
            return writer.getBuffer().toString();
        }else if(output.equals("PDF")) {
            final ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Disposition","inline; filename=\""+outputFileName+".pdf\"");
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();       
            out.flush();
            out.close();
            return null;
        }else {
            final ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment;filename=\"" + outputFileName + ".xls\"");
            JExcelApiExporter exporter = new JExcelApiExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();       
            out.flush();
            out.close();
            return null;
        }
     }
}

