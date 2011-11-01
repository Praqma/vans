/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.vans.report;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import net.praqma.vans.VANSReport;
import net.praqma.vans.filter.Finding;

import net.praqma.vans.util.Status;

/**
 *
 * @author jssu
 */
public class QACReporter {

    private String input;
    private File reportFile;

    public QACReporter(String input, File filePath) {
        this.input = input;
        this.reportFile = filePath;

    }

    public void report() {
        String xml = parseResult();


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(reportFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QACReporter.class.getName()).log(Level.SEVERE, null, ex);
        }


        DataOutputStream dos = new DataOutputStream(fos);
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(dos));
        try {
            br.write(xml);
            br.close();
        } catch (IOException ex) {
            System.out.println("Could not write xml to file:\n" + ex.getMessage());
        }


    }

    private String parseResult() {
        ArrayList<String> result = new ArrayList<String>();
        VANSReport report = new VANSReport("QAC errors");

        String path = "";
        for (String s : input.split("\n")) {
            if (s.matches("") || s.matches(".*-.*")) {
                continue;
            }

            /*     Pattern fileP = Pattern.compile("^.*[h|c][\\s*|\\d]*$", Pattern.MULTILINE);
            Pattern dirP = Pattern.compile("^.*\\\\.*$", Pattern.MULTILINE);

            if (dirP.matcher(s).find()) {
            //if the line is pointing to a dir
            path = s.replace(" ", "");
            continue;
            } else if (fileP.matcher(s).find()) {
            //if the line is pointing to a file it will have multiple numbers in it
            } else {
            //else this is just 'noice line'
            continue;
            }

            String fqFile = path + "\\" + s;
            HashMap<Integer, String> temp = new HashMap<Integer, String>();

            int i = 0;
            for (String s1 : fqFile.split("\\s++")) {
            temp.put(i, s1);
            i++;
            }

            fqFile = temp.get(0);

            //if value of key i-1 is 0 it means there are 0 total erros for this file
            int errors = Integer.parseInt(temp.get(i - 1));
            if (errors <= 0) {
            continue;
            }*/


            Pattern totalP = Pattern.compile("^Total.*\\d$");
            if (!totalP.matcher(s).find()) {
                continue;
            }

            HashMap<Integer, String> temp = new HashMap<Integer, String>();
            int i = 0;
            for (String s1 : s.split("\\s++")) {
                temp.put(i, s1);
                i++;
            }

            int errors = Integer.parseInt(temp.get(i - 1));

            Finding finding = new Finding("QAC has " + temp.get(i - 1) + " errors", Finding.Level.ERROR);

            report.addCase(finding, errors,"QAC",  new Status(temp.get(0), "QAC errors", "QAC has "+ errors + " errors", true));

        }

        return report.getXML();

    }
}
