package com.sav.budgetbee.service;

import com.sav.budgetbee.entity.Category;
import com.sav.budgetbee.entity.Transaction;
import com.sav.budgetbee.entity.User;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import com.sav.budgetbee.exception.GenericException;
import com.sav.budgetbee.exception.ResourceNotFoundException;
import com.sav.budgetbee.payload.request.TransactionRequest;
import com.sav.budgetbee.payload.response.SumByCategoryResponse;
import com.sav.budgetbee.payload.response.TransactionResponse;
import com.sav.budgetbee.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;
    private final TransactionSumService transactionSumService;

    public TransactionResponse createTransaction(UserDetails userDetails, TransactionRequest transactionRequest){
        User user = (User) userDetails;
        Category category = categoryService.findByCategoryNameAndUserId(transactionRequest.getTransactionCategory(),user.getId());

        if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new GenericException("the amount must be greater than 0 " , HttpStatus.BAD_REQUEST);

        BigDecimal amount = category.getTransactionType().name().equals("NEGATIVE") ? transactionRequest.getAmount().negate() : transactionRequest.getAmount();

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .title(transactionRequest.getTitle())
                .description(transactionRequest.getDescription())
                .transactionDate(transactionRequest.getTransactionDate())
                .user(user)
                .category(category)
                .build();
        transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .transactionType(transaction.getCategory().getTransactionType())
                .transactionCategory(transaction.getCategory().getCategoryName())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    public List<TransactionResponse> findAllTransactionByUserId(UserDetails userDetails){
        User user = (User) userDetails;
        return transactionRepository.findAllTransactionByUserId(user.getId());
    }

    public BigDecimal getTransactionSumByIdUser(UserDetails userDetails){
        User user = (User) userDetails;
        return transactionSumService.getTransactionSumByIdUser(user.getId());
    }

    public BigDecimal findTransactionSumByTransactionCategory(UserDetails userDetails, TransactionType transactionType){
        User user = (User) userDetails;
        return transactionRepository.findTransactionSumByTransactionCategory(transactionType, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", user.getId()));
    }

    public List<SumByCategoryResponse> sumTransactionsByCategory (UserDetails userDetails) {
        User user = (User) userDetails;
        return transactionRepository.sumTransactionsByCategory(user.getId());
    }

    public List<SumByCategoryResponse> sumTransactionsByCategoryAndTransactionCategory (UserDetails userDetails, TransactionType transactionType) {
        User user = (User) userDetails;
        return transactionRepository.sumTransactionsByCategoryAndTransactionCategory(transactionType, user.getId());
    }

}
