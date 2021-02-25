package assesment.presentation.actions.administration;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created on 18-dic-2006
 * CEPA
 * DataCenter 5
 */

public class ReadTest {

    public static void main(String[] args) {
        
        try {
            File f = new File("test");
            FileOutputStream fos = new FileOutputStream(f);

            for(int i = 1; i <= 48; i++) {
                URL yahoo = new URL("http://www.psicologiatotal.com/wspsi2.php?xf=GetTextosPregunta&nid="+i);
                BufferedReader in = new BufferedReader(
                new InputStreamReader(yahoo.openStream()));
                StringBuffer all = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    all.append(inputLine);
                }
                in.close();

                String content = all.toString();
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                docBuilderFactory.setNamespaceAware(true);
                DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
                Document doc = docBuilder.parse(is);
                NodeList list = doc.getFirstChild().getChildNodes();
                
                for(int j = 0; j < list.getLength(); j++) {
                    Node node = list.item(j);
                    if(node.getNodeName().equals("texto")) {
                        fos.write(new String("psicotest.question"+i+".text\t"+node.getFirstChild().getNodeValue().trim()+"\t\t\n").getBytes());
                    }
                    if(node.getNodeName().equals("respuesta_A")) {
                        fos.write(new String("psiquestion"+i+".answer1.text\t"+node.getFirstChild().getNodeValue().trim()+"\t\t\n").getBytes());
                    }
                    if(node.getNodeName().equals("respuesta_B")) {
                        fos.write(new String("psiquestion"+i+".answer2.text\t"+node.getFirstChild().getNodeValue().trim()+"\t\t\n").getBytes());
                    }
                    if(node.getNodeName().equals("respuesta_C")) {
                        fos.write(new String("psiquestion"+i+".answer3.text\t"+node.getFirstChild().getNodeValue().trim()+"\t\t\n").getBytes());
                    }
                }
                
            }
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
