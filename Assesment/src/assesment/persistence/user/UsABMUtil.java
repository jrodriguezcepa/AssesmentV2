/*
 * Generated file - Do not edit!
 */
package assesment.persistence.user;

/**
 * Utility class for UsABM.
 */
public class UsABMUtil
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
    * @return Home interface for UsABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.user.UsABMHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.user.UsABMHome) lookupHome(null, assesment.persistence.user.UsABMHome.COMP_NAME, assesment.persistence.user.UsABMHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for UsABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.user.UsABMHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.user.UsABMHome) lookupHome(environment, assesment.persistence.user.UsABMHome.COMP_NAME, assesment.persistence.user.UsABMHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for UsABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.user.UsABMLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.user.UsABMLocalHome) lookupHome(null, assesment.persistence.user.UsABMLocalHome.COMP_NAME, assesment.persistence.user.UsABMLocalHome.class);
   }

}

