package com.sav.budgetbee.controller;

import com.sav.budgetbee.payload.request.CategoryRequest;
import com.sav.budgetbee.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("v1/categories")
    public ResponseEntity<?> createCategory(
            @RequestBody @Valid CategoryRequest categoryRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryRequest, userDetails));
    }

    @GetMapping("v1/categories")
    public ResponseEntity<?> findAllCategoryByUser (@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(categoryService.findAllCategoryByUser(userDetails));
    }
}
