package com.slinger.recipeapp.converters;

import com.slinger.recipeapp.commands.NotesCommand;
import com.slinger.recipeapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    NotesCommandToNotes converter;
    final Long ID = 20L;
    final String RECIPE_NOTES = "some notes here";

    @BeforeEach
    void setUp() {
        converter = new NotesCommandToNotes();
    }

    @Test
    void testNullableObject() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = converter.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}
