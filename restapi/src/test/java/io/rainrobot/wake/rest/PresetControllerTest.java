package io.rainrobot.wake.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.rainrobot.wake.rest.dto.Preset;
import io.rainrobot.wake.rest.request.preset.PresetController;
import io.rainrobot.wake.rest.request.preset.PresetService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {Service.class, MvcConfig.class})
@EnableAutoConfiguration
@WebMvcTest(PresetController.class)
public class PresetControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private PresetService service;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void deletePreset() throws Exception {
        Preset preset = new Preset();
//        List<Employee> allEmployees = Arrays.asList(alex);
//        given(service.getAllEmployees()).willReturn(allEmployees);

        mvc.perform(delete("/preset", preset)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is(alex.getName())));
    }
}