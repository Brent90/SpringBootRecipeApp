package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.UnitOfMeasureCommand;
import com.slinger.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.slinger.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.slinger.recipeapp.domain.UnitOfMeasure;
import com.slinger.recipeapp.repositories.UnitOfMeasureRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {


    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    UnitOfMeasureService service;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand, unitOfMeasureCommandToUnitOfMeasure);
    }

    @Test
    void listAllUom() {   //given
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        //when
        Set<UnitOfMeasureCommand> commands = service.listAllUom();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
        verify(unitOfMeasureRepository, never()).findByDescription(anyString());

    }
}
