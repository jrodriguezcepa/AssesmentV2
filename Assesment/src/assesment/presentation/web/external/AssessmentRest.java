package assesment.presentation.web.external;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import assesment.business.AssesmentAccessRemote;
import assesment.business.administration.user.UsReportFacade;
import assesment.business.ws.cache.CacheManager;
import assesment.business.ws.client.security.User;
import assesment.communication.administration.user.UserSessionData;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.report.ErrorReportDataSource;
import assesment.communication.report.ModuleReportDataSource;
import assesment.communication.report.PsiReportDataSource;
import assesment.communication.report.TotalReportDataSource;
import assesment.communication.user.UserData;
import assesment.communication.util.GenerateReport;
import assesment.presentation.actions.report.QuestionResultAction;
import assesment.presentation.actions.report.Util;

@Path("assessmentws")
public class AssessmentRest {

	@GET
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public User login(@QueryParam("user") String user,@QueryParam("password") String password) throws Exception{
		User userSys =new User(user,password);
		try{
			CacheManager.getInstance().login(userSys);
			return userSys;
		}catch(Throwable th){
			
		}
		return null;
	}

	@GET
	@Path("proof")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
	public byte[] report(@QueryParam("login")String login,@QueryParam("assessmentId")Integer assessmentId,@QueryParam("format")Integer format,@QueryParam("userLogin")String userLogin,@QueryParam("password")String password) throws Exception{
		try{
			User userSys =new User(userLogin,password);
			AssesmentAccessRemote sys = CacheManager.getInstance().login(userSys); 
	        UserSessionData userSessionData = sys.getUserSessionData();
	        Text messages = sys.getText();
	        
	        AssesmentData assessment = sys.getAssesmentReportFacade().findAssesment(assessmentId, userSessionData);
	        UserData user = sys.getUserReportFacade().findUserByPrimaryKey(login, userSessionData);
	        Util util=new Util();
	        String fileName = AssesmentData.FLASH_PATH+"/reports/"+AssesmentData.getFileName(assessment.getId(),userSessionData.getLenguage(),user.getFirstName()+"_"+user.getLastName(),1)+".pdf";
            util.createTotalReport(fileName,user, assessment, sys);
            
            return util.read(new File(fileName));
            
	        
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[]{};
	}

	@GET
	@Path("userassessments")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String[]> getUserAssessments(@QueryParam("user") String user,@QueryParam("password") String password,@QueryParam("driver")Integer driver) throws RemoteException {
		ArrayList<String[]> values = new ArrayList<String[]>();
		try{
			User userSys =new User(user,password);
			AssesmentAccessRemote sys = CacheManager.getInstance().login(userSys);
			if (driver != null){
				values.addAll(sys.getUserReportFacade().getUserAssesments(driver, sys.getUserSessionData()));
			}
			//values.addAll(sys.getUserReportFacade().getUserAssesments(new Integer(814), sys.getUserSessionData()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return values;
	}


	@GET
	@Path("timacuser")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Integer saveTimacUser(@QueryParam("user") String user,@QueryParam("password") String password, @QueryParam("id") String id, @QueryParam("firstname") String firstName, @QueryParam("lastname") String lastName, @QueryParam("email") String email) throws RemoteException {
		try{
			User userSys =new User(user,password);
			AssesmentAccessRemote sys = CacheManager.getInstance().login(userSys);
			if(id != null && id.length() > 0) {
				String cpf = Util.getTimacCPF(id);
				return sys.getUserABMFacade().saveTimacUser(cpf, firstName, lastName, email, sys.getUserSessionData());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}


	@GET
	@Path("timacexistuser")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Object[] existTimacUser(@QueryParam("user") String user,@QueryParam("password") String password, @QueryParam("id") String id) throws RemoteException {
		try{
			User userSys =new User(user,password);
			AssesmentAccessRemote sys = CacheManager.getInstance().login(userSys);
			if(id != null && id.length() > 0) {
				String cpf = Util.getTimacCPF(id);
				return sys.getUserReportFacade().existTimacUser(cpf, sys.getUserSessionData());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new Object[] {0,null,null,null};
	}

	@GET
	@Path("timacreport")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String getTimacReport(@QueryParam("user") String user,@QueryParam("password") String password, @QueryParam("id") String id, @QueryParam("da") Integer da, @QueryParam("type") Integer type) throws RemoteException {
		try{
			if(da.intValue() == AssesmentData.TIMAC_BRASIL_EBTW_2020 && type != 1)
				return null;
			User userSys =new User(user,password);
			AssesmentAccessRemote sys = CacheManager.getInstance().login(userSys);
			if(id != null && id.length() > 0){
				GenerateReport gReport = new GenerateReport(sys.getText(),"pt");
				String cpf = Util.getTimacCPF(id);
		        String fileName = AssesmentData.FLASH_PATH+"/wstimac/"+cpf+"_"+da+".pdf";
				gReport.getReport(cpf, da, type, fileName);
	            
	        	return Base64.encode(new Util().read(new File(fileName)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
