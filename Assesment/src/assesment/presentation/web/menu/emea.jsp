<%@page language="java"
   errorPage="../exception.jsp"
   import="java.util.*"
   import="assesment.business.*"
   import="assesment.communication.language.*"
   import="assesment.communication.security.*"
   import="assesment.communication.language.tables.*"
   import="assesment.communication.administration.user.tables.*"
   import="assesment.communication.question.*"
   import="assesment.communication.module.*"
   import="assesment.presentation.translator.web.util.*"
   import="assesment.presentation.actions.user.*"
%>
<%@page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html" 
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld"
        prefix="bean" 
%>


<%@page import="assesment.communication.util.CountryConstants"%>
<%@page import="assesment.communication.util.CountryData"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html:html>
<%
   AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
   Text messages = sys.getText();
   
   RequestDispatcher dispatcher=request.getRequestDispatcher("/util/jsp/message.jsp");
   dispatcher.include(request,response);

   String check = Util.checkPermission(sys,SecurityConstants.ACCESS_TO_SYSTEM);
   if(check!=null) {
      response.sendRedirect(request.getContextPath()+check);
   }else {
       
%>

   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <title>Assessment</title>
      <style type="text/css">
<!--
.style14 {font-family: Arial, Helvetica, sans-serif; font-weight: bold; font-size: 12px; }
.style17 {
   font-family: Arial, Helvetica, sans-serif;
   font-size: 12;
   font-weight: bold;
}
.style25 {font-size: 12px; font-family: Arial, Helvetica, sans-serif;}
.style26 {font-family: Arial, Helvetica, sans-serif}
.style27 {color: #FFFFFF; font-weight: bold; font-family: Arial, Helvetica, sans-serif; font-size: 14px; }
.style28 {
   font-family: Arial, Helvetica, sans-serif;
   font-size: 16px;
   font-weight: bold;
}
.style29 {
   font-size: 14px;
   font-family: Arial, Helvetica, sans-serif;
   font-weight: bold;
}
.style31 {font-size: 13px; font-family: Arial, Helvetica, sans-serif; color: #000000; }
.style34 {font-size: 14px}
.style35 {font-weight: bold; font-family: Arial, Helvetica, sans-serif;}
.style36 {
   font-size: 11px;
   font-family: Arial, Helvetica, sans-serif;
   font-weight: bold;
}
body {
   background-image: url(./util/imgs/fondo.gif);
   background-repeat: repeat;
}
.style37 {color: #F7F7F7}
.style38 {font-size: 18px}
.style39 {font-family: "Times New Roman", Times, serif}
-->
</style>

      <script type="text/javascript">
         function loadCities(form) {
            var comboCountry = form.elements['question1492'];
            var value = comboCountry.options[comboCountry.options.selectedIndex].value;
            var comboCity = form.elements['question1555'];
            document.getElementById('othercity').style.visibility = 'hidden';
            if(value == -1) {
               comboCity.options.length = 1;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
            }else if(value == 6185) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6178";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6178.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6186) {
               comboCity.options.length = 11;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6170";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6170.text")%>';
               comboCity.options[2].value = "6183";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6183.text")%>';
               comboCity.options[3].value = "6176";
               comboCity.options[3].text = '<%=messages.getText("question1555.answer6176.text")%>';
               comboCity.options[4].value = "6169";
               comboCity.options[4].text = '<%=messages.getText("question1555.answer6169.text")%>';
               comboCity.options[5].value = "6164";
               comboCity.options[5].text = '<%=messages.getText("question1555.answer6164.text")%>';
               comboCity.options[6].value = "6179";
               comboCity.options[6].text = '<%=messages.getText("question1555.answer6179.text")%>';
               comboCity.options[7].value = "6168";
               comboCity.options[7].text = '<%=messages.getText("question1555.answer6168.text")%>';
               comboCity.options[8].value = "6173";
               comboCity.options[8].text = '<%=messages.getText("question1555.answer6173.text")%>';
               comboCity.options[9].value = "6225";
               comboCity.options[9].text = '<%=messages.getText("question1555.answer6225.text")%>';
               comboCity.options[10].value = "6227";
               comboCity.options[10].text = '<%=messages.getText("question1555.answer6227.text")%>';
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6187) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6172";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6172.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6188) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6167";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6167.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6189) {
               comboCity.options.length = 4;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6184";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6184.text")%>';
               comboCity.options[2].value = "6174";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6174.text")%>';
               comboCity.options[3].value = "6227";
               comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6190) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6165";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6165.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6191) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6226";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6226.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6192) {
               comboCity.options.length = 4;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6162";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6162.text")%>';
               comboCity.options[2].value = "6180";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6180.text")%>';
               comboCity.options[3].value = "6227";
               comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6193) {
               comboCity.options.length = 2;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6227";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6194) {
                comboCity.options.length = 3;
                comboCity.options[0].value = "-1";
                comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
                comboCity.options[1].value = "6177";
                comboCity.options[1].text = '<%=messages.getText("question1555.answer6177.text")%>';
                comboCity.options[2].value = "6227";
                comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
                comboCity.options.selectedIndex = 0;               
            }else if(value == 6195) {
               comboCity.options.length = 4;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6182";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6182.text")%>';
               comboCity.options[2].value = "6175";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6175.text")%>';
               comboCity.options[3].value = "6227";
               comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6196) {
                comboCity.options.length = 4;
                comboCity.options[0].value = "-1";
                comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
                comboCity.options[1].value = "6163";
                comboCity.options[1].text = '<%=messages.getText("question1555.answer6163.text")%>';
                comboCity.options[2].value = "6181";
                comboCity.options[2].text = '<%=messages.getText("question1555.answer6181.text")%>';
                comboCity.options[3].value = "6227";
                comboCity.options[3].text = '<%=messages.getText("question1555.answer6227.text")%>';               
                comboCity.options.selectedIndex = 0;               
            }else if(value == 6197) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6166";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6166.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }else if(value == 6231) {
               comboCity.options.length = 3;
               comboCity.options[0].value = "-1";
               comboCity.options[0].text = '<%=messages.getText("messages.emea.selectsite")%>';
               comboCity.options[1].value = "6171";
               comboCity.options[1].text = '<%=messages.getText("question1555.answer6171.text")%>';
               comboCity.options[2].value = "6227";
               comboCity.options[2].text = '<%=messages.getText("question1555.answer6227.text")%>';               
               comboCity.options.selectedIndex = 0;               
            }
         }
         function loadModels(form) {
            var comboTrade = form.elements['question1531'];
            var value = comboTrade.options[comboTrade.options.selectedIndex].value;
            var comboModel = form.elements['question1532'];
            document.getElementById('othermodel').style.visibility = 'hidden';
            if(value == 6206) {
               comboModel.options.length = 6;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6500";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6500.text")%>';
               comboModel.options[2].value = "6501";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6501.text")%>';
               comboModel.options[3].value = "6502";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6502.text")%>';
               comboModel.options[4].value = "6503";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6503.text")%>';
               comboModel.options[5].value = "6269";
               comboModel.options[5].text = '<%=messages.getText("question1532.answer6269.text")%>';
            }else if(value == 6207) {
               comboModel.options.length = 14;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6504";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6504.text")%>';
               comboModel.options[2].value = "6505";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6505.text")%>';
               comboModel.options[3].value = "6255";
               comboModel.options[3].text = '<%=messages.getText("question1532.answer6255.text")%>';
               comboModel.options[4].value = "6506";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6506.text")%>';
               comboModel.options[5].value = "6507";
               comboModel.options[5].text = '<%=messages.getText("question1532,answer6507.text")%>';
               comboModel.options[6].value = "6508";
               comboModel.options[6].text = '<%=messages.getText("question1532,answer6508.text")%>';
               comboModel.options[7].value = "6509";
               comboModel.options[7].text = '<%=messages.getText("question1532,answer6509.text")%>';
               comboModel.options[8].value = "6510";
               comboModel.options[8].text = '<%=messages.getText("question1532,answer6510.text")%>';
               comboModel.options[9].value = "6511";
               comboModel.options[9].text = '<%=messages.getText("question1532,answer6511.text")%>';
               comboModel.options[10].value = "6513";
               comboModel.options[10].text = '<%=messages.getText("question1532,answer6513.text")%>';
               comboModel.options[11].value = "6514";
               comboModel.options[11].text = '<%=messages.getText("question1532,answer6514.text")%>';
               comboModel.options[12].value = "6515";
               comboModel.options[12].text = '<%=messages.getText("question1532,answer6515.text")%>';
               comboModel.options[13].value = "6269";
               comboModel.options[13].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6208) {
               comboModel.options.length = 7;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6516";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6516.text")%>';
               comboModel.options[2].value = "6250";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6250.text")%>';
               comboModel.options[3].value = "6517";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6517.text")%>';
               comboModel.options[4].value = "6252";
               comboModel.options[4].text = '<%=messages.getText("question1532.answer6252.text")%>';
               comboModel.options[5].value = "6254";
               comboModel.options[5].text = '<%=messages.getText("question1532.answer6254.text")%>';                                            
               comboModel.options[6].value = "6269";
               comboModel.options[6].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6209) {
               comboModel.options.length = 4;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = 6518;
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6518.text")%>';
               comboModel.options[2].value = 6519;
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6519.text")%>';
               comboModel.options[3].value = "6269";
               comboModel.options[3].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6210) {  
               comboModel.options.length = 18;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6520";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6520.text")%>';
               comboModel.options[2].value = "6521";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6521.text")%>';
               comboModel.options[3].value = "6522";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6522.text")%>';
               comboModel.options[4].value = "6523";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6523.text")%>';
               comboModel.options[5].value = "6524";
               comboModel.options[5].text = '<%=messages.getText("question1532,answer6524.text")%>';
               comboModel.options[6].value = "6525";
               comboModel.options[6].text = '<%=messages.getText("question1532,answer6525.text")%>';
               comboModel.options[7].value = "6526";
               comboModel.options[7].text = '<%=messages.getText("question1532,answer6526.text")%>';
               comboModel.options[8].value = "6527";
               comboModel.options[8].text = '<%=messages.getText("question1532,answer6527.text")%>';
               comboModel.options[9].value = "6528";
               comboModel.options[9].text = '<%=messages.getText("question1532,answer6528.text")%>';
               comboModel.options[10].value = "6529";
               comboModel.options[10].text = '<%=messages.getText("question1532,answer6529.text")%>';
               comboModel.options[11].value = "6531";
               comboModel.options[11].text = '<%=messages.getText("question1532,answer6531.text")%>';
               comboModel.options[12].value = "6530";
               comboModel.options[12].text = '<%=messages.getText("question1532,answer6530.text")%>';
               comboModel.options[13].value = "6532";
               comboModel.options[13].text = '<%=messages.getText("question1532,answer6532.text")%>';
               comboModel.options[14].value = "6533";
               comboModel.options[14].text = '<%=messages.getText("question1532,answer6533.text")%>';
               comboModel.options[15].value = "6534";
               comboModel.options[15].text = '<%=messages.getText("question1532,answer6534.text")%>';
               comboModel.options[16].value = "6535";
               comboModel.options[16].text = '<%=messages.getText("question1532,answer6535.text")%>';
               comboModel.options[17].value = "6269";
               comboModel.options[17].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6211) {  
               comboModel.options.length = 3;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6536";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6536.text")%>';
               comboModel.options[2].value = "6269";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
               comboModel.options.selectedIndex = 0;
            }else if(value == 6212) {  
               comboModel.options.length = 3;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6260";
               comboModel.options[1].text = '<%=messages.getText("question1532.answer6260.text")%>';
               comboModel.options[2].value = "6269";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6213) {  
               comboModel.options.length = 3;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6537";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6537.text")%>';
               comboModel.options[2].value = "6269";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6214) {  
               comboModel.options.length = 3;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6538";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6538.text")%>';
               comboModel.options[2].value = "6269";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6215) {  
               comboModel.options.length = 3;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6539";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6539.text")%>';
               comboModel.options[2].value = "6269";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6216) {  
               comboModel.options.length = 20;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6494";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6494.text")%>';
               comboModel.options[2].value = "6495";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6495.text")%>';
               comboModel.options[3].value = "6496";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6496.text")%>';
               comboModel.options[4].value = "6497";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6497.text")%>';
               comboModel.options[5].value = "6498";
               comboModel.options[5].text = '<%=messages.getText("question1532,answer6498.text")%>';
               comboModel.options[6].value = "6499";
               comboModel.options[6].text = '<%=messages.getText("question1532,answer6499.text")%>';
               comboModel.options[7].value = "6540";
               comboModel.options[7].text = '<%=messages.getText("question1532,answer6540.text")%>';
               comboModel.options[8].value = "6541";
               comboModel.options[8].text = '<%=messages.getText("question1532,answer6541.text")%>';
               comboModel.options[9].value = "6542";
               comboModel.options[9].text = '<%=messages.getText("question1532,answer6542.text")%>';
               comboModel.options[10].value = "6543";
               comboModel.options[10].text = '<%=messages.getText("question1532,answer6543.text")%>';
               comboModel.options[11].value = "6544";
               comboModel.options[11].text = '<%=messages.getText("question1532,answer6544.text")%>';
               comboModel.options[12].value = "6545";
               comboModel.options[12].text = '<%=messages.getText("question1532,answer6545.text")%>';
               comboModel.options[13].value = "6546";
               comboModel.options[13].text = '<%=messages.getText("question1532,answer6546.text")%>';
               comboModel.options[14].value = "6547";
               comboModel.options[14].text = '<%=messages.getText("question1532,answer6547.text")%>';
               comboModel.options[15].value = "6548";
               comboModel.options[15].text = '<%=messages.getText("question1532,answer6548.text")%>';
               comboModel.options[16].value = "6549";
               comboModel.options[16].text = '<%=messages.getText("question1532,answer6549.text")%>';
               comboModel.options[17].value = "6550";
               comboModel.options[17].text = '<%=messages.getText("question1532,answer6550.text")%>';
               comboModel.options[18].value = "6551";
               comboModel.options[18].text = '<%=messages.getText("question1532,answer6551.text")%>';
               comboModel.options[19].value = "6269";
               comboModel.options[19].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6217) {  
               comboModel.options.length = 6;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6552";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6552.text")%>';
               comboModel.options[2].value = "6553";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6553.text")%>';
               comboModel.options[3].value = "6554";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6554.text")%>';
               comboModel.options[4].value = "6555";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6555.text")%>';
               comboModel.options[5].value = "6269";
               comboModel.options[5].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6218) {  
               comboModel.options.length = 4;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6556";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6556.text")%>';
               comboModel.options[2].value = "6557";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6557.text")%>';
               comboModel.options[3].value = "6269";
               comboModel.options[3].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6219) {  
               comboModel.options.length = 8;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6558";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6558.text")%>';
               comboModel.options[2].value = "6559";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6559.text")%>';
               comboModel.options[3].value = "6560";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6560.text")%>';
               comboModel.options[4].value = "6561";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6561.text")%>';
               comboModel.options[5].value = "6562";
               comboModel.options[5].text = '<%=messages.getText("question1532,answer6562.text")%>';
               comboModel.options[6].value = "6563";
               comboModel.options[6].text = '<%=messages.getText("question1532,answer6563.text")%>';
               comboModel.options[7].value = "6269";
               comboModel.options[7].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else if(value == 6220) {  
               comboModel.options.length = 6;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6564";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6564.text")%>';
               comboModel.options[2].value = "6565";
               comboModel.options[2].text = '<%=messages.getText("question1532,answer6565.text")%>';
               comboModel.options[3].value = "6566";
               comboModel.options[3].text = '<%=messages.getText("question1532,answer6566.text")%>';
               comboModel.options[4].value = "6567";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6567.text")%>';
               comboModel.options[5].value = "6269";
               comboModel.options[5].text = '<%=messages.getText("question1532.answer6269.text")%>';
            }else if(value == 6221) {  
               comboModel.options.length = 22;
               comboModel.options[0].value = "-1";
               comboModel.options[0].text = '<%=messages.getText("messages.emea.selectmodel")%>';
               comboModel.options[1].value = "6568";
               comboModel.options[1].text = '<%=messages.getText("question1532,answer6568.text")%>';
               comboModel.options[2].value = "6261";
               comboModel.options[2].text = '<%=messages.getText("question1532.answer6261.text")%>';
               comboModel.options[3].value = "6262";
               comboModel.options[3].text = '<%=messages.getText("question1532.answer6262.text")%>';
               comboModel.options[4].value = "6569";
               comboModel.options[4].text = '<%=messages.getText("question1532,answer6569.text")%>';
               comboModel.options[5].value = "6570";
               comboModel.options[5].text = '<%=messages.getText("question1532,answer6570.text")%>';
               comboModel.options[6].value = "6571";
               comboModel.options[6].text = '<%=messages.getText("question1532,answer6571.text")%>';
               comboModel.options[7].value = "6263";
               comboModel.options[7].text = '<%=messages.getText("question1532.answer6263.text")%>';
               comboModel.options[8].value = "6264";
               comboModel.options[8].text = '<%=messages.getText("question1532.answer6264.text")%>';
               comboModel.options[9].value = "6265";
               comboModel.options[9].text = '<%=messages.getText("question1532.answer6265.text")%>';
               comboModel.options[10].value = "6572";
               comboModel.options[10].text = '<%=messages.getText("question1532,answer6572.text")%>';
               comboModel.options[11].value = "6573";
               comboModel.options[11].text = '<%=messages.getText("question1532,answer6573.text")%>';
               comboModel.options[12].value = "6574";
               comboModel.options[12].text = '<%=messages.getText("question1532,answer6574.text")%>';
               comboModel.options[13].value = "6575";
               comboModel.options[13].text = '<%=messages.getText("question1532,answer6575.text")%>';
               comboModel.options[14].value = "6576";
               comboModel.options[14].text = '<%=messages.getText("question1532,answer6576.text")%>';
               comboModel.options[15].value = "6266";
               comboModel.options[15].text = '<%=messages.getText("question1532.answer6266.text")%>';
               comboModel.options[16].value = "6267";
               comboModel.options[16].text = '<%=messages.getText("question1532.answer6267.text")%>';
               comboModel.options[17].value = "6577";
               comboModel.options[17].text = '<%=messages.getText("question1532,answer6577.text")%>';               
               comboModel.options[18].value = "6578";
               comboModel.options[18].text = '<%=messages.getText("question1532,answer6578.text")%>';
               comboModel.options[19].value = "6579";
               comboModel.options[19].text = '<%=messages.getText("question1532,answer6579.text")%>';
               comboModel.options[20].value = "6268";
               comboModel.options[20].text = '<%=messages.getText("question1532.answer6268.text")%>';
               comboModel.options[21].value = "6269";
               comboModel.options[21].text = '<%=messages.getText("question1532.answer6269.text")%>';
               comboModel.options.selectedIndex = 0;
            }else {
               if(value == 6232) {
                  document.getElementById('othermake').style.visibility = 'visible';
               }else {
                  document.getElementById('othermake').style.visibility = 'hidden';
               }                                
               comboModel.options.length = 1;
               comboModel.options[0].value = "6269";
               comboModel.options[0].text = '<%=messages.getText("question1532.answer6269.text")%>';
               document.getElementById('othermodel').style.visibility = 'visible';
            }
         }
         function loadOtherCity(form) {
            var comboCity = form.elements['question1555'];
            var value = comboCity.options[comboCity.options.selectedIndex].value;
            if(value == 6227) {
               document.getElementById('othercity').style.visibility = 'visible';
            }else {
               document.getElementById('othercity').style.visibility = 'hidden';
            }
         }
         function loadCity(form,value) {
            var comboCity = form.elements['question1555'];
            for(i = 0; i < comboCity.options.length; i++) {
               if(comboCity.options[i].value == value) {
                  comboCity.options.selectedIndex = i;
               }
            }
            if(value == 6227) {
               document.getElementById('othercity').style.visibility = 'visible';
            }
         }
         function loadModel(form,value) {
            var comboCity = form.elements['question1532'];
            for(i = 0; i < comboCity.options.length; i++) {
               if(comboCity.options[i].value == value) {
                  comboCity.options.selectedIndex = i;
               }
            }
            if(value == 6269) {
               document.getElementById('othercity').style.visibility = 'visible';
            }
         }
         function loadOtherModel(form) {
            var comboModel = form.elements['question1532'];
            var value = comboModel.options[comboModel.options.selectedIndex].value;
            if(value == 6269) {
               document.getElementById('othermodel').style.visibility = 'visible';
            }else {
               document.getElementById('othermodel').style.visibility = 'hidden';
            }
         }
      </script>
   </head>
   <body scroll="auto" onload="loadCities(document.forms['EmeaForm']);loadCity(document.forms['EmeaForm'],'<%=String.valueOf(request.getParameter("question1555"))%>');loadModels(document.forms['EmeaForm']);loadModel(document.forms['EmeaForm'],'<%=String.valueOf(request.getParameter("question1532"))%>')">
      <form name="logout" action="./logout.jsp" method="post"></form>
      <html:form action="/Emea">
         <div align="center">
         <table width="850" border="0" cellpadding="2" cellspacing="0" bgcolor="#99CC99">
               <tr bgcolor="#FFFFFF">
               <td width="16%" height="100" align="center" valign="middle" bgcolor="#FFFFFF"><p align="center" class="style28">
                  <img src="./util/imgs/LOGO.gif" width="88" height="88" border="0" /></p>
               </td>
               <td height="125" align="center" valign="bottom" bgcolor="#FFFFFF">
                  <p align="center" class="style28">
                     <span class="style38"><%=messages.getText("messages.emea.thirdtitle")%></span>
                     </p>
                     <p align="center" class="style28">&nbsp;</p>
                  <p align="center" class="style29">          <%=messages.getText("assesment.emea.title") %></p>
               </td>
               <td width="14%" height="100" align="center" valign="middle" bgcolor="#FFFFFF">
                  <p><img src="./util/imgs/cepa logo 1.jpg" width="94" height="73" border="0"/></p>
               </td>
               </tr>
               <tr bgcolor="#FFFFFF">
               <td height="50" colspan="3" align="center" valign="middle" bgcolor="#FFFFFF">
                  <div align="left">
                     <span class="style31">
                        <%=messages.getText("assesment.emea.explanation")%>
                     </span>
                  </div>
               </td>
               </tr>
         </table>
         <table width="850" border="1" cellpadding="5" cellspacing="0">
            <tr>
            	<td height="30" colspan="5" align="left" bgcolor="#004C38"><p class="style27">
					<%=messages.getText("messages.emea.driverdetails")%></p>
				</td>
            </tr>
            <tr>
               	<td width="20%" height="32" align="left" bgcolor="#E2E2E2">
                  <span class="style14">
					<strong><%=messages.getText("module81.question1492.text")%></strong>
				  </span>
               	</td>
<%    String value = (request.getParameter("question1492") == null) ? "" : request.getParameter("question1492");
%>             	<td width="21%" align="left" bgcolor="#FFFFFF">
               		<select name="question1492" onchange="loadCities(document.forms['EmeaForm']);" size="1">
                		<option value="-1"><%=messages.getText("messages.emea.selectcountry")%>..........</option>
<%    for(int i = 6185; i <= 6197; i++) {  
%>                      <option value='<%=i%>' <%=(value.equals(String.valueOf(i))) ? "selected" : "" %>><%=messages.getText("question1492.answer"+i+".text") %></option>
<%    }
%>                   	<option value="6231" <%=(value.equals("6231")) ? "selected" : "" %>><%=messages.getText("question1492.answer6231.text") %></option>
                  	</select>
               	</td>
               	<td width="10%" align="left" bgcolor="#E2E2E2">
                	<span class="style14"><%=messages.getText("module81.question1555.text")%></span>
               	</td>
               	<td width="21%" align="left" bgcolor="#FFFFFF">
                	<select name="question1555" onchange="loadOtherCity(document.forms['EmeaForm']);" size="1">
                    	<option value="-1"><%=messages.getText("messages.emea.selectsite")%>.....</option>
                  	</select>
               	</td>
               	<td width="28%" align="left" bgcolor="#FFFFFF">
                	<div id="othercity" style="visibility:hidden;">
                    	<label>
                        	<input name="question1556" type="text" value='<%=(request.getParameter("question1556") == null || request.getParameter("question1556").equals(messages.getText("messages.emea.writesite"))) ? messages.getText("messages.emea.writesite") : request.getParameter("question1556")%>' />
                     	</label>
                  	</div>
               	</td>
          	</tr>
          	<tr>
               	<td height="34" align="left" bgcolor="#E2E2E2">
                	<span class="style14"><%=messages.getText("module81.question1530.text")%></span>
               	</td>
<%    value = (request.getParameter("question1530") == null) ? "" : request.getParameter("question1530");
%>             	<td colspan="4" align="left" bgcolor="#FFFFFF">
                	<select name="question1530" size="1">
                    	<option value="-1"><%=messages.getText("messages.emea.selectgroup")%>..........</option>
<%    for(int i = 6198; i <= 6202; i++) {  
%>                   	<option value='<%=i%>' <%=(value.equals(String.valueOf(i))) ? "selected" : "" %>><%=messages.getText("question1530.answer"+i+".text") %></option>
<%    }
%>                	</select>
               	</td>
          	</tr>
            <tr>
               <td width="20%" height="93" align="left" bgcolor="#E2E2E2">
                  <p class="style14"><%=messages.getText("module81.question1643.text")%></p>
               </td>
<%    value = (request.getParameter("question1643") == null) ? "" : request.getParameter("question1643"); %>
               <td colspan="4" align="left" bgcolor="#FFFFFF">
                  <label>
                        <textarea name="question1643" cols="38" rows="5"><%=value%></textarea>
                  </label>
               </td>
            </tr>
         </table>
         <table width="850" border="1" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
               <tr>
               <td height="30" colspan="6" align="left" bgcolor="#004c38">
                  <p class="style27">&nbsp;&nbsp;<%=messages.getText("messages.emea.vehicledetails")%></p>
               </td>
               </tr>
               <tr>
               <td width="20%" height="33" align="left" bgcolor="#E2E2E2">
                  <span class="style14"><%=messages.getText("module81.question1531.text")%></span>
               </td>
<%    value = (request.getParameter("question1531") == null) ? "" : request.getParameter("question1531");
%>             <td width="14%" align="left" bgcolor="#FFFFFF">
                  <select name="question1531" onchange="loadModels(document.forms['EmeaForm']);" size="1">
                        <option value="-1"><%=messages.getText("messages.emea.selectmake")%></option>
<%    for(int i = 6206; i <= 6221; i++) { 
%>                      <option value='<%=i%>' <%=(value.equals(String.valueOf(i))) ? "selected" : "" %>><%=messages.getText("question1531.answer"+i+".text") %></option>
<%    }
%>
                     <option value="6232" <%=(value.equals("6232")) ? "selected" : "" %>><%=messages.getText("question1531.answer6232.text") %></option>
                  </select>
               </td>
               <td width="19%" align="left" bgcolor="#FFFFFF">
                     <div id="othermake" style="visibility:hidden;">
                     <span class="style14">
                        <input name="question1558" type="text" value='<%=(request.getParameter("question1558") == null || request.getParameter("question1558").equals(messages.getText("messages.emea.writemake"))) ? messages.getText("messages.emea.writemake") : request.getParameter("question1558")%>' />
                     </span>
                  </div>
                  </td>
               <td width="12%" align="left" bgcolor="#E2E2E2">
                  <span class="style14"><%=messages.getText("module81.question1532.text")%></span>
               </td>
               <td width="15%" align="left">
                  <select name="question1532" onchange="loadOtherModel(document.forms['EmeaForm']);" size="1" id="question1532">
                        <option value="-1"><%=messages.getText("messages.emea.selectmodel")%></option>
                  </select>
               </td>
               <td width="22%" align="left" bgcolor="#F7F7F7">
                  <div id="othermodel" style="visibility:hidden;">
                     <span class="style14">&nbsp;&nbsp;
                           <input type="text" name="question1557" id="question1557" value='<%=(request.getParameter("question1557") == null || request.getParameter("question1557").equals(messages.getText("messages.emea.writemodel"))) ? messages.getText("messages.emea.writemodel") : request.getParameter("question1557")%>' />
                     </span>
                  </div>
               </td>
               </tr>
               <tr>
               <td height="36" align="left" bgcolor="#E2E2E2" class="style14"><%=messages.getText("module81.question1554.text")%></td>
               <td colspan="5" align="left" valign="middle" bgcolor="#FFFFFF">
<%    value = (request.getParameter("question1554") == null) ? "" : request.getParameter("question1554");
%>                <select name="question1554"  size="1">
                        <option value="-1"><%=messages.getText("messages.emea.selectyear") %></option>
                        <option value="6161" <%=(value.equals("6161")) ? "selected" : "" %>><%=messages.getText("question1554.answer6161.text") %></option>
                        <option value="6160" <%=(value.equals("6160")) ? "selected" : "" %>><%=messages.getText("question1554.answer6160.text") %></option>
                        <option value="6159" <%=(value.equals("6159")) ? "selected" : "" %>><%=messages.getText("question1554.answer6159.text") %></option>
                        <option value="6158" <%=(value.equals("6158")) ? "selected" : "" %>><%=messages.getText("question1554.answer6158.text") %></option>
                        <option value="6157" <%=(value.equals("6167")) ? "selected" : "" %>><%=messages.getText("question1554.answer6157.text") %></option>
                        <option value="6156" <%=(value.equals("6156")) ? "selected" : "" %>><%=messages.getText("question1554.answer6156.text") %></option>
                        <option value="6155" <%=(value.equals("6155")) ? "selected" : "" %>><%=messages.getText("question1554.answer6155.text") %></option>
                        <option value="6154" <%=(value.equals("6154")) ? "selected" : "" %>><%=messages.getText("question1554.answer6154.text") %></option>
                        <option value="6153" <%=(value.equals("6153")) ? "selected" : "" %>><%=messages.getText("question1554.answer6153.text") %></option>
                        <option value="6152" <%=(value.equals("6152")) ? "selected" : "" %>><%=messages.getText("question1554.answer6152.text") %></option>
                        <option value="6151" <%=(value.equals("6151")) ? "selected" : "" %>><%=messages.getText("question1554.answer6151.text") %></option>
                  </select>
               </td>
               </tr>
               <tr>
               <td height="36" align="left" bgcolor="#E2E2E2" class="style14">
                  <span class="style17"><%=messages.getText("module81.question1533.text")%></span>
               </td>
<%    value = (request.getParameter("question1533") == null) ? "" : request.getParameter("question1533");
      String unit = (request.getParameter("unit1533") == null) ? "" : request.getParameter("unit1533");
%>             <td colspan="5" align="left" valign="middle" bgcolor="#FFFFFF">
                  <input type="text" name="question1533" value='<%=value%>'/>
                     <select name="unit1533" size="1" >
                     <option value="0" <%=(value.equals("0")) ? "selected" : "" %>><%=messages.getText("answer.units.kilometers") %></option>
                     <option value="1" <%=(value.equals("1")) ? "selected" : "" %>><%=messages.getText("answer.units.miles") %></option>
                  </select>
               </td>
               </tr>
               <tr>
               <td height="34" align="left" bgcolor="#E2E2E2" class="style14"><%=messages.getText("module81.question1534.text")%> </td>
               <td colspan="5" align="left" bgcolor="#FFFFFF">
<%    value = (request.getParameter("question1534") == null) ? "" : request.getParameter("question1534");
%>                  <select name="question1534" size="1">
                        <option value="-1"><%=messages.getText("messages.emea.selectcateogry")%></option>
                     <option value="6016" <%=(value.equals("6016")) ? "selected" : "" %>><%=messages.getText("question1534.answer6016.text")%></option>
                     <option value="6017" <%=(value.equals("6017")) ? "selected" : "" %>><%=messages.getText("question1534.answer6017.text")%></option>
                     <option value="6014" <%=(value.equals("6014")) ? "selected" : "" %>><%=messages.getText("question1534.answer6014.text")%></option>
                     <option value="6024" <%=(value.equals("6024")) ? "selected" : "" %>><%=messages.getText("question1534.answer6024.text")%></option>
                     <option value="6025" <%=(value.equals("6025")) ? "selected" : "" %>><%=messages.getText("question1534.answer6025.text")%></option>
                     <option value="6021" <%=(value.equals("6021")) ? "selected" : "" %>><%=messages.getText("question1534.answer6021.text")%></option>
                     <option value="6022" <%=(value.equals("6022")) ? "selected" : "" %>><%=messages.getText("question1534.answer6022.text")%></option>
                     <option value="6026" <%=(value.equals("6026")) ? "selected" : "" %>><%=messages.getText("question1534.answer6026.text")%></option>
                  </select>
               </td>
            </tr>
            <tr>
               <td width="20%" height="93" align="left" bgcolor="#E2E2E2">
                  <p class="style14"><%=messages.getText("module81.question1644.text")%></p>
               </td>
<%    value = (request.getParameter("question1644") == null) ? "" : request.getParameter("question1644"); %>
               <td colspan="5" align="left" bgcolor="#FFFFFF">
                  <label>
                        <textarea name="question1644" cols="38" rows="5"><%=value%></textarea>
                  </label>
               </td>
            </tr>
            <tr>
               <td align="left" bgcolor="#E2E2E2" class="style14"><%=messages.getText("module81.question1535.text")%></td>
               <td colspan="5">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6031.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6029.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6028.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6033.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6027.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6032.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1535.answer6030.text")%> </span></div></td>
                        </tr>
<%    value = (request.getParameter("question1535") == null) ? "" : request.getParameter("question1535"); %>
                        <tr>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6031"  <%=(value.equals("6031")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6029" <%=(value.equals("6029")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6028" <%=(value.equals("6028")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6033" <%=(value.equals("6033")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6027" <%=(value.equals("6027")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6032" <%=(value.equals("6032")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1535" type="radio" value="6030" <%=(value.equals("6030")) ? "checked" : "" %>/>
                              </label>
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
               <td align="left" bgcolor="#E2E2E2" class="style14"><%=messages.getText("module81.question1536.text")%></td>
               <td colspan="5">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1536.answer6034.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1536.answer6035.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1536.answer6036.text")%> </span></div></td>
                           <td width="15%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1536.answer6037.text")%> </span></div></td>
                           <td width="43%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1536.answer6038.text")%> </span></div></td>
                        </tr>
<%    value = (request.getParameter("question1536") == null) ? "" : request.getParameter("question1536"); %>
                        <tr>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1536" type="radio" value="6034" <%=(value.equals("6034")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1536" type="radio" value="6035" <%=(value.equals("6035")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1536" type="radio" value="6036" <%=(value.equals("6036")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1536" type="radio" value="6037" <%=(value.equals("6037")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1536" type="radio" value="6038" <%=(value.equals("6038")) ? "checked" : "" %>/>
                              </label>
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
               <td align="left" bgcolor="#E2E2E2" class="style14">
                  <p><%=messages.getText("module81.question1537.text")%></p>
               </td>
               <td colspan="5">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6056" <%=(request.getParameter("answer6056") == null || !request.getParameter("answer6056").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6056.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6055" <%=(request.getParameter("answer6055") == null || !request.getParameter("answer6039").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6055.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6039" <%=(request.getParameter("answer6039") == null || !request.getParameter("answer6039").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6039.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6047" <%=(request.getParameter("answer6047") == null || !request.getParameter("answer6047").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6047.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6049" <%=(request.getParameter("answer6049") == null || !request.getParameter("answer6049").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6049.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6057" <%=(request.getParameter("answer6057") == null || !request.getParameter("answer6057").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6057.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6053" <%=(request.getParameter("answer6053") == null || !request.getParameter("answer6053").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6053.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6040" <%=(request.getParameter("answer6040") == null || !request.getParameter("answer6040").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6040.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6060" <%=(request.getParameter("answer6060") == null || !request.getParameter("answer6060").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6060.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6058" <%=(request.getParameter("answer6058") == null || !request.getParameter("answer6058").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6058.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6044" <%=(request.getParameter("answer6044") == null || !request.getParameter("answer6044").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6044.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6041" <%=(request.getParameter("answer6041") == null || !request.getParameter("answer6041").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6041.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6050" <%=(request.getParameter("answer6050") == null || !request.getParameter("answer6050").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6050.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6045" <%=(request.getParameter("answer6045") == null || !request.getParameter("answer6045").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6045.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6042" <%=(request.getParameter("answer6042") == null || !request.getParameter("answer6042").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6042.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6054" <%=(request.getParameter("answer6054") == null || !request.getParameter("answer6054").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6054.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6043" <%=(request.getParameter("answer6043") == null || !request.getParameter("answer6043").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6043.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6046" <%=(request.getParameter("answer6046") == null || !request.getParameter("answer6046").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6046.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6048" <%=(request.getParameter("answer6048") == null || !request.getParameter("answer6048").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6048.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6059" <%=(request.getParameter("answer6059") == null || !request.getParameter("answer6059").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6059.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6052" <%=(request.getParameter("answer6052") == null || !request.getParameter("answer6052").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6052.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6061" <%=(request.getParameter("answer6061") == null || !request.getParameter("answer6061").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6061.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6051" <%=(request.getParameter("answer6051") == null || !request.getParameter("answer6051").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6051.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6581" <%=(request.getParameter("answer6581") == null || !request.getParameter("answer6581").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6581.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6582" <%=(request.getParameter("answer6582") == null || !request.getParameter("answer6582").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6582.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6583" <%=(request.getParameter("answer6583") == null || !request.getParameter("answer6583").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6583.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6938" <%=(request.getParameter("answer6938") == null || !request.getParameter("answer6938").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1537.answer6938.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6939" <%=(request.getParameter("answer6939") == null || !request.getParameter("answer6939").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1537.answer6939.text")%></td>
                        </tr>
                  </table>
               </td>
            </tr>
         </table>
         <table width="850" border="1" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
               <tr>
               <td height="30" colspan="2" align="left" bgcolor="#004C38">
                  <p class="style27"><strong><%=messages.getText("messages.emea.useperformance") %></strong></p>
               </td>
               </tr>
               <tr>
               <td width="20%" align="left" bgcolor="#E2E2E2">
                  <span class="style14"><strong><%=messages.getText("module81.question1538.text")+". "+messages.getText("module81.question1649.text")%></strong></span>
               </td>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  	<tr bgcolor="#E2E2E2">
                    	<td width="14%" bgcolor="#F7F7F7">
                        	<div align="center">
                            	<label></label>
                                <input type="checkbox" name="answer6062" <%=(request.getParameter("answer6062") == null || !request.getParameter("answer6062").equals("on")) ? "" : "checked"%> />
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7">
                        	<span class="style25"><%=messages.getText("question1538.answer6062.text")%></span>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1649") == null) ? "" : request.getParameter("question1649");
%>             				<select name="question1649" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7000" <%=(value.equals("7000")) ? "selected" : "" %>><%=messages.getText("question1649.answer7000.text") %></option>
                   				<option value="7001" <%=(value.equals("7001")) ? "selected" : "" %>><%=messages.getText("question1649.answer7001.text") %></option>
                   				<option value="7002" <%=(value.equals("7002")) ? "selected" : "" %>><%=messages.getText("question1649.answer7002.text") %></option>
                   				<option value="7003" <%=(value.equals("7003")) ? "selected" : "" %>><%=messages.getText("question1649.answer7003.text") %></option>
                   				<option value="7004" <%=(value.equals("7004")) ? "selected" : "" %>><%=messages.getText("question1649.answer7004.text") %></option>
                   				<option value="7005" <%=(value.equals("7005")) ? "selected" : "" %>><%=messages.getText("question1649.answer7005.text") %></option>
                   				<option value="7006" <%=(value.equals("7006")) ? "selected" : "" %>><%=messages.getText("question1649.answer7006.text") %></option>
                        	</select>
						</td>
                  	</tr>
                  	<tr bgcolor="#E2E2E2">
                       	<td bgcolor="#F7F7F7">
                        	<div align="center">
                            	<label></label>
                                <input type="checkbox" name="answer6063" <%=(request.getParameter("answer6063") == null || !request.getParameter("answer6063").equals("on")) ? "" : "checked"%> />
                            </div>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7">
                        	<span class="style25"><%=messages.getText("question1538.answer6063.text")%></span>
                        </td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1650") == null) ? "" : request.getParameter("question1650");
%>             				<select name="question1650" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7010" <%=(value.equals("7010")) ? "selected" : "" %>><%=messages.getText("question1650.answer7010.text") %></option>
                   				<option value="7011" <%=(value.equals("7011")) ? "selected" : "" %>><%=messages.getText("question1650.answer7011.text") %></option>
                   				<option value="7012" <%=(value.equals("7012")) ? "selected" : "" %>><%=messages.getText("question1650.answer7012.text") %></option>
                   				<option value="7013" <%=(value.equals("7013")) ? "selected" : "" %>><%=messages.getText("question1650.answer7013.text") %></option>
                   				<option value="7014" <%=(value.equals("7014")) ? "selected" : "" %>><%=messages.getText("question1650.answer7014.text") %></option>
                   				<option value="7015" <%=(value.equals("7015")) ? "selected" : "" %>><%=messages.getText("question1650.answer7015.text") %></option>
                   				<option value="7016" <%=(value.equals("7016")) ? "selected" : "" %>><%=messages.getText("question1650.answer7016.text") %></option>
                        	</select>
						</td>
                  	</tr>
                    <tr>
                       	<td bgcolor="#F7F7F7">
                        	<label></label>
                            <div align="center">
                            	<label>
                                    <input type="checkbox" name="answer6064" <%=(request.getParameter("answer6064") == null || !request.getParameter("answer6064").equals("on")) ? "" : "checked"%> />
                            	</label>
                        	</div>
                        </td>
                       	<td width="43%" align="left" class="style25">
                       		<%=messages.getText("question1538.answer6064.text")%>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1651") == null) ? "" : request.getParameter("question1651");
%>             				<select name="question1651" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7020" <%=(value.equals("7020")) ? "selected" : "" %>><%=messages.getText("question1651.answer7020.text") %></option>
                   				<option value="7021" <%=(value.equals("7021")) ? "selected" : "" %>><%=messages.getText("question1651.answer7021.text") %></option>
                   				<option value="7022" <%=(value.equals("7022")) ? "selected" : "" %>><%=messages.getText("question1651.answer7022.text") %></option>
                   				<option value="7023" <%=(value.equals("7023")) ? "selected" : "" %>><%=messages.getText("question1651.answer7023.text") %></option>
                   				<option value="7024" <%=(value.equals("7024")) ? "selected" : "" %>><%=messages.getText("question1651.answer7024.text") %></option>
                   				<option value="7025" <%=(value.equals("7025")) ? "selected" : "" %>><%=messages.getText("question1651.answer7025.text") %></option>
                   				<option value="7026" <%=(value.equals("7026")) ? "selected" : "" %>><%=messages.getText("question1651.answer7026.text") %></option>
                        	</select>
						</td>
                  	</tr>
                  	<tr bgcolor="#E2E2E2">
                       	<td bgcolor="#F7F7F7">
                        	<label></label>
                            <div align="center">
                            	<label></label>
                                <input type="checkbox" name="answer6065" <%=(request.getParameter("answer6065") == null || !request.getParameter("answer6065").equals("on")) ? "" : "checked"%> />
                          	</div>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
                        	<%=messages.getText("question1538.answer6065.text")%>
                        </td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1652") == null) ? "" : request.getParameter("question1652");
%>             				<select name="question1652" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7030" <%=(value.equals("7030")) ? "selected" : "" %>><%=messages.getText("question1652.answer7030.text") %></option>
                   				<option value="7031" <%=(value.equals("7031")) ? "selected" : "" %>><%=messages.getText("question1652.answer7031.text") %></option>
                   				<option value="7032" <%=(value.equals("7032")) ? "selected" : "" %>><%=messages.getText("question1652.answer7032.text") %></option>
                   				<option value="7033" <%=(value.equals("7033")) ? "selected" : "" %>><%=messages.getText("question1652.answer7033.text") %></option>
                   				<option value="7034" <%=(value.equals("7034")) ? "selected" : "" %>><%=messages.getText("question1652.answer7034.text") %></option>
                   				<option value="7035" <%=(value.equals("7035")) ? "selected" : "" %>><%=messages.getText("question1652.answer7035.text") %></option>
                   				<option value="7036" <%=(value.equals("7036")) ? "selected" : "" %>><%=messages.getText("question1652.answer7036.text") %></option>
                        	</select>
						</td>
                   	</tr>
                    <tr bgcolor="#E2E2E2">
                    	<td bgcolor="#F7F7F7">
                        	<div align="center">
                            	<label></label>
                                <input type="checkbox" name="answer6066" <%=(request.getParameter("answer6066") == null || !request.getParameter("answer6066").equals("on")) ? "" : "checked"%> />
                          	</div>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7">
                        	<span class="style25"><%=messages.getText("question1538.answer6066.text")%></span>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1653") == null) ? "" : request.getParameter("question1653");
%>             				<select name="question1653" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7040" <%=(value.equals("7040")) ? "selected" : "" %>><%=messages.getText("question1653.answer7040.text") %></option>
                   				<option value="7041" <%=(value.equals("7041")) ? "selected" : "" %>><%=messages.getText("question1653.answer7041.text") %></option>
                   				<option value="7042" <%=(value.equals("7042")) ? "selected" : "" %>><%=messages.getText("question1653.answer7042.text") %></option>
                   				<option value="7043" <%=(value.equals("7043")) ? "selected" : "" %>><%=messages.getText("question1653.answer7043.text") %></option>
                   				<option value="7044" <%=(value.equals("7044")) ? "selected" : "" %>><%=messages.getText("question1653.answer7044.text") %></option>
                   				<option value="7045" <%=(value.equals("7045")) ? "selected" : "" %>><%=messages.getText("question1653.answer7045.text") %></option>
                   				<option value="7046" <%=(value.equals("7046")) ? "selected" : "" %>><%=messages.getText("question1653.answer7046.text") %></option>
                        	</select>
						</td>
                  	</tr>
                  	<tr bgcolor="#E2E2E2">
                       	<td bgcolor="#F7F7F7">
                        	<div align="center">
                            	<label></label>
                                <input type="checkbox" name="answer6067" <%=(request.getParameter("answer6067") == null || !request.getParameter("answer6067").equals("on")) ? "" : "checked"%> />
                         	</div>
                      	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
                           	<%=messages.getText("question1538.answer6067.text")%>
                       	</td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1654") == null) ? "" : request.getParameter("question1654");
%>             				<select name="question1654" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7050" <%=(value.equals("7050")) ? "selected" : "" %>><%=messages.getText("question1654.answer7050.text") %></option>
                   				<option value="7051" <%=(value.equals("7051")) ? "selected" : "" %>><%=messages.getText("question1654.answer7051.text") %></option>
                   				<option value="7052" <%=(value.equals("7052")) ? "selected" : "" %>><%=messages.getText("question1654.answer7052.text") %></option>
                   				<option value="7053" <%=(value.equals("7053")) ? "selected" : "" %>><%=messages.getText("question1654.answer7053.text") %></option>
                   				<option value="7054" <%=(value.equals("7054")) ? "selected" : "" %>><%=messages.getText("question1654.answer7054.text") %></option>
                   				<option value="7055" <%=(value.equals("7055")) ? "selected" : "" %>><%=messages.getText("question1654.answer7055.text") %></option>
                   				<option value="7056" <%=(value.equals("7056")) ? "selected" : "" %>><%=messages.getText("question1654.answer7056.text") %></option>
                        	</select>
						</td>
                  	</tr>
                    <tr>
                       	<td bgcolor="#F7F7F7">
                        	<label></label>
                            <div align="center">
                            	<label>
                                	<input type="checkbox" name="answer6270" <%=(request.getParameter("answer6270") == null || !request.getParameter("answer6270").equals("on")) ? "" : "checked"%> />
                              	</label>
                          	</div>
                       	</td>
                        <td align="left" width="43%" class="style25"><%=messages.getText("question1538.answer6270.text")%></td>
                        <td width="43%" align="left" bgcolor="#F7F7F7" class="style25">
<%    value = (request.getParameter("question1655") == null) ? "" : request.getParameter("question1655");
%>             				<select name="question1655" size="1">
                    			<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   				<option value="7060" <%=(value.equals("7060")) ? "selected" : "" %>><%=messages.getText("question1655.answer7060.text") %></option>
                   				<option value="7061" <%=(value.equals("7061")) ? "selected" : "" %>><%=messages.getText("question1655.answer7061.text") %></option>
                   				<option value="7062" <%=(value.equals("7062")) ? "selected" : "" %>><%=messages.getText("question1655.answer7062.text") %></option>
                   				<option value="7063" <%=(value.equals("7063")) ? "selected" : "" %>><%=messages.getText("question1655.answer7063.text") %></option>
                   				<option value="7064" <%=(value.equals("7064")) ? "selected" : "" %>><%=messages.getText("question1655.answer7064.text") %></option>
                   				<option value="7065" <%=(value.equals("7065")) ? "selected" : "" %>><%=messages.getText("question1655.answer7065.text") %></option>
                   				<option value="7066" <%=(value.equals("7066")) ? "selected" : "" %>><%=messages.getText("question1655.answer7066.text") %></option>
                        	</select>
						</td>
                  	</tr>
                  </table>
               </td>
           </tr>
            <tr>
               <td width="20%" height="93" align="left" bgcolor="#E2E2E2">
                  <p class="style14"><%=messages.getText("module81.question1645.text")%></p>
               </td>
<%    value = (request.getParameter("question1645") == null) ? "" : request.getParameter("question1645"); %>
               <td colspan="4" align="left" bgcolor="#FFFFFF">
                  <label>
                        <textarea name="question1645" cols="38" rows="5"><%=value%></textarea>
                  </label>
               </td>
            </tr>
          	<tr>
               	<td height="34" align="left" bgcolor="#E2E2E2">
                	<span class="style14"><%=messages.getText("module81.question1656.text")%></span>
               	</td>
<%    value = (request.getParameter("question1656") == null) ? "" : request.getParameter("question1656");
%>             	<td colspan="4" align="left" bgcolor="#FFFFFF">
                	<select name="question1656" size="1">
                    	<option value="-1"><%=messages.getText("messages.emea.select")%>..........</option>
                   		<option value="7070" <%=(value.equals("7070")) ? "selected" : "" %>><%=messages.getText("question1656.answer7070.text") %></option>
                   		<option value="7071" <%=(value.equals("7071")) ? "selected" : "" %>><%=messages.getText("question1656.answer7071.text") %></option>
                   		<option value="7072" <%=(value.equals("7072")) ? "selected" : "" %>><%=messages.getText("question1656.answer7072.text") %></option>
                   		<option value="7073" <%=(value.equals("7073")) ? "selected" : "" %>><%=messages.getText("question1656.answer7073.text") %></option>
                   		<option value="7074" <%=(value.equals("7074")) ? "selected" : "" %>><%=messages.getText("question1656.answer7074.text") %></option>
                   		<option value="7075" <%=(value.equals("7075")) ? "selected" : "" %>><%=messages.getText("question1656.answer7075.text") %></option>
                   		<option value="7076" <%=(value.equals("7076")) ? "selected" : "" %>><%=messages.getText("question1656.answer7076.text") %></option>
                   		<option value="7077" <%=(value.equals("7077")) ? "selected" : "" %>><%=messages.getText("question1656.answer7077.text") %></option>
                   		<option value="7078" <%=(value.equals("7078")) ? "selected" : "" %>><%=messages.getText("question1656.answer7078.text") %></option>
                	</select>
               	</td>
          	</tr>
           <tr>
               <td height="46" align="left" bgcolor="#E2E2E2">
                  <strong class="style25"><%=messages.getText("module81.question1539.text")%></strong>
               </td>
<%    value = (request.getParameter("question1539") == null) ? "" : request.getParameter("question1539");
      unit = (request.getParameter("unit1539") == null) ? "" : request.getParameter("unit1539");
%>             <td align="left" valign="middle">
                  <input name="question1539" type="text" value='<%=value%>'/>
                     <select name="unit1539" size="1" >
                     <option value="0" <%=(unit.equals("0")) ? "selected" : "" %>><%=messages.getText("answer.units.kilometers") %></option>
                     <option value="1" <%=(unit.equals("1")) ? "selected" : "" %>><%=messages.getText("answer.units.miles") %></option>
                  </select>
               </td>
            </tr>
            <tr>
               <td width="20%" height="93" align="left" bgcolor="#E2E2E2">
                  <p class="style14"><%=messages.getText("module81.question1646.text")%></p>
               </td>
<%    value = (request.getParameter("question1646") == null) ? "" : request.getParameter("question1646"); %>
               <td colspan="4" align="left" bgcolor="#FFFFFF">
                  <label>
                        <textarea name="question1646" cols="38" rows="5"><%=value%></textarea>
                  </label>
               </td>
            </tr>
               <tr>
               <td height="62" align="left" bgcolor="#E2E2E2">
                  <strong class="style25">
                     <%=messages.getText("module81.question1541.text")%>
                  </strong>
               </td>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" bgcolor="#F7F7F7">
                              <div align="center">
                                 <label>
                                    <input type="checkbox" name="answer6075"  <%=(request.getParameter("answer6075") == null || !request.getParameter("answer6075").equals("on")) ? "" : "checked"%>/>
                                 </label>
                              </div>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1541.answer6075.text")%></td>
                           <td width="15%" bgcolor="#F7F7F7" class="style25">
                              <div align="center">
                                 <label>
                                    <input type="checkbox" name="answer6074"  <%=(request.getParameter("answer6074") == null || !request.getParameter("answer6074").equals("on")) ? "" : "checked"%>/>
                                 </label>
                              </div>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1541.answer6074.text")%></td>
                        </tr>
                        <tr>
                           <td>
                              <div align="center">
                                 <label>
                                    <input type="checkbox" name="answer6076"  <%=(request.getParameter("answer6076") == null || !request.getParameter("answer6076").equals("on")) ? "" : "checked"%>/>
                                 </label>
                              </div>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1541.answer6076.text")%></td>
                           <td class="style25">
                              <div align="center">
                                 <label>
                                    <input type="checkbox" name="answer6073"  <%=(request.getParameter("answer6073") == null || !request.getParameter("answer6073").equals("on")) ? "" : "checked"%>/>
                                 </label>
                              </div>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1541.answer6073.text")%></td>
                        </tr>
                  </table>
               </td>
               </tr>
               <tr>
               <td height="21" align="left" bgcolor="#E2E2E2" class="style36">
                  <%=messages.getText("module81.question1542.text")%>
                  </td>
<%    value = (request.getParameter("question1542") == null) ? "" : request.getParameter("question1542"); %>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input type="radio" name="question1542" value="6077"  <%=(value.equals("6077")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1542.answer6077.text")%></td>
                           <td width="15%" align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input type="radio" name="question1542" value="6079"  <%=(value.equals("6079")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1542.answer6079.text")%></td>
                        </tr>
                        <tr>
                           <td align="center">
                              <label>
                                 <input type="radio" name="question1542" value="6080"  <%=(value.equals("6080")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1542.answer6080.text")%></td>
                           <td align="center">
                              <label>
                                 <input type="radio" name="question1542" value="6081"  <%=(value.equals("6081")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1542.answer6081.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input type="radio" name="question1542" value="6078"  <%=(value.equals("6078")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1542.answer6078.text")%></td>
                           <td align="center" bgcolor="#F7F7F7"><label></label></td>
                           <td align="left" bgcolor="#F7F7F7" class="style25">&nbsp;</td>
                        </tr>
                  </table>
               </td>
               </tr>
               <tr>
               <td height="21" align="left" bgcolor="#E2E2E2" class="style14">
                  <strong class="style25"><%=messages.getText("module81.question1543.text")%></strong>
               </td>
<%    value = (request.getParameter("question1543") == null) ? "" : request.getParameter("question1543"); %>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input type="radio" name="question1543" value="6082"  <%=(value.equals("6082")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1543.answer6082.text")%></td>
                           <td width="15%" align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="radio" name="question1543" value="6084"  <%=(value.equals("6084")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1543.answer6084.text")%></td>
                        </tr>
                        <tr>
                           <td height="35" align="center">
                              <label>
                                 <input type="radio" name="question1543" value="6083"  <%=(value.equals("6083")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1543.answer6083.text")%></td>
                           <td align="center" class="style14">
                              <label>
                                 <input type="radio" name="question1543" value="6085"  <%=(value.equals("6085")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1543.answer6085.text")%></td>
                        </tr>
                  </table>
               </td>
               </tr>
               <tr>
               <td height="21" align="left" bgcolor="#E2E2E2" class="style14">
                  <strong class="style25"><%=messages.getText("module81.question1544.text")%></strong>
               </td>
<%    value = (request.getParameter("question1544") == null) ? "" : request.getParameter("question1544"); %>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input type="radio" name="question1544" value="6086"  <%=(value.equals("6086")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1544.answer6086.text")%></td>
                           <td width="15%" align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="radio" name="question1544" value="6088"  <%=(value.equals("6088")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1544.answer6088.text")%></td>
                        </tr>
                        <tr>
                           <td height="35" align="center">
                              <label>
                                 <input type="radio" name="question1544" value="6087"  <%=(value.equals("6087")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1544.answer6087.text")%></td>
                           <td align="center" class="style14">
                              <label>
                                 <input type="radio" name="question1544" value="6089"  <%=(value.equals("6089")) ? "checked" : "" %> />
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1544.answer6089.text")%></td>
                        </tr>
                  </table>
               </td>
               </tr>
               <tr>
               <td height="21" align="left" bgcolor="#E2E2E2">
                  <strong class="style25"><%=messages.getText("module81.question1545.text")%></strong>
               </td>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6093"  <%=(request.getParameter("answer6093") == null || !request.getParameter("answer6093").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6093.text")%></td>
                           <td width="15%" align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                              <input type="checkbox" name="answer7082"  <%=(request.getParameter("answer7082") == null || !request.getParameter("answer7082").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer7082.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" class="style14">
                              <label>
                                 <input type="checkbox" name="answer7083"  <%=(request.getParameter("answer7083") == null || !request.getParameter("answer7083").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1545.answer7083.text")%></td>
                           <td align="center" class="style14">
                              <label>
                                 <input type="checkbox" name="answer7084"  <%=(request.getParameter("answer7084") == null || !request.getParameter("answer7084").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" class="style25"><%=messages.getText("question1545.answer7084.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6091"  <%=(request.getParameter("answer6091") == null || !request.getParameter("answer6091").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6091.text")%></td>
                           <td align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6090"  <%=(request.getParameter("answer6090") == null || !request.getParameter("answer6090").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6090.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6096"  <%=(request.getParameter("answer6096") == null || !request.getParameter("answer6096").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6096.text")%></td>
                           <td align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6095"  <%=(request.getParameter("answer6095") == null || !request.getParameter("answer6095").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6095.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6092"  <%=(request.getParameter("answer6092") == null || !request.getParameter("answer6092").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6092.text")%></td>
                           <td align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input type="checkbox" name="answer6094"  <%=(request.getParameter("answer6094") == null || !request.getParameter("answer6094").equals("on")) ? "" : "checked"%>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1545.answer6094.text")%></td>
                        </tr>
                  </table>
               </td>
               </tr>
               <tr>
               <td height="21" align="left" bgcolor="#E2E2E2">
                  <strong class="style25"><%=messages.getText("module81.question1546.text")%></strong>
               </td>
<%    value = (request.getParameter("question1546") == null) ? "" : request.getParameter("question1546"); %>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input name="question1546" type="radio" value="6097" <%=(value.equals("6097")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1546.answer6097.text")%></td>
                           <td width="15%" align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input name="question1546" type="radio" value="6100" <%=(value.equals("6100")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1546.answer6100.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#FFFFFF">
                              <label>
                                 <input name="question1546" type="radio" value="6098" <%=(value.equals("6098")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#FFFFFF" class="style25"><%=messages.getText("question1546.answer6098.text")%></td>
                           <td align="center" bgcolor="#FFFFFF" class="style14">
                              <label>
                                 <input name="question1546" type="radio" value="6101" <%=(value.equals("6101")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#FFFFFF" class="style25"><%=messages.getText("question1546.answer6101.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input name="question1546" type="radio" value="6099" <%=(value.equals("6099")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1546.answer6099.text")%></td>
                           <td align="center" bgcolor="#F7F7F7" class="style25">&nbsp;</td>
                           <td align="left" bgcolor="#F7F7F7" class="style25">&nbsp;</td>
                        </tr>
                  </table>
               </td>
               </tr>
               <tr>
               <td height="21" align="left" bgcolor="#E2E2E2">
                  <strong class="style25"><%=messages.getText("module81.question1547.text")%></strong>
               </td>
<%    value = (request.getParameter("question1547") == null) ? "" : request.getParameter("question1547"); %>
               <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="13%" align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input name="question1547" type="radio" value="6102" <%=(value.equals("6102")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td width="29%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1547.answer6102.text")%></td>
                           <td width="15%" align="center" bgcolor="#F7F7F7" class="style14">
                              <label>
                                 <input name="question1547" type="radio" value="6105" <%=(value.equals("6105")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td width="43%" align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1547.answer6105.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#FFFFFF">
                              <label>
                                 <input name="question1547" type="radio" value="6103" <%=(value.equals("6103")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#FFFFFF" class="style25"><%=messages.getText("question1547.answer6103.text")%></td>
                           <td align="center" bgcolor="#FFFFFF" class="style14">
                              <label>
                                 <input name="question1547" type="radio" value="6271" <%=(value.equals("6271")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#FFFFFF" class="style25"><%=messages.getText("question1547.answer6271.text")%></td>
                        </tr>
                        <tr>
                           <td align="center" bgcolor="#F7F7F7">
                              <label>
                                 <input name="question1547" type="radio" value="6104" <%=(value.equals("6104")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td align="left" bgcolor="#F7F7F7" class="style25"><%=messages.getText("question1547.answer6104.text")%></td>
                           <td align="center" bgcolor="#F7F7F7" class="style25">&nbsp;</td>
                           <td align="left" bgcolor="#F7F7F7" class="style25">&nbsp;</td>
                        </tr>
                  </table>
               </td>
               </tr>
            <tr>
               <td width="20%" height="93" align="left" bgcolor="#E2E2E2">
                  <p class="style14"><%=messages.getText("module81.question1647.text")%></p>
               </td>
<%    value = (request.getParameter("question1647") == null) ? "" : request.getParameter("question1647"); %>
               <td colspan="4" align="left" bgcolor="#FFFFFF">
                  <label>
                        <textarea name="question1647" cols="38" rows="5"><%=value%></textarea>
                  </label>
               </td>
            </tr>
         </table>
         <table width="850" border="1" cellspacing="0" cellpadding="5" bgcolor="#FFFFFF">
               <tr>
               <td height="30" colspan="2" align="left" bgcolor="#004c38">
                  <p class="style27"><strong><%=messages.getText("messages.emea.suitablevehicle")%> </strong></p>
               </td>
               </tr>
               <tr>
               <td width="20%" height="34" align="left" bgcolor="#E2E2E2">
                  <span class="style14"><%=messages.getText("module81.question1548.text")%></span>
               </td>
<%    value = (request.getParameter("question1548") == null) ? "" : request.getParameter("question1548"); %>
               <td align="left" bgcolor="#FFFFFF">
                  <select name="question1548"  size="1">
                     <option value="-1" ><%=messages.getText("messages.emea.selectcateogry")%></option>
                     <option value="6110" <%=(value.equals("6110")) ? "selected" : "" %>><%=messages.getText("question1548.answer6110.text")%></option>
                     <option value="6111" <%=(value.equals("6111")) ? "selected" : "" %>><%=messages.getText("question1548.answer6111.text")%></option>
                     <option value="6106" <%=(value.equals("6106")) ? "selected" : "" %>><%=messages.getText("question1548.answer6106.text")%></option>
                     <option value="6113" <%=(value.equals("6113")) ? "selected" : "" %>><%=messages.getText("question1548.answer6113.text")%></option>
                     <option value="6108" <%=(value.equals("6108")) ? "selected" : "" %>><%=messages.getText("question1548.answer6108.text")%></option>
                     <option value="6109" <%=(value.equals("6109")) ? "selected" : "" %>><%=messages.getText("question1548.answer6109.text")%></option>
                     <option value="6112" <%=(value.equals("6112")) ? "selected" : "" %>><%=messages.getText("question1548.answer6112.text")%></option>
                     <option value="6107" <%=(value.equals("6107")) ? "selected" : "" %>><%=messages.getText("question1548.answer6107.text")%></option>
                  </select>
               </td>
               </tr>
               <tr>
               <td align="left" bgcolor="#E2E2E2" class="style14"><%=messages.getText("module81.question1549.text")%></td>
<%    value = (request.getParameter("question1549") == null) ? "" : request.getParameter("question1549"); %>
               <td colspan="5">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6116.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6115.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6114.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6117.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6119.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6118.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1549.answer6120.text")%> </span></div></td>
                        </tr>
                        <tr>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6116" <%=(value.equals("6116")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6115" <%=(value.equals("6115")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6114" <%=(value.equals("6114")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6117" <%=(value.equals("6117")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6119" <%=(value.equals("6119")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6118" <%=(value.equals("6118")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1549" type="radio" value="6120" <%=(value.equals("6120")) ? "checked" : "" %>/>
                              </label>
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
               <td align="left" bgcolor="#E2E2E2" class="style14"><%=messages.getText("module81.question1550.text")%></td>
<%    value = (request.getParameter("question1550") == null) ? "" : request.getParameter("question1550"); %>
               <td colspan="5">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1550.answer6121.text")%> </span></div></td>
                           <td width="14%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1550.answer6122.text")%> </span></div></td>
                           <td width="14%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1550.answer6123.text")%> </span></div></td>
                           <td width="15%" bgcolor="#F7F7F7"><div align="center"><span class="style25"><%=messages.getText("question1550.answer6124.text")%> </span></div></td>
                           <td width="43%" bgcolor="#FFFFFF"><div align="center"><span class="style25"><%=messages.getText("question1550.answer6125.text")%> </span></div></td>
                        </tr>
                        <tr>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1550" type="radio" value="6121" <%=(value.equals("6121")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1550" type="radio" value="6122" <%=(value.equals("6122")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1550" type="radio" value="6123" <%=(value.equals("6123")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#F7F7F7" align="center">
                              <label>
                                 <input name="question1550" type="radio" value="6124" <%=(value.equals("6124")) ? "checked" : "" %>/>
                              </label>
                           </td>
                           <td bgcolor="#FFFFFF" align="center">
                              <label>
                                 <input name="question1550" type="radio" value="6125" <%=(value.equals("6125")) ? "checked" : "" %>/>
                              </label>
                           </td>
                        </tr>
                     </table>
                  </td>
               </tr>
               <tr>
               <td align="left" bgcolor="#E2E2E2" class="style14">
                  <p><%=messages.getText("module81.question1551.text")%></p>
               </td>
               <td colspan="5">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6143" <%=(request.getParameter("answer6143") == null || !request.getParameter("answer6143").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6143.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6142" <%=(request.getParameter("answer6142") == null || !request.getParameter("answer6142").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6142.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6126" <%=(request.getParameter("answer6126") == null || !request.getParameter("answer6126").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6126.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6134" <%=(request.getParameter("answer6134") == null || !request.getParameter("answer6134").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6134.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6136" <%=(request.getParameter("answer6136") == null || !request.getParameter("answer6136").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6136.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6144" <%=(request.getParameter("answer6144") == null || !request.getParameter("answer6144").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6144.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6140" <%=(request.getParameter("answer6140") == null || !request.getParameter("answer6140").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6140.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6127" <%=(request.getParameter("answer6127") == null || !request.getParameter("answer6127").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6127.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6147" <%=(request.getParameter("answer6147") == null || !request.getParameter("answer6147").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6147.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6145" <%=(request.getParameter("answer6145") == null || !request.getParameter("answer6145").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6145.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6131" <%=(request.getParameter("answer6131") == null || !request.getParameter("answer6131").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6131.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6128" <%=(request.getParameter("answer6128") == null || !request.getParameter("answer6128").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6128.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6137" <%=(request.getParameter("answer6137") == null || !request.getParameter("answer6137").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6137.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6132" <%=(request.getParameter("answer6132") == null || !request.getParameter("answer6132").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6132.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6129" <%=(request.getParameter("answer6129") == null || !request.getParameter("answer6129").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6129.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6141" <%=(request.getParameter("answer6141") == null || !request.getParameter("answer6141").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6141.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6130" <%=(request.getParameter("answer6130") == null || !request.getParameter("answer6130").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6130.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6133" <%=(request.getParameter("answer6133") == null || !request.getParameter("answer6133").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6133.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6135" <%=(request.getParameter("answer6135") == null || !request.getParameter("answer6135").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6135.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6146" <%=(request.getParameter("answer6146") == null || !request.getParameter("answer6146").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6146.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6139" <%=(request.getParameter("answer6139") == null || !request.getParameter("answer6139").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6139.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6148" <%=(request.getParameter("answer6148") == null || !request.getParameter("answer6148").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6148.text")%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6138" <%=(request.getParameter("answer6138") == null || !request.getParameter("answer6138").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6138.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6584" <%=(request.getParameter("answer6584") == null || !request.getParameter("answer6584").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6584.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer6585" <%=(request.getParameter("answer6585") == null || !request.getParameter("answer6585").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer6585.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer6586" <%=(request.getParameter("answer6586") == null || !request.getParameter("answer6586").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer6586.text")%></td>
                        </tr>
                        <tr bgcolor="#F7F7F7">
                           <td width="14%" align="center" valign="middle">
                              <label><input type="checkbox" name="answer7080" <%=(request.getParameter("answer7080") == null || !request.getParameter("answer7080").equals("on")) ? "" : "checked"%>/></label>
                           </td>
                           <td width="28%" align="left"><span class="style25"><%=messages.getText("question1551.answer7080.text")%></span></td>
                           <td width="15%" align="center">
                              <label><input type="checkbox" name="answer7081" <%=(request.getParameter("answer7081") == null || !request.getParameter("answer7081").equals("on")) ? "" : "checked"%>/></label>
                              <label></label>
                           </td>
                           <td width="43%" align="left" class="style25"><%=messages.getText("question1551.answer7081.text")%></td>
                        </tr>
                  </table>
               </td>
            </tr>
         </table>
         <table width="850" border="1" cellspacing="0" cellpadding="5">
          	<tr>
               <td height="30" colspan="3" align="left" bgcolor="#004C38"><p class="style27"><strong><%=messages.getText("messages.emea.comments")%>&nbsp; </strong></p></td>
            </tr>
            <tr>
               <td width="20%" height="93" align="left" bgcolor="#E2E2E2">
                  <p class="style14"><%=messages.getText("module81.question1552.text")%></p>
               </td>
<%    value = (request.getParameter("question1552") == null) ? "" : request.getParameter("question1552"); %>
               <td colspan="2" align="left" bgcolor="#FFFFFF">
                  <label>
                        <textarea name="question1552" cols="38" rows="5"><%=value%></textarea>
                  </label>
               </td>
            </tr>
            <tr>
               <td height="30" bgcolor="#E2E2E2">
                  <p class="style25"><strong>&nbsp;&nbsp; </strong></p>
               </td>
               <td width="27%" align="left" bgcolor="#FFFFFF">
                  <p class="style34">
                        <label></label>
                        <label></label>
                     <span class="style35"><%=messages.getText("messages.emea.thanks")%>&nbsp;&nbsp; </span>
                  </p>
               </td>          
               <td width="53%" height="30" align="left" bgcolor="#FFFFFF">&nbsp;&nbsp;
                     <html:submit value='<%=messages.getText("assesment.emea.submit")%>' />
                     <input type="button" value='<%=messages.getText("assesment.emea.cancel")%>' onclick="javascript:document.forms['logout'].submit();" />
                  </td>
               </tr>
         </table>       
         <table width="850" border="1" cellspacing="0" cellpadding="0">
            <tr>
               <td bgcolor="#E2E2E2"><div align="center">
                  <span class="style39">Powered by CEPA  International&nbsp;&nbsp;2009&nbsp;&nbsp;</span></div>
               </td>
            </tr>
            <tr><td align="left" valign="middle">&nbsp;</td></tr>
         </table>
      </div>
      </html:form>
   </body>
<% }  %>
</html:html>
