# IRWS1

Input:

- text file name that contains the queries

Output:

- Outputs the Following in the console:
        -  Precision
        -  Recall
        -  P@5
        -  P@R=0.5
        -  Average precision
        -  Mean average precision for each search engines

- Outputs the top three search engines and therir mean average precision in a text file 'output.txt'

# Calculation
The mean average precision (MAP) is taken after calculating average precisions of all input data for each search engine. The search engines that give top three MAP are selected and printed on an output file.

# Running the appliation
`javac -d . src\com\griffith\sem1\irws\IRWS.java`

`java com.griffith.sem1.irws.IRWS <command line parameter>`

# Run requirements
| Property | Info |
| ------ | ------ |
| java | 1.8 or newer  |
| Main File | com.griffith.sem1.irws.IRWS.java |
|||
