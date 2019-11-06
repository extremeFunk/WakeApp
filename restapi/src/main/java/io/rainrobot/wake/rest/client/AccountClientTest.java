package io.rainrobot.wake.rest.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Date;

import io.rainrobot.wake.client.AccountClient;
import io.rainrobot.wake.rest.dto.Account;
import io.rainrobot.wake.rest.dto.Path;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class AccountClientTest extends ClientTest {

    private AccountClient client;
    private static final String URL = Path.getAccoutUrl();

    @Before
    @Override
    public void setup() {
        super.setup();
        client = clientFactory.getAccountClient();
    }

    @Test
    public void getAccount() throws Exception{
        Account sent = new Account(1, "testAccount");
        server.expect(requestTo(URL))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(oMapper.writeValueAsBytes(sent),
                        MediaType.APPLICATION_JSON));

        Account recived = client.getAccount();
        Assert.assertEquals(sent.getUsername(), recived.getUsername());
        Assert.assertEquals(sent.getId(), recived.getId());
        server.verify();
    }

    @Test
    public void updateAccount() throws Exception {
        Account sent = new Account(1, "testAccount");
        server.expect(requestTo(URL))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().json(oMapper.writeValueAsString(sent)))
                .andRespond(withSuccess());

        client.updateAccount(sent);

        server.verify();
    }

    @Test
    public void deleteAccount() throws Exception{

        server.expect(requestTo(URL))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());
        client.deleteAccount();
        server.verify();
    }

    @Test
    public void getUsername() throws Exception {
        String sent = "testAccount";
        server.expect(requestTo(Path.getUsernameUrl()))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(sent, MediaType.TEXT_PLAIN));

        String recived = client.getUsername();
        Assert.assertEquals(sent, recived);
        server.verify();
    }

    @Test
    public void getLastChange() throws Exception {
        Date expected = new Date();
        server.expect(requestTo(Path.getLastChangeUrl()))
                .andExpect(header(Path.AUTH_HEADER_LABEL, AUTH))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(oMapper.writeValueAsBytes(expected),
                                        MediaType.APPLICATION_JSON));
        Date recived = client.getLastChange();
        Assert.assertEquals(expected, recived);
        server.verify();
    }
}