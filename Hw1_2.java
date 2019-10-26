//package Bioinformatics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Hw1_2 {

    String inputFile;
    int minSequenceRange;
    int maxSequenceRange;
    String outputFile;
    CommonUtilities commonUtilities;

    public Hw1_2(String inputFile, int minSequenceRange, int maxSequenceRange, String outputFile) {
        this.inputFile = inputFile;
        this.minSequenceRange = minSequenceRange;
        this.maxSequenceRange = maxSequenceRange;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) throws IOException {
        Hw1_2 sequencePartitioner = new Hw1_2(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
        sequencePartitioner.partitionSequences(sequencePartitioner.inputFile, sequencePartitioner.outputFile, sequencePartitioner.minSequenceRange, sequencePartitioner.maxSequenceRange);
    }

    private void partitionSequences(String inputFileName, String outputFile, int minSequenceRange, int maxSequenceRange) throws IOException {
        List<String> partitionedSequence = new ArrayList<>();
        commonUtilities = new CommonUtilities();
        List<String> splitSequence = commonUtilities.readFromFileToPartition(inputFileName);

        for (String sequence : splitSequence) {
            sequence = sequence.trim();
            int currentSequenceIndex = 0;
            while(currentSequenceIndex < sequence.length()) {
                int randomNum = (int) (Math.random() * ((maxSequenceRange - minSequenceRange) + 1)) + minSequenceRange;
                //if(randomNum >= minSequenceRange && randomNum <= maxSequenceRange) {
                if (randomNum <= (sequence.length() - currentSequenceIndex)) {
                    partitionedSequence
                            .add(sequence.substring(currentSequenceIndex, (currentSequenceIndex + randomNum)));
                    currentSequenceIndex += (randomNum);
                }
                else
                    currentSequenceIndex++;
                //}
            }
        }

        //write to file here
        commonUtilities.writeToFileOnPartitioning(outputFile, partitionedSequence);
        return;
    }
}
