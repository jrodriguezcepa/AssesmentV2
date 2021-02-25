/*
 * Generated file - Do not edit!
 */
package assesment.persistence.assesment;

/**
 * Utility class for AssesmentReport.
 */
public class AssesmentReportUtil
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
    * @return Home interface for AssesmentReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.assesment.AssesmentReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.assesment.AssesmentReportHome) lookupHome(null, assesment.persistence.assesment.AssesmentReportHome.COMP_NAME, assesment.persistence.assesment.AssesmentReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for AssesmentReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.assesment.AssesmentReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.assesment.AssesmentReportHome) lookupHome(environment, assesment.persistence.assesment.AssesmentReportHome.COMP_NAME, assesment.persistence.assesment.AssesmentReportHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for AssesmentReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.assesment.AssesmentReportLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.assesment.AssesmentReportLocalHome) lookupHome(null, assesment.persistence.assesment.AssesmentReportLocalHome.COMP_NAME, assesment.persistence.assesment.AssesmentReportLocalHome.class);
   }

}

