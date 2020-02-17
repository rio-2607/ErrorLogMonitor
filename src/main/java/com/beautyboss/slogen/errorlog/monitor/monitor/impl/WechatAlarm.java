package com.beautyboss.slogen.errorlog.monitor.monitor.impl;


import com.beautyboss.slogen.errorlog.monitor.http.HttpClient;
import com.beautyboss.slogen.errorlog.monitor.monitor.MonitorRecord;
import com.beautyboss.slogen.errorlog.monitor.monitor.AlarmService;
import com.beautyboss.slogen.errorlog.monitor.utils.ThreadPoolFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Author : Slogen
 * Date   : 2020-02-05 14:12
 */
public class WechatAlarm implements AlarmService {

    private ExecutorService executorService;

    private HttpClient client = new HttpClient();

    private String webHookUrl;

    public WechatAlarm() {}

    public WechatAlarm(String webHookUrl, int coreThreadNum, int maxThreadNum) {
        this.webHookUrl = webHookUrl;
        executorService = ThreadPoolFactory.createExecutorService(coreThreadNum,maxThreadNum);
    }

    private Map<String,Object> buildParam(MonitorRecord record) {
        Map<String,Object> map = new HashMap<>();
        map.put("msgtype","text");
        Map<String,String> content = new HashMap<>();
        content.put("content",record.toString());
        map.put("text",content);
        return map;
    }

    @Override
    public boolean alarm(MonitorRecord record) {
        executorService.submit(() -> {
            try {
                Map<String,Object> map = buildParam(record);
                client.sendPostRequest(webHookUrl,map);

            } catch (Exception e) {

            }
        });
        return true;
    }
}
