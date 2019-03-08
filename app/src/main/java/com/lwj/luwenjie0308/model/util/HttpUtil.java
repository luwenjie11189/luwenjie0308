package com.lwj.luwenjie0308.model.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Auther:
 * @Date: 2019/3/8 13:41
 * @Description:
 */
public class HttpUtil<T> {

    private final OkHttpClient okHttpClient;
    private CallBackData callBackData;

    private HttpUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addNetworkInterceptor(getAppInterceptor())
                .build();
    }

    private static Interceptor getAppInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("aaa", "拦截前");
                Response proceed = chain.proceed(request);
                Log.i("aaa", "拦截后");
                return proceed;
            }
        };
        return interceptor;
    }


    public static HttpUtil getInstance() {
        return getHttpUtilInstance.httpUtil;
    }

    private static class getHttpUtilInstance {
        public static HttpUtil httpUtil = new HttpUtil();
    }

    public void getData(String url, final Class<T> tClass, CallBackData callBackData) {
        this.callBackData = callBackData;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(string, tClass);
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = t;
                handler.sendMessage(message);
            }
        });
    }

    public void postData(final String str, final Class<T> tClass, HashMap<String, String> hashMap, CallBackData callBackData) {
        this.callBackData = callBackData;
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> stringStringEntry : hashMap.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            builder.add(key, value);
        }
        FormBody build = builder.build();
        Request request = new Request.Builder()
                .url(str)
                .post(build)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(string, tClass);
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = t;
                handler.sendMessage(message);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String string = (String) msg.obj;
                    callBackData.onFail(string);
                    break;
                case 1:
                    T t = (T) msg.obj;
                    callBackData.onResponse(t);
                    break;
            }
        }
    };

    public interface CallBackData<D> {
        void onResponse(D d);

        void onFail(String err);
    }

}
