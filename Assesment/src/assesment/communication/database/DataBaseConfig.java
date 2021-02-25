/*
 * 
 * Created on 12-ene-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.database;

import java.io.Serializable;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface DataBaseConfig extends Serializable {
	
	/**
	 * Data Base direction
	 * eg: jdbc:postgresql://host:port/data base name
	 */
	public static final String url="jdbc:postgresql://localhost:5432/assesment";
	
	/**
	 * User data base
	 */
	public static final String user="postgres";
	
	/**
	 * Pasword
	 */
	public static final String password="postgres";
	
}
