/*
 * Generated by XDoclet - Do not edit!
 */
package assesment.persistence.question;

/**
 * Local interface for QuestionReport.
 */
public interface QuestionReportLocal
   extends javax.ejb.EJBLocalObject
{

   public assesment.communication.question.QuestionData findQuestion( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData,int type ) throws java.lang.Exception;

   public java.util.Collection getPsicoQuestions( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findQuestionResults( java.lang.Integer question,int type,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findQuestionReportByModule( java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection findIrelevantQuestionReportByModule( java.lang.Integer module,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer findAssesmentId( java.lang.Integer question,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String[] getAgenSex( java.lang.String user,java.lang.Integer assesment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String[][] getWrongAnswers( java.lang.Integer assessment,java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getEmailByQuestion( java.lang.Integer assessment,java.lang.String user,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String getQuestionImage( java.lang.Integer module,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getQuestionReport( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getQuestionReportByCedi( java.lang.Integer assessment,java.lang.Integer cedi,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getQuestionReport( java.lang.Integer assessment,java.lang.Integer group,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.HashMap getFullAnswers( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.String[][] getCompleteAnswers( java.lang.Integer assessment,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.lang.Integer[][] getOptionAnswers( java.lang.Integer assessment,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getVideos( assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getSurveyResult( java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getTotalAnswers( java.lang.Integer assessment,assesment.communication.language.Text messages,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public assesment.communication.question.QuestionData findQuestionBKP( java.lang.Integer id,assesment.communication.administration.user.UserSessionData userSessionData,int type ) throws java.lang.Exception;

   public java.lang.String[][] getCompleteAnswersBKP( java.lang.Integer assessment,java.lang.String login,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getQuestionReportBKP( java.lang.Integer assessment,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

   public java.util.Collection getQuestionReportByDivision( java.lang.Integer assessment,java.lang.String division,assesment.communication.administration.user.UserSessionData userSessionData ) throws java.lang.Exception;

}
