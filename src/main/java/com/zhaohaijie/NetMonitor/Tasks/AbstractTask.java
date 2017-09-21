package com.zhaohaijie.NetMonitor.Tasks;


import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;

import java.util.List;
import java.util.Observable;

/**
 * Created by ZHJ on 2/15/2017.
 */
public abstract class AbstractTask extends Observable implements Runnable {
    protected static final Log logger = LogFactory.getLog();

    public abstract String getTaskId();
    public abstract void registerWorker(TaskWorker worker);
    public abstract void registerWorkers(List<TaskWorker> workers);
}
