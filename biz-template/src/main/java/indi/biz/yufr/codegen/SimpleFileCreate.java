package indi.biz.yufr.codegen;

import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.FileType;

/**
 * @date: 2021/1/5 13:39
 * @author: farui.yu
 */
public class SimpleFileCreate implements IFileCreate {

    @Override
    public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
        checkDir(filePath);
        return true;
    }
}
