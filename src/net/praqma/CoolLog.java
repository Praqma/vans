package net.praqma;

public class CoolLog {
	String fqname;
	
	protected CoolLog(String fqname){
		this.fqname = fqname;
	}
	
	public void doSomething(){
		System.out.println(getString());
	}
	
	public String getString(){
		return "Hello from "+fqname;
	}
	
	public void throwException() throws Exception {
		throw new Exception("OH NO!");
	}
}
