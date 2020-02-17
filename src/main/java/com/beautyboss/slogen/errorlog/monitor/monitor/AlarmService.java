package com.beautyboss.slogen.errorlog.monitor.monitor;

/**
 * Author : Slogen
 * Date   : 2020-02-05 14:03
 */
public interface AlarmService {

    /**
     * 发出报警消息
     * @param record
     * @return
     */
    boolean alarm(MonitorRecord record);

}
