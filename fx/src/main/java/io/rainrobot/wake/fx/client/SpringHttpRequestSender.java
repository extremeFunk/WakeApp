package io.rainrobot.wake.fx.client;

import io.rainrobot.wake.client.*;
import io.rainrobot.wake.core.util.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestOperations;

import java.io.UnsupportedEncodingException;


public class SpringHttpRequestSender implements IHttpRequestSender {

    private RestOperations temple;
    private HttpHeaders headers;

    public SpringHttpRequestSender(RestOperations temple, HttpHeaders headers) {
        this.temple = temple;
        this.headers = headers;
    }

    @Override
    public <Response, Request> Response send(String url,
                                             HttpMethodEnum method,
                                             Class<Response> returnTyp,
                                             Request ReqBody) {

        HttpEntity entity = new HttpEntity(ReqBody, headers);
        HttpMethod springMethod = HttpMethod.valueOf(method.name());
        ResponseEntity<Response> resEntity;

        try {
            resEntity = temple.exchange(url, springMethod, entity, returnTyp);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            Log.err(this, e);
            throw new ResponseException(
                    e.getStatusCode().value(),
                    getError(e.getResponseBodyAsByteArray()));
        }
        checkStatus(resEntity);
        Response resBody = resEntity.getBody();

        if (resBody == null && returnTyp != Void.class) throw new NullResponseException();
        else return resBody;
    }

    private String getError(byte[] byteBody) {
        String body = null;
        try {
            body = new String(byteBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.err(this,  e);
            throw new RuntimeException("Unable to encode respond message");
        }
        int startIdx = body.indexOf("message") + 14;
        int endIdx = body.indexOf("path") - 3;
        return body.substring(startIdx, endIdx);
    }

    private <Response> void checkStatus(ResponseEntity<Response> resEntity) {
        HttpStatus statusCode = resEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new HttpStatusException(statusCode.value(), resEntity.toString());
        }
    }

    @Override
    public void setAuthorization(String auth) {
        headers.remove(AddressValues.AUTH_HEADER_LABEL);
        headers.add(AddressValues.AUTH_HEADER_LABEL, auth);
    }

    @Override
    public <Response> Response get(String url, Class<Response> returnTyp) {
        return send(url, HttpMethodEnum.GET, returnTyp, null);
    }

    @Override
    public <Response> Response getWithParam(String prefix,
                                            String param,
                                            Class<Response> returnType) {
        String url = prefix + "/" + param;
        return get(url, returnType);
    }


    @Override
    public <Response, Request> Response sendWithParam(String prefix,
                                                      String param,
                                                      HttpMethodEnum method,
                                                      Class<Response> returnType,
                                                      Request reqBody) {
        String url = prefix + "/" + param;
        return send(url, method, returnType, reqBody);
    }

    @Override
    public void removeAuth() {
        headers.remove(AddressValues.AUTH_HEADER_LABEL);
    }

}
