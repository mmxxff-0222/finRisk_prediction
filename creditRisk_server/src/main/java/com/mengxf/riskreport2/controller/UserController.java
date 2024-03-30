package com.mengxf.riskreport2.controller;

import com.mengxf.riskreport2.Utils.ApiResponse;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.FinInfoPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import com.mengxf.riskreport2.pojo.UserPojo;
import com.mengxf.riskreport2.service.ReportServiceImpl;
import com.mengxf.riskreport2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;
import java.util.List;

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
    UserServiceImpl userService;

    @RequestMapping(value = "/getAllUsers",method = RequestMethod.GET)
    public String getAllUsers(){
        List<UserPojo> users = new ArrayList<>();
        int code = userService.getAllUsers(users);
        return ApiResponse.allUsersRespinse(code,users);
    }

    @RequestMapping(value = "/getLoansByName/{userName}",method = RequestMethod.GET)
    public String getLoansByName(@PathVariable("userName")String userName){
        List<LoanPojo> loans = new ArrayList<>();
        int code = userService.findLoanByName(userName,loans);
        return ApiResponse.getLoanResponse(code,loans);
    }

    @RequestMapping(value = "/getBorrInfoByName/{userName}",method = RequestMethod.GET)
    public String getBorrInfoByName(@PathVariable("userName")String userName){
        BorrowerPojo borrInfo = new BorrowerPojo();
        int code = userService.findBorrowerByName(userName,borrInfo);
        return ApiResponse.getBorrInfoResponse(code,borrInfo);
    }

    @RequestMapping(value = "/getFinInfoByName/{userName}",method = RequestMethod.GET)
    public String getFinHealthInfoByName(@PathVariable("userName")String userName){
        FinInfoPojo finInfo = new FinInfoPojo();
        int code = userService.findFinHealthByName(userName,finInfo);
        return ApiResponse.getFinInfoResponse(code,finInfo);
    }





}
