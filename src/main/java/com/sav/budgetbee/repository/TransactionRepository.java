package com.sav.budgetbee.repository;

import com.sav.budgetbee.entity.Transaction;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import com.sav.budgetbee.payload.response.SumByCategoryResponse;
import com.sav.budgetbee.payload.response.TransactionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT new com.sav.budgetbee.payload.response.TransactionResponse(" +
            "t.id," +
            "t.amount," +
            "t.title," +
            "t.description," +
            "t.category.transactionType," +
            "t.category.categoryName," +
            "t.transactionDate) FROM Transaction t WHERE t.user.id = :userId")
    List<TransactionResponse> findAllTransactionByUserId(long userId);

    @Query("SELECT sum(t.amount) " +
            "FROM Transaction t " +
            "WHERE t.category.transactionType = :transactionType " +
            "AND t.user.id = :userId")
    Optional<BigDecimal> findTransactionSumByTransactionCategory(TransactionType transactionType, long userId);

    @Query("SELECT new com.sav.budgetbee.payload.response.SumByCategoryResponse" +
            "(t.category.categoryName, sum(t.amount))" +
            "FROM Transaction t " +
            "WHERE t.user.id = :userId " +
            "GROUP BY t.category")
    List<SumByCategoryResponse> sumTransactionsByCategory (Long userId);

    @Query("SELECT new com.sav.budgetbee.payload.response.SumByCategoryResponse" +
            "(t.category.categoryName, sum(t.amount))" +
            "FROM Transaction t " +
            "WHERE t.user.id = :userId " +
            "AND t.category.transactionType = :transactionType " +
            "GROUP BY t.category")
    List<SumByCategoryResponse> sumTransactionsByCategoryAndTransactionCategory (TransactionType transactionType, Long userId);

}
