package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.route.RouteListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserResponseDto;

public interface UserServiceV2 {
    UserResponseDto getUserDetails(String userUid);

    UserResponseDto createUser(UserDto userDto);

    UserResponseDto updateUser(String userUid, UserDto userDto);

    RouteListResponseDto getUserExternalRoutes(String userUid);
}
