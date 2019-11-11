package io.rainrobot.wake.rest.request.account;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.rest.configuration.appuser.Authority;
import io.rainrobot.wake.rest.configuration.appuser.State;
import io.rainrobot.wake.rest.configuration.appuser.service.UserService;
import io.rainrobot.wake.rest.configuration.security.SecurityConfiguration;
import org.junit.Before;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.rest.configuration.appuser.AppAuth;
import io.rainrobot.wake.rest.configuration.appuser.AppUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @MockBean
    private AccountService service;

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
    public void deleteAccount() throws Exception {
        String usrNm = "admin";
        AppUser appUser = new AppUser(usrNm, "pass");
        appUser.setAuthority(Authority.ADMIN);
        appUser.setState(State.ACTIVE);

        mvc.perform(delete(Path.getAccoutUrl())
                  .with(authentication(new AppAuth(appUser, true)))
                  .with(csrf())
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

        mvc.perform(get(Path.USERNAME)
                .with(authentication(new AppAuth(appUser, true))))
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