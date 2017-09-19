package com.zhaohaijie.NetMonitor.Tasks;

/**
 * Created by ZHJ on 16/02/2017.
 */
public interface TaskResultProcessor {
    void addFilter(Filter newFilter);
    void processTaskResult(TaskResult result);
}
