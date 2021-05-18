package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.OrderItem;
import com.fot.Canteen_Management_System.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmailAndPassword(String email,String password);

    @Query(value = "SELECT u FROM User u WHERE u.id=?1")
    List<OrderItem> findByUserId(Integer id);
}
