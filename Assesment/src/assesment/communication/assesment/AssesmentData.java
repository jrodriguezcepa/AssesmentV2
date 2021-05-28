/**
 * CEPA
 * Assesment
 */
package assesment.communication.assesment;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import assesment.communication.module.ModuleData;
import assesment.communication.question.QuestionData;
import assesment.communication.util.MailSender;
import assesment.persistence.util.Util;

public class AssesmentData extends AssesmentAttributes {

	//public static final String FLASH_PATH = "C://flash";
	public static final String FLASH_PATH = 
		(MailSender.isProductionServer())?"/home/flash":"C://flash";
	
	public static final String JASPER_PATH = 
			(MailSender.isProductionServer())?"/home/flash/jasper/":"C://flash/jasper/";
	
	public static final String REPORT_PATH = 
			(MailSender.isProductionServer())?"/home/flash/reports/":"C://flash//reports//";
	
	public static final String RSMM_PATH = 
			(MailSender.isProductionServer())?"/home/flash/rsmm/":"C://flash//rsmm//";
	
	public static final String WS_PATH = 
			(MailSender.isProductionServer())?"/home/flash/ws/":"C://flash//ws//";

	public static final int SEMM = 9;

    public static final int JJ = 11;
    public static final int JJ_2 = 57;
    public static final int JJ_3 = 70;
    public static final int JJ_4 = 85;
    public static final int JJ_5 = 115;
    public static final int JJ_6 = 134;
    public static final int JJ_7 = 164;
    public static final int JJ_8 = 209;

    public static final int MONSANTO_NEW_HIRE = 14;
	public static final int MONSANTO_EMEA = 15;
	public static final int MONSANTO_BRAZIL = 16;
	public static final int MONSANTO_ARGENTINA = 21;
    public static final int NEW_HIRE = 20;
	public static final int MICHELIN = 25;
	public static final int MICHELIN_2 = 26;
	public static final int FACEBOOK = 27;
	public static final int ABITAB = 30;
	public static final int CEPA_DRIVING_SYSTEM = 31;

	public static final int MICHELIN_3 = 34;
	public static final int MICHELIN_4 = 37;
	public static final int MICHELIN_5 = 45;
	public static final int MICHELIN_6 = 59;
	public static final int MICHELIN_7 = 76;
	public static final int MICHELIN_8 = 78;
	public static final int MICHELIN_9 = 79;
	public static final int SCHERING = 35;
	public static final int GM = 36;
    public static final int JANSSEN = 38;
    public static final int ALLERGAN_1 = 40;
    public static final int ALLERGAN_2 = 42;
    public static final int JANSSEN_2 = 47;
	public static final int MONSANTO_LAN = 49;
	public static final int MAMUT_ANDINO = 52;
	public static final int PEPSICO = 54;
	public static final int BAYER = 55;
	public static final int NALCO = 56;
    public static final int DNB = 58;
    
    public static final int ANGLO = 60;
    public static final int ANGLO_3 = 64;
    
    public static final int TRANSMETA = 62;
	public static final int PEPSICO_CANDIDATOS = 63;
	
	public static final int DOW = 65;
	public static final int ACU = 66;
	public static final int IMESEVI = 67;

    public static final int ANTP_MEXICO = 72;
    public static final int NYCOMED = 80;

    public static final int PEPSICO_CEPA_SYSTEM = 82;

    public static final int ASTRAZENECA = 71;
    public static final int ASTRAZENECA_2 = 83;
    public static final int ASTRAZENECA_2013 = 114;

    public static final int BIMBO_MEXICO = 86;

	public static final int UNILEVER = 87;
	public static final int SANOFI_BRASIL = 92;

	public static final int CHEMTURA = 93;
	public static final int DEMO_MOVILES = 111;

	public static final int DA_NUEVO = 117;
	public static final int FIA_ASSESSMENT = 121;


	public static final int PLATERO = 123;
	public static final int COKE_DEUTSCHLAND = 135;
	public static final int MONSATO_DATOS_PERSONALES = 137;

	public static final int SURVEY = 151;

	public static final int BAYER_MEXICO = 168;
	public static final int REPORTE_ACCIDENTE = 171;
	public static final int COACHING_TELEMATICS_4 = 177;
	public static final int COACHING_TELEMATICS_3 = 178;
	public static final int COACHING_TELEMATICS_2 = 179;
	public static final int COACHING_TELEMATICS_1 = 180;
	public static final int COACHING_TELEMATICS_5 = 181;

	public static final int MDP_2015 = 184;
	public static final int DOW_TOJ = 189;

	public static final int NEW_HIRE_V2 = 192;

	public static final int PHILLIP_MORRIS_MX_2015 = 196;
	public static final int CIEMSA_2015 = 197;
	public static final int NUEVOS_EMPLEADOS_PM = 203;


	public static final int CEPA_SYSTEM_TRACTOCAMIONES_RICA = 207;
	
	public static final int TIMAC_BRASIL = 213;
	public static final int TIMAC_BRASIL_EBTWPLUS = 485;

	public static final int BAYER_MEXICO_NUEVOS_EMPLEADOS = 216;
	public static final int MONSANTO_SURVEY_BR_2016 = 217;

	public static final int BAYERMX_ELEARNINGPACK_V2 = 226;
	public static final int BAYERMX_ELEARNINGPACK_V2_REPETICION = 227;

	public static final int BAYERMX_ELEARNINGPACK_VERSION2 = 237;
	public static final int BAYERMX_ELEARNINGPACK_VERSION2_REPETICION = 238;
	public static final int LIMAGRAIN_ELEARNINGPACK = 246;

	public static final int BAYERMX_ELEARNINGPACK_MONITORES = 258;
	public static final int BAYERMX_ELEARNINGPACK_MONITORES_REPETICION = 259;

	public static final int BAYERMX_RIESGO_TRASERO = 268;
	public static final int BAYER_MEXICO_VELOCIDAD = 284;
	public static final int BAYER_MEXICO_INTERSECCIONES = 314;
	public static final int BAYER_MEXICO_ENTENDERCRC = 323;
	
	public static final int FRESENIUS = 324;

	public static final int MINCIVIL_COLOMBIA = 326;
    public static final int ANTP_MEXICO_RSMM = 331;

    public static final Integer ROCHE_MX_ELEARNINGPACK = 278; 
    public static final Integer ROCHE_MX_INTERSECCIONES = 338; 
    public static final Integer ROCHE_MX_RIESGOTRASERO= 336; 
    public static final Integer ROCHE_MX_VELOCIDAD = 339; 
    
	public static final Integer ANTP_MEXICO_PNSV = 344;
	public static final Integer ANTP_MEXICO_PNSV_2 = 470;

	public static final Integer CEPA_BR_LOGISTICA = 345;
	public static final Integer JJ_RIESGO_TRASERO = 352;

	public static final Integer SANOFI_EBTW = 363;

    public static final int TRANSPORTES_NIQUINI = 431;

	public static final int SAFETY_SURVEY = 452;
	
	public static final int SANOFI_MEXICO_EBTW = 487;
	public static final int SANOFI_MEXICO_DA = 488;
	
	public static final int HOJA_RUTA_2019 = 501;

	public static final int MDP_CHARLA = 1725;
	public static final int MDP_CHARLA_V2 = 1732;

	public static final int UPM_CHARLA = 1729;
	public static final int UPM_CHARLA_V2 = 1730;

	public static final int LUMIN_CHARLA = 1772;

	public static final int GRUPO_MODELO_FOTO = 1586;
	public static final int GRUPO_MODELO_EBTW = 1614;
	public static final int GRUPO_MODELO_CEPATIP = 1646;
	public static final int GRUPO_MODELO_CUESTIONARIO = 1652;

	public static final int SANOFI_BRASIL_EBTW_2019 = 985;
	public static final int SANOFI_BRASIL_DA_2019 = 713;
	
	public static final int TIMAC_BRASIL_EBTW_2020 = 1051;
	public static final int TIMAC_BRASIL_DA_2020 = 1052;

	public static final int COTRAM_EBTWPLUS = 342;

	public static final int FRESENIUS_INTELIGENCIAEMCIONAL = 1089;
	public static final int FRESENIUS_EBTWPLUS = 1090;

	public static final int CCFC = 1100;

	public static final int FRAYLOG_FATIGA = 1148;
	
	public static final int BAYER_WEBINARPLUS = 1609;
	public static final int BAYER_WEBINARRENOVATION = 1610;
	public static final int BAYER_WEBINARAGRO = 1611;
	public static final int BAYER_WEBINARREBOQUE = 1612;
	public static final int BAYER_WEBINARPESADOS = 1620;
	
	public static final int BTWPLUS_WEBINAR = 1149;
	public static final int PESADOS_WEBINAR = 1270;
	public static final int BTWPLUS2_WEBINAR = 1324;
	public static final int HRD_WEBINAR = 1327;
	public static final int WEBINAR_4X4 = 1471;
	public static final int MOTO_WEBINAR = 1804;

	public static final int SAFEFLEET_WEBINARPLUS = 1615;
	public static final int SAFEFLEET_WEBINARPLUS2 = 1616;
	public static final int SAFEFLEET_WEBINARHRD = 1617;
	public static final int SAFEFLEET_WEBINARMONITORS = 1619;

	public static final int SAFEFLEET_LATAM = 1166;
	public static final int SAFEFLEET_MEX = 1167;
	
	public static final int MERCANCIAS_PELIGROSAS = 1130;
	public static final int MERCANCIAS_PELIGROSAS_RENOVACION = 1129;
	
	private static final int VESTAS_DA = 1221;

	public static final int MERCADO_LIVRE_START = 1281;
	public static final int MERCADO_LIBRE_START = 1301;
	
	public static final int MONDELEZ_DA = 1341;
	public static final int MONDELEZ_DA_V2 = 1566;
	public static final int MONDELEZ_FILES = 1379;

	public static final int UPL_NEWHIRE = 1489;

	public static final int BTW2_REFRESH_WEBINAR = 1504;
	public static final int MONITORES_WEBINAR = 1517;
	public static final int TRITREN_WEBINAR = 1644;

	public static final int GUINEZ_INGENIERIA = 1531;
	public static final int LIGHT_VEHICLES_2020 = 1597;

	public static final int MUTUAL_DA = 1613;

	public static final int SAFEFLEET_ACADEMY_KMS = 1383;
	public static final int SAFEFLEET_ACADEMY_EVENTS = 1386;
	public static final int SAFEFLEET_ACADEMY_HISTORY = 1391;
	public static final int SAFEFLEET_ACADEMY_COLLABORATOR = 1631;
	public static final int SAFEFLEET_ACADEMY_DRIVERDETRAN = 1625;
	public static final int SAFEFLEET_ACADEMY_COLLABORATORDETRAN = 1626;

	public static final int ALRIYADAH_INITIALA = 1634;
	public static final int ALRIYADAH_INITIALB = 1635;
	public static final int ALRIYADAH_FINAL = 1636;
	
    public static final int MUTUAL_RSMM = 1703;

    public static final int BAYER_ARGENTINA_DEMO = 1704;
	public static final int ABBOTT_NEWDRIVERS = 1707;
	public static final int ABBEVIE_LATAM = 1712;
	public static final int GDC = 1711;
	public static final int ASTRAZENECA_BRASIL_DRIVERASSESSMENT = 1714;
	public static final int SUMITOMO = 1754;
	public static final int SUMITOMO_4X4 = 1754;

	private static final int BAYER_CONOSUR_DA = 1743;
	private static final int GUINEZ_INGENIERIA_V2 = 1746;

	public static final int BAYERCONOSUR_ACADEMY_HISTORY = 1764;


	public static final int KOF_ASESORES = 1744;
	public static final int KOF_COORDINADORES = 1774;


    private Collection<ModuleData> modules;
	private Integer psiCount = 0;

	private String corporationName;
    
    public AssesmentData() {
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
     * @param psiFeedback 
     * @param yellow2 
     * @param green 
     * @param certificateImage 
     * @param certificate 
     * @param archived 
     * @param terms 
     * @param icon 
     * @param untilApproved 
     * @param attachPDF 
     * @param link 
     */
    public AssesmentData(Integer id, String name, String description, Integer corporationId, Date start, Date end, Collection<ModuleData> modules, Integer status, boolean psitest, boolean elearning, boolean instantFeedback, boolean reportFeedback, boolean errorFeedback, boolean psiFeedback, Integer green, Integer yellow, boolean certificate, String certificateImageES, String certificateImageEN, String certificateImagePT, boolean archived, boolean showEmailWRT, Integer terms, String link_es, String link_en, String link_pt, boolean icon, boolean attachesPDF, boolean attachenPDF, boolean attachptPDF, boolean untilApproved, String link) {
        super(id,name,description,corporationId,start,end,status,psitest,elearning,instantFeedback,reportFeedback,errorFeedback,psiFeedback,green,yellow,certificate,certificateImageES,certificateImageEN,certificateImagePT,archived,showEmailWRT,terms,link_es,link_en,link_pt,icon, attachesPDF, attachenPDF, attachptPDF, untilApproved, link);
        this.modules = modules;
    }

    public Collection<ModuleData> getModules() {
        return modules;
    }

    public void setModules(Collection<ModuleData> modules) {
        this.modules = modules;
    }

    public Iterator getModuleIterator() {
        Collections.sort((List)modules);
        return modules.iterator();
    }

    public int getQuestionCount() {
        int count = 0;
        Iterator it = modules.iterator();
        while(it.hasNext()) {
            count += ((ModuleData)it.next()).getQuestionSize();
        }
        return count;
    }

    public int getTestQuestionCount() {
        int count = 0;
        Iterator it = modules.iterator();
        while(it.hasNext()) {
            Iterator it2 = ((ModuleData)it.next()).getQuestionIterator();
            while(it2.hasNext()) {
            	if(((QuestionData)it2.next()).getTestType().intValue() == QuestionData.TEST_QUESTION) {
            		count++;
            	}
            }
        }
        return count;
    }

    public Integer getPsiCount() {
		return psiCount;
	}

	public void setPsiCount(Integer psiCount) {
		this.psiCount = psiCount;
	}

	public void setAnswered(Integer module,Integer answered) {
		if(module == 0) {
			psiCount = answered;
		}else {
			Iterator it = getModuleIterator();
			while(it.hasNext()) {
				ModuleData moduleData = (ModuleData)it.next();
				if(moduleData.getId().intValue() == module.intValue()) {
					moduleData.setAnswered(answered);
				}
			}
		}
	}

	public ModuleData findModule(Integer module) {
		Iterator it = getModuleIterator();
		while(it.hasNext()) {
			ModuleData moduleData = (ModuleData)it.next();
			if(moduleData.getId().intValue() == module.intValue()) {
				return moduleData;
			}
		}
		return null;
	}

	public static String getFileName(Integer id, String language, String name, int reportType) {
		String fileName = "";
		if(language.equals("en")) {
			fileName = (reportType == 1) ? "result" : "certificate";
		} else {
			fileName = (reportType == 1) ? "resultado" : "certificado";			
		}
		fileName += "_"+replaceChars(name);
		switch(id.intValue()) {
			case AssesmentData.MDP_2015:
				fileName = "MDP_"+fileName;
				break;
		}
		return fileName;
	}

	public static String replaceChars(String text) {
		String value = "";
		text.replaceAll("'", "");
		for(int i = 0; i < text.length(); i++) {
			switch(text.toLowerCase().charAt(i)) {
				case 'á':case 'à': case 'ã':
					value += "a";
					break;
				case 'é': case 'ê':
					value += "e";
					break;
				case 'í':
					value += "i";
					break;
				case 'ó':
					value += "o";
					break;
				case 'ú':
					value += "u";
					break;
				case 'ñ':
					value += "n";
					break;
				case 'ç':
					value += "c";
					break;
				case '-':
					value += "_";
					break;
				case ' ':
					value += "_";
					break;
				default:
					value += String.valueOf(text.charAt(i));
			}
		}
		return value;
	}

	public static boolean isJJ(int assessment) {
		return (assessment == JJ || assessment == JJ_2 || assessment == JJ_3 || assessment == JJ_4 || 
				assessment == JJ_5 || assessment == JJ_6 || assessment == JJ_7 || assessment == JJ_8);
	}

	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}

	public String getCorporationName() {
		return corporationName;
	}

	public boolean isWebinar() {
		 return (id.intValue() == BTWPLUS_WEBINAR || 
				 id.intValue() == BTWPLUS2_WEBINAR || 
				 id.intValue() == PESADOS_WEBINAR || 
				 id.intValue() == HRD_WEBINAR || 
				 id.intValue() == MOTO_WEBINAR || 
				 id.intValue() == WEBINAR_4X4 || 
				 id.intValue() == BTW2_REFRESH_WEBINAR ||
				 id.intValue() == MONITORES_WEBINAR ||
				 id.intValue() == BAYER_WEBINARAGRO ||
				 id.intValue() == BAYER_WEBINARPESADOS ||
				 id.intValue() == BAYER_WEBINARPLUS ||
				 id.intValue() == BAYER_WEBINARREBOQUE ||
				 id.intValue() == BAYER_WEBINARRENOVATION ||
				 id.intValue() == SAFEFLEET_WEBINARHRD ||
				 id.intValue() == SAFEFLEET_WEBINARMONITORS ||
				 id.intValue() == SAFEFLEET_WEBINARPLUS ||
				 id.intValue() == SAFEFLEET_WEBINARPLUS2);
	}

	public boolean isPsi() {
		return (id.intValue() == AssesmentData.MUTUAL_DA ||
				id.intValue() == AssesmentData.GUINEZ_INGENIERIA || 
				id.intValue() == AssesmentData.GUINEZ_INGENIERIA_V2 || 
				id.intValue() == AssesmentData.MONDELEZ_DA || 
				id.intValue() == AssesmentData.MONDELEZ_DA_V2 || 
				id.intValue() == AssesmentData.UPL_NEWHIRE || 
				id.intValue() == AssesmentData.ASTRAZENECA_BRASIL_DRIVERASSESSMENT || 
				id.intValue() == AssesmentData.SUMITOMO || 
				id.intValue() == AssesmentData.SUMITOMO_4X4 || 
				id.intValue() == AssesmentData.TIMAC_BRASIL_DA_2020 ||
     	     	id.intValue() == AssesmentData.ABBOTT_NEWDRIVERS || 
     	     	id.intValue() == AssesmentData.ABBEVIE_LATAM || 
     			id.intValue() == AssesmentData.BAYER_CONOSUR_DA || 
     			id.intValue() == AssesmentData.VESTAS_DA || 
				
				id.intValue() == AssesmentData.MINCIVIL_COLOMBIA || 
				id.intValue() == AssesmentData.CCFC || 
				id.intValue() == AssesmentData.SAFEFLEET_MEX || 
				id.intValue() == AssesmentData.SAFEFLEET_LATAM || 
				 
     			id.intValue() == AssesmentData.LIGHT_VEHICLES_2020 || 
     	     	id.intValue() == AssesmentData.BAYER_ARGENTINA_DEMO
     	     	);
	}
	
}
