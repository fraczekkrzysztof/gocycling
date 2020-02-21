package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.dao.EventRepository;
import com.fraczekkrzysztof.gocycling.dao.UserRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.entity.Event;
import com.fraczekkrzysztof.gocycling.entity.User;
import org.junit.*;
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

    @Before
    public  void prepTests(){

    }

    @Test
    public void shouldReturnTwoUsers(){
        Event event = new Event.Builder().setName("Test1").setPlace("TestPlace1").setCreated(LocalDateTime.now()).setDateAndTime(LocalDateTime.now().plusDays(5)).build();
        User user = new User();
        user.setId("asdf");
        user.setName("Jan");
        User user2 = new User();
        user2.setId("qwer");
        user2.setName("Ziutek");
        Confirmation confirmation = new Confirmation.Builder().setEvent(event).serUserUid("asdf").build();
        Confirmation confirmation2 = new Confirmation.Builder().setEvent(event).serUserUid("qwer").build();
        event.setConfirmationList(Arrays.asList(confirmation,confirmation2));
        eventRepository.save(event);
        userRepository.save(user);
        userRepository.save(user2);
        List<User> listOfUsers = userRepository.findUserConfirmedEvent(event.getId(), PageRequest.of(0,5)).getContent();
        Assert.assertEquals(2,listOfUsers.size());
        userRepository.deleteAll();
        eventRepository.deleteAll();
    }

}
