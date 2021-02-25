/*
 * Generated file - Do not edit!
 */
package assesment.business.tag;

/**
 * Utility class for TagReportFacade.
 */
public class TagReportFacadeUtil
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
    * @return Home interface for TagReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.tag.TagReportFacadeHome getHome() throws javax.naming.NamingException
   {
         return (assesment.business.tag.TagReportFacadeHome) lookupHome(null, assesment.business.tag.TagReportFacadeHome.JNDI_NAME, assesment.business.tag.TagReportFacadeHome.class);
   }

   /**
    * Obtain remote home interface from parameterised initial context
    * @param environment Parameters to use for creating initial context
    * @return Home interface for TagReportFacade. Lookup using JNDI_NAME
    */
   public static assesment.business.tag.TagReportFacadeHome getHome( java.util.Hashtable environment ) throws javax.naming.NamingException
   {
       return (assesment.business.tag.TagReportFacadeHome) lookupHome(environment, assesment.business.tag.TagReportFacadeHome.JNDI_NAME, assesment.business.tag.TagReportFacadeHome.class);
   }

}

