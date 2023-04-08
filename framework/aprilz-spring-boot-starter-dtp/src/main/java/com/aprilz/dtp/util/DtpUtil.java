package com.aprilz.dtp.util;

import com.aprilz.dtp.core.DtpExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DtpUtil {
    public static Map<String, DtpExecutor> map = new ConcurrentHashMap<>();

    public static void set(String name, DtpExecutor dtpExecutor) {
        map.put(name, dtpExecutor);
    }

    public static DtpExecutor get(String name) {
        return map.get(name);
    }
}
