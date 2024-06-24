package org.example.edu.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.edu.bo.custom.EmployeeBo;
import org.example.edu.controllers.LandingPageController;
import org.example.edu.dao.Custom.impl.EmployeeDaoimpl;
import org.example.edu.dao.DaoFactory;
import org.example.edu.entity.UserEntity;
import org.example.edu.model.User;
import org.example.edu.util.DaoType;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeBoImpl implements EmployeeBo {
    static EmployeeDaoimpl employeeDaoimpl = DaoFactory.getInstance().getDao(DaoType.USER);


    public boolean insertUser(User user) {

        UserEntity userEntity = new ObjectMapper().convertValue(user, UserEntity.class);
        return employeeDaoimpl.insert(userEntity);

    }


    public ObservableList getAllUsers() {

        ObservableList<UserEntity> list = employeeDaoimpl.SearchAll();
        ObservableList<User> userList = FXCollections.observableArrayList();

        list.forEach(userEntity -> {
            userList.add(new ObjectMapper().convertValue(userEntity,User.class));
        });
        return userList;
    }

    public User getUserById(String id) {

        UserEntity userEntity = employeeDaoimpl.searchById(id);
        return new ObjectMapper().convertValue(userEntity,User.class);

    }

    public boolean updateUser(User user) {

        UserEntity userEntity = new ObjectMapper().convertValue(user, UserEntity.class);

        return employeeDaoimpl.update(userEntity);

    }

    public boolean deleteUserById(String text) {

        return employeeDaoimpl.delete(text);

    }

    public static String passwordEncrypt(String password) {

        return new String(Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8)));
    }

    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String generateEmployeeId() {

        String lastEmployeeId = employeeDaoimpl.getLatestId();
        if (lastEmployeeId==null){
            return "E0001";
        }

        int number = Integer.parseInt(lastEmployeeId.split("E")[1]);
        number++;
        return String.format("E%04d", number);
    }

    public static void sendEmail(String receiveEmail, String text) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myEmail = "janidulakshan08@gmail.com";
        String password = "lkpxmmaboxkzweja";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,password);
            }
        });
        String text1 ="Don't share this otp Code : "+text;
        Message message = prepareMessage(session,myEmail,receiveEmail,text1);
        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myEmail, String receiveEmail, String text) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO,new InternetAddress[]{
                    new InternetAddress(receiveEmail)
            });
            message.setSubject("OTP CODE");
            message.setText(text);

            return message;
        }catch (Exception e){
            Logger.getLogger(LandingPageController.class.getName()).log(Level.SEVERE,null,e);
        }
        return null;
    }


    public static boolean isUpdatePassword(String email, String password) {


        String encryptPassword=passwordEncrypt(password);
        return employeeDaoimpl.update(email,encryptPassword);
    }

    public UserEntity getUserByEmail(String email) {
        return employeeDaoimpl.Search(email);

    }

    public String passwordDecrypt(String password) {
        return new String(Base64.getDecoder().decode(password));
    }
}



