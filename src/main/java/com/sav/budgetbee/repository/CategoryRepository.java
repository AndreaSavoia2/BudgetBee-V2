package com.sav.budgetbee.repository;

import com.sav.budgetbee.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Set<Category> findAllByUserUsername(String username);
    boolean existsByCategoryNameAndUserId(String category, long id);
    Optional<Category> findByCategoryNameAndUserId(String categoryName, long id);
}
