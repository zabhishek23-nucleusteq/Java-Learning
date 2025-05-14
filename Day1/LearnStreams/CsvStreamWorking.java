package Day1.LearnStreams;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class CsvStreamWorking {
    public static void main(String[] args) throws IOException {
        Path inputFile = Paths.get("./Day1/LearnStreams/Employee.csv");
        Path outputFilLegacy = Paths.get("./Day1/LearnStreams/FilterEmployeeUsingIOStream.csv");
        Path outputFileModern = Paths.get("./Day1/LearnStreams/FilterEmployeeUsingNIO2.csv");

        filterStreamUsingNIO2(inputFile, outputFileModern);

        filterStreamUsingInputOutputSteam(inputFile.toString(), outputFilLegacy.toString());
    }

    private static void filterStreamUsingInputOutputSteam(String inputFile, String outputFile) throws IOException {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8)
                );
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)
                )
        ) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    bw.write(line);
                    bw.newLine();
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4 &&
                        Integer.parseInt(parts[3]) >= 10000 &&
                        Integer.parseInt(parts[2]) == 10) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            System.out.println("Filtered output written to: " + outputFile);
        }
    }



    private static void filterStreamUsingNIO2(Path inputFile, Path outputFile) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {
            List<String> filtered = reader.lines()
                    .filter(l -> !l.startsWith("EMP_NAME"))
                    .filter(l -> {
                        String[] parts = l.split(",");
                        return parts.length == 4 &&
                                Integer.parseInt(parts[3]) >= 10000 &&
                                Integer.parseInt(parts[2]) == 10;
                    }).collect(Collectors.toList());

            // Add header back to the output if needed
            filtered.add(0, "EMP_NAME,EMP_ID,DEPT_ID,SAL");

            // Write to the file, creating it if it doesn't exist
            Files.write(outputFile, filtered, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("output written to: " + outputFile);
        }
    }
}
