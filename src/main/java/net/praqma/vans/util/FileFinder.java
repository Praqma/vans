package net.praqma.vans.util;

import java.util.*;
import java.io.*;

/**
 * Recursive file listing under a specified directory.
 *
 * @author Jes Struck
 */
public final class FileFinder {

    private File diroctory;
    private String regex;


    public FileFinder(File file, String regex) {
        this.diroctory = file;
        this.regex = regex;
    }

    /**
     * Recursively walk a directory tree and return a List of all
     * Files found; the List is sorted using File.compareTo().
     *
     * @param aStartingDir is a valid directory, which can be read.
     */
    public List<File> getFileListing() throws FileNotFoundException {
        validateDirectory(diroctory);
        List<File> result = getFileListingNoSort(diroctory);
        Collections.sort(result);
        return result;
    }

    // PRIVATE //
    private List<File> getFileListingNoSort(
            File aStartingDir) throws FileNotFoundException {
        List<File> result = new ArrayList<File>();
        File[] filesAndDirs = aStartingDir.listFiles();
        List<File> filesDirs = Arrays.asList(filesAndDirs);
        for (File file : filesDirs) {
            if (!file.isFile()) {
                //must be a directory
                //recursive call!
                List<File> deeperList = getFileListingNoSort(file);
                result.addAll(deeperList);
            }
            if (file.toString().matches(this.regex)) {
                result.add(file); //always add, even if directory
            }
        }
        return result;
    }

    /**
     * Directory is valid if it exists, does not represent a file, and can be read.
     */
    private void validateDirectory(
            File aDirectory) throws FileNotFoundException {
        if (aDirectory == null) {
            throw new IllegalArgumentException("Directory should not be null.");
        }
        if (!aDirectory.exists()) {
            throw new FileNotFoundException("Directory does not exist: " + aDirectory);
        }
        if (!aDirectory.isDirectory()) {
            throw new IllegalArgumentException("Is not a directory: " + aDirectory);
        }
        if (!aDirectory.canRead()) {
            throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
        }
    }
}