package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
    @Query(value = "SELECT n From OrderItem n WHERE n.Status=0")
    Collection<OrderItem> getNewOrder();

//    @Query("SELECT o,u FROM OrderItem o JOIN o.User u");
//    public List<OrderItem> getorder();
}
