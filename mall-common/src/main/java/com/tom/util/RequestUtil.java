package com.tom.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *请求工具类
 * Created by tom on 2020/11/11
 */
public class RequestUtil {
    public static String getRequestIp(HttpServletRequest request) {
        String ipAdress = request.getHeader("x-forwarded-for");
        if (ipAdress == null || ipAdress.length() == 0 || "unknown".equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAdress == null || ipAdress.length() == 0 || "unknown".equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAdress == null || ipAdress.length() == 0 || "unknown".equalsIgnoreCase(ipAdress)) {
            ipAdress = request.getRemoteUser();
            if ("127.0.0.1".equals(ipAdress) || "0:0:0:0:0:0:0:1".equals(ipAdress)) {
                try {
                    InetAddress inetAddress=InetAddress.getLocalHost();
                    ipAdress = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        if (ipAdress!=null&&ipAdress.length()>15) {
            if (ipAdress.contains(",")) {
                ipAdress = ipAdress.split(",")[0];
            }
        }
        return ipAdress;
    }
}
