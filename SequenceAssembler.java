package Bioinformatics;

import java.io.IOException;

public class SequenceAssembler {

    String inputFile;
    int minSequenceRange;
    int maxSequenceRange;
    String outputFile;
    CommonUtilities commonUtilities;

    public SequenceAssembler(String inputFile, int minSequenceRange, int maxSequenceRange, String outputFile) {
        this.inputFile = inputFile;
        this.minSequenceRange = minSequenceRange;
        this.maxSequenceRange = maxSequenceRange;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) throws IOException {
        SequenceAssembler sequenceAssembler = new SequenceAssembler("second_output.txt", 100, 150, "third_output.txt");
        CommonUtilities commonUtilities = new CommonUtilities();
        commonUtilities.readFromFileToAssemble(sequenceAssembler.inputFile);
    }
}
