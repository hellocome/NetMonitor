package com.zhaohaijie.NetMonitor.CamScan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZHJ on 10/8/2017.
 */
public class ResultFileWritter {
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
        File path = new File(new File(".").getCanonicalPath() + File.separator + SAVETO +
                File.separator+ fileName);
        File file = new File(path.getAbsolutePath() + File.separator+ fileName);

        if(!path.exists() || !path.isDirectory()){
            path.mkdir();
        }

        if(!file.exists()){
            file.createNewFile();
        }

        writter = new FileWriter(file, false);
    }

    private static String getDateTimeNow(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }


    public void writeResult(PortScanResult result) throws IOException{
        writter.write(String.format("%s:%d=%s", result.getAddress().getAddress(), result.getAddress().getPort(), Boolean.toString(result.IsPortOpen())));
    }
}
