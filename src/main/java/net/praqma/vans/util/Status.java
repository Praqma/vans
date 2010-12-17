package net.praqma.vans.util;

public class Status
{
	public String message = "";
	public String type    = "";
	public String text    = "";
	
	public boolean failed = false;
	
	public Status( String message, String type, String text, boolean failed )
	{
		this.message = message;
		this.type    = type;
		this.text    = text;
		
		this.failed  = failed;
	}
	
	public Status()
	{
		
	}
}
