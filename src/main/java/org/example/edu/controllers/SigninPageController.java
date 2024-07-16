package org.example.edu.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.BoFactory;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.entity.EmployeeEntity;
import org.example.edu.util.BoType;

import java.io.IOException;

public class SigninPageController {
    
    public JFXTextField username;
    public JFXPasswordField passwordfield;
    public AnchorPane signinanchor;


    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    EmployeeBoImpl employeeBoImpl = new EmployeeBoImpl();


    public void signinaction(ActionEvent actionEvent) throws IOException {

        employeeBoImpl = BoFactory.getInstance().getBo(BoType.USER);

        EmployeeEntity userEntity = employeeBoImpl.getUserByEmail(username.getText());


        if (userEntity == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address or Password").show();
            return;
        }

        String password = employeeBoImpl.passwordDecrypt(userEntity.getPassword());

        if (userEntity.getRole().equals("Admin") && password.equals(passwordfield.getText())) {
            System.out.println("Logged");
            try {
                SceneSwitchController.getInstance().switchScene(signinanchor, "manageEmployee-form.fxml");
            } catch (IOException i) {
                throw new RuntimeException(i);
            }
        } else if (userEntity.getRole().equals("Employee") && password.equals(passwordfield.getText())) {
            SceneSwitchController.getInstance().switchScene(signinanchor, "manageCustomers-form.fxml");
        } else if (userEntity.getId() == null) {
            System.out.println("Null");
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
        }

    }



    public void forgetaction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(signinanchor, "landingPage-form.fxml");
    }

    public void needaction(ActionEvent actionEvent) {

        new Alert(Alert.AlertType.ERROR, "Invalid Password").show();
    }
}

