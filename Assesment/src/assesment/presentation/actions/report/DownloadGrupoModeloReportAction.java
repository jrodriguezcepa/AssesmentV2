package assesment.presentation.actions.report;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.GroupData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DownloadGrupoModeloReportAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	        Text messages = sys.getText();
	        
	        GroupData group = sys.getAssesmentReportFacade().findGroup(new Integer(((DynaActionForm)form).getString("group")), sys.getUserSessionData());

			Collection<UserData> users = sys.getUserReportFacade().findGroupUsers(group.getId(), sys.getUserSessionData());

	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename=Total.xls");
	        
	        ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
	        ArrayList<Object> row = new ArrayList<Object>();
	        row.add(new Object[] {messages.getText("user.data.nickname"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("user.data.firstname"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("user.data.lastname"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("user.data.mail"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
        	row.add(new Object[] {"CEDI", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	        row.add(new Object[] {"Posición", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	        row.add(new Object[] {"Tipo de licencia", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	        row.add(new Object[] {"Vigencia de la licencia", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	        row.add(new Object[] {"Fecha de nacimiento", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	        row.add(new Object[] {"Foto", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	    	row.add(new Object[] {"Driver Assessment", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	    	row.add(new Object[] {"eBTW", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
 	    	row.add(new Object[] {"Cuestionario Pendientes", new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
			rows.add(row);

			Integer[] cedis=null;
			UserData us = sys.getUserReportFacade().findUserByPrimaryKey(sys.getUserSessionData().getFilter().getLoginName(),sys.getUserSessionData());
			cedis = sys.getCorporationReportFacade().findCediUser(us.getLoginName(), sys.getUserSessionData());
			users =  sys.getUserReportFacade().findCediUsers(cedis, "","","", "", sys.getUserSessionData());
			
			HashMap<String, HashMap<Integer, Object[]>> userResults = sys.getAssesmentReportFacade().getUserCediResults(cedis, sys.getUserSessionData());
			
			Iterator<UserData> it = users.iterator();
			while(it.hasNext()) {
		        ArrayList<Object> rowUser = new ArrayList<Object>();
				UserData user = it.next();
				HashMap<Integer, Object[]> values = (userResults.containsKey(user.getLoginName())) ? userResults.get(user.getLoginName()) : new HashMap<Integer, Object[]>();
				//CEDI users data
				HashMap<Integer, Object[]> values2=null;
				Object[] data0 = {null,null,null,null,null};

				
				rowUser.add(user.getLoginName());
				rowUser.add(user.getFirstName());
				rowUser.add(user.getLastName());
				rowUser.add(user.getEmail());
				values2 = (userResults.containsKey(user.getLoginName())) ? userResults.get(user.getLoginName()) : new HashMap<Integer, Object[]>();
				if(values2.containsKey(0)) {
					data0 = values2.get(0);
				}
				rowUser.add((user.getExtraData2() == null) ? "---" : user.getExtraData2() );
				rowUser.add((data0[1] == null) ? "---" : messages.getText((String)data0[1]));
				rowUser.add((data0[2] == null) ? "---" : messages.getText((String)data0[2]));
				rowUser.add((data0[3] == null) ? "---" : Util.formatDate((Date)data0[3]));
				rowUser.add((data0[4] == null) ? "---" : Util.formatDate((Date)data0[4]));
				rowUser.add((data0[0] == null) ? "Pendiente" : "Cargado");
				for(int i = 1; i < 4; i++) {
					if(values.containsKey(i)) {
						Object[] data = values.get(i);
						if(((Integer)data[0]).intValue() == 0) {
							rowUser.add("Pendiente");
						}else {
							int percent = 0;
							if(((Integer)data[2]).intValue() == 0 && ((Integer)data[3]).intValue() == 0) {
								percent = 100;
							}else {
								percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
							}
							rowUser.add(Util.formatDate((Date)data[1])+" ("+percent+"%)");	
						}
					} else {
						rowUser.add("No asociado");
					}
				}
				rows.add(rowUser);
			}

			String n = group.getName();
			if(n.length() > 30)
				n = n.substring(0, 30);
			n = n.replaceAll("/", " ");
			ExcelGenerator.generatorObjectXLS(rows, n, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}