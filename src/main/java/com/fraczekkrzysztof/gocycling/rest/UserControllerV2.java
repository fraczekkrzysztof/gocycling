package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserResponseDto;
import com.fraczekkrzysztof.gocycling.service.UserServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/")
public class UserControllerV2 {

    private final UserServiceV2 userService;

    @GetMapping("/users/{userUid}")
    public ResponseEntity<UserResponseDto> getUserDetails(@PathVariable("userUid") String userUid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponseDto.builder().user(userService.getUserDetails(userUid)).build());
    }

    @PatchMapping("/users/{userUid}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userUid") String userUid, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponseDto.builder().user(userService.updateUser(userUid, userDto)).build());
    }
}
