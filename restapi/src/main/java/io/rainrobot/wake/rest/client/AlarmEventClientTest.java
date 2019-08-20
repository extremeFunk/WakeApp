package io.rainrobot.wake.rest.client;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import io.rainrobot.wake.client.AlarmEventClient;
import io.rainrobot.wake.core.AlarmEvent;
import io.rainrobot.wake.core.Device;
import io.rainrobot.wake.core.Path;
import io.rainrobot.wake.core.Preset;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import static org.junit.Assert.*;

public class AlarmEventClientTest extends ClientTest{

    private AlarmEventClient client;

    @Before
    @Override
    public void setup() {
        super.setup();
        client = clientFactory.getEventClient();
    }
    @Test
    public void createAlarmEvent() throws Exception{
        AlarmEvent expected = new AlarmEvent();
        expected.setId(2);
        Preset preset = new Preset();
        preset.setId(3);
        expected.setPreset(preset);
        Device device = new Device();
        device.setId(4);
        expected.setDevice(device);
        server.expect(requestTo(Path.getAlarmUrl()))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andRespond(withSuccess(oMapper.writeValueAsBytes(expected),
                                                MediaType.APPLICATION_JSON));

        AlarmEvent recived = client.createAlarmEvent(3);

        assertEquals(expected.getId(), recived.getId());
        assertEquals(expected.getDevice().getId(), recived.getDevice().getId());
        assertEquals(expected.getPreset().getId(), recived.getPreset().getId());

        server.verify();
    }

    @Test
    public void updateAlarmEvent() throws Exception{
        AlarmEvent event = new AlarmEvent();
        event.setId(2);
        Preset preset = new Preset();
        preset.setId(3);
        event.setPreset(preset);
        Device device = new Device();
        device.setId(4);
        event.setDevice(device);
        server.expect(requestTo(Path.getAlarmUrl()))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().json(oMapper.writeValueAsString(event)))
                .andRespond(withSuccess());

        client.updateAlarmEvent(event);
        server.verify();
    }

    @Test
    public void deleteAlarmEvent() throws Exception{
        AlarmEvent sent = new AlarmEvent();
        sent.setId(3);
        server.expect(requestTo(Path.getAlarmUrl()+"/"+sent.getId()))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        client.deleteAlarmEvent(sent);
        server.verify();
    }

    @Test
    public void getAllEvents() throws Exception{
    }
}