/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.praqma.vans.filter;

import java.util.ArrayList;

/**
 *
 * @author jssu
 */
public class Findings extends ArrayList<Finding> {

    private int errors = 0;

    public boolean add(Finding f) {
        boolean done = super.add(f);

        if (f.level == Finding.Level.ERROR || f.level == Finding.Level.FATAL) {
            errors++;
        }

        return done;
    }

    public int numberOfErrors() {
        return errors;
    }

    public void reset() {
        this.clear();
        errors = 0;
    }
}