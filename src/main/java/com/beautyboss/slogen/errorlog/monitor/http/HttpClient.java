package com.beautyboss.slogen.errorlog.monitor.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2020-02-05 14:56
 */
public class HttpClient {

    private static final Integer TIME_OUT = 1000;

    public JSONObject sendPostRequest(String baseURL, Map<String, Object> parameterMap) throws HttpException {
        try {
            String body = JSON.toJSONString(parameterMap);
            String content = Request.Post(baseURL).bodyString(body, ContentType.APPLICATION_JSON).connectTimeout(TIME_OUT).execute()
                    .returnContent().asString();
            return JSON.parseObject(content);
        } catch (Exception e) {
            return null;
        }


    }

}