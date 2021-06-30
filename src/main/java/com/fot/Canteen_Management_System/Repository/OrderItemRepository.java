package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Dto.OrderItemDto;
import com.fot.Canteen_Management_System.Entity.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {

    @Query(value="SELECT new com.fot.Canteen_Management_System.Dto.OrderItemDto(u.name,o.quantity,i.img_path,o.price,u.mobile,i.Item_name,o.Order_time,o.Status,i.isDelete,u.id,i.item_id,o.id) FROM User u left join OrderItem o on o.u_id=u.id left join Item i  on i.item_id=o.item_id WHERE o.Status=0 order by o.Order_time DESC")
    public List<OrderItemDto> getorder();

    @Query(value="SELECT new com.fot.Canteen_Management_System.Dto.OrderItemDto(u.name,o.quantity,i.img_path,o.price,u.mobile,i.Item_name,o.Order_time,o.Status,i.isDelete,u.id,i.item_id,o.id) FROM User u,OrderItem o,Item i WHERE o.u_id=u.id and i.item_id=o.item_id and u.id=?1 ORDER BY o.Order_time DESC")
    public List<OrderItemDto> getorderHistory(Integer id);

    @Query(value = "SELECT o From OrderItem o WHERE o.Status=0")
    Collection<OrderItem> newOrderCount();

//    @Transactional
//    @Procedure(procedureName = "getOrder")
//    List<OrderItem> getorders();

//    @Transactional
//    @Procedure(procedureName = "getOrder2")
//    List<OrderItem> getorderHistory();

//    @Query(value = "{call  getOrderHistoryDetails(:id)}",nativeQuery = true)
//    List<OrderItemDto> getorderHistory(Integer id);


}
