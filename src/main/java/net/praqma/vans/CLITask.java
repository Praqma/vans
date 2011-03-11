package net.praqma.vans;

import java.io.File;

import net.praqma.util.debug.PraqmaLogger;
import net.praqma.vans.filter.MavenFilter;
import net.praqma.vans.task.Shell;
import net.praqma.vans.task.Task;
import net.praqma.vans.util.VANSException;

public class CLITask
{
	private static final String cwd     = System.getProperty( "user.dir" );
	private static final String filesep = System.getProperty( "file.separator" );

	public static void main( String[] args ) throws VANSException
	{
		File site_conf = new File( System.getProperty( "user.home" ) + filesep + "vans.conf" );
		/* PROTOTYPE shell */
		//Shell shell = new Shell( new File( cwd ), "dir" );
		//Shell shell = new Shell( new File( cwd ), "cd /d c:\\projects\\COOL\\trunk & mvn package" );
		Shell shell1 = new Shell( new File( "c:\\projects\\PRAQMA\\COOL\\trunk" ), "mvn package" );
		Shell shell2 = new Shell( new File( "c:\\projects\\PRAQMA\\VANS\\trunk" ), "mvn package" );
		//Shell shell = new Shell( new File( cwd ), "dir" );
		MavenFilter filter = new MavenFilter();
		
		
		Tasks task = new Tasks( new Task[]{shell1,shell2}, filter );
		//Task task = new Task( shell, filter );
		
		
		task.run();
		
		File findings = new File( "vansreport.xml" );
		File junit = new File( "junitreport.xml" );
		task.saveVANSReport( findings );
		task.saveReport( junit );
	}

}
