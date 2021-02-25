package assesment.communication.assesment;

import java.sql.SQLException;

public class ResultLineJJ extends ResultLine {

	private String countryName;
	private String op_group;
	private String supervisor;
	
	public ResultLineJJ(Object[] data) throws SQLException {
		super(data);
		switch(extraData) {
        case 4460:
    		op_group = "Vistakon";
            supervisor = "Daniele Santos";
            switch (country) {
            	case 31:
            		countryName = "Uruguay";
            		break;
            	case 33:
            		countryName = "Argentina";
            		break;
            	case 34:
            		countryName = "Paraguay";
            		break;
            	case 54:
            		countryName = "Chile";
            		break;
                case 32:
            		countryName = "Brasil";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
            		break;
                case 66:
                	countryName = "Puerto Rico";
            		break;
                case 69:
                	countryName = "Trinidad & Tobago";
            		break;
                case 56:
                	countryName = "Costa Rica";
            		break;
                case 67:
                	countryName = "El Salvador";
            		break;
                case 59:
                	countryName = "Guatemala";
            		break;
                case 61:
                	countryName = "Honduras";
            		break;
                case 63:
                	countryName = "Panamá";
            		break;
                case 37:
                	countryName = "Ecuador";
            		break;
                case 55:
            		countryName = "Colombia";
            		break;
                case 42:
            		countryName = "México";
            		break;
                case 64:
            		countryName = "Perú";
            		break;
                case 39:
            		countryName = "Venezuela";
            		break;
                case 85:
                	countryName = "Jamaica";
                	break;
                case 62:
                	countryName = "Nicaragua";
                	break;
            }
            break;
        case 4461:
    		op_group = "Consumo";
            switch (country) {
            	case 31:
            		countryName = "Uruguay";
            		supervisor = "Santiago Romero";
            		break;
            	case 33:
            		countryName = "Argentina";
            		supervisor = "Santiago Romero";
            		break;
            	case 34:
            		supervisor = "Santiago Romero";
            		countryName = "Paraguay";
            		break;
            	case 54:
            		countryName = "Chile";
            		supervisor = "Santiago Romero";
            		break;
                case 32:
            		countryName = "Brasil";
                	supervisor = "Jackson Tota";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
                	supervisor = "Steven Rolon";
            		break;
                case 66:
                	countryName = "Puerto Rico";
                	supervisor = "Steven Rolon";
            		break;
                case 69:
                	countryName = "Trinidad & Tobago";
                	supervisor = "Steven Rolon";
            		break;
                case 56:
                	countryName = "Costa Rica";
                	supervisor = "Julio Altafulla";
            		break;
                case 67:
                	countryName = "El Salvador";
                	supervisor = "Julio Altafulla";
            		break;
                case 59:
                	countryName = "Guatemala";
                	supervisor = "Julio Altafulla";
            		break;
                case 61:
                	countryName = "Honduras";
                	supervisor = "Julio Altafulla";
            		break;
                case 63:
                	countryName = "Panamá";
                	supervisor = "Julio Altafulla";
            		break;
                case 37:
                	countryName = "Ecuador";
                	supervisor = "Jaime Cruz";
            		break;
                case 55:
            		countryName = "Colombia";
                	supervisor = "Jaime Cruz";
            		break;
                case 42:
            		countryName = "México";
                	supervisor = "Arthur Owen";
            		break;
                case 64:
            		countryName = "Perú";
                	supervisor = "Luis Cardo";
            		break;
                case 39:
            		countryName = "Venezuela";
                	supervisor = "Mario Lozano";
            		break;
            }
    		break;
        case 4462:
    		op_group = "MD&D";
            switch (country) {
                case 32:
            		countryName = "Brasil";
                	supervisor = "Eduardo Fernandes";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
                	supervisor = "Francisco Martinez";
            		break;
                case 66:
                	countryName = "Puerto Rico";
                	supervisor = "Francisco Martinez";
            		break;
                case 85:
                	countryName = "Jamaica";
                	supervisor = "Francisco Martinez";
            		break;
                case 42:
            		countryName = "México";
                	supervisor = "Maria Benitez";
            		break;
                case 37:
                	countryName = "Ecuador";
                	supervisor = "Jose Yung";
            		break;
                case 39:
            		countryName = "Venezuela";
                	supervisor = "Jose Yung";
            		break;
                case 55:
            		countryName = "Colombia";
                	supervisor = "Jose Yung";
            		break;
                case 64:
            		countryName = "Perú";
                	supervisor = "Jose Yung";
            		break;
                case 31:
            		countryName = "Uruguay";
                	supervisor = "Alberto Castro";
            		break;
                case 33:
            		countryName = "Argentina";
                	supervisor = "Alberto Castro";
            		break;
                case 54:
            		countryName = "Chile";
                	supervisor = "Alberto Castro";
            		break;
                case 63:
                	countryName = "Panamá";
                	supervisor = "---";
            		break;
                case 59:
                	countryName = "Guatemala";
                	supervisor = "---";
            		break;
            }
    		break;
        case 4463:
    		op_group = "Janssen-Cilag";
            switch (country) {
            	case 31:
            		countryName = "Uruguay";
            		supervisor = "Guillermo Marrese";
            		break;
            	case 54:
            		countryName = "Chile";
            		supervisor = "Guillermo Marrese";
            		break;
            	case 64:
            		countryName = "Perú";
            		supervisor = "Guillermo Marrese";
            		break;
                case 33:
            		countryName = "Argentina";
                	supervisor = "Silvia Calello";
            		break;
                case 32:
            		countryName = "Brasil";
                	supervisor = "Antonio Manzano";
            		break;
                case 55:
            		countryName = "Colombia";
                	supervisor = "Greicy Aguilera";
            		break;
                case 39:
            		countryName = "Venezuela";
                	supervisor = "Luisa Ana Frabotta";
            		break;
                case 42:
            		countryName = "México";
                	supervisor = "Luis Cisneros";
            		break;
                case 57:
                	countryName = "Rep. Dominicana";
                	supervisor = "Luis Cisneros";
            		break;
                case 66:
                	countryName = "Puerto Rico";
                	supervisor = "Luis Cisneros";
            		break;
                case 69:
                	countryName = "Trinidad & Tobago";
                	supervisor = "Luis Cisneros";
            		break;
                case 56:
                	countryName = "Costa Rica";
                	supervisor = "Luis Cisneros";
            		break;
                case 67:
                	countryName = "El Salvador";
                	supervisor = "Luis Cisneros";
            		break;
                case 59:
                	countryName = "Guatemala";
                	supervisor = "Luis Cisneros";
            		break;
                case 61:
                	countryName = "Honduras";
                	supervisor = "Luis Cisneros";
            		break;
                case 62:
                	countryName = "Nicaragua";
                	supervisor = "Luis Cisneros";
            		break;
                case 63:
                	countryName = "Panamá";
                	supervisor = "Luis Cisneros";
            		break;
                case 85:
                	countryName = "Jamaica";
                	supervisor = "Luis Cisneros";
            		break;
                case 170:
                	countryName = "Belice";
                	supervisor = "Luis Cisneros";
            		break;
            }
            break;
		}
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getOp_group() {
		return op_group;
	}

	public void setOp_group(String op_group) {
		this.op_group = op_group;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}
	
	public int compareTo(Object o) {
		try {
			ResultLineJJ l = (ResultLineJJ)o;
			if(supervisor != null && l.supervisor != null && !l.supervisor.equals(supervisor)) {
				return supervisor.compareTo(l.supervisor);
			}
			if(countryName != null && l.countryName != null && !l.countryName.equals(countryName)) {
				return countryName.compareTo(l.countryName);
			}
			if(op_group != null && l.op_group != null && !l.op_group.equals(op_group)) {
				return op_group.compareTo(l.op_group);
			}
			return super.compareTo(l);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}
