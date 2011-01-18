package net.praqma.vans.task;

import java.io.File;

import net.praqma.vans.util.VANSException;

public abstract interface Task
{
	public String getCmd();
	public File getCwd();
	public abstract String execute() throws VANSException;
	public void verbose( boolean verbose );
}
