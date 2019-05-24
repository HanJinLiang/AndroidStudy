package com.hanjinliang.androidstudy.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by caijun on 2018/5/26.
 * 功能介绍： 链接：https://www.jianshu.com/p/c18f2de566b0
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        long t1 = System.nanoTime();//请求发起的时间

        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                if(sb.length()>0)
                sb.delete(sb.length() - 1, sb.length());
                Log.e("CSDN_LQR",String.format("发送请求 %s RequestParams:{%s}",
                        request.url(), sb.toString()));
            }
        } else {
            Log.e("CSDN_LQR",String.format("发送请求 %s %s",
                    request.url(), request.headers()));
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        try {
            Log.e("CSDN_LQR",
                    String.format("接收响应: [%s]  %n%.1fms 返回json:%n %s %n%s",
                            response.request().url(),
                            (t2 - t1) / 1e6d,
                            new JSONObject(responseBody.string()).toString(5),
                            response.headers()
                    ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}