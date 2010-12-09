package net.praqma;

public class Task {
	private Filter filter;
	private CoolLog log;
	
	public Task(){
		log = new CoolLog("FQName");
	}
	
	public void setFilter(String filterName) throws Exception{
		if(filterName.equals("Default"))
			filter = new Filter(filterName);
		else
			throw new Exception("Filter not supported.");
	}
	
	public CoolLog getLog(){
		return log;
	}
	
	protected String protectedMethod(){
		return "This method is protected.";
	}
	
	public void execute(){
		
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Filter getFilter() {
		return filter;
	}
}
