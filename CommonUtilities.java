package Bioinformatics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

// Making the helper class static
public static class CommonUtilities {

    static int partitionDiem = 78;
    static String fileLengthSpecifier = "%0$-78s";

    public static List<String> readFromFileToPartition(String inputFileName) throws IOException {

        File file = new File(inputFileName);
        List<String> fileLinesList = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        List<String> fileStringsToPartition = new ArrayList<>();
        StringBuilder partitionText = new StringBuilder();

        for(String line : fileLinesList) {
            if(line.contains(">gpbs"))
                continue;

            if (line.trim().isEmpty()) {
                fileStringsToPartition.add(partitionText.toString());
                partitionText.setLength(0);
            }
            else
                partitionText.append(line);
        }
        return fileStringsToPartition;
    }

    public static List<String> readFromFileToAssemble(String inputFileName) throws FileNotFoundException{

        return null;
    }

    public static void writeToFileOnGenerating(String outputFileName, String outputToFile, int lineCount) throws IOException {

        try (FileWriter fileWriter = new FileWriter(outputFileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            if (lineCount == 1) {
                printWriter.println(String.format(fileLengthSpecifier, ">gpbs sequence operations being performed"));
            }

            for (int i = 0; i < outputToFile.length(); ) {
                if ((outputToFile.length() - i) < partitionDiem) {
                    printWriter.println(String.format(fileLengthSpecifier, outputToFile.substring(i, outputToFile.length())));
                } else {
                    //bufferedWriter.write(String.format(fileLengthSpecifier, outputToFile.substring(i, i + partitionDiem)));
                    printWriter.println(String.format(fileLengthSpecifier, outputToFile.substring(i, i + partitionDiem)));
                }
                i += partitionDiem;
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.err.println(String.format("Problem writing to the file %s", outputFileName));
        }
        return;
    }

    public static void writeToFileOnPartitioning(String outputFileName, List<String> outputToFile) throws IOException {
        try (FileWriter fileWriter = new FileWriter(outputFileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.println(String.format(fileLengthSpecifier, ">gpbs sequence operations being performed"));

            for(String line:outputToFile) {
                for (int i = 0; i < line.length(); ) {
                    if ((line.length() - i) < partitionDiem) {
                        printWriter.println(String.format(fileLengthSpecifier, line.substring(i, line.length())));
                    } else {
                        printWriter.println(String.format(fileLengthSpecifier, line.substring(i, i + partitionDiem)));
                    }
                    i += partitionDiem;
                }
            }
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.err.println(String.format("Problem writing to the file %s", outputFileName));
        }
        return;
    }

    public static void writeToFileOnAssembling(String outputFileName, String outputToFile, int lineCount) throws IOException {
    }
}
