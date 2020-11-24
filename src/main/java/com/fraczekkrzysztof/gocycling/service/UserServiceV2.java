package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserDto;

import java.util.List;

public interface UserServiceV2 {
    UserDto getUserDetails(String userUid);

    UserDto updateUser(String userUid, UserDto userDto);

    List<RouteDto> getUserExternalRoutes(String userUid);
}
