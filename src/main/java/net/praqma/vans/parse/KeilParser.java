package net.praqma.vans.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import net.praqma.util.xml.XML;
import net.praqma.vans.util.VANSException;

public class KeilParser extends XML {

    private List<File> files = new ArrayList<File>();
    private String dir = null;
    private static final Pattern pattern_proj_dir = Pattern.compile("$(.*)", Pattern.MULTILINE);

    public KeilParser(File uvproj) throws IOException, VANSException {
        super(uvproj);
        this.dir = uvproj.getParentFile().getPath();

        this.files = process(getRoot());

    }

    private List<File> process(Element root) throws VANSException {

        List<Element> targets = getElements(root, "Targets");
        if (targets.size() != 1) {
            throw new VANSException("We does not have support for multiple targets in a Keil project file yet");
        }
        List<Element> t = getElements(targets.get(0), "Target");
        List<Element> gs = getElements(t.get(0), "Groups");
        List<Element> g = getElements(gs.get(0), "Group");

        int groupIndex = 0;
        for (Element group : g) {
            List<Element> fs = getElements(g.get(groupIndex), "Files");
            List<Element> fileElements = getElements(fs.get(0), "File");
            groupIndex++;
            // Finds all the .c files in a Keil project
            for (Element f : fileElements) {
                List<Element> fn = getElements(f, "FilePath");

                String file = fn.get(0).getTextContent();
                Matcher match = pattern_proj_dir.matcher(file);

                if (match.find()) {
                    File f1 = new File(dir + "\\" + file);
                    this.files.add(f1);
                }
            }

            // Finds all .h files in a Keil project
            List<Element> t1 = getElements(targets.get(0), "Target");
            for (Element f : getElements(t1.get(0))) {
                // System.out.println("Target: "+f.getNodeName());
            }
            List<Element> to = getElements(t1.get(0), "TargetOption");
            for (Element f : getElements(to.get(0))) {
                // System.out.println("TargetOption: "+f.getNodeName());
            }

            List<Element> tco = getElements(to.get(0), "TargetArmAds");
            for (Element f : getElements(tco.get(0))) {
                // System.out.println("TargetArmAds: "+f.getNodeName());
            }

            List<Element> cAds = getElements(tco.get(0), "Cads");
            for (Element f : getElements(cAds.get(0))) {
                // System.out.println("cAds: "+f.getNodeName());
            }
            List<Element> vc = getElements(cAds.get(0), "VariousControls");
            List<Element> inc = getElements(vc.get(0), "IncludePath");

            String incPaht = inc.get(0).getTextContent();
            String[] incPaths = incPaht.split(";");

            for (String path : incPaths) {
                path = dir + path;
                Matcher match = pattern_proj_dir.matcher(path);
                if (match.find()) {

                    File directory = new File(path);
                    String[] children = directory.list();

                    if (children == null) {
                        break;
                        // Either dir does not exist or is not a directory
                    } else {
                        for (int i = 0; i < children.length; i++) {
                            // Get filename of file or directory
                            String filename = children[i];
                            if (filename.endsWith(".c") || filename.endsWith(".h") || filename.endsWith(".cpp")) {
                                this.files.add(new File(path + "\\" + filename));
                            }
                        }
                    }

                }

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
