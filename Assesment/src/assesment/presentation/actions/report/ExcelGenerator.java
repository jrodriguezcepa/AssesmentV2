package assesment.presentation.actions.report;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

/**
 * 
 * 
 * @author fcarriquiry
 * 
 */
public class ExcelGenerator {

	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,ArrayList<ArrayList<String>> rows2,String sheetName2,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheet(0,sheetName, rows, book);
		addSheet(1,sheetName2, rows2, book);

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,ArrayList<ArrayList<String>> rows2,String sheetName2,ArrayList<ArrayList<String>> rows3,String sheetName3,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheet(0,sheetName, rows, book);
		addSheet(1,sheetName2, rows2, book);
		addSheet(2,sheetName3, rows3, book);

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
	
	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,ArrayList<ArrayList<String>> rows2,String sheetName2,ArrayList<ArrayList<String>> rows3,String sheetName3,ArrayList<ArrayList<String>> rows4,String sheetName4,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheet(0,sheetName, rows, book);
		addSheet(1,sheetName2, rows2, book);
		addSheet(2,sheetName3, rows3, book);
		addSheet(3,sheetName4, rows4, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
	

	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,ArrayList<ArrayList<String>> rows2,String sheetName2,ArrayList<ArrayList<String>> rows3,String sheetName3,ArrayList<ArrayList<String>> rows4,String sheetName4,ArrayList<ArrayList<String>> rows5,String sheetName5,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheet(0,sheetName, rows, book);
		addSheet(1,sheetName2, rows2, book);
		addSheet(2,sheetName3, rows3, book);
		addSheet(3,sheetName4, rows4, book);
		addSheet(4,sheetName5, rows5, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,ArrayList<ArrayList<String>> rows2,String sheetName2,ArrayList<ArrayList<String>> rows3,String sheetName3,ArrayList<ArrayList<String>> rows4,String sheetName4,ArrayList<ArrayList<String>> rows5,String sheetName5,ArrayList<ArrayList<String>> rows6,String sheetName6,ArrayList<ArrayList<String>> rows7,String sheetName7,ArrayList<ArrayList<String>> rows8,String sheetName8,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheet(0,sheetName, rows, book);
		addSheet(1,sheetName2, rows2, book);
		addSheet(2,sheetName3, rows3, book);
		addSheet(3,sheetName4, rows4, book);
		addSheet(4,sheetName5, rows5, book);
		addSheet(5,sheetName6, rows6, book);
		addSheet(6,sheetName7, rows7, book);
		addSheet(7,sheetName8, rows8, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
}

	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,OutputStream output){
		   
			HSSFWorkbook book = new HSSFWorkbook();

			addSheet(0,sheetName, rows, book);

	        try {
	        	book.write(output);
	            output.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
   
	public static void addSheet(int sheetIndex,String sheetName,ArrayList<ArrayList<String>> rows,HSSFWorkbook book){
		if(sheetName!=null && sheetName.length()>31){
        	sheetName=sheetName.substring(0, 30);
        } 
		// Se crea una hoja dentro del libro
        HSSFSheet sheet = book.createSheet(sheetName);
       
        book.setSheetName(sheetIndex,sheetName,HSSFWorkbook.ENCODING_UTF_16);
        // Se crea una fila dentro de la hoja
        int rowIndex=0;
        
        
        for(ArrayList<String> row : rows ){
        	short celIndex=0;
        	HSSFRow rowXls = sheet.createRow(rowIndex++);
        	for(String celValue : row){
		        // Se crea una celda dentro de la fila
		        HSSFCell cell = rowXls.createCell(celIndex++);
		        cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
		        // Se crea el contenido de la celda y se mete en ella.
		        HSSFRichTextString texto = new HSSFRichTextString(celValue);
		        cell.setCellValue(texto);
		        
        	}
        }
	}
   
	public static void generatorObjectXLS(ArrayList<ArrayList<Object>> rows,String sheetName,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addObjectSheet(0,sheetName, rows, book);

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void addObjectSheet(int sheetIndex,String sheetName,ArrayList<ArrayList<Object>> rows,HSSFWorkbook book){
		if(sheetName!=null && sheetName.length()>31){
        	sheetName=sheetName.substring(0, 30);
        } 
		// Se crea una hoja dentro del libro
        HSSFSheet sheet = book.createSheet(sheetName);
        HSSFCellStyle dateStyle = book.createCellStyle();
        

        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        
        book.setSheetName(sheetIndex,sheetName,HSSFWorkbook.ENCODING_UTF_16);
        // Se crea una fila dentro de la hoja
        int rowIndex=0;
        
        
        for(ArrayList<Object> row : rows ){
        	short celIndex=0;
        	HSSFRow rowXls = sheet.createRow(rowIndex++);
        	for(Object celValue : row){
		        HSSFCell cell = rowXls.createCell(celIndex++);
		        cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
		        if(celValue != null) { 
	        		if(celValue instanceof Date) {
	        		    cell.setCellValue((Date)celValue);
	        		    cell.setCellStyle(dateStyle);
	        		}else if(celValue instanceof Object[]) {
	        			Object[] v = (Object[])celValue; 
	        			if(v.length == 4) {
					        HSSFRichTextString texto = new HSSFRichTextString(String.valueOf(v[0]));
					        HSSFCellStyle style = book.createCellStyle();
							style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
							style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							//style.setFillForegroundColor(Short.parseShort((String)v[3]));
					        cell.setCellStyle(style);
					        cell.setCellValue(texto);
					        short v1 = Short.valueOf((String)v[1]);
					        short v2 = Short.valueOf((String)v[2]);
	        				sheet.addMergedRegion(new Region(0,v1,0,v2));
	        				for(short j = v1; j < v2; j++)
	        					celIndex++;
	        			}else {
		        			if(v[0] instanceof Date) {
			        		    cell.setCellValue((Date)v[0]);
						        HSSFCellStyle style = book.createCellStyle();
						        //style.setFillForegroundColor(((Short)v[1]).shortValue());
								style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
								style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
						        cell.setCellStyle(style);
		        			}else {
		        				HSSFRichTextString texto = new HSSFRichTextString(String.valueOf(v[0]));
		        				cell.setCellValue(texto);
						        HSSFCellStyle style = book.createCellStyle();
						        //style.setFillForegroundColor(((Short)v[1]).shortValue());
								//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						        cell.setCellStyle(style);
		        			}
	        			}					 

						/*HSSFFont font = book.createFont();
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						font.setColor(((Short)v[2]).shortValue());
						style.setFont(font);*/

	        		}else {
				        // Se crea una celda dentro de la fila
				        // Se crea el contenido de la celda y se mete en ella.
				        HSSFRichTextString texto = new HSSFRichTextString(String.valueOf(celValue));
				        cell.setCellValue(texto);
	        		}		        
        		}		        
        	}
        }
	}

	public static void generatorObjectXLS(ArrayList<ArrayList<Object>> rows,String sheetName,ArrayList<ArrayList<Object>> rows2,String sheetName2,ArrayList<ArrayList<Object>> rows3,String sheetName3,ArrayList<ArrayList<Object>> rows4,String sheetName4,ArrayList<ArrayList<Object>> rows5,String sheetName5,ArrayList<ArrayList<Object>> rows6,String sheetName6,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addObjectSheet(0,sheetName, rows, book);
		addObjectSheet(1,sheetName2, rows2, book);
		addObjectSheet(2,sheetName3, rows3, book);
		addObjectSheet(3,sheetName4, rows4, book);
		addObjectSheet(4,sheetName5, rows5, book);
		addObjectSheet(5,sheetName6, rows6, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void generatorObjectXLS(ArrayList<ArrayList<Object>> rows,String sheetName,ArrayList<ArrayList<Object>> rows2,
			String sheetName2,ArrayList<ArrayList<Object>> rows3,String sheetName3,ArrayList<ArrayList<Object>> rows4,String sheetName4,
			ArrayList<ArrayList<Object>> rows5,String sheetName5,ArrayList<ArrayList<Object>> rows6,String sheetName6,
			ArrayList<ArrayList<Object>> rows7,String sheetName7,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addObjectSheet(0,sheetName, rows, book);
		addObjectSheet(1,sheetName2, rows2, book);
		addObjectSheet(2,sheetName3, rows3, book);
		addObjectSheet(3,sheetName4, rows4, book);
		addObjectSheet(4,sheetName5, rows5, book);
		addObjectSheet(5,sheetName6, rows6, book);
		addObjectSheet(6,sheetName7, rows7, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void generatorObjectXLS(ArrayList<ArrayList<Object>> rows,String sheetName,
			ArrayList<ArrayList<Object>> rows2,String sheetName2,ArrayList<ArrayList<Object>> rows3,String sheetName3,
			ArrayList<ArrayList<Object>> rows4,String sheetName4,OutputStream output){

		HSSFWorkbook book = new HSSFWorkbook();

		addObjectSheet(0,sheetName, rows, book);
		addObjectSheet(1,sheetName2, rows2, book);
		addObjectSheet(2,sheetName3, rows3, book);
		addObjectSheet(3,sheetName4, rows4, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void generatorXLS(ArrayList<ArrayList<Object>> rows, String sheetName,
			ArrayList<ArrayList<Object>> rows2, String sheetName2, ArrayList<ArrayList<Object>> rows3,
			String sheetName3, ServletOutputStream outputstream) {

		HSSFWorkbook book = new HSSFWorkbook();

		addObjectSheet(0,sheetName, rows, book);
		addObjectSheet(1,sheetName2, rows2, book);
		addObjectSheet(2,sheetName3, rows3, book);
		

        try {
        	book.write(outputstream);
        	outputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void generatorXLS(ArrayList<ArrayList<Object>> rows, String sheetName,
			ArrayList<ArrayList<Object>> rows2, String sheetName2, ServletOutputStream outputstream) {

		HSSFWorkbook book = new HSSFWorkbook();

		addObjectSheet(0,sheetName, rows, book);
		addObjectSheet(1,sheetName2, rows2, book);
		
        try {
        	book.write(outputstream);
        	outputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
 }