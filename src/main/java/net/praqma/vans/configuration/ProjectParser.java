package net.praqma.vans.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import net.praqma.util.debug.Logger;
import net.praqma.util.xml.XML;
import net.praqma.vans.configuration.Configuration.Type;
import net.praqma.vans.util.VANSException;

public class ProjectParser extends XML
{
	private static ClassLoader classloader = ProjectParser.class.getClassLoader();
	
	public Map<String, String> reports = new HashMap<String, String>();
	
	public List<Configuration> confs = new ArrayList<Configuration>();
	
	public Type superType = Type.unknown;

	public ProjectParser( File configuration ) throws VANSException, IOException
	{
		super( configuration );
		
		Element taske = this.getFirstElement( "tasks" );
		
		/* Save the reports? */
		String junit = getRoot().getAttribute( "junitreport" );
		String vans  = getRoot().getAttribute( "vansreport" );
		
		if( junit.length() > 0 )
		{
			reports.put( "junit", junit );
		}
		
		if( vans.length() > 0 )
		{
			reports.put( "vans", vans );
		}
		
		List<Element> tasks = this.getElements( taske, "task" );
		
		superType = Type.valueOf( getRoot().getAttribute( "type" ) );
		
		for( Element task : tasks )
		{
			Type mytype = superType;
			
			if( !getRoot().getAttribute( "type" ).equals( "" ) )
			{
				mytype = Type.valueOf( getRoot().getAttribute( "type" ) );
				logger.debug( "Setting type to " + mytype );
			}
			
			
			Class<Configuration> eclass = null;
			Configuration conf = null;
			
			try
			{	
				eclass = (Class<Configuration>) classloader.loadClass( "net.praqma.vans.configuration." + mytype  );
			}
			catch ( ClassNotFoundException e )
			{
				logger.error( "The class " + mytype + " is not available." );
				throw new VANSException( "The class " + mytype + " is not available." );
			}
			
			try
			{
				conf = (Configuration)eclass.newInstance();
			}
			catch ( Exception e )
			{
				logger.error( "Could not instantiate the class " + mytype );
				throw new VANSException( "Could not instantiate the class " + mytype );
			}
			
			List<Element> options = getElements( task, "option" );
			
			for( Element option : options )
			{
				conf.addOption( option.getAttribute( "name" ), option.getTextContent() );
			}
			
			conf.setType( mytype );
			
			conf.workingDir = new File( task.getAttribute( "directory" ) );
			if( !conf.workingDir.exists() )
			{
				throw new VANSException( "The working directory " + conf.workingDir + " does not exist." );
			}
			
			conf.setName( taske.getAttribute( "name" ) );
			confs.add( conf );
		}
	}
	
}
