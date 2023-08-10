package com.ysgsignin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class QianDaoApplication{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(QianDaoApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  学生考勤系统启动成功    ლ(´ڡ`ლ)ﾞ  \n");
    }
}
