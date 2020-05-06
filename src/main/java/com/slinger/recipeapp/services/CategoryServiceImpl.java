package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.CategoryCommand;
import com.slinger.recipeapp.converters.CategoryToCategoryCommand;
import com.slinger.recipeapp.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<CategoryCommand> findAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> findDescriptions() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false).
                map(category -> category.getDescription()).collect(Collectors.toSet());
    }
}
