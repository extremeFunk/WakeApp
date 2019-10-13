package io.rainrobot.wake.rest.request.preset;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.util.DateUtil;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.rest.configuration.appuser.AppUser;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PresetController.class)
//@ContextConfiguration(classes = {
////        MvcConfig.class,
//        SecurityConfiguration.class})
public class PresetControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PresetService service;
//    @MockBean
//    private JwtAuthenticationFilter authFilter;

    @Test
    public void getAccountName() throws Exception {
        String usrNm = "admin";
        AppUser appUser = new AppUser(usrNm, "pass");
        Preset preset = new Preset();
        preset.setAccount(new Account(0, usrNm));
        preset.setActiveState(true);
        Date time = DateUtil.fromHrAndMn(3,3);
        preset.setTime(time);
        Preset b = new Preset();
        b.setAccount(new Account(0, usrNm));
        b.setActiveState(true);
//        Date v;
//        b.setTime(v);
        List<Preset> presetList = new ArrayList<>();
        presetList.add(preset);
        presetList.add(b);

        Mockito.when(service.getAllPresets(usrNm)).thenReturn(presetList);

        ResultMatcher resultMatcher;
        mvc.perform(get(Path.getPresetUrl())
                .with(authentication(new AppAuth(appUser, true))))
                .andExpect((jsonPath("$.preset.time", Matchers.equalTo(""))));

    }

    @Test
    public void name() {

    }
}
