package indi.biz.yufr.codegen;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * @date: 2021/1/5 12:40
 * @author: farui.yu
 */
public class FileOutConfigForMapStruct extends FileOutConfig {

    public FileOutConfigForMapStruct() {
        super.setTemplatePath(CodeConstant.MAP_STRUCT_TEMPLATE);
    }

    @Override
    public String outputFile(TableInfo tableInfo) {

        return CodeConstant.PROJECT_PATH
                + "/" + "src/main/java"
                + "/" + CodeConstant.BASE_PACKAGE_DIR
                + "/" + "service/mapper"
                + "/" + tableInfo.getEntityName() + "Convert" + StringPool.DOT_JAVA;
    }

}
