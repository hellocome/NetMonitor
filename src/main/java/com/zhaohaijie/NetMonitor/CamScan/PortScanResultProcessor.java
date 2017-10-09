package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.AbstractTaskResultProcessor;
import com.zhaohaijie.NetMonitor.Tasks.TaskResult;

public class PortScanResultProcessor extends AbstractTaskResultProcessor {
    protected static Log logger = LogFactory.getLog();
    public static int count = 0;
    public static int openCount = 0;

    public void processTaskResult(TaskResult result) {
        try {
            PortScanResult res = (PortScanResult)result;
            count++;

            if(res.IsPortOpen()) {
                openCount++;
                logger.info(result);
                ResultFileWritter.getInstance().writeResult(res);
                logger.info("Processed: " + count + " Open: " + openCount);
            }
            else{
                if(count % 500 == 0){
                    logger.info("Processed: " + count + " Open: " + openCount);
                }
            }
        } catch (Exception ex) {
            logger.error("Failed to processTaskResult", ex);
        }
    }
}
