package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.CustomerBoImpl;
import org.example.edu.model.Customer;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageCustomerController implements Initializable {
    public AnchorPane customeranchor;
    public JFXTextField cusid;
    public JFXTextField cusemail;
    public JFXTextField cusaddress;
    public JFXTextField cusname;
    public TableView customertable;
    public TableColumn idcol;
    public TableColumn emailcol;
    public TableColumn namecol;
    public TableColumn addresscol;
    public Button btnupdate;


    CustomerBoImpl customerBoImpl = new CustomerBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusid.setText(customerBoImpl.generateEmployeeId());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        customertable.setItems(customerBoImpl.getAllCustomer());

    }

    public void searchaction(ActionEvent actionEvent) {

        Customer customer = customerBoImpl.getCusById(cusid.getText());
        cusname.setText(customer.getName());
        cusemail.setText(customer.getEmail());

        cusaddress.setText(customer.getAddress());

    }

    public void addaction(ActionEvent actionEvent) {
        Customer customer = new Customer(
                cusid.getText(),
                cusname.getText(),
                cusemail.getText(),
                cusaddress.getText()

        );

        if (!cusname.getText().equals("") && customerBoImpl.isValidEmail(cusemail.getText()) && !cusaddress.getText().equals("")) {


            boolean isInsert = customerBoImpl.insertUser(customer);
            if (isInsert) {
                customertable.setItems(customerBoImpl.getAllCustomer());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Added");
                alert.setContentText("Customer Added Successfully..!");
                alert.showAndWait();
                cusaddress.setText("");
                cusname.setText("");
                cusemail.setText("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    public void deleteaction(ActionEvent actionEvent) {

        if (!cusid.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Customer");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                boolean isDeleted = customerBoImpl.deleteCusById(cusid.getText());
                if (isDeleted) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Customer Deleted");
                    alert2.setContentText("Customer deleted successfully");
                    alert2.showAndWait();
                    customertable.setItems(customerBoImpl.getAllCustomer());
                    cusemail.setText("");
                    cusaddress.setText("");
                    cusname.setText("");
                    clear();
                    cusid.setText(customerBoImpl.generateEmployeeId());
                }
            } else {
                /*Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something Missing");
                alert.setContentText("Please Check your Form again..!!!");
                alert.showAndWait();*/
            }
        }







    }
    public void clear () {

        cusid.setText("");
        cusname.setText("");
        cusemail.setText("");
        cusaddress.setText("");
    }
    public void dashboardaction(ActionEvent actionEvent) {
    }

    public void manageemployeeaction(ActionEvent actionEvent) {
    }

    public void itemdetailsaction(ActionEvent actionEvent) {
    }

    public void supplierdetailsaction(ActionEvent actionEvent) {
    }

    public void orderdetailsaction(ActionEvent actionEvent) {
    }

    public void updateaction(ActionEvent actionEvent) {

        if (!cusemail.getText().equals("") && !cusaddress.getText().equals("") && !cusname.getText().equals("")) {
            Customer customer = new Customer(
                    cusid.getText(),
                    cusname.getText(),
                    cusemail.getText(),
                    cusaddress.getText());

            boolean isUpdated = customerBoImpl.updateCus(customer);
            if (isUpdated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Update");
                alert.setContentText("Customer Updated Successfully");
                alert.showAndWait();
                cusemail.setText("");
                cusaddress.setText("");
                cusname.setText("");
                customertable.setItems(customerBoImpl.getAllCustomer());
                clear();
                cusid.setText(customerBoImpl.generateEmployeeId());

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }


    }

    public void dataBar(MouseEvent mouseEvent) {
    }
}
