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

```
<dependency>
  <groupId>com.JLiteSpider</groupId>
  <artifactId>JLiteSpider</artifactId>
  <version>1.1</version>
</dependency>
```

>直接下载jar包:  

点击[下载](http://maven.oschina.net/index.html#nexus-search;quick~JLiteSpider)。  

###设计

JLiteSpider将整个的爬虫抓取流程抽象成四个部分。  

* UrlList:

>用来生成想要抓取的url字符串链表。url链表可以由用户进行初始设定，或者由外部传入。

这部分的接口设计如下：  

```
public interface UrlList {
	/**
	 * 返回你想要抓取的url链表
	 * **/
	public List<String> returnUrlList();
}
```

你所需要做的是，实现这个接口，并将想要抓取的url链表返回。具体的实现细节，可以由你高度定制。  

* Downloader:

>这部分实现的是页面下载的任务，将想要抓取的url链表，转化（下载后存储）为相应的页面数据链表。

接口设计如下：

```
public interface Downloader {
	/**
	 * 这个函数将url链表中对应的网页下载，然后将结果保存到字符串链表中，返回
	 * **/
	public List<String> download(List<String> urlList);
}
```

你同样可以实现这个接口，具体的实现可由你自由定制，只要实现`download`函数，实现将`urlList`转化为返回页面的链表的过程。  
当然，我在这里提供了一个简单的线程池下载器`DefaultDownloader`的实现（好让JListSpider看起来不是那么的无用;-)）。具体的`DefaultDownloader`使用方法在后面会讲，不过我还是强烈推荐你能自己实现下载器，这样才能满足更个性化的需求，当然也是JListSpider设计的初衷（只是一个接口）。

* Saver:

>`Saver`用来