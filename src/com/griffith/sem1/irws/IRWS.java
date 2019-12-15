package com.griffith.sem1.irws;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IRWS {

	public static void main(String[] args) throws Exception {

		List<String> inputData = getInputData(args[0]);
		processInputData(inputData);

	}

	private static List<String> getInputData(final String arg) throws Exception {
		FileReader fr = new FileReader(arg);
		BufferedReader br = new BufferedReader(fr);
		List<String> response = new ArrayList<>();
		String st;
		while ((st = br.readLine()) != null) {
			response.add(st);
		}

		br.close();
		return response;

	}

	private static void processInputData(final List<String> data) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		for (String inp : data) {
			String arr[] = inp.split(";");
			int rCount = getRCount(arr[2]);
			double precision = getPrecision(rCount, arr[2].length());
			double recall = getRecall(rCount, Integer.parseInt(arr[3]));
			double patFive = getPatFive(arr[2]);
			
		    writer.write("Precision: " + precision + ", Recall: " + recall + ", P@5: " + patFive);
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

	private static double getPrecision(final int rCount, final int count) {
		double precision = (double) rCount / (double) count;

		// System.out.println("The precision is: " + precision);
		return precision;
	}

	private static double getRecall(final int rCount, final int relavent) {
		double recall = (double) rCount / (double) relavent;
		// System.out.println("The recall is:" + recall);
		return recall;
	}

	private static double getPatFive(final String str) {
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			char t = str.charAt(i);
			if (t == 'R') {
				++count;
			}
			if (count > 0) {
				sb.append(t);
			}
			if (count == 5) {
				break;
			}
		}
		double patFive = 5.0 / sb.length();
		// System.out.println("P@5: " +patFive);
		return patFive;

	}
}