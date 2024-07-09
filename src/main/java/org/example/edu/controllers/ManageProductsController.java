package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.ProductBoImpl;
import org.example.edu.bo.custom.impl.SupplierBoImpl;
import org.example.edu.model.Product;
import org.example.edu.model.Supplier;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageProductsController implements Initializable {
    public AnchorPane productanchor;
    public JFXTextField pId;
    public JFXTextField pName;
    public JFXTextField pQty;
    public TableView productTable;
    public TableColumn idcol;
    public TableColumn namecol;
    public TableColumn sizecol;
    public TableColumn qtycol;
    public TableColumn supIdcol;
    public TableColumn pricecol;
    public ComboBox cmb;
    public ComboBox cmbSup;
    public JFXTextField pricePerOne;

    ProductBoImpl productBoImpl = new ProductBoImpl();
    SupplierBoImpl supplierBoImpl = new SupplierBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDropMenu();
        loadSuplierIds();

        pId.setText(productBoImpl.generateProId());

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizecol.setCellValueFactory(new PropertyValueFactory<>("size"));
        qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        supIdcol.setCellValueFactory(new PropertyValueFactory<>("supId"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(productBoImpl.getAllPro());
    }

    private void loadSuplierIds() {

        ObservableList<Supplier> allSupplier = supplierBoImpl.getAllSupplier();
        ObservableList ids = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            ids.add(supplier.getId());

        });
        cmbSup.setItems(ids);
    }

    private void loadDropMenu() {
        ObservableList<Object> items = FXCollections.observableArrayList();
        items.add("XS");
        items.add("S");
        items.add("M");
        items.add("L");
        items.add("2XL");
        items.add("3XL");
        cmb.setItems(items);
    }

    public void AddAction(ActionEvent actionEvent) {

        String id = pId.getText();
        String name = pName.getText();
        String size =cmb.getValue().toString();
        Integer qty = Integer.parseInt(pQty.getText());
        Double price  =Double.valueOf( pricePerOne.getText());
        String cmb2 =cmbSup.getValue().toString();

        Product product =new Product(
                id,name,size,qty,price,cmb2
        );

        if (!pName.getText().equals("") && !pQty.getText().equals("")) {


            boolean isInsert = productBoImpl.insertPro(product);
            if (isInsert) {
                productTable.setItems(productBoImpl.getAllPro());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Added");
                alert.setContentText("Product Added Successfully..!");
                alert.showAndWait();
                pricePerOne.setText("");
                pName.setText("");
                pQty.setText("");
                cmb.setValue("");
                cmbSup.setValue("");
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
        }
    }

    public void searchAction(ActionEvent actionEvent) {

        Product product = productBoImpl.getProById(pId.getText());
        pName.setText(product.getName());
        pQty.setText(String.valueOf(product.getQty()));
        pricePerOne.setText(String.valueOf(product.getPrice()));
        cmb.setValue(product.getSize());
        cmbSup.setValue(product.getSupId());
    }

    public void DeleteActiion(ActionEvent actionEvent) {

        if (!pId.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting");
            alert.setContentText("Are you sure want to delete this Product");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get()== ButtonType.OK){
                boolean isDeleted = productBoImpl.deleteProById(pId.getText());
                if (isDeleted){
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Product Deleted");
                    alert2.setContentText("Product deleted successfully");
                    alert2.showAndWait();
                    productTable.setItems(productBoImpl.getAllPro());
                    pricePerOne.setText("");
                    pName.setText("");
                    pQty.setText("");
                    cmb.setValue("");
                    cmbSup.setValue("");
                    pId.setText(productBoImpl.generateProId());
                    pId.setText(productBoImpl.generateProId());

                }
            }
        }
    }

    public void UpdateAction(ActionEvent actionEvent) {

        if (!pName.getText().equals("") && !pQty.getText().equals("")){

            String id = pId.getText();
            String name = pName.getText();
            String size =cmb.getValue().toString();
            Integer qty = Integer.parseInt(pQty.getText());
            Double price  =Double.valueOf( pricePerOne.getText());
            String cmb2 =cmbSup.getValue().toString();

            Product product =new Product(
                    id,name,size,qty,price,cmb2
            );

            boolean isUpdated = productBoImpl.updatePro(product);
            if (isUpdated){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Update");
                alert.setContentText("Product Updated Successfully");
                alert.showAndWait();
                pricePerOne.setText("");
                pName.setText("");
                pQty.setText("");
                cmb.setValue("");
                cmbSup.setValue("");
                productTable.setItems(productBoImpl.getAllPro());
                pId.setText(productBoImpl.generateProId());
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something Missing");
            alert.setContentText("Please Check your Form again..!!!");
            alert.showAndWait();
        }
    }

    public void ProductAction(ActionEvent actionEvent) {
    }

    public void CusAction(ActionEvent actionEvent) {
    }

    public void PlaceAction(ActionEvent actionEvent) {
    }

    public void SupplierAction(ActionEvent actionEvent) {
    }

    public void log(ActionEvent actionEvent) {
    }
}
