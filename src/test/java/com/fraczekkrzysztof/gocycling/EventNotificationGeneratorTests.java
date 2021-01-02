package com.fraczekkrzysztof.gocycling;


import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class EventNotificationGeneratorTests {
//
//    @MockBean
//    EventRepository eventRepository;
//    @MockBean
//    NotificationRepository notificationRepository;
//
//    @Autowired
//    @Qualifier("updateEventNotificationGenerator")
//    EventNotificationGeneratorForConfirmations notificationGenerator;
//
//    @Before
//    public void before(){
//        Event eventToList1 = Event.builder()
//                .id(1)
//                .name("test1")
//                .place("place1")
//                .dateAndTime(LocalDateTime.now().plusDays(5))
//                .created(LocalDateTime.now())
//                .details("Details")
//                .canceled(false)
//                .build();
//        Event eventToList2 = Event.builder()
//                .id(2)
//                .name("test2")
//                .place("place2")
//                .dateAndTime(LocalDateTime.now().plusDays(5))
//                .created(LocalDateTime.now())
//                .details("Details")
//                .canceled(false)
//                .build();
//
//        Confirmation confirmation1 = Confirmation.builder()
//                .id(3)
//                .userUid("2345432")
//                .event(eventToList1).build();
//
//        Confirmation confirmation2 = Confirmation.builder()
//                .id(4)
//                .userUid("56546453")
//                .event(eventToList1).build();
//
//        Confirmation confirmation3 = Confirmation.builder()
//                .id(5)
//                .userUid("2345432")
//                .event(eventToList2).build();
//
//        Confirmation confirmation4 = Confirmation.builder()
//                .id(6)
//                .userUid("56546453")
//                .event(eventToList2).build();
//
//        List<Confirmation> listForEvent1 = Arrays.asList(confirmation1,confirmation2);
//        List<Confirmation> listForEvent2 = Arrays.asList(confirmation3,confirmation4);
//
//        when(eventRepository.findAllById(any())).thenReturn(Arrays.asList(eventToList1,eventToList2));
//        when(notificationRepository.saveAll(any())).thenReturn(Arrays.asList(new Notification(), new Notification()));
//    }
//
//    @Test
//    public void updateEventNotificationGeneratorTest(){
//        notificationGenerator.addEventIdAndIgnoreUser(1,"56546453");
//        notificationGenerator.addEventIdAndIgnoreUser(2,null);
//        notificationGenerator.generateNotification();
//        verify(notificationRepository,times(1)).saveAll(any());
//        verify(eventRepository,times(1)).findAllById(any());
//    }
}
