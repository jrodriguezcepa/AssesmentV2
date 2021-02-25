/**
 * Created on 17-sep-2007
 * CEPA
 * DataCenter 5
 */
package assesment.communication.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import assesment.business.AssesmentAccess;
import assesment.communication.language.Text;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class CountryXML {

    private static final String TAG_COUNTRIES = "countries";
    private static final String TAG_COUNTRY = "country";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    
    private Text messages;
    
    public CountryXML(AssesmentAccess sys) {
        messages = sys.getText();
    }

    public String generateXML() {
        String text = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.newDocument();
            generateModuleXML(xmlDoc);
            text = generateXMLText(xmlDoc);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return text;
    }

    public void generateModuleXML(Document xmlDoc) {
        
        Element countriesElement = xmlDoc.createElement(TAG_COUNTRIES);

        CountryConstants cc = new CountryConstants(messages);
        Iterator countries = cc.getCountryIterator();
        while(countries.hasNext()) {
            
            CountryData data = (CountryData)countries.next(); 
            
            Element countryElement = xmlDoc.createElement(TAG_COUNTRY);
            countryElement.setAttribute(TAG_ID,data.getId());
            
            Element nameNode = xmlDoc.createElement(TAG_NAME);
            org.w3c.dom.Text nameValue = xmlDoc.createTextNode(TAG_NAME);
            nameValue.setNodeValue(data.getName());
            nameNode.appendChild(nameValue);
            countryElement.appendChild(nameNode);

            countriesElement.appendChild(countryElement);
        }
        xmlDoc.appendChild(countriesElement);
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

}
