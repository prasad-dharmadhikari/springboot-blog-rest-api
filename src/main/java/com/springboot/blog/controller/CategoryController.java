package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "id") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "id") Long categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteCategory(@PathVariable(value = "id") Long categoryId) {
        categoryService.removeCategory(categoryId);
        ObjectMapper objectMapper = new ObjectMapper();
        var objectNode = objectMapper.createObjectNode();
        objectNode.put("message", "Category deleted successfully...");
        return ResponseEntity.ok(objectNode);
    }
}
