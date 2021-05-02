package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void save(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getNewOrder(){
        return (List<OrderItem>) orderItemRepository.getNewOrder();
    }
}
