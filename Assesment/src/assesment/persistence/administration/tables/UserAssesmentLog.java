/*
 * Created on 14-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.persistence.administration.tables;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException;

import assesment.persistence.assesment.tables.Assesment;
import assesment.persistence.user.tables.User;
import assesment.persistence.user.tables.UserAssesmentData;

public class UserAssesmentLog implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private String userid;
    private Date date;
    private String comment;
    private Integer action;
    private Integer assesment;
    private String loginName;
    private Integer psiresult1;
    private Integer psiresult2;
    private Integer psiresult3;
    private Integer psiresult4;
    private Integer psiresult5;
    private Integer psiresult6;
    
    private Integer psiId;
    private Integer psiTestId;
    
    private Integer newHire;
    
    private String elearningRedirect; // Elearning
    private Integer elearningInstance; // Elearning
    private boolean elearningEnabled; // Elearning, habilia o no la redireccion al elearning

    private Date endDate;
    
    private boolean reportSended;
    private Date acceptTerms;
    
    public UserAssesmentLog(){}
	
	
	public UserAssesmentLog(UserAssesment ua, String loginName,Date date, Integer action, String comment){
		this.userid=loginName;
		this.date=date;
		this.comment=comment;
		this.action=action;
		this.assesment=ua.getPk().getAssesment().getId();
		this.loginName=ua.getPk().getUser().getLoginName();
		this.psiresult1=ua.getPsiresult1();
		this.psiresult2=ua.getPsiresult2();
		this.psiresult3=ua.getPsiresult3();
		this.psiresult4=ua.getPsiresult4();
		this.psiresult5=ua.getPsiresult5();
		this.psiresult6=ua.getPsiresult6();
		this.psiId=ua.getPsiId();
		this.psiTestId=ua.getPsiTestId();
		this.newHire=ua.getNewHire();
		this.elearningRedirect=ua.getElearningRedirect();
		this.elearningInstance=ua.getElearningInstance();
		this.elearningEnabled=ua.isElearningEnabled();
		this.endDate=ua.getEndDate();
		this.reportSended=ua.isReportSended();
		this.acceptTerms=ua.isAcceptTerms();

	}
	
	public void setUserid(String userId) {
		this.userid = userId;
	}
	public String getUserid() {
		return userid;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}

	public void setAction(Integer action) {
		this.action = action;
	}
	public Integer getAction() {
		return action;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getComment() {
		return comment;
	}

	
	public void setAssesment(Integer assesment) {
		this.assesment = assesment;
	}
	
	/**
	 * @return Returns the assesment.
	 */
	public Integer getAssesment() {
		return assesment;
	}
	
	public void setLoginName(String login) {
		this.loginName = login;
	}
	
	
	public String getLoginName() {
		return loginName;
	}

    public Integer getPsiresult1() {
        return psiresult1;
    }


    public Integer getPsiresult2() {
        return psiresult2;
    }


    public Integer getPsiresult3() {
        return psiresult3;
    }


    public Integer getPsiresult4() {
        return psiresult4;
    }


    public Integer getPsiresult5() {
        return psiresult5;
    }


    public Integer getPsiresult6() {
        return psiresult6;
    }


    public void setPsiresult1(Integer psiresult1) {
        this.psiresult1 = psiresult1;
    }


    public void setPsiresult2(Integer psiresult2) {
        this.psiresult2 = psiresult2;
    }


    public void setPsiresult3(Integer psiresult3) {
        this.psiresult3 = psiresult3;
    }


    public void setPsiresult4(Integer psiresult4) {
        this.psiresult4 = psiresult4;
    }


    public void setPsiresult5(Integer psiresult5) {
        this.psiresult5 = psiresult5;
    }


    public void setPsiresult6(Integer psiresult6) {
        this.psiresult6 = psiresult6;
    }


    public void setPsicoResult(int result, int value) {
        switch(result) {
            case 0:
                psiresult1 = value;
                break;
            case 1:
                psiresult2 = value;
                break;
            case 2:
                psiresult3 = value;
                break;
            case 3:
                psiresult4 = value;
                break;
            case 4:
                psiresult5 = value;
                break;
            case 5:
                psiresult6 = value;
                break;
        }
    }


    public Integer getPsiId() {
        return psiId;
    }


    public Integer getPsiTestId() {
        return psiTestId;
    }


    public void setPsiId(Integer psiId) {
        this.psiId = psiId;
    }


    public void setPsiTestId(Integer psiTestId) {
        this.psiTestId = psiTestId;
    }


    public Integer getNewHire() {
        return newHire;
    }


    public void setNewHire(Integer newHire) {
        this.newHire = newHire;
    }


	/**
	 * @return the eLearningRedirect
	 */
	public String getElearningRedirect() {
		return elearningRedirect;
	}


	/**
	 * @param learningRedirect the eLearningRedirect to set
	 */
	public void setElearningRedirect(String learningRedirect) {
		elearningRedirect = learningRedirect;
	}


	/**
	 * @return the eLearningInstance
	 */
	public Integer getElearningInstance() {
		return elearningInstance;
	}


	/**
	 * @param learningInstance the eLearningInstance to set
	 */
	public void setElearningInstance(Integer learningInstance) {
		elearningInstance = learningInstance;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the elearningEnabled
	 */
	public boolean isElearningEnabled() {
		return elearningEnabled;
	}


	/**
	 * @param elearningEnabled the elearningEnabled to set
	 */
	public void setElearningEnabled(boolean elearningEnabled) {
		this.elearningEnabled = elearningEnabled;
	}

	public boolean isReportSended() {
		return reportSended;
	}


	public void setReportSended(boolean reportSended) {
		this.reportSended = reportSended;
	}


	public Date isAcceptTerms() {
		return acceptTerms;
	}


	public void setAcceptTerms(Date acceptTerms) {
		this.acceptTerms = acceptTerms;
	}
	
}
