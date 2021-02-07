package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.route.RouteDto;
import com.fraczekkrzysztof.gocycling.dto.route.RouteListResponseDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserDto;
import com.fraczekkrzysztof.gocycling.dto.user.UserResponseDto;
import com.fraczekkrzysztof.gocycling.entity.ExternalApps;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.entity.UserExternalApp;
import com.fraczekkrzysztof.gocycling.external.AllExternalRoutesRetriever;
import com.fraczekkrzysztof.gocycling.mapper.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserMapper userMapper;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AllExternalRoutesRetriever routesRetriever;
    @Autowired
    UserServiceV2 userService;

    @Test
    void shouldProperlyMappedUserInformationAndReturnedUserDto() {
        //given
        UserExternalApp userExternalApp = UserExternalApp.builder()
                .accessToken("SomeAccessToken")
                .refreshToken("SomeRefreshTOken")
                .appUserId(123453L)
                .appType(ExternalApps.STRAVA)
                .expiresAt(Instant.now().getEpochSecond())
                .build();
        User givenUser = User.builder().id("123").name("Testowy Janusz").externalAppList(Arrays.asList(userExternalApp)).build();
        when(userRepository.findById("123")).thenReturn(Optional.of(givenUser));
        UserDto expectedUser = UserDto.builder()
                .id("123")
                .name("Testowy Janusz")
                .externalApps(Arrays.asList(ExternalApps.STRAVA))
                .build();

        //when
        UserResponseDto userResponse = userService.getUserDetails("123");

        //then
        assertThat(userResponse.getUser()).isEqualTo(expectedUser);
    }

    @Test
    void shouldCreateUser() {
        //given
        UserDto givenUserToCreate = UserDto.builder()
                .id("456")
                .name("Testowy Sylwester").build();
        UserDto expectedCreatedUser = UserDto.builder()
                .id("456")
                .name("Testowy Sylwester").build();

        //when
        UserResponseDto createdUser = userService.createUser(givenUserToCreate);

        //then
        assertThat(createdUser.getUser()).isEqualTo(expectedCreatedUser);
    }

    @Test
    void shouldUpdateUser() {
        //given
        UserDto givenDataToUpdateUser = UserDto.builder()
                .id("789")
                .name("Janusz Brzęczyszczykiewicz")
                .build();
        User userToUpdate = User.builder()
                .id("789")
                .name("Janusz B")
                .build();
        when(userRepository.findById("789")).thenReturn(Optional.of(userToUpdate));
        UserDto expectedUpdatedUser = UserDto.builder()
                .id("789")
                .name("Janusz Brzęczyszczykiewicz")
                .build();

        //when
        UserResponseDto updatedUser = userService.updateUser("789", givenDataToUpdateUser);

        //then
        assertThat(updatedUser.getUser()).isEqualTo(expectedUpdatedUser);
    }

    @Test
    void shouldReturnUserExternalRoutesList() {
        //given
        RouteDto route1 = RouteDto.builder()
                .appType(ExternalApps.STRAVA)
                .created(12345L)
                .elevation("300m")
                .length("35km")
                .link("https://strava.com/route/12345")
                .name("Moja trasa 1")
                .build();
        RouteDto route2 = RouteDto.builder()
                .appType(ExternalApps.STRAVA)
                .created(7890L)
                .elevation("500m")
                .length("60km")
                .link("https://strava.com/route/67890")
                .name("Moja trasa 2")
                .build();
        List<RouteDto> givenRoutesList = Arrays.asList(route1, route2);
        List<RouteDto> expectedRoutesList = Arrays.asList(route1, route2);
        when(routesRetriever.getExternalRoutes("456")).thenReturn(givenRoutesList);

        //when
        RouteListResponseDto receivedRoutes = userService.getUserExternalRoutes("456");

        //then
        assertThat(receivedRoutes.getRoutes()).isEqualTo(expectedRoutesList);
    }
}
