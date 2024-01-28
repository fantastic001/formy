package org.psw_isa.psw_isa_backend_test;
import org.junit.jupiter.api.AfterEach;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.psw_isa.psw_isa_backend.BackendApplication;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes=BackendApplication.class)
public class ImageUploadControllerTest {

    private MockMvc mockMvc;

     @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }


    @AfterEach
    public void rollback() {
        Resource resource = new ClassPathResource("data-h2.sql");
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(resource);
        resourceDatabasePopulator.execute(dataSource);

        // delete directory /var/data/formy/media
        File directory = new File("/var/data/formy/media");
        if (directory.exists()) {
            for (File file : directory.listFiles()) {
                file.delete();
            }
            directory.delete();
        }
    }


    @Test
    public void testImageUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "Test image content".getBytes()
        );

        mockMvc.perform(multipart("/images/upload/").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testEmptyImageUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "empty-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/images/upload/").file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testImageUploadWithWrongMediaType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.APPLICATION_JSON_VALUE,
                "Test image content".getBytes()
        );

        mockMvc.perform(multipart("/images/upload/").file(file))
                .andExpect(status().isBadRequest());
    }

}
