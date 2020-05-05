package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long recipeId);

    RecipeCommand findByRecipeCommandId(Long recipeId);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

}
