package com.zhaohaijie.NetMonitor.Tasks;

import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ZHJ on 16/02/2017.
 */
public abstract class TaskWorker implements Observer {
    private final ConcurrentLinkedQueue<TaskResultProcessor> queue = new ConcurrentLinkedQueue<>();

    public void registerTaskResultProcessor(TaskResultProcessor processor){
        if(!queue.contains(processor)){
            queue.add(processor);
        }
    }

    protected <T> void notifyTaskResultProcessor(TaskResult<T> result){
        for (TaskResultProcessor processor: queue) {
            processor.processTaskResult(result);
        }
    }
}
