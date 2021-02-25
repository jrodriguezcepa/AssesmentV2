/**
 * Created on 17-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.module;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import assesment.business.AssesmentAccess;
import assesment.communication.assesment.AssesmentData;
import assesment.communication.language.Text;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ModuleXML {

    private static final String TAG_MODULE = "module";
    private static final String TAG_ID = "id";
    private static final String TAG_ADULT = "adult_at";
    private static final String TAG_TYPE = "type";
    private static final String TAG_TEXT = "text";
    private static final String TAG_ANSWER = "answer";
    private static final String TAG_ANSWERS = "answers";
    //private static final String TAG_ORDER = "order";
    private static final String TAG_QUESTION = "question";
    private static final String TAG_QUESTIONS = "questions";
    private static final String TAG_GROUP = "group";
    private static final String TAG_IMAGE = "image";
    
    private static final int[][] answerIndex = {{0,1,2},{0,2,1},{1,0,2},{1,2,0},{2,0,1},{2,1,0}};
    private Text messages;
    
    public ModuleXML(AssesmentAccess sys) {
        messages = sys.getText();
    }

    public String generateXML(ModuleData module) {
        String text = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.newDocument();
            generateModuleXML(xmlDoc,module);
            text = generateXMLText(xmlDoc);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return text;
    }

    public void generateModuleXML(Document xmlDoc, ModuleData module) {
        
        Element moduleElement = xmlDoc.createElement(TAG_MODULE);
        moduleElement.setAttribute(TAG_ID,String.valueOf(module.getId()));
        
        Iterator it = module.getQuestionIterator();
        Element questionsElement = xmlDoc.createElement(TAG_QUESTIONS);

        Element[] questions = new Element[module.getQuestionSize()];
        Random random = new Random();
        int moduleIndex = 0;
        while(it.hasNext()) {
            QuestionData question = (QuestionData)it.next();
            
            Element questionElement = xmlDoc.createElement(TAG_QUESTION);

            int questionType = question.getType().intValue();
            if(questionType == QuestionData.BIRTHDATE) {
                Calendar c = Calendar.getInstance();
                questionElement.setAttribute(TAG_ADULT,c.get(Calendar.YEAR)+"-"+String.valueOf(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE));
                questionType = QuestionData.DATE;
            }

            questionElement.setAttribute(TAG_ID,String.valueOf(question.getId()));
            
            if(question.getGroup() != null && question.getGroup().intValue() != 0) { 
                questionElement.setAttribute(TAG_GROUP,String.valueOf(question.getGroup()));
            }
            
            if(question.getImage() != null && question.getImage().trim().length() > 0) {
                questionElement.setAttribute(TAG_IMAGE,question.getImage());
            }
            
            String text = messages.getText(question.getKey()).trim();
            
            Element typeNode = xmlDoc.createElement(TAG_TYPE);
            questionElement.appendChild(typeNode);

            org.w3c.dom.Text typeValue = xmlDoc.createTextNode(TAG_TYPE);
            typeValue.setNodeValue(String.valueOf(questionType));
            typeNode.appendChild(typeValue);

            Element textNode = xmlDoc.createElement(TAG_TEXT);
            questionElement.appendChild(textNode);
            
            org.w3c.dom.Text textValue = xmlDoc.createTextNode(TAG_TEXT);
            textValue.setNodeValue(text);
            textNode.appendChild(textValue);
            
            
            
            if(question.getAnswerSize() > 0) {
                Element answers = xmlDoc.createElement(TAG_ANSWERS);
                
                if(module.getId().intValue() != ModuleData.PSICO 
                		&& question.getAnswerSize() == 3 
                        && question.getTestType().intValue() == QuestionData.TEST_QUESTION
                        && module.getAssesment().intValue() != AssesmentData.MONSANTO_NEW_HIRE
                        && module.getAssesment().intValue() != AssesmentData.ASTRAZENECA_2
                        && module.getAssesment().intValue() != AssesmentData.ASTRAZENECA_2013
                       // && module.getAssesment().intValue() != AssesmentData.ANTP_MEXICO
                        && module.getAssesment().intValue() != AssesmentData.ABITAB) {
                    Iterator<AnswerData> answerIterator = question.getAnswerIterator();
                    int index = 0;
                    Element[] answerNodes = new Element[3];
                    int answerIndexValue = random.nextInt(6);
                    while(answerIterator.hasNext()) {
                        AnswerData answer = answerIterator.next();
                        
                        Element answerNode = xmlDoc.createElement(TAG_ANSWER);
                        answerNode.setAttribute(TAG_ID,String.valueOf(answer.getId()));
                        answerNode.setAttribute(TAG_TEXT,messages.getText(answer.getKey()).trim());
    
                        answerNodes[answerIndex[answerIndexValue][index]] = answerNode;
                        index++;
                    }
                    for(int i = 0; i < answerNodes.length; i++) {
                        answers.appendChild(answerNodes[i]);
                    }
                }else {
                    Iterator<AnswerData> answerIterator = question.getAnswerIterator();
                    while(answerIterator.hasNext()) {
                        AnswerData answer = answerIterator.next();
                        
                        Element answerNode = xmlDoc.createElement(TAG_ANSWER);
                        answerNode.setAttribute(TAG_ID,String.valueOf(answer.getId()));
                        answerNode.setAttribute(TAG_TEXT,messages.getText(answer.getKey()).trim());
    
                        answers.appendChild(answerNode);
                    }
                }
                questionElement.appendChild(answers);
            }

            if(module.getType() != null && module.getType().intValue() == ModuleData.PERSONAL_DATA) {
                questions[moduleIndex] = questionElement;
                moduleIndex++;
            }else {
                int value = random.nextInt(questions.length);
                while(questions[value] != null) {
                    value = random.nextInt(questions.length);
                }
                questions[value] = questionElement;
            }
        }

        for(int i = 0; i < questions.length; i++) {
            questionsElement.appendChild(questions[i]);
        }

        moduleElement.appendChild(questionsElement);
        xmlDoc.appendChild(moduleElement);
    }

    private String generateXMLText(Document xmlDoc) {
        StringWriter strWriter = null;
        XMLSerializer xmlSerializer = null;
        OutputFormat outFormat = null;
        try {
            xmlSerializer = new XMLSerializer();
            strWriter = new StringWriter();
            outFormat = new OutputFormat();

            outFormat.setEncoding("ISO-8859-1");
            outFormat.setVersion("1.0");
            outFormat.setIndenting(true);
            outFormat.setIndent(4);

            xmlSerializer.setOutputCharStream(strWriter);
            xmlSerializer.setOutputFormat(outFormat);
            xmlSerializer.serialize(xmlDoc);
            strWriter.close();
        } catch (IOException ioEx) {
            System.out.println("Error : " + ioEx);
        }
        return strWriter.toString();
    }

    public void saveXMLDocument(String fileName,String textoXML) {
        try {
            OutputStream fout = new FileOutputStream(fileName);
            OutputStream bout = new BufferedOutputStream(fout);
            OutputStreamWriter out = new OutputStreamWriter(bout);
            out.write(textoXML);
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("La Máquina Virtual no soporta la codificación Latin-1.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }
}
