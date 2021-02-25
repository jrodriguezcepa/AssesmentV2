package assesment.communication.administration;

import java.util.Calendar;
import java.util.Date;

public class AbitabFile {

	private String codigoEmpresa; // Alfanumérico, 3
	private int lote; // Numérico, 10
	private String identificacion; // Alfanumérico, 13, CI del participante
	private String nroDocumento; // Alfanumérico, 16, CI del participante
	private int moneda; // Numérico, 1 Pesos, 2 Dólares
	private double importePago; // Numérico, 9 enteros, 2 decimales
	private int cuota; // Numérico, 4
	private String fechaDePago; // 	ddmmaa 6
	private int agencia; // Numérico 5
	
	/**
	 * Obitiene la fecha de Pago en formato Date
	 * @return
	 */
	public Date fechaDePagoToDate(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, Integer.valueOf(fechaDePago.substring(0,2)).intValue());
		c.set(Calendar.MONTH, Integer.valueOf(fechaDePago.substring(2,4)).intValue()+1);
		c.set(Calendar.YEAR, 2000 + Integer.valueOf(fechaDePago.substring(4,6)).intValue());
		return c.getTime();
	}

	/**
	 * @return the codigoEmpresa
	 */
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}

	/**
	 * @param codigoEmpresa the codigoEmpresa to set
	 */
	public void setCodigoEmpresa(String codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
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
	 * @return the moneda
	 */
	public int getMoneda() {
		return moneda;
	}

	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}

	/**
	 * @return the importePago
	 */
	public double getImportePago() {
		return importePago;
	}

	/**
	 * @param importePago the importePago to set
	 */
	public void setImportePago(double importePago) {
		this.importePago = importePago/100;
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
	 * @return the fechaDePago
	 */
	public String getFechaDePago() {
		return fechaDePago;
	}

	/**
	 * @param fechaDePago the fechaDePago to set
	 */
	public void setFechaDePago(String fechaDePago) {
		this.fechaDePago = fechaDePago;
	}

	/**
	 * @return the agencia
	 */
	public int getAgencia() {
		return agencia;
	}

	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public AbitabFile(String codigoEmpresa, int lote,
			String identificacion, String nroDocumento, int moneda,
			double importePago, int cuota, String fechaDePago, int agencia) {
		super();
		this.codigoEmpresa = codigoEmpresa;
		this.lote = lote;
		this.identificacion = identificacion;
		this.nroDocumento = nroDocumento;
		this.moneda = moneda;
		this.importePago = importePago;
		this.cuota = cuota;
		this.fechaDePago = fechaDePago;
		this.agencia = agencia;
	}

	public AbitabFile() {
		super();
	}
	
	public String toLine(){
		StringBuffer result = new StringBuffer(); 
		result.append(codigoEmpresa+"|");
		result.append(lote+"|");
		result.append(identificacion+"|");
		result.append(nroDocumento+"|");
		result.append(moneda+"|");
		result.append(importePago+"|");
		result.append(cuota+"|");
		result.append(fechaDePago+"|");
		result.append(agencia);
		return result.toString();
	}

}
