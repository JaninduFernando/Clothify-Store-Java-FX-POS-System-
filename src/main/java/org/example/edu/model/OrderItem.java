package org.example.edu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private Integer id;

    private String orderId;
    private String itemCode;
    private Integer qty;
    private Double discount;
}
