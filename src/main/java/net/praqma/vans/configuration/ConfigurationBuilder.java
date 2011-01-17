package net.praqma.vans.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.praqma.util.debug.Logger;
import net.praqma.vans.Tasks;
import net.praqma.vans.configuration.DefaultParser.OptionValue;
import net.praqma.vans.filter.Filter;
import net.praqma.vans.task.Task;
import net.praqma.vans.util.VANSException;

public class ConfigurationBuilder
{
	private Logger logger = Logger.getLogger();
	
	public List<Task> tasks = new ArrayList<Task>();
	
	private static ClassLoader classloader = ConfigurationBuilder.class.getClassLoader();
	
	public ConfigurationBuilder( File conf, File defaultConf ) throws VANSException
	{
		ProjectParser pp = null;
		DefaultParser dp = null;
		
		try
		{
			pp = new ProjectParser( conf );
		}
		catch ( IOException e )
		{
			throw new VANSException( e.getMessage() );
		}
		
		try
		{
			dp = new DefaultParser( defaultConf );
		}
		catch ( IOException e )
		{
			throw new VANSException( e.getMessage() );
		}
		
		/* The meat */
		for( Configuration c : pp.confs )
		{
			/* Get defaults */
			if( dp.defaults.containsKey( c.type ) )
			{
				for( OptionValue ov : dp.defaults.get( c.type ) )
				{
					/* Insert a default value if does not exist */
					if( !c.options.containsKey( ov.name ) )
					{
						c.options.put( ov.name, ov.value );
					}
				}
			}
			else
			{
				logger.debug( "No default information for " + c.type );
			}
			
			c.print();
			
			tasks.add( c.buildTask() );
		}
		
		/**/
		Class<Filter> eclass = null;
		String filterstr = pp.superType + "Filter";
		Filter filter = null;
		
		try
		{	
			eclass = (Class<Filter>) classloader.loadClass( "net.praqma.vans.filter." + filterstr );
		}
		catch ( ClassNotFoundException e )
		{
			logger.error( "The class " + filterstr + " is not available." );
			throw new VANSException( "The class " + filterstr + " is not available." );
		}
		
		try
		{
			filter = (Filter)eclass.newInstance();
		}
		catch ( Exception e )
		{
			logger.error( "Could not instantiate the class " + filterstr );
			throw new VANSException( "Could not instantiate the class " + filterstr );
		}
		
		Tasks task = new Tasks( this.tasks, filter );
		
		task.run();
	}
	
	public void run()
	{
		
	}
}
