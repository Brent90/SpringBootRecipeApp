package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.converters.IngredientCommandToIngredient;
import com.slinger.recipeapp.converters.IngredientToIngredientCommand;
import com.slinger.recipeapp.domain.Ingredient;
import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.IngredientRepository;
import com.slinger.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;



    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository) {

        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Ingredient detachedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
        Ingredient savedIngredient = ingredientRepository.save(detachedIngredient);
        return ingredientToIngredientCommand.convert(savedIngredient);
    }

    @Override
    public IngredientCommand findIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found with id " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if(!ingredientOptional.isPresent()) {
            throw new RuntimeException("No ingredient found with id " + ingredientId);
        }

        Ingredient foundIngredient = ingredientOptional.get();

        return ingredientToIngredientCommand.convert(foundIngredient);
    }
}



























