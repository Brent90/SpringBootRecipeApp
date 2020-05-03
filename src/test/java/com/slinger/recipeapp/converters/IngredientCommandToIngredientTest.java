package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.UnitOfMeasureCommand;
import com.slinger.recipeapp.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient converter;
    final Long INGREDIENT_ID = 1L;
    final String DESCRIPTION = "flour";
    final BigDecimal AMOUNT = new BigDecimal(1);
    final Long UOM_ID = 3L;

    @BeforeEach
    void setUp() {
        converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setIngredientDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUom(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertEquals(INGREDIENT_ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getIngredientDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    void convertNullUOM() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setIngredientDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);

        //not setting makes it null
        //ingredientCommand.setUom(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(INGREDIENT_ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getIngredientDescription());
        assertEquals(AMOUNT, ingredient.getAmount());
    }

}























