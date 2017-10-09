package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZHJ on 10/8/2017.
 */
public class ResultFileWritter {
    protected static Log logger = LogFactory.getLog();
    private static String SAVETO = "results";
    private static String fileName;
    private FileWriter writter;
    private static ResultFileWritter writterInstance;
    public static ResultFileWritter getInstance() throws IOException{
        if(writterInstance == null){
            synchronized (ResultFileWritter.class){
                if(writterInstance == null) {
                    writterInstance = new ResultFileWritter();
                }
            }
        }

        return writterInstance;
    }

    private ResultFileWritter() throws IOException {
        fileName = String.format("%s.txt", getDateTimeNow());
        logger.info("File: " + fileName);
        File path = new File(new File(".").getCanonicalPath() + File.separator + SAVETO);
        File file = new File(path.getAbsolutePath() + File.separator+ fileName);
        logger.info("File Abs: " + file.getAbsolutePath());

        if(!path.exists() || !path.isDirectory()){
            logger.info("Dir: " + path.getAbsolutePath());
            path.mkdir();
        }

        if(!file.exists()){
            logger.info("Create: " + file.getAbsolutePath());
            file.createNewFile();
        }

        writter = new FileWriter(file, false);
    }

    private static String getDateTimeNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }


    public synchronized void writeResult(PortScanResult result) throws IOException{
        writter.write(String.format("%s:%d=%s\r\n", result.getAddress().getHostName(),
                result.getAddress().getPort(), Boolean.toString(result.IsPortOpen())));
        writter.flush();
    }
}
