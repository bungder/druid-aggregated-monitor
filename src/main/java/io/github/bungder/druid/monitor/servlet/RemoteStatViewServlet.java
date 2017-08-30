package io.github.bungder.druid.monitor.servlet;

import com.alibaba.druid.support.http.util.IPRange;
import io.github.bungder.druid.monitor.config.ConfigLoader;
import io.github.bungder.druid.monitor.config.ConfigValidator;
import io.github.bungder.druid.monitor.config.DruidConfig;
import io.github.bungder.druid.monitor.config.DruidInstancesConfig;
import io.github.bungder.druid.monitor.remote.RemoteBridge;
import io.github.bungder.druid.monitor.util.FileUtil;
import io.github.bungder.druid.monitor.util.URLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 谭仕昌
 * @date 2017-08-30 08:09
 */
@WebServlet(urlPatterns = "/" + DruidConfig.VIEW_PATH + "/*")
public class RemoteStatViewServlet extends AggregatedResourceServlet {

    private Logger logger = LoggerFactory.getLogger(RemoteStatViewServlet.class);

    private Map<String, RemoteBridge> bridgeMap = new LinkedHashMap<>();
    private List<String> names;
    private ConfigLoader configLoader;

    public RemoteStatViewServlet() {
        super("support/http/resources");
        init();
    }

    public void init(){
        try {
            ServiceLoader<ConfigLoader> serviceLoader = ServiceLoader.load(ConfigLoader.class);
            Iterator<ConfigLoader> iterator =  serviceLoader.iterator();
            if(iterator.hasNext()){
                configLoader = iterator.next();
            }else{
                logger.error("Failed to load " + ConfigLoader.class.getName() + " instance");
                System.exit(1);
            }
            DruidConfig config = configLoader.load();
            ConfigValidator.validate(config, logger);

            if(!StringUtils.isEmpty(config.getUsername())) {
                this.username = config.getUsername();
            }
            if(!StringUtils.isEmpty(config.getPassword())) {
                this.password = config.getPassword();
            }
            if(!CollectionUtils.isEmpty(config.getAllowList())){
                this.allowList = new ArrayList<>(config.getAllowList().size());
                config.getAllowList().stream().forEach(ip -> {
                    this.allowList.add(new IPRange(ip));
                });
            }
            if(!CollectionUtils.isEmpty(config.getDenyList())){
                this.denyList = new ArrayList<>(config.getDenyList().size());
                config.getDenyList().stream().forEach(ip -> {
                    this.denyList.add(new IPRange(ip));
                });
            }

            if(!CollectionUtils.isEmpty(config.getInstances())){
                names = new ArrayList<>(config.getInstances().size());
                for(DruidInstancesConfig instance : config.getInstances()){
                    bridgeMap.put(instance.getName(), new RemoteBridge(instance.getJmxUrl()));
                    names.add(instance.getName());
                }
                Collections.sort(names);
            }
        } catch (Exception e) {
            logger.error("初始化Druid配置信息失败", e);
            System.exit(1);
        }
    }

    @Override
    protected void returnResourceFile(String fileName, String uri, HttpServletResponse response)
            throws ServletException,
            IOException {
        int nodeNum = 1;
        String key = URLUtil.extractPath(fileName, nodeNum);
        if(bridgeMap.containsKey(key)) {
            String realFileName = URLUtil.subtractPath(fileName, nodeNum);
            if(StringUtils.isEmpty(realFileName) || "/".equals(realFileName)){
                realFileName = "/index.html";
            }
            super.returnResourceFile(realFileName, uri, response);
        }else{
            super.returnResourceFile(fileName, uri, response);
        }
    }

    @Override
    protected String process(String url) {
        int nodeNum = 1;
        String key = URLUtil.extractPath(url, nodeNum);
        RemoteBridge bridge = bridgeMap.get(key);
        if (bridge != null) {
            return bridge.process(URLUtil.subtractPath(url, nodeNum));
        }
        return null;
    }

    @Override
    public String listInstances() throws IOException {
        String result = FileUtil.readResourceFileText("pages/instances.html", "UTF-8");
        result = result.replaceAll("<%prefix%>", "/" + DruidConfig.VIEW_PATH + "/");
        String list = "";
        for(int i = 0; i < names.size(); i++){
            String name = names.get(i);
            i++;
            String link = "/" + DruidConfig.VIEW_PATH + "/" + name + "/";
            String line = String.format("<tr><td>%d</td><td><a href=\"%s\" target=\"_blank\">%s</td></tr>", i, link, name);
            list += line;
        }
        result = result.replaceAll("<%table_list%>", list);
        return result;
    }
}
