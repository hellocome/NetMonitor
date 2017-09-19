package com.zhaohaijie.NetMonitor.Logging;

/**
 * Created by ZHJ on 2/15/2017.
 */
public interface Log {

    /**
     *  Log a message with debug log level.
     *
     * @return return the source of the log
     */
    String getLogSource();

    /**
     *  Log a message with debug log level.
     */
    boolean isDebugEnabled();
    /**
     *  Log a message with debug log level.
     *
     * @param message log this message
     */
    void debug(Object message);


    /**
     * Log an error with debug log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    void debug(Object message, Throwable t);


    /**
     * Log a message with info log level.
     *
     * @param message log this message
     */
    void info(Object message);


    /**
     * Log an error with info log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    void info(Object message, Throwable t);


    /**
     * Log a message with error log level.
     *
     * @param message log this message
     */
    void error(Object message);


    /**
     * Log an error with error log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    void error(Object message, Throwable t);

}
