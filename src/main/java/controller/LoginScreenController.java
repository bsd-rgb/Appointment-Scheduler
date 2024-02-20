package controller;

import dao.UsersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Users;

import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private TextField passwordTxtField;

    @FXML
    private Label locationLbl;

    @FXML
    private TextField usernameTxtField;

    private boolean french = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId zoneId = ZoneId.systemDefault();
        locationLbl.setText(zoneId.toString());

    }

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