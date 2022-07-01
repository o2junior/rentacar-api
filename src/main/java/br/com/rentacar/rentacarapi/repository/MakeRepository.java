package br.com.rentacar.rentacarapi.repository;

import br.com.rentacar.rentacarapi.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MakeRepository extends JpaRepository<Make, Long> {
    Boolean existsByName(String name);
    @Transactional
    Make save(Make make);
}
