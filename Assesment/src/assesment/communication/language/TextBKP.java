/*
 * Created on 14-oct-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.communication.language;

import java.io.Serializable;
import java.util.ListResourceBundle;

import sun.nio.cs.UnicodeEncoder;

/**
 * @author fcarriquiry
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TextBKP extends ListResourceBundle implements Serializable {

    private static final long serialVersionUID = 1L;

    private  Object[][]contents = new String[0][0];

    public TextBKP() {
        super();
    }
	
	/* (non-Javadoc)
	 * @see java.util.ListResourceBundle#getContents()
	 */
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return contents;
	}

	public void setContents(Object[][] contents) {
	    this.contents = contents;
	    super.setParent(null);
	}
	
	public String getText(String key) {
        if(key == null || key.length() == 0) {
            return "";
        }
	    String label = null;
	    try {
	        label = super.getString(key);
            if(label.length() == 0) {
                System.out.println(key);
            }
	    }catch(Exception e) {
            System.out.println("-------------------------------------------------");
            System.out.println(key+",");
            System.out.println("-------------------------------------------------");
	        return key;
	    }
	    return (label==null) ? key : label;
	}
	
    /**
     * @param string
     * @return
     */
    public static String getMessage(String string)throws Exception {
    
    	String language = System.getProperty("user.language");
        if(string.equals("generic.error.permissions")) {
            if(language.toLowerCase().equals("es")) {
                return "Sus permisos en esta corporaciÃ³n no le permiten acceder a esta pÃ¡gina";
            } else if(language.toLowerCase().equals("pt")) {
                return "Sus permisos en esta corporaciÃ³n no le permiten acceder a esta pÃ¡gina";
            } else {
                return "You are not allowed to access this page";
            }
        }
        if(string.equals("generic.error.login")) {
            if(language.toLowerCase().equals("es")) {
                return "Se produjo un error en el proceso de login, el usuario o contraseÃ±a no son correctos.";
            } else if(language.toLowerCase().equals("pt")) {
                return "Se ocorrer um erro no processo de login, o usuÃ¡rio ou senha nÃ£o estÃ£o corretos.";
            } else {
                return "An error has been detected on the log in process, username or password are not correct.";
            }
        }
        if(string.equals("generic.error.login.retry")) {
            if(language.toLowerCase().equals("es")) {
                return "Si desear reintentar presione ";
            } else if(language.toLowerCase().equals("pt")) {
                return "Se desejar corrigir pressione ";
            } else {
                return "If you want to retry press ";
            }
        }
        if(string.equals("generic.messages.here")) {
            if(language.toLowerCase().equals("es")) {
                return "aquÃ­.";
            } else if(language.toLowerCase().equals("pt")) {
                return "aqui.";
            } else {
                return "here.";
            }
        }
        if(string.equals("generic.messages.session.expired")) {
            if(language.toLowerCase().equals("es")) {
                return "La sesiÃ³n ha expirado";
            } else if(language.toLowerCase().equals("pt")) {
                return "The sesion ha expirado";
            } else{
                return "The session has expired";
            }
        }
        if(string.equals("generic.messages.accept")) {
            if(language.toLowerCase().equals("es")) {
                return "Aceptar";
            } else if(language.toLowerCase().equals("pt")) {
                return "Aceptar";
            } else{
                return "Accept";
            }
        }
        if(string.equals("generic user.userdata.passconfirm")) {
            if(language.toLowerCase().equals("en")) {
                return "Invalid Password";
            } else if(language.toLowerCase().equals("es")) {
                return "ContraseÃ±a invalida";
            } else if(language.toLowerCase().equals("pt")) {
                return "Senha invÃ¡lida";
            }
        }  
        if(string.equals("user.error.shortpassword")) {
            if(language.toLowerCase().equals("en")) {
                return "Password Too Short";
            } else if(language.toLowerCase().equals("es")) {
                return "Clave Demasiado Breve";
            } else if(language.toLowerCase().equals("pt")) {
                return "Senha Muito Curta";
            }
        }     
        if(string.equals("user.error.invalidpassword")) {
            if(language.toLowerCase().equals("en")) {
                return "Password must contain upper and lower case letters, and numbers or at least one of the following: @#$%^&+=.";
            } else if(language.toLowerCase().equals("es")) {
                return "La clave debe componerse de letras mayÃºsculas, minÃºsculas, y nÃºmeros o alguno de estos sÃ­mbolos: @#$%^&+=.";
            } else if(language.toLowerCase().equals("pt")) {
                return "A senha deve conter letras maiÃºsculas e minÃºsculas, e nÃºmeros o ao menos um destes sÃ­mbolos: @#$%^&+=.";
            }
        }      

        return "Error";
    }

    public static String getArabic(String text) {
    	//return "Ø§Ø®ØªØ¨Ø§Ø± Ø§Ù„Ù…Ø¹Ø±Ù�Ø© (A)";
    	return "jj";
    }
}
