/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.business.administration.user;

/**
 * Home interface for UsABMFacade.
 */
public interface UsABMFacadeHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/UsABMFacade";
   public static final String JNDI_NAME="ejb/UsABMFacade";

   public assesment.business.administration.user.UsABMFacade create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
