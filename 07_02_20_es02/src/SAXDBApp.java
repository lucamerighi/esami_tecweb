import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import beans.Prodotto;

public class SAXDBApp extends DefaultHandler {

	

	private String id;
	private boolean isId;
	private String nome;
	private boolean isNome;
	private String codice;
	private boolean isCodice;
	private double prezzo;
	private boolean isPrezzo;
	private boolean isFrutta;
	private boolean isVerdura;
	private double minV= 0;
	private double maxF=0;
	
	
	public void startElement(String namespaceURI,String localName, String rawName, Attributes atts) {
		if (localName.equals("prodotto")) {
		}
		if(localName.equals("id")) {
			isId=true;
		}
		if(localName.equals("name")) {
			isNome=true;
		}
		if(localName.equals("codice")) {
			isCodice=true;
		}
		if(localName.equals("prezzo")) {
			isPrezzo=true;
		}
	}
	
	public void characters (char ch [], int start,int length) {
		try {
			PrintWriter pw = new  PrintWriter(new FileWriter("MINIVeg.txt"));
		
		if (isId) {
			id = new String(ch, start, length);
			System.out.println("Id: "+id+"\n");
			
		}
		if (isNome) {
			nome= new String(ch, start, length);
			
			System.out.println("Nome: "+nome+"\n");
			
		}
		if (isCodice) {
			codice = new String(ch,start,length);
			if (codice.equals("V")) {
				isFrutta=false;
				isVerdura=true;
			}else {
				isFrutta=true;
				isVerdura=false;
			}
			System.out.println("Codice: "+codice+"\n");
		}
		if (isPrezzo) {
			prezzo = Double.parseDouble(new String(ch,start,length));
				if (isFrutta) {
					if (maxF<prezzo) {
						maxF=prezzo;
					}
				}else {
					if (minV==0 || minV>prezzo) {
						minV=prezzo;
					}
				}
			System.out.println("Prezzo: "+prezzo+"\n");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	
	public void endElement(String namespaceURI,String localName,String qName) {
		
		if(localName.equals("prodotto")) {
			
		}
		if(localName.equals("id")) {
			isId=false;
		}
		if(localName.equals("name")) {
			isNome=false;
		}
		if(localName.equals("codice")) {
			isCodice=false;
		}
		if(localName.equals("prezzo")) {
			isPrezzo=false;
		}
		if(localName.equals("MINIVeg")) {
			System.out.println("MaxF="+maxF+" MinV="+minV);
		}
	}
	
	
}