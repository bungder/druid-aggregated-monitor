package io.github.bungder.druid.monitor.config;

/**
 * 配置信息加载器
 * @author 谭仕昌
 * @date 2017-08-30 12:42
 */
public interface ConfigLoader
{

    /**
     * 加载配置信息
     * @return
     */
    DruidConfig load() throws Exception;

}
