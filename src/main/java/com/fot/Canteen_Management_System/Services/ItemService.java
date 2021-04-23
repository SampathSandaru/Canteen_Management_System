package com.fot.Canteen_Management_System.Services;

import com.fot.Canteen_Management_System.Entity.Item;
import com.fot.Canteen_Management_System.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public void save(Item item){
        itemRepository.save(item);
    }

    public List<Item> getAllItem(){
        return (List<Item>) itemRepository.findAll();
    }

    public Integer allItemCount(){
       List<Item> item= (List<Item>) itemRepository.findAll();
       return item.size();
    }

    public void delete(Integer id){
        itemRepository.deleteById(id);
    }

    public void update(Integer id,Item item){
        Item newitem=itemRepository.findById(id).get();
        if(itemRepository.findById(id).isPresent()){
            newitem.setItem_name(item.getItem_name());
            newitem.setPrice(item.getPrice());
            newitem.setQuantity(item.getQuantity());

            itemRepository.save(newitem);
        }
    }

//    public List<Item> getuserItem(){
//        return (List<Item>) itemRepository.findavailableitem();
//    }
}
