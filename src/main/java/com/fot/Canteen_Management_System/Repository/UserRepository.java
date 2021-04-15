package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
}
