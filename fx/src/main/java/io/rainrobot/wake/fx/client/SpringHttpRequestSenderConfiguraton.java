package io.rainrobot.wake.fx.client;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.rainrobot.wake.client.IHttpRequestSender;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class SpringHttpRequestSenderConfiguraton {

    private SpringHttpRequestSender sender;

    public IHttpRequestSender getRequestSender() {
        return getRequestSender(getRestDefaultTampet());
    }

    public IHttpRequestSender getRequestSender(RestTemplate tamplet) {
        initializeTemple(tamplet);
        sender = new SpringHttpRequestSender(tamplet, getHeaders());
       return sender;
    }

    private void initializeTemple(RestTemplate template) {
        template.setInterceptors(getClientHttpRequestInterceptors());
        template.getMessageConverters().add(getConverters());
    }


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);
        list.add(MediaType.TEXT_PLAIN);
        list.add(MediaType.APPLICATION_OCTET_STREAM);



        headers.setAccept(list);

        return headers;
    }

    public RestTemplate getRestDefaultTampet() {
        return new RestTemplate(getRequestFactory());
    }

    public List<ClientHttpRequestInterceptor> getClientHttpRequestInterceptors() {
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new RequestInterceptor());
        return interceptors;
    }


    public BufferingClientHttpRequestFactory getRequestFactory() {
        return new BufferingClientHttpRequestFactory
                (new SimpleClientHttpRequestFactory());
    }

    public SimpleModule getJsonModule() {
        SimpleModule module = new SimpleModule();
        return module;
    }

    public  HttpMessageConverter<?> getConverters() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().registerModules(getJsonModule());
        return converter;
    }

}
