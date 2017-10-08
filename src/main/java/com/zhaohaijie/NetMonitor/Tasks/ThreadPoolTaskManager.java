package com.zhaohaijie.NetMonitor.Tasks;

import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;

/**
 * Created by ZHJ on 2/15/2017.
 */
public final class ThreadPoolTaskManager implements TaskManager {
    private TaskExecutor taskExecutor;
    private String threadNamePrefix;
    private int threadPriority = Thread.NORM_PRIORITY;
    private boolean daemon = false;
    private ThreadGroup threadGroup;
    private final AtomicInteger threadCount = new AtomicInteger(0);
    private static Log logger = LogFactory.getLog();
    private static ThreadPoolTaskManager threadPoolTaskManager = new ThreadPoolTaskManager();

    // get unique name for the thread.
    protected String getUniqueThreadName() {
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

    private ThreadPoolTaskManager(){
        taskExecutor = new ThreadPoolTaskExecutor(new ThreadFactory(){
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(new ThreadGroup("ThreadPoolGroup"), runnable, getUniqueThreadName());
                thread.setPriority(Thread.NORM_PRIORITY);                 // Just give normal priority
                thread.setDaemon(false);                                  // We don't want the thread to be daemon thread
                return thread;
            }
        }, new AbstractTaskRejected());
    }


    public void addTask(AbstractTask task) {
        if (task != null) {
            logger.debug("Add new task: " + task.getTaskId());
            taskExecutor.execute(task);
        }
    }

    public void addAllTasks(List<AbstractTask> tasks) {
        if (tasks != null) {
            logger.debug("Add total task: " + tasks.size());
            for (AbstractTask task: tasks) {
                taskExecutor.execute(task);
            }
        }
    }


    private void loadTaskBuild(){


    }

    public boolean startTaskManager() {
        try {
            TaskConfiguration config = new TaskConfiguration();
            List<TaskBuilder> taskBuilders = config.getTaskBuilders();
            logger.info("Size: " + taskBuilders.size());

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

    // Singleton

    /**
     * Return the singleton instance of ThreadPoolTaskManager.
     */
    public static ThreadPoolTaskManager getThreadPoolTaskManager(){
            return threadPoolTaskManager;
    }
}
