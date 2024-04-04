package com.mengxf.riskreport2.controller;

import com.mengxf.riskreport2.Utils.ApiResponse;
import com.mengxf.riskreport2.service.ChartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ChartController
 *
 * @author Meng xf
 * @since 2024/4/4 12:33
 */
@RestController
public class ChartController {
    @Autowired
    ChartServiceImpl chartService;

    @RequestMapping(value = "/getChart1Data",method = RequestMethod.GET)
    public String getChart1Data(){
//        System.out.println("图表1——地区分布");
        List<Map<String, Object>> chart1 = chartService.getChart1();
        return ApiResponse.okString("图表1——信息查询成功",chart1);
    }

    @RequestMapping(value = "/getChart2Data",method = RequestMethod.GET)
    public String getChart2Data(){
//        System.out.println("图表2——贷款分布");
        List<Map<String, Object>> chart2 = chartService.getChart2();
        return ApiResponse.okString("图表2——信息查询成功",chart2);
    }

    @RequestMapping(value = "/getChart3Data",method = RequestMethod.GET)
    public String getChart3Data(){
//        System.out.println("图表3——贷款等级分布");
        List<Map<String, Object>> chart = chartService.getChart3();
        return ApiResponse.okString("图表3——信息查询成功",chart);
    }

    @RequestMapping(value = "/getChart4Data",method = RequestMethod.GET)
    public String getChart4Data(){
//        System.out.println("图表4——贷款目的分布");
        List<Map<String, Object>> chart = chartService.getChart4();
        return ApiResponse.okString("图表4——信息查询成功",chart);
    }
}
