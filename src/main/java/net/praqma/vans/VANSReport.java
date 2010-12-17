package net.praqma.vans;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.praqma.util.XML;
import net.praqma.vans.command.Command;
import net.praqma.vans.filter.Filter.Findings;
import net.praqma.vans.filter.Finding;
import net.praqma.vans.util.Status;

import org.w3c.dom.Element;

public class VANSReport extends XML
{
	
	public VANSReport( String name )
	{
		super( "cases" );
		
		Calendar date        = Calendar.getInstance();
		SimpleDateFormat timeformat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss" );
		String timestamp = timeformat.format( date.getTime() );
		
		super.getRoot().setAttribute( "name", name );
		getRoot().setAttribute( "timestamp", timestamp );
		
	}
	
	public void addCase( Findings findings, Command cmd, Status status )
	{
		Element testcase = addElement( "testcase" );
		
		testcase.setAttribute( "number", findings.size() + "" );
		testcase.setAttribute( "errors", findings.numberOfErrors() + "" );
		
		addElement( testcase, "cwd" ).setTextContent( cmd.getCwd().getAbsolutePath().toString() );
		addElement( testcase, "command" ).setTextContent( cmd.getCmd() );
		
		Element find = addElement( testcase, "findings" );
	
		for( Finding f : findings )
		{
			addFinding( f, find );
		}
		
		if( status.failed )
		{
			Element failure = addElement( testcase, "failure" );
			failure.setTextContent( status.text );
			failure.setAttribute( "message", status.message );
			failure.setAttribute( "type", status.type );
		}
	}
	
	public void addFinding( Finding f, Element testcase )
	{
		Element e = addElement( testcase, "finding" );
		e.setAttribute( "level", f.level.toString() );
		Element t = addElement( e, "report" );
		t.setTextContent( f.finding );		
	}
	
	public void setErrors( int errors )
	{
		getRoot().setAttribute( "errors", errors + "" );
	}
	

}
