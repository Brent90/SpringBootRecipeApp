package com.slinger.recipeapp.services;

import com.slinger.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

}
