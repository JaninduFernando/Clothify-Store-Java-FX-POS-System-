package org.example.edu.dao.Custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.edu.dao.Custom.OrderDao;
import org.example.edu.entity.EmployeeEntity;
import org.example.edu.entity.OrderEntity;
import org.example.edu.entity.OrderItemEntity;
import org.example.edu.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    // OrderDaoImpl orderDao = DaoFactory.getInstance().getDao(DaoType. ORDER);

    public OrderEntity search(String s) {
        return null;
    }


    public ObservableList<OrderEntity> searchAll() {
        return null;
    }

    @Override
    public OrderEntity Search(String s) {
        return null;
    }

    @Override
    public ObservableList<OrderEntity> SearchAll() {
        return null;
    }

    @Override
    public boolean insert(OrderEntity orderEntity) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(orderEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(OrderEntity orderEntity) {
        return false;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    public String getLatestId() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("SELECT id FROM order_table ORDER BY id DESC LIMIT 1");
        String id = (String) query.uniqueResult();
        session.close();
        return id;
    }

    public boolean saveAll(OrderItemEntity orderItemEntity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        session.persist(orderItemEntity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    private void updateQty(String itemCode, Integer qty) {

    }

    public OrderEntity searchById(String id) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        Query query = session.createQuery("FROM order_table WHERE id=:id");
        query.setParameter("id", id);
        OrderEntity orderEntity = (OrderEntity) query.uniqueResult();
        session.close();

        return orderEntity;
    }

    public List<OrderItemEntity> searchOrderDetails() {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

        List<OrderItemEntity> fromOrderDetails = session.createQuery("FROM order_details").list();
        return fromOrderDetails;

    }
}
