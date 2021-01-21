package indi.biz.yufr.codegen;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

/**
 * @date: 2021/1/5 12:40
 * @author: farui.yu
 */
public class FileOutConfigForXML extends FileOutConfig {

    public FileOutConfigForXML() {
        super.setTemplatePath(CodeConstant.XML_TEMPLATE);
    }

    @Override
    public String outputFile(TableInfo tableInfo) {
        return CodeConstant.PROJECT_PATH
                + "/" + "src/main/resources/mapper"
                + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
    }

}
