package xmlparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.*;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class Main {

	public static void main(String[] args) {
		
		//DOMParser
		
		System.out.println("\n\nDOM");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		dbf.setNamespaceAware(true);
		try {
			dbf.setFeature("http://apache.org/xml/features/validation/schema",true);
			dbf.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", false);
			DocumentBuilder builder = dbf.newDocumentBuilder();
			ErrorHandler errhandler2 = new ErrorChecker();
			builder.setErrorHandler(errhandler2);
			File file = new File("fileXML.xml");
			Document doc= builder.parse(file); 
			
			System.out.println("AUDIOVISIVI BN:\n");
			ArrayList<Audiovisivo> audiov = getAudiovisiviBN(doc);
			
			for(Audiovisivo a : audiov) {
				a.stampa();
			}
			
			System.out.println("PELLICOLE 16mm:\n");
			ArrayList<Pellicola> pell = getPellicole16(doc);
			
			for(Pellicola p : pell) {
				p.stampaPellicola();
			}
			
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		
	}
		
	
	static ArrayList<Audiovisivo> getAudiovisiviBN(Document doc){
		ArrayList<Audiovisivo> result = new ArrayList<Audiovisivo>();
		Element root = doc.getDocumentElement();
		root.normalize();
	
		NodeList nodes = root.getElementsByTagName("audiovisivo");
			
		for (int i = 0; i < nodes.getLength(); i++){
			
			Node el = nodes.item(i);
			NodeList child = el.getChildNodes();
			Audiovisivo aud = new Audiovisivo();
			boolean toAdd=false;
			for (int j = 0; j < child.getLength(); j++){
				 Node c= child.item(j); 
				if(c.getNodeName().equals("nomeReg")) {
					aud.setNomeReg(c.getTextContent());
				}
				if(c.getNodeName().equals("cognomeReg")) {
					aud.setCognomeReg(c.getTextContent());
				}
				if(c.getNodeName().equals("titolo")) {
					aud.setTitolo(c.getTextContent());
				}
				if(c.getNodeName().equals("colore")) {
					aud.setColore(c.getTextContent());
					if(c.getTextContent().equals("BN")) toAdd=true;
				}
			}
			
			if(toAdd) {
				result.add(aud);
			}
		
		}
		
		return result;
		
		} 
		
		static ArrayList<Pellicola> getPellicole16(Document doc){
			ArrayList<Pellicola> result = new ArrayList<Pellicola>();
			Element root = doc.getDocumentElement();
			root.normalize();
			
		
			NodeList nodes = root.getElementsByTagName("pellicola");
			
			for (int i = 0; i < nodes.getLength(); i++){
				
				Node el = nodes.item(i);
				NodeList child = el.getChildNodes();
				Pellicola p = new Pellicola();
				boolean toAdd=false;
				for (int j = 0; j < child.getLength(); j++){
					 Node c= child.item(j); 
					if(c.getNodeName().equals("regia")) {
						p.setRegia(c.getTextContent());
					}
					if(c.getNodeName().equals("titolo")) {
						p.setTitolo(c.getTextContent());
					}
					if(c.getNodeName().equals("tipoPell")) {
						int t= Integer.parseInt(c.getTextContent());
						p.setTipo(t);
						if(t==16) toAdd=true;
					}
					if(c.getNodeName().equals("statoConserv")) {
						p.setStato(c.getTextContent());
					}
				}
				
				if(toAdd) {
					result.add(p);
				}
			
			}
			
			
			return result;
			
		}
		

	

}
