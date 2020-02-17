package com.beautyboss.slogen.errorlog.monitor.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author : Slogen
 * Date   : 2020-02-05 13:53
 */
public class ThrowableUtils {

    public static String getThrowableStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        String result = sw.getBuffer().toString();
        return result.length() > 3700 ? result.substring(0,3700) + "\n..." : result;
    }

}
