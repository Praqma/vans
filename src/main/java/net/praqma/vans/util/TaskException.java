package net.praqma.vans.util;

public class TaskException extends Exception
{
	public UCMType type = UCMType.DEFAULT;
	
	public enum UCMType
	{
		DEFAULT
	}
	
	public TaskException()
	{
		super(); 
	}
	
	public TaskException( String s )
	{
		super( s ); 
	}
	
	public TaskException( String s, UCMType type )
	{
		super( s );
		
		this.type = type;
	}

}