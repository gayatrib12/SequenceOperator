package Bioinformatics;

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
    double p;
    String fileName;

    public SequenceGenerator(int n, int a, int c, int g, int t, int k, double p, String fileName) {
        this.n = n;
        this.a = a;
        this.c = c;
        this.g = g;
        this.t = t;
        this.k = k;
        this.p = p;
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        SequenceGenerator sequenceGenerator = new SequenceGenerator(10000, 25,25, 25, 25, 10, 0.1, "output_file");
        sequenceGenerator.executeSequenceGenerator(sequenceGenerator.n, sequenceGenerator.k);
    }

    private void executeSequenceGenerator(int sequenceLength, int noOfSequences){

        List<StringBuilder> resultantSequences = new ArrayList<>();
        StringBuilder masterSequence = new StringBuilder();
        Random randomNoGeneration = new Random();

        //1st iteration
        int masterSequenceCounter = 0;
        while(masterSequenceCounter < sequenceLength){
            float randomIndex = randomNoGeneration.nextFloat();
            masterSequence.append(chooseRandomLetter(randomIndex));
            masterSequenceCounter++;
        }
        System.out.println("masterSequence: "+masterSequence);

        //2nd iteration
        int sequenceCounter = 1;
        while(sequenceCounter < noOfSequences){
            StringBuilder subsequentSequence = masterSequence;

            int subsequentSequenceCounter = 0;
            while(subsequentSequenceCounter < subsequentSequence.length()){
                if(decideOperation(randomNoGeneration.nextFloat())) {
                    if(decideOperation(randomNoGeneration.nextFloat())){
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
            System.out.println("subsequentSequence: "+subsequentSequence);
            resultantSequences.add(subsequentSequence);
        }
        writeToOutputFile(resultantSequences);
    }

    private char chooseRandomLetter(float randomnessIndex){
        if(randomnessIndex >= 0 && randomnessIndex <= 0.25)
            return 'A';
        else if(randomnessIndex > 0.25 && randomnessIndex <= 0.5)
            return 'C';
        else if(randomnessIndex > 0.5 && randomnessIndex <= 0.75)
            return 'G';
        else
            return 'T';
    }

    private boolean decideOperation(float randomnessIndex){
        if(randomnessIndex <= 0.5)
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

    private void writeToOutputFile(List<StringBuilder> resultantSequences){
        //logic to write to file
    }
}
