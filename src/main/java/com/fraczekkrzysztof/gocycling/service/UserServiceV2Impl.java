package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
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
    public UserDto getUserDetails(String userUid) {
        User user = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of if %s", userUid)));
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(String userUid, UserDto userDto) {
        User user = userRepository.findById(userUid)
                .orElseThrow(() -> new NoSuchElementException(String.format("There is no user of if %s", userUid)));
        updateUserFields(user, userDto);
        userRepository.save(user);
        return userMapper.mapUserToUserDto(user);
    }

    @Override
    public List<RouteDto> getUserExternalRoutes(String userUid) {
        return routesRetriever.getExternalRoutes(userUid);
    }

    private void updateUserFields(User user, UserDto userDto) {
        user.setName(userDto.getName());
    }
}
