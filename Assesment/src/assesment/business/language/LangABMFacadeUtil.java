/*
 * Generated file - Do not edit!
 */
package assesment.business.language;

/**
 * Utility class for LangABMFacade.
 */
public class LangABMFacadeUtil
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
    * @return Home interface for LangABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.language.LangABMFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.language.LangABMFacadeHome) lookupHome(null, assesment.business.language.LangABMFacadeHome.JNDI_NAME, assesment.business.language.LangABMFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for LangABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.language.LangABMFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.language.LangABMFacadeHome) lookupHome(environment, assesment.business.language.LangABMFacadeHome.JNDI_NAME, assesment.business.language.LangABMFacadeHome.class);
   }

}

