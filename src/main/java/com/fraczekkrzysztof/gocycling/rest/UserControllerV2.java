package com.fraczekkrzysztof.gocycling.rest;

import com.fraczekkrzysztof.gocycling.dto.route.RouteListResponseDto;
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
                .body(userService.getUserDetails(userUid));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.createUser(userDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
    }

    @PatchMapping("/users/{userUid}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userUid") String userUid, @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(userUid, userDto));
    }

    @GetMapping("/users/{userUid}/externalRoutes")
    public ResponseEntity<RouteListResponseDto> getExternalRoutes(@PathVariable("userUid") String userUid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserExternalRoutes(userUid));
    }
}
