<%@page import="assesment.communication.assesment.GroupData"%>
<%@page import="assesment.communication.user.UserData"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Collection"%>
<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="assesment.communication.administration.user.UserSessionData"%><%@ page contentType="application/json" %>

<%@page import="assesment.business.administration.user.UsReportFacade"%>
<%@page import="assesment.communication.assesment.AssesmentData"%>
<%@page import="assesment.presentation.actions.administration.AnswerUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="assesment.communication.question.AnswerData"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONTokener"%>
<%@page import="org.json.JSONStringer"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.json.JSONArray"%>
<%@page import="assesment.business.AssesmentAccess"%>
<%@page import="assesment.communication.language.Text"%>
<%@page import="java.util.Enumeration"%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
	if(sys == null) {
		response.sendRedirect("logout.jsp");
	}else {
		Text messages = sys.getText();
		UserSessionData userSessionData = sys.getUserSessionData();
		String login = userSessionData.getFilter().getLoginName();
		UsReportFacade userReport = sys.getUserReportFacade();
		Enumeration enumeration = request.getParameterNames();
	 	if(enumeration.hasMoreElements())  {
			String name = (String)enumeration.nextElement();
			JSONObject object = new JSONObject(name);
			String module = object.getString("module");
			JSONArray answers = object.getJSONArray("answers");
			HashMap answerMap = new HashMap();
			for(int i = 0; i < answers.length(); i++) {
				JSONObject answerObj = (JSONObject)answers.get(i);
				Integer id = new Integer(answerObj.getString("id"));
				String answer = (answerObj.has("answer")) ? answerObj.getString("answer") : "true";
				answerMap.put(id,answer);
			}
			AssesmentData assesment = sys.getAssesmentReportFacade().findAssesmentByModule(new Integer(module), sys.getUserSessionData());
			if(assesment != null) {
				boolean cont = true;
				if(module.equals("1079") && answerMap.containsKey(new Integer(17586))) {
					String[][] errors = Util.validateMonsantoSurver(answerMap);
					if(errors != null) {
						cont = false;
						response.getWriter().print("{\"valids\": [");
						response.getWriter().print("],");
						response.getWriter().print("\"errors\": [");
						for(int i = 0; i < errors.length; i++) {
							String valid = (i == 0) ? "" : ",";
							response.getWriter().print("{");			
							response.getWriter().print("\"id\" : \""+errors[i][0]+"\",");			
							response.getWriter().print("\"error\" : \""+messages.getText(errors[i][1])+"\"");
							if(i == errors.length - 1) {
								response.getWriter().print("}");
							}else {
								response.getWriter().print("},");
							}
						}
						response.getWriter().print("]");
						response.getWriter().print("}");
					}
				}
				if(cont) {
					Integer assessmentId = assesment.getId();
					boolean done = userReport.isAssessmentDone(login,assessmentId,userSessionData,assesment.isPsitest());
					if(answerMap.size() > 0 && !done) {
						AnswerUtil answerUtil = AnswerUtil.getInstance(assesment);
						System.out.println("------------EMPIEZO SALVO PREGUNTAS-------------------");
						System.out.println("Usuario: "+login);
		                Iterator it = answerMap.keySet().iterator();
		                while(it.hasNext()) {
							System.out.println("Pregunta("+login+"): "+it.next());
		                }
						Object[] values = answerUtil.saveAnswers(sys,assesment, module,answerMap,true);
						Integer answered = userReport.getQuestionCount(login,new Integer(module),assessmentId,userSessionData);
						assesment.setAnswered(new Integer(module),answered);
						String[] valids = (String[])values[0];
						String[][] errors = (String[][])values[1];
						response.getWriter().print("{\"valids\": [");
						for(int i = 0; i < valids.length; i++) {
							String valid = (i == 0) ? "" : ",";
							valid += "\""+valids[i]+"\"";
							response.getWriter().print(valid);			
						}
						response.getWriter().print("],");
						response.getWriter().print("\"errors\": [");
						for(int i = 0; i < errors.length; i++) {
							String valid = (i == 0) ? "" : ",";
							response.getWriter().print("{");			
							response.getWriter().print("\"id\" : \""+errors[i][0]+"\",");			
							response.getWriter().print("\"error\" : \""+messages.getText(errors[i][1])+"\"");
							if(i == errors.length - 1) {
								response.getWriter().print("}");
							}else {
								response.getWriter().print("},");
							}
						}
						String finalText = "";
						done = userReport.isAssessmentDone(login,assessmentId,userSessionData,assesment.isPsitest());
						System.out.println("Done("+login+"): "+done);						
				        if(done) {
			        		if(assessmentId.intValue() == AssesmentData.BAYERMX_RIESGO_TRASERO) {
								if(sys.getUserReportFacade().isResultGreen(userSessionData.getFilter().getLoginName(), assesment.getId(), userSessionData)) {
						        	sys.getUserABMFacade().setEndDate(login,assessmentId,userSessionData);
								}
			        		}else {
								System.out.println("Cargo fecha final: "+login);
					        	sys.getUserABMFacade().setEndDate(login,assessmentId,userSessionData);
			        		}
				        	String redirect = null;
					        if(assesment.isElearning()) {
					        	answerUtil.elearningRedirection(sys,login,assesment);
					        	redirect = userReport.getElearningURL(login,assessmentId,userSessionData);
					        }
					        if(assessmentId.intValue() == AssesmentData.MONSANTO_NEW_HIRE
					        		|| assessmentId.intValue() == AssesmentData.NEW_HIRE
					        		|| assessmentId.intValue() == AssesmentData.SURVEY
					        		|| assessmentId.intValue() == AssesmentData.NEW_HIRE_V2
					        		|| assessmentId.intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_V2
					        		|| assessmentId.intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_V2_REPETICION
					        		|| assessmentId.intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2 
					        		|| assessmentId.intValue() == AssesmentData.BAYERMX_ELEARNINGPACK_VERSION2_REPETICION) {
								answerUtil.newFeedback(sys,assesment,redirect);
					        }
				
				        	if(redirect != null && redirect.length() > 0) {
				        		finalText = "\"final_text\": \""+messages.getText("assessment.elearning.lessons")+"\"";
				        		finalText += ",\"redirect\": \""+redirect+"\"";
				        	}else {
					        	finalText = "\"final_text\": \""+messages.getText("assesment.reporthtml.waitmessage")+"\"";
					        }
				        }
						System.out.println("------------TERMINO SALVO PREGUNTAS-------------------");
						response.getWriter().print("]");
						if(finalText.length() > 0) {
							response.getWriter().print(",");
							response.getWriter().print(finalText);
						}
						response.getWriter().print("}");
			 		}
				}
			}
		}
	}
%>