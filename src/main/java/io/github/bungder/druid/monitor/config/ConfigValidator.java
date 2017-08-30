package io.github.bungder.druid.monitor.config;

import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 谭仕昌
 * @date 2017-08-30 14:06
 */
public class ConfigValidator {

    /**
     * 保留关键字
     */
    public static final Set<String> RESERVE_KEYS = Arrays.asList("css", "js", "instances").stream().collect(Collectors.toSet());

    public static void validate(DruidConfig config, Logger logger){
        if(CollectionUtils.isEmpty(config.getInstances())){
            logger.error("实例配置为空。程序终止。");
            System.exit(1);
        }
        for(DruidInstancesConfig instance : config.getInstances()){
            if(RESERVE_KEYS.contains(instance.getName())){
                logger.error(String.format("%s 是保留词，不允许使用，请修改druid的实例配置。", instance.getName()));
                System.exit(1);
            }
        }
    }

}
