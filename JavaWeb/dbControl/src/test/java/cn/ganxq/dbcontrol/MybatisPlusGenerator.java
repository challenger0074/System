package cn.ganxq.dbcontrol;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;


public class MybatisPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/file_storage?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai",
                        "root", "root")
                .globalConfig(builder -> {
                    builder.author("ganxq") // 设置作者
                            .enableSwagger() // 开启 swagger 模式

//                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() // 禁止打开输出目录
                            .outputDir("C:\\project\\github\\ganxqSYS\\JavaWeb\\dbControl\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("cn.ganxq.dbcontrol") // 设置父包名
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml,
                                    "C:\\project\\github\\ganxqSYS\\JavaWeb\\dbControl\\src\\main\\resources\\mapper")) // 设置mapperXml生成路径
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml("mappers"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("upload_music") // 设置需要生成的表名
                            .entityBuilder().enableFileOverride()//覆盖已生成的实体文件
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .mapperBuilder()
                            /*.enableRestStyle()*/; // 启用 REST 风格

                })
                 // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

/*代码覆盖需要在每一个生成策略后加enableFileOverride()方法,不加的策略不覆盖*/
}
