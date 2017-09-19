package com.zhaohaijie.NetMonitor.Logging;


public abstract class LogFactory {
    private static ConsoleLog consoleLog = new ConsoleLog();
    public static Log getLog(){
        return consoleLog;
    }
}
