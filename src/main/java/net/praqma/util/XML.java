package net.praqma.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML
{
	private Document doc;
	private Element root;
	
	public XML( String roottag )
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware( true );
		DocumentBuilder builder;
		try
		{
			builder = factory.newDocumentBuilder();
			doc = builder.newDocument( );
		}
		catch ( ParserConfigurationException e )
		{
			e.printStackTrace();
		}
		
		/* Preparing the root note */
		root = (Element)doc.appendChild( doc.createElement( roottag ) );
	}
	
	public Element getRoot()
	{
		return root;
	}
	
	public Element addElement( String tag )
	{
		return addElement( root, tag );
	}
	
	public Element addElement( Element root, String tag )
	{		
		return (Element)root.appendChild( doc.createElement( tag ) );
	}
	
	
	public String GetXML()
	{
		StringWriter out = new StringWriter();
		
		try
		{
		    TransformerFactory factory = TransformerFactory.newInstance();
		    //factory.setAttribute( "indent-number", new Integer( 4 ) );
		    		    
		    Transformer transformer = factory.newTransformer();
		    
		    transformer.setOutputProperty( OutputKeys.METHOD, "xml" );
		    transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
		    		    
		    //aTransformer.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
		    transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4" );
	
		    Source src = new DOMSource( doc );
		    
	    	Result dest = new StreamResult( out );
	    	transformer.transform( src, dest );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		return out.toString();
	}
	
	public void SaveState( String filename )
	{
		String xml = GetXML();
		try
		{
			FileWriter fw = new FileWriter( filename );
			BufferedWriter bw = new BufferedWriter( fw );
			bw.append( xml );
			bw.close();
			fw.close();
		}
		catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
