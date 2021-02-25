/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.assesment;

import java.util.Calendar;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.dom4j.Text;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.presentation.translator.web.util.Util;

public class AssesmentForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    

    public static final String NO = "0";
    public static final String YES = "1";

    private String id;
    private String name_es;
    private String name_en;
    private String name_pt;
    
    private FormFile icon;
    private FormFile attachesPDF;
    private FormFile attachenPDF;
    private FormFile attachptPDF;
    
	private String description;
	private String corporation;
	private String startDay;
	private String startMonth;
	private String startYear;
    private String endDay;
    private String endMonth;
    private String endYear;
    private String status;

    private String target;

    private String psitest;
    private String elearning;
    private String certificate;

    private String instantFeedback;
    private String reportFeedback;
    private String errorFeedback;
    private String psiFeedback;
    
    private String green;
    private String yellow;

    private String certificateImageES;
    private String certificateImageEN;
    private String certificateImagePT;

    private FormFile certificateES;
    private FormFile certificateEN;
    private FormFile certificatePT;

    private String copy;
    private String archived;
    private String emailWRT;
    
    private String repeatable;

    public AssesmentForm() {	    
	}
	
	public AssesmentForm(String corporation) {
	    this.corporation = corporation;
        this.target = "corporation";
        this.status = String.valueOf(AssesmentAttributes.EDITABLE);
	}

    public AssesmentForm(AssesmentAttributes data, AssesmentAccess sys) throws Exception {
        if(data.getId() != null) {
            this.id = String.valueOf(data.getId());
        }
	    String[] texts = sys.getLanguageReportFacade().findTexts(data.getName(),sys.getUserSessionData());
        this.name_es = texts[0];
        this.name_en = texts[1];
        this.name_pt = texts[2];
        
        this.description = data.getDescription();
        this.corporation = String.valueOf(data.getCorporationId());
        
        Calendar start = Calendar.getInstance();
        start.setTime(data.getStart());
        this.startDay = String.valueOf(start.get(Calendar.DATE));
        this.startMonth = String.valueOf(start.get(Calendar.MONTH)+1);
        this.startYear = String.valueOf(start.get(Calendar.YEAR));
        
        Calendar end = Calendar.getInstance();
        end.setTime(data.getEnd());
        this.endDay = String.valueOf(end.get(Calendar.DATE));
        this.endMonth = String.valueOf(end.get(Calendar.MONTH)+1);
        this.endYear = String.valueOf(end.get(Calendar.YEAR));
        
        psitest = (data.isPsitest()) ? YES : NO;
        elearning = (data.isElearning()) ? YES : NO;
        if(data.isCertificate()) {
        	certificate = "1";
        	if(!Util.empty(data.getCertificateImageES()) || !Util.empty(data.getCertificateImageEN()) || !Util.empty(data.getCertificateImageES())) {
            	certificate = "2";
        	}
        }else {
        	certificate = "0";
        }
        
        instantFeedback = (data.isInstantFeedback()) ? YES : NO;
        reportFeedback = (data.isReportFeedback()) ? YES : NO;
        errorFeedback = (data.isErrorFeedback()) ? YES : NO;
        psiFeedback = (data.isPsiFeedback()) ? YES : NO;

        this.status = String.valueOf(data.getStatus());
        
        this.green = String.valueOf(data.getGreen());
        this.yellow = String.valueOf(data.getYellow());
        this.certificateImageES = data.getCertificateImageES();
        this.certificateImageEN = data.getCertificateImageEN();
        this.certificateImagePT = data.getCertificateImagePT();
        
        archived = (data.isArchived()) ? YES : NO;
        emailWRT = (data.isShowEmailWRT()) ? YES : NO;
        repeatable = (data.isUntilApproved()) ? YES : NO;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorporation() {
        return corporation;
    }

    public String getDescription() {
        return description;
    }

    public String getEndDay() {
        return endDay;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getStartDay() {
        return startDay;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String validate() {
        if(empty(corporation)) {
            return "assesment.error.emptycorporation";
        }
        if(empty(name_es) || empty(name_en) || empty(name_pt)) {
            return "assesment.error.emptyname";
        }
        try {
            if(!Util.checkDate(Integer.parseInt(startDay),Integer.parseInt(startMonth),Integer.parseInt(startYear))) {
                return "assesment.error.wrongstartdate";
            }
        }catch(Exception e) {
            return "assesment.error.wrongstartdate";
        }
        try {
            if(!Util.checkDate(Integer.parseInt(endDay),Integer.parseInt(endMonth),Integer.parseInt(endYear))) {
                return "assesment.error.wrongenddate";
            }
        }catch(Exception e) {
            return "assesment.error.wrongenddate";
        }
        try {
            if(empty(green)) {
                return "assesment.error.emptygreen";
            }else {
                Integer.parseInt(green);
            }
        }catch(Exception e) {
            return "assesment.error.wronggreen";
        }
        try {
            if(empty(yellow)) {
                return "assesment.error.emptyred";
            }else {
                Integer.parseInt(yellow);
            }
        }catch(Exception e) {
            return "assesment.error.wrongred";
        }
        return null;
    }

    public AssesmentAttributes createAssesment() {
        AssesmentAttributes attributes = new AssesmentAttributes();
        if(!empty(id)) {
            attributes.setId(new Integer(id));
            attributes.setName("assessment"+id+".name");
        }else {
            attributes.setName("assessment.noname");
        }
        attributes.setDescription(description);
        attributes.setCorporationId(new Integer(corporation));
        attributes.setStatus(new Integer(status));
        attributes.setStart(getDate(startDay,startMonth,startYear));
        attributes.setEnd(getDate(endDay,endMonth,endYear));
        attributes.setPsitest(psitest.equals(YES));
        attributes.setElearning(false);
        attributes.setInstantFeedback(false);
        attributes.setReportFeedback(reportFeedback.equals(YES));
        attributes.setErrorFeedback(false);
        attributes.setCertificate(Integer.parseInt(certificate) > 0);
        attributes.setPsiFeedback(false);
        attributes.setGreen(new Integer(green));
        attributes.setYellow(new Integer(yellow));
        attributes.setCertificateImageES(certificateImageES);
        attributes.setCertificateImageEN(certificateImageEN);
        attributes.setCertificateImagePT(certificateImagePT);
        if(archived != null)
        	attributes.setArchived(archived.equals(YES));
        else
        	attributes.setArchived(false);
        if(emailWRT != null)
        	attributes.setShowEmailWRT(emailWRT.equals(YES));
        else
        	attributes.setShowEmailWRT(true);
        if(repeatable != null)
        	attributes.setUntilApproved(repeatable.equals(YES));
        else
        	attributes.setUntilApproved(false);
        return attributes;
    }
    
    private boolean empty(String label) {
        return label == null || label.trim().length() == 0;
    }
    
    public Date getStartDate() {
    	try {
    		return getDate(startDay, startMonth, startYear);
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public Date getEndDate() {
    	try {
    		return getDate(endDay, endMonth, endYear);
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    private Date getDate(String day,String month,String year) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DATE,Integer.parseInt(day));
        date.set(Calendar.MONTH,Integer.parseInt(month)-1);
        date.set(Calendar.YEAR,Integer.parseInt(year));
        return date.getTime();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPsitest() {
        return psitest;
    }

    public void setPsitest(String psitest) {
        this.psitest = psitest;
    }

    public String getInstantFeedback() {
        return instantFeedback;
    }

    public String getPsiFeedback() {
        return psiFeedback;
    }

    public String getReportFeedback() {
        return reportFeedback;
    }

    public void setInstantFeedback(String instantFeedback) {
        this.instantFeedback = instantFeedback;
    }

    public void setPsiFeedback(String psiFeedback) {
        this.psiFeedback = psiFeedback;
    }

    public void setReportFeedback(String reportFeedback) {
        this.reportFeedback = reportFeedback;
    }

    public String getGreen() {
        return green;
    }

    public String getYellow() {
        return yellow;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public void setYellow(String yellow) {
        this.yellow = yellow;
    }

    public String getErrorFeedback() {
        return errorFeedback;
    }

    public void setErrorFeedback(String errorFeedback) {
        this.errorFeedback = errorFeedback;
    }

    public String getElearning() {
        return elearning;
    }

    public void setElearning(String elearning) {
        this.elearning = elearning;
    }

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
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

	public String getCopy() {
		return copy;
	}

	public void setCopy(String copy) {
		this.copy = copy;
	}

	public FormFile getCertificateES() {
		return certificateES;
	}

	public void setCertificateES(FormFile certificateES) {
		this.certificateES = certificateES;
	}

	public FormFile getCertificateEN() {
		return certificateEN;
	}

	public void setCertificateEN(FormFile certificateEN) {
		this.certificateEN = certificateEN;
	}

	public FormFile getCertificatePT() {
		return certificatePT;
	}

	public void setCertificatePT(FormFile certificatePT) {
		this.certificatePT = certificatePT;
	}

	public String getArchived() {
		return archived;
	}

	public void setArchived(String archived) {
		this.archived = archived;
	}

	public String getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(String repeatable) {
		this.repeatable = repeatable;
	}

	public String getEmailWRT() {
		return emailWRT;
	}

	public void setEmailWRT(String emailWRT) {
		this.emailWRT = emailWRT;
	}

	public FormFile getIcon() {
		return icon;
	}

	public void setIcon(FormFile icon) {
		this.icon = icon;
	}

	public FormFile getAttachesPDF() {
		return attachesPDF;
	}

	public void setAttachesPDF(FormFile attachesPDF) {
		this.attachesPDF = attachesPDF;
	}

	public FormFile getAttachenPDF() {
		return attachenPDF;
	}

	public void setAttachenPDF(FormFile attachenPDF) {
		this.attachenPDF = attachenPDF;
	}

	public FormFile getAttachptPDF() {
		return attachptPDF;
	}

	public void setAttachptPDF(FormFile attachptPDF) {
		this.attachptPDF = attachptPDF;
	}

	public String getName_es() {
		return name_es;
	}

	public void setName_es(String name_es) {
		this.name_es = name_es;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_pt() {
		return name_pt;
	}

	public void setName_pt(String name_pt) {
		this.name_pt = name_pt;
	}

}