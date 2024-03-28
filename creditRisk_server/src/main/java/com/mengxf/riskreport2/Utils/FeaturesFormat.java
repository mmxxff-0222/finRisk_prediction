package com.mengxf.riskreport2.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * FeaturesFormat
 *
 * @author Meng xf
 * @since 2024/3/25 12:34
 */
public class FeaturesFormat {
    // "verification_status", "purpose","term", "application_type"
    public static final Map<String,Map<String,Integer>> categoryCode = new HashMap<>();

    public Map<String,Object> getFeatures(Map<String,Object> originalMap){
        Map<String,Object> featuresMap = new LinkedHashMap<>();
        for (String key : Const.FEATURES_LIST){
            if (originalMap.containsKey(key)){
                featuresMap.put(key, originalMap.get(key));
            }
        }
        return featuresMap;
    }

    /***
     * "verification_status", "purpose", "term", "application_type"
     * 'grade', 'emp_length', 'home_ownership', 'addr_state'
     * @return 返回一个Map字典，可表示以上8个非数值型数据对应的数据，以便模型学习
     */
    public static Map<String,Map<String,Integer>> loadCodeDict(){
        // 创建一个Map来存储指定的数据
        Map<String, Map<String, Integer>> dataMap = new HashMap<>();

        // 添加 'verification_status' 数据
        Map<String, Integer> verificationStatusMap = new HashMap<>();
        verificationStatusMap.put("Not Verified", 0);
        verificationStatusMap.put("Source Verified", 1);
        verificationStatusMap.put("Verified", 2);
        dataMap.put("verification_status", verificationStatusMap);

        // 添加 'purpose' 数据
        Map<String, Integer> purposeMap = new HashMap<>();
        purposeMap.put("car", 0);
        purposeMap.put("credit_card", 1);
        purposeMap.put("educational", 3);
        purposeMap.put("debt_consolidation", 2);
        purposeMap.put("home_improvement", 4);
        purposeMap.put("house", 5);
        purposeMap.put("major_purchase", 6);
        purposeMap.put("medical", 7);
        purposeMap.put("moving", 8);
        purposeMap.put("other", 9);
        purposeMap.put("renewable_energy", 10);
        purposeMap.put("small_business", 11);
        purposeMap.put("vacation", 12);
        purposeMap.put("wedding", 13);
        dataMap.put("purpose", purposeMap);

        // 添加 'term' 数据
        Map<String, Integer> termMap = new HashMap<>();
        termMap.put(" 36 months", 0);
        termMap.put(" 60 months", 1);
        dataMap.put("term", termMap);

        // 添加 'application_type' 数据
        Map<String, Integer> applicationTypeMap = new HashMap<>();
        applicationTypeMap.put("INDIVIDUAL", 0);
        applicationTypeMap.put("JOINT", 1);
        dataMap.put("application_type", applicationTypeMap);

        // 编码并添加 'grade' 特征的编码器
        Map<String, Integer> gradeEncoderMap = new HashMap<>();
        gradeEncoderMap.put("E", 1);
        gradeEncoderMap.put("D", 2);
        gradeEncoderMap.put("C", 3);
        gradeEncoderMap.put("B", 4);
        gradeEncoderMap.put("A", 5);
        gradeEncoderMap.put("Other", 0);  // 如果输入不在预期的范围内，返回0
        dataMap.put("grade", gradeEncoderMap);

        // 编码并添加 'emp_length' 特征的编码器
        Map<String, Integer> empLengthEncoderMap = new HashMap<>();
        empLengthEncoderMap.put("< 1 year", 1);
        empLengthEncoderMap.put("1 year", 2);
        empLengthEncoderMap.put("2 years", 3);
        empLengthEncoderMap.put("3 years", 4);
        empLengthEncoderMap.put("4 years", 5);
        empLengthEncoderMap.put("5 years", 6);
        empLengthEncoderMap.put("6 years", 7);
        empLengthEncoderMap.put("7 years", 8);
        empLengthEncoderMap.put("8 years", 9);
        empLengthEncoderMap.put("9 years", 10);
        empLengthEncoderMap.put("10 years", 11);
        empLengthEncoderMap.put("10+ years", 12);
        empLengthEncoderMap.put("Other", 0);  // 如果输入不在预期的范围内，返回0
        dataMap.put("emp_length", empLengthEncoderMap);

        // 编码并添加 'home_ownership' 特征的编码器
        Map<String, Integer> homeOwnershipEncoderMap = new HashMap<>();
        homeOwnershipEncoderMap.put("RENT", 1);
        homeOwnershipEncoderMap.put("MORTGAGE", 2);
        homeOwnershipEncoderMap.put("OWN", 3);
        homeOwnershipEncoderMap.put("Other", 0);  // 如果输入不在预期的范围内，返回0
        dataMap.put("home_ownership", homeOwnershipEncoderMap);

        // 编码并添加 'addr_state' 特征的编码器
        Map<String, Integer> stateEncoderMap = new HashMap<>();
        stateEncoderMap.put("DC", 1);
        stateEncoderMap.put("NY", 2);
        stateEncoderMap.put("MA", 3);
        stateEncoderMap.put("WA", 4);
        stateEncoderMap.put("CA", 5);
        stateEncoderMap.put("ND", 6);
        stateEncoderMap.put("AK", 7);
        stateEncoderMap.put("DE", 8);
        stateEncoderMap.put("CT", 9);
        stateEncoderMap.put("WY", 10);
        stateEncoderMap.put("CO", 11);
        stateEncoderMap.put("NE", 12);
        stateEncoderMap.put("IL", 13);
        stateEncoderMap.put("NJ", 14);
        stateEncoderMap.put("TX", 15);
        stateEncoderMap.put("MN", 16);
        stateEncoderMap.put("MD", 17);
        stateEncoderMap.put("VA", 18);
        stateEncoderMap.put("UT", 19);
        stateEncoderMap.put("SD", 20);
        stateEncoderMap.put("NH", 21);
        stateEncoderMap.put("IA", 22);
        stateEncoderMap.put("KS", 23);
        stateEncoderMap.put("GA", 24);
        stateEncoderMap.put("PA", 25);
        stateEncoderMap.put("OH", 26);
        stateEncoderMap.put("HI", 27);
        stateEncoderMap.put("OR", 28);
        stateEncoderMap.put("NV", 29);
        stateEncoderMap.put("TN", 30);
        stateEncoderMap.put("IN", 31);
        stateEncoderMap.put("WI", 32);
        stateEncoderMap.put("NC", 33);
        stateEncoderMap.put("RI", 34);
        stateEncoderMap.put("FL", 35);
        stateEncoderMap.put("AZ", 36);
        stateEncoderMap.put("MO", 37);
        stateEncoderMap.put("LA", 38);
        stateEncoderMap.put("VT", 39);
        stateEncoderMap.put("MI", 40);
        stateEncoderMap.put("ME", 41);
        stateEncoderMap.put("OK", 42);
        stateEncoderMap.put("MT", 43);
        stateEncoderMap.put("NM", 44);
        stateEncoderMap.put("KY", 45);
        stateEncoderMap.put("ID", 46);
        stateEncoderMap.put("SC", 47);
        stateEncoderMap.put("AL", 48);
        stateEncoderMap.put("WV", 49);
        stateEncoderMap.put("AR", 50);
        stateEncoderMap.put("MS", 51);
        stateEncoderMap.put("Other", 0);  // 如果输入不在预期的范围内，返回0
        dataMap.put("addr_state", stateEncoderMap);

        return dataMap;
    }


    /**
     * 对从数据库中加载的数据模型进行数据标准化，以适配机器学习分类器的要求
     * @param originalMap
     * @return
     */
    public static Map<String, Object> featuresNormalization(Map<String,Object> originalMap){
        Map<String,Map<String,Integer>> codeDict = loadCodeDict();
        Set<String> strings = codeDict.keySet();
        for (String featureName : strings){
            String textLabel = originalMap.get(featureName).toString();
            originalMap.put(featureName, codeDict.get(featureName).get(textLabel));
        }
        return originalMap;
    }
}
