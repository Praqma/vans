package net.praqma;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Shell {
	private String path;
	private boolean merge;
	private boolean dieOnFailure;
	
	public Shell(String path){
		this.path = path;
	}
	
	public void execute(){
		try{
			FileReader reader = new FileReader(path);
			BufferedReader in = new BufferedReader(reader);
			String text = null;
			while( (text = in.readLine()) != null){
				Process p = Runtime.getRuntime().exec(text);
				//waitFor(): When waiting, this process doesn't print out until exec(text) is done.
				//if we don't waitFor(), each line added to output will show on screen as it is added.
				p.waitFor();
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				System.out.println("Output from process:"+text+":");
				while((line=input.readLine()) != null){
					System.out.println("\t"+line);
				}
			}
			in.close();
			reader.close();
			
		} catch(Exception e){
			System.out.println("User error. (Message: "+e.getMessage()+")");
		}
	}

}
