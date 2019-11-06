package io.rainrobot.wake.rest.request.account;

import io.rainrobot.wake.rest.dto.Account;
import io.rainrobot.wake.rest.configuration.appuser.Authority;
import io.rainrobot.wake.rest.configuration.appuser.State;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.rainrobot.wake.rest.dto.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.rest.dto.AppUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Service.class)
@WebMvcTest(AccountController.class)
//@ContextConfiguration(classes = {
////        MvcConfig.class,
//        SecurityConfiguration.class})

public class AccountControllerTest {
    @Autowired
    private MockMvc mvc;
//    @MockBean
//    private UserService usrService;
    @MockBean
    private AccountService service;
//    @MockBean
//    private JwtAuthenticationFilter authFilter;

//    @Autowired
//    private WebApplicationContext wac;
//
//    @Before
//    public void setUp() throws Exception {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(wac)
//                .apply(SecurityMockMvcConfigurers
//                        .springSecurity())
//                .build();
//    }

    @Test
    public void getAccount() {
    }

    @Test
    public void updateAccountName() throws Exception {
        String usrNm = "admin";
        AppUser appUser = new AppUser(usrNm, "pass");

        mvc.perform(get(Path.getAccoutUrl())
                .with(authentication(new AppAuth(appUser, true)))

                )
                .andExpect(status().isOk());

        verify(service, times(1))
                .findByUsername(usrNm);
    }

    @Test
//    @WithMockUser(value = "admin",
//            username = "admin",
//            password = "pass",
//            authorities = {"ADMIN"})
    public void deleteAccount() throws Exception {
        String usrNm = "admin";
        AppUser appUser = new AppUser(usrNm, "pass");
        appUser.setAuthority(Authority.ADMIN);
        appUser.setState(State.ACTIVE);

//        when(usrService.findByUsername(usrNm)).thenReturn(appUser);
//        String token = Values.TOKEN_PREFIX + TokenBuilder.create(usrNm);

        mvc.perform(delete(Path.getAccoutUrl())
                  .with(authentication(new AppAuth(appUser, true)))
                  .with(csrf())
//                .header(Values.AUTH_HEADER, token)
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1))
                .deleteByUsername(usrNm);
    }

    @Test
    public void getAccountName() throws Exception {
        String usrNm = "admin";
        AppUser appUser = new AppUser(usrNm, "pass");
//        HttpHeaders headers = new HttpHeaders();
//        Mockito.when(usrService.findByUsername(usrNm)).thenReturn(appUser);
//        String token = Values.TOKEN_PREFIX + TokenBuilder.create(usrNm);
//        headers.set(Values.AUTH_HEADER, token);

        mvc.perform(get(Path.USERNAME)
                .with(authentication(new AppAuth(appUser, true))))
//                .header(Values.AUTH_HEADER, token))
                .andExpect(status().isOk())
                .andExpect(content().string(usrNm));
    }

    @Test
    public void getLastChange() throws Exception {
        String usrNm = "admin";
        AppUser appUser = new AppUser(usrNm, "pass");

        Account account = new Account();
        Date date = new Date();
        account.setLastChange(date);
        given(service.findByUsername(usrNm)).willReturn(account);

        MvcResult mvcResult = mvc.perform(get(Path.LASTCHANGE)
                .with(authentication(new AppAuth(appUser, true))))
                .andExpect(status().isOk())
                .andReturn();

        verify(service, times(1))
                .findByUsername(usrNm);
    }
}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class testConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http	.cors().and()
                .csrf().disable()
                .authorizeRequests(
//                        GET, Path.ACCOUNT
                    )
                .antMatchers("/login", "/singup")
                .permitAll();
    }
}