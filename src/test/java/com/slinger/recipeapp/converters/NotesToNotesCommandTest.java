package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.NotesCommand;
import com.slinger.recipeapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    NotesToNotesCommand converter;
    final Long ID = 1L;
    final String RECIPE_NOTES = "notes";

    @BeforeEach
    void setUp() {
        converter = new NotesToNotesCommand();
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID);
        notes.setRecipeNotes(RECIPE_NOTES);

        //then
        NotesCommand notesCommand = converter.convert(notes);

        //when
        assertNotNull(notesCommand);
        assertEquals(ID, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}
