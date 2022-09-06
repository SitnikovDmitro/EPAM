package com.ra.model.repository;

import com.ra.model.entity.Cheque;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChequeRepository extends CrudRepository<Cheque, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM cheques WHERE price >= ?1 AND price <= ?2 ORDER BY creationTime;")
    List<Cheque> findChequesSortedByCreationTime(Integer fromPrice, Integer toPrice);

    @Query(nativeQuery = true, value = "SELECT * FROM cheques WHERE price >= ?1 AND price <= ?2 ORDER BY price;")
    List<Cheque> findChequesSortedByPrice(Integer fromPrice, Integer toPrice);
}
