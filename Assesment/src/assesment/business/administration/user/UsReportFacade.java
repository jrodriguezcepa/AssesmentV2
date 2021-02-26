/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.business.administration.user;

/**
 * Remote interface for UsReportFacade.
 */
public interface UsReportFacade
   extends javax.ejb.EJBObject
{

   public assesment.communication.user.UserData findUserByPrimaryKey( java.lang.String userPK,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findAbitabUser( java.lang.String userPK,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findAbitabUser( java.lang.String userPK,java.lang.String email,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findList( java.util.HashMap parameters,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findListWrongCode( java.util.HashMap parameters,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.util.ListResult findUserList( java.lang.String attrName,java.lang.String attrValue,int first,int count,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.user.UserData findUserByEmail( java.lang.String email,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.user.UserData findBasfUser( java.lang.String ng,java.lang.String cpf,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isFirstAccess( java.lang.String user )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findUserAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findPendingUserAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findTotalUserAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findWebinarParticipants( java.lang.String code,java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findAllWebinarParticipants( java.lang.String wbCode,java.lang.String firstName,java.lang.String lastName,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findUserReportAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findActiveAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findGroups( assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.report.UsersReportDataSource findUsersAssesment( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData,assesment.communication.language.Text messages )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getUserAssesments( java.lang.Integer driver,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer[] getUserPsicoResult( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.report.ResultReportDataSource findGeneralResult( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData,assesment.communication.language.Text messages )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.report.UserResultReportDataSource findUsersResult( java.lang.Integer assesment,java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData,assesment.communication.language.Text messages )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap findUsersResults( java.lang.Integer assesment,java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getUserModules( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData,boolean feedback )
      throws java.lang.Exception, java.rmi.RemoteException;

   public int[] getUserModule( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findAssesmentUsers( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findAssesmentPsiUsers( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findAssesmentPDUsers( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.report.TotalResultReportDataSource findTotalResult( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData,assesment.communication.language.Text messages )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isPsicologicDone( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isPersonalDataDone( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getCorporationsDC( assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap getModuleHashResult( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap findPsicoModuleResult( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findModuleResult( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isAssessmentDone( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData,boolean isPSI )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Date isAssessmentDone( assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean hasResults( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer getUserCode( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer getNextCode( java.lang.String accesscode,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isPsiGreen( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isPsiRed( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isResultGreen( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean isResultRed( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getChampionResult( java.lang.Integer questionCount,java.lang.String countries,java.lang.Integer company,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String getRedirect( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getAccessCode( int assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getAssessmentUsers( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String getElearningURL( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String[][] getCandidatos( assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer getQuestionCount( java.lang.String user,java.lang.Integer module,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.report.AssessmentReportData getAssessmentReport( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer terms( java.lang.Integer assessment,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer terms( java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public int getSendedReportId( java.lang.String loginname,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection getUserList( java.lang.String value,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String[] getPSIanswers( java.lang.String loginname,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String checkDependencies( java.lang.String loginname,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap getUserAssessmentStatus( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findGroupUsers( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap getMultiAssessmentResult( java.lang.Integer multiId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean hasErrors( java.lang.Integer multiId,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findMultiAnswerUsers( java.lang.Integer assessmentId,java.util.Date from,java.util.Date to,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer getFailedAssesments( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap getGroupUsers( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.HashMap getUserGroupCount( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean userExist( java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean userExistsIdNumber( java.lang.String idNumber,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.communication.assesment.GroupData findUserGroup( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.Integer cediUser( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findCediUsers( java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findCediUsers( java.lang.Integer[] cedis,java.lang.String cedi,java.lang.String firstname,java.lang.String lastname,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.lang.String getCurrentTimacUser( java.lang.String cpf,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public boolean validateWebinarCode( java.lang.String webinarCode,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public assesment.persistence.administration.tables.AssessmentUserData getAssessmentUserData( java.lang.String user,java.lang.String code,java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findCediMissingUsers( java.lang.Integer[] cedis,java.lang.Integer type,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

   public java.util.Collection findCediMissingUsers( java.lang.String cedi,java.lang.String firstName,java.lang.String lastName,java.lang.Integer[] cedis,java.lang.Integer type,assesment.communication.administration.user.UserSessionData userSessionData )
      throws java.lang.Exception, java.rmi.RemoteException;

}