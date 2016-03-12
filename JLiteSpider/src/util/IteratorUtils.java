package util;

import java.util.Iterator;

/**
 * 迭代相关工具类
 */
public class IteratorUtils {

    public static boolean isEmpty(Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

}
