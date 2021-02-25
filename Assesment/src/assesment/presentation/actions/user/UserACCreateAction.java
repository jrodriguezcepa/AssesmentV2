/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.communication.administration.AccessCodeData;
import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.security.SecurityConstants;
import assesment.communication.user.UserData;
import assesment.communication.util.MD5;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

public class UserACCreateAction extends AbstractAction {

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        UserCreateForm createData = (UserCreateForm) form;
        switch(Integer.parseInt(createData.getAssesment())) {
        	case AssesmentData.ABITAB:
        		return mapping.findForward("reloadSession");
        	case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
        		return mapping.findForward("logoutAnglo");
        }
        return mapping.findForward("logout");
    }

    public ActionForward action(ActionMapping mapping, ActionForm createForm, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        HttpSession session = request.getSession();
        AssesmentAccess sys = (AssesmentAccess)session.getAttribute("AssesmentAccess");
        Text messages = sys.getText();

        UserCreateForm createData = (UserCreateForm) createForm;
        
        Integer assesment = new Integer(createData.getAssesment());
        AssesmentAttributes attr = sys.getAssesmentReportFacade().findAssesmentAttributes(assesment,sys.getUserSessionData());

        if(assesment.intValue() == AssesmentData.DNB && Util.empty(createData.getEmail())) {
        	createData.setEmail("cepabomberos@gmail.com");
        }

        String nick = createData.getLoginname();
        String firstName = createData.getFirstName();
        String lastName = createData.getLastName();
        String language = createData.getLanguage();
        String mail = createData.getEmail();
        if(assesment.intValue() == AssesmentData.IMESEVI && Util.empty(mail)) {
        	mail = "cepadriverassessment@gmail.com";
        }
        String role = SecurityConstants.ACCESS_TO_SYSTEM;
        String passwd = createData.getPassword();
        try {
            AccessCodeData ac = sys.getAssesmentReportFacade().getAccessCode(createData.getRole(),sys.getUserSessionData());
            UserData data = null; 
            String msg = null;
            
            if(assesment.intValue() == AssesmentData.ABITAB) {
        		msg = validateAbitab(messages,createForm);
	            if (msg != null) {
	                session.setAttribute("Msg", msg);
	                return mapping.findForward("backAbitab");
	            }else {
	            	MD5 md5 = new MD5();
	            	Collection list = sys.getUserReportFacade().findAbitabUser(nick, sys.getUserSessionData());
	            	int index = 1;
	            	Iterator it = list.iterator();
	            	while(it.hasNext()) {
	            		StringTokenizer tokenizer = new StringTokenizer((String)it.next(),"_");
	            		tokenizer.nextToken();
	            		tokenizer.nextToken();
	            		int value = Integer.parseInt(tokenizer.nextToken());
	            		if(value+1 > index) {
	            			index = value+1;
	            		}
	            	}
	            	nick = "abitab_"+nick+"_"+index;
	            	passwd = md5.encriptar(nick);
	            	data = new UserData(nick,passwd,createData.getFirstName(),createData.getLastName(),"es",createData.getEmail(),role,null);
	            	data.setBirthDate(Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()));
	            	data.setExtraData(createData.getCompany());
	            	data.setVehicle(createData.getVehicle());
	            	data.setStartDate(Calendar.getInstance().getTime());
	            }
            }else {
	            switch(assesment.intValue()) {
            		case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
            			msg = validateAnglo(messages,createForm);
            			break;
            		case AssesmentData.IMESEVI:
            			msg = validateImesevi(messages,createForm);
            			break;
            		case AssesmentData.ASTRAZENECA:
            			msg = validateAZ(messages,createForm);
            			break;
	            	case AssesmentData.MONSANTO_BRAZIL: 
	            		msg = validateMonsantoBR(messages,createForm);
	            		break;
	            	case AssesmentData.PEPSICO_CANDIDATOS:
	            		msg = validatePepsicoCandidatos(messages,createForm);
	            		break;
	            	case AssesmentData.PEPSICO:
	            		msg = validatePepsico(messages,createForm);
	            		break;
	            	default:
	            		msg = validate(messages,createForm,attr.isReportFeedback(),attr.getId().intValue()); 
	            }
	            if (msg != null) {
	                session.setAttribute("Msg", msg);
	                session.setAttribute("assesment", assesment);
	                session.setAttribute("accesscode",createData.getRole());
	                createData.setPassword(null);
	                createData.setRePassword(null);
	                return mapping.findForward(getBack(attr.getId()));
	            }
	            String valid = UserData.validatePassword(passwd);
	            if(valid != null) {
	                session.setAttribute("Msg",valid);
	                session.setAttribute("assesment", assesment);
	                session.setAttribute("accesscode",createData.getRole());
	                createData.setPassword(null);
	                createData.setRePassword(null);
	                return mapping.findForward(getBack(attr.getId()));
	            }
	
	            Date expiry = null;
	            if(ac.getExtension() != null && ac.getExtension().intValue() > 0) {
	                Calendar c = Calendar.getInstance();
	                c.add(Calendar.DATE,ac.getExtension().intValue());
	                expiry = c.getTime();
	            }
	            data = new UserData(nick,passwd,firstName,lastName,language,mail,role,expiry);
            	data.setBirthDate(Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()));
	            switch(attr.getId().intValue()) {
	            	case AssesmentData.PEPSICO: case AssesmentData.PEPSICO_CANDIDATOS: case AssesmentData.PEPSICO_CEPA_SYSTEM:
	                    data.setExtraData(createData.getCompany());
	                    break;
	            	case AssesmentData.DNB:
	                	data.setSex(new Integer(createData.getSex()));
	                    data.setExtraData(createData.getCompany());
	                    break;
	                default:
	                	data.setSex(new Integer(createData.getSex()));
	            }
	
	            data.setCountry(new Integer(createData.getCountry()));
	            if(Util.empty(createData.getNationality()) || !Util.isNumber(createData.getNationality())) {
	                data.setNationality(new Integer(createData.getCountry()));
	            }else {
	                data.setNationality(new Integer(createData.getNationality()));
	            }
	            data.setStartDate(Util.getDate(createData.getStartDay(),createData.getStartMonth(),createData.getStartYear()));
	            if((assesment.intValue() == AssesmentData.PEPSICO || assesment.intValue() == AssesmentData.PEPSICO_CANDIDATOS
	            		 || assesment.intValue() == AssesmentData.PEPSICO_CEPA_SYSTEM)
	            		&& Util.isChecked(createData.getExpiryType())) {
	            	Calendar c = Calendar.getInstance();
	            	c.set(2100, 0, 1);
	            	data.setLicenseExpiry(c.getTime());
	            }else {
	            	data.setLicenseExpiry(Util.getDate(createData.getExpiryDay(),createData.getExpiryMonth(),createData.getExpiryYear()));
	            }
	            data.setVehicle(createData.getVehicle());
	            if(!Util.empty(createData.getLocation()) && Util.isNumber(createData.getLocation())) {
	                data.setLocation(new Integer(createData.getLocation()));
	            }
	            if(!Util.empty(createData.getFdm()) && Util.isNumber(createData.getFdm())) {
	                data.setDatacenter(new Integer(createData.getFdm()));
	            }
	            switch(attr.getId().intValue()) {
	            	case AssesmentData.JJ: case AssesmentData.JJ_2: case AssesmentData.JJ_3: case AssesmentData.JJ_4:  case AssesmentData.JJ_5:  case AssesmentData.JJ_6: case AssesmentData.JJ_7:   
	                    data.setExtraData(createData.getCompany());
	                    return mapping.findForward("confirmation");
	            	case AssesmentData.ABITAB:
	                    data.setExtraData(String.valueOf(UserData.ASSESMENT_USER));
	                    break;
	            	case AssesmentData.PEPSICO_CANDIDATOS:
	    	            data.setExtraData2(createData.getExtraData2());
	            		data.setExtraData3(createData.getExtraData3());
	            		break;
	            }
            }
            
            sys.getUserABMFacade().userCreateFromAC(data,assesment,createData.getRole(),sys.getUserSessionData());
            
            session.setAttribute("user",nick);
            session.setAttribute("password",passwd);
            return mapping.findForward("next");
                
        }catch(Exception e) {
            Throwable thw = (e instanceof ServletException) ? ((ServletException)e).getRootCause() : e;
            while (thw.getCause() != null) {
                thw = thw.getCause();
            }
            if (thw.getClass().toString().indexOf("AlreadyExistsException") > 0) {
                session.setAttribute("Msg",messages.getText("user.error.alreadyexist"));
                session.setAttribute("assesment", assesment);
                session.setAttribute("accesscode",createData.getRole());
                createData.setPassword(null);
                createData.setRePassword(null);
                return mapping.findForward(getBack(attr.getId()));
            }else {
                createData.setPassword(null);
                createData.setRePassword(null);
                createData.setLoginname(null);
                throw e;
            }
        }
    }

	private String validateAbitab(Text messages, ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if(createData.getLoginname().length() < 6 || createData.getLoginname().length() > 8 || !Util.isLongNumber(createData.getLoginname())) {
            return messages.getText("abitab.error.invalidci");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (Util.empty(createData.getEmail())) {
            return messages.getText("newhire.email.isempty");
        }
        if (Util.empty(createData.getEmailConfirmation())) {
            return messages.getText("newhire.emailconfirmation.isempty");
        }
        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
            return messages.getText("newhire.email.wrongconfirmation");
        }
        Date birthDate = Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()); 
        if (birthDate == null) {
            return messages.getText("generic.user.userdata.birthdate.isempty");
        }
		return null;
	}

	private String getBack(Integer assessment) {
        switch(assessment) {
            case AssesmentData.JJ: case AssesmentData.JJ_2: case AssesmentData.JJ_3:
            case AssesmentData.JJ_4: case AssesmentData.JJ_5: case AssesmentData.JJ_6: 
            case AssesmentData.JJ_7: case AssesmentData.JJ_8:
                return "backJJ";
/*            case AssesmentData.MONSANTO_NEW_HIRE:
                return "backNewHire";
            case AssesmentData.MONSANTO_BRAZIL:
                return "backMonsantoBR";
            case AssesmentData.PEPSICO: case AssesmentData.PEPSICO_CEPA_SYSTEM:
                return "backPepsico";
            case AssesmentData.PEPSICO_CANDIDATOS: 
                return "backPepsicoCandidatos";
            case AssesmentData.ABITAB:
                return "backAbitab";
            case AssesmentData.DNB:
                return "backDNB";
            case AssesmentData.ANGLO: case AssesmentData.ANGLO_3:
                return "backAnglo";
            case AssesmentData.IMESEVI:
                return "backImesevi";
            case AssesmentData.ASTRAZENECA:
                return "backAZ";*/
            default:
                return "back";
        }
    }
    
    private String validate(Text messages,ActionForm form,boolean validateEmail,int corporationId) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        Date birthDate = Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()); 
        if (birthDate == null) {
            return messages.getText("generic.user.userdata.birthdate.isempty");
        }else {
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDate);
            birth.add(Calendar.YEAR,18);
            Calendar now = Calendar.getInstance();
            if(now.before(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
            birth.add(Calendar.YEAR,82);
            if(now.after(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
        }
        if (Util.empty(createData.getLanguage())) {
            return messages.getText("generic.user.userdata.language.isempty");
        }
        if (Util.empty(createData.getCountry())) {
            return messages.getText("generic.user.userdata.country.isempty");
        }
        /*if (Util.empty(createData.getNationality()) && corporationId != AssesmentData.JJ
        		&& corporationId != AssesmentData.JJ_2
        		&& corporationId != AssesmentData.JJ_3
        		&& corporationId != AssesmentData.JJ_4
        		&& corporationId != AssesmentData.JJ_5
        		&& corporationId != AssesmentData.JJ_6
        		&& corporationId != AssesmentData.JJ_7
        		&& corporationId != AssesmentData.JJ_8) {
            return messages.getText("generic.user.userdata.nationality.isempty");
        }*/
        if (corporationId == AssesmentData.MONSANTO_NEW_HIRE) {
            if (Util.empty(createData.getEmail())) {
                return messages.getText("newhire.email.isempty");
            }
            if (Util.empty(createData.getEmailConfirmation())) {
                return messages.getText("newhire.emailconfirmation.isempty");
            }
            if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
                return messages.getText("newhire.email.wrongconfirmation");
            }
        }
        if(validateEmail) {
            if (Util.empty(createData.getEmail())) {
                return messages.getText("user.email.isempty");
            }
        }
        return null;
    }

    private String validateImesevi(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (!Util.empty(createData.getEmail()) || !Util.empty(createData.getEmailConfirmation())) {
	        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
	        	return messages.getText("monsantobr.email.wrongconfirmation");
	        }
        }
        return null;
    }

    private String validateAZ(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        Date birthDate = Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()); 
        if (birthDate == null) {
            return messages.getText("generic.user.userdata.birthdate.isempty");
        }else {
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDate);
            birth.add(Calendar.YEAR,18);
            Calendar now = Calendar.getInstance();
            if(now.before(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
            birth.add(Calendar.YEAR,82);
            if(now.after(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (!Util.empty(createData.getEmail()) || !Util.empty(createData.getEmailConfirmation())) {
	        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
	        	return messages.getText("monsantobr.email.wrongconfirmation");
	        }
        }
        return null;
    }

    private String validateMonsantoBR(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (Util.empty(createData.getCountry())) {
            return messages.getText("generic.user.userdata.country.isempty");
        }
        if (Util.empty(createData.getEmail())) {
        	return messages.getText("monsantobr.email.isempty");
        }
        if (Util.empty(createData.getEmailConfirmation())) {
        	return messages.getText("monsantobr.emailconfirmation.isempty");
        }
        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
        	return messages.getText("monsantobr.email.wrongconfirmation");
        }
        return null;
    }

    private String validatePepsico(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (Util.empty(createData.getCountry())) {
            return messages.getText("generic.user.userdata.country.isempty");
        }
        Date birthDate = Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()); 
        if (birthDate == null) {
            return messages.getText("generic.user.userdata.birthdate.isempty");
        }else {
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDate);
            birth.add(Calendar.YEAR,18);
            Calendar now = Calendar.getInstance();
            if(now.before(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
            birth.add(Calendar.YEAR,82);
            if(now.after(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
        }
        if (Util.empty(createData.getEmail())) {
        	return messages.getText("monsantobr.email.isempty");
        }
        if (Util.empty(createData.getEmailConfirmation())) {
        	return messages.getText("monsantobr.emailconfirmation.isempty");
        }
        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
        	return messages.getText("monsantobr.email.wrongconfirmation");
        }
        return null;
    }

    private String validatePepsicoCandidatos(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return messages.getText("generic.user.userdata.loginname.isinvalid");
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return messages.getText("generic user.userdata.passconfirm");
            }
        } else {
            return messages.getText("generic.user.userdata.pass.isempty");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (Util.empty(createData.getCountry())) {
            return messages.getText("generic.user.userdata.country.isempty");
        }
        Date birthDate = Util.getDate(createData.getBirthDay(),createData.getBirthMonth(),createData.getBirthYear()); 
        if (birthDate == null) {
            return messages.getText("generic.user.userdata.birthdate.isempty");
        }else {
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDate);
            birth.add(Calendar.YEAR,18);
            Calendar now = Calendar.getInstance();
            if(now.before(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
            birth.add(Calendar.YEAR,82);
            if(now.after(birth)) {
                return messages.getText("user.birthdate.wrongrange");
            }
        }
        if (Util.empty(createData.getEmail())) {
        	return messages.getText("monsantobr.email.isempty");
        }
        if (Util.empty(createData.getEmailConfirmation())) {
        	return messages.getText("monsantobr.emailconfirmation.isempty");
        }
        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
        	return messages.getText("monsantobr.email.wrongconfirmation");
        }
        if (Util.empty(createData.getExtraData2())) {
        	return "Debe seleccionar Producto";
        }
        if (Util.empty(createData.getExtraData3())) {
        	return "Debe seleccionar CEDIS";
        }
        return null;
    }

    private String validateAnglo(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        if (Util.empty(createData.getLoginname())) {
            return "Usuário inválido";
        }
        if (!Util.empty(createData.getPassword()) && !Util.empty(createData.getRePassword())) {
            if (createData.getPassword().compareTo(createData.getRePassword()) != 0) {
                return "A confirmação da senha deve ser igual a senha";
            }
        } else {
            return "O campo Senha está vazio";
        }
        if (Util.empty(createData.getFirstName())) {
            return "Nome não informado";
        }
        if (Util.empty(createData.getLastName())) {
            return "Sobrenome não informado";
        }

        if (Util.empty(createData.getSex()) || createData.getSex().equals("0")) {
            return "Estado não informado";
        }
        if (Util.empty(createData.getCountry()) || createData.getCountry().equals("0")) {
            return "Operação não informado";
        }
        if (Util.empty(createData.getNationality()) || createData.getNationality().equals("0")) {
            return "Divisão não informado";
        }

        if (Util.empty(createData.getEmail())) {
        	return "E-mail não informado";
        }
        if (Util.empty(createData.getEmailConfirmation())) {
        	return "Confirmação do e-mail não preenchida";
        }
        if (!createData.getEmail().equals(createData.getEmailConfirmation())) {
        	return "Confirmação do e-mail deve ser igual ao e-mail";
        }
        return null;
    }

    private String getTitle1(String language) {
        if(language.equals("es")) {
            return "Nuevo usuario";
        }else if(language.equals("pt")) {
            return "Usuario novo";
        }else {
            return "New User";
        }
    }
    
    private String getMessage1(String language, String loginname) {
        String message = "";
        if(language.equals("es")) {
            message += "<b>Por su pedido, su usuario ha sido creado.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";
        }else if(language.equals("pt")) {
            message += "<b>Segundo SolicitaÃ§Ã£o, su usuario foi criado.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";        
        }else {
            message += "<b>For your asking your user has been created.</b><br>";
            message += "<b>url:</b> <a href=\"http://da.cepasafedrive.com/assesment/>da.cepasafedrive.com/assesment</a><br>";
            message += "<b>Login:</b> "+loginname;
            message += "<br>";
            message += "<b>Password: </b>";
        }
        return Util.emailTranslation(message);
    }
}