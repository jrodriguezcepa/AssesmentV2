package assesment.persistence.util;

import org.apache.poi.hssf.util.HSSFColor;

public class CellXLS {

	
	private HSSFColor color;
	private String value;
	
	public CellXLS(HSSFColor color,String v){
		this.color=color;
		this.value=v;
	}
	
	public HSSFColor getColor() {
		return color;
	}
	public void setColor(HSSFColor color) {
		this.color = color;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static CellXLS getCell(String m){
		CellXLS cel=new CellXLS(
					new HSSFColor(){ 
						@Override
						public short getIndex() {
							// TODO Auto-generated method stub
							return HSSFColor.BLACK.index;
						}
					}, m);
		
		return cel;
	}
	
	public static CellXLS getCell(final HSSFColor color, String m){
		CellXLS cel=new CellXLS(color, m);
		
		return cel;
	}
	
}
