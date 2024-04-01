package com.mengxf.riskreport2.Utils;


import com.alibaba.fastjson2.JSONObject;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.FinInfoPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import com.mengxf.riskreport2.pojo.UserPojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApiResponse
 *
 * @author Meng xf
 * @since 2024/3/22 14:16
 */
public class ApiResponse {
    public static String okString(String msg, Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", msg);
        map.put("data",data);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String okString(String msg){
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
            default:
                map.put("code",400);
                map.put("message","报告生成失败！！！");
        }

        map.put("data",data);
        String s = JSONObject.toJSONString(map);
        return s;
    }

    public static String allUsersRespinse(int code, List<UserPojo> data){
        Map<String, Object> map =new HashMap<>();
        if (code == Const.SUCCESS){
            map.put("code",200);
            map.put("message", "所有用户查询成功！！");
            map.put("data",data);
        }else {
            map.put("code",400);
            map.put("message", "用户表为空！！");
            map.put("data","NONE");
        }
        return JSONObject.toJSONString(map);
    }

    public static String getLoanResponse(int code, List<LoanPojo> loans) {
        Map<String,Object> map = new HashMap<>();
        switch (code){
            case Const.SUCCESS:
                map.put("code",200);
                map.put("msg","成功查询到贷款信息！");
                map.put("data",loans);
                return JSONObject.toJSONString(map);
            case Const.NO_EXIST:
                map.put("msg","该用户不存在！！");
                break;
            case Const.EMPTY:
                map.put("msg","用户贷款信息为空！！");
                break;
            case Const.NOT_NECESSARY:
                map.put("msg","该用户为管理员，无贷款记录");
                break;
            default:
                map.put("msg","Error!!");
        }
        map.put("code",400);
        map.put("data","NONE");
        return JSONObject.toJSONString(map);
    }

    public static String getBorrInfoResponse(int code, BorrowerPojo borrInfo) {
        Map<String,Object> map = new HashMap<>();
        switch (code){
            case Const.SUCCESS:
                map.put("code",200);
                map.put("msg","成功查询到借款人信息！");
                map.put("data",borrInfo);
                return JSONObject.toJSONString(map);
            case Const.NO_EXIST:
                map.put("msg","该用户不存在！！");
                break;
            case Const.EMPTY:
                map.put("msg","用户借款人信息为空！！");
                break;
            case Const.NOT_NECESSARY:
                map.put("msg","该用户为管理员，无借款信息！！");
                break;
            default:
                map.put("msg","Error!!");
        }
        map.put("code",400);
        map.put("data","NONE");
        return JSONObject.toJSONString(map);
    }

    public static String getFinInfoResponse(int code, FinInfoPojo finInfo) {
        Map<String,Object> map = new HashMap<>();
        switch (code){
            case Const.SUCCESS:
                map.put("code",200);
                map.put("msg","成功查询到借款人信息！");
                map.put("data",finInfo);
                return JSONObject.toJSONString(map);
            case Const.NO_EXIST:
                map.put("msg","该用户不存在！！");
                break;
            case Const.EMPTY:
                map.put("msg","借款人金融健康信息为空！！");
                break;
            case Const.NOT_NECESSARY:
                map.put("msg","该用户为管理员，无金融健康信息信息！！");
                break;
            default:
                map.put("msg","Error!!");
        }
        map.put("code",400);
        map.put("data","NONE");
        return JSONObject.toJSONString(map);
    }

    public static String getRiskResponse(String riskGrade) {
        Map<String,Object> map = new HashMap<>();
        if (riskGrade.equals("NONE")){
            map.put("code",400);
            map.put("msg","预测失败！！");
            map.put("index",4);
        }else {
            map.put("code",200);
            map.put("msg",riskGrade);
            map.put("index",Const.RISK_LIST.indexOf(riskGrade));

        }
        return JSONObject.toJSONString(map);
    }

    public static String allLoansRespinse(int code, List<LoanPojo> loans) {
        Map<String, Object> map =new HashMap<>();
        if (code == Const.SUCCESS){
            map.put("code",200);
            map.put("message", "所有用户查询成功！！");
            map.put("data",loans);
        }else {
            map.put("code",400);
            map.put("message", "用户表为空！！");
            map.put("data","NONE");
        }
        return JSONObject.toJSONString(map);
    }

    public static String allPersonRespinse(int code, List<BorrowerPojo> persons) {
        Map<String, Object> map =new HashMap<>();
        if (code == Const.SUCCESS){
            map.put("code",200);
            map.put("message", "所有用户信息查询成功！！");
            map.put("data",persons);
        }else {
            map.put("code",400);
            map.put("message", "用户信息表为空！！");
            map.put("data","NONE");
        }
        return JSONObject.toJSONString(map);
    }
}
