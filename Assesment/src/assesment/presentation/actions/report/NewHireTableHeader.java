package assesment.presentation.actions.report;

import java.io.IOException;

import assesment.communication.assesment.AssesmentData;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class NewHireTableHeader extends PdfPageEventHelper {

	private String title;

	public NewHireTableHeader(String title) {
		this.title = title;
	}

	public void onStartPage(PdfWriter writer, Document document) {
		Image img;
		try {
			img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/header.jpg");
			img.setAbsolutePosition(PageSize.A4.getWidth()-(img.getWidth()), PageSize.A4.getHeight()-img.getHeight());
	        document.add(img);

			Image logo = Image.getInstance(AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
			logo.setAbsolutePosition(PageSize.A4.getWidth()-(logo.getWidth()+40),PageSize.A4.getHeight()-(logo.getHeight()+30));
	        document.add(logo);
	        
	        int h = (int) PageSize.A4.getHeight();
	        absText(writer, title, 20, h-40, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public void onEndPage(PdfWriter writer, Document document) {
		Image img;
		try {
			if(writer.getPageNumber() > 1)
				absText(writer, String.valueOf(writer.getPageNumber()), (int)PageSize.A4.getWidth()-20, 20, 10);
			
			img = Image.getInstance(AssesmentData.RSMM_PATH+"/images/footer.jpg");
			img.setAbsolutePosition(0, 0);
	        document.add(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
  
	private void absText(PdfWriter writer, String text, int x, int y, int size) {
	    try {
	    	PdfContentByte cb = writer.getDirectContent();
	    	BaseFont bf = BaseFont.createFont(Util.FONT_REPORT_NAME, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	    	cb.saveState();
	    	cb.beginText();
	    	cb.moveText(x, y);
	    	cb.setFontAndSize(bf, size);
	    	cb.showText(text);
	    	cb.endText();
	    	cb.restoreState();
	    } catch (DocumentException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}

