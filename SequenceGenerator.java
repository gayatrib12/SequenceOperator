package Bioinformatics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceGenerator {

    int n;
    int a;
    int c;
    int g;
    int t;
    int k;
    int total;
    double p;
    String fileName;

    float propA;
    float propC;
    float propG;
    float propT;
    // CommonUtilities commonUtilities;

    public SequenceGenerator(int n, int a, int c, int g, int t, int k, double p, String fileName) {
        this.n = n;
        this.a = a;
        this.c = c;
        this.g = g;
        this.t = t;
        this.k = k;
        this.p = p;
        this.fileName = fileName;
        this.total = a + g + c + t;
        calculateProportionACGT();

    }

    public static void main(String[] args) throws IOException {
        SequenceGenerator sequenceGenerator = new SequenceGenerator(10000, 25,25, 25, 25, 10, 0.1, "first_output.txt");
        sequenceGenerator.executeSequenceGenerator(sequenceGenerator.n, sequenceGenerator.k, sequenceGenerator.fileName);
    }

    private void calculateProportionACGT(){
        this.propA = (float) this.a/this.total;
        this.propC = propA + (float) this.c/this.total;
        this.propG = propC + (float) this.a/this.total;
        this.propT = propG + (float) this.a/this.total;
    }

    // As the arguments for this method are already present as the class variables, should we remove them?
    private void executeSequenceGenerator(int sequenceLength, int noOfSequences, String outputFileName) throws IOException {

        List<String> resultantSequences = new ArrayList<>();
        StringBuilder masterSequence = new StringBuilder();
        Random randomNoGeneration = new Random();
        // commonUtilities = new CommonUtilities();

        //1st iteration
        int masterSequenceCounter = 0;
        while(masterSequenceCounter < sequenceLength){
            float randomIndex = randomNoGeneration.nextFloat();
            masterSequence.append(chooseRandomLetter(randomIndex));
            masterSequenceCounter++;
        }
        CommonUtilities.writeToFileOnGenerating(outputFileName, masterSequence.toString(), 1);

        //2nd iteration
        int sequenceCounter = 1;
        while(sequenceCounter < noOfSequences){
            StringBuilder subsequentSequence = masterSequence;

            int subsequentSequenceCounter = 0;
            while(subsequentSequenceCounter < subsequentSequence.length()){

                // The document says
                // The next k−1 sequences will be a copy of the first sequence, but each letter will be 
                // mutated with replace or delete with probability p.

                // Which means, to be either delete or replace - it is probability p and between delete and replace, he did not mention the 
                // probability, so I guess it should be 0.5
                if(decideOperation(randomNoGeneration.nextFloat(), this.p)) {
                    if(decideOperation(randomNoGeneration.nextFloat(), 0.5)){
                        //if replace
                        performReplace(randomNoGeneration.nextFloat(), subsequentSequence, subsequentSequenceCounter);
                    }else{
                        //if delete
                        performDelete(subsequentSequenceCounter, subsequentSequence);
                    }
                }
                subsequentSequenceCounter++;
            }
            sequenceCounter++;

            //write rest of sequences to file here
            CommonUtilities.writeToFileOnGenerating(outputFileName, subsequentSequence.toString(), subsequentSequenceCounter);
            resultantSequences.add(subsequentSequence.toString());
        }
    }

    private char chooseRandomLetter(float randomnessIndex){
        // I guess you missed to add these. So, I have changed.
        if(randomnessIndex >= 0 && randomnessIndex <= this.propA)
            return 'A';
        else if(randomnessIndex > this.propA && randomnessIndex <= this.propC)
            return 'C';
        else if(randomnessIndex > this.propC && randomnessIndex <= this.propG)
            return 'G';
        else
            return 'T';
    }

    private boolean decideOperation(float randomnessIndex, double p){
        if(randomnessIndex <= p)
            return true;
        else
            return false;
    }

    private void performReplace(float replacementIndex, StringBuilder permutedString, int currentIndex){
        permutedString.setCharAt(currentIndex, chooseRandomLetter(replacementIndex));
    }

    private void performDelete(int currentIndex, StringBuilder permutedString){
        permutedString.deleteCharAt(currentIndex);
    }
}
