package com.mengxf.riskreport2.controller;

import com.alibaba.fastjson2.JSONObject;
import com.mengxf.riskreport2.Utils.ApiResponse;
import com.mengxf.riskreport2.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * ReportController
 *
 * @author Meng xf
 * @since 2024/3/22 13:59
 */
@RestController
public class ReportController {
    @Autowired
    ReportServiceImpl reportService;
//    @RequestMapping(value = "/doReport")
//    public String doReport(@RequestParam("userName")String userName){
//        String s = reportService.doReport(userName);
//        return ApiResponse.okGetString(s);
//    }

    @RequestMapping(value = "/doReport/{userName}",method = RequestMethod.GET)
    public String doReport(@PathVariable("userName")String userName){
        System.out.println("doReport");
        Map<String, Object> reportResult = reportService.doReport(userName);
        int code = Integer.parseInt(reportResult.get("reportCode").toString());
        String url = reportResult.get("data").toString();
        return ApiResponse.reportResponse(code, url);
    }

    @RequestMapping(value = "/getRisk",method = RequestMethod.POST)
    public String getRisk(@RequestBody String request){
//        System.out.println(request);
//        Map<String, Object > map = new HashMap<>();
        // 解析请求
        JSONObject obj = JSONObject.parseObject(request);
        Map<String,Object> map = JSONObject.parseObject(JSONObject.toJSONString(obj.get("features")),Map.class);
        System.out.println(map);
        // 解析后的数据调用查询方法
        String riskGrade = reportService.getRiskGrade(map);
        return ApiResponse.getRiskResponse(riskGrade);
    }
}
