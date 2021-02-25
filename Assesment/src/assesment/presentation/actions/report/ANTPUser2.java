package assesment.presentation.actions.report;

import java.sql.ResultSet;
import java.util.Date;

public class ANTPUser2 implements Comparable<ANTPUser2>{
	
	private String loginname; 
	private String company;
	private Date evaluation;
	private String evaluator;
	private int status;
	
	private int[][] answers;
	
	//= {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},};

	private static final int NO_INICIADO = 0;
	private static final int INICIADO = 1;
	private static final int FINALIZADO = 2;
	
	public ANTPUser2(ResultSet setUsers, int questionCount) throws Exception {
		Date end = null;
		answers = new int[7][0];
		answers[0] = new int[questionCount];
		for(int i = 0; i < questionCount; i++) 
			answers[0][i] = -1;
		answers[1] = new int[]{0,0,0,0,0};
		answers[2] = new int[]{0,0,0};
		answers[3] = new int[]{0,0,0,0,0,0,0};
		answers[4] = new int[]{0};
		answers[5] = new int[]{0};
		answers[6] = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
		loginname = setUsers.getString("loginname");
		end = setUsers.getDate("enddate");
    	if(end != null) {
        	status = FINALIZADO;
    	}else {
    		if(company != null) {
    			status = INICIADO; 
    		}else {
    			status = NO_INICIADO;
    		}
    	}
	}

	public void setAnswer(ResultSet setUsers) throws Exception {
		int id = setUsers.getInt("qId");
		switch(id) {
			case 22113: case 25581:
				company = setUsers.getString("text");
				break;
			case 22114: case 25582:
				evaluation = setUsers.getDate("date");
				break;
			case 22115: case 25583:
				evaluator = setUsers.getString("text");
				break;
		}
	}
	
	public Object[] getLine() {
		Object[] values = new Object[216];
		values[0] = loginname;
		values[1] = company;
		values[2] = evaluation;
		values[3] = evaluator;
		switch(status) {
			case NO_INICIADO:
				values[4] = "No iniciado";
				break;
			case INICIADO:
				values[4] = "Iniciado";
				break;
			case FINALIZADO:
				values[4] = "Finalizado";
				break;
		}
		int column = 5;
		for(int i = 0; i < answers[0].length;i++) {
			switch(answers[0][i]) {
				case -1:
					values[column] = "--";
					break;
				case 1:
					values[column] = "Si";
					break;
				case 2:
					values[column] = "No";
					break;
				case 3:
					values[column] = "En Proceso";
					break;
				case 4:
					values[column] = "N/A";
					break;
			}
			column++;
		}
		for(int i = 1; i < answers.length;i++) {
			for(int j = 0; j < answers[i].length;j++) {
				switch(answers[i][j]) {
					case -1:
						values[column] = null;
						break;
					case 1:
						values[column] = new Integer(1);
						break;
					case 2:
						values[column] = new Integer(3);
						break;
					case 3:
						values[column] = new Integer(4);
						break;
					case 4:
						values[column] = new Integer(10);
						break;
				}
				column++;
			}
		}
		return values;
	}

	public void addResult(int module, int question, int value) {
		answers[module-1][question-1] = value;
	}
	
	@Override
	public int compareTo(ANTPUser2 arg0) {
		if(company == null)
			return 1;
		if(arg0.company == null)
			return -1;
		return company.compareTo(arg0.company);
	}
}
