package com.fot.Canteen_Management_System.Services;

//import com.fot.Canteen_Management_System.Entity.Invoices;
import com.fot.Canteen_Management_System.Entity.Daysummry;
import com.fot.Canteen_Management_System.Entity.OrderItem;
//import com.fot.Canteen_Management_System.Repository.InvoicesRepository;
import com.fot.Canteen_Management_System.Repository.DaysummaryRepository;
import com.fot.Canteen_Management_System.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoicesService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DaysummaryRepository daysummaryRepository;

    public void save(Integer orderid){

        if(orderItemRepository.findById(orderid).isPresent()){
            OrderItem orderItem=orderItemRepository.findById(orderid).get();
            orderItem.setStatus(1);
            orderItemRepository.save(orderItem);
        }

    }

    public List<Daysummry> getsummary(){
        return (List<Daysummry>) daysummaryRepository.findAll();
    }

}
