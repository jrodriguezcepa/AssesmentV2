/**
 * CEPA
 * Assesment
 */
package assesment.communication.assesment;

import java.util.Date;

import assesment.communication.user.UserData;
import assesment.communication.util.MD5;

public class AssesmentAttributes implements Comparable<AssesmentAttributes> {

    public static final int EDITABLE = 0; 
    public static final int NO_EDITABLE = 1; 
    public static final int ENDED = 2; 
    
    protected Integer id;
    
    private String name;
    private String description;
    
    private Integer corporationId;
    private Integer status;
    
    private Date start;
    private Date end;
    
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
	private boolean archived;
	private boolean showEmailWRT;
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
	private Boolean showReport;

	public AssesmentAttributes() {
    }

    /**
     * @param id
     * @param name
     * @param description
     * @param start
     * @param end
     * @param psiFeedback 
     * @param reportFeedback 
     * @param instantFeedback 
     * @param psiFeedback2 
     * @param psiFeedback2 
     * @param yellow 
     * @param green 
     * @param certificateImage 
     * @param certificate 
     * @param archived 
     * @param link2 
     * @param link_pt 
     * @param link_en 
     * @param icon 
     */
    public AssesmentAttributes(Integer id, String name, String description, Integer corporationId, Date start, Date end, Integer status,boolean psitest, boolean elearning, boolean instantFeedback, boolean reportFeedback, boolean errorFeedback, boolean psiFeedback, Integer green, Integer yellow, boolean certificate, String certificateImageES, String certificateImageEN, String certificateImagePT, boolean archived, boolean showEmailWRT, Integer terms, String link_es, String link_en, String link_pt, boolean icon, boolean attachesPDF, boolean attachenPDF, boolean attachptPDF, boolean untilApproved, String link) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.corporationId = corporationId;
        this.start = start;
        this.end = end;
        this.status = status;
        this.psitest = psitest;
        this.elearning = elearning;
        this.instantFeedback = instantFeedback;
        this.reportFeedback = reportFeedback;
        this.errorFeedback = errorFeedback;
        this.psiFeedback = psiFeedback;
        this.green = green;
        this.yellow = yellow;
        this.certificate = certificate;
        this.certificateImageES = certificateImageES;
        this.certificateImageEN = certificateImageEN;
        this.certificateImagePT = certificateImagePT;
        this.archived = archived;
        this.showEmailWRT = showEmailWRT;
        this.terms = terms;
        this.link_es = link_es;
        this.link_en = link_en;
        this.link_pt = link_pt;
        this.icon = icon;
        this.attachesPDF = attachesPDF;
        this.attachenPDF = attachenPDF;
        this.attachptPDF = attachptPDF;
        this.untilApproved = untilApproved;
        this.link = link;
    }

    public AssesmentAttributes(Integer id) {
		this.id = id;
	}

	public AssesmentAttributes(Integer id, String name) {
        this.id = id;
        this.name = name;
	}

	public String getDescription() {
        return description;
    }

    public Date getEnd() {
        return end;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStart() {
        return start;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Integer getCorporationId() {
        return corporationId;
    }

    public void setCorporationId(Integer corporationId) {
        this.corporationId = corporationId;
    }

    public Integer getStatus() {
        return status;
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

    public String getStatusName() {
        switch(status) {
            case 0:
                return "assesment.status.editable";
            case 1:
                return "assesment.status.noeditable";
            default:
                return "assesment.status.ended";
        }
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

	public String getCertificateImage(String language) {
		if(language.equals("es")) {
			if(!empty(certificateImageES)) {
				return certificateImageES;
			}
		} else if(language.equals("en")) {
			if(!empty(certificateImageEN)) {
				return certificateImageEN;
			}
		} else if(language.equals("pt")) {
			if(!empty(certificateImagePT)) {
				return certificateImagePT;
			}
		}
		return null;
	}

	private boolean empty(String value) {
		return ((value == null) || (value.trim().length() == 0));
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

	@Override
	public int compareTo(AssesmentAttributes o) {
		return start.compareTo(o.start);
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

	public String getAssessmentLink(UserData user) {
		if((id.equals(AssesmentData.SAFEFLEET_ACADEMY_KMS) || id.equals(AssesmentData.SAFEFLEET_ACADEMY_EVENTS)
				|| id.equals(AssesmentData.SAFEFLEET_ACADEMY_COLLABORATOR) || id.equals(AssesmentData.SAFEFLEET_ACADEMY_DRIVERDETRAN) 
				|| id.equals(AssesmentData.SAFEFLEET_ACADEMY_COLLABORATORDETRAN) || id.equals(AssesmentData.SAFEFLEET_ACADEMY_HISTORY)) 
				&& user.getDatacenter() != null) {
        	Integer driver = user.getDatacenter(); 
        	String hash = new MD5().encriptar("jjacademy_"+driver);
        	int type = -1;
        	switch(id.intValue()) {
	        	case AssesmentData.SAFEFLEET_ACADEMY_KMS:
	        		type = 1;
	        		break;
	        	case AssesmentData.SAFEFLEET_ACADEMY_EVENTS:
	        		type = 2;
	        		break;
	        	case AssesmentData.SAFEFLEET_ACADEMY_COLLABORATOR:
	        		type = 3;
	        		break;
	        	case AssesmentData.	SAFEFLEET_ACADEMY_DRIVERDETRAN:
	        		type = 4;
	        		break;
	        	case AssesmentData.	SAFEFLEET_ACADEMY_COLLABORATORDETRAN:
	        		type = 5;
	        		break;
	        	case AssesmentData.	SAFEFLEET_ACADEMY_HISTORY:
	        		type = 6;
	        		break;
        	}
        	return "https://fdmpro.cepasafedrive.com/datacenter/home.jsp?jj=jjacademy&hash="+hash+"&driver="+driver+"&type="+type;
		}
		if(link == null) {
			return "./newmodule.jsp?id="+id;
		} else {
			return link;
		}
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setShowReport(Boolean showReport) {
		this.showReport = showReport;
	}

	public Boolean getShowReport() {
		return showReport;
	}

}
