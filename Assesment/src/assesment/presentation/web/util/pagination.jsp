<%@page language="java" 
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%!
		int i; int pageSize; int total; int pageNum; int pageMax;
		String link; boolean all; String attribute; String value;
		AssesmentAccess sys; Text messages;
%>
<%
		sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
		messages=sys.getText();
		all = false;
		pageSize = Integer.parseInt(request.getParameter("size"));
		if (pageSize < 1) pageSize = 1;
		pageNum = Integer.parseInt(request.getParameter("num"));
		if (pageNum < 1) pageNum = 1;
		total = Integer.parseInt(request.getParameter("total"));
		attribute = request.getParameter("attribute");
		value = request.getParameter("value");
		pageMax=(int)total/pageSize;		
		if(total % pageSize > 0) {
			pageMax++;
		}
		if(pageNum > pageMax) {
		    pageNum = pageMax;
		}
		link = "layout.jsp?refer="+(String)request.getParameter("link")+
				"&attribute="+attribute+"&value="+value+"&pageSize="+pageSize;
%>
								<table width='100%' border="0">
					         		<tr class="default">
         								<td class="default">	
					   			       		<table width='20%'  border="0" class="default" align="left">
					          					<tr class="default">
          											<td class="default">
<%
				if(pageNum > 1 && !all){
%>											        	<a href='<%=link%>&pageNum=1'><<</a>
<%				}else {
%>														<<	 
<%				}
%>         			          						</td>
					           						<td class="default">
<%				if(pageNum>1 && !all){
%>											        	<a href='<%=link%>&pageNum=<%=pageNum-1%>'><</a>
<%				}else{
%>														<	 
<%				}
%>		        									</td>
					    				      		<td class="default">
<%				if((!all && pageMax > 1 )&&(total<=100)){
%>											        	<a href='<%=link%>&all=all'>														
														<%=messages.getText("generic.messages.all")%>
														</a>
<%				}else{						
%>						
														<!--  Button All Disabled for the moment. -->
														<!-- <%=messages.getText("generic.messages.all")%> -->
<%				}
%>	        										</td>
           											<td align="center" class="default">
<%				if(pageNum<pageMax && !all){
%>											        	<a href='<%=link%>&pageNum=<%=pageNum+1%>'>></a>
<%				}else{
%>														>
<%				}
%>		        									</td>
					          						<td class="default">
<%				if(pageNum<pageMax && !all){
%>											        	<a href='<%=link%>&pageNum=<%=pageMax%>'>>></a>
<%				}else {
%>														>>	 
<%				}
%>					        						</td>
  												</tr>        		
					         				</table>
          								</td>
					          			<td>
          									<table align="center">
					          					<tr class="default">
<%
				if(pageMax < 15) {
					for(i=1; i<=pageMax; i++){
%>													<td class="default">
<%						if( (pageNum!=i) ){
%>														<a href='<%=link%>&pageNum=<%=i%>'><%=i%></a>				
<%						}else{		
%>														<%=i%>
<%						}			
%>													</td>	
<%					}				
				}else{
					int start = 1;
					if((pageNum > 2) && pageNum < pageMax-5){
						start = pageNum - 2;
					}
					int end = pageMax - 5;
					if(end - start < 10) {
						for(i=start; i < pageMax; i++) {
%>													<td class="default">
<%							if( (pageNum!=i) ){
%>														<a href='<%=link%>&pageNum=<%=i%>'><%=i%></a>				
<%							}else{		
%>														<%=i%>
<%							}	
%>													</td>		
<%						}
					}else {
						for(i=start; i < start+5; i++) {
%>													<td class="default">
<%							if( (pageNum!=i) ){
%>														<a href='<%=link%>&pageNum=<%=i%>'><%=i%></a>				
<%							}else{		
%>														<%=i%>
<%							}			
%>													</td>
<%						}
%>													<td class="default">. . .</td>				
<%						for(i=pageMax-4; i <= pageMax; i++) {
%>													<td class="default">
<%							if( (pageNum!=i) ){
%>														<a href='<%=link%>&pageNum=<%=i%>'><%=i%></a>				
<%							}else{		
%>														<%=i%>
<%							}			
%>													</td>
<%						}
					}
				}			
%>  						  	   				</tr>
		  	 								</table>  			
					          			</td>
		          						<td class="default">    
		          						<!--  Show 1..10 of 23  -->      
<%				if( ((pageSize*(pageNum))-total) > 0 ){           	
%>					         				<%=(pageSize*pageNum-pageSize+1)+"-"+total+" "+messages.getText("generic.messages.of")+" "+total%>
<%				}else {	
%>											<%=(pageSize*(pageNum-1)+1)+"-"+(pageSize*pageNum)+" "+messages.getText("generic.messages.of")+" "+total%>
<%				}
%>          							</td>
					      	  		</tr> 		    	
								</table> 		
