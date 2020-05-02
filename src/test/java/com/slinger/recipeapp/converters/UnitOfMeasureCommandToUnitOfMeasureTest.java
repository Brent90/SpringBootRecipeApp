package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.UnitOfMeasureCommand;
import com.slinger.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    UnitOfMeasureCommandToUnitOfMeasure converter;
    final Long ID = 3L;
    final String DESCRIPTION = "cup";

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        //then
        UnitOfMeasure uom = converter.convert(unitOfMeasureCommand);

        //when
        assertNotNull(uom);
        assertEquals(ID, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());
    }
}
