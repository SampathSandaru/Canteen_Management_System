package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.Daysummry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysummaryRepository extends CrudRepository<Daysummry,Integer> {
}
