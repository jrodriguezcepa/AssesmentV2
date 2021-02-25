/*
 * Generated file - Do not edit!
 */
package assesment.business.administration.corporation;

/**
 * Utility class for CorpABMFacade.
 */
public class CorpABMFacadeUtil
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
    * @return Home interface for CorpABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.corporation.CorpABMFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.administration.corporation.CorpABMFacadeHome) lookupHome(null, assesment.business.administration.corporation.CorpABMFacadeHome.JNDI_NAME, assesment.business.administration.corporation.CorpABMFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for CorpABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.corporation.CorpABMFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.administration.corporation.CorpABMFacadeHome) lookupHome(environment, assesment.business.administration.corporation.CorpABMFacadeHome.JNDI_NAME, assesment.business.administration.corporation.CorpABMFacadeHome.class);
   }

}

