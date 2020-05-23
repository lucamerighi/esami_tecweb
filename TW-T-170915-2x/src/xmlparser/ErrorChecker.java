package xmlparser;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;


public class ErrorChecker extends DefaultHandler {
	
	public ErrorChecker (){ }
	
	@Override
	public void error (SAXParseException e) {
	System.out.println("Parsing error: "+e.getMessage());
	}
	@Override
	public void warning (SAXParseException e) {
	System.out.println("Parsing problem: "+e.getMessage());
	}
	
	@Override
	public void fatalError (SAXParseException e) {
	System.out.println("Parsing error: "+e.getMessage());
	System.out.println("Cannot continue.");
	System.exit(1);
	}
}
