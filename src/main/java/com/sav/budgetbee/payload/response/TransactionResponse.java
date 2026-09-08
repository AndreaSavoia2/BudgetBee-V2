package com.sav.budgetbee.payload.response;

import com.sav.budgetbee.entity.enumeration.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionResponse {
    private long id;
    private BigDecimal amount;
    private String title;
    private String description;
    private TransactionType transactionType;
    private String transactionCategory;
    private LocalDate transactionDate;


}
