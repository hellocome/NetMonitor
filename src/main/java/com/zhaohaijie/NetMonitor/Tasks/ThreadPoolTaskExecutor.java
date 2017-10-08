package com.zhaohaijie.NetMonitor.Tasks;

import java.util.concurrent.*;

/**
 * Created by ZHJ on 2/15/2017.
 *
 * @author ZHJ
 * @since 1.0
 * @see ThreadPoolExecutor
 */
public class ThreadPoolTaskExecutor implements TaskExecutor {
    private final Object poolSizeMonitor = new Object();

    // corePoolSize - the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
    //                we can make this configurable, just set it as default value because want to finish this code test as soon as possible.
    private int corePoolSize = 3;

    // maximumPoolSize - the maximum number of threads to allow in the pool
    private final int maxPoolSize = Integer.MAX_VALUE;

    // keepAliveTime - when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
    //                 we can make this configurable, just set it as default value because want to finish this code test as soon as possible.
    private final int keepAliveSeconds = 60;
    private final int queueCapacity = Integer.MAX_VALUE;
    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadFactory threadFactory;
    private RejectedExecutionHandler rejectedExecutionHandler;

    public int getKeepAliveSeconds() {
        synchronized (this.poolSizeMonitor) {
            return this.keepAliveSeconds;
        }
    }

    public int getQueueCapacity() {
        synchronized (this.poolSizeMonitor) {
            return this.queueCapacity;
        }
    }
    /**
     * Return the ThreadPoolExecutor's core pool size.
     */
    public int getCorePoolSize() {
        synchronized (this.poolSizeMonitor) {
            return this.corePoolSize;
        }
    }

    public void setCorePoolSize(int size) {
        synchronized (this.poolSizeMonitor) {
            if(size > 0) {
                this.corePoolSize = size;
            }
        }
    }

    /**
     * Return the ThreadPoolExecutor's maximum pool size.
     */
    public int getMaxPoolSize() {
        synchronized (this.poolSizeMonitor) {
            return this.maxPoolSize;
        }
    }

    /**
     * Return the current pool size.
     * @see ThreadPoolExecutor#getPoolSize()
     */
    public int getPoolSize() {
        if(this.threadPoolExecutor == null){
            // Not initialized, assume 0 pool size.
            return 0;
        }

        return this.threadPoolExecutor.getPoolSize();
    }

    /**
     * Get active count of the threads
     * @return Get the number of active threads
     */
    public int getActiveCount() {
        if (this.threadPoolExecutor == null) {
            // Not initialized, assume 0 active count.
            return 0;
        }

        return this.threadPoolExecutor.getActiveCount();
    }

    /**
     * Return task queue.
     */
    private BlockingQueue<Runnable> createBlockingQueue(int queueCapacity) {
        return new LinkedBlockingQueue<>(queueCapacity);
    }


    protected void initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {

        BlockingQueue<Runnable> queue = createBlockingQueue(this.queueCapacity);

        this.threadPoolExecutor = new ThreadPoolExecutor(
                    this.corePoolSize, this.maxPoolSize, this.keepAliveSeconds, TimeUnit.SECONDS,
                    queue, threadFactory, rejectedExecutionHandler);
    }


    protected ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        if(this.threadPoolExecutor == null){
            throw new IllegalStateException("ThreadPoolTaskExecutor not initialized");
        }

        return this.threadPoolExecutor;
    }

    public ThreadPoolTaskExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler){
        this.threadFactory = threadFactory;
        this.rejectedExecutionHandler = rejectedExecutionHandler;
   }

    public void start(){
        initializeExecutor(threadFactory, rejectedExecutionHandler);
    }

    @Override
    public void execute(Runnable task) throws RejectedExecutionException{
        Executor executor = getThreadPoolExecutor();

        try {
            executor.execute(task);
        }
        catch (RejectedExecutionException ex) {
            throw new RejectedExecutionException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public Future<?> submit(Runnable task) {
        ExecutorService executor = getThreadPoolExecutor();

        try {
            return executor.submit(task);
        }
        catch (RejectedExecutionException ex) {
            throw new RejectedExecutionException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }
}
