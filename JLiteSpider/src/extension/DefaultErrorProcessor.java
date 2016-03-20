package extension;


import core.ErrorProcessor;

public class DefaultErrorProcessor implements ErrorProcessor {

    @Override
    public void onException(String url, Throwable e) {
        System.out.println("Error: url:" + url);
        e.printStackTrace();
    }

}
