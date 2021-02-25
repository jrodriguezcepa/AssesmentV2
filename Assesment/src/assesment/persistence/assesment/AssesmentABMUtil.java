/*
 * Generated file - Do not edit!
 */
package assesment.persistence.assesment;

/**
 * Utility class for AssesmentABM.
 */
public class AssesmentABMUtil
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
    * @return Home interface for AssesmentABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.assesment.AssesmentABMHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.assesment.AssesmentABMHome) lookupHome(null, assesment.persistence.assesment.AssesmentABMHome.COMP_NAME, assesment.persistence.assesment.AssesmentABMHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for AssesmentABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.assesment.AssesmentABMHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.assesment.AssesmentABMHome) lookupHome(environment, assesment.persistence.assesment.AssesmentABMHome.COMP_NAME, assesment.persistence.assesment.AssesmentABMHome.class);
   }

}

