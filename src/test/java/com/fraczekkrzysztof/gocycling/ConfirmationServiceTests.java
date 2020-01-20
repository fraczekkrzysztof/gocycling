package com.fraczekkrzysztof.gocycling;

import com.fraczekkrzysztof.gocycling.dao.ConfirmationRepository;
import com.fraczekkrzysztof.gocycling.entity.Confirmation;
import com.fraczekkrzysztof.gocycling.service.ConfirmationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfirmationServiceTests {

    @MockBean
    ConfirmationRepository confirmationRepository;

    @Autowired
    ConfirmationService confirmationService;

    @Before
    public void before(){
        Confirmation confirmation = new Confirmation.Builder().setId(1).build();
        when(confirmationRepository.findByUserUidAndEventId(anyString(),anyLong())).thenReturn(confirmation);
        doNothing().when(confirmationRepository).delete(ArgumentMatchers.any(Confirmation.class));
    }

    @Test
    public void deleteByUserUidAndEventId(){
        confirmationService.deleteByUserUidAndEventId("test", 1);
        assertTrue(true);
    }
}
