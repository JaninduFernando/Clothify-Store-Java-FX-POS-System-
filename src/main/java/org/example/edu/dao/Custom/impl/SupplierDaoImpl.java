package org.example.edu.dao.Custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.edu.dao.Custom.SupplierDao;
import org.example.edu.entity.SupplierEntity;
import org.example.edu.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public SupplierEntity Search(String s) {
        return null;
    }

    @Override
    public ObservableList<SupplierEntity> SearchAll() {
        return null;
    }

    @Override
    public ObservableList<SupplierEntity> searchAll() {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        List<SupplierEntity> list = session.createQuery("FROM supplier").list();
        session.close();
        ObservableList<SupplierEntity> supplierEntityList = FXCollections.observableArrayList();

        list.forEach(supplierEntity -> {
            supplierEntityList.add(supplierEntity);
        });
        return supplierEntityList;
    }

    @Override
    public boolean insert(SupplierEntity supplierEntity) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(supplierEntity);
        session.getTransaction().commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    public String getLatestId() {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;

    }
}

