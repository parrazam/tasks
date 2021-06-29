package es.parravidales.tasks.utils;

import es.parravidales.tasks.TasksApplication;
import es.parravidales.tasks.config.TestConfiguration;
import es.parravidales.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {TasksApplication.class, TestConfiguration.class},
  properties = "spring.main.allow-bean-definition-overriding=true")
public class BaseItTest {

  @Autowired
  protected WebApplicationContext webApplicationContext;

  @MockBean
  protected TaskRepository repository;

  protected MockMvc mockMvc;

  @BeforeEach
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

}
