package Main;

import java.util.List;

/**
 * author: Yixin Luo
 * 2016/3/3
 * 
 * 解析器的接口。解析器用于提取关键信息，使用jsoup或正则表达式等完成任务。
 * 
 * **/
public interface Processor {
	public void process(List<String> pages, Saver saver);
}

