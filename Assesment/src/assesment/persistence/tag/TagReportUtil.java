/*
 * Generated file - Do not edit!
 */
package assesment.persistence.tag;

/**
 * Utility class for TagReport.
 */
public class TagReportUtil
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
    * @return Home interface for TagReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.tag.TagReportHome getHome() throws javax.naming.NamingException
   {
         return (assesment.persistence.tag.TagReportHome) lookupHome(null, assesment.persistence.tag.TagReportHome.COMP_NAME, assesment.persistence.tag.TagReportHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for TagReport. Lookup using COMP_NAME
    */
   public static assesment.persistence.tag.TagReportHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.persistence.tag.TagReportHome) lookupHome(environment, assesment.persistence.tag.TagReportHome.COMP_NAME, assesment.persistence.tag.TagReportHome.class);
   }

}

