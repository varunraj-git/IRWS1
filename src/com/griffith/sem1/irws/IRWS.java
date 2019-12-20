package com.griffith.sem1.irws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class IRWS {

	public static void main(final String[] args) throws Exception {
		/* read input data */
		final List<String> inputData = getInputData(args[0]);
		/* process the search data from the file */
		final String outputDt = processInputData(inputData);
		/* prints to file */
		printOutputData(outputDt);
	}

	/*
	 * Read the input file from given file name Expects the file to be in the
	 * classpath if not given the absolute path
	 */
	public static List<String> getInputData(final String arg) throws IOException {

		try (final FileReader fr = new FileReader(arg); final BufferedReader br = new BufferedReader(fr)) {
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
	public static void printOutputData(final String output) throws IOException {
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

	/*
	 * Inputs the list from the file returns the processed data as String
	 */
	public static String processInputData(final List<String> data) {

		/* key = engine# value = count */
		final Map<String, Integer> searchEngineCount = new HashMap<>();
		/* key = engine# value = precision count */
		final Map<String, Double> searchEngineAvgPrec = new HashMap<>();

		for (final String inp : data) {
			final String[] arr = inp.split(";");
			final int rel = Integer.parseInt(arr[3]);
			final int ret = arr[2].length();
			final int relret = getRCount(arr[2]);
			final double precision = getRoundedNumber((double) relret / (double) ret);
			final double recall = getRoundedNumber((double) relret / (double) rel);
			final double patFive = getRoundedNumber(getPatNumber(arr[2], 5));
			double patr05 = 0.0;
			if (recall >= 0.5 && recall < 0.6) {
				patr05 = precision;
			}
			final double averagePrecision = getAveragePrecision(arr[2], rel);

			/*
			 * Getting search engine count. Updates the map with existing count if any,
			 * inserts new if not present. will also adds the average precision and puts in
			 * `searchEngineAvgPrec`
			 */
			if (searchEngineCount.get(arr[1]) != null) {
				searchEngineCount.put(arr[1], searchEngineCount.get(arr[1]) + 1);
				searchEngineAvgPrec.put(arr[1], searchEngineAvgPrec.get(arr[1]) + averagePrecision);
			} else {
				searchEngineCount.put(arr[1], 1);
				searchEngineAvgPrec.put(arr[1], averagePrecision);
			}

			System.out.println(inp);
			System.out.println("=============================");
			System.out.println("Precision: " + precision);
			System.out.println("Recall: " + recall);
			System.out.println("P@5: " + patFive);
			System.out.println("P@R=0.5" + patr05);
			System.out.println("Average Precision: " + averagePrecision);
			System.out.println("\n");
		}

		// Here the 'searchEngineCount' gets updated with average by dividing with the
		// Q(number of engines)
		searchEngineCount.entrySet().forEach(entry -> {
			final double meanAveragePrecision = getRoundedNumber(
					searchEngineAvgPrec.get(entry.getKey()) / entry.getValue());
			searchEngineAvgPrec.put(entry.getKey(), meanAveragePrecision);

		});

		return getTopThreeSearchEngines(searchEngineAvgPrec);

	}

	/*
	 * Loop through the string, get count of 'R'
	 */
	private static int getRCount(final String engine) {
		int count = 0;
		for (int i = 0; i < engine.length(); i++) {
			if (engine.charAt(i) == 'R') {
				++count;
			}
		}
		return count;
	}

	/*
	 * Generic function to get precision at a given number
	 */
	private static double getPatNumber(final String str, final int num) {
		if (num > str.length()) {
			return 0.0;
		}

		int i = 0;
		int count = 0;
		while (i < num) {
			if (str.charAt(i) == 'R') {
				++count;
			}
			++i;
		}
		return count / (double) num;

	}

	/*
	 * Find average precision based on given formula
	 */
	private static double getAveragePrecision(final String str, final int rel) {
		final List<Integer> rList = getRs(str);
		double tempVal = 0.0;
		for (int i = 0; i < rList.size(); i++) {
			tempVal += (i + 1) / (double) rList.get(i);
		}
		return getRoundedNumber(tempVal / rel);
	}

	/*
	 * get the list of locations where R is present in the input string
	 */

	private static List<Integer> getRs(final String str) {
		final List<Integer> rList = new ArrayList<>();

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 'R') {
				rList.add(i + 1);
			}
		}
		return rList;
	}

	/*
	 * returns top three engines as a concatenated string in the format top
	 * Engine1#averageP;Engine2#averageP;Engine3#averageP
	 */
	private static String getTopThreeSearchEngines(final Map<String, Double> values) {
		double first = 0.0;
		String firstS = "";
		double second = 0.0;
		String secondS = "";
		double third = 0.0;
		String thirdS = "";
		for (final Entry<String, Double> data : values.entrySet()) {
			final double val = data.getValue();
			if (val > first) {
				third = second;
				thirdS = secondS;
				second = first;
				secondS = firstS;
				first = val;
				firstS = data.getKey();

			} else if (val > second) {
				third = second;
				thirdS = secondS;
				second = val;
				secondS = data.getKey();
			} else if (val > third) {
				third = val;
				thirdS = data.getKey();
			}

		}
		final StringBuilder sb = new StringBuilder();
		sb.append(firstS).append("#").append(first).append(";").append(secondS).append("#").append(second).append(";")
				.append(thirdS).append("#").append(third);

		return sb.toString();
	}

	private static double getRoundedNumber(final double val) {
		return Math.round(val * 100.0) / 100.0;
	}
}
