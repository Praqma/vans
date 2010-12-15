package net.praqma.vans;

import java.util.List;

import net.praqma.util.XML;
import net.praqma.vans.filter.Filter.Findings;
import net.praqma.vans.filter.Finding;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VANSLog extends XML
{
	
	public VANSLog( List<Findings> findingsList )
	{
		for( Findings findings : findingsList )
		{
			Element testcase = addElement( "case" );
			
			for( Finding f : findings )
			{
				addFinding( f, testcase );
			}
		}
	}
	
	public void addFinding( Finding f, Element testcase )
	{
		System.out.println( "adding " + f.finding );
		Element e = addElement( testcase, "finding" );
		e.setAttribute( "level", f.level.toString() );
		Element t = addElement( e, "report" );
		t.setTextContent( f.finding );		
	}
}
