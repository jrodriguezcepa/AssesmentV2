package assesment.communication.administration;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class AbitabRegistry implements Serializable{

	public static String EMAIL_ABITAB_PARA_ENVIO = "gestion.automatica@abitab.com.uy"; // gbenaderet@cepasafedrive.com
	
	//public static String EMAIL_CEPA_PARA_ENVIAR_A_ABITAB = "abitab@cepasafedrive.com";
	//public static String EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD = "abitab2010";
	//public static String HOST_SMTP = "smtp.cepasafedrive.com";
	
	public static final String FROM_NAME = "CEPA - Driver Assessment";
	/**
	 * Email alternativo GMAIL
	 */
	public static String EMAIL_CEPA_PARA_ENVIAR_A_ABITAB = "abitab@cepaebtw.com";
	public static String EMAIL_CEPA_PARA_ENVIAR_A_ABITAB_PASSWORD = "E99688";
	public static String HOST_SMTP = "smtp.gmail.com";

	
	/**
	 * Direcotrio donde se almacenan los archivos enviados a abitab
	 */
	public static String DIR_ABITAB_SENDED = "/abitab/sended/";
	
	/**
	 * Directorio donde se almacenan los archivos recibidos de abitab
	 * 
	 */
	public static String DIR_ABITAB_RECEIVED = "/abitab/received/";
	
	
	public static String TIPO_DE_GESTION = "C";
	public static String EMPRESA = "CEP"; // "XXX";
	public static int NRO_EMPRESA = 401; // 401;
	public static int SUB_EMPRESA = 637; // 637;
	public static int TIPO_DE_DOCUMENTO_FACUTRA = 1;
	public static int TIPO_MONEDA_PESOS = 1; 
	public static int CONTROL_CUOTAS_CONTROLA = 1;
	public static int CONTROL_CUOTAS_NO_CONTROLA = 2;
	public static int IMPORTE = 200;
	
	public static String CONCEPTO1 = "";
	public static String CONCEPTO2 = "";
	public static String CONCEPTO3 = "";
	public static String CONCEPTO4 = "";
	
	
	private String tipoDeGestion; //Alfanumérico, siempre es "c"
	private String empresa; //	Alfanumérico
	private int nroEmpresa; //	Numérico
	private int subEmpresa; // Numerico
	
	private int lote; //Numérico , se tiene que leer ya sea de BD o de un archivo de configuracion
						// que nos indique cual fue el utlimo "numero de email" (lote) enviado.
	
	private String identificacion;	// Cedula o id del cliente Alfanumérico
	private String nombre;	// nombre del cliente, Alfanumérico
	private String apellido;  // apellido del cliente,	Alfanumérico 
	
	/**
	 * En nroDocumento vamos a poner Codigo Web -> Md5(identificacion) tomando solo 6 caracteres de dicho md5
	 */
	private String nroDocumento;  // cedula o id del cliente, Alfanumérico 
	
	
	private int tipoDeDocumento; // 	Numérico
	private Date fechaDeVencimiento; //	Fecha
	private Date fechaCorte; //	Fecha
	private Date fechaDeEmision; // puede ser la fecha en que termino el assessment, Fecha
	private double importe; //	Numérico
	private double importeTotal; // puede omitirse si es igual al importe,	Numérico
	private double importeMinimo; // puede omitirse si es igual al importe,	Numérico
	private int tipoMoneda; //	Numérico
	
	private double bonificacion; //Numérico, porcentaje Formato: 000,00
	private double descuento; //Numérico, porcentaje Formato: 000,00
	private int diasGracia; //	Numérico, Formato: 000
	private double mora; //	Numérico, Formato: 000,00
	private double cotizacion; //Numérico, Formato: 000,00
	private int cuota;	//	Numérico
	private String remitente; // Alfanumérico
	private String ruc; //	Alfanumérico
	private String razonSocial; //	Alfanumérico
	private String direccion; // Alfanumérico
	private String codigoPostal; //	Alfanumérico
	
	private String concepto1; // Alfanumérico, largo 40 caracteres max
	private String concepto2; // Alfanumérico, largo 40 caracteres maximo
	private String concepto3; // Alfanumérico, largo 40 caracteres maximo
	private String concepto4; // Alfanumérico, largo 40 caracteres maximo
	private String observacion1; //	Alfanumérico, , largo 40 caracteres maximo
	private String observacion2; //	Alfanumérico, , largo 40 caracteres maximo
	private int controlCuotas; //Numérico,. 1 controla, 2 no controla
	/**
	 * @return the tipoDeGestion
	 */
	public String getTipoDeGestion() {
		return tipoDeGestion;
	}
	/**
	 * @param tipoDeGestion the tipoDeGestion to set
	 */
	public void setTipoDeGestion(String tipoDeGestion) {
		this.tipoDeGestion = tipoDeGestion;
	}
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the nroEmpresa
	 */
	public int getNroEmpresa() {
		return nroEmpresa;
	}
	/**
	 * @param nroEmpresa the nroEmpresa to set
	 */
	public void setNroEmpresa(int nroEmpresa) {
		this.nroEmpresa = nroEmpresa;
	}
	/**
	 * @return the subEmpresa
	 */
	public int getSubEmpresa() {
		return subEmpresa;
	}
	/**
	 * @param subEmpresa the subEmpresa to set
	 */
	public void setSubEmpresa(int subEmpresa) {
		this.subEmpresa = subEmpresa;
	}
	/**
	 * @return the lote
	 */
	public int getLote() {
		return lote;
	}
	/**
	 * @param lote the lote to set
	 */
	public void setLote(int lote) {
		this.lote = lote;
	}
	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}
	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}
	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	/**
	 * @return the nroDocumento
	 */
	public String getNroDocumento() {
		return nroDocumento;
	}
	/**
	 * @param nroDocumento the nroDocumento to set
	 */
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	/**
	 * @return the tipoDeDocumento
	 */
	public int getTipoDeDocumento() {
		return tipoDeDocumento;
	}
	/**
	 * @param tipoDeDocumento the tipoDeDocumento to set
	 */
	public void setTipoDeDocumento(int tipoDeDocumento) {
		this.tipoDeDocumento = tipoDeDocumento;
	}
	/**
	 * @return the fechaDeVencimiento
	 */
	public Date getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}
	/**
	 * @param fechaDeVencimiento the fechaDeVencimiento to set
	 */
	public void setFechaDeVencimiento(Date fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}
	/**
	 * @return the fechaCorte
	 */
	public Date getFechaCorte() {
		return fechaCorte;
	}
	/**
	 * @param fechaCorte the fechaCorte to set
	 */
	public void setFechaCorte(Date fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	/**
	 * @return the fechaDeEmision
	 */
	public Date getFechaDeEmision() {
		return fechaDeEmision;
	}
	/**
	 * @param fechaDeEmision the fechaDeEmision to set
	 */
	public void setFechaDeEmision(Date fechaDeEmision) {
		this.fechaDeEmision = fechaDeEmision;
	}
	/**
	 * @return the importe
	 */
	public double getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(double importe) {
		this.importe = importe;
	}
	/**
	 * @return the importeTotal
	 */
	public double getImporteTotal() {
		return importeTotal;
	}
	/**
	 * @param importeTotal the importeTotal to set
	 */
	public void setImporteTotal(double importeTotal) {
		this.importeTotal = importeTotal;
	}
	/**
	 * @return the importeMinimo
	 */
	public double getImporteMinimo() {
		return importeMinimo;
	}
	/**
	 * @param importeMinimo the importeMinimo to set
	 */
	public void setImporteMinimo(double importeMinimo) {
		this.importeMinimo = importeMinimo;
	}
	/**
	 * @return the tipoMoneda
	 */
	public int getTipoMoneda() {
		return tipoMoneda;
	}
	/**
	 * @param tipoMoneda the tipoMoneda to set
	 */
	public void setTipoMoneda(int tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	/**
	 * @return the bonificacion
	 */
	public double getBonificacion() {
		return bonificacion;
	}
	/**
	 * @param bonificacion the bonificacion to set
	 */
	public void setBonificacion(double bonificacion) {
		this.bonificacion = bonificacion;
	}
	/**
	 * @return the descuento
	 */
	public double getDescuento() {
		return descuento;
	}
	/**
	 * @param descuento the descuento to set
	 */
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	/**
	 * @return the diasGracia
	 */
	public int getDiasGracia() {
		return diasGracia;
	}
	/**
	 * @param diasGracia the diasGracia to set
	 */
	public void setDiasGracia(int diasGracia) {
		this.diasGracia = diasGracia;
	}
	/**
	 * @return the mora
	 */
	public double getMora() {
		return mora;
	}
	/**
	 * @param mora the mora to set
	 */
	public void setMora(double mora) {
		this.mora = mora;
	}
	/**
	 * @return the cotizacion
	 */
	public double getCotizacion() {
		return cotizacion;
	}
	/**
	 * @param cotizacion the cotizacion to set
	 */
	public void setCotizacion(double cotizacion) {
		this.cotizacion = cotizacion;
	}
	/**
	 * @return the cuota
	 */
	public int getCuota() {
		return cuota;
	}
	/**
	 * @param cuota the cuota to set
	 */
	public void setCuota(int cuota) {
		this.cuota = cuota;
	}
	/**
	 * @return the remitente
	 */
	public String getRemitente() {
		return remitente;
	}
	/**
	 * @param remitente the remitente to set
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	/**
	 * @return the rUC
	 */
	public String getRuc() {
		return this.ruc;
	}
	/**
	 * @param ruc the rUC to set
	 */
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/**
	 * @return the concepto1
	 */
	public String getConcepto1() {
		return concepto1;
	}
	/**
	 * @param concepto1 the concepto1 to set
	 */
	public void setConcepto1(String concepto1) {
		this.concepto1 = concepto1;
	}
	/**
	 * @return the concepto2
	 */
	public String getConcepto2() {
		return concepto2;
	}
	/**
	 * @param concepto2 the concepto2 to set
	 */
	public void setConcepto2(String concepto2) {
		this.concepto2 = concepto2;
	}
	/**
	 * @return the concepto3
	 */
	public String getConcepto3() {
		return concepto3;
	}
	/**
	 * @param concepto3 the concepto3 to set
	 */
	public void setConcepto3(String concepto3) {
		this.concepto3 = concepto3;
	}
	/**
	 * @return the concepto4
	 */
	public String getConcepto4() {
		return concepto4;
	}
	/**
	 * @param concepto4 the concepto4 to set
	 */
	public void setConcepto4(String concepto4) {
		this.concepto4 = concepto4;
	}
	/**
	 * @return the observacion1
	 */
	public String getObservacion1() {
		return observacion1;
	}
	/**
	 * @param observacion1 the observacion1 to set
	 */
	public void setObservacion1(String observacion1) {
		this.observacion1 = observacion1;
	}
	/**
	 * @return the observacion2
	 */
	public String getObservacion2() {
		return observacion2;
	}
	/**
	 * @param observacion2 the observacion2 to set
	 */
	public void setObservacion2(String observacion2) {
		this.observacion2 = observacion2;
	}
	/**
	 * @return the controlCuotas
	 */
	public int getControlCuotas() {
		return controlCuotas;
	}
	/**
	 * @param controlCuotas the controlCuotas to set
	 */
	public void setControlCuotas(int controlCuotas) {
		this.controlCuotas = controlCuotas;
	}
	
	/**
	 * 
	 * @param tipoDeGestion
	 * @param empresa
	 * @param nroEmpresa
	 * @param subEmpresa
	 * @param lote
	 * @param identificacion
	 * @param nombre
	 * @param apellido
	 * @param nroDocumento
	 * @param tipoDeDocumento
	 * @param fechaDeVencimiento
	 * @param fechaCorte
	 * @param fechaDeEmision
	 * @param importe
	 * @param importeTotal
	 * @param importeMinimo
	 * @param tipoMoneda
	 * @param bonificacion
	 * @param descuento
	 * @param diasGracia
	 * @param mora
	 * @param cotizacion
	 * @param cuota
	 * @param remitente
	 * @param ruc
	 * @param razonSocial
	 * @param direccion
	 * @param codigoPostal
	 * @param concepto1
	 * @param concepto2
	 * @param concepto3
	 * @param concepto4
	 * @param observacion1
	 * @param observacion2
	 * @param controlCuotas
	 */
	public AbitabRegistry(String tipoDeGestion, String empresa, int nroEmpresa,
			int subEmpresa, int lote, String identificacion, String nombre,
			String apellido, String nroDocumento, int tipoDeDocumento,
			Date fechaDeVencimiento, Date fechaCorte, Date fechaDeEmision,
			double importe, double importeTotal, double importeMinimo,
			int tipoMoneda, double bonificacion, double descuento,
			int diasGracia, double mora, double cotizacion, int cuota,
			String remitente, String ruc, String razonSocial, String direccion,
			String codigoPostal, String concepto1, String concepto2,
			String concepto3, String concepto4, String observacion1,
			String observacion2, int controlCuotas) {
		super();
		this.tipoDeGestion = tipoDeGestion;
		this.empresa = empresa;
		this.nroEmpresa = nroEmpresa;
		this.subEmpresa = subEmpresa;
		this.lote = lote;
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nroDocumento = nroDocumento;
		this.tipoDeDocumento = tipoDeDocumento;
		this.fechaDeVencimiento = fechaDeVencimiento;
		this.fechaCorte = fechaCorte;
		this.fechaDeEmision = fechaDeEmision;
		this.importe = importe;
		this.importeTotal = importeTotal;
		this.importeMinimo = importeMinimo;
		this.tipoMoneda = tipoMoneda;
		this.bonificacion = bonificacion;
		this.descuento = descuento;
		this.diasGracia = diasGracia;
		this.mora = mora;
		this.cotizacion = cotizacion;
		this.cuota = cuota;
		this.remitente = remitente;
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.concepto1 = concepto1;
		this.concepto2 = concepto2;
		this.concepto3 = concepto3;
		this.concepto4 = concepto4;
		this.observacion1 = observacion1;
		this.observacion2 = observacion2;
		this.controlCuotas = controlCuotas;
	}
	
	/**
	 * 
	 */
	public AbitabRegistry() {
		super();
	}

	/**
	 * Genera la linea de todos los campos separados por |
	 * @return
	 */
	public String toLine(){
		
		StringBuffer registro = new StringBuffer();
		registro.append( tipoDeGestion+"|");
		registro.append( empresa+"|");
		registro.append( nroEmpresa+"|");
		registro.append( subEmpresa+"|");
		registro.append( lote+"|");
		registro.append( ((identificacion == null || identificacion.trim() == "")?" ":identificacion.trim()) + "|");
		registro.append( replaceAcentosyComas(nombre)+"|");
		registro.append( replaceAcentosyComas(apellido)+"|");
		registro.append( ((nroDocumento == null || nroDocumento.trim() == "")?" ":nroDocumento.trim()) + "|");
		registro.append( tipoDeDocumento+"|");
		registro.append( convertDateToString(fechaDeVencimiento)+"|");
		registro.append( convertDateToString(fechaCorte)+"|");
		registro.append( convertDateToString(fechaDeEmision)+"|");		
		registro.append( convertNumberToString(importe,2,false) + "|");
		registro.append( convertNumberToString(importeTotal,2,false) + "|");
		registro.append( convertNumberToString(importeMinimo,2,false) + "|");		
		registro.append( tipoMoneda + "|"); 
		registro.append( convertNumberToString(bonificacion,2,true) + "|");
		registro.append( convertNumberToString(descuento,2,true)  + "|");		
		registro.append( ((diasGracia == 0)?" ":String.valueOf(diasGracia).trim()) +"|");		
		registro.append( convertNumberToString(mora,2,true) + "|");
		registro.append( convertNumberToString(cotizacion,2,true) + "|");		
		registro.append( ((cuota == 0)?" ":String.valueOf(cuota)) + "|");
		registro.append( ((remitente == null || remitente.trim() == "")?" ":remitente.trim()) + "|");
		registro.append( ((ruc == null || ruc.trim() == "") ? " " : ruc.trim()) +"|");
		registro.append( ((razonSocial == null || razonSocial.trim() == "")?" ":razonSocial.trim()) +"|");
		registro.append( ((direccion == null || direccion.trim() == "")? " ":direccion.trim()) +"|");
		registro.append( ((codigoPostal == null || codigoPostal.trim() == "")?" ":codigoPostal.trim()) +"|");
		registro.append( ((concepto1 == null || concepto1.trim() == "")?" ":concepto1.trim()) +"|");
		registro.append( ((concepto2 == null || concepto2.trim() == "")?" ":concepto2.trim()) +"|");
		registro.append( ((concepto3 == null || concepto3.trim() == "")?" ":concepto3.trim()) +"|");
		registro.append( ((concepto4 == null || concepto4.trim() == "")?" ":concepto4.trim()) +"|");
		registro.append( ((observacion1 == null || observacion1.trim() == "")?" ":observacion1.trim()) +"|");
		registro.append( ((observacion2 == null || observacion2.trim() == "")?" ":observacion2.trim()) +"|");
		registro.append( String.valueOf(controlCuotas).trim());
		
		return registro.toString();
	}
	
	/**
	 * Remplaza acentos y comas, porque no son soportados por Abitab.
	 * @param campo
	 * @return
	 */
	public String replaceAcentosyComas(String campo){
		String resultado = campo.replace("á", "a").
				replace("é","e").
				replace("í","i").
				replace("ó","o").
				replace("ú","u").
				replace("Á","A").
				replace("É","E").
				replace("Í","I").
				replace("Ó","O").
				replace("Ú","U").
				replace(","," ");
		return resultado;
	}
	
	/**
	 * Convierte un Date a string dd/mm/aa
	 * @param d
	 * @return
	 */
	public static String convertDateToString(Date d){
		if (d == null) return " ";
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		
		String year = String.valueOf(c.get(Calendar.YEAR)).substring(2); // Solo los ultimos 2 caracteres 2010 -> 10
		
		String monthStr = "";
		int month = c.get(Calendar.MONTH)+1;
		if (month >= 1 && month <= 9)
			monthStr = "0".concat(String.valueOf(month));
		else
			monthStr = String.valueOf(month); 
		
		String dayStr = "";
		int day = c.get(Calendar.DATE);
		if (day >= 1 && day <= 9)
			dayStr = "0".concat(String.valueOf(day));
		else
			dayStr = String.valueOf(day);
		
		return dayStr + "/" + monthStr + "/" + year;
	}
	
	/**
	 * Convierte un numero en string con decimales.
	 * @param number
	 * @param cantidadDecimales
	 * @return
	 */
	public static String convertNumberToString(Double number, int cantidadDecimales,boolean leftCero){
		
		if (number == null || number == 0) return " "; // espacio en blanco.
		
		String result = " ";
		
		// Cantidad de Decimales
		if (cantidadDecimales == 2){
			result = String.valueOf(Math.round(number*100));
		}else if (cantidadDecimales == 0){
			result = String.valueOf(Math.round(number));
		}
		
		// Cero a la izquierda
		if (leftCero){
			result = "0".concat(result);
		}
		
		return result;
		
		
	}
	
}
