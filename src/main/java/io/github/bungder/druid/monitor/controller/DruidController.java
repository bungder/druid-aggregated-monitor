package io.github.bungder.druid.monitor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 谭仕昌
 * @date 2017-08-30 14:37
 */
@RestController("/ss")
public class DruidController {

    {
        System.out.println("99");
    }

    @RequestMapping("a")
    public String index(){
        return "egg";
    }


}
