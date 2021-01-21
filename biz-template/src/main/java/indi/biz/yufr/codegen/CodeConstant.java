package indi.biz.yufr.codegen;

/**
 * @date: 2021/1/5 13:47
 * @author: farui.yu
 */
public class CodeConstant {

    public static final String PROJECT_PATH = System.getProperty("user.dir") + "/user-domain";

    private static final String CURRENT_PACKAGE = CodeConstant.class.getPackage().getName();

    public static final String BASE_PACKAGE = CURRENT_PACKAGE.substring(0, CURRENT_PACKAGE.lastIndexOf("."));
    public static final String BASE_PACKAGE_DIR = BASE_PACKAGE.replace(".", "/");

    public static final String DTO_TEMPLATE = "/mybatis-plus/dto.java.ftl";
    public static final String DTO_SEARCH_TEMPLATE = "/mybatis-plus/criteria.java.ftl";
    public static final String MAP_STRUCT_TEMPLATE = "/mybatis-plus/mapStruct.java.ftl";
    public static final String XML_TEMPLATE = "/templates/mapper.xml.ftl";
}
