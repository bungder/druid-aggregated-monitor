package io.github.bungder.druid.monitor.config;

import java.io.Serializable;

/**
 * Druid连接池实例信息配置
 * @author 谭仕昌
 * @date 2017-08-30 12:42
 */
public class DruidInstancesConfig implements Serializable {
    private static final long serialVersionUID = 2338432978676737881L;

    /** 实例名称 */
    private String name;
    /** Druid连接池实例对应的JMX URL */
    private String jmxUrl;

    public DruidInstancesConfig(String name, String jmxUrl) {
        this.name = name;
        this.jmxUrl = jmxUrl;
    }

    public DruidInstancesConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJmxUrl() {
        return jmxUrl;
    }

    public void setJmxUrl(String jmxUrl) {
        this.jmxUrl = jmxUrl;
    }
}
