//package Bioinformatics;

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
    	int x;
    	int y;
    	String mergedString;
    	int arrayIndex;
    	
    	public String toString() {
    		System.out.println("score: " + score + " x: " + x + " y: " + y + " mergedString: " + mergedString);
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
        	Result result = calculateAlignment(fragments.get(0), fragments);
        	if(result.score > 0) {
        		fragments.remove(result.arrayIndex);
        		fragments.remove(0);
        		fragments.add(0,result.mergedString);
        	} else {
        		break;
        	}
        }
        System.out.println(fragments.get(0));
        CommonUtilities commonUtilities = new CommonUtilities();
        commonUtilities.writeToFileOnGenerating(outputFile, fragments.get(0), 1);
    }

    public Result calculateAlignment(String input, List<String> inputList) {
		Result res = new Result();
    	res.score = 0;
		
    	for(int i=1; i<inputList.size(); i++) {
    		Result temp = getAlignedResult(input,inputList.get(i));
    		if(temp.score > res.score) {
    			res = temp;
    			res.arrayIndex = i;
    		}
		}
		
		return res;
	}

	public Result getAlignedResult(String a, String b) {
    	Result r1 = getDoveTailMerge(a,b);
    	Result r2 = getDoveTailMerge(b,a);
    	Result r = r1.score >= r2.score ? r1 : r2;
    	return r;
    }
    
	public String getAlignedString(String a, String b) {
    	Result r1 = getDoveTailMerge(a,b);
    	Result r2 = getDoveTailMerge(b,a);
    	String merged = r1.score >= r2.score ? r1.mergedString : r2.mergedString;
    	return merged;
    }
    
    public Result getDoveTailMerge(String a, String b) {
    	int[][] arr = new int[a.length()+1][b.length()+1];
    	int j=1;
    	for(;j<=b.length();j++) {
    		if(a.charAt(0) == b.charAt(j-1)) {
    			arr[1][j] = 9;
    			break;
    		}
    	}
    	return dovetailDP(arr,1,j,a,b);
    
    }
    
    public Result dovetailDP(int[][] arr, int i, int j, String a, String b) {
    	Result res = new Result();
    	for(int y = i; y<arr.length; y++) {
    		for(int x = j; x<arr[0].length; x++) {
    			int temp = 0;
    			if(a.charAt(y-1) == b.charAt(x-1))
    				temp = arr[y-1][x-1] + this.matchScore;
    			else {
    				temp = arr[y-1][x-1] + this.replaceScore;
    			}
    			arr[y][x] = Math.max(temp, Math.max(arr[y-1][x]+this.deleteInsertScore, arr[y][x-1]+this.deleteInsertScore));
    		}
    	}
    	
    	int max = -1;
    	int maxIdx = 0;
    	for(int z=0;z<arr.length;z++) {
    		if(arr[z][arr[0].length-1] > max) {
    			max = arr[z][arr[0].length-1];
    			maxIdx=z;
    		}
    	}
    	
    	res.y = maxIdx;
    	res.x = j;
    	StringBuffer sb = new StringBuffer();
    	sb.append(b);
    	sb.append(a.substring(maxIdx));
    	res.mergedString = sb.toString();
    	res.score = arr[maxIdx][arr[0].length - 1];
    	return res;
    }
}
