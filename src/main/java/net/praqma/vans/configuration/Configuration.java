package net.praqma.vans.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.praqma.util.debug.Logger;
import net.praqma.vans.task.Task;

public class Configuration
{
	protected Map<String, String> options = new HashMap<String, String>();
	
	protected Logger logger = Logger.getLogger();
	
	public enum Type
	{
		unknown,
		Maven
	}
	
	protected Type type = Type.unknown;
	public String name = "";
	public File workingDir = null;
	
	public Task buildTask()
	{
		System.out.println( "Whow.... This is the default configuration..." );
		
		return null;
	}
	
	/* Setters */
	
	public void setName( String name )
	{
		this.name = name;
	}
	
	public void setType( Type type )
	{
		this.type = type;
	}
	
	public void addOption( String name, String option )
	{
		options.put( name, option );
	}
	
	/* Getters */
	
	/* AUX */
	
	public void print()
	{
		Iterator<Entry<String, String>> it = options.entrySet().iterator();
		int c = 0;
	    while( it.hasNext() )
	    {
	    	
	    	
	    	Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
	    	
	    	if( entry.getValue().length() > 0 )
	    	{
		    	c++;
		    	System.out.println( "[" + c + "] " + entry.getKey().toString() + ": " + entry.getValue().toString() );
	    	}
	    }
	}
}
