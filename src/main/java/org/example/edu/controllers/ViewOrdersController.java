package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.BoFactory;
import org.example.edu.bo.custom.impl.CustomerBoImpl;
import org.example.edu.bo.custom.impl.OrderBoImpl;
import org.example.edu.bo.custom.impl.ProductBoImpl;
import org.example.edu.bo.custom.impl.SupplierBoImpl;
import org.example.edu.dao.Custom.impl.OrderDaoImpl;
import org.example.edu.entity.OrderItemEntity;
import org.example.edu.model.*;
import org.example.edu.util.BoType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewOrdersController implements Initializable {
    public AnchorPane viewOrderanchor;
    public TableView viewOrderTable;
    public TableColumn orderIdCol;
    public TableColumn ItemCodeCol;
    public TableColumn qtyCol;
    public TableColumn discountCol;
    public JFXTextField cusId;
    public JFXTextField cusName;
    public JFXTextField cusAddress;
    public JFXTextField cusEmail;
    public JFXTextField supId;
    public JFXTextField supCompany;
    public JFXTextField supName;
    public JFXTextField supEmail;

    String id;
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    ProductBoImpl productBoImpl = new ProductBoImpl();
    OrderBoImpl orderBoImpl = BoFactory.getInstance().getBo(BoType.ORDER);
    CustomerBoImpl customerBoImpl = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    SupplierBoImpl supplierBoImpl = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        discountCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("discount"));

        viewOrderTable.setItems(new OrderBoImpl().getOrderDetails());

    }

    boolean isRowSelect, isMouseClick, isPriceValid;

    public void dataBar(MouseEvent mouseEvent) {

        int index = viewOrderTable.getSelectionModel().getSelectedIndex();

        try {
            isRowSelect = true;
            String orderId = orderIdCol.getCellData(index).toString();
            String itemId = ItemCodeCol.getCellData(index).toString();
            Product product = productBoImpl.getProById(itemId);

            id = orderId;

            Order order = new OrderBoImpl().getOrderById(orderId);
            Customer customer = new CustomerBoImpl(). getCusById(order.getCusId());
            Supplier supplier = new SupplierBoImpl().getSupById(product.getSupId());

            cusAddress.setText(customer.getAddress());
            cusId.setText(customer.getId());
            cusEmail.setText(customer.getEmail());
            cusName.setText(customer.getName());

            supId.setText(supplier.getId());
            supName.setText(supplier.getName());
            supCompany.setText(supplier.getCompany());
            supEmail.setText(supplier.getEmail());

        } catch(Exception e)
        {
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

    public void anchorClick(MouseEvent mouseEvent) {

        cusId.setText("");
        cusName.setText("");
        cusAddress.setText("");
        cusEmail.setText("");

        supId.setText("");
        supName.setText("");
        supCompany.setText("");
        supEmail.setText("");
    }


}
