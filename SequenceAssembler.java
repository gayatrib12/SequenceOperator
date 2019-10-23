package Bioinformatics;

import java.io.IOException;
import java.util.List;

public class SequenceAssembler {

    String inputFile;
    int matchScore;
    int replaceScore;
    int deleteInsertScore;
    String outputFile;
    CommonUtilities commonUtilities;

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
        SequenceAssembler sequenceAssembler = new SequenceAssembler(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), 
         Integer.parseInt(args[3]), args[4]);
        CommonUtilities commonUtilities = new CommonUtilities();
        List<String> fragments = commonUtilities.readFromFileToAssemble(sequenceAssembler.inputFile);
        sequenceAssembler.assemble(fragments);
    }

    public void assemble(List<String> fragments) throws IOException
    {
        while(fragments.size() > 1) {
        	Result result = MergeFirstSequence(fragments.get(0), fragments);
        	if(result.score > 0) {
        		fragments.remove(result.arrayIndex);
                fragments.remove(0);
        		fragments.add(0,result.mergedString);
        	} else {
        		break;
        	}
        }
        CommonUtilities commonUtilities = new CommonUtilities();
        commonUtilities.writeToFileOnGenerating(outputFile, fragments.get(0), 1);
    }

    public Result MergeFirstSequence(String fragment, List<String> fragmentList) {
		Result result = new Result();
    	result.score = 0;
		
    	for(int i=1; i<fragmentList.size(); i++) {
            Result r1 = getDoveTailMergeResult(fragment, fragmentList.get(i));
            Result r2 = getDoveTailMergeResult(fragmentList.get(i), fragment);

            Result temp = r1.score >= r2.score ? r1: r2;
    		if(temp.score > result.score) {
    			result = temp;
    			result.arrayIndex = i;
    		}
		}
		
		return result;
	}

	
    public Result getDoveTailMergeResult(String a, String b) {
    	int[][] arr = new int[a.length() + 1][b.length() + 1];
    	int j = 1;
    	for( ; j <= b.length(); j++) {
    		if(a.charAt(0) == b.charAt(j-1)) {
    			break;
    		}
    	}
    	return doveTailResult(arr, 1, j, a, b);
    }
    
    public Result doveTailResult(int[][] arr, int i, int j, String a, String b) {
    	Result result = new Result();
    	for(int y = i; y < arr.length; y++) {
    		for(int x = j; x < arr[0].length; x++) {
    			int temp = 0;
    			if(a.charAt(y-1) == b.charAt(x-1))
    				temp = arr[y-1][x-1] + this.matchScore;
    			else 
                    temp = arr[y-1][x-1] + this.replaceScore;
                    
    			arr[y][x] = Math.max(temp, Math.max(arr[y-1][x] + this.deleteInsertScore, arr[y][x-1] + this.deleteInsertScore));
    		}
    	}
    	
    	int maxScore = -1;
    	int maxScoreIndex = 0;
    	for(int z=0;z<arr.length;z++) {
    		if(arr[z][arr[0].length-1] > maxScore) {
    			maxScore = arr[z][arr[0].length-1];
    			maxScoreIndex = z;
    		}
    	}

    	StringBuffer sb = new StringBuffer();
    	sb.append(b);
    	sb.append(a.substring(maxScoreIndex));
    	result.mergedString = sb.toString();
    	result.score = arr[maxScoreIndex][arr[0].length - 1];
    	return result;
    }
}
