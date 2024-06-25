package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.dao.Custom.EmployeeDao;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable {
    public JFXTextField emailField;
    public JFXTextField newpassword;
    public JFXTextField reenternewpassword;
    public JFXTextField otpcode;
    public AnchorPane landing;
    private int otp;
    EmployeeBoImpl employeeBoImpl = new EmployeeBoImpl();

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Alert(Alert.AlertType.INFORMATION, "If your first time to sign in to this, Please reset your password clicked forgot password button").show();

    }


    public void SendOtpAction(ActionEvent actionEvent) {
        Random random = new Random();
        otp = random.nextInt(999999) + 100000;
        System.out.println(otp);

        try {
            EmployeeBoImpl.sendEmail(emailField.getText(), Integer.toString(otp));
            new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR, "Access Denided..your Email is invalid").show();
        }
    }

    public void resetaction(ActionEvent actionEvent) {
        try {
            if (newpassword.getText().equals(reenternewpassword.getText())) {
                if (otp == Integer.parseInt(otpcode.getText())) {
                    boolean isUpdatePassword = employeeBoImpl.isUpdatePassword(emailField.getText(), newpassword.getText());
                    if (isUpdatePassword) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Reset Password");
                        alert.setContentText("Password reset Successfully");
                        alert.showAndWait();
                        newpassword.setText("");
                        reenternewpassword.setText("");
                        otpcode.setText("");
                        sceneSwitch.switchScene(landing, "signin-form.fxml");

                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Incorrect OTP, Please Check your OTP").show();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Password & Confirmation Password does not match..!!").show();
            }
        } catch (Exception i) {
            System.out.println(i);
            new Alert(Alert.AlertType.ERROR, "Invalid OTP").show();
        }
    }
}


