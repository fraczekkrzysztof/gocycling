package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.ClubRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.MemberRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public  void prepTests(){

    }

    @Test
    public void shouldReturnTwoUsers(){
        Event event = Event.builder().name("Test1").place("TestPlace1").created(LocalDateTime.now()).createdBy("ziutek").dateAndTime(LocalDateTime.now().plusDays(5)).build();
        User user = new User();
        user.setId("asdf");
        user.setName("Jan");
        User user2 = new User();
        user2.setId("qwer");
        user2.setName("Ziutek");
        Confirmation confirmation = Confirmation.builder().event(event).userUid("asdf").build();
        Confirmation confirmation2 = Confirmation.builder().event(event).userUid("qwer").build();
        event.setConfirmationList(Arrays.asList(confirmation,confirmation2));
        eventRepository.save(event);
        userRepository.save(user);
        userRepository.save(user2);
        List<User> listOfUsers = userRepository.findUserConfirmedEvent(event.getId(), PageRequest.of(0,5)).getContent();
        Assert.assertEquals(2,listOfUsers.size());
        userRepository.deleteAll();
        eventRepository.deleteAll();
    }

//    @Test
//    public void shouldReturnTwoMembers(){
//        Club club = Club.builder().name("Test").location("Location").latitude(11).longitude(12).privateMode(false).details("Details").owner("123").created(LocalDateTime.now()).build();
//        User user = new User();
//        user.setId("asdf");
//        user.setName("Jan");
//        User user2 = new User();
//        user2.setId("qwer");
//        user2.setName("Ziutek");
//        Member member = Member.builder().club(club).userUid("asdf").build();
//        Member member2 = Member.builder().club(club).userUid("asdf").build();
//        clubRepository.save(club);
//        userRepository.saveAll(Arrays.asList(user,user2));
//        memberRepository.saveAll(Arrays.asList(member,member2));
//        List<User> listOfUsers = userRepository.findUserClubMembers(club.getId(),PageRequest.of(0,5)).getContent();
//        Assert.assertEquals(2,listOfUsers.size());
//        memberRepository.deleteAll();
//        clubRepository.deleteAll();
//        userRepository.deleteAll();
//    }

}
