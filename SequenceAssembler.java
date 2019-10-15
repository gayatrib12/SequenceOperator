package Bioinformatics;

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
}
