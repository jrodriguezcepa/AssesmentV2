package assesment.persistence.schedulertask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionTask {

	private Connection connection = null;
	private Statement st = null;
	
	public ConnectionTask(){
		
	}
	
	public void conect() {
		try{
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/assesment","postgres","pr0v1s0r1A");
			st = connection.createStatement();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void disconnect(){
		try{
			if (st != null){
				st.close();
			}
			
			if (connection != null){
				connection.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int executeUpdate(String sql){
		try{
			return st.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public ResultSet executeQuery(String queryStr){
		try{
			return st.executeQuery(queryStr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
