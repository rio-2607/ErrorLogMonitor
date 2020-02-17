package com.beautyboss.slogen.errorlog.monitor.appender;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import com.beautyboss.slogen.errorlog.monitor.monitor.MonitorRecord;
import com.beautyboss.slogen.errorlog.monitor.utils.ThrowableUtils;

/**
 * Author : Slogen
 * Date   : 2020-02-05 13:38
 */
public abstract class AbstractMonitorAppender extends AppenderBase<LoggingEvent> {
    @Override
    protected void append(LoggingEvent eventObject) {
        try {
            if(!need2Alarm()) {
                return;
            }
            Level level = eventObject.getLevel();
            if(Level.ERROR != level) {
                // 只处理error级别的报错
                return;
            }
            String userLogedErrorMessage = eventObject.getFormattedMessage();
            String stackTraceInfo = "";

            IThrowableProxy proxy = eventObject.getThrowableProxy();
            if(null != proxy) {
                Throwable t = ((ThrowableProxy) proxy).getThrowable();
                stackTraceInfo = ThrowableUtils.getThrowableStackTrace(t);
            }
            MonitorRecord record = MonitorRecord.buildRecord(stackTraceInfo,userLogedErrorMessage,
                    getAppName(),getEnv(),eventObject.getMessage());
            monitor(record);
        } catch (Exception e) {
            addError("日志报警异常，异常原因:{}",e);
        }


    }

    protected abstract String getAppName();

    protected abstract String getEnv();

    protected abstract void monitor(MonitorRecord monitorRecord);

    // 是否需要报警，增加报警开关，后续跟配置管理中间件配合使用
    protected abstract boolean need2Alarm();


}
