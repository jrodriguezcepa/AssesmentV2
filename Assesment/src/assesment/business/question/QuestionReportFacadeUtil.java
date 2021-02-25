/*
 * Generated file - Do not edit!
 */
package assesment.business.question;

/**
 * Utility class for QuestionReportFacade.
 */
public class QuestionReportFacadeUtil
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
    * @return Home interface for QuestionReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.question.QuestionReportFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.question.QuestionReportFacadeHome) lookupHome(null, assesment.business.question.QuestionReportFacadeHome.JNDI_NAME, assesment.business.question.QuestionReportFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for QuestionReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.question.QuestionReportFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.question.QuestionReportFacadeHome) lookupHome(environment, assesment.business.question.QuestionReportFacadeHome.JNDI_NAME, assesment.business.question.QuestionReportFacadeHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for QuestionReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.question.QuestionReportFacadeLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.business.question.QuestionReportFacadeLocalHome) lookupHome(null, assesment.business.question.QuestionReportFacadeLocalHome.JNDI_NAME, assesment.business.question.QuestionReportFacadeLocalHome.class);
   }

}

