package repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rainrobot.wake.rest.configuration.appuser.Authority;
import io.rainrobot.wake.rest.dto.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@EnableAutoConfiguration
@DataMongoTest
public class AppUserRepoTest {
    public static final String USER = "user";
    @Autowired
    AppUserRepo repo;
    private AppUser user;

    @Before
    public void first_test() {
        /*
        saved user structure

        appuser
            account
                presets
                    events X 3
                        device name 0-2
                device X 3
                    event X 1 (name 0-2)
        */

        user = AppUser.builder()
                .username(USER)
                .password("password")
                .email("email")
                .authority(getAuthorities())
                .state("active")
                .resetToken(0)
                .account(getAccount())
                .build();

        repo.save(user);
    }

    private List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(()-> Authority.ROLE_USER.name());
        return list;
    }

    @Test
    public void appUserExist() throws Exception {
        AppUser found = repo.findByUsername(USER);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(found);
        System.out.println(json);
        assertTrue(repo.existsByUsername(USER));
    }

    @Test
    public void appUserDataCorrect() throws Exception {
        AppUser found = repo.findByUsername(USER);
        assertEquals(found, user);
    }

    private Account getAccount() {
        return Account.builder().lastChange(getNow())
                .deviceList(getDevices())
                .presetList(getPresets())
                .build();
    }

    private List<Preset> getPresets() {
        List<Preset> list = new ArrayList();
        //only one preset
        for (int i = 0; i < 1; i++) {
            String name = String.valueOf(i);
            list.add(
                Preset.builder().activeState(true)
                    .name(name)
                    .time(getNow())
                    .alarmEventList(getPresetEvents())
                    .build()
            );
        }
        return list;
    }

    private List<AlarmEvent> getPresetEvents() {
        List<AlarmEvent> list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            String deviceName = String.valueOf(i);
            list.add(getEvent(deviceName));
        }
        return list;
    }

    private AlarmEvent getEvent(String deviceName) {
        return AlarmEvent.builder().delay(1)
                .device(deviceName)
                .shutOff(1)
                .snooze(1)
                .sound(Sound.BIRDS)
                .vol(1)
                .build();
    }

    private Date getNow() {
        return Calendar.getInstance().getTime();
    }

    private List<Device> getDevices() {
        List<Device> list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            String name = String.valueOf(i);
            list.add(
                    Device.builder()
                            .name(name)
                            .eventList(getDeviceEvents(name))
                            .build()
            );
        }
        return list;
    }

    private List<AlarmEvent> getDeviceEvents(String devName) {
        List<AlarmEvent> list = new ArrayList<>();
        list.add(getEvent(devName));
        return list;
    }
}