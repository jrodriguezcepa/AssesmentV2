/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.business.tag;

/**
 * Home interface for TagABMFacade.
 */
public interface TagABMFacadeHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/TagABMFacade";
   public static final String JNDI_NAME="ejb/TagABMFacade";

   public assesment.business.tag.TagABMFacade create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
