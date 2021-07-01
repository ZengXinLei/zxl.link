package com.example.demo;

/**
 * @className: com.red3.app.common.GeneratorCode
 * @description: TODO
 * @author: zxl
 * @create: 2021-07-02 0:28
 */


import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GeneratorCode {


    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");  //生成路径(一般都是生成在此项目的src/main/java下面)
        gc.setAuthor("zxl"); //设置作者
        gc.setOpen(false);
        gc.setFileOverride(true); //第二次生成会把第一次生成的覆盖掉
        gc.setServiceName("Z%sService"); //生成的service接口名字首字母是否为I，这样设置就没有
        gc.setBaseResultMap(true); //生成resultMap
        mpg.setGlobalConfig(gc);

        //2、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/zengblog?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root123");
        mpg.setDataSource(dsc);

        // 3、包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("sys");
        pc.setParent("com.example.demo");
        mpg.setPackageInfo(pc);

        // 4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperControllerClass("com.example.demo.controller");
//        strategy.setSuperServiceClass("com.example.demo.service");
//        strategy.setSuperMapperClass("com.example.demo.dao");
//        strategy.setSuperEntityClass("com.example.demo.entity");
        // strategy.setTablePrefix("t_"); // 表名前缀
        strategy.setEntityLombokModel(true); //使用lombok
        String[] tables={"user","user_role","role"};
        strategy.setInclude(tables);  // 逆向工程使用的表   如果要生成多个,这里可以传入String[]
        mpg.setStrategy(strategy);

        //5、执行
        mpg.execute();
    }

}