package helper;

import java.io.*;
import java.util.Scanner;

public class IOUtil  {

    private String fileName = "loginActivity.txt";

    File file = new File(fileName);
    Scanner inputFile = new Scanner(file);

    FileWriter fwriter = new FileWriter(fileName, true);
    PrintWriter output = new PrintWriter(fwriter);

    /*- You can then use print() or println() methods to append data to the file
		- Use close() method to close the file*/

    //what should go in this method below?
    public static void appendLogin(){

    }


    public IOUtil() throws IOException {
        System.out.printf("File not found.");
    }
}
