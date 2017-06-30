package com.recklessmo.service.http;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by hpf on 2/9/17.
 */
@Service
public class RemoteHttpService {

    private CloseableHttpClient httpClient;

    public RemoteHttpService(){
        httpClient = HttpClients.createDefault();
    }

    /**
     *
     * url
     *
     * @param url
     * @param params
     * @param responseHandler
     */
    public String postHttpRequest(String url, Map<String, String> params) throws Exception{
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramList = new LinkedList<>();
        params.entrySet().forEach(item -> {
            paramList.add(new BasicNameValuePair(item.getKey(), item.getValue()));
        });
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
        httpPost.setEntity(urlEncodedFormEntity);
        HttpResponse response = httpClient.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
