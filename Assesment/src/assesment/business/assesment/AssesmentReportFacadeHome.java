/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.business.assesment;

/**
 * Home interface for AssesmentReportFacade.
 */
public interface AssesmentReportFacadeHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/AssesmentReportFacade";
   public static final String JNDI_NAME="ejb/AssesmentReportFacade";

   public assesment.business.assesment.AssesmentReportFacade create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
