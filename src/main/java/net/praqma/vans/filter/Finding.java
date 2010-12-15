package net.praqma.vans.filter;

public class Finding
{
	public enum Level
	{
		WARNING,
		ERROR,
		FATAL
	}
	
	public String finding = "";
	public Level level = Level.WARNING;
	
	public Finding( String finding, Level level )
	{
		this.finding = finding;
		this.level   = level;
	}
	

	public String toString()
	{
		return finding + ", " + level;
	}
}
