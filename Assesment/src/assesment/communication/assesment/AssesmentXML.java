/**
 * Created on 17-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.assesment;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;
import assesment.communication.module.ModuleData;
import assesment.communication.user.UserData;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class AssesmentXML {

    private Text messages;
    private AssesmentAccess sys;
    
    private static final String TAG_ASSESMENT = "assesment";
    private static final String TAG_MODULES = "modules";
    private static final String TAG_MODULE = "module";
    private static final String TAG_ID = "id";
    private static final String TAG_ORDER = "order";
    private static final String TAG_PENDING = "status";
    private static final String TAG_TEXT = "text";
    private static final String TAG_RIGTH = "correct_answers";
    private static final String TAG_TOTAL = "total_answers";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_CORPORATION = "company";
    private static final String TAG_INSTRUCTIONS = "instructions";
    private static final String TAG_PRIVACY = "privacy";


    public AssesmentXML(AssesmentAccess sys) {
        this.sys = sys;
        messages = sys.getText();
    }

    public String generateXML(AssesmentData assesment,Collection userModules,boolean feedback) {
        String text = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.newDocument();
            generateAssesmentXML(xmlDoc,assesment,userModules,feedback);
            text = generateXMLText(xmlDoc);
            //saveXMLDocument("/home/jrodriguez/Desktop/assesment.xml",text);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return text;
    }

    public void generateAssesmentXML(Document xmlDoc, AssesmentData assesment, Collection userModules, boolean feedback) {
        
        try {
            Element assesmentElement = xmlDoc.createElement(TAG_ASSESMENT);
            assesmentElement.setAttribute(TAG_ID,String.valueOf(assesment.getId()));
            assesmentElement.setAttribute(TAG_USER_ID,sys.getUserSessionData().getFilter().getLoginName());
            UserData userData = sys.getUserReportFacade().findUserByPrimaryKey(sys.getUserSessionData().getFilter().getLoginName(),sys.getUserSessionData());
            assesmentElement.setAttribute(TAG_USER_NAME,userData.getFirstName()+" "+userData.getLastName());
            assesmentElement.setAttribute(TAG_CORPORATION,String.valueOf(assesment.getCorporationId()));
            if(assesment.getId().intValue() == AssesmentData.MONSANTO_NEW_HIRE) {
                assesmentElement.setAttribute(TAG_INSTRUCTIONS,"./data/nhInstructions");
            }else {
                assesmentElement.setAttribute(TAG_INSTRUCTIONS,"./data/instructions");
            }
            assesmentElement.setAttribute(TAG_PRIVACY,"./data/privacy");
                
            Iterator it = assesment.getModuleIterator();
            Element modulesElement = xmlDoc.createElement(TAG_MODULES);
    
            int order = 0;
            boolean psi = false;
            boolean psiDone = sys.getUserReportFacade().isPsicologicDone(sys.getUserSessionData().getFilter().getLoginName(),sys.getUserSessionData().getFilter().getAssesment(),sys.getUserSessionData());
            while(it.hasNext()) {
                int rigth = 0;
                int total = 0;
                ModuleData module = (ModuleData)it.next();
                
                if(module.getType().intValue() == ModuleData.FINAL_MODULE) {
                    if(assesment.isPsitest()/* && !psiDone*/) {
                        
                        Element moduleElement = xmlDoc.createElement(TAG_MODULE);
                        moduleElement.setAttribute(TAG_ID,String.valueOf(0));
                        moduleElement.setAttribute(TAG_ORDER,String.valueOf(order+1));

                        String pending = (psiDone) ? "1" : "0";
                        moduleElement.setAttribute(TAG_PENDING,pending);
                        
                        Element textNode = xmlDoc.createElement(TAG_TEXT);
                        moduleElement.appendChild(textNode);
                        
                        org.w3c.dom.Text textValue = xmlDoc.createTextNode(TAG_TEXT);
                        textValue.setNodeValue(messages.getText("assesment.module.psicologic"));
                        textNode.appendChild(textValue);
                            
                        modulesElement.appendChild(moduleElement);
                        psi = true;
                    }
                }
                
                Element moduleElement = xmlDoc.createElement(TAG_MODULE);
                moduleElement.setAttribute(TAG_ID,String.valueOf(module.getId()));
                moduleElement.setAttribute(TAG_ORDER,String.valueOf(module.getOrder()));

                Iterator it1 = userModules.iterator();
                boolean find = false;
                while(it1.hasNext()) {
                    int[] values = (int[])it1.next();
                    if(values[0] == module.getId().intValue()) {
                        find = true;
                        rigth = values[1];
                        total = values[2];
                    }
                }
                String pending = (find) ? "1" : "0";
                moduleElement.setAttribute(TAG_PENDING,pending);
                if(total > 0 && feedback) {
                    moduleElement.setAttribute(TAG_RIGTH,String.valueOf(rigth));
                    moduleElement.setAttribute(TAG_TOTAL,String.valueOf(total));
                }
                
                Element textNode = xmlDoc.createElement(TAG_TEXT);
                moduleElement.appendChild(textNode);
                
                org.w3c.dom.Text textValue = xmlDoc.createTextNode(TAG_TEXT);
                textValue.setNodeValue(messages.getText(module.getKey()));
                textNode.appendChild(textValue);

                modulesElement.appendChild(moduleElement);
                
                order = Math.max(order,module.getOrder().intValue());
            }
            
            if(!psi) {
	            if(assesment.isPsitest()/* && !psiDone*/) {
	                
	                Element moduleElement = xmlDoc.createElement(TAG_MODULE);
	                moduleElement.setAttribute(TAG_ID,String.valueOf(0));
	                moduleElement.setAttribute(TAG_ORDER,String.valueOf(order+1));
	
	                String pending = (psiDone) ? "1" : "0";
	                moduleElement.setAttribute(TAG_PENDING,pending);
	                
	                Element textNode = xmlDoc.createElement(TAG_TEXT);
	                moduleElement.appendChild(textNode);
	                
	                org.w3c.dom.Text textValue = xmlDoc.createTextNode(TAG_TEXT);
	                textValue.setNodeValue(messages.getText("assesment.module.psicologic"));
	                textNode.appendChild(textValue);
	                    
	                modulesElement.appendChild(moduleElement);
	            }
            }
    
            assesmentElement.appendChild(modulesElement);
            xmlDoc.appendChild(assesmentElement);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String generateXMLText(Document xmlDoc) {
        StringWriter strWriter = null;
        XMLSerializer xmlSerializer = null;
        OutputFormat outFormat = null;
        try {
            xmlSerializer = new XMLSerializer();
            strWriter = new StringWriter();
            outFormat = new OutputFormat();

            outFormat.setEncoding("UTF-8");
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
