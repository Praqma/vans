package net.praqma.vans.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import net.praqma.util.xml.XML;

public class IARParser extends XML
{
	private List<File> files = new ArrayList<File>();
	private String dir       = null;
	
	private static final Pattern pattern_proj_dir = Pattern.compile( ".*\\$PROJ_DIR\\$(.*)", Pattern.MULTILINE );

	public IARParser( File ewp ) throws IOException
	{
		super( ewp );
		
		this.files = process( getRoot() );
		
		this.dir = ewp.getParentFile().getPath();
	}
	
	private List<File> process( Element root )
	{
		List<Element> groups = getElements( root, "group" );
		List<File> files = new ArrayList<File>();
		
		for( Element g : groups )
		{
			files.addAll( process( g ) );
		}
		
		List<Element> fileElements  = getElements( root, "file" );
		
		for( Element f : fileElements )
		{
			String file = f.getTextContent();
			Matcher match = pattern_proj_dir.matcher( file );
			
			if( match.find() )
			{
				
				files.add( new File( dir + match.group( 1 ) ) );
			}
		}
		
		return files;
	}
	
	public List<File> getFiles()
	{
		return files;
	}

}
