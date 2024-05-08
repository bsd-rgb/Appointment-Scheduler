package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    /** The protocol used to connect to the database. */
    private static final String protocol = "jdbc";
    /** The database vendor to be used, MySQL in this instance. */
    private static final String vendor = ":mysql:";
    /** The location of the database. */
    private static final String location = "//localhost/";
    /** The name of the database to connect to. */
    private static final String databaseName = "client_schedule";
    /** The constructed URL for database connection using local time. */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /** The database driver reference. */
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    /** The username used to connect to the database. */
    private static final String userName = "sqlUser";
    /** The password used to connect to the database. */
    private static String password = "Passw0rd!";
    /** The connection interface is used to perform several database functions. */
    public static Connection connection;  // Connection Interface

   /** Establishes a connection to the database. */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Closes connection to the database. */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed.");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
