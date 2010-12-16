package net.praqma.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
	
	//public void SaveState( String filename )
	public void SaveState( File filename )
	{
		System.out.println( "Saving log to " + filename );
		
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
	
	public String transform( File xml, String xsl, File output )
	{
		//StreamSource xsltSource = new StreamSource( xsl );
		StreamSource xmlSource  = new StreamSource( xml );
		StreamSource xsltSource = new StreamSource( getClass().getResourceAsStream( xsl ) );


        TransformerFactory transFact = TransformerFactory.newInstance();

        try
		{
        	Transformer transformer = transFact.newTransformer( xsltSource );
        	transformer.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );
        	transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
        	transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", "4" );
        	transformer.setOutputProperty( OutputKeys.METHOD, "xml" );
        	
        	/* Correct UTF-8 encoding!? */
        	OutputStream os = new FileOutputStream( output );
        	transformer.transform( xmlSource, new StreamResult( os ) );
			
			return os.toString();
			
			/*
			StringWriter sw = new StringWriter();
        	trans.transform( xmlSource, new StreamResult( sw ) );
			return sw.toString();
			 */
		}
		catch ( TransformerException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( FileNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
}
