<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@ page language="java" isErrorPage="true"
	import="java.util.*"
	import="assesment.communication.module.*"
	import="assesment.presentation.translator.web.util.*"
	import="assesment.communication.language.Text"
	import="assesment.communication.question.QuestionData"
%>
<%@page import="assesment.communication.administration.user.UserSessionData"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>
<%@page import="assesment.communication.question.AnswerData"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>
<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys == null) {
		response.sendRedirect("logout.jsp");
	}else {
		UserSessionData userSessionData = sys.getUserSessionData();
		Text messages = sys.getText();
		String moduleId = request.getParameter("module");
		String multiId = request.getParameter("multi");
		int assessmentId = userSessionData.getFilter().getAssesment().intValue();
		if(!Util.empty(moduleId) && sys.hasAssessment()) {
        	ModuleData moduleData = sys.getModuleReportFacade().findModule(new Integer(moduleId),userSessionData);
        	HashMap hash = sys.getUserReportFacade().getMultiAssessmentResult(new Integer(multiId),userSessionData);

        	if(moduleData != null) {
%>		
		
		
		
		

		<%@page import="assesment.communication.administration.UserAnswerData"%>

	<form id="assessment" action="result.jsp" data-module='<%=multiId%>'  data-success-redirect="repeat_da.jsp">
<%		    Iterator it = (assessmentId == AssesmentData.ANTP_MEXICO_RSMM || assessmentId == AssesmentData.TRANSPORTES_NIQUINI) ? moduleData.getQuestionDisorder() : moduleData.getQuestionIterator();
			int lastGroup = -1;
			int index = 0;
	        while(it.hasNext()) {
  	          	QuestionData question = (QuestionData)it.next();
  	          	String questionId = "qw"+question.getId();
  	          	String answerId = "q"+question.getId();
  	          	int questionType = question.getType().intValue();
  	          	if(questionType == QuestionData.BIRTHDATE) {
  	          		questionType = QuestionData.DATE;
  	          	}
  	          	int qId = question.getId().intValue();
  	          	if(questionType == QuestionData.OPTIONAL_DATE && (qId == 22872 || qId == 41449 || qId == 41602 || qId == 42924))
  	          		questionType = 71;
  	          	int questionGroup = question.getGroup().intValue();
  	          	Integer userAnswerData = null;
  	          	String completed = "";
  	          	if(hash.containsKey(question.getId())) {
  	          		userAnswerData = (Integer)hash.get(question.getId());
  	          		completed = "data-completed=\"true\"";
  	          	}
  	          	String video = "";
  	          	if(questionType == QuestionData.YOU_TUBE_VIDEO || questionType == QuestionData.VIDEO) {
  	          		video = "data-video-id=\""+messages.getText(question.getKey())+"\"";
  	          	}
  	          	if(questionGroup != lastGroup || questionGroup == 0) {
  	          		if(lastGroup != -1) {
%>				</fieldset>  	          			
<% 	          		}
  	          		lastGroup = questionGroup;
%>				<fieldset>  	          			
<%					
					if(Integer.parseInt(moduleId) != ModuleData.PSICO) {
						String image = sys.getQuestionReportFacade().getImage(question.getId(),userSessionData);
						if(image != null) {
%>				<img class="picture" src='<%="../flash/assessment/images/"+image%>'>
<%	  		        	}		
					}
				}
%>				<div id='<%=questionId%>' data-type='<%=String.valueOf(questionType)%>' <%=video%> <%=completed%>>
<%				if(questionType != QuestionData.YOU_TUBE_VIDEO && questionType != QuestionData.VIDEO) {
%>                    <label for='<%=answerId%>'><%=messages.getText(question.getKey())%></label>
<%				}
				if(questionType == QuestionData.COUNTRY) {
					CountryConstants cc = new CountryConstants(messages);
					Iterator countryIt = cc.getCountryIterator();
%>	               	<select id='<%=answerId%>' name='<%=answerId%>'>
	                	<option value="">---------</option>
<%					while(countryIt.hasNext()) {
						CountryData country = (CountryData)countryIt.next();
						String selected = (userAnswerData != null && userAnswerData.intValue() == Integer.parseInt(country.getId())) ? "selected" : "";	
%>	                    <option value='<%=country.getId()%>' <%=selected%>><%=country.getName()%></option>							
<%					}
%>					</select>
<%				}else if(questionType == QuestionData.EXCLUDED_OPTIONS) {
					Iterator answerIt = (assessmentId == AssesmentData.ANTP_MEXICO_RSMM || assessmentId == AssesmentData.TRANSPORTES_NIQUINI) ? question.getAnswerDisorder() : question.getAnswerIterator();			
					while(answerIt.hasNext()) {
						AnswerData answerData = (AnswerData)answerIt.next();
						String selected = (userAnswerData != null && userAnswerData.equals(answerData.getId())) ? "checked" : "";	
%>					<label class="radio"><input type="radio" name='<%=answerId%>' value='<%=String.valueOf(answerData.getId())%>' <%=selected%>><%=messages.getText(answerData.getKey())%></label>
<%						
					}
				}else if(questionType == QuestionData.LIST) {
%>	               	<select id='<%=answerId%>' name='<%=answerId%>'>
  		              	<option value="">---------</option>
<%					Iterator answerIt = (assessmentId == AssesmentData.ANTP_MEXICO_RSMM || assessmentId == AssesmentData.TRANSPORTES_NIQUINI) ? question.getAnswerDisorder() : question.getAnswerIterator();			
					while(answerIt.hasNext()) {
						AnswerData answerData = (AnswerData)answerIt.next();
						String selected = (userAnswerData != null && userAnswerData.equals(answerData.getId())) ? "selected" : "";	
%>						<option value='<%=String.valueOf(answerData.getId())%>' <%=selected%>><%=messages.getText(answerData.getKey())%></option>
<%					}						
%>					</select>
<%	        	}
%>			</div>
<%	        	index++;
			}
%>			</fieldset>
		</form>
<%         	
	        }
		}
	}
%>
