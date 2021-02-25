/**
 * CEPA
 * Assesment
 */
package assesment.communication.assesment;

import java.util.Date;

import assesment.communication.language.Text;


public class AssesmentListData implements Comparable<AssesmentListData> {

    public static final int EDITABLE = 0; 
    public static final int NO_EDITABLE = 1; 
    public static final int ENDED = 2; 
    
    private Integer id;
    
    private String name;
    private String corporation;
    
    private Date start;
    private Date end;
    
    public AssesmentListData() {
    }

    public AssesmentListData(Object[] data, Text messages) {
        this.id = (Integer) data[0];
        this.name = messages.getText((String) data[1]).trim();
        if(data.length > 2) {
        	this.corporation = (String) data[2];
        	this.start = (Date)data[3];
        	this.end = (Date)data[4];
        }
 	}

	public AssesmentListData(AssesmentAttributes data, Text messages) {
        this.id = data.getId();
        this.name = messages.getText(data.getName()).trim();
    	this.start = data.getStart();
    	this.end = data.getEnd();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public int compareTo(AssesmentListData o) {
		return name.compareTo(o.name);
	}

}
