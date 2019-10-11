package io.rainrobot.wake.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.GregorianCalendar;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AccountSerializer;
import io.rainrobot.wake.core.Preset;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
@SpringBootTest(classes = Service.class)
@JsonTest
public class JsonConversionTest {


    @Autowired
    private JacksonTester<Account> accountJacksonTester;
    @Autowired
    private JacksonTester<Preset> presetjacksonTestr;
    private static final String PATH_PREFIX = "@.";



    @Test
    public void accountSerializeTest() throws Exception {
        int id = 5;
        String name = "name";
        Account account = new Account(id, name);

        System.out.println("object: " + account.toString());
        // Or use JSON path based assertions

        JsonContent<Account> content = accountJacksonTester.write(account);

        System.out.println("accountJacksonTester: " + content.getJson());

        String usrNmPath = PATH_PREFIX + AccountSerializer.USERNAME;
        assertThat(content).hasJsonPathStringValue(usrNmPath);
        assertThat(content).extractingJsonPathStringValue(usrNmPath)
                .isEqualTo(name);

    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":5,\"username\":\"name\"," +
                            "\"lastChange\":null,\"deviceList\":null," +
                             "\"presetList\":null}";

        Account referenceObject = new Account(5, "name");
        System.out.println(referenceObject.toString());
        Account parseObject = accountJacksonTester.parseObject(content);
        System.out.println(parseObject.toString());

        assertThat(accountJacksonTester.parse(content))
                .isEqualTo(referenceObject);
        assertThat(parseObject.getUsername()).isEqualTo("name");
    }

    @Test
    public void testDeserialize2() throws Exception {
        String content = "{\"account\":{\"id\":2,\"username\":\"bob\"}," +
                            "\"activeState\":true,\"alarmEventList\":[]," +
                            "\"id\":2,\"name\":\"New Preset\",\"time\":9933000}";


//        referenceObject.setAccount(new Account(2, "bob"));
//        System.out.println(referenceObject.toString());
        Preset parseObject = presetjacksonTestr.parseObject(content);
        System.out.println(parseObject.toString());

        assertThat(parseObject.getId()).isEqualTo(2);
        assertThat(parseObject.getName()).isEqualTo("New Preset");
        assertThat(parseObject.getAccount()).isEqualTo(new Account(2, "bob"));
    }

    @Test
    public void testDeserialize3() throws Exception {
        String content = "{\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"New Preset\",\n" +
                "        \"alarmEventList\": [],\n" +
                "        \"account\": {\n" +
                "            \"id\": 1,\n" +
                "            \"username\": \"shlomi\"\n" +
                "        },\n" +
                "        \"time\": 2733000,\n" +
                "        \"activeState\": true\n" +
                "    }";


        Preset parseObject = presetjacksonTestr.parseObject(content);
        System.out.println(parseObject.toString());

        Preset ref = new Preset();
        ref.setId(2);
//        ref.setAlarmEventList(new HashSet<>());
        ref.setName("New Preset");
        GregorianCalendar time = new GregorianCalendar();
        time.setTimeInMillis(2733000);
        ref.setTime(time.getTime());
        ref.setActiveState(true);
        ref.setAccount(new Account(1, "shlomi"));
        System.out.println(ref.toString());

        assertThat(parseObject).isEqualToComparingFieldByField(ref);
     }
}
