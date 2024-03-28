package com.mengxf.riskreport2.controller;

import com.mengxf.riskreport2.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author Meng xf
 * @since 2024/3/20 15:42
 *
 * HTTP请求类型及作用：
 * POST：增
 * DELETE：删
 * PUT： 改
 * GET：查
 */
@RestController
public class UserController {
    @Autowired
    ReportServiceImpl reportService;



}
