package io.github.bungder.druid.monitor.servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 谭仕昌
 * @date 2017-08-30 14:35
 */
public class DruidInstancesNames {

    private static List<String> names;

    public static List<String> getNames() {
        List<String> copy = new ArrayList<>(names.size());
        for(String n : names){
            copy.add(n);
        }
        return copy;
    }

    protected static void setNames(List<String> names) {
        DruidInstancesNames.names = names;
    }
}
