package assesment.persistence.util;

import java.io.Serializable;

import assesment.communication.exception.I18NException;

public class LoggerFormatting implements Serializable {
	
	public String formattingText(String message,String userID){
		return new String (userID==null ? message:userID+" :"+message);
	}
	
	public String formattingText(I18NException exception,String method,String exceptionName,String userID){
		
		return  formattingText(method+","+exceptionName+","+exception.getLabelKey(),userID);
	}
	
public String formattingText(Exception exception,String method,String exceptionName,String userID){
		
	
		return  formattingText(method+","+exceptionName+","+exception.getMessage()+","+exception.getCause(),userID);
	}
}
