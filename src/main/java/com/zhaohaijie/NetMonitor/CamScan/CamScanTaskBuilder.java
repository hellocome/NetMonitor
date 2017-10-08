package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.AbstractTask;
import com.zhaohaijie.NetMonitor.Tasks.TaskBuilder;
import com.zhaohaijie.NetMonitor.Tasks.TaskManager;
import com.zhaohaijie.NetMonitor.Tasks.TaskWorker;
import com.zhaohaijie.NetMonitor.Utils.NetUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CamScanTaskBuilder implements TaskBuilder {

    private static final Log logger = LogFactory.getLog();
    private final AtomicInteger idCount = new AtomicInteger(0);
    private CamScanConfigurationAdapter configurationAdapter;
    private TaskManager taskManager = null;
    private Thread taskDispatcherThread = null;
    private TaskDispatcher taskDispatcher = null;
    private String taskName;
    private boolean run = false;


    /**
     * initTaskBuild
     */
    private void initTaskBuild() {

        try {
            configurationAdapter = CamScanConfigurationAdapter.getConfigurationAdapter(taskName); // configuration adapter for read project specific setting
            taskDispatcher = new TaskDispatcher(taskManager);
            taskDispatcherThread = new Thread(taskDispatcher); // create a spearate thread for the task dispatcher.
            taskDispatcherThread.start();
        } catch (Exception ex) {
            logger.error("IniTaskBuild", ex);
        }
    }

    /**
     * return a unique task id
     * @return return a unique task id/name
     */
    private String generateNextTaskId(){
        return "GithubTwitterSearchTask_" + this.idCount.incrementAndGet();
    }

    public synchronized void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public synchronized void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public synchronized String getTaskName() {
        return this.taskName;
    }

    /**
     * Stop task builder
     */
    @Override
    public void stopTaskBuilder() {
        try {
            run = false;
        } catch (Exception e) {
            logger.error("stopTaskBuilder", e);
        }
    }

    /**
     * Start task builder
     */
    @Override
    public void startTaskBuilder() {
        if(!run && taskManager != null) {
            run = true;
            initTaskBuild();
            buildTasks();
        }
    }

    /**
     * build tasks and push into the queue
     */
    private void buildTasks() {
        try {
            List<TaskWorker> workers = configurationAdapter.getTaskWorkers();
            ArrayList<InetSocketAddress> addresses = getInetSocketAddressList();
            Integer id = 0;

            for (final InetSocketAddress address : addresses) {
                // logger.info("Build task: " + address);
                CamScanTask task = new CamScanTask(address, (id++).toString());
                task.registerWorkers(workers);

                taskDispatcher.add(task);
            }

        }catch (Exception ex){
            logger.error("Fail to build tasks", ex);
        }
    }

    /**
     *
     * for future use.
     *
     * @return return next Task
     * @throws NotImplementedException not implemented
     */
    public AbstractTask buildNextTask() throws NotImplementedException{
        throw new NotImplementedException();
    }


    private ArrayList<InetSocketAddress> getInetSocketAddressList(){
        String CIDRBlock = configurationAdapter.getCIDRBlock();
        String[] portlist = configurationAdapter.getPortList().split(",");
        String[] ips = NetUtils.CIDRToIPArray(CIDRBlock);

        ArrayList<Integer> ports = new ArrayList<Integer>();
        ArrayList<InetSocketAddress> addresses = new ArrayList<>();


        for(String port: portlist){
            ports.add(Integer.parseInt(port.trim()));
        }

        for(String ip: ips){
            for(int port: ports){
                addresses.add(new InetSocketAddress(ip, port));
            }
        }

        return addresses;
    }
}
