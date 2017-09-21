package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.TaskWorker;
import com.zhaohaijie.NetMonitor.Utils.PortScanner;

import java.net.InetSocketAddress;
import java.util.Observable;

public class PortScanWorker extends TaskWorker {

    protected static final Log logger = LogFactory.getLog();

    /**
     * Task is prepared, we are going to do the task
     * @param o According to the design o is Observable which is AbstractTask
     * @param arg According to the design o is args should be List<String>
     *            for safety reason, we just try the exception and log the error here.
     */
    public void update(Observable o, Object arg){

        try {
            InetSocketAddress address = (InetSocketAddress)arg;
            PortScanResult taskResult = processScan(address);

            if (taskResult != null) {
                notifyTaskResultProcessor(taskResult);
            }

        }catch (Exception ex){
            logger.error("Failed to notify all the processor", ex);
        }
    }


    private PortScanResult processScan(InetSocketAddress address) {
        boolean isOpen = PortScanner.isTcpPortOpen(address);
        return new PortScanResult(address, isOpen);
    }
}
