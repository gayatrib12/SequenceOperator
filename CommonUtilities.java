package Bioinformatics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CommonUtilities {

    int partitionDiem = 78;
    String fileLengthSpecifier = "%0$-78s";
    String fastaFormatSpecifier = ">pns_gb";

    public List<String> readFromFileToPartition(String inputFileName) throws IOException {

        File file = new File(inputFileName);
        List<String> fileLinesList = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        List<String> fileStringsToPartition = new ArrayList<>();
        StringBuilder partitionText = new StringBuilder();

        for(String line : fileLinesList) {
            if(line.trim().contains(fastaFormatSpecifier)) {
                fileStringsToPartition.add(partitionText.toString());
                partitionText.setLength(0);
                continue;
            }
            partitionText.append(line);
        }
        fileStringsToPartition.remove(0);
        return fileStringsToPartition;
    }

    public List<String> readFromFileToAssemble(String inputFileName) throws IOException {

        File file = new File(inputFileName);
        List<String> fileLinesList = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        List<String> fileStringsToPartition = new ArrayList<>();
        StringBuilder partitionText = new StringBuilder();

        for(String line : fileLinesList) {
            if(line.trim().contains(fastaFormatSpecifier))
                continue;

            if(line.trim().isEmpty())
            {
                fileStringsToPartition.add(partitionText.toString());
                partitionText.setLength(0);
            }
            else
            {
                partitionText.append(line);
            }
        }


        List<String> result = new ArrayList<>();
        for(String line: fileStringsToPartition)
        {
            line = line.trim();
            if(!line.isEmpty())
                result.add(line);
        }
        return result;
    }

    public void writeToFileOnGenerating(String outputFileName, List<String> outputToFile, int lineCount) throws IOException {

        try (FileWriter fileWriter = new FileWriter(outputFileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for(String line:outputToFile) {
                printWriter.println(String.format(fileLengthSpecifier, fastaFormatSpecifier));

                for (int i = 0; i < line.length(); ) {
                    if ((line.length() - i) < partitionDiem) {
                        printWriter.println(String.format(fileLengthSpecifier, line.substring(i, line.length())));
                    } else {
                        //bufferedWriter.write(String.format(fileLengthSpecifier, outputToFile.substring(i, i + partitionDiem)));
                        printWriter.println(String.format(fileLengthSpecifier, line.substring(i, i + partitionDiem)));
                    }
                    i += partitionDiem;
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println(String.format("Problem writing to the file %s", outputFileName));
        }
        return;
    }

    public void writeToFileOnPartitioning(String outputFileName, List<String> outputToFile) throws IOException {
        try (FileWriter fileWriter = new FileWriter(outputFileName, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            for(String line:outputToFile) {
                printWriter.println(String.format(fileLengthSpecifier, fastaFormatSpecifier));

                for (int i = 0; i < line.length(); ) {
                    if ((line.length() - i) < partitionDiem) {
                        printWriter.println(String.format(fileLengthSpecifier, line.substring(i, line.length())));
                        // printWriter.println(String.format(fileLengthSpecifier, fastaFormatSpecifier));
                    } else {
                        printWriter.println(String.format(fileLengthSpecifier, line.substring(i, i + partitionDiem)));
                    }
                    i += partitionDiem;
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println(String.format("Problem writing to the file %s", outputFileName));
        }
        return;
    }

    public void writeToFileOnAssembling(String outputFileName, String outputToFile, int lineCount) throws IOException {
    }
}
