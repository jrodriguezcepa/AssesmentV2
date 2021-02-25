/**
 * CEPA
 * Assesment
 */
package assesment.persistence.assesment.tables;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import assesment.communication.assesment.CategoryData;
import assesment.communication.assesment.GroupData;
import assesment.persistence.corporation.tables.Corporation;

public class Group {

    private Integer id;    
    private Corporation corporation; 
    private String name;
    private Boolean initialText;
    private String image;
    private Integer layout;
    private Boolean repeatable;
    
    private Set<Category> categorySet;

    public Group() {
    }

	public Group(GroupData groupData, Session session) {
		setData(groupData, session);
	}

	public void setData(GroupData groupData, Session session) {
		this.id = groupData.getId();
		corporation = (Corporation)session.load(Corporation.class, groupData.getCorporation());
		this.name = groupData.getName();
		this.initialText = groupData.isInitialText();
		this.image = groupData.getImage();
		this.layout = groupData.getLayout();
		this.repeatable = groupData.isRepeatable();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Corporation getCorporation() {
		return corporation;
	}

	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Category> getCategorySet() {
		return categorySet;
	}

	public void setCategorySet(Set<Category> categorySet) {
		this.categorySet = categorySet;
	}

	public GroupData getData(Session session) {
		GroupData group = new GroupData(id, corporation.getId() ,name, initialText.booleanValue(), image, layout, repeatable.booleanValue());
		group.setCorporationName(corporation.getName());
		if(initialText) {
			Query q = session.createSQLQuery("SELECT text, language FROM generalmessages WHERE labelkey = 'assessment.group.id"+id+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
			Iterator it = q.list().iterator();
			while(it.hasNext()) {
				Object[] data = (Object[]) it.next();
				if(data[1].equals("es"))
					group.setEsInitialText((String)data[0]);
				if(data[1].equals("en"))
					group.setEnInitialText((String)data[0]);
				if(data[1].equals("pt"))
					group.setPtInitialText((String)data[0]);
			}
		}
		Iterator it = categorySet.iterator();
		while(it.hasNext()) {
			CategoryData c = ((Category)it.next()).getData(session);
			if(c != null)
				group.addCategory(c);
		}
		return group;
	}

	public GroupData getData(Session session, String user) {
		GroupData group = new GroupData(id, corporation.getId() ,name, initialText.booleanValue(), image, layout, repeatable.booleanValue());
		group.setCorporationName(corporation.getName());
		if(initialText) {
			Query q = session.createSQLQuery("SELECT text, language FROM generalmessages WHERE labelkey = 'assessment.group.id"+id+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
			Iterator it = q.list().iterator();
			while(it.hasNext()) {
				Object[] data = (Object[]) it.next();
				if(data[1].equals("es"))
					group.setEsInitialText((String)data[0]);
				if(data[1].equals("en"))
					group.setEnInitialText((String)data[0]);
				if(data[1].equals("pt"))
					group.setPtInitialText((String)data[0]);
			}
		}
		Iterator it = categorySet.iterator();
		while(it.hasNext()) {
			CategoryData c = ((Category)it.next()).getData(session, user);
			if(c != null)
				group.addCategory(c);
		}
		return group;
	}

	public GroupData getNoPdfData(Session session) {
		GroupData group = new GroupData(id, corporation.getId() ,name, initialText.booleanValue(), image, layout, repeatable.booleanValue());
		group.setCorporationName(corporation.getName());
		if(initialText) {
			Query q = session.createSQLQuery("SELECT text, language FROM generalmessages WHERE labelkey = 'assessment.group.id"+id+"'").addScalar("text", Hibernate.STRING).addScalar("language", Hibernate.STRING);
			Iterator it = q.list().iterator();
			while(it.hasNext()) {
				Object[] data = (Object[]) it.next();
				if(data[1].equals("es"))
					group.setEsInitialText((String)data[0]);
				if(data[1].equals("en"))
					group.setEnInitialText((String)data[0]);
				if(data[1].equals("pt"))
					group.setPtInitialText((String)data[0]);
			}
		}
		Iterator it = categorySet.iterator();
		while(it.hasNext()) {
			CategoryData c = ((Category)it.next()).getNoPdfData(session);
			if(c != null) {
				group.addCategory(c);
			}
		}
		return group;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getInitialText() {
		return initialText;
	}

	public void setInitialText(Boolean initialText) {
		this.initialText = initialText;
	}

	public Integer getLayout() {
		return layout;
	}

	public void setLayout(Integer layout) {
		this.layout = layout;
	}

	public Boolean getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}

}
