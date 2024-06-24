package org.example.edu.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.dao.Custom.EmployeeDao;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LandingPageController implements Initializable {
    public TextField txtEmail;
    public JFXTextField txtNewPass;
    public JFXTextField txtRePassWord;
    public TextField txtOTPCode;
    public AnchorPane txtRePass;
    public JFXTextField emailField;
    private int otp;
    EmployeeBoImpl employeeBoImpl = new EmployeeBoImpl();

    SceneSwitchController sceneSwitch = SceneSwitchController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Alert(Alert.AlertType.INFORMATION,"If your first time to sign in to this, Please reset your password clicked forgot password button").show();

    }



    public void ResetAction(ActionEvent actionEvent) {

        try {
            if (txtNewPass.getText().equals(txtRePassWord.getText())){
                if (otp==Integer.parseInt(txtOTPCode.getText())){
                    boolean isUpdatePassword = employeeBoImpl.isUpdatePassword(txtEmail.getText(),txtNewPass.getText());
                    if (isUpdatePassword){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Reset Password");
                        alert.setContentText("Password reset Successfully");
                        alert.showAndWait();
                        txtNewPass.setText("");
                        txtRePassWord.setText("");
                        txtOTPCode.setText("");
                        sceneSwitch.switchScene(txtRePass, "welcomeForm.fxml");

                    }
                }else {
                    new Alert(Alert.AlertType.ERROR,"Incorrect OTP, Please Check your OTP").show();
                }

            }else {
                new Alert(Alert.AlertType.ERROR,"Password & Confirmation Password does not match..!!").show();
            }
        }catch (Exception e){
            System.out.println(e);
            new Alert(Alert.AlertType.ERROR,"Invalid OTP").show();
        }
    }

    public void SendOtpAction(ActionEvent actionEvent) {
        Random random = new Random();
        otp = random.nextInt(999999)+100000;
        System.out.println(otp);

        try {
            EmployeeBoImpl.sendEmail(emailField.getText(),Integer.toString(otp));
            new Alert(Alert.AlertType.INFORMATION,"Send Email Successfully").show();
        } catch (MessagingException e) {
            new Alert(Alert.AlertType.ERROR,"Access Denided..your Email is invalid").show();
        }
    }
}
