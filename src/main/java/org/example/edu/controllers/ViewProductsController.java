package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.ProductBoImpl;
import org.example.edu.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProductsController implements Initializable {
    public JFXTextField pId;
    public JFXTextField pName;
    public JFXTextField pQty;
    public TableColumn idcol;
    public TableColumn namecol;
    public TableColumn sizecol;
    public TableColumn qtycol;
    public TableColumn supIdcol;
    public TableColumn pricePerOnecol;
    public ComboBox cmbSize;
    public ComboBox cmbSup;
    public JFXTextField pricePerOne;
    public AnchorPane VProductanchor;
    public TableView VProductTable;

    ProductBoImpl productBoImpl = new ProductBoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sizecol.setCellValueFactory(new PropertyValueFactory<>("size"));
        qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        supIdcol.setCellValueFactory(new PropertyValueFactory<>("supId"));
        pricePerOnecol.setCellValueFactory(new PropertyValueFactory<>("price"));

        VProductTable.setItems(productBoImpl.getAllPro());

    }
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();





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

    boolean isAction = true,isMouseClick,isPriceValid ;
    public void dataBar(MouseEvent mouseEvent) {
        int index = VProductTable.getSelectionModel().getSelectedIndex();


        if(index < 0){
            return;
        }
        String id = idcol.getCellData(index).toString();

        if (isAction){
            isPriceValid = true;
            Product product = productBoImpl.getProById(id);
            pId.setText(product.getId());
            pName.setText(product.getName());
            pQty.setText(String.valueOf(product.getQty()));
            pricePerOne.setText(String.valueOf(product.getPrice()));
            cmbSize.getSelectionModel().select(product.getSize());
            cmbSup.getSelectionModel().select(product.getSupId());

            if (!product.getId().equals("")){
                isMouseClick = true;
            }
        }
    }
    public void VProductanchor(MouseEvent mouseEvent) {

        pId.setText("");
        pName.setText("");
        pQty.setText(String.valueOf(""));
        pricePerOne.setText(String.valueOf(""));
        cmbSize.getSelectionModel().select("");
        cmbSup.getSelectionModel().select("");
    }


}
