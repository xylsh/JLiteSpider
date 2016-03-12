package Test.Douban;

import core.UrlSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apple on 16/3/12.
 */
public class DoubanUrlSource implements UrlSource {

    public Iterator<String> iterator() {

        List<String> urlList = new ArrayList<String>();
        urlList.add("http://www.douban.com/tag/中国/movie");
        urlList.add("http://www.douban.com/tag/中国/movie?start=15");
        urlList.add("http://www.douban.com/tag/中国/movie?start=30");

        return urlList.iterator();
    }
}
