package assesment.presentation.actions.report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;

public class NewHireReport {

	private Text messages;
	
	private String[] userData;
	
	private Object[][] modules;
	private int right = 0;
	private int wrong = 0;

	private int percentR;

	private String[][] errors;
	private int[] values;

	public NewHireReport(Text messages, String[] userData, int[] values, Object[][] map, String[][] errors) {
		this.messages = messages;
		this.modules = map;
		for(int i = 0; i < map.length; i++) {
			wrong += ((Integer)map[i][1]).intValue();
			right += ((Integer)map[i][2]).intValue();
		}
    	percentR = right * 100 / (right + wrong);
    	this.errors = errors;
    	this.userData = userData;
    	this.values = values;
	}

	public void executeReport(OutputStream out, int type) {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			writer.setMargins(20, 20, 0, 10);
			document.open();
  
			NewHireTableHeader header = new NewHireTableHeader("NewHire");
			writer.setPageEvent(header);

			addTitlePage(document);

			addPage1(writer, document);
			
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}

	private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		
		Image img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/header.jpg");
		img.setAbsolutePosition(PageSize.A4.getWidth()-img.getWidth(), PageSize.A4.getHeight()-img.getHeight());
        document.add(img);

		Image logo = Image.getInstance(AssesmentData.FLASH_PATH+"/images/logo_cepa.png");
		logo.setAbsolutePosition(PageSize.A4.getWidth()-(logo.getWidth()+40),PageSize.A4.getHeight()-(logo.getHeight()+30));
        document.add(logo);
		/*Image logo = Image.getInstance(AssesmentData.RSMM_PATH+"images/logo.jpg");
		logo.setAbsolutePosition(PageSize.A4.getWidth()-(logo.getWidth()+20),PageSize.A4.getHeight()-(logo.getHeight()+30));
        document.add(logo);

		for(int i = 0; i < 10; i++)
			preface.add(new Paragraph(" "));
		
		// Lets write a big header
        Paragraph title = new Paragraph(messages.getText("rsmm.report.title1"), titleFont);
        title.setAlignment(Element.ALIGN_LEFT);
		preface.add(title);
			preface.add(new Paragraph(" "));
        Paragraph client = new Paragraph(messages.getText("rsmm.report.client"), catFont);
        client.setAlignment(Element.ALIGN_LEFT);
		preface.add(client);
		for(int i = 0; i < 26; i++)
			preface.add(new Paragraph(" "));
		
		document.add(preface);*/
	}

	private void addErrors(PdfWriter writer, Document document, int start) throws Exception {
//	        String[][] modules = {{"Inspecao Previa", "5", "4", "1" ,"20"}, {"Interacao con Transito", "20", "18", "2" ,"10"}, {"Condutor", "8", "5", "3" ,"37"}, {"Reglamentacoes Gerais", "8", "3", "5" ,"62"}};

		//String[][] values = {{"Inspecao Previa", "111116789 222226789 333456789 444456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 ", "INCORRECTA", "111156789 123456789 123456789 123456789 123456789 123456789"}, {"Inspecao Previa", "PREGUNTA 2", "123456789 123456789 123456789 123456789 123456789 123456789", "123456789 123456789 123456789 123456789 123456789 123456789123456789 123456789 123456789 123456789 123456789 123456789"}, {"Interacao con Transito", "PREGUNTA 1", "INCORRECTA", "CORRECTA"}, {"Interacao con Transito", "PREGUNTA 2", "INCORRECTA", "CORRECTA"}, {"Interacao con Transito", "PREGUNTA 3", "INCORRECTA", "CORRECTA"}, {"Condutor", "PREGUNTA 1", "INCORRECTA", "CORRECTA"}, {"Condutor", "PREGUNTA 2", "INCORRECTA", "CORRECTA"}, {"Reglamentacoes Gerais", "PREGUNTA 1", "INCORRECTA", "CORRECTA"}, {"Reglamentacoes Gerais", "PREGUNTA 2", "INCORRECTA", "CORRECTA"}, {"Reglamentacoes Gerais", "PREGUNTA 3", "INCORRECTA", "CORRECTA"}};

		Paragraph preface = new Paragraph();
		
		preface.add(new Paragraph(" "));

        absText(writer, messages.getText("user.report.errortitle"), 20, start+20, 18, Font.NORMAL);

		int h = start;
		String last = "";
		
		for(int i = 0; i < errors.length; i++) {
		
			if(h < 100) {
				document.newPage();
				h = 770;
				last = "";
			}

			if(!last.equals(errors[i][0])) {
				absText(writer, messages.getText(errors[i][0]), 20, h, 14, Font.NORMAL);
				last = errors[i][0];
				h -= 20;
			}
			
			Image back = Image.getInstance(AssesmentData.FLASH_PATH+"/images/whiteBox.jpg");
			back.setAbsolutePosition(20, h-60);
			back.scalePercent(100, 40);
	        document.add(back);
	
	        if(messages.getText(errors[i][1]).length() <= 95) {
	        	absText(writer, messages.getText(errors[i][1]), 30, h-10, 12, Font.NORMAL);
	        }else {
	        	String[] lines = parseLine(messages.getText(errors[i][1]), 115);
	        	if(lines.length > 1) {
		        	absText(writer, lines[0], 30, h-7, 10, Font.NORMAL);
		        	absText(writer, lines[1], 30, h-17, 10, Font.NORMAL);
	        	}else {
		        	absText(writer, lines[0], 30, h-10, 10, Font.NORMAL);
	        	}
	        }
	
			Image correct = Image.getInstance(AssesmentData.FLASH_PATH+"/images/correct.png");
			correct.setAbsolutePosition(40, h-40);
			correct.scalePercent(24);
	        document.add(correct);
	        if(messages.getText(errors[i][3]).length() <= 45) {
	        	absText(writer, messages.getText(errors[i][3]), 70, h-35, 10, Font.NORMAL);
	        } else {
	        	String[] lines = parseLine(messages.getText(errors[i][3]), 45);
	        	switch(lines.length) {
	        		case 1:
	    	        	absText(writer, messages.getText(errors[i][3]), 70, h-35, 10, Font.NORMAL);
	    	        	break;
	        		case 2:
	    	        	absText(writer, lines[0], 70, h-30, 10, Font.NORMAL);
	    	        	absText(writer, lines[1], 70, h-40, 10, Font.NORMAL);
	    	        	break;
	        		default:
	        			if(lines.length >= 3) {
		    	        	absText(writer, lines[0], 70, h-25, 10, Font.NORMAL);
		    	        	absText(writer, lines[1], 70, h-35, 10, Font.NORMAL);
		    	        	absText(writer, lines[2], 70, h-45, 10, Font.NORMAL);
	        			}
	    	        	break;
	        	}
	        }
	        
			Image incorrect = Image.getInstance(AssesmentData.FLASH_PATH+"/images/incorrect.png");
			incorrect.setAbsolutePosition(300, h-40);
			incorrect.scalePercent(24);
	        document.add(incorrect);
	        if(messages.getText(errors[i][2]).length() <= 45) {
	        	absText(writer, messages.getText(errors[i][2]), 330, h-35, 10, Font.NORMAL);
	        } else {
	        	String[] lines = parseLine(messages.getText(errors[i][2]), 45);
	        	switch(lines.length) {
	        		case 1:
	    	        	absText(writer, messages.getText(errors[i][2]), 330, h-35, 10, Font.NORMAL);
	    	        	break;
	        		case 2:
	    	        	absText(writer, lines[0], 330, h-30, 10, Font.NORMAL);
	    	        	absText(writer, lines[1], 330, h-40, 10, Font.NORMAL);
	    	        	break;
	        		default:
	        			if(lines.length >= 3) {
		    	        	absText(writer, lines[0], 330, h-25, 10, Font.NORMAL);
		    	        	absText(writer, lines[1], 330, h-35, 10, Font.NORMAL);
		    	        	absText(writer, lines[2], 330, h-45, 10, Font.NORMAL);
	        			}
	    	        	break;
	        	}
	        }

		    h -= 80;

		}		
        document.add(preface);
	}
	
	private String[] parseLine(String string, int len) {
		Collection<String> s = new LinkedList<String>();
		StringTokenizer t = new StringTokenizer(string);
		String str = "";
		while(t.hasMoreTokens()) {
			String w = t.nextToken();
			if(w.length() >= len) {
				s.add(w);
			}else {
				if(str.length() + w.length() <= len) {
					if(str.length() == 0)
						str = w;
					else 
						str += " "+w;
				}else {
					s.add(str);
					str = w;
				}
			}
		}
		if(str.length() > 0)
			s.add(str);
		return (String[])s.toArray(new String[0]);
	}

	private void addPage1(PdfWriter writer, Document document) throws Exception {
		
    		Paragraph preface = new Paragraph();
    		// We add one empty line
    		preface.add(new Paragraph(" "));
    		Paragraph text = new Paragraph(userData[0], FontFactory.getFont(Util.FONT_REPORT_NAME, 12, Font.NORMAL));
    		text.setAlignment(Element.ALIGN_RIGHT);
    		preface.add(text);

    		if(userData[1] != null) { 
	    		text = new Paragraph(messages.getText("generic.corporation") + ": "+userData[1], FontFactory.getFont(Util.FONT_REPORT_NAME, 18, Font.NORMAL));
	    		text.setAlignment(Element.ALIGN_LEFT);
	    		preface.add(text);
    		}
    		
    		text = new Paragraph(messages.getText("generic.assesment") + ": "+userData[3], FontFactory.getFont(Util.FONT_REPORT_NAME, 18, Font.NORMAL));
    		text.setAlignment(Element.ALIGN_LEFT);
    		preface.add(text);

    		if(userData[1] == null) {
        		preface.add(new Paragraph(" "));
    		}

    		text = new Paragraph(messages.getText("user.data.nickname") + ": "+userData[2], FontFactory.getFont(Util.FONT_REPORT_NAME, 18, Font.NORMAL));
    		text.setAlignment(Element.ALIGN_LEFT);
    		preface.add(text);

    		if(userData[1] == null) {
        		preface.add(new Paragraph(" "));
    		}

    		Image backSemaforo = Image.getInstance(AssesmentData.FLASH_PATH+"/images/whiteBox2.jpg");
    		backSemaforo.setAbsolutePosition(20, 480);
    		backSemaforo.scalePercent(100, 65);
	        document.add(backSemaforo);

	        absText(writer, messages.getText("generic.newhire.driverprofile"), 150, 640, 18, Font.NORMAL);

	        Image semaforo = Image.getInstance(AssesmentData.FLASH_PATH+"/images/semaforoNH.png");
			semaforo.setAbsolutePosition(190, 510);
			semaforo.scalePercent(24);
	        document.add(semaforo);
    		
			Image flecha = Image.getInstance(AssesmentData.FLASH_PATH+"/images/flechaNH.png");
            if(values[0] <= 4) {
            	flecha.setAbsolutePosition(168, 590);
            } else if(values[0] <= 8) {
            	flecha.setAbsolutePosition(168, 550);
            } else {
            	flecha.setAbsolutePosition(168, 510);
            }
            flecha.scalePercent(24);
	        document.add(flecha);
            
	        
	        absText(writer, messages.getText("generic.newhire.basictest"), 310, 640, 18, Font.NORMAL);
	        semaforo = Image.getInstance(AssesmentData.FLASH_PATH+"/images/semaforoNH.png");
			semaforo.setAbsolutePosition(370, 510);
			semaforo.scalePercent(24);
	        document.add(semaforo); 
    		
	        flecha = Image.getInstance(AssesmentData.FLASH_PATH+"/images/flechaNH.png");
            if(values[1] <= 6) {
            	flecha.setAbsolutePosition(348, 590);
            } else if(values[1] <= 12) {
            	flecha.setAbsolutePosition(348, 550);
            } else {
            	flecha.setAbsolutePosition(348, 510);
            }
            flecha.scalePercent(24);
	        document.add(flecha);
                        
			Image back = Image.getInstance(AssesmentData.FLASH_PATH+"/images/grafico.png");
			back.setAbsolutePosition(20, 300);
			back.scalePercent(24);
	        document.add(back);

	        String nameR = "cont_00";
	        if(percentR < 100) {
	        	nameR += "0";
	        	if(percentR < 10) {
		        	nameR += "0";
		        }
	        }
			Image graphR = Image.getInstance(AssesmentData.FLASH_PATH+"/images/Green/green_"+nameR+percentR+".png");
			graphR.setAbsolutePosition(140, 310);
			graphR.scalePercent(24);
	        document.add(graphR);

	        absText(writer, messages.getText("report.userresult.wrong").toLowerCase()+": "+wrong, 350, 380, 12, Font.NORMAL);
	        
			Image graphW = Image.getInstance(AssesmentData.FLASH_PATH+"/images/Red/red_"+nameR+percentR+".png");
			graphW.setAbsolutePosition(325, 310);
			graphW.scalePercent(24);
	        document.add(graphW);

	        absText(writer, messages.getText("report.userresult.right").toLowerCase()+": "+right, 165, 380, 12, Font.NORMAL);
    				
			absText(writer, messages.getText("generic.user.results").toUpperCase(), 30, 428, 18, Font.NORMAL);

			/*text = new Paragraph(messages.getText("generic.user.results").toUpperCase(), FontFactory.getFont(Util.FONT_REPORT_NAME, 18, Font.NORMAL));
    		text.setAlignment(Element.ALIGN_LEFT);
    		preface.add(text);*/
    		
    		/*preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));*/
    		
	        //String[][] modules = {{"Inspecao Previa", "5", "4", "1" ,"20"}, {"Interacao con Transito", "20", "18", "2" ,"10"}, {"Condutor", "8", "5", "3" ,"37"}, {"Reglamentacoes Gerais", "8", "3", "5" ,"62"}};
	        //modules = new Object[][]{{"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}, {"Inspecao Previa", new Integer(4), new Integer(1)}};

	        int[][] yValues = {{200, 50, 30}, {395, 65, 225}, {380, 75, 215}, {360, 87, 190}, {340, 95, 55}, {320, 62, 35}, {290, 72, 495}, {272, 78, 495}};
    		int index = modules.length-1;
    		
    		back = (modules.length > 5) ? Image.getInstance(AssesmentData.FLASH_PATH+"/images/whiteBox2.jpg") : Image.getInstance(AssesmentData.FLASH_PATH+"/images/whiteBox.jpg");
			back.setAbsolutePosition(20, yValues[index][0]);
   			back.scalePercent(100, yValues[index][1]);
	        document.add(back);

			absText(writer, messages.getText("report.userresult.moduletitle").toUpperCase(), 30, 255, 18, Font.NORMAL);

/*    		text = new Paragraph(messages.getText("report.userresult.moduletitle").toUpperCase(), FontFactory.getFont(Util.FONT_REPORT_NAME, 18, Font.NORMAL));
    		text.setAlignment(Element.ALIGN_LEFT);
    		preface.add(text);*/
    		
	        absText(writer, messages.getText("generic.module"), 40, 235, 10, Font.BOLD);
	        absText(writer, messages.getText("report.users.total.count"), 250, 235, 10, Font.BOLD);
	        absText(writer, messages.getText("report.userresult.wrong"), 340, 235, 10, Font.BOLD);
	        absText(writer, messages.getText("report.userresult.right"), 430, 235, 10, Font.BOLD);
	        absText(writer, "%", 520, 235, 10, Font.BOLD);
	        
	        int h = 235;
	        int[] percentsR = new int[modules.length];
	        for(int i = 0; i < modules.length; i++) {
	        	int r = ((Integer)modules[i][2]).intValue();
	        	int w = ((Integer)modules[i][1]).intValue();
	        	int percent = r * 100 / (r+w);
	        	percentsR[i] = percent;
		        absText(writer, messages.getText((String)modules[i][0]), 40, h-20, 10, Font.NORMAL);
		        absText(writer, String.valueOf(r+w), 250, h-20, 10, Font.NORMAL);
		        absText(writer, String.valueOf(w), 340, h-20, 10, Font.NORMAL);
		        absText(writer, String.valueOf(r), 430, h-20, 10, Font.NORMAL);
		        absText(writer, String.valueOf(percent), 520, h-20, 10, Font.NORMAL);
		        h -= 20;
	        }
/*
    		params.put("resultados_modulo",);
			params.put("modulo",);
*/

	        if(modules.length > 6) {
	    		document.add(preface);
	        	document.newPage();
	        	preface = new Paragraph();
	        }
	        
    		back = Image.getInstance(AssesmentData.FLASH_PATH+"/images/backGraphs"+modules.length+".jpg");
			back.setAbsolutePosition(20, yValues[index][2]);
			back.scalePercent(24);
			document.add(back);
			
    		if(modules.length > 4) {
    			absText(writer, messages.getText("report.userresult.moduletitle").toUpperCase(), 30, yValues[index][2] + 245, 18, Font.NORMAL);
		        absText(writer, messages.getText("report.userresult.wrong"), 440, yValues[index][2] + 251, 10, Font.NORMAL);
		        absText(writer, messages.getText("report.userresult.right"), 505, yValues[index][2] + 251, 10, Font.NORMAL);
    		}else {
    			absText(writer, messages.getText("report.userresult.moduletitle").toUpperCase(), 30, yValues[index][2] + 130, 18, Font.NORMAL);
		        absText(writer, messages.getText("report.userresult.wrong"), 440, yValues[index][2] + 136, 10, Font.NORMAL);
		        absText(writer, messages.getText("report.userresult.right"), 505, yValues[index][2] + 136, 10, Font.NORMAL);
    		}

/*    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));
    		if(modules.length < 7) {
        		preface.add(new Paragraph(" "));
        		preface.add(new Paragraph(" "));
        		preface.add(new Paragraph(" "));
	    		if(modules.length > 2) {
		    		preface.add(new Paragraph(" "));
		    		if(modules.length > 3) {
			    		preface.add(new Paragraph(" "));
			    		if(modules.length > 4) {
				    		preface.add(new Paragraph(" "));
				    		if(modules.length > 5) {
					    		preface.add(new Paragraph(" "));
				    		}
			    		}
		    		}
	    		}
    		}
*/    		

/*
			text = new Paragraph(messages.getText("report.userresult.moduletitle").toUpperCase(), FontFactory.getFont(Util.FONT_REPORT_NAME, 18, Font.NORMAL));
    		text.setAlignment(Element.ALIGN_LEFT);
    		preface.add(text);
*/
    		int x = 50;
    		if(modules.length > 4) {
	    		for(int i = 0; i < 4; i++) {
		    		Image img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/red.png");
					img.setAbsolutePosition(x, yValues[index][2] + 135);
					img.scalePercent(23);
			        document.add(img);
		
			        nameR = "level__00";
			        if(percentsR[i] < 100) {
			        	nameR += "0";
			        	if(percentsR[i] < 10) {
				        	nameR += "0";
				        }
			        }

			        img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/GreenModule/"+nameR+percentsR[i]+".png");
					img.setAbsolutePosition(x, yValues[index][2] + 135);
					img.scalePercent(23);
			        document.add(img);
			        
			        x += 130;
	    		}
	    		x = 50;
	    		for(int i = 4; i < modules.length; i++) {
		    		Image img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/red.png");
					img.setAbsolutePosition(x, yValues[index][2] + 20);
					img.scalePercent(23);
			        document.add(img);
		
			        nameR = "level__00";
			        if(percentsR[i] < 100) {
			        	nameR += "0";
			        	if(percentsR[i] < 10) {
				        	nameR += "0";
				        }
			        }

			        img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/GreenModule/"+nameR+percentsR[i]+".png");
					img.setAbsolutePosition(x, yValues[index][2] + 20);
					img.scalePercent(23);
			        document.add(img);
			        
			        x += 130;
	    		}
    		}else {
	    		for(int i = 0; i < modules.length; i++) {
		    		Image img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/red.png");
					img.setAbsolutePosition(x, yValues[index][2] + 20);
					img.scalePercent(23);
			        document.add(img);
		
			        nameR = "level__00";
			        if(percentsR[i] < 100) {
			        	nameR += "0";
			        	if(percentsR[i] < 10) {
				        	nameR += "0";
				        }
			        }

			        img = Image.getInstance(AssesmentData.FLASH_PATH+"/images/GreenModule/"+nameR+percentsR[i]+".png");
					img.setAbsolutePosition(x, yValues[index][2] + 20);
					img.scalePercent(23);
			        document.add(img);
			        
			        x += 130;
	    		}
    		}
    		
    		document.add(preface);
    		
	        if(modules.length > 6) {
	        	addErrors(writer, document, 440);
	        } else {
	        	document.newPage();
	        	addErrors(writer, document, 740);
	        }
	}

	private void absText(PdfWriter writer, String text, int x, int y, int size, int fontWeight) {
		absText(writer, text, x, y, size, false, fontWeight);
	}
	
	private void absText(PdfWriter writer, String text, int x, int y, int size, boolean right, int fontWeight) {
	    try {
	    	PdfContentByte cb = writer.getDirectContent();
	    	BaseFont bf = BaseFont.createFont(Util.FONT_REPORT_NAME, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	    	cb.saveState();
	    	cb.beginText();
	    	cb.setFontAndSize(bf, size);
	    	if(fontWeight == Font.BOLD) {
		    	cb.setLineWidth((float)0.5);
		    	cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE); 
	    	}
	    	if(right) {
	    		ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(text, FontFactory.getFont(Util.FONT_REPORT_NAME, size, fontWeight)), x, y, 0);
	    	}else {	
	    		cb.moveText(x, y);
	    		cb.showText(text);
	    	}
	    	cb.endText();
	    	cb.restoreState();
	    } catch (DocumentException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
	
}
