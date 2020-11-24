package com.fraczekkrzysztof.gocycling.mapper.user;

import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .externalApps(user.getExternalAppList().stream().map(UserExternalApp::getAppType).collect(Collectors.toList()))
                .build();
    }
}
