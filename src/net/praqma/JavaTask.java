package net.praqma;

public class JavaTask {

	public static void main(String[] args) {
		int length = args.length;
		if(length==0){
			System.out.println("Write parameters.");
		}else{
			System.out.println("Welcome to Skynet.");
			for(int i=0;i<length;i++){
				String cmd = args[i];
				if(cmd.startsWith("junitlog=")){
					System.out.println("Running JunitLog...");
					String fqname = cmd.substring(9);
					CoolLog cl = new CoolLog(fqname);
					System.out.println(cl.getString());
				} else if(cmd.startsWith ("shellpname=")){
					System.out.println("Getting shell...");
					String fqname = cmd.substring(11);
					Shell shell = new Shell(fqname);
					System.out.println("Executing command...");
					shell.execute();
					System.out.println("Done executing.");
				}
				else {
					System.out.println("Command not supported: "+cmd);
				}
			}
		}
	}

}
