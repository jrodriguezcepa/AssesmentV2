/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.administration.property;

/**
 * Remote interface for PropertyReport.
 * @generated 
 */
public interface PropertyReport
   extends javax.ejb.EJBObject
{

   public assesment.communication.administration.property.PropertyData findPropertyByPrimaryKey( java.lang.String propertyPK,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

}
