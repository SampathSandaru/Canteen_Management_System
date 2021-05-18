package com.fot.Canteen_Management_System.Services;



import com.fot.Canteen_Management_System.Dto.OrderItemDto;
import com.fot.Canteen_Management_System.Entity.OrderItem;

import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import com.fot.Canteen_Management_System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    public void save(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getNewOrder(){
        return (List<OrderItem>) orderItemRepository.getNewOrder();
    }

    public  List< OrderItemDto> getdata(){
        return orderItemRepository.getorder();
    }
}
