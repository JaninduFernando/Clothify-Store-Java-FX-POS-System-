package org.example.edu.dao;

import org.example.edu.dao.Custom.impl.EmployeeDaoimpl;
import org.example.edu.util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory(){}

    public static DaoFactory getInstance() {return  instance!=null?instance:(instance=new DaoFactory()); }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case USER:return (T)new EmployeeDaoimpl();
            //case CUSTOMER:return (T)new CustomerDaoImpl();
        }
        return null;
    }
}
