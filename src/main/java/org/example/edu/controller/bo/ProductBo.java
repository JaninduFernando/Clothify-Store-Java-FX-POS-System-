package org.example.edu.controller.bo;

import org.example.edu.controller.dto.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductBo extends SuperBo{
    public ArrayList<Product> convertToAlist(List<Product> list);

}
