package com.griffith.sem1.irws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class IRWS {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public static void main(final String[] args) throws Exception {

        final List<String> inputData = getInputData(args[0]);
        processInputData(inputData);

    }

    private static List<String> getInputData(final String arg) throws Exception {
        final FileReader fr = new FileReader(arg);
        final BufferedReader br = new BufferedReader(fr);
        final List<String> response = new ArrayList<>();
        String st;
        while ((st = br.readLine()) != null) {
            response.add(st);
        }

        br.close();
        return response;

    }

    private static void processInputData(final List<String> data) throws IOException {

        final BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        for (final String inp : data) {
            final String arr[] = inp.split(";");
            final int rel = Integer.parseInt(arr[3]);
            final int ret = arr[2].length();
            final int relret = getRCount(arr[2]);
            final double precision = (double) relret / (double) ret;
            final double recall = (double) relret / (double) rel;
            final double patFive = getPatNumber(arr[2], 5);
            final double patn = getPatNumber(arr[2], rel);
            final double averagePrecision = getAveragePrecision(arr[2], rel);

            writer.write("Precision: " + precision + ", Recall: " + recall + ", P@5: " + patFive +
                    ", p@n: " + patn + ", averageP: " + averagePrecision);
            writer.newLine();
        }
        writer.close();

    }

    private static int getRCount(final String engine) {
        int count = 0;
        for (int i = 0; i < engine.length(); i++) {
            if (engine.charAt(i) == 'R') {
                ++count;
            }
        }
        return count;
    }

    private static double getPatNumber(final String str, final int num) {
        final StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            final char t = str.charAt(i);
            if (t == 'R') {
                ++count;
            }
            if (count > 0) {
                sb.append(t);
            }
            if (count == num) {
                break;
            }
        }
        final double patNum = (double) num / sb.length();
        return patNum;

    }

    private static double getAveragePrecision(final String str, final int rel) {
        final List<Integer> rList = getRs(str);
        double tempVal = 0.0;
        for (int i = 0; i < rList.size(); i++) {
            tempVal = (i + 1) / (double) rList.get(i);
        }
        return tempVal / rel;

    }

    private static List<Integer> getRs(final String str) {
        final List<Integer> rList = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'R') {
                rList.add(i + 1);
            }
        }
        return rList;
    }
}
