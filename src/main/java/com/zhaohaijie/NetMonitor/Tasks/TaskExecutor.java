package com.zhaohaijie.NetMonitor.Tasks;

import java.util.concurrent.Future;

/**
 * Created by ZHJ on 2/15/2017.
 */
public interface TaskExecutor {
    void start();
    void execute(Runnable task);
    Future<?> submit(Runnable task);
}
