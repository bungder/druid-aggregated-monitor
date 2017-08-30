package io.github.bungder.druid.monitor.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * @author 谭仕昌
 * @date 2017-08-30 14:56
 */
public class FileUtil {

    public static String readResourceFileText(String resourcePath, String encoding) throws IOException {
        Resource res = new ClassPathResource(resourcePath);
        // ① 指定文件资源对应的编码格式（UTF-8）
        EncodedResource encRes = new EncodedResource(res,encoding);
        // ② 这样才能正确读取文件的内容，而不会出现乱码
        String content  = FileCopyUtils.copyToString(encRes.getReader());
        return content;
    }

}
