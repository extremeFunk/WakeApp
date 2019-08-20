package io.rainrobot.wake.fx.client;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;

import io.rainrobot.wake.core.util.Log;
import org.springframework.web.client.HttpServerErrorException;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        try {
            traceRequest(request, body);
            ClientHttpResponse response = execution.execute(request, body);
            traceResponse(response);
            return response;
        } catch (IOException e) {
            throw new RequestInterceptorException(e.getMessage());
        }
    }

    private void traceRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
        Log.d(this, "===========================request begin================================================");
        Log.i("URI         :" + request.getURI().toString());
        Log.i("Method      :" + request.getMethod().toString());
        Log.i("Headers     :" + request.getHeaders().toString());
        Log.i("Request body:" + new String(body, "UTF-8"));
        Log.d(this, "==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {

        Log.i("============================response begin==========================================");
        try {
            if (response == null) Log.i("Error: Response is null");
            else {
                Log.i("Status code  : " + response.getStatusCode().toString());
                Log.i("Status text  : " + response.getStatusText());
                Log.i("Headers      : " + response.getHeaders().toString());
                if(response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    Log.i("Response body: " + "cannot pars response body");
                } else {
                    Log.i("Response body: " + parsResponseBody(response));
                }
            }
        } catch (ProtocolException e) {
            String msg = "Unexpected Response throw's ProtocolException";
            Log.err("RequestInterceptor", msg, e);
        }
        Log.i( "=======================response end=================================================");
    }

    private String parsResponseBody(ClientHttpResponse response) throws IOException {
        String responsBodyTxt;

        InputStreamReader inputStreamReader
                = new InputStreamReader(response.getBody(), "UTF-8");

        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader
                = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        responsBodyTxt = inputStringBuilder.toString();
        return responsBodyTxt;
    }

    public class RequestInterceptorException extends RuntimeException {
        public RequestInterceptorException(String msg) {
            super(msg);
        }
    }
}
