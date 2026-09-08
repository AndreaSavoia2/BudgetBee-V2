package com.sav.budgetbee.payload.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SumByCategoryResponse {

    private String category;
    private BigDecimal sumAmount;

}
