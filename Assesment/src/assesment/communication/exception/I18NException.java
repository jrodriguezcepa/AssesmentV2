/*
 * Created on 17-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.exception;

import java.io.Serializable;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface I18NException extends Serializable {

	public String getMessage();
	
	public String getLabelKey();
	
	public void setLabelKey(String labelKey);
	
	public String getMsg();
	
	public void setMsg(String msg);
	

}
