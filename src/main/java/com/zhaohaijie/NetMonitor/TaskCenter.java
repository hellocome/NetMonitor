package com.zhaohaijie.NetMonitor;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.*;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ZHJ on 2/15/2017.
 */
public final class TaskCenter implements TaskManager {
    private ThreadPoolTaskExecutor taskExecutor;
    private final AtomicInteger threadCount = new AtomicInteger(0);
    private static final Log logger = LogFactory.getLog();
    private static TaskCenter taskCenter = null;

    private TaskConfiguration config = null;

    // get unique name for the thread.
    private String getUniqueThreadName() {
        return "ThreadPoolTaskManager_" + this.threadCount.incrementAndGet();
    }

    static class AbstractTaskRejected implements RejectedExecutionHandler{
        public void rejectedExecution(Runnable worker, ThreadPoolExecutor executor) {
            if(worker instanceof AbstractTask) {
                logger.info("Task has been rejected! " + ((AbstractTask)worker).getTaskId());
            }
            else{
                logger.info("Task has been rejected! Unknown task");
            }
        }
    }

    private TaskCenter() throws ConfigurationException {
        config = new TaskConfiguration();

        taskExecutor = new ThreadPoolTaskExecutor(new ThreadFactory(){
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(new ThreadGroup("ThreadPoolGroup"), runnable, getUniqueThreadName());
                thread.setPriority(Thread.NORM_PRIORITY);                 // Just give normal priority
                thread.setDaemon(false);                                  // We don't want the thread to be daemon thread
                return thread;
            }
        }, new AbstractTaskRejected());

        taskExecutor.setCorePoolSize(config.getCorePoolSize());
    }


    /**
     * add task to the task queue
     *
     * @param task add task
     */
    public void addTask(AbstractTask task) {
        if (task != null) {
            logger.debug("Processing new task: " + task.getTaskId());
            taskExecutor.execute(task);
        }
    }

    /**
     * add tasks to the task queue
     *
     * @param tasks add tasks to the task queue
     */
    public void addAllTasks(List<AbstractTask> tasks) {
        if (tasks != null) {
            logger.debug("Add total task: " + tasks.size());
            for (AbstractTask task: tasks) {
                taskExecutor.execute(task);
            }
        }
    }


    /**
     * Start task
     * @return start task manager successful
     */
    public boolean startTaskManager() {
        try {
            List<TaskBuilder> taskBuilders = config.getTaskBuilders();

            for (TaskBuilder builder: taskBuilders){
                builder.setTaskManager(this);
                // if we want async add task, make the task build runnable
                // and do task build async.
                builder.startTaskBuilder();
            }

            return true;

        }catch (Exception ex){
            logger.error("Fail to start task manager: ", ex);
            return false;
        }
    }

    /**
     /**
     * Return the singleton instance of TaskCenter.
     *
     * @return Return the singleton instance of TaskCenter
     * @throws Exception if failed to build task center
     */
    public static TaskCenter getTaskCenter() throws Exception {
        if(taskCenter == null){
            taskCenter = new TaskCenter();
        }
        return taskCenter;
    }
}
