package core;


public interface ErrorProcessor {

    /**
     * 异常回调
     */
    void onException(String url,Throwable e);

}
