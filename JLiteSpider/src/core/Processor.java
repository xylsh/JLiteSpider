package core;

import java.util.List;

/**
 * author: Yixin Luo
 * 2016/3/3
 * 
 * 解析器的接口。解析器用于提取关键信息，使用jsoup或正则表达式等完成任务。
 * 
 * **/
public interface Processor {
	/**
	 * pages是传入的要进行解析的文本链表，
	 * 使用saver对象的save(String key, Object value)来完成提取得到的数据的持久化操作
	 * **/
	public void process(List<String> pages, Saver saver);
}

