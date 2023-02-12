package com.ciweimao.novel.chapter;

import com.alibaba.fastjson.JSONObject;
import com.util.tools.ParseKsy;
import com.ciweimao.novel.common.UrlConstants;
import com.ciweimao.novel.request.HttpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContentText {

    public static String getContentText(String chapter_id, String commandKey) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("chapter_id", chapter_id);
        params.put("chapter_command", commandKey);
        String response = new HttpResponse().post(UrlConstants.BOOK_CHAPTER_CONTENT, params);
        JSONObject object = JSONObject.parseObject(response);
        if (Objects.equals(object.getString("code"), "100000")) {
            JSONObject chapterInfo = object.getJSONObject("data").getJSONObject("chapter_info");
            ParseKsy parseKsy = new ParseKsy();
            return parseKsy.decryptContent(chapterInfo.getString("txt_content"), commandKey);
        } else { // 获取章节列表失败
            System.out.println(object.getString("tip") + "获取章节密钥失败");
        }

        return null;
    }
}
