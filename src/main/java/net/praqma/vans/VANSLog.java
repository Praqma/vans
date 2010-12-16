package net.praqma.vans;

import java.io.File;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import net.praqma.util.XML;
import net.praqma.vans.command.Command;
import net.praqma.vans.filter.Filter.Findings;
import net.praqma.vans.filter.Finding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VANSLog extends XML
{
	
	public VANSLog( String name )
	{
		super( "cases" );
		
		super.getRoot().setAttribute( "name", name );
		
		System.out.println( "Creating VANS log" );
		
	}
	
	public void addCase( Findings findings, Command cmd )
	{
		Element testcase = addElement( "testcase" );
		//testcase.setAttribute( "name", name );
		
		addElement( testcase, "cwd" ).setTextContent( cmd.getCwd().getAbsolutePath().toString() );
		addElement( testcase, "command" ).setTextContent( cmd.getCmd() );
		
		//info.setAttribute( "cwd", cmd.getCwd().getAbsolutePath().toString() );
		//info.setAttribute( "cmd", cmd.getCmd() );
		
		Element find = addElement( testcase, "findings" );
		find.setAttribute( "number", findings.size() + "" );
		find.setAttribute( "errors", findings.numberOfErrors() + "" );

		
		for( Finding f : findings )
		{
			addFinding( f, find );
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
