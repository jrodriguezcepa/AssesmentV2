/*
 * Generated file - Do not edit!
 */
package assesment.business.administration.user;

/**
 * Utility class for UsReportFacade.
 */
public class UsReportFacadeUtil
{

   private static Object lookupHome(java.util.Hashtable environment, String jndiName, Class narrowTo) throws javax.naming.NamingException {
      // Obtain initial context
      javax.naming.InitialContext initialContext = new javax.naming.InitialContext(environment);
      try {
         Object objRef = initialContext.lookup(jndiName);
         // only narrow if necessary
         if (java.rmi.Remote.class.isAssignableFrom(narrowTo))
            return javax.rmi.PortableRemoteObject.narrow(objRef, narrowTo);
         else
            return objRef;
      } finally {
         initialContext.close();
      }
   }

   // Home interface lookup methods

   /**
    * Obtain remote home interface from default initial context
    * @return Home interface for UsReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.user.UsReportFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.administration.user.UsReportFacadeHome) lookupHome(null, assesment.business.administration.user.UsReportFacadeHome.JNDI_NAME, assesment.business.administration.user.UsReportFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for UsReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.user.UsReportFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.administration.user.UsReportFacadeHome) lookupHome(environment, assesment.business.administration.user.UsReportFacadeHome.JNDI_NAME, assesment.business.administration.user.UsReportFacadeHome.class);
   }

}

