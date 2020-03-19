package com.xpadro.bookrental.repository;

import com.xpadro.bookrental.entity.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long>,  RentalRepositoryCustom {

    List<Rental> findByUserId(String userId);

}
