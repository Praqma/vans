package test;

import java.io.File;

import net.praqma.vans.configuration.ConfigurationBuilder;
import net.praqma.vans.util.VANSException;

public class ConfTest
{
	public static void main( String[] args ) throws VANSException
	{
		File c1 = new File( "docs/maven.xml" );
		File c2 = new File( "docs/default.xml" );
		
		ConfigurationBuilder cb = new ConfigurationBuilder( c1, c2 );
	}
}
