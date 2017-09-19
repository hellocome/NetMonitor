package com.zhaohaijie.NetMonitor.Utils;

import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner {
    private static final int CONNECTION_TIMEOUT = 5 * 1000;

    public static boolean isTcpPortOpen(String host, int port){
        InetSocketAddress address = new InetSocketAddress(host, port);
        return isTcpPortOpen(address);
    }

    public static boolean isTcpPortOpen(InetSocketAddress address){
        try (Socket socket = new Socket()){
            socket.connect(address, CONNECTION_TIMEOUT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
