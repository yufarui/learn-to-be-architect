package indi.biz.yufr.codegen;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * @date: 2021/1/5 12:40
 * @author: farui.yu
 */
public class FileOutConfigForCriteria extends FileOutConfig {

    public FileOutConfigForCriteria() {
        super.setTemplatePath(CodeConstant.DTO_SEARCH_TEMPLATE);
    }

    @Override
    public String outputFile(TableInfo tableInfo) {

        return CodeConstant.PROJECT_PATH
                + "/" + "src/main/java"
                + "/" + CodeConstant.BASE_PACKAGE_DIR
                + "/" + "service/dto/search"
                + "/" + tableInfo.getEntityName() + "Criteria" + StringPool.DOT_JAVA;
    }

}
