package net.praqma.vans.task;

import java.io.File;

public class BasicTask implements Task
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
