package com.ciweimao.novel.chapter;


import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ciweimao.novel.common.UrlConstants;
import com.ciweimao.novel.request.HttpResponse;

public class ChapterList {

    public static JSONArray getChapterList(String book_id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("book_id", book_id);
        String response = new HttpResponse().post(UrlConstants.GET_CHAPTER_UPDATE_ALL, params);
        JSONObject object = JSONObject.parseObject(response);
        if (Objects.equals(object.getString("code"), "100000")) {
            return object.getJSONObject("data").getJSONArray("chapter_list");
        } else { // 获取章节列表失败
            System.out.println(object.getString("tip") + "获取章节列表失败");
        }

        return null;
    }

}

