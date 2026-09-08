package com.sav.budgetbee.controller;

import com.sav.budgetbee.entity.Transaction;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import com.sav.budgetbee.payload.request.TransactionRequest;
import com.sav.budgetbee.payload.response.SumByCategoryResponse;
import com.sav.budgetbee.payload.response.TransactionResponse;
import com.sav.budgetbee.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("v1/transactions")
    public ResponseEntity<?> createTransaction(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid TransactionRequest transactionRequest
    ) {
        TransactionResponse result = transactionService.createTransaction(userDetails, transactionRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("v1/transactions")
    public ResponseEntity<?> findAllTransactionByUserId(@AuthenticationPrincipal UserDetails userDetails){
        List<TransactionResponse> result = transactionService.findAllTransactionByUserId(userDetails);
        return ResponseEntity.ok(result);
    }

    @GetMapping("v1/transactions/sum")
    public ResponseEntity<?> getTransactionSumByIdUser(@AuthenticationPrincipal UserDetails userDetails){
        BigDecimal result = transactionService.getTransactionSumByIdUser(userDetails);
        return ResponseEntity.ok(result);
    }

    @GetMapping("v1/transactions/sum/{transactionType}")
    public ResponseEntity<?> findTransactionSumByTransactionCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable TransactionType transactionType
            ){
        BigDecimal result = transactionService.findTransactionSumByTransactionCategory(userDetails, transactionType);
        return ResponseEntity.ok(result);
    }

    @GetMapping("v1/transactions/sum-by-category")
    public ResponseEntity<?> sumTransactionsByCategory(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        List<SumByCategoryResponse> result = transactionService.sumTransactionsByCategory(userDetails);
        return ResponseEntity.ok(result);
    }

    @GetMapping("v1/transactions/sum-by-category/{transactionType}")
    public ResponseEntity<?> sumTransactionsByCategoryAndTransactionCategory(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable TransactionType transactionType
    ){
        List<SumByCategoryResponse> result = transactionService.sumTransactionsByCategoryAndTransactionCategory(userDetails, transactionType);
        return ResponseEntity.ok(result);
    }
}
