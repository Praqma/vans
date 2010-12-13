package net.praqma;

import java.util.ArrayList;
import java.util.List;

public class Filter {
	private List<String> include;
	private List<String> exclude;

	public Filter(List<String> include, List<String> exclude) {
		this.include = include;
		this.exclude = exclude;
	}

	public List<String> filter(List<String> list) {
		List<String> result = new ArrayList<String>();
		for (String string : list) {
			if(include.contains(string) && !exclude.contains(string)){
				result.add(string);
			}
		}
		return result;
	}
}
