/*
 * Generated file - Do not edit!
 */
package assesment.business.assesment;

/**
 * Utility class for AssesmentABMFacade.
 */
public class AssesmentABMFacadeUtil
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
    * @return Home interface for AssesmentABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.assesment.AssesmentABMFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.assesment.AssesmentABMFacadeHome) lookupHome(null, assesment.business.assesment.AssesmentABMFacadeHome.JNDI_NAME, assesment.business.assesment.AssesmentABMFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for AssesmentABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.assesment.AssesmentABMFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.assesment.AssesmentABMFacadeHome) lookupHome(environment, assesment.business.assesment.AssesmentABMFacadeHome.JNDI_NAME, assesment.business.assesment.AssesmentABMFacadeHome.class);
   }

}

