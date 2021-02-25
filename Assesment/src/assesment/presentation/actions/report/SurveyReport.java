package assesment.presentation.actions.report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;

import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class SurveyReport {

	private Text messages;
	
	private static Font titleFont = FontFactory.getFont(Util.FONT_REPORT_NAME, 16, Font.BOLD);
	private static Font orangeFont = FontFactory.getFont(Util.FONT_REPORT_NAME, 16, Font.BOLD, BaseColor.ORANGE);
	private static Font catFontSimple = FontFactory.getFont(Util.FONT_REPORT_NAME, 16, Font.NORMAL);
	private static Font whiteFont = FontFactory.getFont(Util.FONT_REPORT_NAME, 16, Font.NORMAL, BaseColor.WHITE);
	private static Font obsFont = FontFactory.getFont(Util.FONT_REPORT_NAME, 12, Font.BOLD);
	private static Font subFont = FontFactory.getFont(Util.FONT_REPORT_NAME, 12, Font.NORMAL);
	private static Font cell = FontFactory.getFont(Util.FONT_REPORT_NAME, 7, Font.NORMAL);
	private static Font cellBold = FontFactory.getFont(Util.FONT_REPORT_NAME, 8, Font.NORMAL);

	private String company;
	private String responsible;
	private String lang;
	private String data;
	
	private int[] index = {2, 1, 3, 3, 1, 1, 2};

	private HashMap<Integer, double[]> results;
	
	public SurveyReport(Text messages, String lang, String company, String responsible, Date enddate, HashMap<Integer, double[]> results) {

		this.messages = messages;
		this.company = company;
		this.lang = lang;
		this.responsible = responsible;
		this.data = Util.formatDate(enddate);
		this.results = results;
	}

	public void executeReport(OutputStream out, int type) {
		try {
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.open();
  
			TableHeader header = new TableHeader("Safety Survey - "+company);
			writer.setPageEvent(header);

			addTitlePage(document);
			document.newPage();
			
			addSecondPage(document);
			document.newPage();
			
			addThirdPage(writer, document);

			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
	    }
	}

	private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		
		Image imgHeader = Image.getInstance(AssesmentData.RSMM_PATH+"images/header.jpg");
		imgHeader.setAbsolutePosition(0,PageSize.A4.getHeight()-imgHeader.getHeight());
        document.add(imgHeader);
        
		Image logo = Image.getInstance(AssesmentData.RSMM_PATH+"images/logo.jpg");
		logo.setAbsolutePosition(PageSize.A4.getWidth()-(logo.getWidth()+20),PageSize.A4.getHeight()-(logo.getHeight()+30));
        document.add(logo);

		// Lets write a big header
        Paragraph title = new Paragraph(messages.getText("survey.report.title"), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
		preface.add(title);
        
		Paragraph client = new Paragraph(company, orangeFont);
        client.setAlignment(Element.ALIGN_CENTER);
		preface.add(client);

		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("survey.report.introduction"), obsFont);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		
		for(int i = 1; i <= 8; i++) {
			Paragraph par = new Paragraph(messages.getText("survey.introduction.line"+i), subFont);
			par.setAlignment(Element.ALIGN_LEFT);
			preface.add(par);
			preface.add(new Paragraph(" "));
		}

		Paragraph sub2 = new Paragraph(messages.getText("survey.report.safetysurvey"), obsFont);
		sub2.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub2);

		Paragraph par = new Paragraph(messages.getText("survey.safetysurvey.line1"), subFont);
		par.setAlignment(Element.ALIGN_LEFT);
		preface.add(par);
		preface.add(new Paragraph(" "));

		String line = messages.getText("survey.safetysurvey.line2.1")+" "+responsible+" "+messages.getText("survey.safetysurvey.line2.2")+" "+data+" "+messages.getText("survey.safetysurvey.line2.3")+" "+company;
		par = new Paragraph(line, subFont);
		par.setAlignment(Element.ALIGN_LEFT);
		preface.add(par);
		preface.add(new Paragraph(" "));
		
		par = new Paragraph(messages.getText("survey.safetysurvey.line3"), subFont);
		par.setAlignment(Element.ALIGN_LEFT);
		preface.add(par);

		document.add(preface);
	}

	private void addSecondPage(Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("survey.report.pilars"), obsFont);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		
		for(int i = 1; i <= 5; i++) {
			Paragraph par = new Paragraph(i+" "+messages.getText("survey.pilars.title"+i), obsFont);
			par.setAlignment(Element.ALIGN_LEFT);
			preface.add(par);
			for(int j = 1; j <= index[i-1]; j++) {
				par = new Paragraph(messages.getText("survey.pilar"+i+".line"+j), subFont);
				par.setAlignment(Element.ALIGN_LEFT);
				preface.add(par);
			}
			preface.add(new Paragraph(" "));
		}

		document.add(preface);
	}

	private void addThirdPage(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));

		for(int i = 6; i <= 7; i++) {
			Paragraph par = new Paragraph(i+" "+messages.getText("survey.pilars.title"+i), obsFont);
			par.setAlignment(Element.ALIGN_LEFT);
			preface.add(par);
			for(int j = 1; j <= index[i-1]; j++) {
				par = new Paragraph(messages.getText("survey.pilar"+i+".line"+j), subFont);
				par.setAlignment(Element.ALIGN_LEFT);
				preface.add(par);
			}
			preface.add(new Paragraph(" "));
		}

		Paragraph par = new Paragraph(messages.getText("survey.report.result")+": "+company, obsFont);
		par.setAlignment(Element.ALIGN_LEFT);
		preface.add(par);

        PdfPTable table = new PdfPTable(7);
	    table.setHeaderRows(2);

	    PdfPCell cell0 = new PdfPCell(new Phrase(messages.getText("")));
	    cell0.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    table.addCell(cell0);

	    cell0 = new PdfPCell(new Phrase(messages.getText("survey.report.answers")));
	    cell0.setColspan(3);
	    cell0.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell0.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell0);
	    
	    cell0 = new PdfPCell(new Phrase(messages.getText("")));
	    cell0.setColspan(3);
	    cell0.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    table.addCell(cell0);

	    PdfPCell cell1 = new PdfPCell(new Phrase(messages.getText("survey.report.pilar")));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
   		cell1.setFixedHeight(25f);
	    table.addCell(cell1);

	    String[] s = {"survey.report.yes", "survey.report.partial", "survey.report.no", "survey.report.points", "%", "survey.report.level"}; 
	    for(int i = 0; i < 6; i++) {
		    PdfPCell cell2 = new PdfPCell(new Phrase(messages.getText(s[i])));
		    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
	   		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
	   		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    table.addCell(cell2);
	    }

	    table.setWidths(new int[]{250,50,50,50,50,50,50});
	    double yes = 0.0;
	    double partial = 0.0;
	    double no = 0.0;
	    double points = 0.0;
	    
	    for(int i = 1; i <= 7; i++) {
	    	PdfPCell pdfCell = new PdfPCell(new Phrase(i+" "+messages.getText("survey.pilars.title"+i), subFont));
       		pdfCell.setBorderWidthLeft(0);
       		pdfCell.setBorderWidthTop(0);
       		if(i % 2 == 0){
       			pdfCell.setBackgroundColor(new BaseColor(210, 210, 210));
       		}
       		pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
       		pdfCell.setFixedHeight(20f);
       		table.addCell(pdfCell);

       		double[] res = results.get(new Integer(1902+i));
       		for(int j = 0; j < 4; j++) {
	       		PdfPCell pdfCell1 = null;
       			if(j == 3) {
       				pdfCell1 = new PdfPCell(new Phrase(String.valueOf(res[j])));
       			}else {
       				pdfCell1 = new PdfPCell(new Phrase(String.valueOf((int)res[j])));
       			}
	       		pdfCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	       		if(i % 2 == 0){
	       			pdfCell1.setBackgroundColor(new BaseColor(211, 211, 211));
	       		}
	       		table.addCell(pdfCell1);
       		}

       		yes += res[0];
       		partial += res[1];
       		no += res[2];
       		points += res[3];
       		
       		double percent = res[3] * 100.0 / (res[0] + res[1] + res[2]);
       		PdfPCell pdfCell1 = new PdfPCell(new Phrase(String.valueOf(Math.round(percent))));
       		pdfCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
       		if(i % 2 == 0){
       			pdfCell1.setBackgroundColor(new BaseColor(211, 211, 211));
       		}
       		table.addCell(pdfCell1);

       		pdfCell1 = new PdfPCell(new Phrase(" "));
       		pdfCell1.setBackgroundColor(getColor(percent));
       		table.addCell(pdfCell1);
        }		    
	    
	    cell1 = new PdfPCell(new Phrase(messages.getText("Total")));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
   		cell1.setFixedHeight(25f);
	    table.addCell(cell1);

	    cell1 = new PdfPCell(new Phrase(messages.getText(String.valueOf((int)yes))));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell1);

	    cell1 = new PdfPCell(new Phrase(messages.getText(String.valueOf((int)partial))));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell1);
	    
	    cell1 = new PdfPCell(new Phrase(messages.getText(String.valueOf((int)no))));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell1);

	    cell1 = new PdfPCell(new Phrase(String.valueOf(points)));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell1);

   		double percent = points * 100.0 / (yes + no + partial);
   		String sP = String.valueOf(percent);
   		sP = sP.substring(0, sP.indexOf(".")+3);
	    cell1 = new PdfPCell(new Phrase(sP));
	    cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
   		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(cell1);
	    
	    cell1 = new PdfPCell(new Phrase(""));
	    cell1.setBackgroundColor(getColor(percent));
	    table.addCell(cell1);

        table.setTotalWidth(550);
        table.writeSelectedRows(0, -1, 20, 480, writer.getDirectContent());

		Image img = Image.getInstance(AssesmentData.RSMM_PATH+"images/tabela-"+lang+".png");
		img.setAbsolutePosition(30, 30);
        document.add(img);

		document.add(preface);
	}

	private BaseColor getColor(double percent) {
		if(percent >= 96) {
			return new BaseColor(59, 103, 188);
		} else if(percent >= 90) {
			return new BaseColor(106, 185, 84);
		} else if(percent >= 80) {
			return new BaseColor(255, 237, 3);
		} else if(percent >= 65) {
			return new BaseColor(255, 137, 11);
		} else {
			return new BaseColor(183, 0, 2);
		}  
	}

	private BaseColor getColorFont(double points) {
		if(points >= 57) {
			return BaseColor.WHITE;
		} else if(points >= 41) {
			return BaseColor.BLACK;
		} else {
			return BaseColor.WHITE;
		}  
	}

	/*    
	private void addPage1(PdfWriter writer, Document document) throws Exception {
    		
    		Paragraph preface = new Paragraph();
    		// We add one empty line
    		preface.add(new Paragraph(" "));
    		preface.add(new Paragraph(" "));

    		Paragraph client = new Paragraph(messages.getText("rsmm.report.index1"), catFont);
            client.setAlignment(Element.ALIGN_LEFT);
    		preface.add(client);

    		String[] parragraphs = new String[4];
    		parragraphs[0] = messages.getText("rsmm.report.whatisrmm1");
    		parragraphs[1] = messages.getText("rsmm.report.whatisrmm2");
    		parragraphs[2] = messages.getText("rsmm.report.whatisrmm3"); 
    		parragraphs[3] = messages.getText("rsmm.report.whatisrmm4"); 

			preface.add(new Paragraph(" "));
    		for(int i = 0; i < parragraphs.length; i++) {
    	        Paragraph line = new Paragraph(parragraphs[i], subFont);
    	        line.setAlignment(Element.ALIGN_LEFT);
    			preface.add(line);
   				preface.add(new Paragraph(" "));
    		}
    		
    		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.index2"), catFontSimple);
    		sub1.setAlignment(Element.ALIGN_LEFT);
			preface.add(sub1);
			preface.add(new Paragraph(" "));

			Paragraph txt = new Paragraph(messages.getText("rsmm.report.index2sub"), subFont);
			txt.setAlignment(Element.ALIGN_LEFT);
			preface.add(txt);

			preface.add(new Paragraph(" "));
			preface.add(new Paragraph(" "));
			preface.add(new Paragraph(" "));

    		document.add(preface);
    		
			Image img = Image.getInstance(AssesmentData.RSMM_PATH+"images/piramide_"+lang+".png");
			img.setAbsolutePosition(30, 60);
	        document.add(img);
	}
	
	private void addPage2(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line

		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.analizeddimensions"), catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.dimensionsare"), subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);

		String[] titles = {messages.getText("rsmm.report.dimension1"), messages.getText("rsmm.report.dimension2"), messages.getText("rsmm.report.dimension3"), messages.getText("rsmm.report.dimension4"), messages.getText("rsmm.report.dimension5")}; 
		String[] texts = new String[5]; 
		texts[0] = messages.getText("rsmm.report.dimensiontext1"); 
		texts[1] = messages.getText("rsmm.report.dimensiontext2"); 
		texts[2] = messages.getText("rsmm.report.dimensiontext3");
		texts[3] = messages.getText("rsmm.report.dimensiontext4"); 
		texts[4] = messages.getText("rsmm.report.dimensiontext5");
		
		preface.add(new Paragraph(" "));
		for(int i = 0; i < titles.length; i++) {
	        Paragraph line = new Paragraph(titles[i], obsFont);
	        line.setIndentationLeft(20);
	        line.setAlignment(Element.ALIGN_LEFT);
			preface.add(line);
				
	        Paragraph text = new Paragraph(texts[i], subFont);
	        text.setIndentationLeft(40);
	        text.setAlignment(Element.ALIGN_LEFT);
			preface.add(text);

			preface.add(new Paragraph(" "));
		}
		
		document.add(preface);
	}
	
	private void addPage3(PdfWriter writer, Document document, String company, int level) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		String maturity = messages.getText("rsmm.report.level"+level);
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.studyresults"), catFont);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.clientlevel1")+" "+company+" "+messages.getText("rsmm.report.clientlevel2")+" "+level+messages.getText("rsmm.report.clientlevel3")+" "+maturity+messages.getText("rsmm.report.clientlevel4")+": ", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(v1, "A", messages.getText("rsmm.report.dimension1"));
        dataset.addValue(v2, "A", messages.getText("rsmm.report.dimension2"));
        dataset.addValue(v3, "A", messages.getText("rsmm.report.shortdimension3"));
        dataset.addValue(v4, "A", messages.getText("rsmm.report.dimension4"));
        dataset.addValue(v5, "A", messages.getText("rsmm.report.dimension5"));

        JFreeChart chart =  ChartFactory.createStackedBarChart(
        		messages.getText("rsmm.report.graphtitle"),  // chart title
        		messages.getText("rsmm.report.graphx"), messages.getText("rsmm.report.graphy"),
                dataset,      // data
                PlotOrientation.VERTICAL,
                true,         // include legend
                true,
                true
            );
        
        final CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
    
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        chart.setBorderPaint(Color.WHITE);
        chart.setBorderStroke(null);
        chart.setBorderVisible(false);
        chart.getLegend().setVisible(false);
        chart.setBackgroundPaint(Color.WHITE);
        //chart.getTitle().setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
    
        BarRenderer renderer = (BarRenderer) plot.getRenderer(); 
        renderer.setSeriesPaint(0, new Color(66, 133, 244));
        renderer.setBarPainter(new StandardBarPainter());
    
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseItemLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
        renderer.setBaseItemLabelPaint(Color.WHITE);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
        renderer.setBasePositiveItemLabelPosition(position);
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        ValueAxis rangeAxis = plot.getRangeAxis();
        
        domainAxis.setTickLabelFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 8));
        rangeAxis.setTickLabelFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 10));
        rangeAxis.setRange(0.0, 5.0);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(500, 250);
        Graphics2D graphics2d = template.createGraphics(500, 250, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 500, 250);
        chart.draw(graphics2d, rectangle2d);

        int line = 34;
        switch(level) {
	    	case 1:
	    		line = 170;
	    		break;
	    	case 2:
	    		line = 136;
	    		break;
	    	case 3:
	    		line = 102;
	    		break;
	    	case 4:
	    		line = 68;
	    		break;
        }
		graphics2d.setColor(Color.RED);
		graphics2d.setStroke(new BasicStroke(2));
		graphics2d.drawLine(110,line,436,line);

        
       graphics2d.dispose();
       contentByte.addTemplate(template, 20, 390);

		for(int i = 0; i < 17; i++)
			preface.add(new Paragraph(" "));

		Paragraph carac = new Paragraph(messages.getText("rsmm.report.levelcompany1")+" "+level+" "+messages.getText("rsmm.report.levelcompany2")+":", subFont);
		carac.setAlignment(Element.ALIGN_LEFT);
		preface.add(carac);
		preface.add(new Paragraph("  "));
       
		preface.add(caracteristicas[level-1]);		
		if(level != 4) {
			preface.add(new Paragraph("  "));
		}
		if(level < 5) {
			document.add(preface);

			document.newPage();
			preface = new Paragraph();
			// We add one empty line

			preface.add(new Paragraph(" "));
			preface.add(new Paragraph(" "));

			Paragraph rec = new Paragraph(messages.getText("rsmm.report.improvelevel")+":", subFont);
			rec.setAlignment(Element.ALIGN_LEFT);
			preface.add(rec);
			preface.add(new Paragraph("  "));

			preface.add(recomendaciones[level-1]);
			preface.add(new Paragraph("  "));
			
			Paragraph sug1 = new Paragraph(messages.getText("rsmm.report.improvelevel1")+" "+level+" "+messages.getText("rsmm.report.improvelevel2")+" "+String.valueOf(level+1)+":", subFont);
			sug1.setAlignment(Element.ALIGN_LEFT);
			preface.add(sug1);
			preface.add(new Paragraph("  "));
			
			if(level == 1) {
				Paragraph sug2 = new Paragraph((String)sugerencias[level-1], subFont);
				sug2.setAlignment(Element.ALIGN_LEFT);
				preface.add(sug2);
			}else {
				preface.add((List)sugerencias[level-1]);
			}
			preface.add(new Paragraph(" "));
		}else {
			document.add(preface);

			document.newPage();
			preface = new Paragraph();
			// We add one empty line

			preface.add(new Paragraph(" "));
			preface.add(new Paragraph(" "));
		}
		
		if(level > 1) {
			Paragraph sug3 = new Paragraph(messages.getText("rsmm.report.improvelevel3")+" "+level+" "+messages.getText("rsmm.report.improvelevel4")+":", subFont);
			sug3.setAlignment(Element.ALIGN_LEFT);
			preface.add(sug3);
			preface.add(new Paragraph("  "));

			if(level == 2) {
				Paragraph sug4 = new Paragraph((String)sugerencias[level-2], subFont);
				sug4.setAlignment(Element.ALIGN_LEFT);
				preface.add(sug4);
			}else {
				preface.add((List)sugerencias[level-2]);
			}
			preface.add(new Paragraph(" "));
		}

		Paragraph sug5 = new Paragraph(messages.getText("rsmm.report.improvelevel5")+" "+company+" "+messages.getText("rsmm.report.improvelevel6"),obsFont);
		sug5.setAlignment(Element.ALIGN_LEFT);
		preface.add(sug5);
		
		document.add(preface);
	}
	
	private void addPage4(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph client = new Paragraph(messages.getText("rsmm.report.annex"), catFont);
        client.setAlignment(Element.ALIGN_LEFT);
		preface.add(client);
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.levelcaracteristics")+" 1", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.level1caracteristics")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		preface.add(new Paragraph("  "));
		 
		preface.add(caracteristicas[0]);		
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.levelsuggestions")+" 2", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		txt = new Paragraph(messages.getText("rsmm.report.level2suggestions")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		preface.add(new Paragraph("  "));
		 
		preface.add(recomendaciones[0]);
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.cepasuggestions"), catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		txt = new Paragraph((String)sugerencias[0], subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);

		document.add(preface);
	}
	
	private void addPage5(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph client = new Paragraph(messages.getText("rsmm.report.annex"), catFont);
        client.setAlignment(Element.ALIGN_LEFT);
		preface.add(client);
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.levelcaracteristics")+" 2", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.level2caracteristics")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		
		preface.add(caracteristicas[1]);		
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.levelsuggestions")+" 3", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		txt = new Paragraph(messages.getText("rsmm.report.level3suggestions")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);

		preface.add(recomendaciones[1]);
		preface.add(new Paragraph(" "));

		document.add(preface);

		document.newPage();
		preface = new Paragraph();
		// We add one empty line

		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.cepasuggestions")+": ", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		preface.add((List)sugerencias[1]);

		document.add(preface);

	}
	
	private void addPage6(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph client = new Paragraph(messages.getText("rsmm.report.annex"), catFont);
        client.setAlignment(Element.ALIGN_LEFT);
		preface.add(client);
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.levelcaracteristics")+" 3", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.level3caracteristics")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		preface.add(new Paragraph(" "));

		preface.add(caracteristicas[2]);		
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.levelsuggestions")+" 4", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		txt = new Paragraph(messages.getText("rsmm.report.level4suggestions")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		preface.add(new Paragraph(" "));

		preface.add(recomendaciones[2]);
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.cepasuggestions")+":", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		preface.add((List)sugerencias[2]);
		document.add(preface);
	}

	
	private void addPage7(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph client = new Paragraph(messages.getText("rsmm.report.annex"), catFont);
        client.setAlignment(Element.ALIGN_LEFT);
		preface.add(client);
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.levelcaracteristics")+" 4", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.level4caracteristics")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);

		preface.add(caracteristicas[3]);		
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.levelsuggestions")+" 5", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		txt = new Paragraph(messages.getText("rsmm.report.level5suggestions")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		
		preface.add(recomendaciones[3]);
		preface.add(new Paragraph(" "));

		sub1 = new Paragraph(messages.getText("rsmm.report.cepasuggestions")+":", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		preface.add((List)sugerencias[3]);

		document.add(preface);
	}

	private void addPage8(PdfWriter writer, Document document) throws Exception {
		Paragraph preface = new Paragraph();
		// We add one empty line
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));

		Paragraph client = new Paragraph(messages.getText("rsmm.report.annex"), catFont);
        client.setAlignment(Element.ALIGN_LEFT);
		preface.add(client);
		preface.add(new Paragraph(" "));

		Paragraph sub1 = new Paragraph(messages.getText("rsmm.report.levelcaracteristics")+" 5", catFontSimple);
		sub1.setAlignment(Element.ALIGN_LEFT);
		preface.add(sub1);
		preface.add(new Paragraph(" "));

		Paragraph txt = new Paragraph(messages.getText("rsmm.report.level5caracteristics")+":", subFont);
		txt.setAlignment(Element.ALIGN_LEFT);
		preface.add(txt);
		
		preface.add(caracteristicas[4]);		
		document.add(preface);
	}

	/*
	public void addMainChart(PdfWriter writer, Document document) throws Exception {
		
		absText(writer, messages.getText("activity.newreport.executiveresume"), 220, 520, 14);
        CheckListData checkListData = result.getActivity().getDefinition().getCheckList();

		int width = 400;
		int height = 200;
	    //Number[][] data = new Integer[2][checkListData.getPoints().size()-4];
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    PdfPTable table = new PdfPTable(1);
	    table.setHeaderRows(0);
	    Iterator itPoints = checkListData.getSortPoints();
	    int index = 1;
        while(itPoints.hasNext()) {
        	PointData point = (PointData)itPoints.next();
        	if(index > prePoints && (toPoints == -1 || index < toPoints)) {
                String pointIndex = String.valueOf(index-prePoints);
                String phraseIndex = (sixPoints) ? messages.getText(point.getText()) : pointIndex+" - "+messages.getText(point.getText());

                PdfPCell pdfCell = new PdfPCell(new Phrase(phraseIndex, cell));
        		pdfCell.setBorder(0);
        		table.addCell(pdfCell);
        		
        		int total = point.getRelevantItems(dataType).size();
        		int wrong = dataType.getPointCount(point.getId());
        		Integer wrongValue = (total == 0) ? 0: wrong * 100 / total;
                Integer rightValue = 100 - wrongValue.intValue();
                if(rightValue.intValue() > 0)
                	dataset.addValue(rightValue, "A", pointIndex);
                if(wrongValue.intValue() > 0)
                	dataset.addValue(wrongValue, "B", pointIndex);
                values.put(String.valueOf(point.getId()), rightValue);
        	}
        	index++;
        }		    

        PdfPCell space = new PdfPCell(new Phrase(" ",cellBold));
		space.setBorder(0);
        table.addCell(space);
        PdfPCell cell = (plus) ? new PdfPCell(new Phrase("-- "+messages.getText("activity.report.fleetgeneral"),cellBold)) : new PdfPCell(new Phrase("-- "+messages.getText("activity.report.fleetmedia"),cellBold));
		cell.setBorder(0);
        table.addCell(cell);
	        
        JFreeChart chart =  ChartFactory.createStackedBarChart(
	                "",  // chart title
	                "", "",
	                dataset,      // data
	                PlotOrientation.VERTICAL,
	                true,         // include legend
	                true,
	                true
	            );
	        
        final CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setMaximumBarWidth(.03f);
        
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        chart.setBorderPaint(Color.WHITE);
        chart.setBorderStroke(null);
        chart.setBorderVisible(false);
        chart.getLegend().setVisible(false);
        
	    plot.getRenderer().setSeriesPaint(1, new Color(183, 87, 87));
	    plot.getRenderer().setSeriesPaint(0, new Color(87, 179, 93));
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
        
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
		chart.draw(graphics2d, rectangle2d);
 
		graphics2d.setColor(Color.BLACK);
		graphics2d.setStroke(new BasicStroke(2));
	    itPoints = checkListData.getSortPoints();
	    index = 1;
	    int x = 0;
	    if(activityType.equals(CEPAActivityConstants.BTW1_NEW_FORM_TABLET) || activityType.equals(CEPAActivityConstants.BTWPLUS1_TABLET) 
	    		|| activityType.equals(CEPAActivityConstants.EBTW_PLUS_TABLET) 
	    		|| activityType.equals(CEPAActivityConstants.BTWBASIC_TABLET)  || activityType.equals(CEPAActivityConstants.BTWBASIC_ONBOARD_TABLET)
	    		|| activityType.equals(CEPAActivityConstants.BTWPLUS1_COACHING_TABLET) || activityType.equals(CEPAActivityConstants.BTWPLUS1_FAMILY_TABLET)) {
	    	x = 38;
	    } else if(activityType.equals(CEPAActivityConstants.BTW2_NEW_FORM_TABLET) || activityType.equals(CEPAActivityConstants.BTWPLUS2_TABLET)  || activityType.equals(CEPAActivityConstants.EBTWPLUS_SKIDCAR_TABLET)
	    		|| activityType.equals(CEPAActivityConstants.BTWPLUS2_TABLET_CON_SKID) || activityType.equals(CEPAActivityConstants.EBTW_DOS_PLUS_TABLET)) {
	    	x = 40;
	    }
	    
        while(itPoints.hasNext()) {
        	PointData point = (PointData)itPoints.next();
    		if(index > prePoints && (toPoints == -1 || index < toPoints)) {
    			String pointKey = point.getPointKey(dataType);
	        	Integer value = (media.containsKey(pointKey)) ? 100 - media.get(pointKey) : 100; 
	        	if(activityType.equals(CEPAActivityConstants.BTW1_NEW_FORM_TABLET) || activityType.equals(CEPAActivityConstants.BTWPLUS1_TABLET)
	        			|| activityType.equals(CEPAActivityConstants.EBTW_PLUS_TABLET)
	   	    		    || activityType.equals(CEPAActivityConstants.BTWBASIC_TABLET)  || activityType.equals(CEPAActivityConstants.BTWBASIC_ONBOARD_TABLET)
	        			|| activityType.equals(CEPAActivityConstants.BTWPLUS1_COACHING_TABLET) || activityType.equals(CEPAActivityConstants.BTWPLUS1_FAMILY_TABLET)) {
	        		x += 24;
	        		if(index == 7) {
	        			x+=1;
	        		}else if(index != 6 && (index % 4 == 0 || index % 5 == 0)) {
	        			x+=1;
        			}
	        		int y = 170-Math.round(value*152/100);
	        		graphics2d.drawLine(x,y,x+9,y);
	    	    } else if(activityType.equals(CEPAActivityConstants.BTW2_NEW_FORM_TABLET) || activityType.equals(CEPAActivityConstants.BTWPLUS2_TABLET)  || activityType.equals(CEPAActivityConstants.EBTWPLUS_SKIDCAR_TABLET)
	    	    		|| activityType.equals(CEPAActivityConstants.BTWPLUS2_TABLET_CON_SKID) || activityType.equals(CEPAActivityConstants.EBTW_DOS_PLUS_TABLET)) {
	        		x += 20;
	        		if(index % 7 == 0)
	        			x--;
	        		int y = 170-Math.round(value*152/100);
	        		graphics2d.drawLine(x,y,x+10,y);
	        	}
    		}
        	index++;
        }
         
        graphics2d.dispose();
        contentByte.addTemplate(template, 20, 315);
	 
        table.setTotalWidth(150);
        table.writeSelectedRows(0, -1, 430, 520, writer.getDirectContent());
	}
	
	public void addMainChartOnBoard(PdfWriter writer, Document document) throws Exception {
		
		absText(writer, messages.getText("activity.newreport.executiveresume"), 220, 520, 14);
        CheckListData checkListData = result.getActivity().getDefinition().getCheckList();

		int width = 350;
		int height = 200;
	    //Number[][] data = new Integer[2][checkListData.getPoints().size()-4];
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	    PdfPTable table = new PdfPTable(1);
	    table.setHeaderRows(0);
	    Iterator itPoints = checkListData.getSortPoints();
	    int index = 1;
        while(itPoints.hasNext()) {
        	PointData point = (PointData)itPoints.next();
        	if(index > prePoints && (toPoints == -1 || index < toPoints)) {
            	StringTokenizer tokenizer = new StringTokenizer(messages.getText(point.getText()));
            	String pointIndex = tokenizer.nextToken();
            	if(pointIndex.endsWith(".")) {
            		pointIndex = pointIndex.substring(0,pointIndex.indexOf("."));
            	}
            	String phraseIndex = messages.getText(point.getText());

                PdfPCell pdfCell = new PdfPCell(new Phrase(phraseIndex, cell));
        		pdfCell.setBorder(0);
        		table.addCell(pdfCell);
        		
        		int total = point.getRelevantItems(dataType).size();
        		int wrong = dataType.getPointCount(point.getId());
        		Integer wrongValue = wrong * 100 / total;
                Integer rightValue = 100 - wrongValue.intValue();
                if(rightValue.intValue() > 0)
                	dataset.addValue(rightValue, "A", pointIndex);
                if(wrongValue.intValue() > 0)
                	dataset.addValue(wrongValue, "B", pointIndex);
                values.put(String.valueOf(point.getId()), rightValue);
        	}
        	index++;
        }		    

        PdfPCell space = new PdfPCell(new Phrase(" ",cellBold));
		space.setBorder(0);
        table.addCell(space);
        PdfPCell cell = (plus) ? new PdfPCell(new Phrase("-- "+messages.getText("activity.report.fleetgeneral"),cellBold)) : new PdfPCell(new Phrase("-- "+messages.getText("activity.report.fleetmedia"),cellBold));
		cell.setBorder(0);
        table.addCell(cell);
	        
        JFreeChart chart =  ChartFactory.createStackedBarChart(
	                "",  // chart title
	                "", "",
	                dataset,      // data
	                PlotOrientation.VERTICAL,
	                true,         // include legend
	                true,
	                true
	            );
	        
        final CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setMaximumBarWidth(.05f);
        
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        chart.setBorderPaint(Color.WHITE);
        chart.setBorderStroke(null);
        chart.setBorderVisible(false);
        chart.getLegend().setVisible(false);
        
	    plot.getRenderer().setSeriesPaint(1, new Color(183, 87, 87));
	    plot.getRenderer().setSeriesPaint(0, new Color(87, 179, 93));
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
        
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
		chart.draw(graphics2d, rectangle2d);
 
		graphics2d.setColor(Color.BLACK);
		graphics2d.setStroke(new BasicStroke(2));
	    itPoints = checkListData.getSortPoints();
	    index = 1;
	    int x = (activityType.equals(CEPAActivityConstants.BTWPLUS_PESADOS_PISTA_TABLET)) ? 59 : 60;
	    
        while(itPoints.hasNext()) {
        	PointData point = (PointData)itPoints.next();
    		if(index > prePoints && (toPoints == -1 || index < toPoints)) {
    			String pointKey = point.getPointKey(dataType);
	        	Integer value = (media.containsKey(pointKey)) ? 100 - media.get(pointKey) : 100; 
        		int y = 170-Math.round(value*152/100);
        		if(activityType.equals(CEPAActivityConstants.BTWPLUS_PESADOS_PISTA_TABLET)){
            		graphics2d.drawLine(x,y,x+13,y);
        			x += (index % 3 == 0) ? 26 : 28;
        		}else {
            		graphics2d.drawLine(x,y,x+12,y);
        			x += (index % 3 == 0) ? 30 : 31;
        		}
    		}
        	index++;
        }
         
        graphics2d.dispose();
        contentByte.addTemplate(template, 20, 315);
	 
        table.setTotalWidth(200);
        table.writeSelectedRows(0, -1, 380, 480, writer.getDirectContent());
	}

	private void addTechnicalAspects(PdfWriter writer, Document document) throws Exception {
		
		Image back = Image.getInstance(TEMP_DIRECTORY_JASPERREPORT+"images//back1.jpg");
		int line1 = 270;
		back.setAbsolutePosition(20,line1);
        document.add(back);
        
        absText(writer, messages.getText("generic.activity.technicalaspectsvalued"), 200, line1+8, 12);
        
        Paragraph technical = new Paragraph(messages.getText("query.activity.generalreport.technicalaspecttextNew"), subFont);
        technical.setAlignment(Element.ALIGN_LEFT);
		document.add(technical);
	}
	
	private void addMainObservations(PdfWriter writer) {
		if(result.getObsSelected() != null) {
		    Iterator<CEPAScheduleActivityRegistryObservationSelectedData> observations = result.getObsSelected().iterator();
		    if(observations.hasNext()) {
		        PdfPTable tableObs = new PdfPTable(1);
		        tableObs.setHeaderRows(1);
			    PdfPCell cellHeader = new PdfPCell(new Phrase(messages.getText("generic.activity.observations"),obsFont));
			    cellHeader.setBorder(0);
			    tableObs.addCell(cellHeader);
		        while(observations.hasNext()) {
		    	    PdfPCell cellObs = new PdfPCell(new Phrase(messages.getText(observations.next().getObservation().getText()),subFont));
		    	    cellObs.setBorder(0);
		        	tableObs.addCell(cellObs);
		        }
				if(activityType.equals(CEPAActivityConstants.TRAINING_ON_THE_JOB_TABLET) && result.getObs() != null && result.getObs().length() > 0) {
		    	    PdfPCell cellObs = new PdfPCell(new Phrase(result.getObs(),cellBold));
		    	    cellObs.setBorder(0);
		        	tableObs.addCell(cellObs);
				}
		        tableObs.setTotalWidth(580);
		        tableObs.writeSelectedRows(0, -1, 20, 190, writer.getDirectContent());
		    }
		}
	}
	
	private void addPointDescription(PdfWriter writer, Document document, PointData point) throws Exception {

		Image back = Image.getInstance(TEMP_DIRECTORY_JASPERREPORT+"images//back1.jpg");
		int line1 = 740;
		back.setAbsolutePosition(20,line1);
        document.add(back);
        
        String title = messages.getText(point.getText());
        int xTitle = (580 - (title.length()*6)) / 2;
        absText(writer, title, xTitle, line1+8, 12);
        
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        String text = messages.getText(point.getText()+".concept");
        int extra = 0;
        if(text.length() < 200) {
        	extra = -30;
        } else if (text.length() < 400) {
        	extra = -15;
        } else if (text.length() > 600) {
        	extra = 30;
        } else if (text.length() > 500) {
        	extra = 15;
        }
        Paragraph technical = new Paragraph(text, subFont);
        technical.setAlignment(Element.ALIGN_LEFT);
		document.add(technical);
        
        for(int i = 0; i < 5; i++)
        	document.add(new Paragraph(" "));
        PdfPTable table = new PdfPTable(3);
	    table.setHeaderRows(1);
	    PdfPCell cell1 = new PdfPCell(new Phrase(""));
	    cell1.setBorderColor(BaseColor.LIGHT_GRAY);
	    cell1.setBorderWidthLeft(0);
	    cell1.setBorderWidthTop(0);
   		cell1.setFixedHeight(25f);
	    table.addCell(cell1);
	    PdfPCell cell2 = new PdfPCell(new Phrase("P",whiteFont));
	    cell2.setBackgroundColor(new BaseColor(87, 179, 93));
   		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell2.setBorder(0);
	    table.addCell(cell2);
	    PdfPCell cell3 = new PdfPCell(new Phrase("N",whiteFont));
	    cell3.setBackgroundColor(new BaseColor(183, 87, 87));
   		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
   		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell3.setBorder(0);
	    table.addCell(cell3);
	    table.setWidths(new int[]{300,30,30});
	   // table.setPaddingTop(10);
	    Collection<ItemData> items = point.getRelevantItems(dataType);
	    Collections.sort((List)items);
	    Iterator itItems = items.iterator();
        while(itItems.hasNext()) {
            document.add(new Paragraph(" "));
        	ItemData item = (ItemData)itItems.next();
       		PdfPCell pdfCell = new PdfPCell(new Phrase("  "+messages.getText(item.getText()), subFont));
       		pdfCell.setBorderWidthLeft(0);
       		pdfCell.setBorderWidthTop(0);
       		pdfCell.setBorderColor(BaseColor.LIGHT_GRAY);
       		pdfCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
       		pdfCell.setFixedHeight(20f);
       		table.addCell(pdfCell);
       		boolean error = dataType.isItem(point.getId(),item.getId());
       		if(error) {
           		PdfPCell pdfCell1 = new PdfPCell(new Phrase(""));
           		pdfCell1.setBorderColor(BaseColor.LIGHT_GRAY);
           		Image img = Image.getInstance(TEMP_DIRECTORY_JASPERREPORT+"images//rojo.jpg");
           		img.setPaddingTop(5);
           		PdfPCell pdfCell2 = new PdfPCell(img);
           		pdfCell2.setBorderColor(BaseColor.LIGHT_GRAY);
           		pdfCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           		pdfCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
           		table.addCell(pdfCell1);
           		table.addCell(pdfCell2);
       		}else {
           		PdfPCell pdfCell1 = new PdfPCell(new Phrase(""));
           		pdfCell1.setBorderColor(BaseColor.LIGHT_GRAY);
           		Image img = Image.getInstance(TEMP_DIRECTORY_JASPERREPORT+"images//verde.jpg");
           		img.setAbsolutePosition(5, 5);
           		PdfPCell pdfCell2 = new PdfPCell(img);
           		pdfCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
           		pdfCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
           		pdfCell2.setBorderColor(BaseColor.LIGHT_GRAY);
           		table.addCell(pdfCell2);
           		table.addCell(pdfCell1);
       		}
        }		    
        table.setTotalWidth(360);
        table.writeSelectedRows(0, -1, 20, 670-extra, writer.getDirectContent());
        
		int width = 150;
		int height = 40+items.size()*20;
	    Number[][] data = new Integer[2][2];
	    
	    data[0][0] = values.get(String.valueOf(point.getId()));
	    data[1][0] = 100 - values.get(String.valueOf(point.getId())).intValue();

	    String pointKey = point.getPointKey(dataType);
	    if((media.containsKey(pointKey))) {
		    data[1][1] = media.get(pointKey);
		    data[0][1] = 100 - media.get(pointKey).intValue();
	    }else {
		    data[0][1] = 100;
		    data[1][1] = 0;
	    }
        //CategoryDataset dataset = DatasetUtilities.createCategoryDataset("A", "", data);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if(data[0][0].intValue() >= 0)
        	dataset.addValue(data[0][0], "A", messages.getText("activity.report.performance"));
        if(data[1][0].intValue() >= 0)
        	dataset.addValue(data[1][0], "B", messages.getText("activity.report.performance"));

        String media = (plus) ? "activity.report.fleetgeneral" : "activity.report.fleetmedia";
        if(data[0][1].intValue() >= 0)
        	dataset.addValue(data[0][1], "A", messages.getText(media));
        if(data[1][1].intValue() >= 0)
        	dataset.addValue(data[1][1], "B", messages.getText(media));
        
        
        JFreeChart chart =  ChartFactory.createStackedBarChart(
	                "",  // chart title
	                "", "",
	                dataset,      // data
	                PlotOrientation.VERTICAL,
	                false,         // include legend
	                true,
	                true
	            );
	        
        final CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setMaximumBarWidth(.2f);
        
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.getRangeAxis().setTickLabelsVisible(false);
        plot.getRangeAxis().setTickMarksVisible(false);
        plot.getDomainAxis().setTickLabelFont(new java.awt.Font(FONT_REPORT_NAME, java.awt.Font.PLAIN, 7));
        plot.getDomainAxis().setMaximumCategoryLabelLines(2);
        chart.setBorderPaint(Color.WHITE);
        chart.setBorderStroke(null);
        chart.setBorderVisible(false);
        //chart.getLegend().setVisible(false);
        
	    plot.getRenderer().setSeriesPaint(1, new Color(183, 87, 87));
	    plot.getRenderer().setSeriesPaint(0, new Color(87, 179, 93));
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
        
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height, new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);
		chart.draw(graphics2d, rectangle2d);
 
		graphics2d.setColor(Color.BLACK);
		graphics2d.setStroke(new BasicStroke(.5f));
        int x = 20;
   		int y = 8 + height/20;
   		y += data[1][1].intValue() * (height/100);
   		//graphics2d.drawLine(x,y,x+110,y);
         
        graphics2d.dispose();
        contentByte.addTemplate(template, 400, 640 - (extra + items.size()*20));

	    Iterator<PointObservationSelectedData> observations = result.getPointObservations(point).iterator();
	    if(observations.hasNext()) {
	        document.add(new Paragraph(" "));
	        PdfPTable tableObs = new PdfPTable(1);
	        tableObs.setHeaderRows(1);
		    PdfPCell cellHeader = new PdfPCell(new Phrase(messages.getText("generic.activity.observations"),obsFont));
		    cellHeader.setBorder(0);
		    tableObs.addCell(cellHeader);
	        while(observations.hasNext()) {
	            document.add(new Paragraph(" "));
	    	    PdfPCell cellObs = new PdfPCell(new Phrase(" - "+messages.getText(observations.next().getObservation().getText()),subFont));
	    	    cellObs.setBorder(0);
	        	tableObs.addCell(cellObs);
	        }
	        tableObs.setTotalWidth(540);
	        tableObs.writeSelectedRows(0, -1, 32, 670-(extra+40+(items.size()*20)), writer.getDirectContent());
	    }

        Paragraph recomendationTxt = new Paragraph(messages.getText("generic.messages.recomendation"), obsFont);
        recomendationTxt.setAlignment(Element.ALIGN_LEFT);
		document.add(recomendationTxt);

        Paragraph recomendation = new Paragraph(messages.getText(point.getText()+".recomendation"), subFont);
        recomendation.setAlignment(Element.ALIGN_LEFT);
		document.add(recomendation);
	}
	*/
	private void absText(PdfWriter writer, String text, int x, int y, int size) {
		absText(writer, text, x, y, size, false);
	}
	
	private void absText(PdfWriter writer, String text, int x, int y, int size, boolean right) {
	    try {
	    	PdfContentByte cb = writer.getDirectContent();
	    	BaseFont bf = BaseFont.createFont(Util.FONT_REPORT_NAME, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
	    	cb.saveState();
	    	cb.beginText();
	    	cb.setFontAndSize(bf, size);
	    	if(right) {
	    		ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(text, subFont), x, y, 0);
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
