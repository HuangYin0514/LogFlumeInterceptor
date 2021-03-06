package com.hy.flume;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.conf.Configurable;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/9 15:10
 */
/**
 * 1、实现一个InterceptorBuilder接口
 * 2、InterceptorBuilder中有个configuref方法,通过configure获取配置文件中的相应key。
 * 3、InterceptorBuilder中有个builder方法，通过builder创建一个自定义的MyAppInterceptor
 * 4、MyAppInterceptor中有两个方法，一个是批处理，一个单条处理，将批处理的逻辑转换为单条处理
 * 5、需要在单条数据中添加 appid，由于appid是变量。需要在MyAppInterceptor的构造器中传入一些参数。
 * 6、为自定义的MyAppInterceptor创建有参构造器，将需要的参数传入进来。
 */
public class MyAppInterceptor implements Interceptor {

    private String appId;

    public MyAppInterceptor() {
    }

    public MyAppInterceptor(String appId) {
        this.appId = appId;
    }

    public void initialize() {

    }

    public Event intercept(Event event) {
        //获取一行数据
        byte[] body = event.getBody();
        String message = new String(body);
        //获取配置文件中的 a1.sources.r1.interceptors.i1.appId = 1
        message = "appId" + appId + "||" + message;
        return null;
    }

    public List<Event> intercept(List<Event> list) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (Event event : list) {
            Event intercept = intercept(event);
            events.add(intercept);
        }
        return events;
    }

    public void close() {

    }

    public class MyAppInterceptorBuilder implements Builder {

        private String appId;

        public Interceptor build() {
            return new MyAppInterceptor(appId);
        }

        public void configure(Context context) {
            appId = context.getString("appId");
        }
    }

}
