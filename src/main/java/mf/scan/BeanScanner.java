package mf.scan;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 扫描器
 *
 * @author yehao
 * @date 2020/4/13
 */
public class BeanScanner {

    /**
     * 扫描包路径
     *
     * @author yehao
     * @date 2020/4/13
     */
    public Set<String> scan(String basePackage) throws IOException {
        Set<String> classNameSet = new HashSet<>();
        Enumeration<URL> urls = Thread.currentThread()
                                      .getContextClassLoader()
                                      .getResources(basePackage.replace(".", "/"));
        while (urls.hasMoreElements()) {
            URL    url      = urls.nextElement();
            String protocol = url.getProtocol();
            if (Objects.equals(protocol, "file")) {
                getPathClassName(url.getPath(), classNameSet, basePackage);
            }
        }
        return classNameSet;
    }

    /**
     * 获取目录下的类
     *
     * @author yehao
     * @date 2020/4/13
     */
    public void getPathClassName(String path, Set<String> classNameSet, String prefix) throws UnsupportedEncodingException {
        File[] files = new File(URLDecoder.decode(path, "UTF-8")).listFiles(file -> (file.isFile()
                && file.getName().endsWith(".class")
                && !file.getName().contains("$"))
                || file.isDirectory());
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    classNameSet.add(prefix + "." + file.getName().split("\\.")[0]);
                } else {
                    getPathClassName(path + "/" + file.getName(), classNameSet, prefix + "." + file.getName());
                }
            }
        }
    }
}
