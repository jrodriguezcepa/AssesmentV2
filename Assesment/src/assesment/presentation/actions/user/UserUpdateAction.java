/*
 * Created on 22-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import assesment.business.AssesmentAccess;
import assesment.business.administration.user.UsABMFacade;
import assesment.business.administration.user.UsReportFacade;
import assesment.communication.language.Text;
import assesment.communication.user.UserData;
import assesment.presentation.actions.administration.AnswerUtil;
import assesment.presentation.translator.web.util.AbstractAction;
import assesment.presentation.translator.web.util.Util;

/**
 * @author fcarriquiry
 */
public class UserUpdateAction extends AbstractAction {

    public ActionForward action(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        AssesmentAccess sys = ((AssesmentAccess)session.getAttribute("AssesmentAccess"));
        Text messages = sys.getText();

  /*      AnswerUtil util = new AnswerUtil();
        String[] users = {"acortesm.guinez", "adelgado.guinez", "agalleguillos.guinez", "aiturra.guinez", "alejandro.quiroz.guinez", "alvaro.fuentes.guinez", "antonio.salgado.guinez", "asandon.guinez", "calvarezc.guinez", "calvarez.guinez", "camilo.a.cortes.guinez", "caraya.guinez", "carolina.ulloa.guinez", "cbriones.guinez", "ccornejo.guinez", "claudio.campillay.guinez", "cristopher.alvear.guinez", "ctoro.guinez", "cvilla.guinez", "dbarrera.guinez", "dcarvajal.guinez", "dgallo.guinez", "droa.guinez", "dsanchez.guinez", "ecortes.guinez", "emedina.guinez", "ennio.cuellar.guinez", "falfaro.guinez", "farrepol.guinez", "fcortes.guinez", "fsanchez.guinez", "gcordova.guinez", "ggiancaspero.guinez", "guijseleme.guinez", "guimmardini.guinez", "guisolivares.guinez", "hernan.blanco.guinez", "hhurtado.guinez", "jannet.pizarro.guinez", "javier.soto.guinez", "jduran.guinez", "jflores.guinez", "jmunoz.guinez", "jramos.guinez", "jreyesl.guinez", "jrivera.guinez", "jvaldivia.guinez", "kimberly.hernandez.guinez", "ksantana.guinez", "laguero.guinez", "laguirre.guinez", "laravena.guinez", "lgajardo.guinez", "lremedi.guinez", "lrojas.guinez", "mavaria.guinez", "mcoliman.guinez", "mgalindo.guinez", "mhigueras.guinez", "milton.gatica.guinez", "mpacheco.guinez", "mseguel.guinez", "mumchavez.guinez", "mvalle.guinez", "mvargas.guinez", "nicolas.quiroga.guinez", "nmonroy.guinez", "nolberto.gajardo.guinez", "nvergara.guinez", "omar.angelexterno.guinez", "oscar.novoa.guinez", "pablo.zuniga.guinez", "pamela.arancibia", "pcarcamo.guinez", "pcastillo.guinez", "ptirado.guinez", "rahumada.guinez", "rcastillo.guinez", "rfuentes.guinez", "rgutierrez.guinez", "rodrigo.cuellar.guinez", "rrojas.guinez", "rseura.guinez", "rveas.guinez", "sergioyuricastellon.guinez", "smiranda.guinez", "svalenzuela.guinez", "vickmarie.e.rivas.guinez", "vriquelme.guinez", "wladimir.diaz.guinez", "xcortes.guinez", "yerko.vergara.guinez"};
        for(int i = 0; i < users.length; i++) {
            util.proccessPsi(users[i], 1531, sys);
        }*/
        
        UserCreateForm updateData = (UserCreateForm) form;
		String msg = validate(messages,form);

		if (msg != null) {
			session.setAttribute("Msg", msg);
			session.setAttribute("user",updateData.getLoginname());
			return mapping.findForward("back");
		}else {

            UsReportFacade userReport=sys.getUserReportFacade();
			UserData data=userReport.findUserByPrimaryKey(updateData.getLoginname(),sys.getUserSessionData());

			data.setLoginName(updateData.getLoginname());
			data.setFirstName(updateData.getFirstName());
            data.setLastName(updateData.getLastName());
            data.setLanguage(updateData.getLanguage());
            data.setEmail(updateData.getEmail());
            data.setRole(updateData.getRole());
            data.setBirthDate(Util.getDate(updateData.getBirthDay(),updateData.getBirthMonth(),updateData.getBirthYear()));
            data.setSex(new Integer(updateData.getSex()));
            data.setCountry(new Integer(updateData.getCountry()));
            if(Util.isNumber(updateData.getNationality()))
            	data.setNationality(new Integer(updateData.getNationality()));
            data.setStartDate(Util.getDate(updateData.getStartDay(),updateData.getStartMonth(),updateData.getStartYear()));
            data.setLicenseExpiry(Util.getDate(updateData.getExpiryDay(),updateData.getExpiryMonth(),updateData.getExpiryYear()));
            data.setVehicle(updateData.getVehicle());
            if(Util.isNumber(updateData.getLocation()))
            	data.setLocation(new Integer(updateData.getLocation()));

            if(Integer.parseInt(updateData.getExpiryType()) == UserData.WITH_EXPIRY) {
                data.setExpiry(Util.getDate(updateData.getUserExpiryDay(),updateData.getUserExpiryMonth(),updateData.getUserExpiryYear()));
            }else {
                data.setExpiry(null);
            }
            data.setExtraData(updateData.getExtraData());
            data.setExtraData2(updateData.getExtraData2());
            
            UsABMFacade userABM = sys.getUserABMFacade();
			userABM.userUpdate(data,false,sys.getUserSessionData());

            session.setAttribute("user",data.getLoginName());
			return mapping.findForward("view");
        }
	}

	
    private String validate(Text messages,ActionForm form) {
        UserCreateForm createData = (UserCreateForm) form;
        try{
            if(!Util.checkDate(Integer.parseInt(createData.getBirthDay()),Integer.parseInt(createData.getBirthMonth()),Integer.parseInt(createData.getBirthYear()))) {
                return messages.getText("generic.user.invalidbirthdate");
            }
        }catch(Exception e) {
            return messages.getText("generic.user.invalidbirthdate");
        }
        if (Util.empty(createData.getFirstName())) {
            return messages.getText("generic.user.userdata.firstname.isempty");
        }
        if (Util.empty(createData.getLastName())) {
            return messages.getText("generic.user.userdata.lastname.isempty");
        }
        if (Util.empty(createData.getLanguage())) {
            return messages.getText("generic.user.userdata.language.isempty");
        }
        if(Integer.parseInt(createData.getExpiryType()) == UserData.WITH_EXPIRY) {
            try{
                if(!Util.checkDate(Integer.parseInt(createData.getUserExpiryDay()),Integer.parseInt(createData.getUserExpiryMonth()),Integer.parseInt(createData.getUserExpiryYear()))) {
                    return messages.getText("generic.user.invalidexpiry");
                }
            }catch(Exception e) {
                return messages.getText("generic.user.invalidexpiry");
            }
        }
        return null;
    }

    public ActionForward cancel(HttpSession session,ActionMapping mapping, ActionForm form) {
        session.setAttribute("user",((UserCreateForm)form).getLoginname());
        return mapping.findForward("view");
    }

}