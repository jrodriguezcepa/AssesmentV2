/*
 * Generated file - Do not edit!
 */
package assesment.business.administration.property;

/**
 * Utility class for PropertyABMFacade.
 */
public class PropertyABMFacadeUtil
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
    * @return Home interface for PropertyABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.property.PropertyABMFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.administration.property.PropertyABMFacadeHome) lookupHome(null, assesment.business.administration.property.PropertyABMFacadeHome.JNDI_NAME, assesment.business.administration.property.PropertyABMFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for PropertyABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.property.PropertyABMFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.administration.property.PropertyABMFacadeHome) lookupHome(environment, assesment.business.administration.property.PropertyABMFacadeHome.JNDI_NAME, assesment.business.administration.property.PropertyABMFacadeHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for PropertyABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.administration.property.PropertyABMFacadeLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.business.administration.property.PropertyABMFacadeLocalHome) lookupHome(null, assesment.business.administration.property.PropertyABMFacadeLocalHome.JNDI_NAME, assesment.business.administration.property.PropertyABMFacadeLocalHome.class);
   }

}

