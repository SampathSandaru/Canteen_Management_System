package com.fot.Canteen_Management_System.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

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
    private LocalDate Order_time;
    private Integer Status;
    private Integer isDelete;
    private Integer user_id;
    private Integer item_id;
    private Integer orderID;


}
