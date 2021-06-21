package com.fraczekkrzysztof.gocycling.dto.user;

import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private String id;
    private String name;
    private List<ExternalApps> externalApps;
}
