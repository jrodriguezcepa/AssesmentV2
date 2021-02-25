package assesment.presentation.actions.report.chart;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.HorizontalAlignment;

public class ChartBar  implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart) {
		LegendTitle legend = chart.getLegend();
		legend.setBorder(0, 0, 0, 0);
		legend.setHorizontalAlignment(HorizontalAlignment.RIGHT);
		
		BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
		
		IntervalMarker marker1 = new IntervalMarker(0,25);
		marker1.setAlpha(0.08f);
		IntervalMarker marker2 = new IntervalMarker(25,50);
		marker2.setAlpha(0.15f);
		IntervalMarker marker3 = new IntervalMarker(50,75);
		marker3.setAlpha(0.08f);
		IntervalMarker marker4 = new IntervalMarker(75,100);
		marker4.setAlpha(0.15f);
		chart.getCategoryPlot().addRangeMarker(marker1);
		chart.getCategoryPlot().addRangeMarker(marker2);
		chart.getCategoryPlot().addRangeMarker(marker3);
		chart.getCategoryPlot().addRangeMarker(marker4);

		renderer.setMaximumBarWidth(0.10);

	}
}
