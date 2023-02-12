package com.util.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ReadJson {


    @SneakyThrows
    public static JSONObject Read() {
        InputStream input = null;
        input = new FileInputStream(System.getProperty("user.dir") + "/config.json");
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int length;
        length = input.read(buffer);
        while (length != -1) {
            sb.append(new String(buffer, 0, length));
            length = input.read(buffer);
        }
        return JSON.parseObject(sb.toString());
    }


    public static class ConfigInfo {
        public String account;
        public String app_version;
        public String device_token;
        public String login_token;

        public ConfigInfo() {
            JSONObject config = ReadJson.Read();
            this.account = config.getString("account");
            this.app_version = config.getString("app_version");
            this.device_token = config.getString("device_token");
            this.login_token = config.getString("login_token");


        }
    }

}
