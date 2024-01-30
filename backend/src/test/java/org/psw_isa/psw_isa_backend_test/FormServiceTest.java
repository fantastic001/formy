package org.psw_isa.psw_isa_backend_test;

import org.psw_isa.psw_isa_backend.repository.FormRepository;
import org.psw_isa.psw_isa_backend.repository.UserRepository;
import org.psw_isa.psw_isa_backend.service.FormService;
import org.psw_isa.psw_isa_backend.service.UserService;
import org.psw_isa.psw_isa_backend.BackendApplication;
import org.psw_isa.psw_isa_backend.models.Form;
import org.psw_isa.psw_isa_backend.models.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes=BackendApplication.class)
public class FormServiceTest {


    @Autowired
    private FormService formService;

    @Autowired
    @MockBean
    private FormRepository formRepository;

    @Autowired
    @MockBean
    private UserRepository userRepository;

    private ArrayList<User> data;
    @BeforeEach
    public void setUp() {
    	data = new ArrayList<User>();
	data.add(new User());
	when(userRepository.findOneByemail(any(String.class))).thenReturn((User) data.get(0));
    }

    @AfterEach
    public void tearDown() {
    }
 
    @Test 
    public void testFindAll() {
        when(formRepository.findAll()).thenReturn(new ArrayList<Form>());
        List<Form> forms = formService.findAll();
        assertNotNull(forms);
    }

    @Test
    public void testGetCsv() {
        ArrayList<Form> forms = new ArrayList<Form>();
        forms.add(new Form());
        when(formRepository.findAll()).thenReturn(forms);
        when(formRepository.findOneByid(any(Long.class))).thenReturn(forms.get(0));
        when(userRepository.findOneByemail(any(String.class))).thenReturn(new User());
        String csv = formService.getCsv((long) 1);
        assertNotNull(csv);
    }
 
}
