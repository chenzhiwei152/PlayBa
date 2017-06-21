package com.yuanchangyuan.wanbei.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by sun.luwei on 2016/12/30.
 */

public class ParserUtil {

    public static <T> T parseObj(String content, Class<T> clazz){
        try {
            return (T) new Gson().fromJson(content,clazz);
        }catch (Exception e){
            LogUtils.e("ParserUtil", "解析异常");
        }
        return null;
    }

    public static <T> List<T> parseList(String content, Class<T> clazz) {

        try {
            return new Gson().fromJson(content, new TypeToken<List<T>>(){}.getType());
        }catch (Exception e){
            LogUtils.e("ParserUtil", "解析异常");
        }
        return null;

    }

}
