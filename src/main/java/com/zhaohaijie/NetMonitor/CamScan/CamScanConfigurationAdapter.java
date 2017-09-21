package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Tasks.TaskConfiguration;
import com.zhaohaijie.NetMonitor.Tasks.TaskWorker;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the configuration adapter for the github twitter search project.
 * it reads the project specific settings. we can separate this out of project if we want to
 * use the package separate.
 *
 * Created by ZHJ on 2/15/2017.
 */
public class CamScanConfigurationAdapter {
    private static final Log logger = LogFactory.getLog();
    private TaskConfiguration codeTestConfiguration;
    private static List<String> twitterSearchKeyword;
    private static final int DEFAULT_TASK_FREQUENCY = 5;
    private static final int DEFAULT_PAGE_UP_TO = 3;
    private static final int DEFAULT_TASK_FREQUENCY_MIN = 1;
    private static final boolean DEFAULT_TASK_BUILDER_LOOP = false;
    private final String taskName;

    private void init() throws ConfigurationException {
        codeTestConfiguration = new TaskConfiguration();
    }

    public CamScanConfigurationAdapter(String taskName){
        this.taskName = taskName;
    }

    /**
     *
     * @return get instance of CamScanConfigurationAdapter
     */
    public static CamScanConfigurationAdapter getConfigurationAdapter(String taskName){
        CamScanConfigurationAdapter configurationAdapter = null;

        try {
            configurationAdapter = new CamScanConfigurationAdapter(taskName);
            configurationAdapter.init();
        } catch (Exception ex) {
            logger.error("Failed to initialize CamScanConfigurationAdapter", ex);
            logger.error("default settings will be used");
        }

        return configurationAdapter;
    }


    public String getCIDRBlock(){
        String ipR = "";

        try {
            if(codeTestConfiguration != null) {
                ipR = codeTestConfiguration.getConfig().getString("//task[@name='" + taskName + "']/Scan/CIDRBlock");
            }
        }catch (Exception ex){
            logger.error("Failed to getCIDRBlock", ex);
        }

        logger.info("CIDRBlock: " + ipR);

        return ipR;
    }

    /**
     *
     * @return get search order, default is asc (asc or desc)
     */
    public String getPortList(){
        String ports = "";

        try {
            if(codeTestConfiguration != null) {
                ports = codeTestConfiguration.getConfig().getString("//task[@name='" + taskName + "']/Scan/PortList");
            }
        }catch (Exception ex){
            logger.error("Failed to getPortList", ex);
        }

        logger.info("GithubSearchOrder: " + ports);

        return ports;
    }


    /**
     * Load the setting once at app init stage. so that don't need to read the setting everytime
     */
    public List<TaskWorker> getTaskWorkers() {
        try {
            if (codeTestConfiguration != null) {
                return codeTestConfiguration.getTaskWorkers(taskName);
            }
        } catch (Exception ex) {
            logger.error("Failed to getGithubSearchKeyWord", ex);
        }

        return new LinkedList<>();
    }
}
