package xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;




public class SaxParserXml extends DefaultHandler {
	private boolean nomeProprio=false, cognome=false, indPost=false, prov=false, stato=false, numtel=false;
	private boolean nazion=false, secNome=false, via=false, email=false,note=false;
	
	private Element1 elem1; 
	private Element2 elem2; 
	
	@Override
	public void startElement(String namespaceURI, String localName, String rawname, Attributes ats) {
		if(localName.equals("informazione")) elem1 = new Element1();
		if(localName.equals("nomeProprio")) nomeProprio=true;
		if(localName.equals("cognome")) cognome=true;
		if(localName.equals("indirizzoPostale")) indPost=true;
		if(localName.equals("provincia")) prov=true;
		if(localName.equals("stato")) stato=true;
		if(localName.equals("nazionalità")) nazion=true;
		if(localName.equals("secondoNome")) secNome=true;
		if(localName.equals("via")) via=true;
		if(localName.equals("email")) email=true;
		if(localName.equals("numTel")) numtel=true;
		if(localName.equals("note")) note=true;
		if(localName.equals("indirizzo")) elem2 = new Element2();

	}
	
	@Override
	public void characters (char ch[], int start, int length) {
		/*if(nomeProprio) elem.setNomeProprio(new String(ch, start, length));
		if(cognome) elem.setCognome(new String(ch, start, length));
		if(indPost) indirizzo.setIndPost(new String(ch, start, length));
		if(prov) indirizzo.setProv(new String(ch, start, length));
		if(stato) indirizzo.setStato(new String(ch, start, length));
		if(nazion) elem.setNazion(new String(ch, start, length));
		if(secNome) elem.addSecNome(new String(ch, start, length));
		if(via) indirizzo.setVia(new String(ch, start, length));
		if(email) elem.addEmail(new String(ch, start, length));
		if(note) elem.addNote(new String(ch, start, length));
		if(numtel) elem.addNumTel(new String(ch, start, length));*/
	
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName) {
		if(localName.equals("nomeProprio")) nomeProprio=false;
		if(localName.equals("cognome")) cognome=false;
		if(localName.equals("indirizzoPostale")) indPost=false;
		if(localName.equals("provincia")) prov=false;
		if(localName.equals("stato")) stato=false;
		if(localName.equals("nazionalità")) nazion=false;
		if(localName.equals("secondoNome")) secNome=false;
		if(localName.equals("via")) via=false;
		if(localName.equals("email")) email=false;
		if(localName.equals("note")) note=false;
		if(localName.equals("numTel")) numtel=false;
		if(localName.equals("indirizzo")) elem1.addInd(elem2);
		if(localName.equals("informazione")) elem1.stampa();
	}

	
	
	
	
}
