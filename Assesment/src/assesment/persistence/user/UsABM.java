/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.user;

/**
 * Remote interface for UsABM.
 */
public interface UsABM
   extends javax.ejb.EJBObject
{

   public void userCreate( assesment.communication.user.UserData data,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userGroupCreate( assesment.communication.user.UserData data,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userCediGroupCreate( assesment.communication.user.UserData data,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userCreate( assesment.communication.user.UserData data,java.lang.String[] assesments,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userCreateFromFile( assesment.communication.user.UserData data,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userUpdate( assesment.communication.user.UserData data,java.lang.Integer corporationId,assesment.communication.administration.user.UserSessionData userSessionData,boolean validate )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void updateUserName( java.lang.String firstName,java.lang.String lastName,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userResetPassword( assesment.communication.user.UserData data,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userResetOwnPassword( java.lang.String password,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userSwitchLanguage( java.lang.String lngName,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userDelete( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean userExist( java.lang.String nick,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void resetPassword( assesment.communication.user.UserData userData,java.lang.String password,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void saveLogin( long date,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void associateAssesment( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteAssociatedAssesment( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public int[] saveAnswer( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.UserAnswerData answer,assesment.communication.administration.user.UserSessionData userSessionData,boolean psico,boolean feedback )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void savePsicoResults( java.lang.String user,java.lang.Integer assesment,int[] values,int psiId,int psiTestId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void saveNewHire( java.lang.String user,java.lang.Integer assesment,java.lang.Integer value,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void setRedirect( java.lang.String userId,java.lang.String redirect,java.lang.Integer eLearningUser,java.lang.Boolean eLearningEnabled,java.lang.Integer assesmentId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void setEndDate( java.lang.String userId,java.lang.Integer assesmentId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void setElearningEnabled( java.lang.String user,java.lang.Integer assessment,boolean result,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.user.UserData createBasfUser( java.lang.Integer assesment,java.lang.String copyUser,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.user.UserData createBasfUserAssessment( java.lang.String login,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void acceptTerms( assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.persistence.user.tables.UserPassword getUserPassword( java.lang.String loginname )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userPasswordCreate( assesment.persistence.user.tables.UserPassword userPassword )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void savePSIaux( java.lang.String answers,int[] values,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void failAssessment( java.lang.Integer assessmentId,java.lang.String user,java.util.Date date,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void saveMultiAnswer( java.lang.Integer questionId,java.lang.Integer answerId,java.lang.Integer multiId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteGroupUsers( java.lang.Integer group,java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void checkGroupAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void userChangeGroup( java.lang.String user,java.lang.Integer groupOld,java.lang.Integer groupNew,assesment.communication.administration.user.UserSessionData userRequest )
      throws java.lang.Exception, java.rmi.RemoteException;

   public void deleteSendedReport( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

}
