package com.bd;

import dao.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Countries;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        //For testing purposes
        //Locale.setDefault(new Locale("fr"));
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