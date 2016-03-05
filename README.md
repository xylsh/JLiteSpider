# JLiteSpider
**A lite Java spider interface(no a framework).**  
**这是一个轻量级的java爬虫接口（注意：不是一个框架）**

###目的
当前已经有数量庞大的java爬虫框架，他们功能都很强大和完善，用他们来开发一个大型的爬虫任务非常的方便和便捷。但是JLiteSpider和他们并不是同类型的东西，JLiteSpider更像是一个轻量级java爬虫的编写规范。当满足下面几点时，你也许需要JLiteSpider，当然你也可以自己写一个:-(  

* 轻量级的抓取需求
* 下载，解析和数据持久化都需要深度定制
* 需要嵌入在其他的系统中

###安装

>使用maven：  

```xml
<dependency>
  <groupId>com.JLiteSpider</groupId>
  <artifactId>JLiteSpider</artifactId>
  <version>1.1</version>
</dependency>
```

>直接下载jar包:  

点击[下载](http://maven.oschina.net/index.html#nexus-search;quick~JLiteSpider)。  

###设计

JLiteSpider将整个的爬虫抓取流程抽象成四个部分，由四个接口来定义。  

#### 1. UrlList:

>用来生成想要抓取的url字符串链表。url链表可以由用户进行初始设定，或者由外部传入。

这部分的接口设计如下：  

```java
public interface UrlList {
	/**
	 * 返回你想要抓取的url链表
	 * **/
	public List<String> returnUrlList();
}
```

你所需要做的是，实现这个接口，并将想要抓取的url链表返回。具体的实现细节，可以由你高度定制。  

#### 2. Downloader:

>这部分实现的是页面下载的任务，将想要抓取的url链表，转化（下载后存储）为相应的页面数据链表。

接口设计如下：

```java
public interface Downloader {
	/**
	 * 这个函数将url链表中对应的网页下载，然后将结果保存到字符串链表中，返回
	 * **/
	public List<String> download(List<String> urlList);
}
```

你同样可以实现这个接口，具体的实现可由你自由定制，只要实现`download`函数，实现将`urlList`转化为返回页面的链表的过程。  
当然，我在这里提供了一个简单的线程池下载器`DefaultDownloader`的实现（好让JListSpider看起来不是那么的无用;-)）。具体的`DefaultDownloader`使用方法在后面会讲，不过我还是强烈推荐你能自己实现下载器，这样才能满足更个性化的需求，当然也是JListSpider设计的初衷（只是一个接口）。

#### 3. Saver:

>`Saver`实现的是数据持久化的任务，讲你解析后得到的数据存入数据库，文件等等。

接口的设计：

```java
public interface Saver {
	/**
	 * 将传入此函数的key和value进行持久化操作。
	 * **/
	public void save(String key, Object value);
}
```

通过实现这个接口，可以讲传过来的key和value进行持久化操作。当然，是要持久化到数据库还是文件？是单线程还是线程池操作？这些都需要由你自己来定义。我在`extension/`中实现了一个打印操作的`PrintSaver`类，将`key`和`value`输出到屏幕。  

#### 4. Processor:

>`Processor`是解析器的接口，这里会从网页的原始文件中提取出有用的信息，并使用`Saver`持久化。

接口设计：

```java
public interface Processor {
	/**
	 * pages是传入的要进行解析的文本链表，
	 * 使用saver对象的save(String key, Object value)来完成提取得到的数据的持久化操作
	 * **/
	public void process(List<String> pages, Saver saver);
}
```

其中`process(List<String> pages, Saver saver)`中的`pages`保存的是原始的网页数据链表，`saver`则是你自定义的数据持久化操作。你所需要做的是，重写这个接口，并定义解析规则，从`pages`中获取需要的信息，并使用`saver`进行持久化操作。我在JLiteSpider的包中依赖了`JSOUP`包，希望对你有用。  

###使用方法

JLiteSpider使用：

```java
Spider.create() //创建实例
      .setUrlList(...) //设置实现了UrlList接口的Url生成器
      .setDownloader(...) //设置实现了Downloader接口的下载器
      .setProcessor(...) //设置实现了Processor接口的解析器
      .setSaver(...) //设置实现了Saver接口的数据持久化方法
      .begin(); //开始爬虫

```

以豆瓣电影的页面为例子：  
实现UrlList接口，`DoubanUrlList.java`：

```java
/**
 *  生成，要抓取的url链表
 * 
 * **/
public class DoubanUrlList implements UrlList{

	/**
	 *   notice: 在这个函数中，新建要创建的url链表并返回
	 * **/
	public List<String> returnUrlList() {
		// TODO Auto-generated method stub
		List<String> urlList = new ArrayList<String>();
		urlList.add("http://www.douban.com/tag/中国/movie");
		urlList.add("http://www.douban.com/tag/中国/movie?start=15");
		urlList.add("http://www.douban.com/tag/中国/movie?start=30");
		return urlList;
	}
	
}
```

实现Processor接口，`DoubanProcessor.java`:

```java
public class DoubanProcessor implements Processor {

	public void process(List<String> pages, Saver saver) {
		// 将返回的每一个网页中的电影名称和链接，提取出来，使用jsoup
		// 最后使用saver做数据持久化
		for (String each : pages) {
			Document doc = Jsoup.parse(each);
			Element ele = doc.body();
			Elements es = ele.select("div#wrapper").select("div#content")
					.select("div.clearfix").select("div.article")
					.select("div.movie-list").select("dl");
			for (int i = 0; i < es.size(); i++) {
				saver.save("href", es.get(i).select("dt").select("a").attr("href"));
				saver.save("title", es.get(i).select("dd").select("a").text());
			}
		}
	}

}
```

组装爬虫，`DoubanMain.java`:

```java
public class DoubanMain {
	private static final String AGENT= "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) "
			+ "AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31";
	public static void main(String[] args) {
		Spider.create().setUrlList(new DoubanUrlList())     //组装UrlList
					   .setDownloader(new DefaultDownloader() //组装下载器
							   .setThreadPoolSize(1)
							   .setUserAgent(AGENT))
					   .setProcessor(new DoubanProcessor())   //组装解析器
					   .setSaver(new PrintSaver())            //组装数据持久化方法
					   .begin();
	}
}
```

###默认的接口实现

>由于JLiteSpider是一个爬虫接口，所以她并没有提供类似其他爬虫框架那样的，很强大的功能实现，而是需要用户的深度定制，实现对应接口的功能。

* 关于自带的下载器`DefaultDownloader`的实现，他提供了不少的自定义设置。  

```java
setUserAgent(String s) //设置user agent
setCookie(String c) //设置cookie
setTimeout(int t) //设置连接超时时间
setProxy(String p) //设置代理
setThreadPoolSize(int size) //设置线程池的线程数目
```

看起来好像还可以？最好还是自己实现一个更好的吧！

* 关于自带的`DefaultUrlList`实现，可以在构造函数中，将`List<String>`作为参数传入，实现url链表的初始化操作。

```java
public DefaultUrlList(List<String> u) {
		this.urlList = u;
	}
```

* 自带的`PrintSaver`功能非常简单，只是实现了输出到屏幕的功能。

```java
public class PrintSaver implements Saver {

	public void save(String key, Object value) {
		System.out.println(key+"->"+value);
	}

}
```

###其他

* Q: 我需要从网页文件中解析出待抓取的url，然后将其加入下载队列，但是JLiteSpider并没有维护这样一个任务队列。

> A: 解决方法是，你可以使用爬虫的嵌套，在获取到url的同时，开启新的爬虫任务，从而完成需求。

* Q: 我觉得你对于爬虫任务的接口的抽象做得太烂了，有更好的实现。

> A: 确实如此，我只是一个新手而已，我渴望能从您那听到更好的解决方案。欢迎提ISSUE。

