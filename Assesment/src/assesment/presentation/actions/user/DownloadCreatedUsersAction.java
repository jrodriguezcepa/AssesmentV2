package assesment.presentation.actions.user;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.report.UserReportData;
import assesment.presentation.translator.web.util.AbstractAction;

public class DownloadCreatedUsersAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	        Text messages = sys.getText();
	    	ArrayList<ArrayList<Object>> sheetResume = (ArrayList<ArrayList<Object>>) sys.getValue();
	        
	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename=Usuarios.xls");
	        
	        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet s = w.createSheet("Usuarios", 0);
	        int line = 0;
			for(ArrayList<Object> row : sheetResume ){
		        int column = 0;
				for(Object cellValue : row){
			        s.addCell(new Label(column,line,String.valueOf(cellValue)));
			        column++;
				}
				line++;
			}
        
			w.write();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}