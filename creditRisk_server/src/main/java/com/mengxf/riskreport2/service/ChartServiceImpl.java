package com.mengxf.riskreport2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengxf.riskreport2.dao.BorrowerDao;
import com.mengxf.riskreport2.dao.LoanDao;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChartServiceImpl
 *
 * @author Meng xf
 * @since 2024/4/4 12:33
 */
@Service
public class ChartServiceImpl {
    @Autowired
    BorrowerDao borrowerDao;
    @Autowired
    LoanDao loanDao;

    /***
     * 获取图一 各地区货款人数 数据
     * @return
     */
    public List<Map<String,Object>> getChart1(){
//        List<Map<String,Object>> result = new ArrayList<>();
        QueryWrapper<BorrowerPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("addr_state as name","count(*) as value");
        queryWrapper.groupBy("addr_state");
        List<Map<String,Object>> result = borrowerDao.selectMaps(queryWrapper);
        Map<String, String> stateMap = createStateMap();

        for (Map<String,Object> map : result){
            String shortName = map.get("name").toString();
            map.put("name",stateMap.get(shortName));
        }


        return result;
    }
    public static Map<String, String> createStateMap() {
        Map<String, String> stateMap = new HashMap<>();
        // 添加州名及其缩写信息到Map中
        stateMap.put("AL", "Alabama");
        stateMap.put("AK", "Alaska");
        stateMap.put("AZ", "Arizona");
        stateMap.put("AR", "Arkansas");
        stateMap.put("CA", "California");
        stateMap.put("CO", "Colorado");
        stateMap.put("CT", "Connecticut");
        stateMap.put("DE", "Delaware");
        stateMap.put("FL", "Florida");
        stateMap.put("GA", "Georgia");
        stateMap.put("HI", "Hawaii");
        stateMap.put("ID", "Idaho");
        stateMap.put("IL", "Illinois");
        stateMap.put("IN", "Indiana");
        stateMap.put("IA", "Iowa");
        stateMap.put("KS", "Kansas");
        stateMap.put("KY", "Kentucky");
        stateMap.put("LA", "Louisiana");
        stateMap.put("ME", "Maine");
        stateMap.put("MD", "Maryland");
        stateMap.put("MA", "Massachusetts");
        stateMap.put("MI", "Michigan");
        stateMap.put("MN", "Minnesota");
        stateMap.put("MS", "Mississippi");
        stateMap.put("MO", "Missouri");
        stateMap.put("MT", "Montana");
        stateMap.put("NE", "Nebraska");
        stateMap.put("NV", "Nevada");
        stateMap.put("NH", "New Hampshire");
        stateMap.put("NJ", "New Jersey");
        stateMap.put("NM", "New Mexico");
        stateMap.put("NY", "New York");
        stateMap.put("NC", "North Carolina");
        stateMap.put("ND", "North Dakota");
        stateMap.put("OH", "Ohio");
        stateMap.put("OK", "Oklahoma");
        stateMap.put("OR", "Oregon");
        stateMap.put("PA", "Pennsylvania");
        stateMap.put("RI", "Rhode Island");
        stateMap.put("SC", "South Carolina");
        stateMap.put("SD", "South Dakota");
        stateMap.put("TN", "Tennessee");
        stateMap.put("TX", "Texas");
        stateMap.put("UT", "Utah");
        stateMap.put("VT", "Vermont");
        stateMap.put("VA", "Virginia");
        stateMap.put("WA", "Washington");
        stateMap.put("WV", "West Virginia");
        stateMap.put("WI", "Wisconsin");
        stateMap.put("WY", "Wyoming");
        stateMap.put("DC", "District of Columbia");

        return stateMap;
    }

    public List<Map<String, Object>> getChart3() {
        QueryWrapper<LoanPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("grade as name","count(*) as value");
        queryWrapper.groupBy("grade");
        queryWrapper.orderByAsc("name");
        return loanDao.selectMaps(queryWrapper);
    }

    public List<Map<String, Object>> getChart4() {
        QueryWrapper<LoanPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("purpose as name","count(*) as value");
        queryWrapper.groupBy("purpose");
        queryWrapper.orderByDesc("value");
        return loanDao.selectMaps(queryWrapper);
    }

    public List<Map<String, Object>> getChart2() {
        QueryWrapper<LoanPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("FLOOR((loan_amnt - 1) / 2000) * 2000 + 1000 AS name","count(*) as value");
        queryWrapper.groupBy("name");
        queryWrapper.orderByAsc("name");
//        List<Map<String, Object>> result = loanDao.selectMaps(queryWrapper);
//        for (Map<String, Object> map: result){
//            int newValue = Integer.parseInt(map.get("value").toString());
//            map.put("value",newValue);
//        }
        return loanDao.selectMaps(queryWrapper);

    }
}
