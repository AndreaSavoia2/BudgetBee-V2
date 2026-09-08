package com.sav.budgetbee.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class TransactionRequest {

    private BigDecimal amount;
    @NotBlank @Size(min = 1, max = 100)
    private String title;
    @Size(max = 65535)
    private String description;
    @NotBlank
    private String transactionCategory;
    private LocalDate transactionDate;


}
