/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import assesment.communication.assesment.AssesmentAttributes;
import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.GroupData;

public class Category {

    private Integer id;
    
    private Group groupId;
    
    private Integer type; 
    
    private String key;
    private String image;
    
    private Integer ord;

    private Integer titleColor;
    private Integer itemColor;
    
    public Category() {
    }

	public Category(CategoryData categoryData, Session session) {
		setData(categoryData, session);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Group getGroupId() {
		return groupId;
	}

	public void setGroupId(Group groupId) {
		this.groupId = groupId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	
	public Integer getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(Integer titleColor) {
		this.titleColor = titleColor;
	}

	public Integer getItemColor() {
		return itemColor;
	}

	public void setItemColor(Integer itemColor) {
		this.itemColor = itemColor;
	}

	public CategoryData getData(Session session) {
		CategoryData c = new CategoryData(id, groupId.getId() ,key, type, image, ord, titleColor, itemColor);
		Query q = session.createSQLQuery("SELECT text, language FROM generalmessages WHERE labelkey = '"+c.getKey()+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
		Iterator it = q.list().iterator();
		while(it.hasNext()) {
			Object[] data = (Object[]) it.next();
			if(data[1].equals("es"))
				c.setEsInitialText((String)data[0]);
			if(data[1].equals("en"))
				c.setEnInitialText((String)data[0]);
			if(data[1].equals("pt"))
				c.setPtInitialText((String)data[0]);
		}

		SQLQuery q2 = session.createSQLQuery("SELECT id, name, ac.astart, ac.aend, status, yellow, green, certificate, untilapproved, link, changename, showreport FROM assesments a " + 
				"JOIN assesmentcategories ac ON ac.assesment = a.id " + 
				"WHERE category = "+c.getId());
		Iterator it2 = q2.list().iterator();
		while(it2.hasNext()) {
			Object[] data = (Object[])it2.next();
			AssesmentAttributes assessment = new AssesmentAttributes((Integer) data[0]);
			String name = (String) data[1];
			if(((Boolean)data[10]).booleanValue()) {
				name = "group"+groupId.getId()+"."+name;
			}
			assessment.setName(name);
			assessment.setStart((Date) data[2]);
			assessment.setEnd((Date) data[3]);
			assessment.setStatus((Integer)data[4]);
			assessment.setYellow((Integer)data[5]);
			assessment.setGreen((Integer)data[6]);
			assessment.setCertificate((Boolean)data[7]);
			assessment.setUntilApproved((Boolean)data[8]);
			assessment.setLink((String)data[9]);
			assessment.setShowReport((Boolean)data[11]);
			c.addAssessment(assessment);
		}
		

		return c;
	}

	public CategoryData getData(Session session, String user) {
		CategoryData c = new CategoryData(id, groupId.getId() ,key, type, image, ord, titleColor, itemColor);
		Query q = session.createSQLQuery("SELECT text, language FROM generalmessages WHERE labelkey = '"+c.getKey()+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
		Iterator it = q.list().iterator();
		while(it.hasNext()) {
			Object[] data = (Object[]) it.next();
			if(data[1].equals("es"))
				c.setEsInitialText((String)data[0]);
			if(data[1].equals("en"))
				c.setEnInitialText((String)data[0]);
			if(data[1].equals("pt"))
				c.setPtInitialText((String)data[0]);
		}

		SQLQuery q2 = session.createSQLQuery("SELECT id, name, ac.astart, ac.aend, status, yellow, green, certificate, untilapproved, link, changename, showreport FROM assesments a " + 
				"JOIN assesmentcategories ac ON ac.assesment = a.id JOIN userassesments ua ON ua.assesment = a.id " + 
				"WHERE ua.loginname = '"+user+"' AND category = "+c.getId());
		Iterator it2 = q2.list().iterator();
		while(it2.hasNext()) {
			Object[] data = (Object[])it2.next();
			AssesmentAttributes assessment = new AssesmentAttributes((Integer) data[0]);
			String name = (String) data[1];
			if(((Boolean)data[10]).booleanValue()) {
				name = "group"+groupId.getId()+"."+name;
			}
			assessment.setName(name);
			assessment.setStart((Date) data[2]);
			assessment.setEnd((Date) data[3]);
			assessment.setStatus((Integer)data[4]);
			assessment.setYellow((Integer)data[5]);
			assessment.setGreen((Integer)data[6]);
			assessment.setCertificate((Boolean)data[7]);
			assessment.setUntilApproved((Boolean)data[8]);
			assessment.setLink((String)data[9]);
			assessment.setShowReport((Boolean)data[11]);
			c.addAssessment(assessment);
		}
		
		if(c.getAssesments().size() == 0)
			return null;
		return c;
	}


	public CategoryData getNoPdfData(Session session) {
		CategoryData c = new CategoryData(id, groupId.getId() ,key, type, image, ord, titleColor, itemColor);
		Query q = session.createSQLQuery("SELECT text, language FROM generalmessages WHERE labelkey = '"+c.getKey()+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
		Iterator it = q.list().iterator();
		while(it.hasNext()) {
			Object[] data = (Object[]) it.next();
			if(data[1].equals("es"))
				c.setEsInitialText((String)data[0]);
			if(data[1].equals("en"))
				c.setEnInitialText((String)data[0]);
			if(data[1].equals("pt"))
				c.setPtInitialText((String)data[0]);
		}

		SQLQuery q2 = session.createSQLQuery("SELECT id, name, ac.astart, ac.aend, status, yellow, green, certificate, untilapproved, link, changename FROM assesments a " + 
				"JOIN assesmentcategories ac ON ac.assesment = a.id " + 
				"WHERE link IS NULL AND category = "+c.getId());
		Iterator it2 = q2.list().iterator();
		while(it2.hasNext()) {
			Object[] data = (Object[])it2.next();
			AssesmentAttributes assessment = new AssesmentAttributes((Integer) data[0]);
			String name = (String) data[1];
			if(((Boolean)data[10]).booleanValue()) {
				name = "group"+groupId.getId()+"."+name;
			}
			assessment.setName(name);
			assessment.setStart((Date) data[2]);
			assessment.setEnd((Date) data[3]);
			assessment.setStatus((Integer)data[4]);
			assessment.setYellow((Integer)data[5]);
			assessment.setGreen((Integer)data[6]);
			assessment.setCertificate((Boolean)data[7]);
			assessment.setUntilApproved((Boolean)data[8]);
			assessment.setLink((String)data[9]);
			c.addAssessment(assessment);
		}
		
		if(c.getAssesments().size() == 0)
			return null;
		return c;
	}

	public void setData(CategoryData categoryData, Session session) {
		id = categoryData.getId();
		groupId = (Group)session.load(Group.class, categoryData.getGroupId());
		this.type = categoryData.getType();
		this.key = categoryData.getKey();
		this.ord = categoryData.getOrder();
		this.image = categoryData.getImage();
		this.titleColor = categoryData.getTitleColor();
		this.itemColor = categoryData.getItemColor();
	}

}
