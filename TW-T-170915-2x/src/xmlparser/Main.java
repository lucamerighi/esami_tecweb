package xmlparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
			
			File out = new File("biblioteca.txt");
			BufferedWriter bw= new BufferedWriter(new PrintWriter(out));
			
			bw.append("MUSICISTI ROCK CON MENO DI 3 ALBUM:\n");
			for(String c: getCognomi(doc, "rock", 3)) {
				bw.append(c + "\n");
			}
			
			bw.append("\n");
			bw.append("\n");
			bw.append("aggiungo dark necessities a  red hot chili peppers\n ");
			boolean res= addAlbum(doc, "red hot","chili peppers", "rock", "dark necessities", 2017);
			bw.append("Esito: " + res+"\n\n");
			
			bw.append("aggiungo californication a  red hot chili peppers \n");
			res= addAlbum(doc, "red hot","chili peppers", "rock", "californication", 2017);
			bw.append("Esito: " + res+"\n\n");
			
			bw.append("aggiungo dark necessities a  black chili peppers\n");
			res= addAlbum(doc, "black","chili peppers", "rock", "dark necessities", 2017);
			bw.append("Esito: " + res+"\n\n");
			
			System.out.println();
			System.out.println();
			
			bw.append("MUSICISTI ROCK CON MENO DI 3 ALBUM:\n");
			for(String c: getCognomi(doc, "rock", 3)) {
				bw.append(c + "\n");
			}
			
			bw.close();
			
			/*
			NodeList nodes = root.getElementsByTagName("informazione");
			
			for (int i = 0; i < nodes.getLength(); i++){
				
				Node el = nodes.item(i);
				  System.out.println("NUOVO CONTATTO:"); 
				  
				  NodeList child = el.getChildNodes();
				  
				  for (int j = 0; j < child.getLength(); j++){ 
					  Node c= child.item(j); 
					  if(c.getNodeName().equals("nome")) {
						  System.out.print("Nome:"); 
						  NodeList namech = c.getChildNodes();
						  String nametoprint="";
						  for (int k = 0; k < namech.getLength(); k++){   //sottotag
							  Node el2= namech.item(k);
							  nametoprint+= " " + el2.getTextContent();
						  }
						  System.out.println(nametoprint);
						  continue;
					  }
					  if(c.getNodeName().equals("indirizzo")) {
						  System.out.print("Indirizzo: "); 
						  NodeList namech = c.getChildNodes();
						  String nametoprint="";
						  for (int k = 0; k < namech.getLength(); k++){ 
							  Node el2= namech.item(k);
							  nametoprint+= el2.getTextContent()+", ";
						  }
						  System.out.println(nametoprint.substring(0, nametoprint.length()-2));
						  continue;
					  }
					  System.out.println(c.getNodeName()+ ": " +c.getTextContent()); /tag
					
				  }
				
				  System.out.println();
			} */
			
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
		

	}
	
  // funzione che trova elementi restituendo solo quelli con un valore preciso in un attibuto
	static Set<String> getCognomi(Document doc, String genere, int totAlbum){
		 Set<String> result = new  HashSet<String>();
		Element root = doc.getDocumentElement();
		root.normalize();
	
		NodeList nodes = root.getElementsByTagName("musicista");
			
		for (int i = 0; i < nodes.getLength(); i++){
			
			Node el = nodes.item(i);
			NodeList child = el.getChildNodes();
			boolean genOk=false;
			int numAlbum=0;
			String cognome="";
			for (int j = 0; j < child.getLength(); j++){
				 Node c= child.item(j); 
				if(c.getNodeName().equals("genere")) {
					if(c.getTextContent().equals(genere)) genOk=true;
				}
				if(c.getNodeName().equals("cognome")) {
					cognome= c.getTextContent();
				}
				if(c.getNodeName().equals("album")) {
					numAlbum++;
				}
			}
			if(genOk && numAlbum<totAlbum) {
				result.add(cognome);
			}
		
		}
		
		return result;
		
		}
	
	static boolean addAlbum(Document doc, String nome, String cognome, String
			genere, String titoloAlbum, int annoPubblicazione) {
		Element root = doc.getDocumentElement();
		root.normalize();
	
		NodeList nodes = root.getElementsByTagName("musicista");
		
		for (int i = 0; i < nodes.getLength(); i++){
			boolean artista=false;
			boolean albumexists=false;		
			Node el = nodes.item(i);
			NodeList child = el.getChildNodes();
		
			for (int j = 0; j < child.getLength(); j++){
				 Node c= child.item(j); 
				 if(c.getNodeName().equals("nome")) {
						if(c.getTextContent().equals(nome)) {
							artista=true;
						}
						else {
							artista=false;
							break;
						}
					}
				if(c.getNodeName().equals("cognome")) {
					if(c.getTextContent().equals(cognome)) {
						artista=true;
					}
					else {
						artista=false;
						break;
					}
				}
				if(c.getNodeName().equals("genere")) {
					if(c.getTextContent().equals(genere)) {
						artista=true;
					}
					else {
						artista=false;
						break;
					}
				}
				if(c.getNodeName().equals("album")) {
					  NodeList albums = c.getChildNodes();
					  for (int k = 0; k < albums.getLength(); k++){ 
						  Node el2= albums.item(k);
						  if(el2.getNodeName().equals("titoloalbum") && el2.getTextContent().equals(titoloAlbum)) {
							  albumexists=true;
						  }
					  }
					
				}
			}
			if(artista && !albumexists) {
				Element newElement = doc.createElement("album");
				Element figlio = doc.createElement("titoloalbum");
				figlio.setTextContent(titoloAlbum);
				newElement.appendChild(figlio);
				figlio = doc.createElement("anno");
				figlio.setTextContent(Integer.toString(annoPubblicazione));
				newElement.appendChild(figlio);
				el.appendChild(newElement);
				return true;
			}
			if(artista && albumexists) {
				System.out.println("Album già esistente");
				return false;
			}
		}
		
		return false;
	}

}
