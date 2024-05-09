// references:
//  https://commons.apache.org/proper/commons-cli/usage.html
// https://www.baeldung.com/java-file-extension#:~:text=java%E2%80%9C.,returns%20extension%20of%20the%20filename.
// https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/HelpFormatter.html
// https://mkyong.com/java/java-get-the-name-or-path-of-a-running-jar-file/
// https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileVisitor.html

package io.hw1.ssc;

import io.muzoo.ssc.assignment.tracker.SscAssignment;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

class MyFileVisitor extends SimpleFileVisitor<Path> {
    private int totalFiles = 0;
    private int totalDirs = 0;
    private int totalDuplicateFiles = 0;
    private final Set<String> specialExtensions = new HashSet<>();

    public int getTotalFiles() {return totalFiles;}
    public int getTotalDirs() {return totalDirs;}
    public int getTotalDuplicateFiles() {return totalDuplicateFiles;}
    public Set<String> getSpecialExtensions() {return specialExtensions;}

    private String getFileExtension(Path file) {
        String fileName = file.getFileName().toString();
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (attrs.isDirectory()){
            totalDirs++;
        }
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile()){
            totalFiles++;

            String fileExtension = getFileExtension(file);
            if (!specialExtensions.add(fileExtension)){
                totalDuplicateFiles++;
            }
        }
        return FileVisitResult.CONTINUE;
    }
}

public class Main extends SscAssignment {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("c", "count duplicates", false, "print total number of duplicates");
        options.addOption("a", "algo", false, "Specifies the algorithm for finding duplicates");
        options.addOption("p", "print", false, "Print relative pathes of all duplicates grouped together");
        options.addOption("f", "path to folder", true, "Specifies the path to the target folder (required)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options,args);
            String folderPath= commandLine.getOptionValue("f");
            if(folderPath==null){
                System.out.println("Folder path not specified");
                printHelp(options);
                return;
            }
            Path folderPathObj = Paths.get(folderPath);
            if(!Files.exists(folderPathObj) || !Files.isDirectory(folderPathObj)){
                System.out.println("Folder does not exist");
                printHelp(options);
                return;
            }
            MyFileVisitor fileVisitor = new MyFileVisitor();
            Files.walkFileTree(folderPath, fileVisitor);
            if(commandLine.hasOption("c")){
                System.out.println("total number of  duplicates "+ fileVisitor.getTotalDuplicateFiles());
            }
        } catch (ParseException | IOException e) {
            System.out.println("Error: "+ e.getMessage());
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar ssc-y23t3-dir-walker.jar", options);
    }

}