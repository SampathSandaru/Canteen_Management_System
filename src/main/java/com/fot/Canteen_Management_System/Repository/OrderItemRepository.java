package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
//    @Query(value = "SELECT n From OrderItem n WHERE n.Status=0")
//    Collection<OrderItem> getNewOrder();

    @Query(value="SELECT new com.fot.Canteen_Management_System.Dto.OrderItemDto(u.name,o.quantity,i.img_path,o.price,u.mobile,i.Item_name,o.Order_time,o.Status) FROM User u,OrderItem o,Item i WHERE o.u_id=u.id and i.item_id=o.item_id and o.Status=0")
    public List<OrderItemDto> getorder();

    @Query(value="SELECT new com.fot.Canteen_Management_System.Dto.OrderItemDto(u.name,o.quantity,i.img_path,o.price,u.mobile,i.Item_name,o.Order_time,o.Status) FROM User u,OrderItem o,Item i WHERE o.u_id=u.id and i.item_id=o.item_id and u.id=?1")
    public List<OrderItemDto> getorderHistory(Integer id);

    @Query(value = "SELECT o From OrderItem o WHERE o.Status=0")
    Collection<OrderItem> newOrderCount();

}
