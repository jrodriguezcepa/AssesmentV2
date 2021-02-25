/*
 * Created on 22-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.hibernate;

import javax.naming.InitialContext;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

/**
 * @author fcarriquiry
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HibernateAccess {

	public static final SessionFactory sessionFactory;
   
    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	// Required hibernate.cfg.xml locate in the root application 
        	

        	InitialContext ic = new InitialContext();
        	Object o = ic.lookup("java:/hibernate/AssesmentSessionFactory");
        	sessionFactory = (SessionFactory)o;
        	
        	
        } catch (Throwable ex) {
        	ex.printStackTrace();
        	// Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
	
	public static final ThreadLocal session = new ThreadLocal();
	
	/**
	 *  If session is empty or closed then create session and opened
	 *  
	 * @return Curren Session
	 * @throws HibernateException
	 */
    public static Session currentSession() throws HibernateException {
        Session s = (Session) session.get();
        // Open a new Session, if this thread has none yet
        if (s == null || !s.isOpen() ) {
        	s = sessionFactory.getCurrentSession();
            if(s== null || ! s.isOpen()){
            	s=sessionFactory.openSession();
            }
            // Store it in the ThreadLocal variable
            session.set(s);
        }
        return s;
    }
    
    /**
     * Close session and remove session class
     * 
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
    /**    Session s = (Session) session.get();
        if (s != null)
            s.close();
        session.set(null);
      **/  
    }
}