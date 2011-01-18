package net.praqma.vans.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.praqma.util.xml.XML;

import org.w3c.dom.Element;

public class DefaultParser extends XML
{
	Map<String,List<OptionValue>> defaults = new HashMap<String, List<OptionValue>>();
	
	public class OptionValue
	{
		public String value = "";
		public String name  = "";
		
		public OptionValue()
		{}
		
		public OptionValue( String name, String value )
		{
			this.name  = name;
			this.value = value;
		}
	}
	
	public DefaultParser( File configuration ) throws IOException
	{
		super( configuration );
		
		List<Element> progs = this.getElements( this.getRoot() );
		
		for( Element prog : progs )
		{
			
			List<OptionValue> values = new ArrayList<OptionValue>();
			List<Element> options = this.getElements( prog );
			
			for( Element option : options )
			{
				OptionValue ov = new OptionValue( option.getAttribute( "name" ), option.getTextContent());
				values.add( ov );
			}
			
			defaults.put( prog.getNodeName(), values );
		}
	}
}
