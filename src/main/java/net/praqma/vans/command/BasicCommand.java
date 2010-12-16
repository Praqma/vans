package net.praqma.vans.command;

import java.io.File;

public class BasicCommand implements Command
{
	
	boolean verbose = false;
	protected String cmd = "";
	protected File cwd = null;
	

	public String execute()
	{
		return null;
	}


	public void verbose( boolean verbose )
	{
		this.verbose = verbose;
	}


	public String getCmd()
	{
		return cmd;
	}


	public File getCwd()
	{
		return cwd;
	}
}
