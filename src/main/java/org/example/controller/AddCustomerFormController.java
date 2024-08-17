package org.example.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.db.DBConnection;
import org.example.model.Customer;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr.");
        titles.add("Mrs.");
        titles.add("Miss");
        cmbTitle.setItems(titles);
    }

    @FXML
    private Button btnSubmit;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private DatePicker txtDOB;

    @FXML
    private JFXTextField txtName;

    @FXML
    void btnSubmitOnAction(ActionEvent event) {
        String id = txtId.getText();
        String title = (String) cmbTitle.getValue();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        LocalDate dob = txtDOB.getValue();

        if (id.trim().isEmpty() || title == null || name.trim().isEmpty() || address.trim().isEmpty() || contact.trim().isEmpty() || dob == null) {
            showAlert(Alert.AlertType.ERROR, "Empty fields", "All the fields should be entered.");
        } else if (DBConnection.getInstance().getConnection().stream().anyMatch(c -> c.getId().equals(id))) {
            showAlert(Alert.AlertType.ERROR, "Customer already exists", "Customer already exists with this ID.");
        } else if (!contact.matches("^0\\d{9}$")) {
            showAlert(Alert.AlertType.ERROR, "Invalid contact", "Invalid contact number.");
        } else {
            Customer customer = new Customer(id, title, name, address, contact, dob);
            System.out.println(customer);

            List<Customer> customerList = DBConnection.getInstance().getConnection();
            customerList.add(customer);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer has been added successfully");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK && alertType == Alert.AlertType.INFORMATION) {
                Stage stage = (Stage) btnSubmit.getScene().getWindow();
                stage.close();
            }
        });
    }

}
