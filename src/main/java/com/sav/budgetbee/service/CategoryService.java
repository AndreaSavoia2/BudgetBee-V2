package com.sav.budgetbee.service;

import com.sav.budgetbee.entity.Category;
import com.sav.budgetbee.entity.User;
import com.sav.budgetbee.entity.enumeration.TransactionType;
import com.sav.budgetbee.exception.GenericException;
import com.sav.budgetbee.exception.ResourceNotFoundException;
import com.sav.budgetbee.payload.request.CategoryRequest;
import com.sav.budgetbee.repository.CategoryRepository;
import com.sav.budgetbee.utils.AdaptString;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public String createCategory(CategoryRequest request, UserDetails userDetails){
        User user = (User) userDetails;
        String categoryName = AdaptString.capitalizeFirstLetter(request.getCategoryName().trim());
        String transactionType = request.getTransactionType().trim().toUpperCase();
        if (transactionType.equals("NEGATIVE") || transactionType.equals("POSITIVE")){
            TransactionType newTransactionType = TransactionType.valueOf(transactionType);
            if (categoryRepository.existsByCategoryNameAndUserId(categoryName,user.getId()))
                throw new GenericException("This category already exist for this user", HttpStatus.CONFLICT);
            Category category = new Category(categoryName,newTransactionType, user);
            categoryRepository.save(category);
        }else {
            throw new GenericException("transaction type must be NEGATIVE or POSITIVE", HttpStatus.BAD_REQUEST);
        }

        return "Category created";
    }

    public Set<Category> findAllCategoryByUser(UserDetails userDetails){
        return categoryRepository.findAllByUserUsername(userDetails.getUsername());
    }

    protected Category findByCategoryNameAndUserId(String categoryName, long id){
        String newCategoryName = AdaptString.capitalizeFirstLetter(categoryName.trim());
        return categoryRepository.findByCategoryNameAndUserId(newCategoryName, id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryName", categoryName));
    }
}
