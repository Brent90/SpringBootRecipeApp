package com.slinger.recipeapp.services;

import com.slinger.recipeapp.commands.UnitOfMeasureCommand;
import com.slinger.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.slinger.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.slinger.recipeapp.domain.UnitOfMeasure;
import com.slinger.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand uomConverter, UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uomConverter = uomConverter;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUom() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(uomConverter::convert)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public UnitOfMeasureCommand saveUnitOfMeasureCommand(UnitOfMeasureCommand unitOfMeasureCommand) {
        UnitOfMeasure detachedUom = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);
        UnitOfMeasure savedUom = unitOfMeasureRepository.save(detachedUom);
        return uomConverter.convert(savedUom);
    }
}
