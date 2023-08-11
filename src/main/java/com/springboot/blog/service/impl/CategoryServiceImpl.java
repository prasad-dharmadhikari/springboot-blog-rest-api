package com.springboot.blog.service.impl;

import com.springboot.blog.configuration.ModelMapperConfiguration;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = new ModelMapperConfiguration().getModelMapper();
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        final var savedCategory = categoryRepository.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        final var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        final var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryId);
        final var updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void removeCategory(Long categoryId) {
        final var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        categoryRepository.deleteById(categoryId);
    }
}
