/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.hibernate.Session;

import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.module.ModuleData;
import assesment.persistence.corporation.tables.Corporation;
import assesment.persistence.hibernate.HibernateAccess;
import assesment.persistence.module.tables.Module;
import assesment.persistence.module.tables.ModuleBKP;

public class Assesment {

    private Integer id;
    
    private Corporation corporation; 
    
    private String name;
    private String description;
    
    private Date start;
    private Date end;
    
    private Integer status;
    private boolean psitest;
    
    private boolean elearning;

    private boolean instantFeedback;
    private boolean reportFeedback;
    private boolean errorFeedback;
    private boolean psiFeedback;

    private Integer green;
    private Integer yellow;
    
    private boolean certificate;
    private String certificateImageES;
    private String certificateImageEN;
    private String certificateImagePT;
    
    private Set<Module> moduleSet;
    
    private boolean archived;
    private boolean showEmailWRT;
    private String dcActivity;
    
    private Integer terms;
    private String link_es;
    private String link_en;
    private String link_pt;
    
    private boolean icon;
    private boolean attachesPDF;
    private boolean attachenPDF;
    private boolean attachptPDF;
    
    private boolean untilApproved;
    private String link;

    public Assesment() {
    }

    public Assesment(AssesmentAttributes attributes) {
        setData(attributes);
    }

    public void setData(AssesmentAttributes attributes) {
        this.id = attributes.getId();
        Session session = HibernateAccess.currentSession();
        this.corporation = (Corporation)session.load(Corporation.class,attributes.getCorporationId()); 
        this.name = attributes.getName();
        this.description = attributes.getDescription();
        this.status = attributes.getStatus();
        this.start = attributes.getStart();
        this.end = attributes.getEnd();
        this.psitest = attributes.isPsitest();
        this.elearning = attributes.isElearning();
        this.instantFeedback = attributes.isInstantFeedback();
        this.reportFeedback = attributes.isReportFeedback();
        this.errorFeedback = attributes.isErrorFeedback();
        this.psiFeedback = attributes.isPsiFeedback();
        this.green = attributes.getGreen();
        this.yellow = attributes.getYellow();
        this.certificate = attributes.isCertificate();
        this.certificateImageES = attributes.getCertificateImageES();
        this.certificateImageEN = attributes.getCertificateImageEN();
        this.certificateImagePT = attributes.getCertificateImagePT();
        this.archived = attributes.isArchived();
        this.showEmailWRT = attributes.isShowEmailWRT();
        this.terms = attributes.getTerms();
        this.icon = attributes.isIcon();
        if(attributes.isAttachesPDF())
        	this.attachesPDF = true;
        if(attributes.isAttachenPDF())
        	this.attachenPDF = true;
        if(attributes.isAttachptPDF())
        	this.attachptPDF = true;
        this.untilApproved = attributes.isUntilApproved();
        this.link = attributes.getLink();
    }

    public Integer getId() {
        return id;
    }

    public Corporation getCorporation() {
        return corporation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Integer getStatus() {
        return status;
    }

    public Set<Module> getModuleSet() {
        return moduleSet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCorporation(Corporation corporation) {
        this.corporation = corporation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setModuleSet(Set<Module> moduleSet) {
        this.moduleSet = moduleSet;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public boolean isPsitest() {
        return psitest;
    }

    public void setPsitest(boolean psitest) {
        this.psitest = psitest;
    }

    public boolean isInstantFeedback() {
        return instantFeedback;
    }

    public boolean isPsiFeedback() {
        return psiFeedback;
    }

    public boolean isReportFeedback() {
        return reportFeedback;
    }

    public void setInstantFeedback(boolean instantFeedback) {
        this.instantFeedback = instantFeedback;
    }

    public void setPsiFeedback(boolean psiFeedback) {
        this.psiFeedback = psiFeedback;
    }

    public void setReportFeedback(boolean reportFeedback) {
        this.reportFeedback = reportFeedback;
    }

    public AssesmentData getData() {
        Collection<ModuleData> modules = new LinkedList<ModuleData>();
        Iterator<Module> it = moduleSet.iterator();
        while(it.hasNext()) {
            modules.add(it.next().getData());
        }
        AssesmentData assessmentData = new AssesmentData(id,name,description,corporation.getId(),start,end,modules,status,psitest,elearning,instantFeedback,reportFeedback,errorFeedback,psiFeedback,green,yellow,certificate,certificateImageES,certificateImageEN,certificateImagePT,archived, showEmailWRT, terms, link_es, link_en, link_pt, icon, attachesPDF, attachenPDF, attachptPDF, untilApproved, link);
        assessmentData.setCorporationName(corporation.getName());
        return assessmentData;
    }

    public AssesmentAttributes getAssesment() {
        return new AssesmentAttributes(id,name,description,corporation.getId(),start,end,status,psitest,elearning,instantFeedback,reportFeedback,errorFeedback,psiFeedback,green,yellow,certificate,certificateImageES,certificateImageEN,certificateImagePT,archived, showEmailWRT, terms, link_es, link_en, link_pt, icon, attachesPDF, attachenPDF, attachptPDF, untilApproved, link);
    }

    public Integer getGreen() {
        return green;
    }

    public Integer getYellow() {
        return yellow;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public void setYellow(Integer yellow) {
        this.yellow = yellow;
    }

    public boolean isErrorFeedback() {
        return errorFeedback;
    }

    public void setErrorFeedback(boolean errorFeedback) {
        this.errorFeedback = errorFeedback;
    }

    public boolean isElearning() {
        return elearning;
    }

    public void setElearning(boolean elearning) {
        this.elearning = elearning;
    }

	public boolean isCertificate() {
		return certificate;
	}

	public void setCertificate(boolean certificate) {
		this.certificate = certificate;
	}

	public String getCertificateImageES() {
		return certificateImageES;
	}

	public void setCertificateImageES(String certificateImageES) {
		this.certificateImageES = certificateImageES;
	}

	public String getCertificateImageEN() {
		return certificateImageEN;
	}

	public void setCertificateImageEN(String certificateImageEN) {
		this.certificateImageEN = certificateImageEN;
	}

	public String getCertificateImagePT() {
		return certificateImagePT;
	}

	public void setCertificateImagePT(String certificateImagePT) {
		this.certificateImagePT = certificateImagePT;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public boolean isShowEmailWRT() {
		return showEmailWRT;
	}

	public void setShowEmailWRT(boolean showEmailWRT) {
		this.showEmailWRT = showEmailWRT;
	}

	public String getDcActivity() {
		return dcActivity;
	}

	public void setDcActivity(String dcActivity) {
		this.dcActivity = dcActivity;
	}

	public Integer getTerms() {
		return terms;
	}

	public void setTerms(Integer terms) {
		this.terms = terms;
	}

	public String getLink_es() {
		return link_es;
	}

	public void setLink_es(String link_es) {
		this.link_es = link_es;
	}

	public String getLink_en() {
		return link_en;
	}

	public void setLink_en(String link_en) {
		this.link_en = link_en;
	}

	public String getLink_pt() {
		return link_pt;
	}

	public void setLink_pt(String link_pt) {
		this.link_pt = link_pt;
	}

	public boolean isIcon() {
		return icon;
	}

	public void setIcon(boolean icon) {
		this.icon = icon;
	}

	public boolean isAttachesPDF() {
		return attachesPDF;
	}

	public void setAttachesPDF(boolean attachesPDF) {
		this.attachesPDF = attachesPDF;
	}

	public boolean isAttachenPDF() {
		return attachenPDF;
	}

	public void setAttachenPDF(boolean attachenPDF) {
		this.attachenPDF = attachenPDF;
	}

	public boolean isAttachptPDF() {
		return attachptPDF;
	}

	public void setAttachptPDF(boolean attachptPDF) {
		this.attachptPDF = attachptPDF;
	}

	public boolean isUntilApproved() {
		return untilApproved;
	}

	public void setUntilApproved(boolean untilApproved) {
		this.untilApproved = untilApproved;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
