package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Synchronized
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {

        if(ingredientCommand == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setIngredientDescription(ingredientCommand.getIngredientDescription());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setUom(uomConverter.convert(ingredientCommand.getUom()));

        return ingredient;
    }
}
