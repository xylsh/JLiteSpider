package core;

import java.util.Iterator;

/**
 * author: Yixin Luo
 * 2016/3/3
 * <p>
 * 解析器的接口。解析器用于提取关键信息，使用jsoup或正则表达式等完成任务。
 **/
public interface Processor<T> {
    /**
     * pages是传入的要进行解析的文本链表，
     * 使用saver对象的save(String key, Object value)来完成提取得到的数据的持久化操作
     */
    void process(Iterator<String> pages, Saver<T> saver);
}

