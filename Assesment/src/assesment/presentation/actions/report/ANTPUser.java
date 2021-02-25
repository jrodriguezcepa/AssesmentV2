package assesment.presentation.actions.report;

import java.sql.ResultSet;
import java.util.Date;

public class ANTPUser implements Comparable<ANTPUser>{
	
	private String loginname; 
	private String firstName;
	private String lastName;
	private String email;
	private int status;
	private Date end;
	
	private int[][] answers = {{0,0,0,0,0,0,0},{0,0,0,0,0},{0,0,0,0},{0,0},{0,0}};

	private static final int NO_INICIADO = 0;
	private static final int INICIADO = 1;
	private static final int FINALIZADO = 2;
	
	public ANTPUser(ResultSet setUsers) throws Exception {
    	loginname = setUsers.getString("loginname"); 
    	end = setUsers.getDate("enddate");
    	firstName = setUsers.getString("firstname");
    	lastName = setUsers.getString("lastname");
    	email = setUsers.getString("email");
    	if(end != null) {
        	status = FINALIZADO;
    	}else {
    		if(setUsers.getString(6) != null) {
    			status = INICIADO; 
    		}else {
    			status = NO_INICIADO;
    		}
    	}
	}
	
	public Object[] getLine() {
		Object[] values = new Object[32];
		values[0] = loginname;
		values[1] = firstName;
		values[2] = lastName;
		values[3] = email;
		switch(status) {
			case NO_INICIADO:
				values[4] = "No iniciado";
				values[5] = "---";
				break;
			case INICIADO:
				values[4] = "Iniciado";
				values[5] = "---";
				break;
			case FINALIZADO:
				values[4] = "Finalizado";
				values[5] = end;
				break;
		}
		int column = 6;
		double totalFinal = 0;
		boolean avgFinal = true;
		for(int i = 0; i < answers.length; i++) {
			boolean avg = true;
			double total = 0;
			for(int j = 0; j < answers[i].length; j++) {
				int value = answers[i][j];
				if(value == 0) {
					values[column] = "--"; 
					avg = false;
					avgFinal = false;
				}else {
					values[column] = String.valueOf(value);
					total += value;
					totalFinal += value;
				}
				column++;
			}
			if(avg) {
				values[column] = String.valueOf(Math.round(total/Double.valueOf(String.valueOf(answers[i].length))));
			}else {
				values[column] = "--";				
			}
			column++;
		}
		if(avgFinal) {
			values[31] = String.valueOf(Math.round(totalFinal/20.0));
		}else {
			values[31] = "--";				
		}
		return values;
	}

	public void addResult(int module, int question, int value) {
		answers[module-1][question-1] = value;
	}
	
	@Override
	public int compareTo(ANTPUser arg0) {
		if(firstName.equals(arg0.firstName))
			return lastName.compareTo(arg0.lastName);
		return firstName.compareTo(arg0.lastName);
	}
}
