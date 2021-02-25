/*
 * Generated file - Do not edit!
 */
package assesment.business.administration.corporation;

/**
 * Utility class for CorpReportFacade.
 */
public class CorpReportFacadeUtil
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
    * @return Home interface for CorpReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.corporation.CorpReportFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.administration.corporation.CorpReportFacadeHome) lookupHome(null, assesment.business.administration.corporation.CorpReportFacadeHome.JNDI_NAME, assesment.business.administration.corporation.CorpReportFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for CorpReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.corporation.CorpReportFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.administration.corporation.CorpReportFacadeHome) lookupHome(environment, assesment.business.administration.corporation.CorpReportFacadeHome.JNDI_NAME, assesment.business.administration.corporation.CorpReportFacadeHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for CorpReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.corporation.CorpReportFacadeLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.business.administration.corporation.CorpReportFacadeLocalHome) lookupHome(null, assesment.business.administration.corporation.CorpReportFacadeLocalHome.JNDI_NAME, assesment.business.administration.corporation.CorpReportFacadeLocalHome.class);
   }

}

