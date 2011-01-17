package net.praqma.vans.configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration
{
	protected Map<String, String> options = new HashMap<String, String>();
	
	public enum Type
	{
		unknown,
		maven
	}
	
	protected Type type = Type.unknown;
	
	public void setType( Type type )
	{
		this.type = type;
	}
	
	public void addOption( String name, String option )
	{
		options.put( name, option );
	}
}
