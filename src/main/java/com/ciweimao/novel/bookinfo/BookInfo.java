package com.ciweimao.novel.bookinfo;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ciweimao.novel.common.UrlConstants;
import com.ciweimao.novel.request.HttpResponse;

public class BookInfo {

    public static JSONObject getBookInfo(String book_id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("book_id", book_id);
        params.put("recommend", "");
        params.put("carousel_position", "");
        params.put("tab_type", "");
        params.put("module_id", "");
        params.put("use_daguan", "0");

        String response = new HttpResponse().post(UrlConstants.BOOK_GET_INFO_BY_ID, params);
        JSONObject object = JSONObject.parseObject(response);
        if (Objects.equals(object.getString("code"), "100000")) {
            return object.getJSONObject("data").getJSONObject("book_info");
        } else {
            System.out.println(object.getString("tip") + "获取书籍信息失败");
        }

        return null;
    }
}