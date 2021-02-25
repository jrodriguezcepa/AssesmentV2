/*
 * Generated file - Do not edit!
 */
package assesment.persistence.module;

/**
 * Utility class for ModuleABM.
 */
public class ModuleABMUtil
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
    * @return Home interface for ModuleABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.module.ModuleABMHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.module.ModuleABMHome) lookupHome(null, assesment.persistence.module.ModuleABMHome.COMP_NAME, assesment.persistence.module.ModuleABMHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for ModuleABM. Lookup using COMP_NAME
    */
   public static assesment.persistence.module.ModuleABMHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.module.ModuleABMHome) lookupHome(environment, assesment.persistence.module.ModuleABMHome.COMP_NAME, assesment.persistence.module.ModuleABMHome.class);
   }

}

