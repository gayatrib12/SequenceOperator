package Bioinformatics;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SequencePartitioner {

    String inputFile;
    int minSequenceRange;
    int maxSequenceRange;
    String outputFile;

    public SequencePartitioner(String inputFile, int minSequenceRange, int maxSequenceRange, String outputFile) {
        this.inputFile = inputFile;
        this.minSequenceRange = minSequenceRange;
        this.maxSequenceRange = maxSequenceRange;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) {
        //SequencePartitioner sequencePartitioner = new SequencePartitioner();
    }

    private void partitionSequences(String inputFileName, String outputFile, int minSequenceRange, int maxSequenceRange) throws IOException {
        List<String> partitionedSequence = new ArrayList<>();
        List<String> splitSequence = readFromFile(inputFileName);

        int splitSequenceIndex = 0;
        while(splitSequenceIndex < splitSequence.size()){
            int currentSequenceIndex = 0;
            while(currentSequenceIndex < splitSequence.get(currentSequenceIndex).length()) {
                int randomNum = (int) (Math.random() * ((maxSequenceRange - minSequenceRange) + 1)) + minSequenceRange;
                if(randomNum >= minSequenceRange) {
                    partitionedSequence.add(splitSequence.get(splitSequenceIndex).substring(splitSequenceIndex, randomNum));
                    currentSequenceIndex += (randomNum);
                }
                currentSequenceIndex++;
            }
            splitSequenceIndex++;
        }

        writeToFile(outputFile, partitionedSequence);
    }

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
