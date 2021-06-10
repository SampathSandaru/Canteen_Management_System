package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Dto.UserPwd;
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

    @Query(value = "SELECT new com.fot.Canteen_Management_System.Dto.UserPwd(c.chang_pwd_date) FROM ChangePwdLog c WHERE c.user_id=?1 order by c.chang_pwd_date desc")
    public List<UserPwd> pwdchnagedate(Integer id);

    @Query(value ="SELECT u FROM User u WHERE u.approve=0")
    public List<User> allnewuser();

    @Query(value ="SELECT u FROM User u WHERE u.approve=1")
    public List<User> allnuser();
}
