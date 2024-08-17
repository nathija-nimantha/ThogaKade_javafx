package org.example.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.db.DBConnection;
import org.example.model.Customer;

import java.util.List;

public class DeleteCustomerFormController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblContact;

    @FXML
    private Label lblDOB;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTitle;

    @FXML
    private JFXTextField txtID;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtID.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        boolean found = false;
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                customerList.remove(customer);
                found = true;
                break;
            }
        }

        if (!found) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Customer not found");
            alert.showAndWait();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtID.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        boolean found = false;
        for(Customer customer : customerList) {
            if(customer.getId().equals(id)) {
                lblAddress.setText(customer.getAddress());
                lblContact.setText(customer.getContact());
                lblDOB.setText(customer.getDateOfBirth().toString());
                lblName.setText(customer.getName());
                lblTitle.setText(customer.getTitle());
                found = true;
                break;
            }
        }

        if(!found) {

            showErrorMessage(null, "Customer not found");

            lblAddress.setText("");
            lblContact.setText("");
            lblDOB.setText("");
            lblName.setText("");
            lblTitle.setText("");
        }

    }
    public void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
