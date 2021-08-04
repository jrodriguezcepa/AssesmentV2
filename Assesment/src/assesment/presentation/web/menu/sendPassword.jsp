<%@page import="assesment.presentation.translator.web.util.Util"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.LinkedList"%>
<%@page import="assesment.communication.util.MailSender"%>
<%@page import="assesment.communication.util.MD5"%>
<%@page language="java" 
	errorPage="../exception.jsp"
	import="java.util.HashMap"
	import="org.json.JSONObject"
	import="org.json.JSONArray"
	import="java.util.Iterator"
	import="java.sql.Statement"
	import="java.sql.DriverManager"
	import="java.sql.Connection"
	import="java.sql.ResultSet"
%>

<%	
	Class.forName("org.postgresql.Driver");
	Connection connDA = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");

	Statement st0 = connDA.createStatement();
	Statement st1 = connDA.createStatement();
	
	String login = request.getParameter("user");
	String emailInserted = request.getParameter("email");
	String sql = "SELECT loginname, email FROM users WHERE role = 'multiassessment'";
	boolean exec = false;
	if(!Util.empty(login)) {
		sql += " AND LOWER(TRIM(loginname)) = '"+login.trim().toLowerCase()+"'";
		exec = true;
	}
	if(!Util.empty(emailInserted)) {
		sql += " AND LOWER(TRIM(email)) = '"+emailInserted.trim().toLowerCase()+"'";
		exec = true;
	}

	JSONObject obj = new JSONObject();
	if(exec) {
		System.out.println(sql);
		ResultSet set = st0.executeQuery(sql);
		if(set.next()) {
			String user = set.getString(1);
			String email = set.getString(2);
			String link = new MD5().encriptar(user+"_"+System.currentTimeMillis());
			st1.execute("INSERT INTO forgotpassword VALUES ('"+link+"', '"+user+"', NOW())");
			link = "https://www.direcaoeficiente.ticketlog.com.br/assesment/password.jsp?link="+link;
			String body = "<html><body><div>Para realizar a troca de clave no aplicativo TicketLog, clique no link abaixo:</div>";
			body += "<div>"+link+"</div>";
			body += "<div>Esse link expirará em 2 horas</div>";
			body += "</body></html>";
			MailSender sender = new MailSender();
			Collection to = new LinkedList();
			to.add(email);
			sender.sendImageMandrill(to, "TicketLog", "support@cepasafedrive.com", "TicketLog - Senha", body, new LinkedList(), new LinkedList(), "support@cepasafedrive.com", "CEPASAFEDRIVE", new LinkedList(), null);
			obj.put("result",1);
		} else {
			obj.put("result",0);
		}
	} else {
		obj.put("result",0);
	}
	st0.close();
	connDA.close();
%>
<%=obj.toString()%>