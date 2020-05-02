package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.domain.Ingredient;
import com.slinger.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    final Long INGREDIENT_ID = 3L;
    final Long UOM_ID = 1L;
    final String DESCRIPTION = "Onion";
    final BigDecimal AMOUNT = new BigDecimal(2);

    IngredientToIngredientCommand converter;

    @BeforeEach
    void setUp() {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        unitOfMeasure.setDescription("Cup");

        ingredient.setUom(unitOfMeasure);

        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(INGREDIENT_ID, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());

        assertNotNull(ingredientCommand.getUom());
        assertEquals("Cup", ingredientCommand.getUom().getDescription());
    }

    @Test
    void convertWithNullUOM() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        unitOfMeasure.setDescription("Cup");

        //don't set to make sure uom is null
        //ingredient.setUom(unitOfMeasure);

        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(INGREDIENT_ID, ingredientCommand.getId());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(AMOUNT, ingredientCommand.getAmount());

        assertNull(ingredientCommand.getUom());

    }
}
















