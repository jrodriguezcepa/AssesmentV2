/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.assesment;

/**
 * Local home interface for AssesmentReport.
 */
public interface AssesmentReportLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/AssesmentReportLocal";
   public static final String JNDI_NAME="AssesmentReportLocal";

   public assesment.persistence.assesment.AssesmentReportLocal create()
      throws javax.ejb.CreateException;

}