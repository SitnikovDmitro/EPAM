package com.ra.model.repository;

import com.ra.model.entity.ChequeLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChequeLineRepository extends CrudRepository<ChequeLine, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM chequeLines INNER JOIN products ON chequeLines.productCode = products.code WHERE chequeLines.chequeId = ?1;")
    List<ChequeLine> findChequeLinesByChequeId(Integer id);
}
