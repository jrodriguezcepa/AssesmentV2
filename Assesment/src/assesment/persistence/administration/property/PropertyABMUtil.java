/*
 * Generated file - Do not edit!
 */
package assesment.persistence.administration.property;

/**
 * Utility class for PropertyABM.
 */
public class PropertyABMUtil
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
    * @return Home interface for PropertyABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.administration.property.PropertyABMHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.administration.property.PropertyABMHome) lookupHome(null, assesment.persistence.administration.property.PropertyABMHome.COMP_NAME, assesment.persistence.administration.property.PropertyABMHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for PropertyABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.administration.property.PropertyABMHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.administration.property.PropertyABMHome) lookupHome(environment, assesment.persistence.administration.property.PropertyABMHome.COMP_NAME, assesment.persistence.administration.property.PropertyABMHome.class);
   }

}

