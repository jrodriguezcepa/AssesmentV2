package assesment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.util.MD5;
import assesment.communication.util.MailSender;

public class MailingAbitab {
	
	//GMAIL
/*    private static final String FROM_NAME = "CEPA - Driver Assessment";
    private static final String FROM_DIR = "info@cepada.com";
    private static final String PASSWORD = "info01";
    private static final String SMTP = "smtp.gmail.com";
*/    
    //LOCAWEB
    protected static final String SMTP = "smtp.cepasafedrive.com";
    protected static final String FROM_NAME = "CEPA - Driver Assessment";
    protected static final String FROM_DIR = "da@cepasafedrive.com";
    protected static final String PASSWORD = "dacepa09";

    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn1 = DriverManager.getConnection("jdbc:postgresql://67.192.60.149:5432/assesment","postgres","pr0v1s0r1A");
            Statement st = conn1.createStatement();
/*            
            ResultSet set = st.executeQuery("SELECT COUNT(*) AS count FROM userassesments ua JOIN users u ON u.loginname = ua.loginname WHERE assesment = 30 AND enddate IS NOT NULL");
            set.next();
            int total = set.getInt("count");
            
			int addressCount = sender.getAddressCount();
			
			int number = total / addressCount;
			if(total % addressCount > 0) {
				number++;				
			}
			
*/			
			MailSender sender = new MailSender();

			ResultSet set = st.executeQuery("SELECT u.email FROM userassesments ua JOIN users u ON u.loginname = ua.loginname WHERE assesment = 30 AND enddate IS NOT NULL AND email NOT IN ('a@a.com','aaguilera21@hotmail.com','abarreirob@gmail.com','abcarteleria@gmail.com','abernardi@antel.com.uy','18nanicel@gmail.com','abitancurt@gmail.com','abitancurt@gmail.com','aboghosian2@yahoo.com','acabrera2006@hotmail.com','acberna@montevideo.com.uy','acha125@hotmail.com','acostaclaudio17@hotmail.com','a.curbelo71@hotmail.com','a@dasf.com','adiaz@piso4.imm.com.uy','adimitrius834@gmail.com','adrianaandrade68@gmail.com','adriana_umpierrez@hotmail.com','adrian_m_lopez@hotmail.com','adrian-viera@hotmail.com','adricapo@adinet.com.uy','adridez@gmail.com','adriky19@hotmail.com','AFIRPO@NORDEX.COM.UY','aflojalequecolea@gmail.com','agab@adinet.com.uy','agbh@adinet.com.uy','agerontakis@gmail.com','a@gmail.com','agomez@comero.com.uy','agomez@comero.com.uy','agrp@live.com.ar','agu11@adinet.com.uy','aguantelablanca@hotmail.com','agustinharetche@hotmail.com','agustinponcedeleon@carle-andrioli.com.uy','ahalegoa@gmail.com','ahansen@geocom.com.uy','ahansen@geocom.com.uy','ahansen@geocom.com.uy','aiglesias68@gmail.com','airamnoel@hotmail.com','ajcalimares@hotmail.com','akon608@hotmail.com','albertoba54@hotmail.com','albiceleste@adinet.com.uy','albita123@hotmail.com','aldobon@adinet.com.uy','aldocabral13hotmail.com','aldubra16@hotmail.com','alecamiou@adinet.com.uy','alecappi@hotmail.com','aledkmilli@gmail.com','alegas22@gmail.com','aleiros@montevideo.com.uy','alejandra_diaz38@hotmail.com','alejandra_ganzberg@praxair.com','alejandralgomez1980@live.com','alejandralgomez1980@live.com','alejandrosenosiain@hotmail.com','alejaroda@hotmail.com','alejo@adinet.com.uy','alejobp@hotmail.com','alejososa@gmail.com','alejpach70@hotmail.com','alelac@adinet.com.uy','alemaggiolo19@gmail.com','alepel5@adinet.com.uy','alepintos90@hotmail.com','aleponcedeleon@hotmail.com','alesabi66@hotmail.com','alevaresi@hotmail.com','alevirsm@hotmail.com','alexandralopez@adinet.com.uy') ORDER BY u.email");
			int number = 10;
            
            MD5 md5 = new MD5();
            boolean cont = true;
            
            while(cont) {
            	int i = 0;
            	Collection bcc = new LinkedList();
            	
            	//bcc.add("rodriguez.jme@gmail.com");
            	while(cont && i < number) {
            		cont = set.next();
            		if(cont) {
	    	    		String email = set.getString(1);
	    	    		if(email != null && email.length() > 1) {
		                	bcc.add(email);
		        	        i++;
	    	    		}
            		}
            	}
            	
            	if(bcc.size() > 0) {
        			String subject = "Velocidad - Campaña Auto Ayuda - Abitab / CEPA";

        			String body = getMail();
        			Collection files = new LinkedList();
        			files.add(AssesmentData.FLASH_PATH+"/images/Velocidad - Campaña Auto Ayuda 02-2011.pps");
        			String[] address = sender.getAvailableMailAddress();
        			if(address[0] != null) {
        				sender.sendImage(address[0], "CEPA Driver Assessment", address[0], address[1], subject, files, body, AssesmentData.FLASH_PATH+"/images/Oficial_logo_cepa.jpg");
        			}

        			//System.out.println("ENVIADO ");
        			Iterator it = bcc.iterator();
        			while(it.hasNext()) {
        				System.out.print("'"+it.next()+"',");	
        			}
    				System.out.println();	
	    		}
	    	}

    	}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMail() {
		String mail = "<html>" +
			"	<head>" +
			"	</head>" +
			"	<body bgcolor=\"#ffffff\" text=\"#000066\">" +
			"		<table>"+
			"			<tr>" +
			"				<td align='left'>" +
			"					<span style='font-size:11.0pt;font-family:sans-serif;color:#000000;'>"+					
			"						Estimado," +
			"					</span>"+	
			"				</td>" +
			"			</tr>"+
			"			<tr height=15><td></td></tr>" +
			"			<tr>" +
			"				<td align='left'>" +
			"					<span style='font-size:11.0pt;font-family:sans-serif;color:#000000;'>"+					
			"						De acuerdo al inter&eacute;s presentado por los participantes del Test de Conducci&oacute;n Segura CEPA - Abitab le estamos enviando un CEPA Tip de 'Velocidad en la Conducci&oacute;n'."+
			"					</span>"+	
			"				</td>" +
			"			</tr>"+
			"			<tr height=15><td></td></tr>" +
			"			<tr>" +
			"				<th align='left'>" +
			"					<span style='font-size:11.0pt;font-family:sans-serif;color:#000000;'>"+					
			"						¡Seamos conductores seguros!"+
			"					</span>"+	
			"				</th>" +
			"			</tr>"+
			"			<tr height=15><td></td></tr>" +
			"			<tr>" +
			"				<td align='left'>" +
			"					<span style='font-size:11.0pt;font-family:sans-serif;color:#000000;'>"+					
			"						Saludos," +
			"					</span>"+	
			"				</td>" +
			"			</tr>"+
			"			<tr height=15><td></td></tr>" +
			"			<tr>" +
			"				<td>" +
			"					<img src='cid:figura1' alt=''>" +
			"					<br>" +
			"				</td>" +
			"			</tr>"+
			"		</table>" +
			"	</body>" +
			"</html>";
		return mail;
	}
}
