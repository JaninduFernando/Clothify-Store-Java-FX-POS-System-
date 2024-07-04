package org.example.edu.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.edu.bo.custom.ProductBo;
import org.example.edu.dao.Custom.impl.ProductDaoImpl;
import org.example.edu.dao.DaoFactory;
import org.example.edu.entity.ProductEntity;
import org.example.edu.model.Product;
import org.example.edu.util.DaoType;

public class ProductBoImpl implements ProductBo {

    ProductDaoImpl productDao= DaoFactory.getInstance().getDao(DaoType.PRODUCT);


    public String generateProId() {
        String lastSupId =  productDao.getLatestId();
        if (lastSupId==null){
            return "P0001";
        }

        int number = Integer.parseInt(lastSupId.split("P")[1]);
        number++;
        return String.format("P%04d", number);
    }

    public boolean insertPro(Product product) {


        ProductEntity productEntity = new ObjectMapper().convertValue(product, ProductEntity.class);
        return productDao.insert(productEntity);
    }


    public ObservableList<Product> getAllPro() {

        ObservableList<ProductEntity> productEntities =  productDao.searchAll();

        ObservableList<Product> productObservableList = FXCollections.observableArrayList();

        productEntities.forEach(productEntity -> {
            productObservableList.add(new ObjectMapper().convertValue(productEntity, Product.class));
        });
        return productObservableList;
    }

    public Product getProById(String id) {
        ProductEntity productEntity =  productDao.searchById(id);
        return new ObjectMapper().convertValue(productEntity,Product.class);

    }

    public boolean deleteProById(String text) {

        return productDao.delete(text);
    }

    public boolean updatePro(Product product) {

        return productDao.update(new ObjectMapper().convertValue(product, ProductEntity.class));
    }
}
