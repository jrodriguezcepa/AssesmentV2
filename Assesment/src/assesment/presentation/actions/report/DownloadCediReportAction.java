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

public class DownloadCediReportAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	        Text messages = sys.getText();

	        GroupData group = sys.getAssesmentReportFacade().findGroup(new Integer(((DynaActionForm)form).getString("group")), sys.getUserSessionData());
			Integer cedi=new Integer(((DynaActionForm)form).getString("cedi"));
			Integer[] cedis = {new Integer(cedi)};

	        Collection<UserData> users = sys.getUserReportFacade().findCediUsers(cedis,"","","", "",sys.getUserSessionData());

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

	        Iterator<CategoryData> itC = group.getOrderedCategories();
			AssesmentAttributes[] assessmentIds = new AssesmentAttributes[group.getAssessmentCount()];
			int index = 0;

				while(itC.hasNext()) {
					CategoryData c = itC.next();
					Iterator<AssesmentAttributes> itA = c.getOrderedAssesments();
					while(itA.hasNext()) {
						AssesmentAttributes a = itA.next();
						assessmentIds[index] = a;
						row.add(new Object[] {messages.getText(a.getName()), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
						index++;
					}
				}
			
			rows.add(row);

			HashMap<String, HashMap<Integer, Object[]>> userResults = sys.getAssesmentReportFacade().getUserCediResults(group.getId(),cedi, sys.getUserSessionData());
			HashMap<String, HashMap<Integer, Object[]>> usersData = sys.getAssesmentReportFacade().getUserCediResults(cedis, sys.getUserSessionData());

			Iterator<UserData> it = users.iterator();
			index = 1;
			while(it.hasNext()) {
		        ArrayList<Object> rowUser = new ArrayList<Object>();
				UserData user = it.next();
				HashMap<Integer, Object[]> values = (userResults.containsKey(user.getLoginName())) ? userResults.get(user.getLoginName()) : new HashMap<Integer, Object[]>();
				HashMap<Integer, Object[]> values2 = (usersData.containsKey(user.getLoginName())) ? usersData.get(user.getLoginName()) : new HashMap<Integer, Object[]>();

				Object[] data0 = {null,null,null,null,null};
				if(values2.containsKey(0)) {
					data0 = values2.get(0);
				}
				rowUser.add(user.getLoginName());
				rowUser.add(user.getFirstName());
				rowUser.add(user.getLastName());
				rowUser.add(user.getEmail());
				rowUser.add((user.getExtraData2() == null) ? "---" : user.getExtraData2() );
				rowUser.add((data0[1] == null) ? "---" : messages.getText((String)data0[1]));
				rowUser.add((data0[2] == null) ? "---" : messages.getText((String)data0[2]));
				rowUser.add((data0[3] == null) ? "---" : Util.formatDate((Date)data0[3]));
				rowUser.add((data0[4] == null) ? "---" : Util.formatDate((Date)data0[4]));

				for(int i = 0; i < assessmentIds.length; i++) {
					AssesmentAttributes assAtt = assessmentIds[i]; 
					if(values.containsKey(assAtt.getId())) {
						Object[] data = values.get(assAtt.getId());
						if(((Integer)data[0]).intValue() == 0) {
							rowUser.add("---");
						} else {
							Short s = new Short(new HSSFColor.GREEN().getIndex());
							if(data[2] == null || data[3] == null)
								System.out.println("aca");
							if(((Integer)data[2]).intValue() == 0 && ((Integer)data[3]).intValue() == 0) {
								rowUser.add(new Object[] {Util.formatDate((Date)data[1])+" (100%)", s});
							}else {
								int percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
								if(percent < assAtt.getYellow()) {
									s = new Short(new HSSFColor.RED().getIndex());
								} else if(percent < assAtt.getGreen()) {
									s = new Short(new HSSFColor.YELLOW().getIndex());
								} 
								rowUser.add(new Object[] {Util.formatDate((Date)data[1])+" ("+percent+"%)", s});
							}
						}
					}else {
						rowUser.add("---");
					}
				}
				rows.add(rowUser);
			}
			

			String n = group.getName();
			if(n.length() > 30)
				n = n.substring(0, 30);
			n = n.replaceAll("/", " ");
			ExcelGenerator.generatorObjectXLS(rows, n, response.getOutputStream());
	        /*
	        WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
	        WritableSheet s = w.createSheet(group.getName(), 0);

	        s.addCell(new Label(0,0,messages.getText("user.data.nickname")));
	        s.addCell(new Label(1,0,messages.getText("user.data.firstname")));
	        s.addCell(new Label(2,0,messages.getText("user.data.lastname")));
	        s.addCell(new Label(3,0,messages.getText("user.data.mail")));
	        Iterator<CategoryData> itC = group.getOrderedCategories();
			AssesmentAttributes[] assessmentIds = new AssesmentAttributes[group.getAssessmentCount()];
			int index = 0;
			while(itC.hasNext()) {
				CategoryData c = itC.next();
				Iterator<AssesmentAttributes> itA = c.getOrderedAssesments();
				while(itA.hasNext()) {
					AssesmentAttributes a = itA.next();
					assessmentIds[index] = a;
					s.addCell(new Label(index+4,0,messages.getText(a.getName())));
					s.setS
					index++;
				}
			}
			
			HashMap<String, HashMap<Integer, Object[]>> userResults = sys.getAssesmentReportFacade().getUserGroupResults(group.getId(), sys.getUserSessionData());
			Iterator<UserData> it = users.iterator();
			index = 1;
			while(it.hasNext()) {
				UserData user = it.next();
				HashMap<Integer, Object[]> values = (userResults.containsKey(user.getLoginName())) ? userResults.get(user.getLoginName()) : new HashMap<Integer, Object[]>();
				s.addCell(new Label(0,index,user.getLoginName()));
				s.addCell(new Label(1,index,user.getFirstName()));
				s.addCell(new Label(2,index,user.getLastName()));
				s.addCell(new Label(3,index,user.getEmail()));
				for(int i = 0; i < assessmentIds.length; i++) {
					AssesmentAttributes assAtt = assessmentIds[i]; 
					if(values.containsKey(assAtt.getId())) {
						Object[] data = values.get(assAtt.getId());
						if(((Integer)data[0]).intValue() == 0) {
							s.addCell(new Label(i+4,index,"---"));
						} else {
							int percent = ((Integer)data[2]).intValue() * 100 / (((Integer)data[2]).intValue() + ((Integer)data[3]).intValue());
							s.addCell(new Label(i+4,index,Util.formatDate((Date)data[1])+" ("+percent+"%)"));
						}
					}else {
						s.addCell(new Label(i+4,index,"---"));
					}
				}
				index++;
			}

			w.write();
			w.close();
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
}