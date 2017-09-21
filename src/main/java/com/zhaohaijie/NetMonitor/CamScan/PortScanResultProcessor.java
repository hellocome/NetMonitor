package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.AbstractTaskResultProcessor;
import com.zhaohaijie.NetMonitor.Tasks.TaskResult;

public class PortScanResultProcessor extends AbstractTaskResultProcessor {
    protected static Log logger = LogFactory.getLog();

    public void processTaskResult(TaskResult result) {
        try {


        } catch (Exception ex) {
            logger.error("Failed to processTaskResult", ex);
        }
    }
}
