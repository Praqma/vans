package net.praqma.vans.command;

public class BasicCommand implements Command
{
	
	boolean verbose = false;
	

	public String execute()
	{
		return null;
	}


	public void verbose( boolean verbose )
	{
		this.verbose = verbose;
	}
}
