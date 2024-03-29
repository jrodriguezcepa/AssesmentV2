/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.assesment;

/**
 * Remote interface for AssesmentABM.
 */
public interface AssesmentABM
   extends javax.ejb.EJBObject
{

   public java.lang.Integer create( assesment.communication.assesment.AssesmentAttributes attributes,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void update( assesment.communication.assesment.AssesmentAttributes attributes,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void delete( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String validateDelete( java.lang.Integer assesment )
      throws java.rmi.RemoteException;

   public void createAccessCode( java.lang.Integer assesment,java.lang.String code,java.lang.Integer number,java.lang.Integer extension,java.lang.String language,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteAccessCode( java.lang.String code,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void updateAccessCodeNumber( java.lang.String code,java.lang.Integer number,java.lang.Integer extension,java.lang.String language,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void decreaseAccessCode( java.lang.String code,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteFeedback( java.lang.Integer assesment,java.lang.String email,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void createFeedback( java.lang.Integer assesment,java.lang.String email,java.lang.Integer report,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteResults( java.lang.Integer assessment,java.lang.String user,int type,boolean onlyTestType,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteResults( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void createLink( java.lang.Integer assessmentId,java.lang.String link,java.lang.String language,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer createGroup( assesment.communication.assesment.GroupData groupData,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void updateGroup( assesment.communication.assesment.GroupData groupData,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer createCategory( assesment.communication.assesment.CategoryData categoryData,java.util.Collection assesments,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void createAssesmentCategory( java.lang.Integer categoryId,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteAssesmentCategory( java.lang.Integer categoryId,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteCategory( java.lang.Integer categoryId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void updateCategory( assesment.communication.assesment.CategoryData categoryData,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void changeAssesmentGroupDate( java.lang.Integer assessmentId,java.lang.Integer categoryId,java.util.Date start,java.util.Date end,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void changeAssesmentReportView( java.lang.Integer assessmentId,java.lang.Integer categoryId,java.lang.Integer show,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void backUpAssessmentOriginal( java.lang.Integer assessmentId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void backUpAssessment( java.lang.Integer assessmentId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

}
