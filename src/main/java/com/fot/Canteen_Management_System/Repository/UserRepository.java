package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.ChangePwdLog;
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

    @Query(value = "SELECT c FROM ChangePwdLog c WHERE c.user_id=?1 order by c.chang_pwd_date desc")
//    @Query(value ="{ call pwdchangedate(:id)}",nativeQuery = true)
    public List<ChangePwdLog> pwdchnagedate(Integer id);

    @Query(value ="{ call allnewuser}",nativeQuery = true)
    public List<User> allnewuser();

    @Query(value ="{ call users}",nativeQuery = true)
    public List<User> allnuser();

    User findByemail(String email);
}
