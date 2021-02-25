package assesment.business.ws.cache;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.Context;

import assesment.business.AssesmentAccessRemote;
import assesment.business.ws.client.security.User;
import assesment.communication.security.SecurityConstants;


public class CacheManager {
	private static Long cKey;
	private static class CacheClean implements Runnable {
		public void run() {
			boolean exit=false;
			while(!exit)
			try{
				synchronized (CacheManager.instance) {
					HashMap<Long, SysCache> cacheaux=new HashMap<Long, SysCache>(CacheManager.instance.cache);
					Iterator<Long> iter=cacheaux.keySet().iterator();
					while(iter.hasNext()){
						Long key=(Long)iter.next();
						SysCache sys=cacheaux.get(key);
						Calendar cal=Calendar.getInstance();
						cal.setTime(new Date());
						cal.add(Calendar.HOUR,-2);
						Calendar cal2=Calendar.getInstance();
						cal2.setTime(sys.getLastAccess());
						//Si no accede hace 2hs lo quito de la cache
						if(cal.compareTo(cal2)>0){
							CacheManager.instance.cache.remove(key);
							System.out.println(new Date().toString()+"-CahceClean se quita "+sys.getSys().getUserSessionData().getFilter().getLoginName()+" ultimo acceso "+sys.getLastAccess().toString());
						}
					}
				}
				
				Thread.sleep(1000*60*30);//Corre cada 30 minutos
			}catch(InterruptedException ie){
				ie.printStackTrace();
				System.out.println("CahceClean sale apaga");
				exit=true;
			}catch(Throwable th){
				th.printStackTrace();
			}
		}
	} 
	
	
	private static CacheManager instance;
	
	private HashMap<Long, SysCache> cache;
	private Long key;
	
	private CacheManager(){
		cache=new HashMap<Long, SysCache>();
		key=1l;
	
	}	
	
	public static synchronized CacheManager getInstance(){
		if(instance==null){
			instance=new CacheManager();
			new Thread(new CacheClean()).start();
		}
		
		return instance;
	}
	
	public AssesmentAccessRemote getSys(User u) throws Exception{
		AssesmentAccessRemote sys=null;
		synchronized (instance) {
			if(u.getcKey()==null){
				//Primer acceso
				sys=login(u);
			}else{
				//Tiene que estar en la ca
				if(cache.containsKey(u.getcKey())){
					//Sigue en cache no fué quitada
					sys=cache.get(u.getcKey()).getSys();
				}else{
					sys=login(u);
				}
			}
			
			if(sys!=null && (u.getcKey()==null ||!cache.containsKey(u.getcKey()))){
				SysCache sc=new SysCache();
				sc.setSys(sys);
				u.setcKey(key);
				cache.put(key++, sc);
				key=key%99999999999999999l+1;
			}else if(cache.containsKey(u.getcKey())){
				cache.get(u.getcKey()).setLastAccess(new Date());
			}
			
		}
		
		return sys;
	}
	
	public AssesmentAccessRemote getSys(User u, boolean horizontalAccess) throws Exception{
		AssesmentAccessRemote sys=getSys(u);
		sys.setHorizontalAccess(horizontalAccess);
		if(!horizontalAccess){
			return sys;	
		}else{
			return null;
		}
		
		
	}
	
	public void logout(User u) throws Exception{
		cache.remove(u.getcKey());
	}

	public AssesmentAccessRemote login(User u){
		if(SecurityConstants.isProductionServer()){
			System.setProperty("java.security.auth.login.config","/home/jboss51/assesmentAuth.config");
		}else{
			System.setProperty("java.security.auth.login.config","C:/jboss51/assesmentAuth.config");
		}
        
		 Properties env = new Properties();
       //env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
      // env.setProperty(Context.URL_PKG_PREFIXES, "org.jnp.interfaces.NamingContextFactory");
       env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.LoginInitialContextFactory");  
       env.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");  
       env.put(Context.SECURITY_PROTOCOL, "assesment");  
       if(SecurityConstants.isProductionServer()){
    	   env.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099/");    
       }else{
    	   env.setProperty(Context.PROVIDER_URL, "jnp://localhost:9099/"); 
       }
       
       env.put(Context.SECURITY_PRINCIPAL, u.getUserlogin()); //Usuario
       env.put(Context.SECURITY_CREDENTIALS, u.getUserpassword());//Password
       
       try {
			AssesmentAccessRemote sys=new AssesmentAccessRemote(u.getUserlogin(),env);
			return sys;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       return null;
       
	}
}
