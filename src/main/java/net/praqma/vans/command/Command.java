package net.praqma.vans.command;

import java.io.File;

public abstract interface Command
{
	public String getCmd();
	public File getCwd();
	public abstract String execute();
	public void verbose( boolean verbose );
}
