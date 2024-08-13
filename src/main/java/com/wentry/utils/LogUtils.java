package com.wentry.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: tangwc
 */
public class LogUtils {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String currTime(){
        return LocalDateTime.now().format(formatter);
    }

    public static String wrapTimeLog(String log) {
        return currTime() + " : " + log;
    }

}
