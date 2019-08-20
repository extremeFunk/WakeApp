package io.rainrobot.wake.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.rainrobot.wake.core.Account;
import io.rainrobot.wake.core.AccountDesrializer;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Preset;
import io.rainrobot.wake.core.json.AccountDeSerializer;
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class AccountDesrializerTest {

    @Test
    public void deserialize() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        //adding our custom serializer and deserializer
//        module.addSerializer(Account.class ,new AccountDeSerializer());
        module.addDeserializer(Account.class, new AccountDesrializer());
        mapper.registerModule(module);

        Account original = createAccount(1);
        String originalName = original.getUsername();
        print("original: " + original.toString());

        String json = mapper.writeValueAsString(original);
        print("json: " + json);

        Account parsed = mapper.readValue(json, Account.class);
        print("parsed: " + parsed.toString());
        Assert.assertEquals(originalName, parsed.getUsername());
    }

    private void print(String string) {
        System.out.println(string);
    }


    private List<AlarmEvent> createAlarmList(int presetId, int... eventId) {
        List<AlarmEvent> list = new ArrayList<>();
        for (int i : eventId) {
            AlarmEvent alarm = createAlarmEvent(i, presetId);
            list.add(alarm);
        }
        return list;
    }

    private AlarmEvent createAlarmEvent(int eventId, int presetId) {
        AlarmEvent event = new AlarmEvent();
        event.setId(eventId);
        event.setPreset(createPreset(presetId));
        event.setDelay(2);
        return event;
    }

    private Preset createPreset(int presetId) {
        Preset preset = new Preset();
        preset.setId(presetId);
        return preset;
    }

    private Preset createPreset(int presetId,
                                boolean activeState,
                                GregorianCalendar time,
                                String name,
                                List<AlarmEvent> alarmList,
                                int accountId) {
        Preset preset = new Preset();
        preset.setId(presetId);
        Account account = createAccount(accountId);
        preset.setAccount(account);
        preset.setActiveState(activeState);
//        preset.setTime(time);
        preset.setName(name);
//        preset.setAlarmEventList(alarmList);

        return preset;
    }

    private Account createAccount(int accountId) {
        Account account = new Account();
        account.setUsername("someone");
        account.setId(accountId);
        return account;
    }
}