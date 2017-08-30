package io.github.bungder.druid.monitor.util;

import com.alibaba.druid.util.StringUtils;

/**
 * @author 谭仕昌
 * @date 2017-08-30 09:26
 */
public class URLUtil {
    /**
     * 减去前面的nodeNum个节点
     *
     * @param url
     * @param nodeNum
     * @return 减去后的结果
     */
    public static String subtractPath(String url, int nodeNum) {
        int index = getPathNodeStartIndex(url, nodeNum);
        if (index <= 0) {
            return url;
        }
        return url.substring(index);
    }

    /**
     * 抽取前面的nodeNum个节点
     *
     * @param url
     * @param nodeNum
     * @return
     */
    public static String extractPath(String url, int nodeNum) {
        int index = getPathNodeStartIndex(url, nodeNum);
        if (index <= 0) {
            return "";
        }
        if(url.startsWith("/")){
            return url.substring(1, index);
        }
        return url.substring(0, index);
    }

    /**
     * 计算url第nodeNum个节点的开始下标（包括『/』）
     *
     * @param url
     * @param nodeNum 第几个节点（从1开始）
     * @return
     */
    public static int getPathNodeStartIndex(String url, int nodeNum) {
        if (nodeNum <= 0) {
            return -1;
        }
        String tmp = url;
        int index = -1;
        if (!StringUtils.isEmpty(url)) {
            if (url.startsWith("/")) {
                nodeNum++;
            }
            int count = 0;
            for (int i = 0; i < tmp.length(); i++) {
                if (tmp.charAt(i) == '/') {
                    count++;
                }
                if (count == nodeNum) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

}
