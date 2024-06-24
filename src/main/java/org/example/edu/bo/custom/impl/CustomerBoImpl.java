package org.example.edu.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.edu.bo.custom.CustomerBo;
import org.example.edu.dao.Custom.impl.CustomerDaoImpl;
import org.example.edu.dao.DaoFactory;
import org.example.edu.entity.CustomerEntity;
import org.example.edu.model.Customer;
import org.example.edu.util.DaoType;

public class CustomerBoImpl implements CustomerBo {

    CustomerDaoImpl customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);

    public boolean insertUser(Customer customer) {

        CustomerEntity customerEntity = new ObjectMapper().convertValue(customer, CustomerEntity.class);
        return customerDao.insert(customerEntity);

    }

    public boolean isValidEmail(String email) {

        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String generateEmployeeId() {

        String lastEmployeeId = customerDao.getLatestId();
        if (lastEmployeeId==null){
            return "C0001";
        }

        int number = Integer.parseInt(lastEmployeeId.split("C")[1]);
        number++;
        return String.format("C%04d", number);
    }

    public ObservableList<Customer> getAllCustomer() {
        ObservableList<CustomerEntity> customerEntities = customerDao.SearchAll();

        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        customerEntities.forEach(customerEntity -> {
            customerList.add(new ObjectMapper().convertValue(customerEntity, Customer.class));
        });
        return customerList;
    }

    public Customer getCusById(String id) {

        CustomerEntity customerEntity = customerDao.searchById(id);
        return new ObjectMapper().convertValue(customerEntity,Customer.class);

    }

    public boolean updateCus(Customer customer) {

        return customerDao.update(new ObjectMapper().convertValue(customer, CustomerEntity.class));
    }

    public boolean deleteCusById(String text) {

        return customerDao.delete(text);
    }
}
