package com.eebbk.bfc.common.file.digest;

import java.io.File;
import java.io.InputStream;

/**
 * 用来处理数据摘要的工具类, eg. md5, SHA等
 *
 * 用来做数据摘要算法的工厂类
 *
 * Created by Simon on 2016/10/12.
 */

public abstract class Digest {

    /**
     *  对字符串求摘要
     */
    public abstract byte[] digest(String str);

    /**
     * 对文件求摘要
     * @param file
     * @return
     */
    public abstract byte[] digest(File file);
    public abstract byte[] digest(byte[]  data);
    public abstract byte[] digest(InputStream is);
}
