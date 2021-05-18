package com.fot.Canteen_Management_System.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {

    private String name;
    private Integer quantity;
    private String img_path;
    private float price;
    private Integer mobile;
    private String Item_name;


}
