package Bioinformatics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SequenceAssembler {

    String inputFile;
    int matchScore;
    int replaceScore;
    int deleteInsertScore;
    String outputFile;
    CommonUtilities commonUtilities;

	//Result class to get the score for an alignment of the fragments, the aligned string and the Index of the fragment in the list
     public class Result {
    	int score;
    	String mergedString;
    	int arrayIndex;
    	
    	public String toString() {
    		System.out.println("score: " + score + "\n" + "\n" +" mergedString: " + mergedString + "\n" + "arrayIndex: " + arrayIndex);
    		return "";
    	}
    }
    
    public SequenceAssembler(String inputFile, int matchScore, int repalceScore, int deleteInsertScore, String outputFile) {
        this.inputFile = inputFile;
        this.matchScore = matchScore;
        this.replaceScore = repalceScore;
        this.deleteInsertScore = deleteInsertScore;
        this.outputFile = outputFile;
    }

    public static void main(String[] args) throws IOException {
        SequenceAssembler sequenceAssembler = new SequenceAssembler("second_output.txt", 1, -1,
         -1, "third_output.txt");
        CommonUtilities commonUtilities = new CommonUtilities();
        List<String> fragments = commonUtilities.readFromFileToAssemble(sequenceAssembler.inputFile);
        sequenceAssembler.assemble(fragments);
    }

    public void assemble(List<String> fragments) throws IOException
    {
		// While there are more than 1 fragments in the list, find the fragment pair with the maximum score and merge the fragments.
        while(fragments.size() > 1) {
			// Get the fragment with the maximum alignment score
			Result result = MergeFirstSequence(fragments.get(0), fragments);
			// Merge the fragment and remove the later from the list
        	if(result.score > 0) {
        		fragments.remove(result.arrayIndex);
                fragments.remove(0);
        		fragments.add(0,result.mergedString);
			}
			// If no pair has a positive score, then break 
			else {
        		break;
        	}
        }
        CommonUtilities commonUtilities = new CommonUtilities();
        List<String> results = new ArrayList<>();
        results.add(fragments.get(0));
        commonUtilities.writeToFileOnGenerating(outputFile, results, 1);
    }

	/**
	 * Given a fragment and a list of fragments, find the fragment with the maximum alignment score
	 * @param fragment
	 * @param fragmentList
	 * @return
	 */
    public Result MergeFirstSequence(String fragment, List<String> fragmentList) {
		Result result = new Result();
    	result.score = 0;
		
		// For each fragment in the list starting from 1
    	for(int i=1; i<fragmentList.size(); i++) {
			// Get the alignment of suffix of fragment with the prefix of the ith fragment
			Result r1 = getDoveTailMergeResult(fragment, fragmentList.get(i));
			
			// The alignment score for the vice versa alignment
            Result r2 = getDoveTailMergeResult(fragmentList.get(i), fragment);

			// Get the alignment with the maximum score
            Result temp = r1.score >= r2.score ? r1: r2;
    		if(temp.score > result.score) {
    			result = temp;
    			result.arrayIndex = i;
    		}
		}
		
		return result;
	}

	
	/**
	 * Given two string a and b, get the alignment score
	 * @param a
	 * @param b
	 * @return
	 */
    public Result getDoveTailMergeResult(String a, String b) {
		// Initial matrix of the DP problem
		int[][] arr = new int[a.length() + 1][b.length() + 1];
		Result result = new Result();
		int j = 1;

		// Find the position that first matches between the strings
    	for( ; j <= b.length(); j++) {
    		if(a.charAt(0) == b.charAt(j-1)) {
    			break;
    		}
		}
		
		// Construct the DP matrix starting from the match
    	for(int y = 1; y < arr.length; y++) {
    		for(int x = j; x < arr[0].length; x++) {
    			int temp = 0;
    			if(a.charAt(y-1) == b.charAt(x-1))
    				temp = arr[y-1][x-1] + this.matchScore;
    			else 
                    temp = arr[y-1][x-1] + this.replaceScore;
                
    			arr[y][x] = Math.max(temp, Math.max(arr[y-1][x] + this.deleteInsertScore, arr[y][x-1] + this.deleteInsertScore));
    		}
    	}
		
		// Given the DP matrix, find the row with the maximim value in the last column
    	int maxScore = -1;
    	int maxScoreIndex = 0;
    	for(int row=0; row < arr.length; row++) {
    		if(arr[row][arr[0].length-1] > maxScore) {
    			maxScore = arr[row][arr[0].length-1];
    			maxScoreIndex = row;
			}
    	}

    	StringBuffer mergedStringBuffer = new StringBuffer();
    	mergedStringBuffer.append(b);
    	mergedStringBuffer.append(a.substring(maxScoreIndex));
    	result.mergedString = mergedStringBuffer.toString();
    	result.score = arr[maxScoreIndex][arr[0].length - 1];
		return result;		
    }
}
