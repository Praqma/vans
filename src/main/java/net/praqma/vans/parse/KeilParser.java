package net.praqma.vans.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.praqma.util.xml.XML;
import net.praqma.vans.util.VANSException;

public class KeilParser extends XML {
	private List<File> files = new ArrayList<File>();
	private String dir = null;

	private static final Pattern pattern_proj_dir = Pattern.compile("$(.*)",
			Pattern.MULTILINE);

	public KeilParser(File uvproj) throws IOException, VANSException {
		super(uvproj);
		this.dir = uvproj.getParentFile().getPath();

		this.files = process(getRoot());

	}

	private List<File> process(Element root) throws VANSException {

		List<Element> targets = getElements(root, "Targets");
		if (targets.size() != 1) {
			throw new VANSException(
					"We does not have support for multiple targets in a Keil project file yet");
		}
		List<Element> t = getElements(targets.get(0), "Target");
		List<Element> gs = getElements(t.get(0), "Groups");
		List<Element> g = getElements(gs.get(0), "Group");
		List<Element> fs = getElements(g.get(0), "Files");
		List<Element> fileElements = getElements(fs.get(0), "File");

		for (Element f : fileElements) {
			List<Element> fn = getElements(f, "FileName");

			String file = fn.get(0).getTextContent();
			Matcher match = pattern_proj_dir.matcher(file);

			if (match.find()) {
				File f1 = new File(dir + "\\" + file);
				this.files.add(f1);
			}
		}

		return files;
	}

	public List<File> getFiles() {
		return files;
	}

	public String getPath() {
		return this.dir;
	}

}
