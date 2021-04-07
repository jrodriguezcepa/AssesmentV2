/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.user;

/**
 * Local interface for UsReport.
 * @generated 
 */
public interface UsReportLocal
   extends javax.ejb.EJBLocalObject
{

   public boolean login( java.lang.String loginName,java.lang.String passwd ) throws java.lang.Exception;

   public assesment.communication.user.UserData findUserByPrimaryKey( java.lang.String loginName,java.lang.Integer corporationId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUserList( java.lang.String value,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAbitabUser( java.lang.String loginName,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAbitabUser( java.lang.String loginName,java.lang.String email,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findList( java.util.HashMap parameters,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findListWrongCode( java.util.HashMap parameters,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.util.ListResult findList( java.lang.String value,assesment.communication.administration.user.UserSessionData userSessionData,int attribute,int first,int count ) throws java.lang.Exception;

   public assesment.communication.user.UserData findUserByEmail( java.lang.String email,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.user.UserData findBasfUser( java.lang.String ng,java.lang.String cpf,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean isFirstAccess( java.lang.String user ) throws java.lang.Exception;

   public java.util.Collection findUserAssesments( java.lang.String user,boolean all,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findTotalUserAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findWebinarParticipants( java.lang.String code,java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAllWebinarParticipants( java.lang.String wbCode,java.lang.String firstName,java.lang.String lastName,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUserAssesments( java.lang.Integer driver,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findUserReportAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findActiveAssesments( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findGroups( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUserAssesmentsCount( assesment.communication.assesment.AssesmentAttributes assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getUserAssesmentsCount( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getNextCode( java.lang.String accesscode,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUserModules( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData,boolean feedback ) throws java.lang.Exception;

   public int[] getUserModule( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findGeneralResults( java.lang.Integer assesment,boolean psico,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findTotalResults( java.lang.Integer assesment,boolean psico,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findUserResults( java.lang.Integer assesment,java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Hashtable getModulesCount( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAssesmentUsers( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAssesmentPsiUsers( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAssesmentPDUsers( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean isPsicologicDone( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean isPersonalDataModuleDone( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getPsicoResult( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer[] getUserPsicoResult( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getCorporationsDC( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getDriversDC( java.lang.Integer corporation,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findModuleResult( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findPsicoModuleResult( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean isAssessmentDone( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Date isAssessmentDone( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean hasResults( java.lang.String user,java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getUserCode( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer[] getResultCount( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer[] getCompletedResultCount( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getChampionResult( java.lang.Integer questionCount,java.lang.String countries,java.lang.Integer company,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String getRedirect( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getAccessCode( int assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getAssessmentUsers( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String getElearningURL( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getCandidatos( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getLastAccess( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean existUserAssessment( java.lang.String login,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getQuestionCount( java.lang.String user,java.lang.Integer module,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUsersReport( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUsersReportByCedi( java.lang.Integer assessment,java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUsersReport( java.lang.Integer assessment,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getNotStartedUsersReport( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getNotStartedUsersReport( java.lang.Integer assessment,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUserReport( java.lang.String user,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getWRTUserAnswers( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getWRTUserAnswersByCedi( java.lang.Integer assessment,java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getWRTUserAnswers( java.lang.Integer assessment,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer terms( java.lang.Integer assessment,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer terms( java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public int getSendedReportId( java.lang.String loginname,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String[] getPSIanswers( java.lang.String loginname,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String checkDependencies( java.lang.String loginname,java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getUserAssessmentStatus( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findGroupUsers( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getMultiAssessmentResult( java.lang.Integer multiId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean hasErrors( java.lang.Integer multiId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findMultiAnswerUsers( java.lang.Integer assessmentId,java.util.Date from,java.util.Date to,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getFailedAssesments( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getUserGroupCount( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getGroupUsers( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean userExist( java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findResults( java.lang.Integer assessment,int result,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.GroupData findUserGroup( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer cediUser( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findCediUsers( java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findCediUsers( java.lang.Integer[] cedis,java.lang.String cedi,java.lang.String firstname,java.lang.String lastname,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findCediMissingUsers( java.lang.Integer[] cedis,java.lang.Integer type,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findCediMissingUsers( java.lang.String cedi,java.lang.String firstName,java.lang.String lastName,java.lang.Integer[] cedis,java.lang.Integer type,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean userExistsIdNumber( java.lang.String idNumber,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String getNextTimacUser( java.lang.String cpf,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String getCurrentTimacUser( java.lang.String cpf,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public boolean validateWebinarCode( java.lang.String webinarCode,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.persistence.administration.tables.AssessmentUserData getAssessmentUserData( java.lang.String user,java.lang.String code,java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Object[] existTimacUser( java.lang.String id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getUserAssesmentsCountbkp( assesment.communication.assesment.AssesmentAttributes assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

}
