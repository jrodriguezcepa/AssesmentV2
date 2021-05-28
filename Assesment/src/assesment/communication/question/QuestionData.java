/**
 * CEPA
 * Assesment
 */
package assesment.communication.question;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import assesment.communication.language.Text;

public class QuestionData implements Comparable {

    public static final int GENERIC = 0;
    public static final int NORMAL = 1;

    public static final int UNIT_KM = 0;
    public static final int UNIT_MILE = 1;

    public static final int NOT_TEST_QUESTION = 0;
    public static final int TEST_QUESTION = 1;
    
    public static final int TEXT = 1;
    public static final int DATE = 2;
    public static final int EXCLUDED_OPTIONS = 3;
    public static final int LIST = 4;
    public static final int KILOMETERS = 6;
    public static final int OPTIONAL_DATE = 7;
    public static final int OPTIONAL_DATE_NA = 71;
    public static final int INCLUDED_OPTIONS = 8;
    public static final int EMAIL = 9;
    public static final int COUNTRY = 10;
    public static final int TEXTAREA = 11;
    public static final int BIRTHDATE = 12;
    public static final int YOU_TUBE_VIDEO = 15;
    public static final int VIDEO = 16;

    private Integer id;
    private String key;
    private Integer module;

    private Integer order;
    private Integer type;

    private String image;
    
    private Collection<AnswerData> answers;
    private Integer testType;
    private Integer group;
	private boolean wrt;

    public QuestionData() {
    }

    public QuestionData(Integer id, String key, Integer module, Integer order, Integer type, String image, Integer testType, Integer group, Collection<AnswerData> answers) {
        this.id = id;
        this.key = key;
        this.module = module;
        this.order = order;
        this.type = type;
        this.image = image;
        this.testType = testType;
        this.group = group;
        this.answers = answers;
        this.wrt = false;
    }

    public QuestionData(Integer id, String key, Integer module, Integer order, Integer type, String image, Integer testType, Integer group, boolean wrt, Collection<AnswerData> answers) {
        this.id = id;
        this.key = key;
        this.module = module;
        this.order = order;
        this.type = type;
        this.image = image;
        this.testType = testType;
        this.group = group;
        this.answers = answers;
        this.wrt = wrt;
    }

    public Integer getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getType() {
        return type;
    }

    public Collection<AnswerData> getAnswers() {
        return answers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setAnswers(Collection<AnswerData> answers) {
        this.answers = answers;
    }

    public int compareTo(Object o) {
        return order.compareTo(((QuestionData)o).order);
    }

    public Iterator<AnswerData> getAnswerIterator() {
        if(answers == null) {
            return new LinkedList().iterator();
        }
        Collections.sort((List)answers);
        return answers.iterator();
    }

    public Iterator<AnswerData> getAnswerDisorder() {
    	Collection<Integer> used = new LinkedList<Integer>();
    	Collection<AnswerData> disorder = new LinkedList<AnswerData>();
    	AnswerData[] array = answers.toArray(new AnswerData[0]);
    	while(used.size() < answers.size()) {
    		Integer x = new Integer((int)Math.round(Math.random()*(array.length-1)));
    		while(used.contains(x)) {
    			x = new Integer((int)Math.round(Math.random()*(array.length-1)));
    		}
    		disorder.add(array[x.intValue()]);
    		used.add(x);
    	}

        return disorder.iterator();
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public int getAnswerSize() {
        if(answers == null) {
            return 0;
        }
        return answers.size();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTestType() {
        return testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Integer getGroup() {
        if(group == null) {
            return new Integer(0);
        }
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getHtmlType() {
    	switch(type.intValue()) {
			case QuestionData.DATE:
			case QuestionData.OPTIONAL_DATE:
			case QuestionData.OPTIONAL_DATE_NA:
			case QuestionData.BIRTHDATE:
				return "date";
			case QuestionData.EMAIL:
				return "email";
			case QuestionData.TEXT:
				return "text";
			case QuestionData.TEXTAREA:
				return "textarea";
			case QuestionData.KILOMETERS:
				return "number";
    	}
    	return "hidden";
    }

	public boolean containsAnswer(String value) {
		try {
			Iterator it = answers.iterator();
			while(it.hasNext()) {
				if(((AnswerData)it.next()).getId().intValue() == Integer.parseInt(value)) {
					return true;
				}
			}
		}catch (Exception e) {
		}
		return false;
	}
	
	public AnswerData findAnswer(Integer answer) {
		Iterator<AnswerData> it = answers.iterator();
		while(it.hasNext()) {
			AnswerData answerData = it.next();
			if(answerData.getId().equals(answer))
				return answerData;
		}
		return null;
	}

	public AnswerData findCorrectAnswer() {
		Iterator<AnswerData> it = answers.iterator();
		while(it.hasNext()) {
			AnswerData answerData = it.next();
			if(answerData.getResultType().equals(AnswerData.CORRECT))
				return answerData;
		}
		return null;
	}

	public boolean isWrt() {
		// TODO Auto-generated method stub
		return wrt;
	}

	public void setWrt(boolean wrt) {
		this.wrt = wrt;
	}

    public String getKey(Text messages) {
    	if(type.intValue() == QuestionData.YOU_TUBE_VIDEO) {
    		return "YouTube Video";
    	}
    	if(type.intValue() == QuestionData.VIDEO) {
    		return "Video";
    	}
        return messages.getText(key);
    }
    
    public String getKey(HashMap<String, String> messages) {
    	if(type.intValue() == QuestionData.YOU_TUBE_VIDEO) {
    		return "YouTube Video";
    	}
    	if(type.intValue() == QuestionData.VIDEO) {
    		return "Video";
    	}
        return messages.get(key);
    }

	public static boolean isFirstNameQuestion(int questionId) {
		return questionId == 25447 || questionId == 26465 || questionId == 26472 || 
				questionId == 26680 || questionId == 49017 || questionId == 41525 || 
				questionId == 41535 || questionId == 43661 || questionId == 45336 || 
				questionId == 46712 || questionId == 46884 || questionId == 46959 || 
				questionId == 48522 || questionId == 48740 || questionId == 48746 ||
				questionId == 50772 || questionId == 50794 || questionId == 50816 ||
				questionId == 50838 || questionId == 51046 || questionId == 51025 || 
				questionId == 50983 || questionId == 50939 || questionId == 50961 || 
				questionId == 52860;
	}

	public static boolean isLastNameQuestion(int questionId) {
		return questionId == 25448 || questionId == 26466 || questionId == 26473 || 
				questionId == 26681 || questionId == 49018 || questionId == 41526 || 
				questionId == 41536 || questionId == 43662 || questionId == 45337 || 
				questionId == 46713 || questionId == 46885 || questionId == 46960 || 
				questionId == 48523 || questionId == 48741 || questionId == 48747 ||
				questionId == 50773 || questionId == 50795 || questionId == 50817 ||
				questionId == 50839 || questionId == 51047 || questionId == 51026 || 
				questionId == 50984 || questionId == 50940 || questionId == 50962 || 
				questionId == 52861;
	}

	public static int getUPLValue(int questionId, int answer) {
    	switch(questionId) {
	        case 48652: 
	        	switch(answer) {		
	        		case 154883: case 154882:
	        			return 2; 
	        		case 154884: 
	        			return 1; 
	        		case 154885: 
	        			return 0; 
	        	}
	        	break;
	        case 48653: 
	        	switch(answer) {
	        		case 154891: case 154890: case 154893: case 154894:
	        			return 2; 
	        		case 154888: case 154889: case 154892: case 154886: case 154887: 
	        			return 1; 
	        	}
	        	break;
	        case 48654: 
	        	switch(answer) {
	        		case 154896: 
	        			return 2; 
	        		case 154897: 
	        			return 1; 
	        		case 154895: 
	        			return 0; 
	        	}
	        	break;	
	        case 48655: 
	        	switch(answer) {
	        		case 154898: 
	        			return 0; 
	        		case 154899: 
	        			return 1; 
	        		case 154900: 
	        			return 2; 
	        	}
	        	break;
	        case 48656: 
	        	switch(answer) {
	        		case 154902: case 154905: case 154906: case 154907: case 154908: case 154904: case 154901: 
	        			return 1; 
	        		case 154903: 
	        			return 2; 
	        	}
	        	break;
	        case 48657: 
	        	switch(answer) {
	        		case 154910: 
	        			return 0; 
	        		case 154909: 
	        			return 1; 
	        	}
	        case 48658: 
	        	switch(answer) {
	        		case 154916: 
	        			return 0; 
	        		case 154914: case 154913:
	        			return 1; 
	        		case 154912: case 154911: case 154917: case 154915: 
	        			return 2; 
	        	}
	        	break;
	        case 48659: 
	        	switch(answer) {
	        		case 154921: 
	        			return 0; 
	        		case 154919: 
	        			return 1; 
	        		case 154920: case 154918:
	        			return 2; 
	        	}
	        	break;
		}    
    	return -1;
	}
}
