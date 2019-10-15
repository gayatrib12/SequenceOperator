package Bioinformatics;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CommonUtilities {

    private List<String> readFromFile(String inputFileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputFileName));
        scanner.useDelimiter(" ");

        List<String> sequenceCollector = new ArrayList<>();
        while(scanner.hasNext()){
            sequenceCollector.add(scanner.next());
        }
        scanner.close();
        return sequenceCollector;
    }

    private void writeToFile(String outputFileName, List<String> outputToFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

        Iterator iterator = outputToFile.iterator();
        while (iterator.hasNext()){
            writer.write(iterator.next().toString());
        }
        writer.close();
    }
}
