/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.business.assesment;

/**
 * Local interface for AssesmentReportFacade.
 */
public interface AssesmentReportFacadeLocal
   extends javax.ejb.EJBLocalObject
{

   public assesment.communication.assesment.AssesmentAttributes findAssesmentAttributes( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.AssesmentData findAssesment( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection[] getAssessments( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.util.ListResult findAssesments( java.lang.String name,java.lang.String corporation,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.util.ListResult findAssesments( java.lang.String name,java.lang.String corporation,java.lang.String archived,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.administration.AccessCodeData getAccessCode( java.lang.String accesscode,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getAccessCodes( assesment.communication.administration.user.UserSessionData userSessionData,assesment.communication.language.Text messages ) throws java.lang.Exception;

   public java.lang.String[] findReportData( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getAssesmentFeedback( java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.FeedbackData getFeedback( java.lang.Integer assesment,java.lang.String email,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getAssesmentsQuestionCount( assesment.communication.assesment.AssesmentAttributes attributes,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getAssesmentsQuestionCount( assesment.communication.assesment.AssesmentAttributes attributes,assesment.communication.administration.user.UserSessionData userSessionData,boolean all ) throws java.lang.Exception;

   public java.lang.Integer getNewHireValue( java.lang.String user,java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getWrongAnswers( java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Hashtable getUserAnswerCount( java.lang.String assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getAssessmentResults( java.lang.Integer assessment,java.util.Date start,java.util.Date end,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer[] getAvailableAssessments( java.lang.String user,java.lang.Integer corporation,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.report.AssessmentReportData getAssessmentReport( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.report.AssessmentReportData getAssessmentReportByCedi( java.lang.Integer assessment,java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.report.AssessmentReportData getAssessmentReport( java.lang.Integer assessment,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getAssessmentUsers( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.GroupData getUserGroup( java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.GroupData findGroup( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.GroupData findNoPdfGroup( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.CategoryData findCategory( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findAssessmentForGroup( java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.AssesmentData findAssesmentByModule( java.lang.Integer moduleId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getUserGroupResults( java.lang.Integer groupId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getUserCediResults( java.lang.Integer groupId,java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.administration.UserMultiAssessmentData getMultiAssessment( java.lang.String user,java.lang.Integer assessmentId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.administration.UserMultiAssessmentData getMultiAssessment( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findGroups( java.lang.String name,java.lang.Integer corporation,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findWebinars( java.lang.String wbCode,java.lang.String wb1,java.lang.String wb2,java.lang.String wb3,java.lang.String wb4,java.lang.String wb5,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String[] getWebinarAdvance( java.lang.String wbCode,java.lang.String assesmentId,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getUserSpecificQuestion( java.lang.String questions,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getUserCediResults( java.lang.Integer[] cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer getAssesmentId( java.lang.String user,java.lang.String webinarCode,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findMutualAssesmentResults( java.lang.Integer assesment,java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getWebinarPersonalData( java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.GroupUsersData getGroupUsersResults( java.lang.Integer groupId,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.util.ListResult findAssesmentsbkp( java.lang.String name,java.lang.String corporation,java.lang.String archived,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.assesment.AssesmentData findAssesmentbkp( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

}
