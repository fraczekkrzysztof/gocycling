package com.fraczekkrzysztof.gocycling.mapper.event;

import com.fraczekkrzysztof.gocycling.dto.event.ConfirmationDto;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConfirmationMapper {

    public List<ConfirmationDto> mapConfirmationListToConfirmationDtoList(List<Confirmation> confirmationList) {
        return confirmationList.stream().map(c -> mapConfirmationToConfirmationDto(c)).collect(Collectors.toList());
    }

    public ConfirmationDto mapConfirmationToConfirmationDto(Confirmation confirmation) {
        return ConfirmationDto.builder()
                .id(confirmation.getId())
                .userId(confirmation.getUser().getId())
                .userName(confirmation.getUser().getName())
                .build();
    }
}
