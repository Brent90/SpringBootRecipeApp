package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;


public interface IngredientService {

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(Long recipeId, Long ingredientId);

    IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand);

    IngredientCommand findIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

}
