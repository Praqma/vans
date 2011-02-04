package net.praqma.cli;

import java.io.File;

import net.praqma.util.option.Option;
import net.praqma.util.option.Options;
import net.praqma.vans.configuration.ConfigurationBuilder;
import net.praqma.vans.util.VANSException;

public class Project
{
	public static void main( String[] args ) throws VANSException
	{
		Options o = new Options();
		
		Option oconfig  = new Option( "config", "c", true, 1, "The project configuration file(XML)" );
		Option odefault = new Option( "default", "d", true, 1, "The default site configuration file(XML)" );
		Option ohelp    = new Option( "help", "h", false, 0, "The help" );
		
		o.setOption( oconfig );
		o.setOption( odefault );
		o.setOption( ohelp );
		
		o.parse( args );
		
		if( ohelp.used )
		{
			o.display();
			System.exit( 0 );
		}
		
		try
		{
			o.checkOptions();
		}
		catch ( Exception e )
		{
			System.err.println( "Incorrect option: \n" + e.getMessage() );
			o.display();
			System.exit( 1 );
		}
		
		int errors = 0;
		
		/* Check the files */
		File configfile = new File( oconfig.getString() );
		if( !configfile.exists() )
		{
			System.err.println( "The configuration file " + configfile + " does not exist." );
			errors++;
		}
		
		File defaultfile = new File( odefault.getString() );
		if( !defaultfile.exists() )
		{
			System.err.println( "The configuration file " + defaultfile + " does not exist." );
			errors++;
		}
		
		if( errors > 0 )
		{
			System.exit( 1 );
		}
		
		System.out.println( "Configuration file: " + configfile );
		System.out.println( "Default       file: " + defaultfile );
		
		ConfigurationBuilder cb = new ConfigurationBuilder( configfile, defaultfile );
	}
}
