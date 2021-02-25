package assesment.communication.report;


import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;

public class ModuleSurveyReportDataSource implements JRDataSource {

	private int index = -1;
	private int totalYes = 0;
	private int totalPartial = 0;
	private int totalNo = 0;
	private double totalPoints = 0.0;
	private Integer[] moduleIds = {724, 725, 726, 727, 728, 729, 730};
	private HashMap<Integer, Double> points = new HashMap<Integer, Double>();
	private HashMap<Integer,Object[]> values = new HashMap<Integer,Object[]>();
	
	private Text messages;
	
	public ModuleSurveyReportDataSource(Collection results, Text messages) {
		this.messages = messages;
		for(int i = 0; i < moduleIds.length; i++) {
			Object[] v = {0, 0, 0, 0.0};
			values.put(moduleIds[i], v);
		}
		points.put(new Integer(46165), new Double(0.5));
		points.put(new Integer(46168), new Double(0.5));
		points.put(new Integer(46180), new Double(0.5));
		points.put(new Integer(46183), new Double(0.5));
		points.put(new Integer(46186), new Double(0.5));
		points.put(new Integer(46189), new Double(0.5));
		points.put(new Integer(46198), new Double(0.5));
		points.put(new Integer(46201), new Double(0.5));
		points.put(new Integer(46207), new Double(0.5));
		points.put(new Integer(46219), new Double(0.5));
		points.put(new Integer(46255), new Double(0.5));
		points.put(new Integer(46258), new Double(0.5));
		points.put(new Integer(46279), new Double(0.5));
		points.put(new Integer(46282), new Double(0.5));
		points.put(new Integer(46285), new Double(0.5));
		points.put(new Integer(46291), new Double(0.5));
		points.put(new Integer(46294), new Double(0.5));
		points.put(new Integer(46297), new Double(0.5));
		points.put(new Integer(46303), new Double(0.5));
		points.put(new Integer(46309), new Double(0.5));
		points.put(new Integer(46312), new Double(0.5));
		points.put(new Integer(46315), new Double(0.5));
		points.put(new Integer(46324), new Double(0.5));
		points.put(new Integer(46327), new Double(0.5));
		points.put(new Integer(46357), new Double(0.5));
		points.put(new Integer(46360), new Double(0.5));
		points.put(new Integer(46372), new Double(0.5));
		points.put(new Integer(46375), new Double(0.5));
		points.put(new Integer(46174), new Double(0.75));
		points.put(new Integer(46177), new Double(0.75));
		points.put(new Integer(46192), new Double(0.75));
		points.put(new Integer(46204), new Double(0.75));
		points.put(new Integer(46210), new Double(0.75));
		points.put(new Integer(46225), new Double(0.75));
		points.put(new Integer(46261), new Double(0.75));
		points.put(new Integer(46333), new Double(0.75));
		points.put(new Integer(46336), new Double(0.75));
		points.put(new Integer(46363), new Double(0.75));
		points.put(new Integer(46366), new Double(0.75));
		points.put(new Integer(46369), new Double(0.75));
		points.put(new Integer(46378), new Double(0.75));
		points.put(new Integer(46381), new Double(0.75));
		
		Iterator<Object[]> it = results.iterator();
		while(it.hasNext()) {
			Object[] data = it.next();
			Integer module = (Integer) data[0];
			Object[] v = values.get(module);
			Integer answer = (Integer) data[1];
			Integer order = (Integer) data[2];
			switch(order.intValue()) {
				case 1:
					v[0] = ((Integer)v[0])+1;
					v[3] = ((Double)v[3])+1.0;
					totalPoints += 1.0;
					totalYes++;
					break;
				case 2:
					v[1] = ((Integer)v[1])+1;
					if(points.containsKey(answer)) {
						v[3] = ((Double)v[3])+points.get(answer);
						totalPoints += points.get(answer);
					}
					totalPartial++;
					break;
				case 3:
					v[2] = ((Integer)v[2])+1;
					totalNo++;
					break;
			}
		}
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
    	if(field.getName().equals("modules")) {
    		return messages.getText("assesment151.module"+moduleIds[index]+".name");
    	}
    	if(field.getName().equals("cantidad")) {
    		if(index == 0)
    			return new Double(totalPoints);
    		if(index == 1)
    			return new Double(63.0 - totalPoints);
    		return new Integer(0);
    	}
    	if(field.getName().equals("texto")) {
    		return String.valueOf(index);
    	}
    	if(field.getName().equals("yes")) {
    		return values.get(moduleIds[index])[0];
    	}
    	if(field.getName().equals("partial")) {
    		return values.get(moduleIds[index])[1];
    	}
    	if(field.getName().equals("no")) {
    		return values.get(moduleIds[index])[2];
    	}
    	if(field.getName().equals("points")) {
    		return values.get(moduleIds[index])[3];
    	}
    	if(field.getName().equals("result")) {
    		double points = ((Double)values.get(moduleIds[index])[3]).doubleValue();
    		double count = ((Integer)values.get(moduleIds[index])[0]).doubleValue() + ((Integer)values.get(moduleIds[index])[1]).doubleValue() + ((Integer)values.get(moduleIds[index])[2]).doubleValue();
    		double v = points * 100.0 / count;
    		if(v < 50)
    			return AssesmentData.FLASH_PATH+"/images/Rectangle-red.png";
    		else if(v < 85)
    			return AssesmentData.FLASH_PATH+"/images/Rectangle-yellow.png";
    		else
    			return AssesmentData.FLASH_PATH+"/images/Rectangle-green.png";
    	}
		return new Integer(1);
	}

	@Override
	public boolean next() throws JRException {
		if(index < moduleIds.length-1) {
			index++;
			return true;
		}
		return false;
	}

	public int getTotalYes() {
		return totalYes;
	}

	public int getTotalPartial() {
		return totalPartial;
	}

	public int getTotalNo() {
		return totalNo;
	}

	public double getTotalPoints() {
		return totalPoints;
	}

	public String getTotalImage() {
		double v = totalPoints * 100.0 / 63.0;
		if(v < 50)
			return AssesmentData.FLASH_PATH+"/images/Rectangle-red.png";
		else if(v < 85)
			return AssesmentData.FLASH_PATH+"/images/Rectangle-yellow.png";
		else
			return AssesmentData.FLASH_PATH+"/images/Rectangle-green.png";
	}

	public Integer getTotalPercent() {
		double v = totalPoints * 100.0 / 63.0;
		return new Integer((int)Math.round(v));
	}
}
