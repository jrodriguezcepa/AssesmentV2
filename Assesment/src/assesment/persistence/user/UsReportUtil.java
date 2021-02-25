/*
 * Generated file - Do not edit!
 */
package assesment.persistence.user;

/**
 * Utility class for UsReport.
 * @generated 
 */
public class UsReportUtil
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
    * @return Home interface for UsReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.user.UsReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.user.UsReportHome) lookupHome(null, assesment.persistence.user.UsReportHome.COMP_NAME, assesment.persistence.user.UsReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for UsReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.user.UsReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.user.UsReportHome) lookupHome(environment, assesment.persistence.user.UsReportHome.COMP_NAME, assesment.persistence.user.UsReportHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for UsReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.user.UsReportLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.user.UsReportLocalHome) lookupHome(null, assesment.persistence.user.UsReportLocalHome.COMP_NAME, assesment.persistence.user.UsReportLocalHome.class);
   }

}

