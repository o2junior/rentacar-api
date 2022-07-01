package br.com.rentacar.rentacarapi.adapter;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Optional;

public abstract class AbstractAdapter<Source, Target> implements Adapter<Source, Target> {

    private final Class<Target> targetClass;
    protected ModelMapper modelMapper;

    public AbstractAdapter(Class<Target> targetClass) {
        this.targetClass = targetClass;
    }

    protected ModelMapper getModelMapper() {
        if (this.modelMapper == null) {
            this.modelMapper = new ModelMapper();
            this.modelMapper.getConfiguration()
                    .setPropertyCondition(Conditions.isNotNull())
                    .setSkipNullEnabled(true)
                    .setAmbiguityIgnored(true)
                    .setMatchingStrategy(MatchingStrategies.LOOSE);
        }

        return this.modelMapper;
    }

    @Override
    public Target adapt(Source source) {
        return Optional.ofNullable(source)
                .map(sourceObject -> this.getModelMapper().map(sourceObject, targetClass))
                .orElse(null);
    }

    @Override
    public Target adapt(Source source, Target target) {
        return Optional.ofNullable(source)
                .map(sourceObject -> {
                    this.getModelMapper().map(sourceObject, target);
                    return target;
                })
                .orElse(null);
    }

}
