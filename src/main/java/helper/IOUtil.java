package helper;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/** The IOUtil class provides a method that writes the login activity to the login_activity.txt file.
 *
 * @author Brandi Davis
 * */
public class IOUtil {

    /** Appends username and login result to the login_activity.txt file.
     *
     * @param loggedInUser string result of the current user attempting to log in
     * @param loginResult boolean result of login attempt
     * @throws IOException in the even the file is not found an exception will be thrown
     * */
    public static void appendLogin(String loggedInUser, boolean loginResult) throws IOException {

        String fileName = "login_activity.txt";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        FileWriter fwriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fwriter);

        outputFile.println("[" + TimeUtil.getLocalZonedDateTime().format(formatter) + "]" + " - [User] " + loggedInUser + " - [Login Successful] " + loginResult);
        outputFile.close();
    }
}
