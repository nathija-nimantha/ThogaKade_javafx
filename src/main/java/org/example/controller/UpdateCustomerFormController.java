package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.example.db.DBConnection;
import org.example.model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustomerFormController {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnUpdate;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtID.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        boolean found = false;
        for(Customer customer : customerList) {
            if(customer.getId().equals(id)) {
                txtAddress.setText(customer.getAddress());
                txtContact.setText(customer.getContact());
                txtDOB.setValue(LocalDate.parse(customer.getDateOfBirth().toString()));
                txtName.setText(customer.getName());
                txtTitle.setText(customer.getTitle());
                found = true;
                break;
            }
        }

        if(!found) {

            showErrorMessage(null, "Customer not found");
        }
    }
    public void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtID.getText();
        String title = txtTitle.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        LocalDate dob = txtDOB.getValue();

        if (id.trim().isEmpty() || title == null || name.trim().isEmpty() || address.trim().isEmpty() || contact.trim().isEmpty() || dob == null) {
            showAlert(Alert.AlertType.ERROR, "Empty fields", "All fields must be filled in.");
        } else if (!contact.matches("^0\\d{9}$")) {
            showAlert(Alert.AlertType.ERROR, "Invalid contact", "Invalid contact number.");
        } else {
            List<Customer> customerList = DBConnection.getInstance().getConnection();

            // Find the customer by ID
            Optional<Customer> optionalCustomer = customerList.stream()
                    .filter(c -> c.getId().equals(id))
                    .findFirst();

            if (optionalCustomer.isPresent()) {
                // Update the existing customer
                Customer customer = optionalCustomer.get();
                customer.setTitle(title);
                customer.setName(name);
                customer.setAddress(address);
                customer.setContact(contact);
                customer.setDateOfBirth(dob);

                System.out.println("Updated Customer: " + customer);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer has been updated successfully");
            } else {
                showAlert(Alert.AlertType.ERROR, "Customer not found", "No customer found with ID: " + id);
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && alertType == Alert.AlertType.INFORMATION) {
                Stage stage = (Stage) btnUpdate.getScene().getWindow();
                stage.close();
            }
        });
    }
}
