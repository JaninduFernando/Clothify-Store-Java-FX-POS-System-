package org.example.edu.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.BoFactory;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.entity.UserEntity;
import org.example.edu.util.BoType;

import java.io.IOException;

public class SigninPageController {
    public AnchorPane welcomeAnchor;
    public JFXTextField username;

    public JFXPasswordField passwordfield;


    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();
    EmployeeBoImpl userBoImpl=new EmployeeBoImpl();



    public void forgetAction(ActionEvent actionEvent) throws IOException {
        sceneSwitch.switchScene(welcomeAnchor, "ResetForm.fxml");
    }

    public void NeedAction(ActionEvent actionEvent) {

    }

    public void signinaction(ActionEvent actionEvent) throws IOException {

        userBoImpl=BoFactory.getInstance().getBo(BoType.USER);

        UserEntity userEntity = userBoImpl.getUserByEmail(username.getText());


        if (userEntity==null){
            new Alert(Alert.AlertType.ERROR,"Invalid Email Address or Password").show();
            return;
        }

        String password = userBoImpl.passwordDecrypt(userEntity.getPassword());

        if (userEntity.getRole().equals("Admin Panel") && password.equals(passwordfield.getText())){
            System.out.println("Logged");
            try {
                SceneSwitchController.getInstance().switchScene(welcomeAnchor, "UserRegisterForm.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else if (userEntity.getRole().equals("Employee") && password.equals(passwordfield.getText())){
            SceneSwitchController.getInstance().switchScene(welcomeAnchor, "AddCusFormController.fxml");
        } else if (userEntity.getId()==null) {
            System.out.println("Null");
        } else{
            new Alert(Alert.AlertType.ERROR,"Invalid Password").show();
        }

    }

    public void link(MouseEvent mouseEvent) {


    }
}

