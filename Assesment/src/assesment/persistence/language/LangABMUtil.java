/*
 * Generated file - Do not edit!
 */
package assesment.persistence.language;

/**
 * Utility class for LangABM.
 */
public class LangABMUtil
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
    * @return Home interface for LangABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.language.LangABMHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.language.LangABMHome) lookupHome(null, assesment.persistence.language.LangABMHome.COMP_NAME, assesment.persistence.language.LangABMHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for LangABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.language.LangABMHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.language.LangABMHome) lookupHome(environment, assesment.persistence.language.LangABMHome.COMP_NAME, assesment.persistence.language.LangABMHome.class);
   }

}

