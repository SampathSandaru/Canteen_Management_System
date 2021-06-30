package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
import com.fot.Canteen_Management_System.Entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item,Integer> {
    @Query(value = "SELECT i FROM Item i WHERE i.isDelete=0")
    Collection<Item> findAll();

//    @Query(value =  "SELECT i FROM Item i WHERE i.quantity>0 AND i.isDelete=0")
//    Collection<Item> findavailableitem();

    @Query(value = "{call  findAvailableItem}",nativeQuery = true)
    List<Item> findavailableitem();

//    @Modifying
//    @Query(value = "UPDATE Item i set i.quantity = i.quantity-:quantity WHERE i.item_id=:item_id")
//    @Transactional

    @Modifying
    @Query(value = "{call  reduceItem(:item_id,:quantity)}",nativeQuery = true)
    @Transactional
    void reduceItem(@Param(value = "item_id")Integer item_id,@Param(value = "quantity")Integer quantity);

}
