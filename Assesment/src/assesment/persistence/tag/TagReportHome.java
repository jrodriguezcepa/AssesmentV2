/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.tag;

/**
 * Home interface for TagReport.
 */
public interface TagReportHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/TagReport";
   public static final String JNDI_NAME="ejb/TagReport";

   public assesment.persistence.tag.TagReport create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}