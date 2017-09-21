package com.zhaohaijie.NetMonitor.Utils;

import org.apache.commons.net.util.SubnetUtils;

public class NetUtils {
    public static String[] CIDRToIPArray(String subnet){
        SubnetUtils utils = new SubnetUtils(subnet);
        return utils.getInfo().getAllAddresses();
    }
}
