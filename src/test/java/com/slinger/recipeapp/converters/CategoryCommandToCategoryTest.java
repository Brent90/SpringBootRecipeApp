package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.CategoryCommand;
import com.slinger.recipeapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Id;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;
    final Long ID = 1L;
    final String DESCRIPTION = "Some description";

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertNotNull(category);
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}
