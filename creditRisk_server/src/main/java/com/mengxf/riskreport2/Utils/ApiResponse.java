package com.mengxf.riskreport2.Utils;


import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiResponse
 *
 * @author Meng xf
 * @since 2024/3/22 14:16
 */
public class ApiResponse {
    public static String okGetString(String msg, Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", msg);
        map.put("data",data);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String okGetString(String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", msg);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String reportResponse(int code, String data){
        Map<String, Object> map =new HashMap<>();
        switch (code){
            case Const.SUCCESS:
                map.put("code",200);
                map.put("message", "成功生成报告！！");
                break;
            case Const.USER_LOSS:
                map.put("code",400);
                map.put("message", "用户表查无此人！！");
                break;
            case Const.BORROWER_LOSS:
                map.put("code",400);
                map.put("message", "借款人详细信息缺失！！");
                break;
            case Const.LOAN_LOSS:
                map.put("code",400);
                map.put("message", "贷款信息缺失！！");
                break;
            case Const.FININFO_LOSS:
                map.put("code",400);
                map.put("message", "借款人金融健康状况未知！！");
                break;
        }

        map.put("data",data);
        String s = JSONObject.toJSONString(map);
        return s;
    }
}
