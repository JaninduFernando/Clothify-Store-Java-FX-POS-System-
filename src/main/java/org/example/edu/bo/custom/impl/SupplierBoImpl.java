package org.example.edu.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.edu.bo.custom.SupplierBo;
import org.example.edu.dao.Custom.impl.SupplierDaoImpl;
import org.example.edu.dao.DaoFactory;
import org.example.edu.entity.SupplierEntity;
import org.example.edu.model.Supplier;
import org.example.edu.util.DaoType;

public class SupplierBoImpl implements SupplierBo {
    SupplierDaoImpl supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);


    public boolean isValidEmail(String email) {

        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public boolean insertSupplier(Supplier supplier) {

        SupplierEntity supplierEntity = new ObjectMapper().convertValue(supplier, SupplierEntity.class);
        return supplierDao.insert(supplierEntity);

    }

    public ObservableList getAllSupplier() {

        ObservableList<SupplierEntity> supplierEntities = supplierDao.searchAll();
        ObservableList<Supplier> list = FXCollections.observableArrayList();

        supplierEntities.forEach(supplierEntity -> {
            list.add(new ObjectMapper().convertValue(supplierEntity, Supplier.class));
        });
        return list;
    }

    public String generateSupId() {

        String lastSupId = supplierDao.getLatestId();
        if (lastSupId == null) {
            return "S0001";
        }

        int number = Integer.parseInt(lastSupId.split("S")[1]);
        number++;
        return String.format("S%04d", number);
    }

    public Supplier getSupById(String id) {
        SupplierEntity supplierEntity = supplierDao.searchById(id);
        return new ObjectMapper().convertValue(supplierEntity, Supplier.class);
    }

    public boolean deleteSupById(String text) {return supplierDao.delete(text);}

        public boolean updateSup(Supplier supplier) {
        return supplierDao.update(new ObjectMapper().convertValue(supplier, SupplierEntity.class));
    }
}
