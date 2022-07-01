package br.com.rentacar.rentacarapi.usecase.make;

import br.com.rentacar.rentacarapi.controller.dto.CustomPage;
import br.com.rentacar.rentacarapi.controller.dto.MakeResponse;
import br.com.rentacar.rentacarapi.model.Make;
import br.com.rentacar.rentacarapi.repository.MakeRepository;
import br.com.rentacar.rentacarapi.usecase.adapter.MakeResponseAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@EnableSpringDataWebSupport
class GetListMakesUseCaseTest {

    @Mock MakeRepository makeRepository;
    @Mock MakeResponseAdapter makeResponseAdapter;

    private GetListMakesUseCase getListMakesUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        this.getListMakesUseCase = new GetListMakesUseCase(makeRepository, makeResponseAdapter);
    }

    @Test
    void getListMakesShouldReturnSuccessfully() {
        // given
        PageRequest pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Make> listMakes = List.of(new Make(), new Make());
        Page<Make> makes = new PageImpl(listMakes, pageable, 2L);

        when(makeRepository.findAll(pageable))
               .thenReturn(makes);
        when(makeResponseAdapter.adapt(new Make()))
               .thenReturn(MakeResponse.builder().build());

        // when
        final CustomPage<MakeResponse> actual = getListMakesUseCase.execute(pageable);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2L, actual.getContent().size());
        Assertions.assertTrue(actual.getContent().stream().allMatch(m -> m instanceof MakeResponse));
    }

    @Test
    void getEmptyListMakesShouldReturnSuccessfully() {
        // given
        PageRequest pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Make> listMakes = new ArrayList<>();
        Page<Make> makes = new PageImpl(listMakes, pageable, 0L);

        when(makeRepository.findAll(pageable))
                .thenReturn(makes);
        when(makeResponseAdapter.adapt(new Make()))
                .thenReturn(MakeResponse.builder().build());

        // when
        final CustomPage<MakeResponse> actual = getListMakesUseCase.execute(pageable);

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(0L, actual.getContent().size());
    }

}
