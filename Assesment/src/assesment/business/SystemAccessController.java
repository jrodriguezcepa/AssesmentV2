/*
 * Created on 17-ene-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.business;


import java.io.Serializable;
import java.util.HashMap;


/**
 * @author fcarriquiry
 * 
 * Pattern Singlenton
 * 
 * GRASP Controller
 * 
 * TODO To change the template for this generated type comment go to
 */
public class SystemAccessController implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static  HashMap  reloadclasses;
	
	private static SystemAccessController instance;
	
	private SystemAccessController() {
		reloadclasses = new HashMap();
	}
	
	public static SystemAccessController getInstance(){
	
		if(instance==null){
			instance=new SystemAccessController();
		}

		return instance;
	}
	
	public void unSuscribe(Object key){
		reloadclasses.remove(key);
	}
	
}
