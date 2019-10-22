//package Bioinformatics;

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
    double p;
    String fileName;
    CommonUtilities commonUtilities;
    float proportionA;
    float proportionC;
    float proportionG;
    Random randomNoGeneration;

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

    public static void main(String[] args) throws IOException {
        //SequenceGenerator sequenceGenerator = new SequenceGenerator(10000, 25,25, 25, 25, 10, 0.1, "first_output.txt");
        SequenceGenerator sequenceGenerator = new SequenceGenerator(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),
         Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Float.parseFloat(args[6]), args[7]);
        sequenceGenerator.calculateProportionACGT(sequenceGenerator.a, sequenceGenerator.c, sequenceGenerator.g, sequenceGenerator.t);
        sequenceGenerator.executeSequenceGenerator(sequenceGenerator.n, sequenceGenerator.k, sequenceGenerator.fileName);
    }

    private void calculateProportionACGT(int a, int c, int g, int t){
        int total = a+c+g+t;
        this.proportionA = (float)a/total;
        this.proportionC = (float)c/total + this.proportionA;
        this.proportionG = (float)g/total + this.proportionC;
    }

    private void executeSequenceGenerator(int sequenceLength, int noOfSequences, String outputFileName) throws IOException {

        List<String> resultantSequences = new ArrayList<>();
        StringBuilder masterSequence = new StringBuilder();
        randomNoGeneration = new Random();
        commonUtilities = new CommonUtilities();

        //1st iteration
        int masterSequenceCounter = 0;
        while(masterSequenceCounter < sequenceLength){
            float randomIndex = randomNoGeneration.nextFloat();
            masterSequence.append(chooseRandomLetter(randomIndex));
            masterSequenceCounter++;
        }
        commonUtilities.writeToFileOnGenerating(outputFileName, masterSequence.toString(), 1);

        //subsequent iterations
        int sequenceCounter = 1;
        while(sequenceCounter < noOfSequences){

            StringBuilder subsequentSequence = new StringBuilder().append(masterSequence.toString());

            int subsequentSequenceCounter = 0;
            while(subsequentSequenceCounter < masterSequence.length()){
                if(decideOperation(randomNoGeneration.nextFloat(), (float)this.p)) {
                    if(decideOperation(randomNoGeneration.nextFloat(), (float)0.5)){
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
            commonUtilities.writeToFileOnGenerating(outputFileName, subsequentSequence.toString().replaceAll("_", ""), subsequentSequenceCounter);
            resultantSequences.add(subsequentSequence.toString());
        }
    }

    private char chooseRandomLetter(float randomnessIndex){
        if(randomnessIndex >= 0 && randomnessIndex <= this.proportionA)
            return 'A';
        else if(randomnessIndex > this.proportionA && randomnessIndex <= this.proportionC)
            return 'C';
        else if(randomnessIndex > this.proportionC && randomnessIndex <= this.proportionG)
            return 'G';
        else
            return 'T';
    }

    private boolean decideOperation(float randomnessIndex, float pr){
        if(randomnessIndex <= pr)
            return true;
        else
            return false;
    }

    private void performReplace(float replacementIndex, StringBuilder permutedString, int currentIndex){

        if(permutedString.charAt(currentIndex) != chooseRandomLetter(replacementIndex)){
            permutedString.setCharAt(currentIndex, chooseRandomLetter(replacementIndex));
            return;
        }

        performReplace(randomNoGeneration.nextFloat(), permutedString, currentIndex);
    }

    private void performDelete(int currentIndex, StringBuilder permutedString){
        permutedString.replace(currentIndex, currentIndex+1,"_");
    }
}
