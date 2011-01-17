package net.praqma.vans.task;

import java.io.File;

public abstract interface Task
{
	public String getCmd();
	public File getCwd();
	public abstract String execute();
	public void verbose( boolean verbose );
}
