package assesment.persistence.administration.jboss;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;

import javax.management.Notification;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.jboss.jmx.adaptor.rmi.RMIAdaptor;
import org.jboss.system.ListenerServiceMBeanSupport;

public class ActiveSessionsListener extends ListenerServiceMBeanSupport 
implements ActiveSessionsListenerMBean{

	    public ActiveSessionsListener() {       
	    }

	    /**
	     * Este es el método que será invocado cuando se reciba una notificación de un MBean al que estemos suscritos. */
	    public void handleNotification2(Notification notif, Object handback) {
		Integer activeSessions = new Integer(0);
		System.out.println("---- RECIBO UNA NOTIFICACION -----");
		System.out.println("notif.getType():" + notif.getType());
		System.out.println("notif.getTimeStamp():" + notif.getTimeStamp());
		System.out.println("notif..getUserData():" + notif.getUserData());

		// Creamos las propiedades para el acceso a JNDI.        
		Hashtable props = new Hashtable();
		props.put("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.put("java.naming.factory.url.pkgs",
				"org.jboss.naming:org.jnp.interfaces");
		props.put("java.naming.provider.url", "jnp://localhost:1099");

		// Creamos el contexto JNDI
		InitialContext context = null;

		try {
			context = new InitialContext(props);

			/* Buscamos el Adaptador JNDI que está enlazado en:
			 "jmx/invoker/RMIAdaptor" */
			RMIAdaptor rmiserver = null;

			rmiserver = (RMIAdaptor) context.lookup("jmx/invoker/RMIAdaptor");

			/* Le pedimos al servidor de MBeans RMI el Bean llamado:
			 jboss.web:type=Manager,path=/assesment,host=localhost  */
			ObjectName beanName = null;

			beanName = new ObjectName(
					"jboss.web:type=Manager,path=/assesment,host=localhost");

			// Obtenemos el valor del atributo activeSessions del MBean.
			activeSessions = new Integer(rmiserver.getAttribute(beanName,
					"activeSessions").toString());
			System.out.println(rmiserver.getAttribute(beanName,
					"activeSessions").toString());

			DataSource ds = (DataSource) context.lookup("java:/assesmentDS");

			Connection con = ds.getConnection();
			Statement s = con.createStatement();
			Date date = new Date();
			s.execute("insert into usersession values ('" + date + "',"
					+ activeSessions + ");");
			s.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sobreescribimos el metodo startService para suscribirnos a las notificaciones cuando arranquemos  */

	protected void startService() throws Exception {
		/* El valor true es para indicarle que si se registran nuevas instancias de  MBeans en el servidor de MBeans que cumplen nuestros criterios de subscripcion, nos suscriban tambien a sus notificaciones*/
		System.out.println("STARTING LISTENER");
		super.subscribe(true);
	}

	/**
	 * Para darnos de baja cuando se pare nuestro MBean.
	 */
	protected void stopService() throws Exception {
		System.out.println("STOPPING LISTENER");
		super.unsubscribe();
	}
	     
	    
	    
}
