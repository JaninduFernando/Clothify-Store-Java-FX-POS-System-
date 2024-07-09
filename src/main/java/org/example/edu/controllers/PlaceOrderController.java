package org.example.edu.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.edu.bo.custom.impl.CustomerBoImpl;
import org.example.edu.bo.custom.impl.OrderBoImpl;
import org.example.edu.bo.custom.impl.ProductBoImpl;
import org.example.edu.entity.OrderItemEntity;
import org.example.edu.entity.ProductEntity;
import org.example.edu.model.*;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    public ComboBox cmbCustomerIDs;
    public ComboBox cmbProductCode;
    public AnchorPane placeorderanchor;
    public Label lblTime;
    public Label lblDate;
    public Label lblOrderId;
    public Label lblName;
    public Label lblAddress;
    public Label lblEmail;
    public Label lblDesc;
    public Label lblUnitPrice;
    public TableView tblCart;
    public TableColumn itemCodeCol;
    public TableColumn DescCol;
    public TableColumn UnitPriceCol;
    public TableColumn QtyCol;
    public TableColumn TotalCol;
    public Label lblSize;
    public TextField QtyFroCus;
    public Label lblNetTotal;

    OrderBoImpl orderBoImpl = new OrderBoImpl();
    ProductBoImpl productBoImpl = new ProductBoImpl();
    CustomerBoImpl customerBoImpl = new CustomerBoImpl();
    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadDateAndTime();
        loadCustomerIDs();
        loadItemCodes();

        cmbCustomerIDs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerDataFroLbl((String) newValue);
        });
        cmbProductCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemDataFroLbl((String) newValue);
        });

        lblOrderId.setText(orderBoImpl.generateOrderId());


        itemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        DescCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void setItemDataFroLbl(String ItemCode) {

        Product product = productBoImpl.getProById(ItemCode);
        lblDesc.setText(product.getName());
        lblUnitPrice.setText(String.valueOf(product.getPrice()));
        lblSize.setText(product.getSize());

    }

    private void setCustomerDataFroLbl(String newValue) {

        Customer customer = customerBoImpl.getCusById(newValue);
        lblName.setText(customer.getName());
        lblEmail.setText(customer.getEmail());
        lblAddress.setText(customer.getAddress());
    }

    private void loadItemCodes() {

        ObservableList<Product> allSupplier = productBoImpl.getAllPro();
        ObservableList ids = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            ids.add(supplier.getId());

        });
        cmbProductCode.setItems(ids);
    }

    private void loadCustomerIDs() {

        ObservableList<Customer> allSupplier = customerBoImpl.getAllCustomer();
        ObservableList ids = FXCollections.observableArrayList();

        allSupplier.forEach(supplier -> {
            ids.add(supplier.getId());

        });
        cmbCustomerIDs.setItems(ids);

    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));


        //Time
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + " : " + time.getMinute() + " : " + time.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    ObservableList<CarTbl> cartList = FXCollections.observableArrayList();

    public void AddToCartAction(ActionEvent actionEvent) {

        String itemCode = (String) cmbProductCode.getValue();
        String desc = lblDesc.getText();
        Integer qtyFroCustomer = Integer.parseInt(QtyFroCus.getText());
        Double unitPrice = Double.valueOf(lblUnitPrice.getText());
        Double total = qtyFroCustomer * unitPrice;
        CarTbl cartTbl = new CarTbl(itemCode, desc, qtyFroCustomer, unitPrice, total, 0.0);
        System.out.println(cartTbl);

        cartList.add(cartTbl);
        tblCart.setItems(cartList);
        calcNetTotal();


    }
    double ttl = 0;
    private void calcNetTotal() {

        for (CarTbl cartObj : cartList) {
            ttl += cartObj.getTotal();
        }
        lblNetTotal.setText(String.valueOf(ttl) + "/=");
    }

    public void AddtoCartAction(ActionEvent actionEvent) {
    }

    public void PlaceOrderAction(ActionEvent actionEvent) {

        String id = lblOrderId.getText();
        String Cusid =cmbCustomerIDs.getValue().toString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;
        try {
            orderDate = format.parse(lblDate.getText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        double amount = ttl;

        ObservableList<OrderItem> orderItemObservableList = FXCollections.observableArrayList();

        for (CarTbl cartTbl : cartList) {
            String oID = lblOrderId.getText();
            String itemCode = cartTbl.getItemCode();
            Integer qty = cartTbl.getQty();
            Double discount = cartTbl.getDiscount();
            orderItemObservableList.add(new OrderItem(null,oID, itemCode, qty, discount)) ;

        }

        orderBoImpl.saveOrderDetails(orderItemObservableList);

        Order Order = new Order(id,Cusid,orderDate,amount );

        boolean isInsert = orderBoImpl.insertOrder(Order);
        if (isInsert) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Placed");
            alert.setContentText("Order Placed Successfully..!");
            alert.showAndWait();
            try {
                sceneSwitch.switchScene(placeorderanchor, "placeOrder.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            new Alert(Alert.AlertType.ERROR, "Somthing Wrong..!!!").show();
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
