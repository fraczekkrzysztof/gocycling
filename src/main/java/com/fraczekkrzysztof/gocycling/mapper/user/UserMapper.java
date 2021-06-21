package com.fraczekkrzysztof.gocycling.mapper.user;

import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDto mapUserToUserDto(User user) {
        UserDto.UserDtoBuilder userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName());
        if (!CollectionUtils.isEmpty(user.getExternalAppList())) {
            userDto.externalApps(user.getExternalAppList().stream().map(UserExternalApp::getAppType).collect(Collectors.toList()));
        }
        return userDto.build();
    }

    public User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .build();
    }
}
