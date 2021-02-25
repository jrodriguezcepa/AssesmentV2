package assesment.persistence.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

	public static void generatorXLS(ArrayList<ArrayList<String>> rows,String sheetName,ArrayList<ArrayList<String>> rows2,String sheetName2,ArrayList<ArrayList<String>> rows3,String sheetName3,ArrayList<ArrayList<String>> rows4,String sheetName4,ArrayList<ArrayList<String>> rows5,String sheetName5,ArrayList<ArrayList<String>> rows6,String sheetName6,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheet(0,sheetName, rows, book);
		addSheet(1,sheetName2, rows2, book);
		addSheet(2,sheetName3, rows3, book);
		addSheet(3,sheetName4, rows4, book);
		addSheet(4,sheetName5, rows5, book);
		addSheet(5,sheetName6, rows6, book);
		

        try {
        	book.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void generatorCellXLSNoStyle(ArrayList<ArrayList<CellXLS>> rows,String sheetName,ArrayList<ArrayList<CellXLS>> rows2,String sheetName2,ArrayList<ArrayList<CellXLS>> rows3,String sheetName3,ArrayList<ArrayList<CellXLS>> rows4,String sheetName4,ArrayList<ArrayList<CellXLS>> rows5,String sheetName5,ArrayList<ArrayList<CellXLS>> rows6,String sheetName6,ArrayList<ArrayList<CellXLS>> rows7,String sheetName7,ArrayList<ArrayList<CellXLS>> rows8,String sheetName8,OutputStream output){
		   
		HSSFWorkbook book = new HSSFWorkbook();

		addSheetCellXLSNoStyle(0,sheetName, rows, book);
		addSheetCellXLSNoStyle(1,sheetName2, rows2, book);
		addSheetCellXLSNoStyle(2,sheetName3, rows3, book);
		addSheetCellXLSNoStyle(3,sheetName4, rows4, book);
		addSheetCellXLSNoStyle(4,sheetName5, rows5, book);
		addSheetCellXLSNoStyle(5,sheetName6, rows6, book);
		addSheetCellXLSNoStyle(6,sheetName7, rows7, book);
		addSheetCellXLSNoStyle(7,sheetName8, rows8, book);
		

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
				        HSSFRichTextString texto = new HSSFRichTextString(String.valueOf(v[0]));
				        cell.setCellValue(texto);
					 
				        HSSFCellStyle style = book.createCellStyle();
				        //style.setFillForegroundColor(((Short)v[1]).shortValue());
						style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

						/*HSSFFont font = book.createFont();
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						font.setColor(((Short)v[2]).shortValue());
						style.setFont(font);*/

				        cell.setCellStyle(style);
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
   
	public static void addSheetCellXLS(int sheetIndex,String sheetName,ArrayList<ArrayList<CellXLS>> rows,HSSFWorkbook book){
		if(sheetName!=null && sheetName.length()>31){
        	sheetName=sheetName.substring(0, 30);
        } 
		// Se crea una hoja dentro del libro
        HSSFSheet sheet = book.createSheet(sheetName);
       
        book.setSheetName(sheetIndex,sheetName,HSSFWorkbook.ENCODING_UTF_16);
        // Se crea una fila dentro de la hoja
        int rowIndex=0;
        
        
        for(ArrayList<CellXLS> row : rows ){
        	short celIndex=0;
        	HSSFRow rowXls = sheet.createRow(rowIndex++);
        	for(CellXLS celValue : row){
		        // Se crea una celda dentro de la fila
		        HSSFCell cell = rowXls.createCell(celIndex++);
		        cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
		        // Se crea el contenido de la celda y se mete en ella.
		        HSSFRichTextString texto = new HSSFRichTextString(celValue.getValue());
		        HSSFCellStyle style = book.createCellStyle();
		        HSSFFont font = book.createFont();
		        font.setColor(celValue.getColor().getIndex());
		        style.setFont(font);
		        cell.setCellStyle(style);	
		        cell.setCellValue(texto);
        	}
        }
	}
	
	public static void addSheetCellXLSNoStyle(int sheetIndex,String sheetName,ArrayList<ArrayList<CellXLS>> rows,HSSFWorkbook book){
		if(sheetName!=null && sheetName.length()>31){
        	sheetName=sheetName.substring(0, 30);
        } 
		// Se crea una hoja dentro del libro
        HSSFSheet sheet = book.createSheet(sheetName);
       
        book.setSheetName(sheetIndex,sheetName,HSSFWorkbook.ENCODING_UTF_16);
        // Se crea una fila dentro de la hoja
        int rowIndex=0;
        
        
        for(ArrayList<CellXLS> row : rows ){
        	short celIndex=0;
        	HSSFRow rowXls = sheet.createRow(rowIndex++);
        	for(CellXLS celValue : row){
		        // Se crea una celda dentro de la fila
		        HSSFCell cell = rowXls.createCell(celIndex++);
		        cell.setEncoding((short)HSSFCell.ENCODING_UTF_16);
		        // Se crea el contenido de la celda y se mete en ella.
		        HSSFRichTextString texto = new HSSFRichTextString(celValue.getValue());
		        cell.setCellValue(texto);
		        
        	}
        }
	}
}