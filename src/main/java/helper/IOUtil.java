package helper;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class IOUtil  {

    private String fileName = "login_activity.txt";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    File file = new File(fileName);
    Scanner inputFile = new Scanner(file);

    static FileWriter fwriter;

    static {
        try {
            fwriter = new FileWriter("login_activity.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static PrintWriter outputFile = new PrintWriter(fwriter);

    /*- You can then use print() or println() methods to append data to the file
		- Use close() method to close the file*/

    //what should go in this method below?
    public static void appendLogin(String loggedInUser, boolean loginResult){

        outputFile.println("[" + TimeUtil.getLocalZonedDateTime().format(formatter) + "]" + " - [User] " + loggedInUser + " - [Login Successful] " + loginResult);
        System.out.println("[" + TimeUtil.getLocalZonedDateTime().format(formatter) + "]" + " - [User] " + loggedInUser + " - [Login Successful] " + loginResult);
        outputFile.close();

    }


    public IOUtil() throws IOException {
        System.out.printf("File not found.");
    }
}
