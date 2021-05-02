package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item,Integer> {
    @Query(value =  "SELECT i FROM Item i WHERE i.quantity>0")
    Collection<Item> findavailableitem();

    @Modifying
    @Query(value = "UPDATE Item i set i.quantity = i.quantity-:quantity WHERE i.item_id=:item_id")
    @Transactional
    void reduceItem(@Param(value = "quantity")Integer quantity,@Param(value = "item_id")Integer item_id);

}
