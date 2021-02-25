

package assesment.communication.util;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailSender implements Serializable{
	
    private static final String SMTP = "smtp.googlemail.com";
    private static final String MAILSERVER_2 = "smtp.cepainternational.com";
    private static final String MAILSERVER_INMOTION = "mail.cepalogistica.com";
    //private static final String MAILSERVER_INMOTION = "mail.cepalogistica.com";
    private static final String REPLY_TO = "info@cepada.com";
     
	private static final String SERVER_PRODUCTION_IP = "172.31.23.142";

	public void send(String toMail, String fromName, String fromMail, String password, String subject, String body) 
        throws Exception {
        send(toMail,new LinkedList(),fromName,fromMail,password,subject,body,new LinkedList(),new LinkedList());
    }
    
    public void send(String toMail, String fromName, String fromMail, String password, String subject, String body, Collection files) throws Exception{
    	send(toMail, new LinkedList(), fromName, fromMail, password, subject, body, files, new LinkedList<String>());
    }
    
    public void send(String toMail, Collection cc, String fromName, String fromMail, String password, String subject, String body, Collection files, Collection<String> bcc) throws Exception{

    	Properties properties = new Properties();

        SMTPAuthenticator auth = null;
        if (fromMail.contains("gmail.com")){
    	    properties.put("mail.smtp.host",SMTP);
        	properties.put("mail.smtp.starttls.enable","true");
        	properties.put("mail.smtp.auth", "true");
        	properties.put("mail.smtp.socketFactory.port", "465");
        	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	properties.put("mail.smtp.socketFactory.fallback", "false");
        	
        	auth = new SMTPAuthenticator(fromMail, password);

        }else{
			if (fromMail.contains("cepainternational.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_2);        	
	        	properties.put("mail.smtp.socketFactory.port", "587");
			}else if (fromMail.contains("cepalogistica.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_INMOTION);        	
			}
        	properties.put("mail.smtp.auth", "true");
        	auth = new SMTPAuthenticator(fromMail, password);
        }

    	properties.put("mail.from",fromName);
    	
        Session mailSession = Session.getInstance(properties,auth);

        InternetAddress[] address = {new InternetAddress(toMail,toMail)};
                
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom( new InternetAddress(fromMail,fromName) );
        message.setRecipients(Message.RecipientType.TO, address);
        
        if(cc != null && cc.size() > 0) {
	        InternetAddress[] ccAddress = new InternetAddress[cc.size()];
	        Iterator it = cc.iterator();
	        int i = 0;
	        while(it.hasNext()) {
	        	String bccEmail = (String)it.next();
	        	ccAddress[i] = new InternetAddress(bccEmail,bccEmail);
	        	i++;
	        }
	        message.setRecipients(Message.RecipientType.CC, ccAddress);
        }
        
        if(bcc != null && bcc.size() > 0) {
	        InternetAddress[] bccAddress = new InternetAddress[bcc.size()];
	        Iterator it = bcc.iterator();
	        int i = 0;
	        while(it.hasNext()) {
	        	String bccEmail = (String)it.next();
	        	bccAddress[i] = new InternetAddress(bccEmail,bccEmail);
	        	i++;
	        }
	        message.setRecipients(Message.RecipientType.BCC, bccAddress);
        }
//       InternetAddress[] bccAddress = {new InternetAddress("federico.millan@cepasafedrive.com","federico.millan@cepasafedrive.com"),new InternetAddress("rodriguez.jme@gmail.com","rodriguez.jme@gmail.com")};
//        message.setRecipients(Message.RecipientType.BCC, bccAddress);
        
        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setDataHandler(new DataHandler(body,"text/html"));
        
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setDataHandler(new DataHandler(body,"text/html"));
//        mbp1.setText(body);
        

        
        // create the Multipart and add its parts to it
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        
        Iterator it = files.iterator();
        while(it.hasNext()) {
            MimeBodyPart mbp2 = new MimeBodyPart();
            // attach the file to the message
            FileDataSource fds = new FileDataSource((String)it.next());
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());
            mp.addBodyPart(mbp2);
        }

        message.setContent(mp);
       	Transport.send(message);
    }

    /**
     * 
     * @param toMail
     * @param smtp
     * @param fromName
     * @param fromMail
     * @param password
     * @param subject
     * @param body
     * @param files
     * @param imageName
     * @param image
     * @throws Exception
     */
    public void send(String toMail, String fromName, String fromMail, String password, String subject, String body, Collection files, String imageName,String image) throws Exception{
    	Collection<String> cc = new LinkedList<String>();
    	send(toMail,fromName,fromMail, password,subject,body,files,imageName,image,cc);
    }
    
    public void send(String toMail, String fromName, String fromMail, String password, String subject, String body, Collection files, String imageName,String image,Collection<String> cc) throws Exception{
        Properties properties = new Properties();
        
        SMTPAuthenticator auth = null;
        if (fromMail.contains("gmail.com")){
    	    properties.put("mail.smtp.host",SMTP);
        	properties.put("mail.smtp.starttls.enable","true");
        	properties.put("mail.smtp.auth", "true");
        	properties.put("mail.smtp.socketFactory.port", "465");
        	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	properties.put("mail.smtp.socketFactory.fallback", "false");
        	
        	auth = new SMTPAuthenticator(fromMail, password);

        }else{
			if (fromMail.contains("cepainternational.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_2);        	
			}else if (fromMail.contains("cepalogistica.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_INMOTION);        	
			}
        	properties.put("mail.smtp.auth", "true");
        	auth = new SMTPAuthenticator(fromMail, password);
        }
        
        properties.put("mail.from",fromName);
        Session mailSession = Session.getInstance(properties,auth);
    
        InternetAddress[] address = {new InternetAddress(toMail,toMail)};
        
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom( new InternetAddress(fromMail,fromName) );
        message.setRecipients(Message.RecipientType.TO, address);
        
        if(cc != null && cc.size() > 0) {
	        InternetAddress[] ccAddress = new InternetAddress[cc.size()];
	        Iterator<String> it = cc.iterator();
	        int i = 0;
	        while(it.hasNext()) {
	        	String bccEmail = it.next();
	        	ccAddress[i] = new InternetAddress(bccEmail,bccEmail);
	        	i++;
	        }
	        message.setRecipients(Message.RecipientType.BCC, ccAddress);
        }

        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setDataHandler(new DataHandler(body,"text/html"));
        
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setDataHandler(new DataHandler(body,"text/html"));
    //    mbp1.setText(body);
        
        MimeBodyPart mbp2 = null;
    
        // attach the file to the message
        if (imageName != null){
        	mbp2 = new MimeBodyPart();
        	FileDataSource fds = new FileDataSource(imageName);
        	mbp2.setDataHandler(new DataHandler(fds));
        	mbp2.setFileName(fds.getName());
        }
    
        // create the Multipart and add its parts to it
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        if (mbp2 != null) mp.addBodyPart(mbp2);

        Iterator it = files.iterator();
        while(it.hasNext()) {
            MimeBodyPart mbp3 = new MimeBodyPart();
            // attach the file to the message
            FileDataSource fds1 = new FileDataSource((String)it.next());
            mbp3.setDataHandler(new DataHandler(fds1));
            mbp3.setFileName(fds1.getName());
            mp.addBodyPart(mbp3);
        }
        
        
        message.setContent(mp);
       	Transport.send(message);
       	
        System.out.println("-----------------------------------------------------");
       	System.out.print("Mail enviado a : "+toMail+" con copia a ");
        it = cc.iterator();
        while(it.hasNext()) {
        	System.out.print(it.next()+" , ");
        }
        System.out.println("-----------------------------------------------------");
    }
    
    public void sendImage(String toMail, String fromName, String fromMail, String password, String subject, Collection files, 
    		String body, String image) throws Exception{
    	String[] s = new String[0];
    	sendImage(toMail,fromName,fromMail,password,subject,files,body,image,s);
    }
    
    public void sendImage(String toMail, String fromName, String fromMail, String password, String subject, Collection files, 
    		String body, String image, String[] cc) throws Exception{
    	

    	Properties properties = new Properties();
        
	    Authenticator auth = null; 
        if (fromMail.contains("gmail.com")){
    	    properties.put("mail.smtp.host",SMTP);
        	properties.put("mail.smtp.starttls.enable","true");
        	properties.put("mail.smtp.auth", "true");
        	// properties.put("mail.smtps.auth", "true");
        	properties.put("mail.smtp.socketFactory.port", "465");
        	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	properties.put("mail.smtp.socketFactory.fallback", "false");
        	
        	auth = new SMTPAuthenticator(fromMail, password);

        }else{
			if (fromMail.contains("cepainternational.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_2);        	
			}else if (fromMail.contains("cepalogistica.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_INMOTION);        	
			}
        	properties.put("mail.smtp.auth", "true");
        	auth = new SMTPAuthenticator(fromMail, password);
        }

    
    	Session mailSession = Session.getInstance(properties,auth);
        
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom( new InternetAddress(fromMail,fromName) );

        InternetAddress[] address = {new InternetAddress(toMail,toMail)};
        message.setRecipients(Message.RecipientType.TO, address);

        if(cc.length > 0) {
        	InternetAddress[] ccAddress = new InternetAddress[cc.length];
        	for(int i = 0; i < cc.length; i++) {
        		ccAddress[i] = new InternetAddress(cc[i],cc[i]);
        	}
            message.setRecipients(Message.RecipientType.CC, ccAddress);
        }
        
        InternetAddress[] replyTo = {new InternetAddress(REPLY_TO,REPLY_TO)};
        message.setReplyTo(replyTo);
        
        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setDataHandler(new DataHandler(body,"text/html"));
        
        // create the Multipart and add its parts to it
        Multipart mp = new MimeMultipart("related");

		BodyPart texto = new MimeBodyPart ();
		texto.setContent(body,"text/html");
		mp.addBodyPart(texto);

        Iterator it = files.iterator();
        while(it.hasNext()) {
			MimeBodyPart fileAtt = new MimeBodyPart();
			fileAtt.attachFile((String)it.next());
			mp.addBodyPart(fileAtt);
        }
        
		MimeBodyPart imagen = new MimeBodyPart();
		imagen.attachFile(image);
		imagen.setHeader("Content-ID","<figura1>");
		mp.addBodyPart(imagen);
        
        message.setContent(mp);
    
       	Transport.send(message);
    }

    public void sendImage(String toMail, String fromName, String fromMail, String password, String subject, Collection files, String body, String image, Collection bcc) throws Exception{
    	
        Properties properties = new Properties();
        SMTPAuthenticator auth = null;
        if (fromMail.contains("gmail.com")){
    	    properties.put("mail.smtp.host",SMTP);
        	properties.put("mail.smtp.starttls.enable","true");
        	properties.put("mail.smtp.auth", "true");
        	properties.put("mail.smtp.socketFactory.port", "465");
        	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	properties.put("mail.smtp.socketFactory.fallback", "false");
        	
        	auth = new SMTPAuthenticator(fromMail, password);

        }else{
			if (fromMail.contains("cepainternational.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_2);        	
			}else if (fromMail.contains("cepalogistica.com")){
	    	    properties.put("mail.smtp.host",MAILSERVER_INMOTION);        	
			}
        	properties.put("mail.smtp.auth", "true");
        	auth = new SMTPAuthenticator(fromMail, password);
        }
    	Session mailSession = Session.getInstance(properties,auth);
    
        
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom( new InternetAddress(fromMail,fromName) );

        InternetAddress[] bccAddress = new InternetAddress[bcc.size()];
        Iterator it = bcc.iterator();
        int i = 0;
        while(it.hasNext()) {
        	String bccEmail = (String)it.next();
        	bccAddress[i] = new InternetAddress(bccEmail,bccEmail);
        	i++;
        }
        message.setRecipients(Message.RecipientType.BCC, bccAddress);
        
        InternetAddress[] address = {new InternetAddress(toMail,toMail)};
        message.setRecipients(Message.RecipientType.TO, address);

        InternetAddress[] replyTo = {new InternetAddress(REPLY_TO,REPLY_TO)};
        message.setReplyTo(replyTo);
        
        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setDataHandler(new DataHandler(body,"text/html"));
        
        // create the Multipart and add its parts to it
        Multipart mp = new MimeMultipart("related");

		BodyPart texto = new MimeBodyPart ();
		texto.setContent(body,"text/html");
		mp.addBodyPart(texto);

        it = files.iterator();
        while(it.hasNext()) {
			MimeBodyPart fileAtt = new MimeBodyPart();
			fileAtt.attachFile((String)it.next());
			mp.addBodyPart(fileAtt);
        }
        
		MimeBodyPart imagen = new MimeBodyPart();
		imagen.attachFile(image);
		imagen.setHeader("Content-ID","<figura1>");
		mp.addBodyPart(imagen);
        
        message.setContent(mp);
    
       	Transport.send(message);
    }

    /**
     * Devuelve la cuenta de email a utilizar para enviar el email.
     * [0]: address
     * [1]: password
     * @return
     */
    public String[] getAvailableMailAddress() {
    	String[] address = new String[2];
        try {
        	Class.forName("org.postgresql.Driver");
        	
        	Connection conn = null;
        	if(isProductionServer()) {
        		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        	}else {
        		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
//        		conn = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/datacenter5","postgres","pr0v1s0r1A");
        	}
               
          
        	Calendar today = Calendar.getInstance();
        	//String sql = "SELECT address,password,date,sended FROM emailaddress WHERE address LIKE '%cepada%' ORDER BY date";
        	
        	// String sql = "SELECT address,password,date,sended FROM emailaddress WHERE address LIKE 'da@cepainternational%' ORDER BY date";
        	
        	// Cambiado por Gus 24-10-2011
        	String sql = "SELECT address,password,date,sended FROM emailaddress WHERE address LIKE '%cepalogistica%' ORDER BY date";
        	//String sql = "SELECT address,password,date,sended FROM emailaddress ORDER BY date";
          
        	Statement stQuery = conn.createStatement();
        	Statement stUpdate = conn.createStatement();
        	ResultSet set = stQuery.executeQuery(sql);
          
        	boolean found = false;
        	while(set.next() && !found) {
        		Calendar last = Calendar.getInstance();
        		last.setTime(set.getDate("date"));
           
        		if(last.get(Calendar.DATE) != today.get(Calendar.DATE) || last.get(Calendar.MONTH) != today.get(Calendar.MONTH) || last.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
		            address[0] = set.getString("address");
		            address[1] = set.getString("password");
		            String update = "UPDATE emailaddress SET sended = 1, \"date\" = CURRENT_TIMESTAMP WHERE address = '"+address[0]+"' ";
		            //System.out.println(update);
		            stUpdate.execute(update);
		            found = true;
		            //System.out.println("Email enviado desde: "+address[0]);
        		}else {
		            int sended = set.getInt("sended");
		            if(sended < 1000) {
		            	address[0] = set.getString("address");
		            	address[1] = set.getString("password");
		             
		            	String update = "UPDATE emailaddress SET sended = "+String.valueOf(sended+1)+", \"date\" = CURRENT_TIMESTAMP WHERE address = '"+address[0]+"' ";
		            	//System.out.println(update);
		            	stUpdate.execute(update);
		            	found = true;
		            	//System.out.println("Email enviado desde: "+address[0]);
		            }
        		}
        	}
         }catch (Exception e) {
        	 e.printStackTrace();
         }
         System.out.println("Enviando email desde "+address[0]);
         return address;
    }

	public int getAddressCount() {
    	int address = 0;
        try {
        	Class.forName("org.postgresql.Driver");
        	
        	Connection conn = null;
        	if(isProductionServer()) {
        		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
        	}else {
        		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/datacenter5","postgres","pr0v1s0r1A");
        	}
               
        	String sql = "SELECT COUNT(*) AS count FROM emailaddress WHERE address LIKE 'da@cepainternational%'";
          
        	Statement stQuery = conn.createStatement();
        	ResultSet set = stQuery.executeQuery(sql);
        	
        	set.next();
        	address = set.getInt("count");
        	
        }catch (Exception e) {
       	 	e.printStackTrace();
        }
		return address;
	}

	/**
	 * Devuelve true si estoy en el servidor de produccion y false
	 * en caso contario.
	 * @return
	 */
	public static boolean isProductionServer(){
		try{
			/*InetAddress inetAddress = InetAddress.getLocalHost();
			String ip = inetAddress.getHostAddress();
			if (ip.equalsIgnoreCase(SERVER_PRODUCTION_IP)){
				return true;
			}else{
				return false;
			}*/
			return new File("/home/ubuntu/").exists();
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public void send(String toMail, String smtp,String fromName, String fromMail, String password, String subject, String body, Collection bcc)
		throws MessagingException, UnsupportedEncodingException{
		Authenticator auth = null;
		
        Properties properties = new Properties();
        properties.put("mail.smtp.host",smtp);
        
        
        if (smtp.contains("gmail.com")){
        	System.out.println("Gmail");
        	properties.put("mail.smtp.starttls.enable","true");
        	properties.put("mail.smtp.auth", "true");
        	// properties.put("mail.smtps.auth", "true");
        	properties.put("mail.smtp.socketFactory.port", "465");
        	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        	properties.put("mail.smtp.socketFactory.fallback", "false");
        	
        	auth = new SMTPAuthenticator(fromMail, password);

        }else{
        	properties.put("mail.smtp.auth", "true");
        	auth = new SMTPAuthenticator(fromMail, password);
        }
        
        
        properties.put("mail.from",fromName);
        // Session mailSession = Session.getInstance(properties,null);
        Session mailSession = Session.getInstance(properties,auth);

        InternetAddress[] address = {new InternetAddress(toMail,toMail)};
        Message message = new MimeMessage(mailSession);
        message.setFrom( new InternetAddress(fromMail,fromName) );
        message.setRecipients(Message.RecipientType.TO, address);
        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setContent(message.toString(),"text/html");
        message.setDataHandler(new DataHandler(body,"text/html"));
        
        InternetAddress[] replyTo = {new InternetAddress(fromMail,fromName)};
        message.setReplyTo(replyTo);

	    // Si lleva copia oculta:
	    if (bcc != null){
	    	Iterator it = bcc.iterator();
    		InternetAddress[] bccAddress = new InternetAddress[bcc.size()];
    		int i = 0; 
	    	while(it.hasNext()) {
	    		String bccStr = (String)it.next();
	    		bccAddress[i] = new InternetAddress(bccStr,bccStr);
	    		i++;
	    	}
    		message.setRecipients(Message.RecipientType.BCC, bccAddress);
	    }

	    if (smtp.contains("gmail.com")){
        	Transport.send(message);
        }else{
        	Transport.send(message);
        	/*
        	Transport transport = mailSession.getTransport(address[0]);
        	transport.connect(smtp, fromMail, password);
        	transport.sendMessage(message,address);
        	*/
        }
	    
    }

	public boolean sendImageMandrill(Collection toMail,String fromName, String fromMail,
			String subject, String body, Collection<String> attachments,
			Collection<String> names, String replyToMail, String replyToName, Collection bccMail, String image)
			throws Exception{
	
	    try{
	    	
	    	Properties properties = new Properties();
			Authenticator auth = null;
			String to = null;
			String bcc = null;
			Session mailSession = null;
			LinkedList addressList = null;
			Iterator it = null;
			Message message;
			InternetAddress[] addressTo = null;
			InternetAddress[] addressBcc = null;
			int i = 0;
			
			final String username = "fcarriquiry@cepasafedrive.com";
	        final String password = "QJUV8HKw1Hd714Q2sfo2yA";
	        final String host= "smtp.mandrillapp.com";
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        //props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.mandrillapp.com");
	        props.put("mail.smtp.port", "587");


	        mailSession = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });
			
		    message = new MimeMessage(mailSession);
		    
		    /* To */		 
		    if (toMail == null || toMail.size() == 0){
		    	toMail.add(fromMail);
		    }
		    
		   //  if (toMail != null && toMail.size() > 0){
			    it = toMail.iterator();
			    addressList = new LinkedList();
			    i = 0;
			    
			    // Filtro direcciones de emails vacias:
			    while (it.hasNext()) {
			    	to = (String) it.next();		
			    	if (!empty(to) && to.contains("@")) { 
			    		addressList.add(to);	    		
			    	}
			    	i++;
			    }
		    
			    // Armo lista de direcciones de emails definitiva:
			    
			    if (addressList.size() > 0){
			    	i = 0;
			    	addressTo = new InternetAddress[addressList.size()];
			    	it = addressList.iterator();
			    	while (it.hasNext()){
			    		to = (String) it.next();
			    		addressTo[i] = new InternetAddress(to,to);
			    		i++;
			    	}
			    }
		   // }
		    if (addressTo != null && addressTo.length > 0){
		    	// message.setRecipients(Message.RecipientType.TO, addressTo);
		    	message.addRecipients(Message.RecipientType.TO, addressTo);
		    }
		    /* Fin TO: */
		    
		    /* BCC */
		    if (bccMail != null && bccMail.size() > 0){
			    it = bccMail.iterator();
			    addressList = new LinkedList();
			    i = 0;			    
			    // Filtro direcciones de emails vacias:
			    while (it.hasNext()) {
			    	bcc = (String) it.next();		
			    	if (!empty(bcc) && bcc.contains("@")) { 
			    		// bcc = "gbenaderet@cepasafedrive.com"; // TODO
			    		addressList.add(bcc);	    		
			    	}
			    	i++;
			    }		    
			    // Armo lista de direcciones de emails definitiva:			    
			    if (addressList.size() > 0){
			    	i = 0;
			    	addressBcc = new InternetAddress[addressList.size()];
			    	it = addressList.iterator();
			    	while (it.hasNext()){
			    		bcc = (String) it.next();
			    		addressBcc[i] = new InternetAddress(bcc,bcc);
			    		i++;
			    	}
			    }
		    }
		    // Si lleva copia oculta:
		    if (addressBcc != null && addressBcc.length > 0){
		    	message.addRecipients(Message.RecipientType.BCC, addressBcc);
		    	// 	message.setRecipients(Message.RecipientType.BCC,addressBcc);
			}
		    /* Fin BCC */
		   
		   // Si tiene algun destinatario ahi si mando el mail
		    if ((addressBcc != null && addressBcc.length > 0) ||
		    		(addressTo != null && addressTo.length > 0) ){
		    
			    message.setFrom( new InternetAddress(fromMail,fromName) );			  
			    message.setSentDate(new Date());
			    message.setSubject(subject);
			    
			    if (replyToMail != null && replyToName != null){
			    	InternetAddress[] replyTo = new InternetAddress[1];
			    	replyTo[0] = new InternetAddress(replyToMail,replyToName);
			    	message.setReplyTo(replyTo);
			    }else{
			    	 InternetAddress[] replyTo = {new InternetAddress(fromMail,fromName)};
			         message.setReplyTo(replyTo);
			    }
			    
			    // En esta parte creamos el contenedor para el cuerpo html del mensaje
			    MimeBodyPart messageBodyPart = new MimeBodyPart();
			    messageBodyPart.setContent(body,"text/html");
			    Multipart multipart = new MimeMultipart();
			    multipart.addBodyPart(messageBodyPart);
			    
			    if(image != null) {
					MimeBodyPart imagen = new MimeBodyPart();
					imagen.attachFile(image);
					imagen.setHeader("Content-ID","<figura1>");
					multipart.addBodyPart(imagen);
			    }
			    
			    Iterator<String> atts = attachments.iterator();
			    Iterator<String> nameIt = names.iterator();
			    while(atts.hasNext()) {
			    	String attachment = atts.next();
			    	String name = nameIt.next();
				    // Aquí se anexa el attachment
				    messageBodyPart = new MimeBodyPart();
				    DataSource source = new FileDataSource(attachment);
				    messageBodyPart.setDataHandler(new DataHandler(source));
				    messageBodyPart.setFileName(name);
				    multipart.addBodyPart(messageBodyPart);
			    }
			    message.setContent(multipart);
			    
	        	Transport.send(message);
			
				return true;
		    }
		
	    }catch(com.sun.mail.smtp.SMTPSendFailedException e){
	    	//System.out.println("com.sun.mail.smtp.SMTPSendFailedException: 454 Atencao: voce atingiu o limite maximo de envio: 1000 mensagens em 60 minuto(s). Apos este periodo o envio volta ao normal.");
	    	e.printStackTrace();
	    	return false;
	    	
		}catch(Exception e){
		   	e.printStackTrace();
			return false;
		}
		
		System.out.println("No se envió el email, destinatarios vacios.");
		return false;
		
	}

    private boolean empty(String label) {
        return label == null || label.trim().length() == 0;
    }
} 
