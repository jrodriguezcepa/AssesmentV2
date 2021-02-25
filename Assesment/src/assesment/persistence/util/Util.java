/**
 * CEPA
 * Assesment
 */
package assesment.persistence.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;

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
            case 'é':
                result.append("&eacute;");
                break;
            case 'í':
                result.append("&iacute;");
                break;
            case 'ó':
                result.append("&oacute;");
                break;
            case 'ú':
                result.append("&uacute;");
                break;
            case 'ñ':
                result.append("&ntilde;");
                break;
            case 'ç':
                result.append("&ccedil;");
                break;
            case 'õ':
                result.append("&otilde;");
                break;
            case 'ã':
                result.append("&atilde;");
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
                case '\r':
                    break;
                case '\n':
                    result.append("<br>");
                    break;
                case '\t':
                    result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                    break;
                case ' ':
                    result.append("  ");
                    break;
                default:
                    result.append(c);
            }
        }
        return result.toString();
    }

    public static String formatFile(String word) {
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i); 
            switch(c) {
                case '\r':
                case '\n':
                    break;
                case '\t':
                    break;
                case ' ':
                    result.append("_");
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
    
    
    public static String formatSQLFull(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if(type == 1) {
            calendar.add(Calendar.DATE,1);
        }
        
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        return "'"+format.format(calendar.getTime())+"' ";
    
    }
}