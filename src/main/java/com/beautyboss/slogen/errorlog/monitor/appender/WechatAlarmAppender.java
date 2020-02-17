package com.beautyboss.slogen.errorlog.monitor.appender;


import com.beautyboss.slogen.errorlog.monitor.monitor.MonitorRecord;
import com.beautyboss.slogen.errorlog.monitor.monitor.AlarmService;
import com.beautyboss.slogen.errorlog.monitor.monitor.impl.WechatAlarm;

/**
 * Author : Slogen
 * Date   : 2020-02-05 13:58
 */
public class WechatAlarmAppender extends AbstractAlarmAppender {

    private String appName;

    private int coreThreadNum;

    private int maxThreadNum;

    private String env;

    private String webHookUrl;

    private AlarmService alarmService;

    public WechatAlarmAppender() {

    }

    public void setWebHookUrl(String webHookUrl) {
        this.webHookUrl = webHookUrl;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setCoreThreadNum(int coreThreadNum) {
        this.coreThreadNum = coreThreadNum;
    }

    public void setMaxThreadNum(int maxThreadNum) {
        this.maxThreadNum = maxThreadNum;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    protected String getAppName() {
        return this.appName;
    }

    @Override
    protected String getEnv() {
        return this.env;
    }

    @Override
    protected void monitor(MonitorRecord monitorRecord) {
        if(null == alarmService) {
            synchronized (this) {
                if(null == alarmService) {
                    //  monitorService需要保持单例且要懒加载
                    alarmService = new WechatAlarm(webHookUrl,coreThreadNum,maxThreadNum);
                }
            }
        }
        alarmService.alarm(monitorRecord);
    }

    @Override
    protected boolean need2Alarm() {
        return true;
    }
}
