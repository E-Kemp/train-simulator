/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarda;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import tarda.recordTypes.Train;

/**
 * Extended class to deal with the influx of data
 * @author elliotkemp97
 */
public class XMLReader {
    
    private static final List<Train> TRAIN_LIST = new ArrayList<>();
    
    public void test(InputStream msg){
        
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(msg);
            
            Node root = (Node) xmlDocument.getDocumentElement().getFirstChild();
            
            NamedNodeMap rootAtt = root.getAttributes();
//            if(rootAtt != null)
//            for(int i = 0; i < root.getAttributes().getLength(); i++)
//                System.out.printf("%s\n", root.getAttributes().item(i));
            
            if(rootAtt.item(0) != null)
                if(rootAtt.item(0).toString().contains("Trust")) {
                    if(root.getFirstChild().getNodeName().equals("schedule")) {
                        NamedNodeMap scheduleAtt = root.getFirstChild().getAttributes();
                        System.out.println(scheduleAtt.getLength()); //connection stopped, debug later
                        System.out.println(scheduleAtt.item(0).getNodeValue());
//                        if(scheduleAtt.getNamedItem("toc").getNodeValue().equals("LE"))
//                        TRAIN_LIST.add(new Train(
//                                
//                        ));
                                
                    }
                }
            
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //LET THE RECURSION BEGIN
    private static Node getNode(Node node, int spacing) {
        if(!node.getNodeName().equalsIgnoreCase("#text"))
            System.out.printf("%20s\n", node.getNodeName());
        
        if(node.getAttributes() != null)
            for(int i = 0; i < node.getAttributes().getLength(); i++)
                System.out.printf("%s\n", node.getAttributes().item(i));
        //11644f37-78d4-475b-9aa6-ee01aee1b31d
        if(node.getNodeValue() != null)
                System.out.println("Platform: " + node.getNodeValue());
        
        NodeList children = node.getChildNodes();
        for(int j = 0; j < children.getLength(); j++)
            getNode(children.item(j), spacing + 2);
        
        return null; //shut up netbeans
    }
    
    
}
