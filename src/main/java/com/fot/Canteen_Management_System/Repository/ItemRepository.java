package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item,Integer> {
//    @Query(value =  "SELECT i FROM Item i WHERE i.quantity>0")
//    Collection<Item> findavailableitem();
}
