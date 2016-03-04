package Test.Douban;

import core.Saver;

public class PrintSaver implements Saver {

	public void save(String key, Object value) {
		// TODO Auto-generated method stub
		System.out.println(key+"->"+value);
	}

}
