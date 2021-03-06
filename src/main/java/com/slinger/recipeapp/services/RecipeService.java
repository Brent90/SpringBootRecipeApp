package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.domain.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    List<Recipe> getRecipesSorted();

    void deleteRecipeById(Long recipeId);

    Recipe findById(Long recipeId);

    RecipeCommand findByRecipeCommandId(Long recipeId);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand saveIngredientToRecipe(RecipeCommand recipeCommand, IngredientCommand ingredientCommand);

}
