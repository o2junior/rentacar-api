package br.com.rentacar.rentacarapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomPage<T> {

    List<T> content;
    CustomPageable pageable;

    public CustomPage(Page<T> page) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(page.getPageable().getPageNumber(),
                page.getPageable().getPageSize(), page.getTotalElements());
    }

    @Data
    class CustomPageable {
        int pageNumber;
        int pageSize;
        long totalElements;

        public CustomPageable(int pageNumber, int pageSize, long totalElements) {
            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalElements = totalElements;
        }
    }
}
