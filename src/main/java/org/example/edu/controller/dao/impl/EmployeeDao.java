package org.example.edu.controller.dao.impl;

import org.example.edu.controller.dao.CrudDao;
import org.example.edu.controller.dto.Employee;

public interface EmployeeDao extends CrudDao<Employee,String>{

    public String searchEmployeeById(String id);




}
