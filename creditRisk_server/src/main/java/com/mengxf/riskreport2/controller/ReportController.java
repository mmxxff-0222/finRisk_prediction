package com.mengxf.riskreport2.controller;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mengxf.riskreport2.Utils.ApiResponse;
import com.mengxf.riskreport2.Utils.Const;
import com.mengxf.riskreport2.service.ReportServiceImpl;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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

    String port;
//    @RequestMapping(value = "/doReport")
//    public String doReport(@RequestParam("userName")String userName){
//        String s = reportService.doReport(userName);
//        return ApiResponse.okGetString(s);
//    }

    @RequestMapping(value = "/doReport/{userName}",method = RequestMethod.GET)
    public String doReport(@PathVariable("userName")String userName){
        // 生成报告
        Map<String, Object> reportResult = reportService.doReport(userName);
        int code = Integer.parseInt(reportResult.get("reportCode").toString());
        String fileName = reportResult.get("data").toString();
        return ApiResponse.reportResponse(code, fileName);
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

    @GetMapping("/download/{userName}")
    public void download(@PathVariable String userName, HttpServletResponse response) throws IOException {
        System.out.println("download report");
        // 下载报告
        String fileName = userName + "_risk_report.docx";
        response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8")); // 预览
        String filePath = Const.OUTDOCS_PATH + File.separator + userName + File.separator + fileName;
        if (!FileUtil.exist(filePath)) {
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }
}
