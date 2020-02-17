package com.beautyboss.slogen.errorlog.monitor.appender;


import com.beautyboss.slogen.errorlog.monitor.monitor.MonitorRecord;
import com.beautyboss.slogen.errorlog.monitor.monitor.MonitorService;
import com.beautyboss.slogen.errorlog.monitor.monitor.impl.WechatMonitor;

/**
 * Author : Slogen
 * Date   : 2020-02-05 13:58
 */
public class WechatMonitorAppender extends AbstractMonitorAppender {

    private String appName;

    private int coreThreadNum;

    private int maxThreadNum;

    private String env;

    private String webHookUrl;

    private MonitorService monitorService;

    public WechatMonitorAppender() {

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
        if(null == monitorService) {
            synchronized (this) {
                if(null == monitorService) {
                    //  monitorService需要保持单例且要懒加载
                    monitorService = new WechatMonitor(webHookUrl,coreThreadNum,maxThreadNum);
                }
            }
        }
        monitorService.monitor(monitorRecord);
    }

    @Override
    protected boolean need2Alarm() {
        return true;
    }
}
