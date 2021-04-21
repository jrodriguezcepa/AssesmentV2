package assesment.communication.report;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.language.Text;

public class TotalPointReportDataSource implements JRDataSource {

    private Integer correctas;
    private Integer incorrectas;
    private Float points;
    private Integer porcentaje_correctas;
    private Integer porcentaje_incorrectas;
    private int index = 0;
	private Text messages;
    
    public TotalPointReportDataSource(Integer correctas, Integer incorrectas, Float points, Text messages) {
        this.correctas = correctas;
        this.incorrectas = incorrectas;
        this.points = points;
        this.messages = messages;
        if(correctas == 0 && incorrectas == 0) {
            porcentaje_correctas = 0;
            porcentaje_incorrectas = 0;
        }else {
        	porcentaje_correctas = Math.round(points * 100 / (correctas + incorrectas));
        	porcentaje_incorrectas = 100 - porcentaje_correctas;
        }
    }

    public boolean next() throws JRException {
        if(index < 2) {
        	index++;
        	return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
    	if(field.getName().equals("cantidad")) {
    		return (index == 1) ? correctas : incorrectas;
    	}
    	if(field.getName().equals("porcentaje")) {
    		return (index == 1) ? porcentaje_correctas : porcentaje_incorrectas;
    	}
    	if(field.getName().equals("puntos")) {
    		return points;
    	}
    	if(field.getName().equals("texto_respuesta")) {
    		return (index == 1) ? messages.getText("report.userresult.right") : messages.getText("report.userresult.wrong");
    	}
    	return null;
    }

	public Integer getPorcentaje_correctas() {
		return porcentaje_correctas;
	}

	public void setPorcentaje_correctas(Integer porcentaje_correctas) {
		this.porcentaje_correctas = porcentaje_correctas;
	}

	public Integer getPorcentaje_incorrectas() {
		return porcentaje_incorrectas;
	}

	public void setPorcentaje_incorrectas(Integer porcentaje_incorrectas) {
		this.porcentaje_incorrectas = porcentaje_incorrectas;
	}


}
