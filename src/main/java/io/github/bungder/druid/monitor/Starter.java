package io.github.bungder.druid.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@ServletComponentScan("io.github.bungder.druid.monitor")
@ComponentScan({"io.github.bungder.druid.monitor"})
@RestController
@EnableAutoConfiguration
public class Starter {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");

    @RequestMapping("/")
    String home() {
        return "<h1>Druid Monitor!</h1><br/>" + sdf.format(new Date());
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Starter.class, args);
    }




}