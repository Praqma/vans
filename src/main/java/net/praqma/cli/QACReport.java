/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.cli;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.praqma.util.option.Option;
import net.praqma.util.option.Options;
import net.praqma.vans.report.QACReporter;

/**
 *
 * @author jssu
 */
public class QACReport {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("VAnS version: " + net.praqma.vans.Version.version);
        Options o = new Options(net.praqma.vans.Version.version);

        Option ofile = new Option("file", "f", true, 1,
                "The output file to parse.");
        Option oreport = new Option("report", "r", false, 1,
                "The file which we should report to");

        o.setOption(ofile);
        o.setOption(oreport);


        o.setDefaultOptions();

        o.setSyntax("QACReporter -f <file>  -r ");
        o.setHeader("Reports the number of QAC warnings from ");
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
        File outputFile = new File(ofile.getString());
        File reportFile = new File(oreport.getString());

        if (!outputFile.exists()) {
            System.err.println("The file does not exist");
            System.exit(1);
        }

        if (o.verbose()) {
            o.print();
        }

        FileInputStream fis = new FileInputStream(outputFile);

        DataInputStream dis = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
        String endText = "";
        String txt;
        try {
            while (((txt = br.readLine()) != null)) {
                endText = endText + txt + "\n";
            }
        } catch (IOException ex) {
            Logger.getLogger(QACReport.class.getName()).log(Level.SEVERE, null, ex);
        }



        QACReporter qacrep = new QACReporter(endText, reportFile);

        qacrep.report();

    }
}
