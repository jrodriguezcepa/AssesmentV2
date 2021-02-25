/*
 * Generated file - Do not edit!
 */
package assesment.persistence.question;

/**
 * Utility class for QuestionReport.
 */
public class QuestionReportUtil
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
    * @return Home interface for QuestionReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.question.QuestionReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.question.QuestionReportHome) lookupHome(null, assesment.persistence.question.QuestionReportHome.COMP_NAME, assesment.persistence.question.QuestionReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for QuestionReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.question.QuestionReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.question.QuestionReportHome) lookupHome(environment, assesment.persistence.question.QuestionReportHome.COMP_NAME, assesment.persistence.question.QuestionReportHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for QuestionReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.question.QuestionReportLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.question.QuestionReportLocalHome) lookupHome(null, assesment.persistence.question.QuestionReportLocalHome.COMP_NAME, assesment.persistence.question.QuestionReportLocalHome.class);
   }

}

