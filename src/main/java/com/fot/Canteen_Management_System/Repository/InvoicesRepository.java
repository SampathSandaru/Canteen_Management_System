package com.fot.Canteen_Management_System.Repository;

import com.fot.Canteen_Management_System.Entity.Invoices;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepository extends CrudRepository<Invoices,Integer> {
}
