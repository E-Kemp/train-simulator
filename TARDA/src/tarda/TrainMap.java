/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarda;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
public class TrainMap extends LinkedHashMap<String, Train> {
    
    DocumentBuilderFactory builderFactory;
    DocumentBuilder builder;
    Document xmlDocument;
    
    public void add(InputStream msg){
        
        try {
            builderFactory = DocumentBuilderFactory.newInstance();
            builder = builderFactory.newDocumentBuilder();
            xmlDocument = builder.parse(msg);
            
            Node root = (Node) xmlDocument.getDocumentElement().getFirstChild();
            
            NamedNodeMap rootAtt = root.getAttributes();
//            if(rootAtt != null)
//            for(int i = 0; i < root.getAttributes().getLength(); i++)
//                System.out.printf("%s\n", root.getAttributes().item(i));
            
            if(rootAtt.item(0) != null)
//                if(rootAtt.item(0).getNodeValue().contains("Trust"))
                    if(root.getFirstChild().getNodeName().equals("schedule")) {
                        NamedNodeMap scheduleAtt = root.getFirstChild().getAttributes();
                        System.out.println(scheduleAtt.getNamedItem("toc").getNodeValue());
                        if(scheduleAtt.getNamedItem("toc").getNodeValue().equals("GW")) {
                            System.out.println("Greater Anglia Schedule:");
                            Train t = new Train(
                                scheduleAtt.getNamedItem("rid").getNodeValue(),
                                scheduleAtt.getNamedItem("trainId").getNodeValue(),
                                scheduleAtt.getNamedItem("uid").getNodeValue()
                            );
                            this.put(t.uID, t);
                            System.out.println(t);
                        }
                    }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(TrainMap.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Train List:\n");
        this.values().iterator().forEachRemaining(t -> {
            str.append(t.toString());
        });
        return str.toString();
    }
    
}
