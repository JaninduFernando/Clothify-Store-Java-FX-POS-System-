package org.example.edu.bo;

import org.example.edu.bo.custom.impl.CustomerBoImpl;
import org.example.edu.bo.custom.impl.EmployeeBoImpl;
import org.example.edu.util.BoType;

public class BoFactory {

    private static BoFactory instance;

    private BoFactory(){}

    public static BoFactory getInstance(){return instance!=null?instance:(instance=new BoFactory());}


    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case USER:return (T)new EmployeeBoImpl();
            case CUSTOMER:return (T)new CustomerBoImpl();

        }
        return null;
    }
}
