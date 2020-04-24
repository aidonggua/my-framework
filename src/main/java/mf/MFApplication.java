package mf;

import mf.annotation.BeanScan;
import mf.config.MFConfig;
import mf.context.MFContext;
import mf.exception.BeanScanException;
import mf.exception.ContextException;
import mf.scan.BeanScanner;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class MFApplication {

    private MFContext mfContext;

    public MFApplication run(Class<? extends MFConfig> mfConfigClass) {
        System.out.println("my-framework run...");
        System.out.println("load config of " + mfConfigClass.getName());

        // 扫描bean
        BeanScan beanScan = mfConfigClass.getAnnotation(BeanScan.class);
        if (Objects.isNull(beanScan)) {
            throw new BeanScanException("");
        }
        System.out.println("start to scan beans...");
        BeanScanner beanScanner = new BeanScanner();
        Set<String> classNameSet;
        try {
            classNameSet = beanScanner.scan(beanScan.value());
        } catch (IOException e) {
            throw new BeanScanException(e.getMessage());
        }
        System.out.println("...finished to scan beans");

        // 初始化上下文
        mfContext = new MFContext();
        try {
            System.out.println("start to init context...");
            mfContext.init(classNameSet);
            System.out.println("...finished to init context");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ContextException(e.getMessage());
        }
        return this;
    }

    public MFContext getMfContext() {
        return mfContext;
    }
}
