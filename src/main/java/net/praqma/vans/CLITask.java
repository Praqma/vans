package net.praqma.vans;

import java.io.File;

import net.praqma.vans.command.Command;
import net.praqma.vans.command.Shell;
import net.praqma.vans.filter.MavenFilter;

public class CLITask
{
	private static final String cwd = System.getProperty( "user.dir" );

	public static void main( String[] args )
	{		
		/* PROTOTYPE shell */
		//Shell shell = new Shell( new File( cwd ), "dir" );
		//Shell shell = new Shell( new File( cwd ), "cd /d c:\\projects\\COOL\\trunk & mvn package" );
		Shell shell = new Shell( new File( "c:\\projects\\COOL\\trunk" ), "mvn package" );
		//Shell shell = new Shell( new File( cwd ), "dir" );
		MavenFilter filter = new MavenFilter();
		
		
		Task task = new Task( new Command[]{shell,shell}, filter );
		//Task task = new Task( shell, filter );
		task.run();
	}

}
