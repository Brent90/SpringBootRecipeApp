package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {

    Set<CategoryCommand> findAll();

    Set<String> findDescriptions();
}
