<%@page language="java" 
	import="java.util.*"
	import="assesment.business.*"
	import="assesment.communication.language.*"
%>

<%@ taglib uri="/WEB-INF/struts-html.tld"
        prefix="html"
%>

<%!
		int i; 
		int pageSize; // cantidad de registros por pagina
		int total; // total de registros
		int pageNum;  // numero de pagina
		int pageMax; 
		boolean all; 
		AssesmentAccess sys; Text messages;
%>
<%
		sys=(AssesmentAccess)session.getAttribute("AssesmentAccess");
		messages=sys.getText();
		all = false;
		pageSize = Integer.parseInt(request.getParameter("size")); 
		if (pageSize < 1) pageSize = 1;
		pageNum = Integer.parseInt(request.getParameter("num"));
		if (pageNum < 0) pageNum = 1;
		total = Integer.parseInt(request.getParameter("total"));
		Hashtable parameters = (Hashtable)session.getAttribute("paginationParameters");
		session.removeAttribute("paginationParameters");
		pageMax=(int)total/pageSize;		
		if(total % pageSize > 0) {
			pageMax++;
		}
		if(pageNum > pageMax) {
		    pageNum = pageMax;
		}
//		link = (String)request.getParameter("link")+"?pageSize="+pageSize;
//		Enumeration en = parameters.keys();		
//		while(en.hasMoreElements()) {
//		    String key = (String)en.nextElement();
//		    String value = (String)parameters.get(key);
//		    link += "&"+key+"="+value;
//		}
%>
								<table width='100%' border="0">
					         		<tr class="default">
         								<td class="default">	
					   			       		<table width='20%'  border="0" class="default" align="left">
					          					<tr class="default">
          											<td class="default">          												
<%
				if(pageNum > 1 && !all){
%>											        	<a href='javascript:submitPagination(1,false)'><<</a>
<%				}else {
%>														<<	 
<%				}
%>         			          						</td>
					           						<td class="default">
<%				if(pageNum>1 && !all){
%>											        	<a href='<%="javascript:submitPagination("+(pageNum-1)+",false)"%>'><</a>
<%				}else{
%>														<	 
<%				}
%>		        									</td>
					    				      		<td class="default">
<%				if((!all && pageMax > 1 )&&(total<=100)){ %>
														<a href='javascript:submitPagination(1,true)'>
															<%=messages.getText("generic.messages.all")%>
														</a>
<%				}else{ %>	
 													    <!--  Button All Disabled for the moment. -->
 													    <!-- <%=messages.getText("generic.messages.all")%> -->
<%				}
%>	        										</td>
           											<td align="center" class="default">
<%				if(pageNum<pageMax && !all){
%>											        	<a href='<%="javascript:submitPagination("+(pageNum+1)+",false)"%>'>></a>
<%				}else{
%>														>
<%				}
%>		        									</td>
					          						<td class="default">
<%				if(pageNum<pageMax && !all){
%>											        	<a href='<%="javascript:submitPagination("+pageMax+",false)"%>'></a>
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
%>														<a href='<%="javascript:submitPagination("+i+",false)"%>'><%=i%></a>				
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
%>														<a href='<%="javascript:submitPagination("+i+",false)"%>'><%=i%></a>				
<%							}else{		
%>														<%=i%>
<%							}	
%>													</td>		
<%						}
					}else {
						for(i=start; i < start+5; i++) {
%>													<td class="default">
<%							if( (pageNum!=i) ){
%>														<a href='<%="javascript:submitPagination("+i+",false)"%>'><%=i%></a>				
<%							}else{		
%>														<%=i%>
<%							}			
%>													</td>
<%						}
%>													<td class="default">. . .</td>				
<%						for(i=pageMax-4; i <= pageMax; i++) {
%>													<td class="default">
<%							if( (pageNum!=i) ){
%>														<a href='<%="javascript:submitPagination("+i+",false)"%>'><%=i%></a>				
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
