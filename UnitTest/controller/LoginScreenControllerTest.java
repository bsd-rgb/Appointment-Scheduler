package controller;

import dao.DBConnection;
import dao.UsersDao;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginScreenControllerTest {
    @Test
    void onValidLogin() throws SQLException {
        String validUsername = "test";
        String validPassword = "test";
        System.out.println("***** TESTING VALID USER LOGIN *****");
        boolean loginResult = login(validUsername, validPassword);
        System.out.println("Login result should succeed with the login result of true. Login Result: " + loginResult);
        assertTrue(loginResult, "Login should succeed with valid credentials");
    }
    @Test
    void onInvalidLogin() throws SQLException {
        String invalidUsername = "t";
        String invalidPassword = "t";
        System.out.println("***** TESTING INVALID USER LOGIN *****");
        boolean loginResult = login(invalidUsername, invalidPassword);
        System.out.println("Login result should fail with the login result of false. Login Result: " + loginResult);
        assertFalse(loginResult, "Login should fail with invalid credentials");
    }
    private boolean login(String username, String password) throws SQLException {
            DBConnection.openConnection();
            try{
            if (UsersDao.findUser(username, password) != null) {
                System.out.println("User found.");
                return true;
            } else {
                System.out.println("User found.");
                return false;
            }
        } catch(SQLException e){
                e.printStackTrace();
                return false;
            }finally {
                DBConnection.closeConnection();
            }
    }
}
