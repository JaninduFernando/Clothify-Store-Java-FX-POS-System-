package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.SupplierBoImpl;
import org.example.edu.model.Supplier;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewSupplierController implements Initializable {
    public JFXTextField supid;
    public JFXTextField supemail;
    public JFXTextField supCompany;
    public JFXTextField supname;
    public TableView suppliertable;
    public TableColumn idcol;
    public TableColumn namecol;
    public TableColumn emailcol;
    public TableColumn addresscol;
    public AnchorPane VSupplieranchor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("company"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));

        suppliertable.setItems(SupplierBoImpl.getAllSupplier());

    }
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();


    boolean isAction = true,isMouseClick,isPriceValid ;
    public void dataBar(MouseEvent mouseEvent) {

        int index = suppliertable.getSelectionModel().getSelectedIndex();


        if(index < 0){
            return;
        }
        String id = idcol.getCellData(index).toString();

        if (isAction){
            isPriceValid = true;
            Supplier supplier = SupplierBoImpl.getSupById(id);
            supid.setText(supplier.getId());
            supname.setText(supplier.getName());
            supCompany.setText(supplier.getCompany());
            supemail.setText(supplier.getEmail());

            if (!supplier.getId().equals("")){
                isMouseClick = true;
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
    public void VSupplieranchor(MouseEvent mouseEvent) {

        supid.setText("");
        supname.setText("");
        supCompany.setText("");
        supemail.setText("");
    }


}
