/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import net.praqma.util.option.Option;
import net.praqma.util.option.Options;

/**
 *
 * @author JsSu
 */
public class FileFinder {

    public static void main(String[] args) {
        Options o = new Options(net.praqma.vans.Version.version);

        Option ofile = new Option("path", "p", true, 1, "path from where we should looke");
        Option orel = new Option("critiria", "c", true, 1, "which files we should report");

        o.setOption(ofile);
        o.setOption(orel);

        o.setDefaultOptions();

        o.setSyntax("FileFinder -p <Path>  -c <critiria>");

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
            System.err.println("The path does not exist");
            System.exit(1);
        }
        try{
            net.praqma.vans.util.FileFinder finder = new net.praqma.vans.util.FileFinder(file, orel.getString());
            List<File> files = finder.getFileListing();
            for (File f : files) {
                System.out.println(f);
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
