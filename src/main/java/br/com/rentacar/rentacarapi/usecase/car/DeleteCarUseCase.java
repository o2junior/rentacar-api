package br.com.rentacar.rentacarapi.usecase.car;

import br.com.rentacar.rentacarapi.repository.CarRepository;
import br.com.rentacar.rentacarapi.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCarUseCase implements UseCase<UUID, Void> {

    private static final String LOG_PREFIX = "[DELETE-CAR-USE-CASE]";
    private final CarRepository carRepository;

    @Override
    @Transactional
    public Void execute(UUID uuid) {
        carRepository.deleteById(uuid);
        log.info("{} - {} Car deleted...", LOG_PREFIX, uuid.toString());
        return null;
    }

}
