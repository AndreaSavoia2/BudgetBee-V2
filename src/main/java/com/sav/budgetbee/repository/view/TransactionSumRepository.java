package com.sav.budgetbee.repository.view;

import com.sav.budgetbee.entity.view.TransactionSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TransactionSumRepository extends JpaRepository<TransactionSum, Long> {

    @Query("SELECT ts.budget FROM TransactionSum ts WHERE ts.idUser = :idUser")
    Optional<BigDecimal> getTransactionSumByIdUser(long idUser);
}
