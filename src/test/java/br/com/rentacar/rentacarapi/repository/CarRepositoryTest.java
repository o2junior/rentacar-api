package br.com.rentacar.rentacarapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DataJpaTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


}
