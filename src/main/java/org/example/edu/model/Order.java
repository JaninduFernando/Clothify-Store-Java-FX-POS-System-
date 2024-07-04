package org.example.edu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    private String id;
    private String cusId;
    private Date date;
    private Double amount;
}
