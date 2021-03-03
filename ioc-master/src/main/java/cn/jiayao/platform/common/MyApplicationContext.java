package cn.jiayao.platform.common;

import cn.jiayao.platform.config.ConfigurationUtils;
import cn.jiayao.platform.core.annotation.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static cn.jiayao.platform.config.ConfigurationUtils.getPropertiesByKey;
import static cn.jiayao.platform.utils.MyArrayUtils.useArrayUtils;

/**
 * 类 名: MyApplicationContext
 * 描 述: IOC工具类
 * 作 者: 黄加耀
 * 创 建: 2019/3/17 : 10:17
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
public class MyApplicationContext {

    /**
     * IOC容器 如： String(loginController) --> Object(loginController实例)
     */
    private Map<String, Object> iocBeanMap = new ConcurrentHashMap(32);
    /**
     * 类集合--存放所有的全限制类名
     */
    private Set<String> classSet = new HashSet();

    public MyApplicationContext() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // 初始化数据
        this.classLoader();
    }

    /**
     * @author: JiaYao
     * @demand: 从IOC容器中获取对象
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    public Object getIocBean(String beanName) {
        if (iocBeanMap != null) {
            return iocBeanMap.get(toLowercaseIndex(beanName));
        } else {
            return null;
        }
    }


    /**
     * @author: JiaYao
     * @demand: 控制反转
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    private void addServiceToIoc(Class classZ) throws IllegalAccessException, InstantiationException {
        // 预留位置，之后优化
        if (classZ.getAnnotation(MyController.class) != null) {
            iocBeanMap.put(toLowercaseIndex(classZ.getSimpleName()), classZ.newInstance());
            System.out.println("控制反转访问控制层:" + toLowercaseIndex(classZ.getSimpleName()));
        } else if (classZ.getAnnotation(MyService.class) != null) {
            // 将当前类交由IOC管理
            MyService myService = (MyService) classZ.getAnnotation(MyService.class);
            iocBeanMap.put(StringUtils.isEmpty(myService.value()) ? toLowercaseIndex(classZ.getSimpleName()) : toLowercaseIndex(myService.value()), classZ.newInstance());
            System.out.println("控制反转服务层:" + toLowercaseIndex(classZ.getSimpleName()));
        } else if (classZ.getAnnotation(MyMapping.class) != null) {
            MyMapping myMapping = (MyMapping) classZ.getAnnotation(MyMapping.class);
            iocBeanMap.put(StringUtils.isEmpty(myMapping.value()) ? toLowercaseIndex(classZ.getSimpleName()) : toLowercaseIndex(myMapping.value()), classZ.newInstance());
            System.out.println("控制反转持久层:" + toLowercaseIndex(classZ.getSimpleName()));
        }
    }


    /**
     * @author: JiaYao
     * @demand: 依赖注入
     * @parameters:
     * @creationDate： 2019年3月17日 10:16:06
     * @email: huangjy19940202@gmail.com
     */
    private void addAutowiredToField(Object obj) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(MyAutowired.class) != null) {
                field.setAccessible(true);
                MyAutowired myAutowired = field.getAnnotation(MyAutowired.class);
                Class<?> fieldClass = field.getType();
                // 接口不能被实例化，需要对接口进行特殊处理获取其子类，获取所有实现类
                if (fieldClass.isInterface()) {
                    // 如果有指定获取子类名
                    if (StringUtils.isNotEmpty(myAutowired.value())) {
                        field.set(obj, iocBeanMap.get(myAutowired.value()));
                    } else {
                        // 当注入接口时，属性的名字与接口实现类名一致则直接从容器中获取
                        Object objByName = iocBeanMap.get(field.getName());
                        if (objByName != null) {
                            field.set(obj, objByName);
                            // 递归依赖注入
                            addAutowiredToField(field.getType());
                        } else {
                            // 注入接口时，如果属性名称与接口实现类名不一致的情况下
                            List<Object> list = findSuperInterfaceByIoc(field.getType());
                            if (list != null && list.size() > 0) {
                                if (list.size() > 1) {
                                    throw new RuntimeException(obj.getClass() + "  注入接口 " + field.getType() + "   失败，请在注解中指定需要注入的具体实现类");
                                } else {
                                    field.set(obj, list.get(0));
                                    // 递归依赖注入
                                    addAutowiredToField(field.getType());
                                }
                            } else {
                                throw new RuntimeException("当前类" + obj.getClass() + "  不能注入接口 " + field.getType().getClass() + "  ， 接口没有实现类不能被实例化");
                            }
                        }
                    }
                } else {
                    String beanName = StringUtils.isEmpty(myAutowired.value()) ? toLowercaseIndex(field.getName()) : toLowercaseIndex(myAutowired.value());
                    Object beanObj = iocBeanMap.get(beanName);
                    field.set(obj, beanObj == null ? field.getType().newInstance() : beanObj);
                    System.out.println("依赖注入" + field.getName());
//                递归依赖注入
                }
                addAutowiredToField(field.getType());
            }
            if (field.getAnnotation(Value.class) != null) {
                field.setAccessible(true);
                Value value = field.getAnnotation(Value.class);
                field.set(obj, StringUtils.isNotEmpty(value.value()) ? getPropertiesByKey(value.value()) : null);
                System.out.println("注入配置文件  " + obj.getClass() + " 加载配置属性" + value.value());
            }
        }
    }

    /**
     * @author: JiaYao
     * @demand: 判断需要注入的接口所有的实现类
     * @parameters:
     * @creationDate： 2019年3月18日 17:12:23
     * @email: huangjy19940202@gmail.com
     */
    private List<Object> findSuperInterfaceByIoc(Class classz) {
        Set<String> beanNameList = iocBeanMap.keySet();
        ArrayList<Object> objectArrayList = new ArrayList<>();
        for (String beanName : beanNameList) {
            Object obj = iocBeanMap.get(beanName);
            Class<?>[] interfaces = obj.getClass().getInterfaces();
            if (useArrayUtils(interfaces, classz)) {
                objectArrayList.add(obj);
            }
        }
        return objectArrayList;
    }

    /**
     * @author: JiaYao
     * @demand: 类加载器
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    private void classLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 加载配置文件所有配置信息
        new ConfigurationUtils(null);
        // 获取扫描包路径
        String classScanPath = (String) ConfigurationUtils.properties.get("ioc.scan.path");
        if (StringUtils.isNotEmpty(classScanPath)) {
            classScanPath = classScanPath.replace(".", "/");
        } else {
            throw new RuntimeException("请配置项目包扫描路径 ioc.scan.path");
        }
        // 获取项目中全部的代码文件中带有MyService注解的
        getPackageClassFile(classScanPath);
        for (String className : classSet) {
            addServiceToIoc(Class.forName(className));
        }
        // 获取带有MyService注解类的所有的带MyAutowired注解的属性并对其进行实例化
        Set<String> beanKeySet = iocBeanMap.keySet();
        for (String beanName : beanKeySet) {
            addAutowiredToField(iocBeanMap.get(beanName));
        }
    }


    /**
     * @author: JiaYao
     * @demand: 扫描项目根目录中所有的class文件
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    private void getPackageClassFile(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName);
        File file = new File(url.getFile());
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileSon : files) {
                if (fileSon.isDirectory()) {
                    // 递归扫描
                    getPackageClassFile(packageName + "/" + fileSon.getName());
                } else {
                    // 是文件并且是以 .class结尾
                    if (fileSon.getName().endsWith(".class")) {
                        System.out.println("正在加载: " + packageName.replace("/", ".") + "." + fileSon.getName());
                        classSet.add(packageName.replace("/", ".") + "." + fileSon.getName().replace(".class", ""));
                    }
                }
            }
        } else {
            throw new RuntimeException("没有找到需要扫描的文件目录");
        }
    }


    /**
     * @author: JiaYao
     * @demand: 类名首字母转小写
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    public static String toLowercaseIndex(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
        }
        return name;
    }

    /**
     * @author: JiaYao
     * @demand: 类名首字母转大写
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    public static String toUpperCaseIndex(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
        }
        return name;
    }


}
