package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.model.User;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class ManageEmployeeController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;

    public TableView Table1;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public ComboBox Cmb;
    public Button Addbuttton;

    EmployeeBoImpl employeeBoImpl = new EmployeeBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDropMenu();


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        txtId.setText(employeeBoImpl.generateEmployeeId());
        Table1.setItems(employeeBoImpl.getAllUsers());
    }

    private void loadDropMenu() {
        ObservableList<Object> items = FXCollections.observableArrayList();
        items.add("Admin Panel");
        items.add("Employee");
        Cmb.setItems(items);
    }

    public void AddAction(ActionEvent actionEvent) {


        Random random = new Random();
        int p = random.nextInt(99999999) + 10000000;

        String encrypt = Integer.toString(p);
        String password = employeeBoImpl.passwordEncrypt(encrypt);

        User user = new User(
                txtId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                password,
                Cmb.getValue().toString(),
                txtAddress.getText()
        );
        if (!txtName.getText().equals("") && employeeBoImpl.isValidEmail(txtEmail.getText()) && !txtAddress.getText().equals("")) {


            boolean isInsert = employeeBoImpl.insertUser(user);
            if (isInsert) {
                Table1.setItems(employeeBoImpl.getAllUsers());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Added");
                alert.setContentText("Employee Added Successfully..!");
                alert.showAndWait();
                txtId.setText(employeeBoImpl.generateEmployeeId());
                txtAddress.setText("");
                txtName.setText("");
                txtEmail.setText("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }

        //clear();
    }

    public void searchAction(ActionEvent actionEvent) {

        User user = employeeBoImpl.getUserById(txtId.getText());
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        Cmb.setValue(user.getRole());
        txtAddress.setText(user.getAddress());

    }

    public void clear(){

        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        Cmb.setValue("");
        txtAddress.setText("");
    }



    public void UpdateAction(ActionEvent actionEvent) {

        if (!txtEmail.getText().equals("") && !txtAddress.getText().equals("") && !txtName.getText().equals("")){
            User user = new User(txtId.getText(),
                    txtName.getText(),
                    txtEmail.getText(),
                    null ,
                    null,
                    txtAddress.getText());

            boolean isUpdated = employeeBoImpl.updateUser(user);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Employee Update");
                alert.setContentText("Employee Updated Successfully");
                alert.showAndWait();
                txtEmail.setText("");
                txtAddress.setText("");
                txtName.setText("");
                Table1.setItems(employeeBoImpl.getAllUsers());
                clear();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }

    }


    public void DeleteActiion(ActionEvent actionEvent) {

        if (!txtId.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Employee");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = employeeBoImpl.deleteUserById(txtId.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Employee Deleted");
                    alert2.setContentText("Employee deleted successfully");
                    alert2.showAndWait();
                    Table1.setItems(employeeBoImpl.getAllUsers());
                    txtEmail.setText("");
                    txtAddress.setText("");
                    txtName.setText("");
                    clear();
                }
            }
        }

    }

    public void ReleaseEmailkey(KeyEvent keyEvent) {
        boolean isValidEmail = employeeBoImpl.isValidEmail(txtEmail.getText());
        if (!isValidEmail) {
            Addbuttton.setVisible(true);
        } else {
            Addbuttton.setDisable(false);
        }
    }





    public void DashAction(ActionEvent actionEvent) {
    }

    public void SupplierAction(ActionEvent actionEvent) {
    }

    public void ManageAction(ActionEvent actionEvent) {
    }

    public void OrderAction(ActionEvent actionEvent) {
    }

    public void ItemAction(ActionEvent actionEvent) {
    }
}
