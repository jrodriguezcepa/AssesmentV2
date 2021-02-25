/*
 * Created on 14-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.language;

import java.rmi.RemoteException;
import java.util.ListResourceBundle;
import java.util.Locale;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import assesment.communication.exception.CommunicationProblemException;
import assesment.communication.exception.InInitializerException;
import assesment.communication.exception.InvalidDataException;
import assesment.persistence.language.LangReport;
import assesment.persistence.language.LangReportHome;
import assesment.persistence.language.LangReportUtil;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Text_es extends ListResourceBundle  {
	
	private static Object[][]contents;
	
	static{
		try{
			LangReportHome languageHome=LangReportUtil.getHome();
			LangReport language=languageHome.create();
			Locale locale=new Locale("es","","");
			
			contents=language.findMessages(locale);
        } catch(Exception e){
            throw new InInitializerException("Can't access language report, naming exception in class communication.language.Test_en",e.getMessage());
        }
	}
	
	/* (non-Javadoc)
	 * @see java.util.ListResourceBundle#getContents()
	 */
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return contents;
	}

	
}
