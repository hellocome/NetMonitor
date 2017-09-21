package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.AbstractTask;
import com.zhaohaijie.NetMonitor.Tasks.TaskManager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TaskDispatcher is used dispatch the tasks
 * Created by ZHJ on 2/17/2017.
 */
class TaskDispatcher implements Runnable {
    private static final Log logger = LogFactory.getLog();
    private boolean run = true;
    private final AtomicInteger count = new AtomicInteger(0);
    // Concurrent queue, so we don't need to use synchronze or locker
    private final BlockingQueue<AbstractTask> taskQueue = new LinkedBlockingQueue<>();
    private TaskManager taskManager = null;
    public TaskDispatcher(TaskManager taskManager){
        this.taskManager = taskManager;
    }

    public void run() {
        try {
            while (run) {
                AbstractTask task = this.taskQueue.take();
                this.taskManager.addTask(task);
            }
        } catch (InterruptedException e) {
            logger.error("IniTaskBuild", e);
        }
    }

    /**
     *
     * @return Get task queue
     */
    public BlockingQueue<AbstractTask> getTaskQueue(){
        return taskQueue;
    }

    /**
     *
     * @param task Add task into concurrent task queue
     */
    public void add(AbstractTask task) {
        taskQueue.add(task);
    }

    public void Stop() {
        run = false;
    }
}
