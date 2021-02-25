<%@page language="java"
	import="assesment.business.*"
	import="assesment.business.administration.user.*"
	import="assesment.communication.assesment.*"
	import="assesment.communication.language.*"
	import="assesment.communication.security.*"
	import="assesment.communication.administration.user.*"
	import="assesment.communication.module.*"
	import="assesment.communication.exception.*"
	import="assesment.communication.util.*"
	import="assesment.presentation.actions.report.*"
	import="assesment.communication.user.*"
	import="assesment.presentation.actions.administration.*"
	import="assesment.communication.corporation.CorporationData"
	import="java.util.*"
	import="java.io.*"
%>
<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%
	AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess"); 
 	UserSessionData userSession=sys.getUserSessionData(); 
 	Text messages=sys.getText();
 	
 	if(userSession.getFilter().getAssesment() != null) {
 	    
 	    AssesmentData assesment = sys.getAssesmentReportFacade().findAssesment(sys.getUserSessionData().getFilter().getAssesment(),sys.getUserSessionData());
 	    String xml = request.getParameter("xml");
 	    //System.out.println(xml);
 	   	//String xmlPSI = "<module id=\"0\"><question id=\"18\"><answer>54</answer></question><question id=\"43\"><answer>129</answer></question><question id=\"20\"><answer>60</answer></question><question id=\"10\"><answer>30</answer></question><question id=\"19\"><answer>55</answer></question><question id=\"33\"><answer>97</answer></question><question id=\"7\"><answer>19</answer></question><question id=\"41\"><answer>121</answer></question><question id=\"6\"><answer>16</answer></question><question id=\"14\"><answer>40</answer></question><question id=\"25\"><answer>73</answer></question><question id=\"29\"><answer>86</answer></question><question id=\"16\"><answer>47</answer></question><question id=\"38\"><answer>113</answer></question><question id=\"23\"><answer>68</answer></question><question id=\"21\"><answer>62</answer></question><question id=\"17\"><answer>50</answer></question><question id=\"3\"><answer>8</answer></question><question id=\"48\"><answer>143</answer></question><question id=\"5\"><answer>14</answer></question><question id=\"44\"><answer>131</answer></question><question id=\"31\"><answer>92</answer></question><question id=\"8\"><answer>23</answer></question><question id=\"45\"><answer>134</answer></question><question id=\"39\"><answer>116</answer></question><question id=\"37\"><answer>110</answer></question><question id=\"1\"><answer>2</answer></question><question id=\"24\"><answer>71</answer></question><question id=\"2\"><answer>5</answer></question><question id=\"46\"><answer>137</answer></question><question id=\"22\"><answer>65</answer></question><question id=\"28\"><answer>83</answer></question><question id=\"42\"><answer>125</answer></question><question id=\"40\"><answer>119</answer></question><question id=\"26\"><answer>77</answer></question><question id=\"12\"><answer>35</answer></question><question id=\"34\"><answer>101</answer></question><question id=\"11\"><answer>32</answer></question><question id=\"9\"><answer>26</answer></question><question id=\"32\"><answer>95</answer></question><question id=\"47\"><answer>140</answer></question><question id=\"35\"><answer>104</answer></question><question id=\"13\"><answer>38</answer></question><question id=\"30\"><answer>89</answer></question><question id=\"27\"><answer>80</answer></question><question id=\"15\"><answer>44</answer></question><question id=\"36\"><answer>107</answer></question><question id=\"4\"><answer>11</answer></question></module>";
// 	   	String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><module id=\"84\" ><question id=\"1611\"><answer>6844</answer></question><question id=\"1612\"><answer>6846</answer></question><question id=\"1613\"><answer>6849</answer></question><question id=\"1614\"><answer>6852</answer></question><question id=\"1615\"><answer>6855</answer></question><question id=\"1616\"><answer>6857</answer></question><question id=\"1617\"><answer>6860</answer></question><question id=\"1618\"><answer>6865</answer></question><question id=\"1619\"><answer>6867</answer></question><question id=\"1620\"><answer>6870</answer></question><question id=\"1621\"><answer>6873</answer></question><question id=\"1622\"><answer>6877</answer></question></module>";
 	   	String responseString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
 	    try {
 	        AnswerUtil answerUtil = null;
 	        if(assesment.getCorporationId() == CorporationData.BASF) {
 	        	answerUtil = new AnswerUtilBasf();
 	        }else if(AssesmentData.isJJ(assesment.getId().intValue())) {
                answerUtil = new AnswerUtilJJ();
 	        }else {
	 	        switch(assesment.getId().intValue()) {
	 	            case AssesmentData.MONSANTO_NEW_HIRE: case AssesmentData.NEW_HIRE:
	 	                answerUtil = new AnswerUtilNewHire();
	 	                break;
	 	            case AssesmentData.JANSSEN: case AssesmentData.JANSSEN_2:
	 	                answerUtil = new AnswerUtilJanssen();
	 	                break;
	 	            case AssesmentData.FACEBOOK:
	 	                answerUtil = new AnswerUtilFacebook();
	 	                break;
	 	            case AssesmentData.ABITAB:
	 	                answerUtil = new AnswerUtilAbitab();
	 	                break;
	 	            case AssesmentData.MICHELIN: 
	 	            case AssesmentData.MICHELIN_3:
	 	            case AssesmentData.MICHELIN_5:
	 	            case AssesmentData.MICHELIN_6:
	 	            case AssesmentData.MICHELIN_7:
	 	            case AssesmentData.MICHELIN_8:
	 	            case AssesmentData.MICHELIN_9:
	 	                answerUtil = new AnswerUtilMichelin();
	 	                break;
	 	            case AssesmentData.MONSANTO_BRAZIL: case AssesmentData.MONSANTO_ARGENTINA:
	 	                answerUtil = new AnswerUtilMonsantoBR();
	 	                break;
	 	            case AssesmentData.PEPSICO: case AssesmentData.PEPSICO_CANDIDATOS: case AssesmentData.PEPSICO_CEPA_SYSTEM:  
	 	                answerUtil = new AnswerUtilPepsico();
	 	                break;
	 	            case AssesmentData.MONSANTO_LAN: case AssesmentData.MAMUT_ANDINO:
	 	            case AssesmentData.NALCO: case AssesmentData.DNB: case AssesmentData.TRANSMETA:
	 	                answerUtil = new AnswerUtilMonsantoLAN();
	 	                break;
	 	            case AssesmentData.SCHERING:
	 	                answerUtil = new AnswerUtilSchering();
	 	                break;
	 	            case AssesmentData.GM:
	 	                answerUtil = new AnswerUtilGM();
	 	                break;
	 	            case AssesmentData.ACU:
	 	                answerUtil = new AnswerUtilACU();
	 	                break;
	 	            case AssesmentData.DOW:
	 	                answerUtil = new AnswerUtilDOW();
	 	                break;
	 	            case AssesmentData.IMESEVI:
	 	                answerUtil = new AnswerUtilImesevi();
	 	                break;
	 	            case AssesmentData.ASTRAZENECA: 
	 	            case AssesmentData.ASTRAZENECA_2:
	 	            case AssesmentData.ASTRAZENECA_2013:
	 	                answerUtil = new AnswerUtilAZ();
	 	                break;
	 	            case AssesmentData.NYCOMED:
	 	                answerUtil = new AnswerUtilNycomed();
	 	                break;
	 	            case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
	 	                answerUtil = new AnswerUtilAnglo();
	 	                break;
	 	            case AssesmentData.SANOFI_BRASIL:
	 	                answerUtil = new AnswerUtilSanofi();
	 	                break;
	 	            case AssesmentData.SURVEY:
	 	                answerUtil = new AnswerUtilSurvey();
	 	                break;
	 	            default:
	 	                answerUtil = new AnswerUtil();
	 	        }
 	        }
 	      	int[] data = answerUtil.saveAnswers(sys,xml,assesment.isInstantFeedback());
	        responseString += "<module id=\""+data[0]+"\" valid_answers=\"true\" ";
            if(assesment.isInstantFeedback() && data[2] > 0) {
		        responseString += "correct_answers = \""+data[1]+"\" ";
		        responseString += "total_answers = \""+data[2]+"\" ";
	        }
	        responseString += ">";
	
	        UsReportFacade userReport = sys.getUserReportFacade();
	        UserSessionData userSessionData = sys.getUserSessionData();
	        String login = userSessionData.getFilter().getLoginName();
	        if(userReport.isAssessmentDone(login,assesment.getId(),userSessionData,assesment.isPsitest())) {
	        	sys.getUserABMFacade().setEndDate(login,assesment.getId(),sys.getUserSessionData());
	        	String redirect = null;
		        if(assesment.isElearning()) {
		        	answerUtil.elearningRedirection(sys,login,assesment);
		        	redirect = userReport.getElearningURL(login,assesment.getId(),userSessionData);
		        }
	        	if(data[1] >= 0 && data[2] >= 0 && assesment.isReportFeedback()) {
					answerUtil.newFeedback(sys,assesment,redirect);
	        	}
	
		        if(assesment.isElearning() && assesment.getId().intValue() != AssesmentData.ABITAB) {
		        	if(redirect != null && redirect.length() > 0) {
				        responseString += "<final_text href=\""+redirect+"\">"+messages.getText("assessment.elearning.lessons")+"</final_text>";
		        	}
		        }
		        if(assesment.getId().intValue() == AssesmentData.ABITAB) {
			        responseString += "<final_text>"+messages.getText("assessment.abitab.endmessage")+"</final_text>";
			    	//new LogoutAction().logout(request);
		        }
	        }
	        
	        responseString += "</module>";
		    System.out.println(responseString);
 	    }catch(InvalidDataException e) {
 	        if(assesment.getCorporationId() == CorporationData.BASF) {
 	 	    	responseString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><module id=\""+e.getSystemMessage()+" "+"\" valid_answers=\"false\" >"+
 	 	    		"<final_text href=\"http://elearning.cepasafedrive.com?user=prueba\">"+e.getKey()+"</final_text></module>";
 	        }else {
 	 	    	responseString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><module id=\""+e.getMessage()+" "+"\" valid_answers=\"false\" ></module>"; 	        	
 	        }
 	    }catch(Exception e) {
 	    	e.printStackTrace();
 	    	responseString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><module id=\""+e.getMessage()+" "+"\" valid_answers=\"false\" ></module>"; 	        	
 	    }
 	    System.out.println(responseString);
    	response.setContentType("text/xml");
    	response.getWriter().write(responseString);
 	}
 	

%>
