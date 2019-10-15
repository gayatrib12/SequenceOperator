package Bioinformatics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SequenceAssembler {

    String inputFile;
    int minSequenceRange;
    int maxSequenceRange;
    String outputFile;

    public SequenceAssembler(String inputFile, int minSequenceRange, int maxSequenceRange, String outputFile) {
        this.inputFile = inputFile;
        this.minSequenceRange = minSequenceRange;
        this.maxSequenceRange = maxSequenceRange;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) {
        //SequenceAssembler sequenceAssembler = new SequenceAssembler();
    }

    private void partitionSequences(String inputFileName, String outputFile, int minSequenceRange, int maxSequenceRange) throws FileNotFoundException {
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

    private List<String> writeToFile(String outputFileName, List<String> outputToFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(outputFileName));
        scanner.useDelimiter(" ");

        List<String> sequenceCollector = new ArrayList<>();
        while(scanner.hasNext()){
            sequenceCollector.add(scanner.next());
        }
        scanner.close();
        return sequenceCollector;
    }
}
