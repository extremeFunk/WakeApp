package io.rainrobot.wake.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import io.rainrobot.wake.client.ClientFactory;
import io.rainrobot.wake.client.IHttpRequestSender;
import io.rainrobot.wake.android.client.springClient.SpringHttpRequestSenderConfiguraton;
import io.rainrobot.wake.core.util.Log;
import io.rainrobot.wake.core.util.defaultLog;
import io.rainrobot.wake.rest.JpaConfig;
import io.rainrobot.wake.rest.Service;
import io.rainrobot.wake.rest.MvcConfig;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Service.class, JpaConfig.class, MvcConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class ClientTest {

    protected final String AUTH = "AUTH";
    protected MockRestServiceServer server;
    @Autowired
    protected ObjectMapper oMapper;
    protected ClientFactory clientFactory;


    @Before
    public void setup() {
        SpringHttpRequestSenderConfiguraton config
                = new SpringHttpRequestSenderConfiguraton();
        RestTemplate tmp = config.getRestDefaultTampet();
        server = MockRestServiceServer.bindTo(tmp).bufferContent().build();
        IHttpRequestSender sender = new SpringHttpRequestSenderConfiguraton()
                .getRequestSender(tmp);
        sender.setAuthorization(AUTH);
        clientFactory = new ClientFactory(sender);
        Log.log = new defaultLog();
    }
}