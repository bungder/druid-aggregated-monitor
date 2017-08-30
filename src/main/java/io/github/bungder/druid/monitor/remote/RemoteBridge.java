package io.github.bungder.druid.monitor.remote;

import com.alibaba.druid.stat.DruidStatService;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 谭仕昌
 * @date 2017-08-30 08:14
 */
public class RemoteBridge {
    private final static Log LOG = LogFactory.getLog(RemoteBridge.class);
    private MBeanServerConnection conn = null;
    private String jmxUrl = null;
    /**
     * web.xml中配置的jmx的用户名
     */
    private String jmxUsername = null;
    /**
     * web.xml中配置的jmx的密码
     */
    private String jmxPassword = null;

    public RemoteBridge(String jmxUrl) throws IOException {
        this.jmxUrl = jmxUrl;
        init();
    }

    public RemoteBridge(String jmxUrl, String jmxUsername, String jmxPassword) throws IOException {
        this.jmxUrl = jmxUrl;
        this.jmxUsername = jmxUsername;
        this.jmxPassword = jmxPassword;
        init();
    }

    private void init() throws IOException {
        initJmxConn();
    }

    /**
     * 初始化jmx连接
     *
     * @throws IOException
     */
    private void initJmxConn() throws IOException {
        if (jmxUrl != null) {
            JMXServiceURL url = new JMXServiceURL(jmxUrl);
            Map<String, String[]> env = null;
            if (jmxUsername != null) {
                env = new HashMap<String, String[]>();
                String[] credentials = new String[]{jmxUsername, jmxPassword};
                env.put(JMXConnector.CREDENTIALS, credentials);
            }
            LOG.info("连接JMX =======> " + JSON.toJSONString(env));
            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
            conn = jmxc.getMBeanServerConnection();
        }
    }

    private String getJmxResult(MBeanServerConnection conn, String url) throws Exception {
        ObjectName name = new ObjectName(DruidStatService.MBEAN_NAME);

        String result = (String) conn.invoke(name, "service", new String[]{url},
                new String[]{String.class.getName()});
        return result;
    }

    public String process(String url) {
        String resp = null;
        if (conn == null) {// 连接在初始化时创建失败
            try {// 尝试重新连接
                initJmxConn();
            } catch (IOException e) {
                LOG.error("init jmx connection error", e);
                resp = DruidStatService.returnJSONResult(DruidStatService.RESULT_CODE_ERROR,
                        "init jmx connection error" + e.getMessage());
            }
            if (conn != null) {// 连接成功
                try {
                    resp = getJmxResult(conn, url);
                } catch (Exception e) {
                    LOG.error("get jmx data error", e);
                    resp = DruidStatService.returnJSONResult(DruidStatService.RESULT_CODE_ERROR, "get data error:"
                            + e.getMessage());
                }
            }
        } else {// 连接成功

            try {
                resp = getJmxResult(conn, url);
            } catch (Exception e) {
                LOG.error("get jmx data error", e);
                resp = DruidStatService.returnJSONResult(DruidStatService.RESULT_CODE_ERROR,
                        "get data error" + e.getMessage());
                // 连接失败之后，MBeanServerConnection实例的terminated属性已经设为true了，但是并没有提供方法进行判断，
                // 先将conn置空，不然不会触发重连
                conn = null;
            }
        }
        return resp;
    }

}
