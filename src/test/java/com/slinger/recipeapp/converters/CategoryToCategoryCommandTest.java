package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.CategoryCommand;
import com.slinger.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand converter;
    final Long ID = 2L;
    final String DESCRIPTION = "description goes here";

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertNotNull(categoryCommand);
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }
}
