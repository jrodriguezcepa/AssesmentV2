/**
 * Created on 24-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.report;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.util.CountryConstants;

public class AdvanceJJDataSource implements JRDataSource {

    private int index = -1;
    private UserAdvanceJJ[] users;
    private Integer questionCount;
    private CountryConstants cc;
    private Integer green;
    private Integer yellow;
    
    public AdvanceJJDataSource(Collection collection, Integer assesmentQuestionCount, Integer green, Integer yellow,Text messages) {
        users = new UserAdvanceJJ[collection.size()]; 
        questionCount = assesmentQuestionCount;
        Collections.sort((List)collection);          
        Iterator<UserAdvanceJJ> it = collection.iterator();
        int i = 0;
        while(it.hasNext()) {
            users[i] = it.next();
            i++;
        }
        cc = new CountryConstants(messages);
        this.green = green;
        this.yellow = yellow;
    }

    public boolean next() throws JRException {
        if(index+1 < users.length) {
            index++;
            return true;
        }
        return false;
    }

    public Object getFieldValue(JRField field) throws JRException {
        if(field.getName().equals("champion")) {
            switch(users[index].getCompany().intValue()) {
                case 4460:
                    return "Daniele Santos";
                case 4461:
                    switch (users[index].getCountry().intValue()) {
                        case 31: case 33: case 34: case 54:
                            return "Santiago Romero";
                        case 32:
                            return "Jackson Tota";
                        case 57: case 66: case 69:
                            return "Steven Rolon";
                        case 56: case 67: case 59: case 61: case 63:
                            return "Julio Altafulla";
                        case 55: case 37:
                            return "Jaime Cruz";
                        case 42:
                            return "Arthur Owen";
                        case 64:
                            return "Luis Cardo";
                        case 39:
                            return "Mario Lozano";
                    }
                case 4462:
                    switch (users[index].getCountry().intValue()) {
                        case 32:
                            return "Eduardo Fernandes";
                        case 66: case 57: case 85:
                            return "Francisco Martinez";
                        case 42:
                            return "Maria Benitez";
                        case 55: case 64: case 37: case 39:
                            return "Jose Yung";
                        case 31: case 54: case 33:
                            return "Alberto Castro";
                    }
                case 4463:
                    switch (users[index].getCountry().intValue()) {
                        case 31: case 64: case 54:
                            return "Guillermo Marrese";
                        case 33:
                            return "Silvia Calello";
                        case 32:
                            return "Antonio Manzano";
                        case 55:
                            return "Greicy Aguilera";
                        case 42: case 57: case 66: case 69: case 56: case 67: case 59: case 61: case 62: case 63: case 85: case 170:
                            return "Luis Cisneros";
                        case 39:
                            return "Luisa Ana Frabotta";
                    }
            }
        }
        if(field.getName().equals("company")) {
            switch(users[index].getCompany()) {
                case 4460:
                    return "Vistakon";
                case 4461:
                    return "Consumo";
                case 4462:
                    return "MD&D";
                case 4463:
                    return "Janssen-Cilag";
            }
        }
        if(field.getName().equals("country")) {
            return cc.find(String.valueOf(users[index].getCountry()));
        }
        if(field.getName().equals("user")) {
            return users[index].getFullName();
        }
        if(field.getName().equals("reference")) {
            switch(users[index].getCompany()) {
                case 4460:
                    return "VK"+String.valueOf(3000+users[index].getCode().intValue());
                case 4461:
                    return "CN"+String.valueOf(2000+users[index].getCode().intValue());
                case 4462:
                    return "MD&D"+String.valueOf(1000+users[index].getCode().intValue());
                case 4463:
                    return "Janse"+String.valueOf(4000+users[index].getCode().intValue());
            }
        }
        if(field.getName().equals("percent")) {
            double value = users[index].getCorrect().doubleValue() / questionCount.doubleValue();
            return String.valueOf(Math.round(value * 100.0));
        }
        if(field.getName().equals("psi")) {
            double value = users[index].getPsi().doubleValue() / 6.0;
            if(value <= 3) {
                return AssesmentData.FLASH_PATH+"/images/green.jpg";
            }else if(value <= 3) {
                return AssesmentData.FLASH_PATH+"/images/yellow.jpg";
            }else {
                return AssesmentData.FLASH_PATH+"/images/red.jpg";
            }
        }
        if(field.getName().equals("result")) {
            double value = users[index].getCorrect().doubleValue() / questionCount.doubleValue();
            if(value < yellow) {
                return AssesmentData.FLASH_PATH+"/images/red.jpg";
            }else if(value < green) {
                return AssesmentData.FLASH_PATH+"/images/yellow.jpg";
            }else {
                return AssesmentData.FLASH_PATH+"/images/green.jpg";
            }
        }
        return null;
    }

    public int getTotal() {
        return users.length;
    }


}
