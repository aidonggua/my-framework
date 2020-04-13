package mf.context;

import mf.annotation.Autowired;
import mf.annotation.Bean;
import mf.exception.AutowiredException;
import mf.exception.ContextException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 上下文
 *
 * @author yehao
 * @date 2020/4/13
 */
public class MFContext {

    private Map<String, Object> beanNameMap = new HashMap<>();

    private Map<String, Object> beanTypeMap = new HashMap<>();

    /**
     * 初始化上下文
     *
     * @author yehao
     * @date 2020/4/13
     */
    public void init(Set<String> classNameSet) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String className : classNameSet) {
            if (beanNameMap.containsKey(className)) {
                throw new ContextException("class " + className + " already exist");
            }

            Class<?> c              = Class.forName(className);
            Bean     beanAnnotation = c.getAnnotation(Bean.class);
            if (beanAnnotation == null) {
                continue;
            }
            System.out.println("register bean " + className);

            Object bean = c.newInstance();
            beanNameMap.put(c.getName(), bean);

            Class<?>[] interfaces = c.getInterfaces();
            if (interfaces != null) {
                for (Class<?> anInterface : interfaces) {
                    if (beanTypeMap.containsKey(anInterface.getName())) {
                        throw new ContextException("multiple class implement same interface");
                    }
                    beanTypeMap.put(anInterface.getName(), bean);
                }
            }
        }

        autoWired();
    }

    /**
     * 自动装配
     *
     * @author yehao
     * @date 2020/4/13
     */
    public void autoWired() {
        beanNameMap.forEach((k, v) -> {
            Field[] declaredFields = v.getClass()
                                      .getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);

                Autowired autowired = declaredField.getDeclaredAnnotation(Autowired.class);
                if (autowired == null) {
                    continue;
                }

                String fieldTypeName = declaredField.getType()
                                                    .getName();
                Object bean = beanTypeMap.get(fieldTypeName);
                if (bean == null) {
                    throw new AutowiredException("not found bean " + fieldTypeName);
                }
                try {
                    declaredField.set(v, bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new AutowiredException(e.getMessage());
                }
            }
        });
    }

    /**
     * 获取实例
     *
     * @author yehao
     * @date 2020/4/13
     */
    public Object getInstance(Class<?> c) {
        return beanTypeMap.get(c.getName());
    }

    public Map<String, Object> getBeanNameMap() {
        return beanNameMap;
    }

    public void setBeanNameMap(Map<String, Object> beanNameMap) {
        this.beanNameMap = beanNameMap;
    }

    public Map<String, Object> getBeanTypeMap() {
        return beanTypeMap;
    }

    public void setBeanTypeMap(Map<String, Object> beanTypeMap) {
        this.beanTypeMap = beanTypeMap;
    }
}
