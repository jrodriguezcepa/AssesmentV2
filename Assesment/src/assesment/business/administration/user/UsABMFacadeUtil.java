/*
 * Generated file - Do not edit!
 */
package assesment.business.administration.user;

/**
 * Utility class for UsABMFacade.
 */
public class UsABMFacadeUtil
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
    * @return Home interface for UsABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.user.UsABMFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.administration.user.UsABMFacadeHome) lookupHome(null, assesment.business.administration.user.UsABMFacadeHome.JNDI_NAME, assesment.business.administration.user.UsABMFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for UsABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.user.UsABMFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.administration.user.UsABMFacadeHome) lookupHome(environment, assesment.business.administration.user.UsABMFacadeHome.JNDI_NAME, assesment.business.administration.user.UsABMFacadeHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for UsABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.user.UsABMFacadeLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.business.administration.user.UsABMFacadeLocalHome) lookupHome(null, assesment.business.administration.user.UsABMFacadeLocalHome.JNDI_NAME, assesment.business.administration.user.UsABMFacadeLocalHome.class);
   }

}

