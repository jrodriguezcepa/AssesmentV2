package assesment.presentation.actions.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResourcesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResourcesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static boolean isProductionServer(){
    	return new File("/home").exists();
    }
    
    public String getPathFlash(){
    	if(isProductionServer()){
    		return "/home/flash/";
    	}else{
    		return "C:/flash/";
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			
			String url = request.getRequestURL().toString();
            if(!url.equals("https://www.cepada.com/assesment/flash/images/")) {
			
				OutputStream oos = response.getOutputStream();
	
	            byte[] buf = new byte[8192];
	
	
	            InputStream is = new FileInputStream(getPathFlash()+url.split("/flash/")[1]);
	
	            int c = 0;
	
	            while ((c = is.read(buf, 0, buf.length)) > 0) {   
	                oos.write(buf, 0, c);
	                oos.flush();
	            }
	
	            oos.close();
	            is.close();
            }		 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

 
	 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
