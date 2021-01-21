package indi.biz.yufr.codegen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.util.*;

/**
 * CodeGenerator ,切换你的数据库为mysql(不可为内存性h2),否则无法生成
 *
 * @date: 2020/12/31 11:40
 * @author: farui.yu
 */
@Slf4j
public class CodeGenerator {

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

//        generateBase();

        // 代码生成器
        new AutoGenerator()
                .setGlobalConfig(getGlobalConfig())
                .setDataSource(getDataSourceConfig())
                .setPackageInfo(getPackageConfig())
                .setCfg(getInjectionConfig())
                .setTemplate(getTemplateConfig())
                .setStrategy(getStrategyConfig())
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static void generateBase() {
        FreemarkerTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        templateEngine.init(null);
        String base = CodeConstant.PROJECT_PATH
                + "/" + "src/main/java"
                + "/" + CodeConstant.BASE_PACKAGE_DIR;

        String baseDir = base
                + "/" + "base";

        String configDir = base
                + "/" + "config";
        checkDir(baseDir);
        checkDir(configDir);

        Map<String, Object> map = new HashMap<>();
        map.put("base", CodeConstant.BASE_PACKAGE);

        createBaseFile(templateEngine, baseDir, map, "base");
        createBaseFile(templateEngine, configDir, map, "config");

    }

    private static void createBaseFile(FreemarkerTemplateEngine templateEngine, String outPutDir, Map<String, Object> map, String moduleName) {

        String path = "classpath:/" + moduleName + "/*.java.ftl";
        try {
            Resource[] resources = resourceResolver.getResources(path);

            for (int j = 0; j < resources.length; j++) {
                String resourcePath = resources[j].getURL().getPath();
                String fileName = resourcePath.substring(resourcePath.lastIndexOf("/"));
                String javaName = fileName.substring(0, fileName.lastIndexOf("."));

                templateEngine.writer(map, "/" + moduleName + urlSlashHandler(fileName),
                        outPutDir + urlSlashHandler(javaName));
            }
        } catch (Exception e) {
            log.debug("生成文件失败", e);
        }
    }

    private static StrategyConfig getStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        // strategy.setSuperControllerClass("");
        // 写于父类中的公共字段
        strategy.setSuperEntityClass(CodeConstant.BASE_PACKAGE + ".base.AbstractEntity");
        strategy.setSuperEntityColumns("id", "created_by",
                "created_time", "last_modified_by", "last_modified_time");

        // strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setInclude("user_attribute", "user_attribute_definition");
        strategy.setControllerMappingHyphenStyle(true);
        return strategy;
    }

    private static TemplateConfig getTemplateConfig() {
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("mybatis-plus/entity.java");
        templateConfig.setService("mybatis-plus/service.java");
        templateConfig.setServiceImpl("mybatis-plus/serviceImpl.java");
        templateConfig.setController("mybatis-plus/controller.java");

        templateConfig.setXml(null);
        return templateConfig;
    }

    private static InjectionConfig getInjectionConfig() {
        InjectionConfig cfg = new SimpleInjectionConfig();

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfigForDTO());
        focList.add(new FileOutConfigForCriteria());
        focList.add(new FileOutConfigForMapStruct());
        focList.add(new FileOutConfigForXML());
        cfg.setFileCreate(new SimpleFileCreate());
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        // pc.setModuleName(scanner("模块名"));
        pc.setModuleName("");
        pc.setParent("indi.biz.yufr");
        return pc;
    }

    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/ksAuth?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        return dsc;
    }

    private static GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                .setOutputDir(CodeConstant.PROJECT_PATH + "/src/main/java")
                .setAuthor("yufr")
                .setOpen(false)
                .setSwagger2(true)
                .setIdType(IdType.ASSIGN_ID)
                .setFileOverride(true)
                .setBaseResultMap(true)
                .setBaseColumnList(true);
    }

    private static void checkDir(String filePath) {
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            file.mkdirs();
        }
    }

    private static String urlSlashHandler(String path) {
        String location = path;
        if (!location.startsWith("/")) {
            location = "/" + location;
        }
        if (location.endsWith("/")) {
            location = location.substring(0, location.length() - 1);
        }
        return location;
    }
}
