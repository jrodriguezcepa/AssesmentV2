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

import assesment.persistence.assesment.tables.AssesmentBKP;
import assesment.persistence.user.tables.User;
import assesment.persistence.user.tables.UserAssesmentData;

public class UserAssesmentBKP implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private User user; 
	private AssesmentBKP assesment;
    private Set answers;
    private Set psianswers;
    
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
    
    public UserAssesmentBKP(){}
	
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}

	public void setAssesment(AssesmentBKP assesment) {
		this.assesment = assesment;
	}
	public AssesmentBKP getAssesment() {
		return assesment;
	}
	
	public Set getAnswers() {
		if(answers == null) {
			answers = new HashSet();
		}
        return  answers;
    }


    public void setAnswers(Set answers) {
        this.answers = answers;
    }




    public Set getPsianswers() {
        return psianswers;
    }


    public void setPsianswers(Set psianswers) {
        this.psianswers = psianswers;
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


	public void deleteResults() {
	    answers = new HashSet();
	    psianswers = new HashSet();
	    
	    psiresult1 = null;
	    psiresult2 = null;
	    psiresult3 = null;
	    psiresult4 = null;
	    psiresult5 = null;
	    psiresult6 = null;
	    
	    psiId = null;
	    psiTestId = null;
	    
	    newHire = null;
	    
	    elearningRedirect = null; // Elearning
	    elearningInstance = null; // Elearning
	    elearningEnabled = false; // Elearning, habilia o no la redireccion al elearning

	    endDate = null;
	}
	
	public UserAnswer getAnsweredQuestion(Integer question) {
		Iterator it = answers.iterator();
		while(it.hasNext()) {
			UserAnswer answer = (UserAnswer)it.next();
			if(answer.getQuestion().getId().equals(question)) {
				return answer;
			}
		}
		return null;
	}
	
	public UserPsiAnswer getPsiAnsweredQuestion(Integer question) {
		Iterator it = psianswers.iterator();
		while(it.hasNext()) {
			UserPsiAnswer answer = (UserPsiAnswer)it.next();
			if(answer.getPsiquestion().getId().equals(question)) {
				return answer;
			}
		}
		return null;
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
