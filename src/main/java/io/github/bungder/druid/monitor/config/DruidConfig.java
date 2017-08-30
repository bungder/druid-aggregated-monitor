package io.github.bungder.druid.monitor.config;

import java.io.Serializable;
import java.util.List;

/**
 * @author 谭仕昌
 * @date 2017-08-30 13:15
 */
public class DruidConfig implements Serializable {

    public static final String VIEW_PATH = "aggregation";

    private static final long serialVersionUID = 7841684682237301350L;
    /** 登录名 */
    private String username;
    /** 密码 */
    private String password;
    /** IP白名单 */
    private List<String> allowList;
    /** IP黑名单 */
    private List<String> denyList;

    private List<DruidInstancesConfig> instances;

    public DruidConfig(String username, String password, List<DruidInstancesConfig> instances, List<String> allowList, List<String> denyList) {
        this.username = username;
        this.password = password;
        this.instances = instances;
        this.allowList = allowList;
        this.denyList = denyList;
    }

    public DruidConfig() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<DruidInstancesConfig> getInstances() {
        return instances;
    }

    public void setInstances(List<DruidInstancesConfig> instances) {
        this.instances = instances;
    }

    public List<String> getAllowList() {
        return allowList;
    }

    public void setAllowList(List<String> allowList) {
        this.allowList = allowList;
    }

    public List<String> getDenyList() {
        return denyList;
    }

    public void setDenyList(List<String> denyList) {
        this.denyList = denyList;
    }
}
