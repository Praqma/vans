package net.praqma.vans.util;

public class VANSException extends Exception
{
	public UCMType type = UCMType.DEFAULT;
	
	public enum UCMType
	{
		DEFAULT
	}
	
	public VANSException()
	{
		super(); 
	}
	
	public VANSException( String s )
	{
		super( s ); 
	}
	
	public VANSException( String s, UCMType type )
	{
		super( s );
		
		this.type = type;
	}

}