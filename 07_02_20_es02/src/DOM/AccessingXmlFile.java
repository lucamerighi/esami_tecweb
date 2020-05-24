package DOM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*; 
import org.w3c.dom.*;

import beans.Prodotto;

public class AccessingXmlFile {
	
	public static Document loadDocument(String fileName) {
		Document doc=null;
		try {
			File file = new File(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setAttribute("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "prodotto.xsd");
			dbf.setValidating(true);
			dbf.setNamespaceAware(true);
			dbf.setFeature("http://apache.org/xml/features/validation/schema",true);
			dbf.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace",false);
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			ErrorChecker errors = new ErrorChecker();
			db.setErrorHandler(errors);
			doc = db.parse(file);
			return doc;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static void main(String argv[]) throws IOException {
		double maxF=0;
		double minV=0;
		PrintWriter pw = new  PrintWriter(new FileWriter("MINIVeg.txt"));
		List<Prodotto> prodotti = new ArrayList<Prodotto>();
		Document document = loadDocument("prodotto.xml");
		Element root = document.getDocumentElement(); 
		root.normalize(); 
		NodeList nodes = root.getElementsByTagName("prodotto");
		for (int i=0;i<nodes.getLength();i++) {
			Node node = nodes.item(i);
			NodeList child =  node.getChildNodes();
			Prodotto x = new Prodotto();
			boolean toAdd = false;
				for (int j=0;j<child.getLength();j++) {
					Node n = child.item(j);
					if (n.getNodeName().equals("id")) {
						x.setId(n.getTextContent());
					}
					if (n.getNodeName().equals("verdura") || n.getNodeName().equals("frutta")) {
						Node name = n.getFirstChild();
						Node codice = n.getLastChild();
								x.setNome(name.getTextContent());
								x.setCodice(codice.getTextContent());
							
					}
					if (n.getNodeName().equals("prezzo")) {
						x.setPrezzo(Double.parseDouble(n.getTextContent()));
						toAdd=true;
					}
				}
			
			if (toAdd) {
				prodotti.add(x);
			}
		}
		/*
		 * Erano da fare come metodi a parte!!!
		 */
		for (Prodotto x: prodotti) {
			pw.println("Prodotto: "+x.getCodice()+" Nome: "+x.getNome()+" Prezzo: "+x.getPrezzo());
		}
		
		for (Prodotto pr: prodotti) {
			if (pr.getCodice().equals("F") && maxF<pr.getPrezzo()) {
				maxF=pr.getPrezzo();
			}
			if (pr.getCodice().equals("V") && (minV==0 || minV>pr.getPrezzo())) {
				minV=pr.getPrezzo();
			}
		}
		
		System.out.println("MaxF: "+maxF);
		System.out.println("MinV: "+minV);
		pw.close();
		
	}
	
}
