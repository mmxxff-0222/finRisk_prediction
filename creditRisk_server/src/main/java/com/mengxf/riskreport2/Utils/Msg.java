package com.mengxf.riskreport2.Utils;

/**
 * Message
 *
 * @author Meng xf
 * @since 2024/3/20 11:43
 */
public class Msg {
    public static String findLoan(int flag){
        switch (flag){
            case Const.NO_EXIST:
                return "用户不存在，查无此人！！";
            case Const.NOT_NECESSARY:
                return "管理人员无贷款记录！！";
            default:
                return "共查询到"+flag+"条贷款记录";
        }
    }

    public static String findFinInfo(int flag){
        switch (flag){
            case Const.NO_EXIST:
                return "用户不存在，查无此人！！";
            case Const.SUCCESS:
                return "成功查询用户财务状况！";
            case Const.NOT_NECESSARY:
                return "管理人员无财务状况信息!";
            default:
                return "当前财务状况为空，请告知用户及时填写！";
        }
    }
}
