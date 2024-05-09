package com.bd;

import dao.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/** The main application.
 *
 * @author Brandi Davis
 * */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /** Loads initial data from the database and launches the program.
     *
     * Opens the connection to the database
     * Initializes several variables by selecting all countries, contacts, customers, users, and appointments from the database
     * Closes the connection once the application is closed
     * @throws SQLException in the event of an error selecting all countries, contacts, customers, users, and appointments from the database
     * */
    public static void main(String[] args) throws SQLException {

        //Locale.setDefault(new Locale("fr"));
        //System.out.println("Java FX Version: " + System.getProperty("javafx.runtime.version"));
        DBConnection.openConnection();
        CountriesDao.selectAllCountries();
        ContactsDao.selectContacts();
        CustomersDao.selectCustomers();
        UsersDao.selectUsers();
        AppointmentsDao.SelectAppointments();

        launch();

        DBConnection.closeConnection();
    }
}