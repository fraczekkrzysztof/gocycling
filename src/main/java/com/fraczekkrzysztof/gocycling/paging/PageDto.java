package com.fraczekkrzysztof.gocycling.paging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageDto {

    private int pageSize;
    private int thisPage;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int nextPage;
    private int lastPage;
}
