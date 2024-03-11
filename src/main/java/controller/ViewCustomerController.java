package controller;

import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewCustomerController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            CustomersDao.selectCustomers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        customerTable.setItems(Customers.getAllCustomers());
        System.out.println(Customers.getAllCustomers());

        /*customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));*/

    }

    @FXML
    private TableColumn<Customers, String> customerNameCol;
    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TableColumn<Customers, Integer> customerIdCol;


    @FXML
    private TableColumn<Customers, Integer> divisionCol;

    @FXML
    private TableColumn<Customers, String> phoneCol;

    @FXML
    private TableColumn<Customers, String> postalCodeCol;

    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) {

    }


}

