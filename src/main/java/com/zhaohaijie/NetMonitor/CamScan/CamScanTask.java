package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.AbstractTask;
import com.zhaohaijie.NetMonitor.Tasks.TaskWorker;

import java.net.InetSocketAddress;
import java.util.List;

public class CamScanTask extends AbstractTask {
    protected static Log logger = LogFactory.getLog();

    private String taskId;
    private InetSocketAddress socketAddress;

    @Override
    public void run() {
        try {
            this.setChanged();
            this.notifyObservers(this.socketAddress);
        } catch (Exception ex) {
            logger.error(String.format("Fail to run task: taskid=%s ip=%s:%d", taskId, this.socketAddress.getAddress(), this.socketAddress.getPort()));
            logger.error("Fail to run task, reason: ", ex);
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public CamScanTask(String ip, int port, String taskId) {
        this.taskId = taskId;
        socketAddress = new InetSocketAddress(ip, port);
    }

    public CamScanTask(InetSocketAddress socketAddress, String taskId) {
        this.taskId = taskId;
        this.socketAddress = socketAddress;
    }

    public void registerWorker(TaskWorker worker) {
        this.addObserver(worker);
    }

    public void registerWorkers(List<TaskWorker> workers) {
        //logger.info("registerWorkers");
        if (workers != null) {
            for (TaskWorker worker : workers) {
                // logger.info("registerWorkers: " + worker);
                registerWorker(worker);
            }
        }
    }
}