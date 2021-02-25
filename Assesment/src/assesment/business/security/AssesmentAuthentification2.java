/**
 * Created on 23-ene-2006
 * CEPA
 * DataCenter 5
 */
package assesment.business.security;

import java.security.MessageDigest;
import java.security.acl.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import assesment.communication.util.MD5;

/**
 * @author jrodriguez
 */
public class AssesmentAuthentification2 extends DatabaseServerLoginModule {
	
	private static boolean wrt=false;
	private static String u=null;
	private static String p=null;
	
    public AssesmentAuthentification2() {
        super();
        System.out.println("AssesmentAuthentification2");
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options)
    {
       super.initialize(subject, callbackHandler, sharedState, options);
       dsJndiName = "java:/assesmentDS";
       if( dsJndiName == null )
          dsJndiName = "java:/DefaultDS";
       Object tmp = options.get("principalsQuery");
       if( tmp != null )
          principalsQuery = tmp.toString();
       tmp = options.get("rolesQuery");
       if( tmp != null )
          rolesQuery = tmp.toString();
       log.trace("DatabaseServerLoginModule, dsJndiName="+dsJndiName);
       log.trace("principalsQuery="+principalsQuery);
       log.trace("rolesQuery="+rolesQuery);
    }

    protected Group[] getRoleSets() throws LoginException {
    	
        String username = this.getUsername();
        if(username==null && wrt){
        	username=u;
        	wrt=false;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        Group group = new SimpleGroup("Roles");
  
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dsJndiName);
            conn = ds.getConnection();

            ps = conn.prepareStatement("select role from users where loginname=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                String name = rs.getString(1);
                if(name.equals("multiassessment") || name.equals("groupassessment") || name.equals("clientgroupreporter"))
                	name = "systemaccess";
                String groupName = "Roles";
                group = new SimpleGroup(groupName);
                group.addMember(new SimplePrincipal(name));
            }
            rs.close();
        }catch(NamingException ex) {
            ex.printStackTrace();
           throw new LoginException(ex.toString(true));
        }catch(SQLException ex) {
            ex.printStackTrace();
            throw new LoginException(ex.toString());
        }finally {
           if(ps != null) {
              try{
                 ps.close();
              }catch(SQLException e) {}
           }
           if(conn != null) {
              try {
                 conn.close();
              }catch (Exception ex) {}
           }
        }
        Group[] roleSets = {group};
        return roleSets;
    }
    
   

    protected String[] getUsernameAndPassword() throws LoginException {
    	 Connection conn = null;
         PreparedStatement ps = null;
         String[] info = super.getUsernameAndPassword();
         
         if(info[0] != null) { 
             try {
                 InitialContext ctx = new InitialContext();
                 DataSource ds = (DataSource) ctx.lookup(dsJndiName);
                 conn = ds.getConnection();
                 
                 ps = conn.prepareStatement("select expiry from users where loginname=? and expiry is not null");
                 ps.setString(1, info[0]);
                 ResultSet rs = ps.executeQuery();
                 boolean valid = true;
                 if(rs.next()) {
                     Calendar now = Calendar.getInstance();
                     Calendar expiry = Calendar.getInstance();
                     expiry.setTime(rs.getDate(1));
                     valid = now.before(expiry); 
                 }
                 rs.close();
                 
                 if(valid && info[1] != null) {
                    info[1] = new MD5().encriptar(info[1]);
                 }
   	        	 u=info[0];
 	    	     p=info[1];
             }catch(NamingException ex) {
                 ex.printStackTrace();
                throw new LoginException(ex.toString(true));
             }catch(SQLException ex) {
                 ex.printStackTrace();
                 throw new LoginException(ex.toString());
             }finally {
                if(ps != null) {
                   try{
                      ps.close();
                   }catch(SQLException e) {}
                }
                if(conn != null) {
                   try {
                      conn.close();
                   }catch (Exception ex) {}
                }
             }
             return info;
 	        
         }else{
        	 System.out.println("USER "+u+" PASS "+p);
        	return new String[]{u,p};
         }
        
    }
    
    /**
	 * Encripta un String con el algoritmo MD5.
	 * 
	 * @return String
	 */
	private String hash(String clear){
		String res=null;
		try{	
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(clear.getBytes());
			int size = b.length;
			StringBuffer h = new StringBuffer(size);
			for (int i = 0; i < size; i++) {
				int u = b[i] & 255; // unsigned conversion
				if (u < 16) {
					h.append("0" + Integer.toHexString(u));
				} else {
					h.append(Integer.toHexString(u));
				}
			}
			res= h.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return res;
	}

	/**
	 * Encripta un String con el algoritmo MD5.
	 * 
	 * @return String
	 */
	public String encriptar(String palabra){
		String pe = "";
		try {
			pe = hash(palabra);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("<strong>Error: Al encriptar el password</strong>");
		}
		return pe;
	}
    
}
