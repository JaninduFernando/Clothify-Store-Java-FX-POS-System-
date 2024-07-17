package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.SupplierBoImpl;
import org.example.edu.model.Supplier;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageSuppliersController implements Initializable {
    public AnchorPane supplieranchor;
    public JFXTextField supid;
    public JFXTextField supemail;
    public JFXTextField supaddress;
    public JFXTextField supname;
    public TableView suppliertable;
    public TableColumn idcol;
    public TableColumn namecol;
    public TableColumn emailcol;
    public TableColumn addresscol;
    public Button btnupdate;

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    SupplierBoImpl supplierBoImpl = new SupplierBoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        supid.setText(supplierBoImpl.generateSupId());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("company"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        suppliertable.setItems(supplierBoImpl.getAllSupplier());
    }

    public void searchaction(ActionEvent actionEvent) {

        Supplier supplier = supplierBoImpl.getSupById(supid.getText());
        supname.setText(supplier.getName());
        supemail.setText(supplier.getEmail());
        supaddress.setText(supplier.getCompany());
    }

    public void addaction(ActionEvent actionEvent) {

        Supplier supplier = new Supplier(
                supid.getText(),
                supname.getText(),
                supemail.getText(),
                supaddress.getText()

        );

        if (!supname.getText().equals("") && supplierBoImpl.isValidEmail(supemail.getText()) && !supaddress.getText().equals("")) {


            boolean isInsert =  supplierBoImpl.insertSupplier(supplier);
            if (isInsert) {
                supid.setText(supplierBoImpl.generateSupId());
                suppliertable.setItems( supplierBoImpl.getAllSupplier());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Supplier Added");
                alert.setContentText("Supplier Added Successfully..!");
                alert.showAndWait();
                supaddress.setText("");
                supname.setText("");
                supemail.setText("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    public void deleteaction(ActionEvent actionEvent) {

        if (!supid.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Supplier");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = supplierBoImpl.deleteSupById(supid.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Supplier Deleted");
                    alert2.setContentText("Supplier deleted successfully");
                    alert2.showAndWait();
                    suppliertable.setItems(supplierBoImpl.getAllSupplier());
                    supemail.setText("");
                    supaddress.setText("");
                    supname.setText("");
                    supid.setText(supplierBoImpl.generateSupId());
                }
            }
        }
    }

    public void updateaction(ActionEvent actionEvent) {

        if (!supemail.getText().equals("") && !supaddress.getText().equals("") && !supname.getText().equals("")){
            Supplier supplier = new Supplier(
                    supid.getText(),
                    supname.getText(),
                    supemail.getText(),
                    supaddress.getText());

            boolean isUpdated = supplierBoImpl.updateSup(supplier);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Update");
                alert.setContentText("Customer Updated Successfully");
                alert.showAndWait();
                supemail.setText("");
                supaddress.setText("");
                supname.setText("");
                suppliertable.setItems(supplierBoImpl.getAllSupplier());
                supid.setText(supplierBoImpl.generateSupId());

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
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
