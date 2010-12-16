package net.praqma.vans.command;

public abstract interface Command
{
	public abstract String execute();
	public void verbose( boolean verbose );
}
