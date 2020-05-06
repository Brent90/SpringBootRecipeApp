package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.converters.IngredientCommandToIngredient;
import com.slinger.recipeapp.converters.IngredientToIngredientCommand;
import com.slinger.recipeapp.domain.Ingredient;
import com.slinger.recipeapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientRepository ingredientRepository;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientRepository ingredientRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Ingredient detachedIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
        Ingredient savedIngredient = ingredientRepository.save(detachedIngredient);
        return ingredientToIngredientCommand.convert(savedIngredient);
    }
}
