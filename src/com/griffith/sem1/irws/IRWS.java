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
			int rel = Integer.parseInt(arr[3]);
			int ret = arr[2].length();
			int relret = getRCount(arr[2]);
			double precision = (double) relret / (double) ret;
			double recall = (double) relret/(double) rel;
			double patFive = getPatNumber(arr[2], 5);
			double patn = getPatNumber(arr[2], rel);
			double averagePrecision = 
			
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


	private static double getPatNumber(final String str, final int num) {
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
			if (count == num) {
				break;
			}
		}
		double patNum = (double) num / sb.length();
		return patNum;

	}
	
	private static double getAveragePrecision(final String str, final int rel) {
		Object rList[] = getRs(str);
		
		
		
	}
	
	private static Object[] getRs(final String str) {
		List<Integer> rList = new ArrayList<>();
		
		for(int i=0; i< str.length(); i++) {
			if(str.charAt(i) == 'R') {
				rList.add(i+1);
			}
		}
		return rList.toArray();
	}
}