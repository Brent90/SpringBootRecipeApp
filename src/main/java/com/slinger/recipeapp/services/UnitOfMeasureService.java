package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUom();

    UnitOfMeasureCommand saveUnitOfMeasureCommand(UnitOfMeasureCommand unitOfMeasureCommand);
    UnitOfMeasureCommand findByDescription(String description);
}
