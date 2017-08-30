import com.alibaba.fastjson.JSON;
import io.github.bungder.druid.monitor.config.DruidConfig;
import io.github.bungder.druid.monitor.config.DruidInstancesConfig;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author 谭仕昌
 * @date 2017-08-30 13:43
 */
public class ConfigSample {

    @Test
    public void test() {
        DruidConfig config = new DruidConfig("root", "root",
                Arrays.asList(new DruidInstancesConfig("app", "service:jmx:rmi:///jndi/rmi://127.0.0.1:9876/jmxrmi")), null, null);
        System.out.println(JSON.toJSONString(config, true));
    }

    @Test
    public void testReplace(){
        String a = "cccccc<%ddd%>cccccc";
        System.out.println(a.replaceAll("<%ddd%>", "OKOK"));
    }

}
