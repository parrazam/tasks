package es.parravidales.tasks.controller;


import es.parravidales.tasks.service.TaskService;
import es.parravidales.tasks.utils.BaseItTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static es.parravidales.tasks.utils.RandomUtils.nextLong;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerExceptionHandlerTest extends BaseItTest {

  final String BASE_ENDPOINT = "/task";

  @MockBean
  private TaskService taskService;

  @MockBean
  private ControllerExceptionHandler controllerExceptionHandler;

  @Test
  @SneakyThrows
  public void testGenericExceptionIsHandled() {
    RuntimeException expectedRuntimeException = new RuntimeException("Fake exception");
    given(taskService.getTask(anyLong())).willThrow(expectedRuntimeException);
    willCallRealMethod().given(controllerExceptionHandler).handleGenericException(expectedRuntimeException);

    mockMvc.perform(get(BASE_ENDPOINT + "/" + nextLong()))
      .andDo(print())
      .andExpect(status().isInternalServerError());

    then(controllerExceptionHandler).should().handleGenericException(expectedRuntimeException);
  }

}