/*
 * Generated file - Do not edit!
 */
package assesment.business.question;

/**
 * Utility class for QuestionABMFacade.
 */
public class QuestionABMFacadeUtil
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
    * @return Home interface for QuestionABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.question.QuestionABMFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.question.QuestionABMFacadeHome) lookupHome(null, assesment.business.question.QuestionABMFacadeHome.JNDI_NAME, assesment.business.question.QuestionABMFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for QuestionABMFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.question.QuestionABMFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.question.QuestionABMFacadeHome) lookupHome(environment, assesment.business.question.QuestionABMFacadeHome.JNDI_NAME, assesment.business.question.QuestionABMFacadeHome.class);
   }

}

