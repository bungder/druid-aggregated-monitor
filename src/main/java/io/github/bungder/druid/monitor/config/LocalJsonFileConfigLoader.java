package io.github.bungder.druid.monitor.config;

import com.alibaba.fastjson.JSON;
import io.github.bungder.druid.monitor.util.FileUtil;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author 谭仕昌
 * @date 2017-08-30 12:49
 */
public class LocalJsonFileConfigLoader implements ConfigLoader {

    private String filePath = "druid-instances.json";

    @Override
    public DruidConfig load() throws IOException {
        String text = FileUtil.readResourceFileText(filePath, "UTF-8");
        return parse(text);
    }

    protected DruidConfig parse(String text){
        if(StringUtils.isEmpty(text)){
            return new DruidConfig();
        }
        return JSON.parseObject(text, DruidConfig.class);
    }
}
