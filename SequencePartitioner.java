package Bioinformatics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SequencePartitioner {

    String inputFile;
    int minSequenceRange;
    int maxSequenceRange;
    String outputFile;
    CommonUtilities commonUtilities;

    public SequencePartitioner(String inputFile, int minSequenceRange, int maxSequenceRange, String outputFile) {
        this.inputFile = inputFile;
        this.minSequenceRange = minSequenceRange;
        this.maxSequenceRange = maxSequenceRange;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) throws IOException {
        SequencePartitioner sequencePartitioner = new SequencePartitioner("first_output.txt", 100, 150, "second_output.txt");
        sequencePartitioner.partitionSequences(sequencePartitioner.inputFile, sequencePartitioner.outputFile, sequencePartitioner.minSequenceRange, sequencePartitioner.maxSequenceRange);
    }

    private void partitionSequences(String inputFileName, String outputFile, int minSequenceRange, int maxSequenceRange) throws IOException {
        List<String> partitionedSequence = new ArrayList<>();
        commonUtilities = new CommonUtilities();
        List<String> splitSequence = commonUtilities.readFromFileToPartition(inputFileName);

        for(String sequence : splitSequence){
            int currentSequenceIndex = 0;
            while(currentSequenceIndex < sequence.length()) {
                int randomNum = (int) (Math.random() * ((maxSequenceRange - minSequenceRange) + 1)) + minSequenceRange;
                if(randomNum >= minSequenceRange && randomNum <= maxSequenceRange) {
                    if (randomNum <= (sequence.length() - currentSequenceIndex) + 1) {
                        partitionedSequence.add(sequence.substring(currentSequenceIndex, (currentSequenceIndex + randomNum) - 1));
                        currentSequenceIndex += (randomNum);
                    }
                }
                currentSequenceIndex++;
            }
        }

        //write to file here
        commonUtilities.writeToFileOnPartitioning(outputFile, partitionedSequence);
        return;
    }
}
