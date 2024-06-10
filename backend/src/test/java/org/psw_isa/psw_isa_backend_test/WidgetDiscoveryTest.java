package org.psw_isa.psw_isa_backend_test;

import org.psw_isa.psw_isa_backend.repository.FormItemRepository;
import org.psw_isa.psw_isa_backend.repository.FormRepository;
import org.psw_isa.psw_isa_backend.repository.UserRepository;
import org.psw_isa.psw_isa_backend.service.UserService;
import org.psw_isa.psw_isa_backend.BackendApplication;
import org.psw_isa.psw_isa_backend.WidgetDiscovery;
import org.psw_isa.psw_isa_backend.models.Form;
import org.psw_isa.psw_isa_backend.models.FormItem;
import org.psw_isa.psw_isa_backend.models.User;
import org.psw_isa.psw_isa_backend.models.widgets.short_answer.ShortAnswer;
import org.psw_isa.psw_isa_backend.models.widgets.short_answer.ShortAnswerRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.*;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.*;

import javax.mail.search.FromTerm;

@ExtendWith(SpringExtension.class)
@SpringBootApplication
@ContextConfiguration(classes=BackendApplication.class)
public class WidgetDiscoveryTest {

    @TestConfiguration
    static class DiscoveryServiceconfig {
  
        @Bean
        public WidgetDiscovery discovery() {
            return new WidgetDiscovery();
        }
    }


    @Autowired
    @MockBean
    private FormItemRepository formItemRepository;

    @Autowired
    @MockBean
    private FormRepository formRepository; 

    @Autowired
    @MockBean
    private UserRepository userRepository; 

    @Autowired
    @MockBean
    private ShortAnswerRepository shortAnswerRepository;

    @Autowired
    private WidgetDiscovery discovery;

    @BeforeEach
    public void setUp() {
	    // given
        User author = new User();
        userRepository.save(author);

        Form form = new Form(
            LocalDateTime.now(), 
            LocalDateTime.now().plusDays(10), author, "test", "");

        FormItem item = new FormItem(form, "test", "", 1);

        ShortAnswer sa = new ShortAnswer(item, null);
        
        ArrayList<FormItem> items = new ArrayList<>();
        items.add(item);
        when(formItemRepository.findAll()).thenReturn(items);

        ArrayList<ShortAnswer> data = new ArrayList<>();
        data.add(sa);
        when(shortAnswerRepository.findAll()).thenReturn(data);
    }

    @AfterEach
    public void tearDown() {
    }

 
    @Test 
    public void testFindAll() {
    	assertEquals(discovery.getWidgets().size(), 1);
    }

    @Test 
    public void testFindFromFormItem() {
        assertNotNull(discovery.findWidgetFromFormItem(formItemRepository.findAll().get(0)));
    }
 
}
