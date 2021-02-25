package assesment.presentation.translator.web.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.jboss.logging.Logger;

import assesment.communication.administration.user.UserSessionData;
import assesment.persistence.util.LoggerFormatting;
import assesment.presentation.translator.web.administration.user.LogoutAction;

public class ActionController extends ActionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoggerFormatting logFormatting=new LoggerFormatting();
	
	public void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		try{
			super.doGet(arg0, arg1);
		}catch(Exception e){
			exception(" do Get",e,arg0,arg1, Logger.getLogger((String)arg0.getAttribute("className")));
		}
	}
	
	
	public void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
		try {
			super.doPost(arg0, arg1);
		}catch(Exception e) {
			e.printStackTrace();
			exception(" do Post",e,arg0,arg1, Logger.getLogger((String)arg0.getAttribute("className")));
		}
	}
	
	private void exception(String method, Exception e,HttpServletRequest arg0, HttpServletResponse arg1,Logger logger)throws IOException {
	    try {
            if(arg0.getSession().getAttribute("AssesmentAccess") == null) {
            	new LogoutAction().logout(arg0);
            	arg1.sendRedirect("../assesment/index.jsp?session=1");
            }else {
                log("Exception Catch in ActionController method " + method, e);
                arg0.setAttribute("exception", e);
    
                String usr = "anonymous";
                if (arg0 != null && arg0.getAttribute("AssesmentAccess") != null) {
                    UserSessionData userSessionData = (UserSessionData) arg0.getAttribute("UserSessionData");                
                    usr = userSessionData.getFilter().getLoginName();
                }           
                Throwable thw = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e;
                while (thw.getCause() != null) {
                    thw = thw.getCause();
                }
    
                if (thw.getClass().toString().indexOf("SecurityException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"SecurityException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/login.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("RemoteException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"RemoteException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("DCSecurityException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"DCSecurityException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher(arg0.getContextPath()+"/login.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("DeslogedException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"DeslogedException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher(arg0.getContextPath()+"/login.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("InvalidDataEception") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"InvalidDataException", usr), e);
                    arg0.setAttribute("exception",thw);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("PermissionInsufficientEception") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"PermissionInsufficientEception", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/insufficientpermissions.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("CommunicationProblemException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"CommunicationProblemException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("InInitializerException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"InInitializerException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("AlreadyExistsException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"AlreadyExistsException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                } else if (thw.getClass().toString().indexOf("DCException") > 0) {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"InvalidDataException", usr), e);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                } else {
                    logger.fatal(logFormatting.formattingText(e,"Exception Catch in ActionController method " + method,"Exception ssss", usr), e);
                    arg0.setAttribute("exception",thw);
                    RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                    dispatcher.forward(arg0, arg1);
                }
            }
        } catch (Exception ex) {
            try {
                RequestDispatcher dispatcher = arg0.getRequestDispatcher("/exception.jsp");
                dispatcher.forward(arg0, arg1);
            } catch (ServletException se) {
                se.printStackTrace();
            }
        }
	}
}
