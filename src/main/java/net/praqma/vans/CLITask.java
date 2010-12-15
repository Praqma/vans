package net.praqma.vans;

import java.io.File;

import net.praqma.vans.command.Shell;
import net.praqma.vans.filter.MavenFilter;

public class CLITask
{
	private static final String cwd = System.getProperty( "user.dir" );

	public static void main( String[] args )
	{
		System.out.println( "DIR IS : " + cwd );
		
		/* PROTOTYPE shell */
		//Shell shell = new Shell( new File( cwd ), "dir" );
		Shell shell = new Shell( new File( cwd ), "cd /d c:\\projects\\COOL\\trunk & mvn package" );
		MavenFilter filter = new MavenFilter();
		Task task = new Task( shell, filter );
		task.run();
	}

}
