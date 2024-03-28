package com.mengxf.riskreport2.Utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Constants
 *
 * @author Meng xf
 * @since 2024/3/20 13:44
 */
public class Const {
    // 用户身份
    public static final int BORROWER = 1;
    public static final int ADMIN = 0;

    // 报告缺失必要数据
    public static final int USER_LOSS = 10;
    public static final int BORROWER_LOSS = 11;
    public static final int LOAN_LOSS = 12;
    public static final int FININFO_LOSS = 13;

    //常见返回值
    public static final int NO_EXIST = -99;
    public static final int SUCCESS = -100;
    public static final int NOT_NECESSARY = -101;
    public static final int EMPTY = -102;

    // 报告
    public static final String OUTPUT_PATH = "/Users/mxfmeng/Desktop/1AAA毕业设计-金融用户画像风险分析/输出报告/";
    public static final String TEMPLATE_PATH = "/Users/mxfmeng/Desktop/FinRisk-Prediction/creditRisk_server/src/main/resources/template/";
    public static final List<String> FEATURES_LIST = Arrays.asList("loan_amnt",  "term",  "int_rate",  "installment",  "grade",  "emp_length",  "home_ownership",  "annual_inc",  "verification_status",  "purpose",  "addr_state",  "dti",  "delinq_2yrs",  "open_acc",  "pub_rec",  "revol_bal",  "revol_util",  "total_acc",  "total_pymnt",  "total_rec_late_fee",  "last_pymnt_amnt",  "application_type",  "acc_now_delinq",  "tot_coll_amt",  "tot_cur_bal");
    public static final List<String> RISK_LIST = Arrays.asList("无风险 Zero Risk", "低风险 Low Risk", "中风险 Normal Risk", "高风险 High Risk");
    public static final String FLASK_SERVER = "http://127.0.0.1:5000";

}

