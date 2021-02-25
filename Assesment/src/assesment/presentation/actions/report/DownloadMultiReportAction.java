package assesment.presentation.actions.report;


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
import assesment.communication.administration.MultiAnswerUserData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.CategoryData;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.translator.web.util.AbstractAction;

public class DownloadMultiReportAction  extends AbstractAction {

    public ActionForward cancel(HttpSession session, ActionMapping mapping, ActionForm form) {
        return null;
    }

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		try {
	        HttpSession session = request.getSession();
	    	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	        Text messages = sys.getText();
	        
			Collection<MultiAnswerUserData> users = sys.getUserReportFacade().findMultiAnswerUsers(new Integer(((DynaActionForm)form).getString("assessment")), null, null, sys.getUserSessionData());

	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename=Fatiga.xls");
	        
	        ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
	        
	        ArrayList<Object> row = new ArrayList<Object>();
			row.add(new Object[] {messages.getText("user.data.ci"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("user.data.firstname"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("user.data.lastname"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("assessment.enddate"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
	        row.add(new Object[] {messages.getText("assessment.result"), new Short(new HSSFColor.GREY_25_PERCENT().getIndex())});
			rows.add(row);

	        Iterator<MultiAnswerUserData> it = users.iterator();
			while(it.hasNext()) {
				MultiAnswerUserData user = it.next();
		        ArrayList<Object> rowUser = new ArrayList<Object>();
		        rowUser.add(new Object[] {user.getLoginName().replace("FY", ""), new Short(new HSSFColor.WHITE().getIndex())});
		        rowUser.add(new Object[] {user.getFirstName(), new Short(new HSSFColor.WHITE().getIndex())});
		        rowUser.add(new Object[] {user.getLastName(), new Short(new HSSFColor.WHITE().getIndex())});
		        rowUser.add(new Object[] {user.getEnd(), new Short(new HSSFColor.WHITE().getIndex())});

				Short s = new Short(new HSSFColor.GREEN().getIndex());
				if(!user.getResult().equals("approuved")) {
					s = new Short(new HSSFColor.RED().getIndex());
				}
				rowUser.add(new Object[] {messages.getText("result."+user.getResult()), s});
				rows.add(rowUser);
			}

			ExcelGenerator.generatorObjectXLS(rows, "Fatiga", response.getOutputStream());
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