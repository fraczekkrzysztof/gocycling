package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.dto.route.RouteListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserResponseDto;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.external.AllExternalRoutesRetriever;
import com.fraczekkrzysztof.gocycling.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceV2Impl implements UserServiceV2 {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final AllExternalRoutesRetriever routesRetriever;

    @Override
    public UserResponseDto getUserDetails(String userUid) {
        User user = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of if %s", userUid)));
        UserDto mappedUser = userMapper.mapUserToUserDto(user);
        return UserResponseDto.builder().user(mappedUser).build();
    }

    @Override
    public UserResponseDto createUser(UserDto userDto) {
        User userToAdd = userMapper.mapUserDtoToUser(userDto);
        userRepository.save(userToAdd);
        UserDto addedUser = userMapper.mapUserToUserDto(userToAdd);
        return UserResponseDto.builder().user(addedUser).build();
    }

    @Override
    public UserResponseDto updateUser(String userUid, UserDto userDto) {
        User user = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of if %s", userUid)));
        updateUserFields(user, userDto);
        userRepository.save(user);
        UserDto mappedUpdatedUser = userMapper.mapUserToUserDto(user);
        return UserResponseDto.builder().user(userDto).build();
    }

    @Override
    public RouteListResponseDto getUserExternalRoutes(String userUid) {
        List<RouteDto> userExternalRoutes = routesRetriever.getExternalRoutes(userUid);
        return RouteListResponseDto.builder().routes(userExternalRoutes).build();
    }

    private void updateUserFields(User user, UserDto userDto) {
        user.setName(userDto.getName());
    }
}
