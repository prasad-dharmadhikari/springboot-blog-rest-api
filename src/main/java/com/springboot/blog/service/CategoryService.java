package com.springboot.blog.service;

import com.springboot.blog.payload.CategoryDto;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(Long categoryId);



}
