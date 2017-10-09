package com.zhaohaijie.NetMonitor.CamScan;

import com.zhaohaijie.NetMonitor.Tasks.TaskResult;

import java.net.InetSocketAddress;

public class PortScanResult implements TaskResult<Boolean> {
    private InetSocketAddress address;
    private boolean isPortOpen;

    public PortScanResult(InetSocketAddress address, boolean isPortOpen){
        this.address = address;
        this.isPortOpen = isPortOpen;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public boolean IsPortOpen() {
        return isPortOpen;
    }

    public Boolean getResultObject(){
        return isPortOpen;
    }


    public  Object getTaskObject(){
        return null;
    }

    @Override
    public String toString(){
        return String.format("%s:%d = %s", address.getAddress(), address.getPort(), Boolean.toString(isPortOpen));
    }
}
