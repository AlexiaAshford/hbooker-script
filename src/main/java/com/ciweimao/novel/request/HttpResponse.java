package com.ciweimao.novel.request;

import com.alibaba.fastjson.JSONObject;
import com.util.tools.ParseKsy;
import com.util.tools.ReadJson;
import okhttp3.*;

import java.util.Map;
import java.util.HashMap;

import com.ciweimao.novel.common.UrlConstants;


public class HttpResponse {
    public static JSONObject config = null;
    String account = config.getString("account");
    String app_version = config.getString("app_version");
    String device_token = config.getString("device_token");
    String login_token = config.getString("login_token");


     String getRequest(String url, Map<String, String> params) {
        OkHttpClient client = new OkHttpClient();
        Map<String, String> headers = new HashMap<>();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        StringBuilder paramsStr = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            paramsStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        RequestBody body = RequestBody.create(mediaType, paramsStr.toString());
        headers.put("User-Agent", "Android  com.kuangxiangciweimao.novel");
        Request request = new Request.Builder().url(UrlConstants.WEB_SITE + url).post(body).headers(Headers.of(headers)).build();
        try {
            Response response = client.newCall(request).execute();

            assert response.body() != null;
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String url, Map<String, String> params) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("account", this.account);
        data.put("app_version", this.app_version);
        data.put("device_token", this.device_token);
        data.put("login_token", this.login_token);
        data.putAll(params); // 合并参数
//        System.out.println(data);
        String result = this.getRequest(url, data);
        if (result != null) {
            ParseKsy parseKsy = new ParseKsy();
            return parseKsy.decrypt(result);
        } else {
            System.out.println(url + "请求失败");
        }
        return null;
    }


}
