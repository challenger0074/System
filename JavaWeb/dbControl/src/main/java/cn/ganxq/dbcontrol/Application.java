package cn.ganxq.dbcontrol;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author：ganxq
 */
@MapperScan(basePackages = {"cn.ganxq.dbcontrol.mapper"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

}
