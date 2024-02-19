package controller;

import dao.UsersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Users;

public class LoginScreenController {

    @FXML
    private TextField passwordTxtField;

    @FXML
    private Label locationLbl;

    @FXML
    private TextField usernameTxtField;

    @FXML
    void onActionLogin(ActionEvent event) {

        //when the button is selected, we want to check the username and password

        try {

            String userName = usernameTxtField.getText();
            String password = passwordTxtField.getText();

            if(UsersDao.selectUser(userName,password) != null ) {
                System.out.println("User Found!");
            } else {
                System.out.println("User not found");
            }



        }catch (Exception e) {

            System.out.println(e.getMessage());

        }


    }

}