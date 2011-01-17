package net.praqma.vans.configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import net.praqma.util.debug.Logger;
import net.praqma.util.xml.XML;
import net.praqma.vans.configuration.Configuration.Type;
import net.praqma.vans.util.VANSException;

public class ConfigurationParser extends XML
{
	Logger logger = Logger.getLogger();
	
	private static ClassLoader classloader = ConfigurationParser.class.getClassLoader();
	
	Map<String, Configuration> confs = new HashMap<String, Configuration>();

	public ConfigurationParser( File configuration ) throws VANSException, IOException
	{
		super( configuration );
		
		String type = getRoot().getNodeName();
		
		Element taske = this.getFirstElement( "tasks" );
		
		List<Element> tasks = this.getElements( taske, "task" );
		
		Type superType = Type.valueOf( getRoot().getAttribute( "type" ) );
		
		for( Element task : tasks )
		{
			
			Class<Configuration> eclass = null;
			Configuration conf = null;
			
			try
			{	
				eclass = (Class<Configuration>) classloader.loadClass( "net.praqma.vans.configuration." + type  );
			}
			catch ( ClassNotFoundException e )
			{
				logger.error( "The class " + type + " is not available." );
				throw new VANSException( "The class " + type + " is not available." );
			}
			
			try
			{
				conf = (Configuration)eclass.newInstance();
			}
			catch ( Exception e )
			{
				logger.error( "Could not instantiate the class " + type );
				throw new VANSException( "Could not instantiate the class " + type );
			}
			
			List<Element> options = getElements( task, "option" );
			
			for( Element option : options )
			{
				conf.addOption( option.getAttribute( "name" ), option.getTextContent() );
			}
			
			if( !getRoot().getAttribute( "type" ).equals( "" ) )
			{
				conf.setType( Type.valueOf( getRoot().getAttribute( "type" ) ) );
			}
			else
			{
				conf.setType( superType );
			}
			
			confs.put( taske.getAttribute( "name" ), conf );
		}
	}
	
}
