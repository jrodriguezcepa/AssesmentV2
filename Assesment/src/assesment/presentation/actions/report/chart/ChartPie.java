package assesment.presentation.actions.report.chart;

import java.awt.Color;
import java.awt.Font;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;

public class ChartPie  implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart) {
		LegendTitle legend = chart.getLegend();
		if(legend != null) {
			legend.setBorder(0, 0, 0, 0);
			legend.setMargin(20,10,10,100);
		}
		
		PiePlot plot = (PiePlot)chart.getPlot();
		plot.setLabelLinksVisible(false);
		plot.setLabelFont(new Font("Arial",0,0));
		plot.setLabelShadowPaint(Color.WHITE);
		plot.setLabelOutlinePaint(Color.WHITE);
		
		plot.setShadowPaint(Color.WHITE);
	}
}
