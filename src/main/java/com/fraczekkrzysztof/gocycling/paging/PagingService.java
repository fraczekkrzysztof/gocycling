package com.fraczekkrzysztof.gocycling.paging;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PagingService {

    public PageDto generatePageInfo(Page page) {
        PageDto.PageDtoBuilder pageBuilder = PageDto.builder()
                .pageSize(page.getSize())
                .thisPage(page.getNumber())
                .lastPage(page.getTotalPages() - 1);

        if (page.hasNext()) {
            return pageBuilder.nextPage(page.nextPageable().getPageNumber()).build();
        }
        return pageBuilder.build();
    }
}
