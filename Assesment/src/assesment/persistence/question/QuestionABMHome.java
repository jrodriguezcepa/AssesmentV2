/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.question;

/**
 * Home interface for QuestionABM.
 */
public interface QuestionABMHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/QuestionABM";
   public static final String JNDI_NAME="ejb/QuestionABM";

   public assesment.persistence.question.QuestionABM create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
