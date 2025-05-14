package Day2.ExceptionHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileProcessor {
    public static void main(String[] args){
        Path inputPath = Paths.get("Day2/ExceptionHandling/input.txt");
        Path outputPath = Paths.get("Day2/ExceptionHandling/output.txt");
        try{
            processFile(inputPath,outputPath);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Input File not found : "+e.getMessage());
        }
        catch (FileParsingException e)
        {
            System.err.println("Parsing error : "+e.getMessage());
        }
        catch (IOException e)
        {
            System.err.println("I/O error"+e.getMessage());
        }
        finally {
            System.out.println("File processing completed");
        }
    }

    private static void processFile(Path inputPath, Path outputPath) throws IOException, FileParsingException {
        if(!Files.exists(inputPath)){
            throw new FileNotFoundException(inputPath.toString());
        }
        try(
            BufferedReader br = Files.newBufferedReader(inputPath);
            BufferedWriter bw = Files.newBufferedWriter(outputPath);
        ){
            String line = "";
            while ((line = br.readLine()) != null )
            {
                if(!line.matches("^[a-zA-Z0-9\\s]+$")){
                    throw new FileParsingException("Invalid Content : "+line);
                }
                bw.write(line.toUpperCase());
                bw.newLine();
            }
        }
    }
}
