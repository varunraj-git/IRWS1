/**
 * Class used for file operations
 *
 *
 * */
package com.griffith.sem1.irws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

    /*
     * Read the input file from given file name
     * Expects the file to be in the classpath if not given the absolute path
     */
    public List<String> getInputData(final String arg) throws IOException {

        try (final FileReader fr = new FileReader(arg);
                final BufferedReader br = new BufferedReader(fr)) {
            final List<String> response = new ArrayList<>();
            String st;
            while ((st = br.readLine()) != null) {
                response.add(st);
            }
            return response;
        }

    }

    /*
     * Writes the output to output.txt
     */
    public void printOutputData(final String output) throws IOException {
        final String[] splits = output.split(";");
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write("TOP THREE ENGINES:");
            writer.newLine();
            writer.newLine();
            writer.write("Top Engines \t \t Average Precision");
            writer.newLine();
            writer.write("======================================");
            writer.newLine();
            writer.newLine();

            System.out.println("Mean Average Precision:");
            for (final String data : splits) {
                final String[] spl = data.split("#");
                writer.write("\t" + spl[0] + "\t \t \t \t \t \t" + spl[1]);
                System.out.println(spl[0] + " - " + spl[1]);
                writer.newLine();
            }
        }
    }

}
