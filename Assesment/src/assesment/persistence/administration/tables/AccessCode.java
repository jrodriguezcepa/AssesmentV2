/**
 * Created on 25-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.persistence.administration.tables;

import org.hibernate.classic.Session;

import assesment.communication.administration.AccessCodeData;
import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.hibernate.HibernateAccess;

public class AccessCode {

    private String code;
    private Assesment assesment;
    private Integer number;
    private Integer used;
    private Integer extension;
    private String language;
    
    public AccessCode() {
    }

    public AccessCode(String code,Integer assesmentId,Integer number,Integer extension,String language) {
        Session session = HibernateAccess.currentSession();
        this.code = code;
        assesment = (Assesment)session.load(Assesment.class,assesmentId);
        this.number = number;
        this.used = new Integer(0);
        this.extension = extension;
        this.language = language;
    }

    public Assesment getAssesment() {
        return assesment;
    }

    public Integer getUsed() {
        return used;
    }

    public String getCode() {
        return code;
    }

    public Integer getNumber() {
        return number;
    }

    public void setAssesment(Assesment assesment) {
        this.assesment = assesment;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public AccessCodeData getAccessCodeData() {
    	if(assesment == null) {
            return new AccessCodeData(code,null,number,used,extension,null,language);
    	}
        return new AccessCodeData(code,assesment.getId(),number,used,extension,assesment.getName(),language);
    }

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    
}
