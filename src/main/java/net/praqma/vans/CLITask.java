package net.praqma.vans;

import java.io.File;

import net.praqma.vans.command.Command;
import net.praqma.vans.command.Shell;
import net.praqma.vans.filter.MavenFilter;
import net.praqma.vans.util.TaskException;

public class CLITask
{
	private static final String cwd = System.getProperty( "user.dir" );

	public static void main( String[] args ) throws TaskException
	{		
		/* PROTOTYPE shell */
		//Shell shell = new Shell( new File( cwd ), "dir" );
		//Shell shell = new Shell( new File( cwd ), "cd /d c:\\projects\\COOL\\trunk & mvn package" );
		Shell shell1 = new Shell( new File( "c:\\projects\\COOL\\trunk" ), "mvn package" );
		Shell shell2 = new Shell( new File( "c:\\projects\\VANS\\trunk" ), "mvn package" );
		//Shell shell = new Shell( new File( cwd ), "dir" );
		MavenFilter filter = new MavenFilter();
		
		
		Task task = new Task( new Command[]{shell1,shell2}, filter );
		//Task task = new Task( shell, filter );
		
		
		task.run();
		
		File findings = new File( "vansreport.xml" );
		File junit = new File( "junitreport.xml" );
		task.saveVANSReport( findings );
		task.saveReport( junit );
	}

}
