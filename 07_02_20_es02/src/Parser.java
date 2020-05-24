
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.XMLReader;

public class Parser {

	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		spf.setValidating(true);
		try
		{
			SAXParser saxParser = spf.newSAXParser();
			XMLReader xmlReader = saxParser.getXMLReader();
			xmlReader.setFeature("http://apache.org/xml/features/validation/schema",true);
			ContentHandler handler = new SAXDBApp();
			ErrorHandler ehandler = new SAXDBApp();
			DTDHandler dhandler = new SAXDBApp();
			EntityResolver enhandler = new SAXDBApp();
			xmlReader.setEntityResolver(enhandler);
			xmlReader.setDTDHandler(dhandler);
			xmlReader.setErrorHandler(ehandler);
			xmlReader.setContentHandler(handler);
			xmlReader.parse("prodotto.xml");
			
			System.out.println("Finito");
			
		}catch (Exception e){
				System.err.println(e.getMessage());
				System.exit(1);
		};
	}
	
}
