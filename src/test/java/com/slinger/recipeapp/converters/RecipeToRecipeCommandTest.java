package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.domain.*;
import com.sun.xml.bind.v2.model.core.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    RecipeToRecipeCommand converter;
    final Long RECIPE_ID = 2L;
    final String DESCRIPTION ="Spicy Pizza";
    final Integer PREP_TIME = 20;
    final Integer COOK_TIME = 25;
    final Integer SERVINGS = 5;
    final String SOURCE = "recipes.com";
    final String URL = "www.recipes.com/fresh-pizza";
    final String DIRECTIONS = "directions...";
    final Difficulty DIFFICULTY = Difficulty.MEDIUM;

    final Long NOTES_ID = 1L;
    final String NOTES_DESCRIPTION = "some notes for the recipe";
    final Long CAT_ID_1 = 1L;
    final Long CAT_ID_2 = 2L;
    final Long ING_ID_1 = 10L;
    final Long ING_ID_2 = 3L;

    @BeforeEach
    void setUp() {
        converter = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                    new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                    new CategoryToCategoryCommand());
    }

    @Test
    void tesNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        notes.setRecipeNotes(NOTES_DESCRIPTION);
        recipe.setNotes(notes);

        Category category_1 = new Category();
        category_1.setId(CAT_ID_1);
        recipe.getCategories().add(category_1);

        Category category_2 = new Category();
        category_2.setId(CAT_ID_2);
        recipe.getCategories().add(category_2);

        Ingredient ingredient_1 = new Ingredient();
        ingredient_1.setId(ING_ID_1);
        recipe.addIngredient(ingredient_1);

        Ingredient ingredient_2 = new Ingredient();
        ingredient_2.setId(ING_ID_2);
        recipe.addIngredient(ingredient_2);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertNotNull(recipeCommand);
        assertNotNull(recipeCommand.getIngredients());
        assertNotNull(recipeCommand.getCategories());
        assertEquals(RECIPE_ID, recipeCommand.getId());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
        assertEquals(NOTES_DESCRIPTION, recipeCommand.getNotes().getRecipeNotes());

    }
}
