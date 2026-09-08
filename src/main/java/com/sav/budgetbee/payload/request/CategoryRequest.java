package com.sav.budgetbee.payload.request;

import com.sav.budgetbee.entity.enumeration.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CategoryRequest {
    @NotBlank @Size(max = 100)
    private String categoryName;
    private String transactionType;
}
