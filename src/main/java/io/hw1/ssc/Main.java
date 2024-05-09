// references:
//  https://commons.apache.org/proper/commons-cli/usage.html
// https://www.baeldung.com/java-file-extension#:~:text=java%E2%80%9C.,returns%20extension%20of%20the%20filename.
// https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/HelpFormatter.html
// https://mkyong.com/java/java-get-the-name-or-path-of-a-running-jar-file/
// https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileVisitor.html

package io.hw1.ssc;

import io.muzoo.ssc.assignment.tracker.SscAssignment;
import org.apache.commons.cli.Options;


public class Main extends SscAssignment {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("a", "count duplicates", false, "print total number of duplicates");
        options.addOption("a", "algo", false, "Specifies the algorithm for finding duplicates");
        options.addOption("p", "print", false, "Print relative pathes of all duplicates grouped together");
        options.addOption("f", "path to folder", false, "Specifies the path to the target folder (required)");


    }

}