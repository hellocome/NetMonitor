package com.zhaohaijie.NetMonitor.Tasks;

import java.util.Observer;

/**
 * Created by ZHJ on 16/02/2017.
 */
public abstract class TaskWorker implements Observer {
    public abstract void registerTaskResultProcessor(TaskResultProcessor processor);
}
