package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Entity.Invoices;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Repository.InvoicesRepository;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoicesService {

    @Autowired
    private InvoicesRepository invoicesRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public void save(Invoices invoices){

        if(orderItemRepository.findById(invoices.getOrderID()).isPresent()){
            OrderItem orderItem=orderItemRepository.findById(invoices.getOrderID()).get();
            orderItem.setStatus(1);
            orderItemRepository.save(orderItem);
        }

        invoicesRepository.save(invoices);
    }
}
