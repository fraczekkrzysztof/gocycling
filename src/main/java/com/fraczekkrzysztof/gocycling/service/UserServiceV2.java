package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dto.user.UserDto;

public interface UserServiceV2 {
    UserDto getUserDetails(String userUid);

    UserDto updateUser(String userUid, UserDto userDto);
}
