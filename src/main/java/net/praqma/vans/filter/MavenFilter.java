package net.praqma.vans.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.praqma.vans.filter.Finding.Level;
import net.praqma.vans.util.Status;

public class MavenFilter extends Filter
{
	//private static final String rx_java_file = Pattern.quote( "((?\\\\[\\w]+)+\\\\[\\w]+\\.\\w+):\\[(\\d+),(\\d+)\\]" );
	private static final String rx_java_file = "((\\\\[\\w]+)+\\\\([\\w]+\\.\\w+)):\\[(\\d+),(\\d+)\\]";
	private static final Pattern pattern_warning = Pattern.compile( "^\\[WARNING\\]\\s*(.*)$", Pattern.MULTILINE );
	private static final Pattern pattern_error   = Pattern.compile( "^\\[ERROR\\]\\s*"+rx_java_file+"|BUILD ERROR(.*)$", Pattern.MULTILINE );
	
	//private static final Pattern pattern_build_error = Pattern.compile( "^\\[ERROR\\]\\s*BUILD ERROR\\s*\\[INFO\\].*?\\[INFO\\](.*?)\\[INFO\\]", Pattern.MULTILINE );
	private static final Pattern pattern_build_error = Pattern.compile( "^\\[ERROR\\]\\s*BUILD ERROR\\s*\\[INFO\\].*?$\\s*\\[INFO\\](.*?)$\\s*\\[INFO\\]", Pattern.MULTILINE );
	
	//private static final Pattern pattern_compilation_failure = Pattern.compile( "^\\[ERROR\\]\\s*Compilation failure\\s*\\n(.*?)\\[INFO\\]", Pattern.MULTILINE | Pattern.DOTALL );
	private static final Pattern pattern_compilation_failure = Pattern.compile( "^\\[INFO\\]\\s*Compilation failure(.*?)\\[INFO\\]", Pattern.MULTILINE | Pattern.DOTALL );
	
	public MavenFilter()
	{

	}
	
	public Status filter( String input )
	{
		System.out.println( "Running Maven filter..." );
		
		Matcher matches = null;
		
		boolean failed = false;
		
		/* Find warnings */
		matches = pattern_warning.matcher( input );
		while( matches.find() )
		{
			findings.add( new Finding( matches.group( 1 ), Level.WARNING ) );
			if( failOnWarnings )
			{
				failed = true;
			}
		}
		
		/* Find errors */
		matches = pattern_error.matcher( input );
		while( matches.find() )
		{
			findings.add( new Finding( matches.group( 1 ), Level.ERROR ) );
			
			failed = true;
		}
		
		/* Checking if the task failed */
		if( failed )
		{
			System.out.println( "Failed!" );
			
			matches = pattern_compilation_failure.matcher( input );
			while( matches.find() )
			{
				return new Status( "Compilation failed", "", matches.group( 1 ).trim(), true );
			}
			
			matches = pattern_build_error.matcher( input );
			while( matches.find() )
			{
				return new Status( "Build error", "", matches.group( 1 ).trim(), true );
			}
		}
		
		return new Status();
	}
	
	public String getName()
	{
		return "Maven";
	}
}
