package net.praqma.vans.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.praqma.vans.filter.Finding.Level;

public class MavenFilter extends Filter
{
	private static final Pattern pattern_warning = Pattern.compile( "^\\[WARNING\\]\\s*(.*)$", Pattern.MULTILINE );
	private static final Pattern pattern_error   = Pattern.compile( "^\\[ERROR\\]\\s*(.*)$", Pattern.MULTILINE );
	
	public MavenFilter()
	{
		System.out.println( "Creating Maven filter" );
	}
	
	public void filter( String input )
	{
		System.out.println( "Maven filter" );
		
		Matcher matches = null;
		
		/* Find warnings */
		matches = pattern_warning.matcher( input );
		while( matches.find() )
		{
			findings.add( new Finding( matches.group( 1 ), Level.WARNING ) );
		}
		
		/* Find errors */
		matches = pattern_error.matcher( input );
		while( matches.find() )
		{
			findings.add( new Finding( matches.group( 1 ), Level.ERROR ) );
		}
	}
}
