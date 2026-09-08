package com.sav.budgetbee.service;

import com.sav.budgetbee.exception.ResourceNotFoundException;
import com.sav.budgetbee.repository.view.TransactionSumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionSumService {

    private final TransactionSumRepository transactionSumRepository;

    protected BigDecimal getTransactionSumByIdUser(long idUser){
        return transactionSumRepository.getTransactionSumByIdUser(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", idUser));
    }
}
