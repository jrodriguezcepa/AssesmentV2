package assesment.presentation.translator.web.administration.user;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jboss.security.plugins.JaasSecurityManager;

import assesment.presentation.translator.web.util.AbstractAction;

public class LogoutAction extends AbstractAction {

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		HttpSession session = request.getSession();
		try {
		    JaasSecurityManager manager = getHome();
		    manager.flushCache();
		    session.invalidate();
		}catch (Exception create) {
		    create.printStackTrace();
		}
		return mapping.findForward("init");
	}

	protected Context getInitialContext() throws Exception {
		Hashtable props = new Hashtable();
		props.put(
			Context.INITIAL_CONTEXT_FACTORY,
			"org.jnp.interfaces.NamingContextFactory");
		props.put(
			Context.URL_PKG_PREFIXES,
			"org.jboss.naming:org.jnp.interfaces");
		props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
		Context ctx = new InitialContext(props);
		return ctx;
	}

	/**
	 * Get the home interface
	 */
	protected JaasSecurityManager getHome() throws Exception {
		Context ctx = this.getInitialContext();
		JaasSecurityManager o = (JaasSecurityManager)ctx.lookup("java:/jaas/assesment");
		return o;
	}
	
	public void logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		try {
			if(request.getUserPrincipal()!=null	){
				JaasSecurityManager manager = getHome();
				manager.flushCache();
				session.invalidate();
			}
		}catch (Exception create) {
		   // create.printStackTrace();
		}
	}

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        return null;
    }

}