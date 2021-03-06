package net.praqma.cli;

import java.io.File;
import java.io.IOException;

import net.praqma.vans.parse.KeilParser;
import net.praqma.vans.util.VANSException;
import net.praqma.util.option.Option;
import net.praqma.util.option.Options;

public class KeilParse {

	public static void main(String[] args) {
		//System.out.println("VAnS version: " + net.praqma.vans.Version.version);
		Options o = new Options(net.praqma.vans.Version.version);

		Option ofile = new Option("file", "f", true, 1,
				"The file to parse. *.uvproj");
		Option orel = new Option("relative", "r", false, 0,
				"If set, only the relative path is provided");
		Option ocut = new Option("cutoff", "c", false, 1,
				"Cutoff a given string from the path");

		o.setOption(ofile);
		o.setOption(orel);
		o.setOption(ocut);

		o.setDefaultOptions();

		o.setSyntax("KeilParser -f <file> [ -r ] [ -c <cutoff> ]");
		o.setHeader("Parse an Keil project file for files.");
		o.setDescription("Examples:" + Options.linesep
				+ "KeilParse -f test.uvproj -c c:\\data");

		o.parse(args);

		try {
			o.checkOptions();
		} catch (Exception e) {
			System.err.println("Incorrect option: " + e.getMessage());
			o.display();
			System.exit(1);
		}
		File file = new File(ofile.getString());

		if (!file.exists()) {
			System.err.println("The file does not exist");
			System.exit(1);
		}

		if (o.verbose()) {
			o.print();
		}

		boolean relative = orel.used ? true : false;
		String cutoff = ocut.used ? ocut.getString().toLowerCase() : "";

		try {
			KeilParser p = new KeilParser(file);
			int length = p.getPath().length();
			for (File f : p.getFiles()) {
				String d = "";
				if (relative) {
					d = f.getAbsolutePath().substring(length);
				} else {
					d = f.getAbsolutePath();
				}

				if (cutoff.length() > 0) {
					if (d.toLowerCase().startsWith(cutoff)) {
						d = d.substring(cutoff.length());
					}
				}

				System.out.println(d);
			}
		} catch (IOException e) {
			System.err.println("The file was not an acceptable project file: "
					+ e.getMessage());
			System.exit(1);
		} catch (VANSException e) {
			System.err.println(e.getMessage());
		}
	}
}
