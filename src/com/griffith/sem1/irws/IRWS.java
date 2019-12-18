package com.griffith.sem1.irws;

import java.util.List;

public class IRWS {

    public static void main(final String[] args) throws Exception {
        final FileOperations fileOps = new FileOperations();
        final ProcessSearchData processData = new ProcessSearchData();

        /* read input data */
        final List<String> inputData = fileOps.getInputData(args[0]);
        final String outputDt = processData.processInputData(inputData);
        fileOps.printOutputData(outputDt);
    }
}
