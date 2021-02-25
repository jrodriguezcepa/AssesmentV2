/*
 * Generated file - Do not edit!
 */
package assesment.persistence.corporation;

/**
 * Utility class for CorpReport.
 */
public class CorpReportUtil
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
    * @return Home interface for CorpReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.corporation.CorpReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.corporation.CorpReportHome) lookupHome(null, assesment.persistence.corporation.CorpReportHome.COMP_NAME, assesment.persistence.corporation.CorpReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for CorpReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.corporation.CorpReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.corporation.CorpReportHome) lookupHome(environment, assesment.persistence.corporation.CorpReportHome.COMP_NAME, assesment.persistence.corporation.CorpReportHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for CorpReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.corporation.CorpReportLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.corporation.CorpReportLocalHome) lookupHome(null, assesment.persistence.corporation.CorpReportLocalHome.COMP_NAME, assesment.persistence.corporation.CorpReportLocalHome.class);
   }

}

