package com.ciweimao.novel.chapter;

import com.alibaba.fastjson.JSONObject;
import com.ciweimao.novel.common.UrlConstants;
import com.ciweimao.novel.request.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContentCommand {

    public static String getChapterCommand(String chapter_id) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("chapter_id", chapter_id);
        String response = new HttpResponse().post(UrlConstants.BOOK_CHAPTER_COMMAND, params);
        JSONObject object = JSONObject.parseObject(response);
        if (Objects.equals(object.getString("code"), "100000")) {
            return object.getJSONObject("data").getString("command");
        } else { // 获取章节列表失败
            System.out.println(object.getString("tip") + "获取章节密钥失败");
        }

        return null;
    }
}
