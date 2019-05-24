package com.wafersystems.virsical.map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 代码生成，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 *
 * @author tandk
 * @date 2019/4/30 10:57
 */
@Slf4j
@UtilityClass
public class CodeGenerator {
  /**
   * 如果模板引擎是 freemarker
   */
  private final String templatePath = "/templates/mapper.xml.ftl";

  /**
   * <p>
   * 读取控制台内容
   * </p>
   */
  public static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in, "UTF-8");
    StringBuilder help = new StringBuilder();
    help.append("请输入" + tip + "：");
    log.info(help.toString());
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotEmpty(ipt)) {
        return ipt;
      }
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }

  /**
   * 代码生成入口（使用时 create() 改成 main(String[] args) 方法）
   */
  public static void create() {
    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/src/main/java");
    gc.setAuthor("tandk");
    gc.setFileOverride(true);
    gc.setOpen(false);
    gc.setActiveRecord(true);
    gc.setBaseResultMap(true);
    gc.setBaseColumnList(true);
    gc.setIdType(IdType.AUTO);
    //实体属性 Swagger2 注解
    gc.setSwagger2(true);
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();
    mpg.setGlobalConfig(gc);
    DataSourceConfig dsc = getDataSourceConfig();
    mpg.setDataSource(dsc);

    // 包配置
    PackageConfig pc = new PackageConfig();
    pc.setParent("com.wafersystems.virsical.map");
    mpg.setPackageInfo(pc);

    // 自定义配置
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        // to do nothing
      }
    };

    // 自定义输出配置
    List<FileOutConfig> focList = new ArrayList<>();
    // 自定义配置会被优先输出
    focList.add(new FileOutConfig(templatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
        return projectPath + "/src/main/resources/mapper/"
          + (pc.getModuleName() == null ? "" : pc.getModuleName() + "/")
          + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
      }
    });
    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);

    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig();

    // 配置自定义输出模板
    //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
    templateConfig.setEntity("custom-templates/entity.java");
    templateConfig.setController("custom-templates/controller.java");

    templateConfig.setXml(null);
    mpg.setTemplate(templateConfig);
    StrategyConfig strategy = getStrategyConfig(pc);

    mpg.setStrategy(strategy);
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }

  /**
   * 代码生成策略配置
   *
   * @param pc PackageConfig
   * @return StrategyConfig
   */
  private static StrategyConfig getStrategyConfig(PackageConfig pc) {
    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setLogicDeleteFieldName("del_flag");
    strategy.setSuperEntityClass("");
    strategy.setEntityLombokModel(true);
    strategy.setRestControllerStyle(true);
    strategy.setSuperControllerClass("com.wafersystems.virsical.map.common.BaseController");
    strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
    strategy.setSuperEntityColumns("id");
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setTablePrefix(pc.getModuleName() + "_");
    return strategy;
  }

  /**
   * 数据库配置
   *
   * @return DataSourceConfig
   */
  private static DataSourceConfig getDataSourceConfig() {
    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://localhost:3306/virsical_map?characterEncoding=utf8"
      + "&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true"
      + "&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true");
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("root");
    return dsc;
  }

}