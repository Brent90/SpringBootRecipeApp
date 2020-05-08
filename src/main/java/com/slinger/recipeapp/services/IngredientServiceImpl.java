package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.converters.IngredientCommandToIngredient;
import com.slinger.recipeapp.converters.IngredientToIngredientCommand;
import com.slinger.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.slinger.recipeapp.domain.Ingredient;
import com.slinger.recipeapp.domain.Recipe;
import com.slinger.recipeapp.repositories.IngredientRepository;
import com.slinger.recipeapp.repositories.RecipeRepository;
import com.slinger.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;



    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 UnitOfMeasureCommandToUnitOfMeasure uomConverter) {

        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomConverter = uomConverter;
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

    @Override
    public IngredientCommand saveOrUpdateIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe not found with id " + ingredientCommand.getRecipeId());
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> optionalIngredient = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

                if(!optionalIngredient.isPresent()) { //need to add the ingredient
                    Ingredient newIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
                    newIngredient.setRecipe(recipe);
                    recipe.addIngredient(newIngredient);
                } else{ //need to update the ingredient
                    Ingredient foundIngredient = optionalIngredient.get();
                    foundIngredient.setAmount(ingredientCommand.getAmount());
                    foundIngredient.setIngredientDescription(ingredientCommand.getIngredientDescription());
                    foundIngredient.setUom(uomConverter.convert(ingredientCommand.getUom()));


                }

                Recipe savedRecipe = recipeRepository.save(recipe);

                Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                        .stream()
                        .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                        .findFirst();


                return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }


}



























