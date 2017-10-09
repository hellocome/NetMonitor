package com.zhaohaijie.NetMonitor.Logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ConsoleLog implements Log {

    private String logSource = "UNKNOWN";

    /**
     *  debug log enable or not,.
     *
     * @return is debug log mode enabled or log
     */
    public boolean isDebugEnabled(){
        return true;
    }

    @Override
    public String getLogSource(){
        return logSource;
    }

    public ConsoleLog(String logSource) {
        this.logSource = logSource;
    }

    public ConsoleLog(){

    }

    /**
     * Help method to print on Console,.
     *
     */
    private static void consoleLog(String mode, Object log){
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date();

        if(log != null){
            System.out.println(String.format("$1%s %6s %s", dateFormat.format(date), mode, log));
        }
        else{
            System.out.println(String.format("$1%s $2%6s object is null", dateFormat.format(date), mode));
        }
    }

    private static void consoleLog(String mode, Throwable log){
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final Date date = new Date();

        if(log != null){
            System.out.println(String.format("$1%s $2%6s", dateFormat.format(date), mode));
            log.printStackTrace();
        }
        else{
            System.out.println(String.format("$1%s $2%6s object is null", dateFormat.format(date), mode));
        }
    }

    /**
     *  Log a message with debug log level.
     *
     * @param message log this message
     */
    public void debug(Object message){
        if(isDebugEnabled()){
            consoleLog("DEBUG", message);
        }
    }


    /**
     * Log an error with debug log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void debug(Object message, Throwable t){
        if(isDebugEnabled()){
            consoleLog("DEBUG", message);
            consoleLog("DEBUG", t);
        }
    }


    /**
     * Log a message with info log level.
     *
     * @param message log this message
     */
    public void info(Object message){
        consoleLog("INFO", message);
    }


    /**
     * Log an error with info log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void info(Object message, Throwable t){
        consoleLog("INFO", message);
        consoleLog("INFO", t);
    }


    /**
     * Log a message with error log level.
     *
     * @param message log this message
     */
    public void error(Object message){
        consoleLog("ERROR", message);
    }


    /**
     * Log an error with error log level.
     *
     * @param message log this message
     * @param t log this cause
     */
    public void error(Object message, Throwable t){
        consoleLog("ERROR", message);
        consoleLog("ERROR", t);
    }
}
