package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.model.Employee;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class ManageEmployeeController implements Initializable {

    public JFXTextField employeeid;
    public JFXTextField employeeemail;
    public JFXTextField employeeaddress;
    public JFXTextField employeename;
    @FXML
    private TableColumn<?, ?> addresscol;

    @FXML
    private TableColumn<?, ?> emailcol;
    @FXML
    private TableView<?> employeetable;

    @FXML
    private TableColumn<?, ?> idcol;

    @FXML
    private TableColumn<?, ?> namecol;


    public Button Addbuttton;

    EmployeeBoImpl employeeBoImpl = new EmployeeBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        employeeid.setText(employeeBoImpl.generateEmployeeId());
        employeetable.setItems(employeeBoImpl.getAllUsers());
    }


    public void AddAction(ActionEvent actionEvent) {


        Random random = new Random();
        int p = random.nextInt(99999999) + 10000000;

        String encrypt = Integer.toString(p);
        String password = employeeBoImpl.passwordEncrypt(encrypt);

        Employee user = new Employee(
                employeeid.getText(),
                employeename.getText(),
                employeeemail.getText(),
                password,
                "Employee",
                employeeaddress.getText()
        );
        if (!employeename.getText().equals("") && employeeBoImpl.isValidEmail(employeeemail.getText()) && !employeeaddress.getText().equals("")) {


            boolean isInsert = employeeBoImpl.insertUser(user);
            if (isInsert) {
                employeetable.setItems(employeeBoImpl.getAllUsers());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee Added Successfully..!");
                alert.showAndWait();
                employeeid.setText(employeeBoImpl.generateEmployeeId());
                employeeaddress.setText("");
                employeename.setText("");
                employeeemail.setText("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }

        //clear();
    }



    public void clear() {

        employeeid.setText("");
        employeename.setText("");
        employeeemail.setText("");
        employeeaddress.setText("");
    }





    

    public void ReleaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = employeeBoImpl.isValidEmail(employeeemail.getText());
        if (!isValidEmail) {
            Addbuttton.setVisible(true);
        } else {
            Addbuttton.setDisable(false);
        }
    }

    public void addaction(ActionEvent actionEvent) {
        
        
        System.out.println(employeename.getText());
        System.out.println(employeeaddress.getText());
        System.out.println(employeeemail.getText());
    }

    public void searchaction(ActionEvent actionEvent) {
        Employee user = employeeBoImpl.getUserById(employeeid.getText());
        employeename.setText(user.getName());
        employeeemail.setText(user.getEmail());
        employeeaddress.setText(user.getAddress());
    }

    public void updateaction(ActionEvent actionEvent) {
        if (!employeeemail.getText().equals("") && !employeeaddress.getText().equals("") && !employeename.getText().equals("")) {
            Employee user = new Employee(employeeid.getText(),
                    employeename.getText(),
                    employeeemail.getText(),
                    null,
                    null,
                    employeeaddress.getText());

            boolean isUpdated = employeeBoImpl.updateUser(user);
            if (isUpdated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Update");
                alert.setContentText("Employee Updated Successfully");
                alert.showAndWait();
                employeeemail.setText("");
                employeeaddress.setText("");
                employeename.setText("");
                employeetable.setItems(employeeBoImpl.getAllUsers());
                clear();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }

    }

    public void deleteaction(ActionEvent actionEvent) {
        if (!employeeid.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Employee");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                boolean isDeleted = employeeBoImpl.deleteUserById(employeeid.getText());
                if (isDeleted) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully");
                    alert2.showAndWait();
                    employeetable.setItems(employeeBoImpl.getAllUsers());
                    employeeemail.setText("");
                    employeeaddress.setText("");
                    employeename.setText("");
                    clear();
                }
            }
        }
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
}






