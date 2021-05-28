/**
 * CEPA
 * Assesment
 */
package assesment.presentation.translator.web.util;

import java.security.MessageDigest;
import java.sql.Time;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentListData;
import assesment.communication.language.Text;
import assesment.communication.question.VideoData;
import assesment.communication.util.MD5;

/**
 * @author jrodriguez
 */
public class Util {

    public static final double KMS_TO_MILES = 1.609344;
    private static final String PATH="/tmp/assesment/images/";
    /**
     * 
     */
    public Util() {
        super();
    }
    
    public static boolean empty(String label) {
        return label == null || label.trim().length() == 0;
    }

    public static boolean isRegistrable() {
    	Calendar c = Calendar.getInstance();
    	int day = c.get(Calendar.DATE);
    	/*if(day != 31)
    		return false;
    	int hour = c.get(Calendar.HOUR_OF_DAY);
    	int minute = c.get(Calendar.MINUTE);
    	switch(hour) {
			case 8:
				return minute >= 45;
			case 9:
				return minute <= 10;
			case 13:
				return minute >= 45;
			case 14:
				return minute <= 10;
    	}*/
    	return true;
    }
    
    public static boolean isRegistrableMDP() {
    	Calendar c = Calendar.getInstance();
    	int day = c.get(Calendar.DATE);
    	/*if(day == 23 || day >= 26) {
	    	int hour = c.get(Calendar.HOUR_OF_DAY);
	    	int minute = c.get(Calendar.MINUTE);
	    	switch(hour) {
				case 10: case 15:
					return minute >= 15 && minute <= 40;
				case 8: case 13:
					return minute <= 10;
				case 7: case 12:
					return minute >= 45;
	    	}
    	}*/
    	return true;
    }

    public static boolean isRegistrableLumin() {
    	Calendar c = Calendar.getInstance();
    	int day = c.get(Calendar.DATE);
    	if(day == 19) {
	    	int hour = c.get(Calendar.HOUR_OF_DAY);
	    	int minute = c.get(Calendar.MINUTE);
	    	switch(hour) {
				case 8: case 11: case 16:
					return minute <= 10;
				case 7: case 10: case 15:
					return minute >= 45;
	    	}
    	}
    	return true;
    }

    public static String getInValue(HttpServletRequest request,HttpSession session,String name) {
		String value = request.getParameter(name);
		if(Util.empty(name)) {
			value = String.valueOf(session.getAttribute(name));
			session.removeAttribute(name);
		}
		return value;
	}
	
	public static String checkPermission(AssesmentAccess systemAccess,String label) {
    	if(systemAccess==null){
			return "/home.jsp";
		}
		if(!systemAccess.getUserSessionData().checkPermission(label)){
			return "/error.jsp";
		}
    	return null;
    }
    
    
    public static String formatDate(Date date) {
        if(date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.YEAR);
    }

    public static String formatDateHour(Date date) {
        if(date == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
    }

    public static String formatDateElearning(Date date) {
        if(date == null) {
            return "1970-01-01";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String value = calendar.get(Calendar.YEAR)+"-";
        if(calendar.get(Calendar.MONTH)+1 < 10) {
        	value += "0"+String.valueOf(calendar.get(Calendar.MONTH)+1);
        }else {
        	value += String.valueOf(calendar.get(Calendar.MONTH)+1);
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10) {
        	value += "-0"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }else {
        	value += "-"+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }
        return value;
    }

    public static String formatHour(Time time) {
        if(time == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        String minute = (calendar.get(Calendar.MINUTE) < 10) ? "0"+String.valueOf(calendar.get(Calendar.MINUTE)) : String.valueOf(calendar.get(Calendar.MINUTE)); 
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":"+minute;
    }

    public static Date getDate(String date, String month, String year) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(date));
            calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
            calendar.set(Calendar.YEAR,Integer.parseInt(year));
            return calendar.getTime();
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Time getTime(String hour, String minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE,Integer.parseInt(minute));
        return new Time(calendar.getTimeInMillis());
    }
    
    public static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
        }catch(Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isLongNumber(String string) {
        try {
            Long.parseLong(string);
        }catch(Exception e) {
            return false;
        }
        return true;
    }

    public static Collection stringToCollection(String stringList){
        String[] stringArray=stringList.split("<");
        Collection col=new LinkedList();
        
        for(int i=0; i < stringArray.length; i++){
            col.add(stringArray[i]);
        }
        
        return col;
    }

    public static Hashtable stringToHash(String stringList){
        String[] stringArray=stringList.split("<");
        Hashtable col=new Hashtable();
        
        for(int i=0; i < stringArray.length; i++){
            col.put(stringArray[i],stringArray[i]);
        }
        
        return col;
    }
    public static boolean checkEmail(String email ) {
    	if(empty(email))
    		return false;
    	email.trim();
       	StringTokenizer mail1 = new StringTokenizer(email);	
       	mail1.nextElement();
       	if(mail1.hasMoreElements()==true){
   		 return false;
       	}
       	Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
     	Matcher m = p.matcher(email);
     	if (!m.find()){
     		return false;
     	}
     	else{
     		return true;
     	}
    }
    public static boolean checkDate(int day,int month,int year) {
        if(day <= 0 || month <= 0 || year <= 0) {
            return false;
        }
        String validateYear=""+year;
        if(validateYear.length()!=4){
        	return false;
        }
          switch(month) {
            case 2:
                int max = (year % 4 == 0) ? 29 : 28; 
                if(day > max) {
                    return false;
                }
                break;
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                if(day > 31) {
                    return false;
                }    
                break;
            case 4: case 6: case 9: case 11:
                if(day > 30) {
                    return false;
                }    
                break;
            default: return false;    
        }
        return true;
    }

    public static boolean checkHour(int hour, int minute) {
        if(hour > 23 || hour < 0) {
            return false;
        }
        if(minute > 59 || minute < 0) {
            return false;
        }
        return true;
    }
    
    public static boolean isChecked(String label) {
        return !Util.empty(label) && label.toLowerCase().equals("on");
    }

    public static String formatDouble(Double value,int decimals) {
        String result = String.valueOf(value);
        int point = result.indexOf('.');
        if(point > 0) {
            if(point+1+decimals < result.length()) {                
                int lastValue = Integer.parseInt(String.valueOf(result.charAt(point+decimals)));
                if(Integer.parseInt(String.valueOf(result.charAt(point+1+decimals))) >= 5) {
                    lastValue++;
                }
                result = result.substring(0,point+decimals)+String.valueOf(lastValue);
            }
        }        
        return result;
    }
    
    public static boolean dateRange(String yearFrom,String yearTo,String monthFrom,String monthTo,int rangeYear){
	  try{
		  int yearRange = Integer.parseInt(yearTo.trim())-Integer.parseInt(yearFrom.trim());
		  int monthRange = Integer.parseInt(monthTo.trim())-Integer.parseInt(monthFrom.trim());
		  if (rangeYear<0||yearRange>rangeYear||yearRange<0||((yearRange==rangeYear)&&(monthRange>0)))
			  return false;
	  }catch(Exception e){
		  return false;
	  } 
	  return true;
  }  
  

    public static Calendar getDate(String word) {
        StringTokenizer tokDate = new StringTokenizer(word,"/");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(tokDate.nextToken()));
        c.set(Calendar.MONTH,Integer.parseInt(tokDate.nextToken())-1);
        c.set(Calendar.YEAR,Integer.parseInt(tokDate.nextToken()));
        return c;
    }

    public static String emailTranslation(String word) {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); 
            switch(c) {
	            case 'á':
	                result.append("&aacute;");
	                break;
	           case 'â':
	               result.append("&acirc;");
	               break;
	           case 'ã':
	               result.append("&atilde;");
	               break;
	           case 'ä':
	               result.append("&auml;");
	               break;
	           case 'é':
	                result.append("&eacute;");
	                break;
	           case 'ê':
	                // Todo
	                result.append("&ecirc;");
	                break;
	           case 'ë':
	               result.append("&euml;");
	               break;
	           case 'í':
	                result.append("&iacute;");
	                break;
	           case 'î':
	               result.append("&icirc;");
	               break;
	           case 'ï':
	               result.append("&iuml;");
	               break;
	           case 'ó':
	                result.append("&oacute;");
	                break;
	           case 'ô':
	               result.append("&ocirc;");
	               break;
	           case 'ö':
	               result.append("&ouml;");
	               break;
	           case 'õ':
	                result.append("&otilde;");
	                break;
	           case 'ú':
	                result.append("&uacute;");
	                break;
	           case 'û':
	               result.append("&ucirc;");
	               break;
	           case 'ü':
	               result.append("&uuml;");
	               break;
	            case 'ñ':
	                result.append("&ntilde;");
	                break;
	            case 'ç':
	                result.append("&ccedil;");
	                break;
	            case '\n':
	                result.append("<br>");
	                break;
	            default:
	                result.append(c);
            }
        }
        return result.toString();
    }

    public static String formatHTML(String word) {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); 
            switch(c) {
            case 'á':
                result.append("&aacute;");
                break;
           case 'â':
               result.append("&acirc;");
               break;
           case 'ã':
               result.append("&atilde;");
               break;
           case 'ä':
               result.append("&auml;");
               break;
           case 'é':
                result.append("&eacute;");
                break;
           case 'ê':
                // Todo
                result.append("&ecirc;");
                break;
           case 'ë':
               result.append("&euml;");
               break;
           case 'í':
                result.append("&iacute;");
                break;
           case 'î':
               result.append("&icirc;");
               break;
           case 'ï':
               result.append("&iuml;");
               break;
           case 'ó':
                result.append("&oacute;");
                break;
           case 'ô':
               result.append("&ocirc;");
               break;
           case 'ö':
               result.append("&ouml;");
               break;
           case 'õ':
                result.append("&otilde;");
                break;
           case 'ú':
                result.append("&uacute;");
                break;
           case 'û':
               result.append("&ucirc;");
               break;
           case 'ü':
               result.append("&uuml;");
               break;
            case 'ñ':
                result.append("&ntilde;");
                break;
            case 'ç':
                result.append("&ccedil;");
                break;
            case '\r':
                break;
            case '\n':
                result.append("<br>");
                break;
            case '\t':
                result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                break;
            default:
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String dateToString(Text messages, Calendar date, String language) {
        StringBuffer buffer = new StringBuffer();
        if(language.equals("en")) {
            buffer.append(messages.getText("generic.month."+String.valueOf(date.get(Calendar.MONTH)+1))).append(" ").append(date.get(Calendar.DATE)).append(", ").append(" ").append(date.get(Calendar.YEAR));
        }else {
            buffer.append(date.get(Calendar.DATE)).append(" ").append(messages.getText("generic.messages.of")).append(" ").append(messages.getText("generic.month."+String.valueOf(date.get(Calendar.MONTH)+1))).append(" ").append(messages.getText("generic.messages.of")).append(" ").append(date.get(Calendar.YEAR));
        }
        return buffer.toString();
    }

    public static String[][] validateMonsantoSurver(HashMap hash) {
    	String division = (String)hash.get(new Integer(17586));
    	if(division == null || division.trim().length() == 0) {
    		String[][] s = {{"17586", "Debe ingresar Diretoria"}};
    		return s;
    	}
    	String subdivision = (String)hash.get(new Integer(17588));
    	if(subdivision == null || subdivision.trim().length() == 0) {
    		String[][] s = {{"17588", "Debe ingresar Área"}}; 
    		return s;
    	}
    	String unit  = (String)hash.get(new Integer(17589));
    	if(unit == null || unit.trim().length() == 0) {
    		String[][] s = {{"17589", "Debe ingresar Unidade"}}; 
    		return s;
    	}
    	String text  = (String)hash.get(new Integer(17591));
    	if((division.equals("Outro") || subdivision.equals("Outro") || unit.equals("Outro")) && (text == null || text.length() < 2)) {
    		String[][] s = {{"17591", "Por favor, especifique sua Diretoria/Área/Unidade"}}; 
    		return s;
    	}
    	return null;
    }

    public static String getVideo(String videoId, Collection<VideoData> videos) {
    	try {
    		Iterator<VideoData> it = videos.iterator();
    		while(it.hasNext()) {
    			VideoData video = it.next();
    			if(video.getId().intValue() == Integer.parseInt(videoId)) {
    				return video.getKey();
    			}
    		}
    	} catch (Exception e) {
    	}
    	return videoId;
    }
    
    public static Iterator<AssesmentListData> getAssessmentIterator(Collection c, Text messages) {
    	Collection<AssesmentListData> list = new LinkedList<AssesmentListData>();
    	Iterator it = c.iterator();
    	while(it.hasNext()) {
    		list.add(new AssesmentListData((Object[])it.next(), messages));
    	}
    	Collections.sort((List)list);
    	return list.iterator();
    }
    
    public static Iterator<AssesmentListData> getAssessmentAttrIterator(Collection c, Text messages) {
    	Collection<AssesmentListData> list = new LinkedList<AssesmentListData>();
    	Iterator it = c.iterator();
    	while(it.hasNext()) {
    		list.add(new AssesmentListData((AssesmentAttributes)it.next(), messages));
    	}
    	Collections.sort((List)list);
    	return list.iterator();
    }

    public static int validateAcheHash(String email, String hash, AssesmentAccess sys) {
    	try {
	    	MD5 md5 = new MD5();
        	String key = "JR67lm87";

        	String Hash1 = key + email;

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(Hash1.getBytes("UTF8"));
        	
        	Base64 codec = new Base64();
        	String v = codec.encode(b);
        	
	    	if(v.equals(hash)) {
	    		if(sys.getUserReportFacade().userExist(email, sys.getUserSessionData())) {
	    			return 1;
	    		}else {
	    			return 2;
	    		}
	    	}else {
	    		return 0;
	    	}
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    	return 0;
    }
    

    public static String passwordAche(String email) {
    	try {
	    	MD5 md5 = new MD5();
        	String key = "JR67lm87";

        	String Hash1 = key + email;

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b = md.digest(Hash1.getBytes("UTF8"));
        	
        	Base64 codec = new Base64();
        	return md5.encriptar(codec.encode(b));
        	
    	}catch(Exception e) {
    		e.printStackTrace(); 
    	}
    	return "";
    }
    
    public static boolean isValidUser(String user, String password) {
    	boolean result = true;
    	try {
    		 result = user.matches("[a-z@._\\-A-Z0-9]*");
    		 if(password != null)
    		 	result = password.matches("[a-z@._\\-A-Z0-9]*");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    public static boolean isValidAccessCode(String ac) {
    	try {
    		return ac.matches("[a-z._\\-A-Z0-9]*");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return true;
    }


	public static String getTimacCPF(String code) {
		String v = new String(code);
		while(v.length() < 11)
			v = "0"+v;
		return v;
	}
}
