/*
 * Generated file - Do not edit!
 */
package assesment.persistence.module;

/**
 * Utility class for ModuleReport.
 */
public class ModuleReportUtil
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
    * @return Home interface for ModuleReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.module.ModuleReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.module.ModuleReportHome) lookupHome(null, assesment.persistence.module.ModuleReportHome.COMP_NAME, assesment.persistence.module.ModuleReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for ModuleReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.module.ModuleReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.module.ModuleReportHome) lookupHome(environment, assesment.persistence.module.ModuleReportHome.COMP_NAME, assesment.persistence.module.ModuleReportHome.class);
   }

   /**
    * Obtain local home interface from default initial context
    * @return Local home interface for ModuleReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.module.ModuleReportLocalHome getLocalHome() throws javax.naming.NamingException
   {
      return (assesment.persistence.module.ModuleReportLocalHome) lookupHome(null, assesment.persistence.module.ModuleReportLocalHome.COMP_NAME, assesment.persistence.module.ModuleReportLocalHome.class);
   }

}

