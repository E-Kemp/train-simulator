/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.Document;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Extended class to deal with the influx of data
 * @author elliotkemp97
 */
public class XMLReader extends Stack<dataRecord> {
    
    public boolean add(String msg) {
        try {
            
            //insert message segregation code here
            
            return true;
        }
        catch(Exception e) {
            System.out.println("Something went wrong! \n " + e);
            return false;
        }
    }
    
    public static void test(InputStream msg){
        
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(msg);
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/";
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            
            //Do more here
            
        } catch(XPathExpressionException e) {
            //System.out.println("Something went wrong!");
        } catch (SAXException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
