package com.fraczekkrzysztof.gocycling.service;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.dto.club.*;
import com.fraczekkrzysztof.gocycling.entity.Club;
import com.fraczekkrzysztof.gocycling.entity.Member;
import com.fraczekkrzysztof.gocycling.entity.User;
import com.fraczekkrzysztof.gocycling.mapper.club.ClubMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ClubServiceTests {

    @MockBean
    ClubRepository clubRepository;
    @MockBean
    UserRepository userRepository;

    @Autowired
    ClubMapper clubMapper;

    @Autowired
    ClubServiceV2 clubService;

    List<Club> fakeClubList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        prepareFakeCLubList();
    }

    private void prepareFakeCLubList() {
        User user1 = User.builder()
                .id("123")
                .name("Testowy Jan").build();
        User user2 = User.builder()
                .id("456")
                .name("Testowy Edward").build();
        User user3 = User.builder()
                .id("789")
                .name("Testowy Wiesław").build();

        Member user1OfClub1 = Member.builder()
                .id(1L)
                .user(user1)
                .confirmed(true)
                .build();

        Member user2OfClub1 = Member.builder()
                .id(2L)
                .user(user2)
                .confirmed(true)
                .build();

        Member user3OfClub1 = Member.builder()
                .id(3L)
                .user(user3)
                .confirmed(true)
                .build();

        Member user1OfClub2 = Member.builder()
                .id(4L)
                .user(user1)
                .confirmed(true)
                .build();

        Member user2OfClub2 = Member.builder()
                .id(5L)
                .user(user2)
                .confirmed(true)
                .build();

        Member user3OfClub3 = Member.builder()
                .id(6L)
                .user(user3)
                .confirmed(true)
                .build();

        Club club1 = Club.builder()
                .id(1L)
                .name("Club1")
                .created(LocalDateTime.of(2020, 12, 01, 12, 00))
                .location("LocationOfClub1")
                .details("Super Club 1")
                .longitude(12.1)
                .latitude(14.1)
                .user(user1)
                .memberList(new ArrayList<>())
                .privateMode(false)

                .build();

        Club club2 = Club.builder()
                .id(2L)
                .name("Club2")
                .created(LocalDateTime.of(2020, 11, 01, 12, 00))
                .location("LocationOfClub2")
                .details("Super Club 2")
                .longitude(18.1)
                .latitude(19.1)
                .user(user2)
                .memberList(new ArrayList<>())
                .privateMode(false)
                .build();

        Club club3 = Club.builder()
                .id(3L)
                .name("Club3")
                .created(LocalDateTime.of(2020, 11, 15, 12, 00))
                .location("LocationOfClub2")
                .details("Super Club 3")
                .longitude(21.1)
                .latitude(22.1)
                .user(user3)
                .memberList(new ArrayList<>())
                .privateMode(false)
                .build();

        userRepository.saveAll(Arrays.asList(user1, user2, user3));
        clubRepository.saveAll(Arrays.asList(club1, club2, club3));

        club1.getMemberList().addAll(Arrays.asList(user1OfClub1, user2OfClub1, user3OfClub1));
        club2.getMemberList().addAll(Arrays.asList(user1OfClub2, user2OfClub2));
        club3.getMemberList().addAll(Arrays.asList(user3OfClub3));
        fakeClubList.addAll(Arrays.asList(club1, club2, club3));
    }

    @Test
    void shouldReturnClubsDtoListWhenAtLeastOneClubIsInRepository() {
        //given
        ClubDto club1 = ClubDto.builder()
                .id(1L)
                .name("Club1")
                .created(LocalDateTime.of(2020, 12, 01, 12, 00))
                .location("LocationOfClub1")
                .details("Super Club 1")
                .longitude(12.1)
                .latitude(14.1)
                .ownerId("123")
                .ownerName("Testowy Jan")
                .privateMode(false)
                .build();

        ClubDto club2 = ClubDto.builder()
                .id(2L)
                .name("Club2")
                .created(LocalDateTime.of(2020, 11, 01, 12, 00))
                .location("LocationOfClub2")
                .details("Super Club 2")
                .longitude(18.1)
                .latitude(19.1)
                .ownerId("456")
                .ownerName("Testowy Edward")
                .privateMode(false)
                .build();

        ClubDto club3 = ClubDto.builder()
                .id(3L)
                .name("Club3")
                .created(LocalDateTime.of(2020, 11, 15, 12, 00))
                .location("LocationOfClub2")
                .details("Super Club 3")
                .longitude(21.1)
                .latitude(22.1)
                .ownerId("789")
                .ownerName("Testowy Wiesław")
                .privateMode(false)
                .build();

        when(clubRepository.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl(fakeClubList));

        //when
        ClubListResponse returnedList = clubService.getAllClubs(PageRequest.of(0, 20));
        //then
        assertThat(returnedList.getClubs()).containsExactlyInAnyOrder(club1, club2, club3);
    }

    @Test
    void shouldReturnClubDtoWithMembershipList() {
        //given
        MemberDto user1OfClub1 = MemberDto.builder()
                .id(1L)
                .userUid("123")
                .userName("Testowy Jan")
                .confirmed(true)
                .build();

        MemberDto user2OfClub1 = MemberDto.builder()
                .id(2L)
                .userUid("456")
                .userName("Testowy Edward")
                .confirmed(true)
                .build();

        MemberDto user3OfClub1 = MemberDto.builder()
                .id(3L)
                .userUid("789")
                .userName("Testowy Wiesław")
                .confirmed(true)
                .build();

        ClubDto club1 = ClubDto.builder()
                .id(1L)
                .name("Club1")
                .created(LocalDateTime.of(2020, 12, 01, 12, 00))
                .location("LocationOfClub1")
                .details("Super Club 1")
                .longitude(12.1)
                .latitude(14.1)
                .ownerId("123")
                .ownerName("Testowy Jan")
                .memberList(Arrays.asList(user1OfClub1, user2OfClub1, user3OfClub1))
                .privateMode(false)
                .build();

        when(clubRepository.findById(1L)).thenReturn(fakeClubList.stream().filter(c -> c.getId() == 1L).findFirst());

        //when
        ClubResponse returnedClub = clubService.getClubById(1L);
        //then
        assertThat(returnedClub.getClub())
                .isEqualTo(club1);
        assertThat(returnedClub.getClub().getMemberList())
                .containsExactlyInAnyOrder(user1OfClub1, user2OfClub1, user3OfClub1);

    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenThereIsNoClubInDatabase() {
        //given
        long notExistingId = 99L;
        //when
        Throwable thrown = catchThrowable(() -> clubService.getClubById(notExistingId));
        //then
        assertThat(thrown)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(String.format("There is no club of id %d", notExistingId));
    }

    @Test
    void shouldReturnClubDtoAfterSuccessfullySaveToDatabase() {
        //given
        User owner = User.builder().id("999").name("Janusz").build();
        ClubDto clubToAdd = ClubDto.builder()
                .name("New club")
                .location("SuperClub")
                .details("Our new super club")
                .longitude(18.1)
                .latitude(19.1)
                .ownerId("999")
                .ownerName("Janusz")
                .privateMode(false)
                .build();
        ClubDto expectedClubDto = ClubDto.builder()
                .id(99L)
                .name("New club")
                .created(LocalDateTime.of(2020, 12, 01, 12, 00))
                .location("SuperClub")
                .details("Our new super club")
                .longitude(18.1)
                .latitude(19.1)
                .ownerId("999")
                .ownerName("Janusz")
                .privateMode(false)
                .build();
        when(userRepository.findById("999")).thenReturn(Optional.of(owner));
        //when
        ClubResponse addedClub = clubService.addClub(clubToAdd);
        //then
        assertThat(addedClub.getClub()).usingRecursiveComparison().ignoringFields("created", "id").
                isEqualTo(expectedClubDto);
    }

    @Test
    void shouldAddUserToClub() {
        User mockedUser = mock(User.class);
        when(mockedUser.getName()).thenReturn("Ziom");
        when(mockedUser.getId()).thenReturn("876");
        Member member1 = mock(Member.class);
        List<Member> listBeforeAdd = new ArrayList<>();
        listBeforeAdd.add(member1);
        Member addedMember = mock(Member.class);
        when(addedMember.getUser()).thenReturn(mockedUser);
        when(addedMember.getId()).thenReturn(555L);
        List<Member> listAfterAdd = new ArrayList<>();
        listAfterAdd.addAll(Arrays.asList(member1, addedMember));
        Club mockedClub = mock(Club.class);
        when(mockedClub.getMemberList()).thenReturn(listBeforeAdd, listAfterAdd);
        when(userRepository.findById("666")).thenReturn(Optional.of(mockedUser));
        when(clubRepository.findById(10L)).thenReturn(Optional.of(mockedClub));
        MemberDto expectedMember = MemberDto.builder()
                .confirmed(true)
                .id(555L)
                .userName("Ziom")
                .userUid("876")
                .build();

        //when
        MemberResponse newMember = clubService.addMembership(10, "666");

        //then
        assertThat(newMember.getMember()).isEqualTo(expectedMember);
    }

    @Test
    void shouldDeleteMember() {
        User userWithMembershipToDelete = mock(User.class);
        when(userWithMembershipToDelete.getId()).thenReturn("234");
        Member memberToDelete = mock(Member.class);
        when(memberToDelete.getUser()).thenReturn(userWithMembershipToDelete);
        Member randomMember = mock(Member.class);
        List<Member> listOfMember = new ArrayList<>();
        listOfMember.addAll(Arrays.asList(memberToDelete, randomMember));
        Club mockedClub = mock(Club.class);
        when(mockedClub.getMemberList()).thenReturn(listOfMember);
        when(clubRepository.findById(20L)).thenReturn(Optional.of(mockedClub));

        //when
        Throwable thrown = catchThrowable(() -> clubService.deleteMembership(20, "234"));
        //then
        assertThat(thrown)
                .doesNotThrowAnyException();
    }
}
