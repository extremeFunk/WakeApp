package io.rainrobot.wake.client;


public interface IHttpRequestSender {

    void setAuthorization(String auth);

    <Response> Response get(String url, Class<Response> returnTyp);

    <Response> Response getWithParam(String prefix,
                                     String param,
                                     Class<Response> returnType);

    <Response, Request> Response send(String url,
                                      HttpMethodEnum method,
                                      Class<Response> returnTyp,
                                      Request ReqBody);

    <Response, Request> Response sendWithParam(String prefix,
                                               String param,
                                               HttpMethodEnum method,
                                               Class<Response> returnType,
                                               Request reqBody);

    void removeAuth();


}
