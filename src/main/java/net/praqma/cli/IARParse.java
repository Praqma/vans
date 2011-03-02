package net.praqma.cli;

import java.io.File;
import java.io.IOException;

import net.praqma.vans.parse.IARParser;

import net.praqma.util.io.BuildNumberStamper;
import net.praqma.util.option.Option;
import net.praqma.util.option.Options;

/*
 * Usage
 * java -classpath COOL-0.1.5.jar net.praqma.cli.BuildNumber -f stamptest.txt -m 12 -p 1234 -s 22221 --minor 22b
 * 
 * 
 */

public class IARParse
{
	
	public static void main( String[] args )
	{
		Options o = new Options( net.praqma.vans.Version.version );
		
		Option ofile = new Option( "file", "f", true, 1, "The file to parse. *.ewp" );
		
		o.setOption( ofile );
		
		o.setDefaultOptions();
		
		o.setSyntax( "IARParse -f file" );
		o.setDescription( "Parse an IAR project file for files." );
		
		o.parse( args );
		
		try
		{
			o.checkOptions();
		}
		catch ( Exception e )
		{
			System.err.println( "Incorrect option: " + e.getMessage() );
			o.display();
			System.exit( 1 );
		}
		
		File file = new File( ofile.getString() );
		
		if( !file.exists() )
		{
			System.err.println( "The file does not exist" );
			System.exit( 1 );
		}
				
		if( o.verbose() )
		{
			o.print();
		}
		
		File dir = new File( System.getProperty( "user.dir" ) );
		
		try
		{
			IARParser p = new IARParser( file );
		    for( File f : p.getFiles() )
		    {
		    	System.out.println( f.getAbsolutePath() );
		    }
		}
		catch ( IOException e )
		{
			System.err.println( "The file was not an acceptable project file: " + e.getMessage() );
			System.exit( 1 );
		}
	}
}
