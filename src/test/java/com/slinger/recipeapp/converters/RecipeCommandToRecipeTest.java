package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.CategoryCommand;
import com.slinger.recipeapp.commands.IngredientCommand;
import com.slinger.recipeapp.commands.NotesCommand;
import com.slinger.recipeapp.commands.RecipeCommand;
import com.slinger.recipeapp.domain.Difficulty;
import com.slinger.recipeapp.domain.Notes;
import com.slinger.recipeapp.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    RecipeCommandToRecipe converter;
    final Long RECIPE_ID = 2L;
    final String DESCRIPTION ="Fresh Salsa";
    final Integer PREP_TIME = 20;
    final Integer COOK_TIME = 5;
    final Integer SERVINGS = 4;
    final String SOURCE = "recipes.com";
    final String URL = "www.recipes.com/fresh-salsa";
    final String DIRECTIONS = "directions...";
    final Difficulty DIFFICULTY = Difficulty.EASY;

    final Long NOTES_ID = 1L;
    final String NOTES_DESCRIPTION = "some notes for the recipe";
    final Long CAT_ID_1 = 1L;
    final Long CAT_ID_2 = 2L;
    final Long ING_ID_1 = 10L;
    final Long ING_ID_2 = 3L;

    @BeforeEach
    void setUp() {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                    new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                    new CategoryCommandToCategory());
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        notesCommand.setRecipeNotes(NOTES_DESCRIPTION);
        recipeCommand.setNotes(notesCommand);

        CategoryCommand category_1 = new CategoryCommand();
        category_1.setId(CAT_ID_1);
        recipeCommand.getCategories().add(category_1);

        CategoryCommand category_2 = new CategoryCommand();
        category_2.setId(CAT_ID_2);
        recipeCommand.getCategories().add(category_2);

        IngredientCommand ingredient_1 = new IngredientCommand();
        ingredient_1.setId(ING_ID_1);
        recipeCommand.getIngredients().add(ingredient_1);

        IngredientCommand ingredient_2 = new IngredientCommand();
        ingredient_2.setId(ING_ID_2);
        recipeCommand.getIngredients().add(ingredient_2);

        //when
        Recipe recipe = converter.convert(recipeCommand);

        //then
        assertNotNull(recipe);
        assertNotNull(recipe.getCategories());
        assertNotNull(recipeCommand.getIngredients());
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(NOTES_DESCRIPTION, recipe.getNotes().getRecipeNotes());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }


}
