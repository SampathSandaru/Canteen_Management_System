package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.Daysummry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysummaryRepository extends CrudRepository<Daysummry,Integer> {

    @Query(value = "SELECT d FROM Daysummry d ORDER BY d.id desc ")
    List<Daysummry> findAll();
}
