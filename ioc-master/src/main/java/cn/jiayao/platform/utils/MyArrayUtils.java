package cn.jiayao.platform.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 类 名: MyArrayUtils
 * 描 述:
 * 作 者: 黄加耀
 * 创 建: 2019/3/18 : 16:34
 * 邮 箱: huangjy19940202@gmail.com
 *
 * @author: jiaYao
 */
public class MyArrayUtils {

    /**
     * @author: JiaYao
     * @demand: 判断数组中是否包含元素
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    public static boolean useArrayUtils(String[] arr, String targetValue) {
        return ArrayUtils.contains(arr, targetValue);
    }

    /**
     * @author: JiaYao
     * @demand: 判断数组中是否包含元素
     * @parameters:
     * @creationDate：
     * @email: huangjy19940202@gmail.com
     */
    public static boolean useArrayUtils(Class[] arr, Class targetValue) {
        return ArrayUtils.contains(arr, targetValue);
    }

}
