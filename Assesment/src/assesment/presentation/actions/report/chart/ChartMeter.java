package assesment.presentation.actions.report.chart;

import java.awt.Color;
import java.awt.Font;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MeterPlot;

public class ChartMeter  implements JRChartCustomizer {

	public void customize(JFreeChart chart, JRChart jasperChart) {
		
		MeterPlot plot = (MeterPlot)chart.getPlot();
		plot.setMeterAngle(180);
		plot.setTickLabelsVisible(false);
		plot.setDrawBorder(false);
		plot.setValueFont(new Font("Arial",0,0));
		plot.setBackgroundPaint(Color.WHITE);
		plot.setNeedlePaint(Color.BLACK);
		//plot.setTickSize(0);
		//plot.setTickPaint(null);
		plot.setTickSize(5);
		//plot.getTickLabelPaint().drawBackgroundImage(arg0, arg1)(arg0, arg1)
	}
}
