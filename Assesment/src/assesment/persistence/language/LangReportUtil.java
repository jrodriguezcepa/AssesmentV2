/*
 * Generated file - Do not edit!
 */
package assesment.persistence.language;

/**
 * Utility class for LangReport.
 */
public class LangReportUtil
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
    * @return Home interface for LangReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.language.LangReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.language.LangReportHome) lookupHome(null, assesment.persistence.language.LangReportHome.COMP_NAME, assesment.persistence.language.LangReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for LangReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.language.LangReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.language.LangReportHome) lookupHome(environment, assesment.persistence.language.LangReportHome.COMP_NAME, assesment.persistence.language.LangReportHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for LangReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.language.LangReportLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.language.LangReportLocalHome) lookupHome(null, assesment.persistence.language.LangReportLocalHome.COMP_NAME, assesment.persistence.language.LangReportLocalHome.class);
   }

}

