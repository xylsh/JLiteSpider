package core;
/**
 * author : Yixin Luo
 * 2016/3/4
 * 
 * 数据持久化的接口
 * **/
public interface Saver {
	/**
	 * 将传入此函数的key和value进行持久化操作。
	 * **/
	public void save(String key, Object value);
}
