package br.com.rentacar.rentacarapi.repository;

import br.com.rentacar.rentacarapi.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    Page<Car> findAllByAvailable(Boolean available, Pageable pageable);

    Page<Car> findAllByModel(String mode, Pageable pageable);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Car save(Car car);

}
