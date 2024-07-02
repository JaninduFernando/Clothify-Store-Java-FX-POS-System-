package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.SupplierBoImpl;
import org.example.edu.model.Supplier;

import java.net.URL;
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
    }

    public void updateaction(ActionEvent actionEvent) {
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
