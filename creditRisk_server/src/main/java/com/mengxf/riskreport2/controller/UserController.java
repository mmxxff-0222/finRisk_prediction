package com.mengxf.riskreport2.controller;

import com.alibaba.fastjson2.JSONObject;
import com.mengxf.riskreport2.Utils.ApiResponse;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.FinInfoPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import com.mengxf.riskreport2.pojo.UserPojo;
import com.mengxf.riskreport2.service.BasicCURDserviceImpl;
import com.mengxf.riskreport2.service.ReportServiceImpl;
import com.mengxf.riskreport2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UserController
 *
 * @author Meng xf
 * @since 2024/3/20 15:42
 * <p>
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
    @Autowired
    BasicCURDserviceImpl basicService;

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public String getAllUsers() {
        List<UserPojo> users = new ArrayList<>();
        int code = userService.getAllUsers(users);
        return ApiResponse.allUsersRespinse(code, users);
    }
    @RequestMapping(value = "/getUsers/{current}-{size}", method = RequestMethod.GET)
    public String getAllUsersPages(@PathVariable("current") int current,@PathVariable("size") int size) {
        List<UserPojo> users = new ArrayList<>();
        int code = userService.getUsers(users,current,size);
        return ApiResponse.allUsersRespinse(code, users);
    }

    @RequestMapping(value = "/getLoansByName/{userName}", method = RequestMethod.GET)
    public String getLoansByName(@PathVariable("userName") String userName) {
        List<LoanPojo> loans = new ArrayList<>();
        int code = userService.findLoanByName(userName, loans);
        System.out.println(ApiResponse.getLoanResponse(code, loans));
        return ApiResponse.getLoanResponse(code, loans);
    }

    @RequestMapping(value = "/getBorrInfoByName/{userName}", method = RequestMethod.GET)
    public String getBorrInfoByName(@PathVariable("userName") String userName) {
        BorrowerPojo borrInfo = new BorrowerPojo();
        int code = userService.findBorrowerByName(userName, borrInfo);
        return ApiResponse.getBorrInfoResponse(code, borrInfo);
    }

    @RequestMapping(value = "/getFinInfoByName/{userName}", method = RequestMethod.GET)
    public String getFinHealthInfoByName(@PathVariable("userName") String userName) {
        FinInfoPojo finInfo = new FinInfoPojo();
        int code = userService.findFinHealthByName(userName, finInfo);
        return ApiResponse.getFinInfoResponse(code, finInfo);
    }

    @RequestMapping(value = "/updateUserById", method = RequestMethod.POST)
    public String updateUserById(@RequestBody String request) {
        // 解析请求
        // 解析请求
        JSONObject obj = JSONObject.parseObject(request);
        JSONObject formData = JSONObject.parseObject(JSONObject.toJSONString(obj.get("form")));
        basicService.updateUser(Integer.parseInt(formData.get("userID").toString()), formData.get("userName").toString(), formData.get("phone").toString(), Integer.parseInt(formData.get("permissions").toString()));
        return ApiResponse.okString("success");
    }

    @DeleteMapping(value = "/deleteUserByid/{userID}")
    public String deleteUserByID(@PathVariable int userID) {
        // 在这里编写删除资源的业务逻辑，例如调用Service层方法来删除资源
        // 这里只是简单的示例，实际情况根据业务需求编写具体的逻辑
        basicService.deleteUser(userID);
        return ApiResponse.okString("已删除");
    }


    @RequestMapping(value = "/getAllLoans", method = RequestMethod.GET)
    public String getAllLoans() {
        List<LoanPojo> loans = new ArrayList<>();
        int code = userService.getAllLoans(loans);
        return ApiResponse.allLoansRespinse(code, loans);
    }

    @DeleteMapping(value = "/deleteLoanByid/{userID}")
    public String deleteLoanByid(@PathVariable int userID) {
        basicService.deleteLoan(userID);
        return ApiResponse.okString("已删除");
    }

    @PutMapping(value = "/updateLoan")
    public String updateLoan(@RequestBody String request) {
        // 解析请求
        JSONObject obj = JSONObject.parseObject(request);
        JSONObject formData = JSONObject.parseObject(JSONObject.toJSONString(obj.get("form")));
        System.out.println(formData);
        basicService.updateLoan(
                Integer.parseInt(formData.get("loanID").toString()),
                Integer.parseInt(formData.get("userID").toString()),
                Float.parseFloat(formData.get("loan_amnt").toString()),
                Float.parseFloat(formData.get("int_rate").toString()),
                Float.parseFloat(formData.get("installment").toString()),
                formData.get("term").toString(),
                formData.get("grade").toString(),
                formData.get("application_type").toString(),
                formData.get("purpose").toString()
        );
        return ApiResponse.okString("success");
    }

    @RequestMapping(value = "/getAllPerson", method = RequestMethod.GET)
    public String getAllPerson() {
        List<BorrowerPojo> persons = new ArrayList<>();
        int code = userService.getAllBorrower(persons);
        return ApiResponse.allPersonRespinse(code, persons);
    }

    @DeleteMapping(value = "/deletePersonByid/{userID}")
    public String deletePersonByid(@PathVariable int userID) {
        basicService.deletePerson(userID);
        return ApiResponse.okString("已删除");
    }

    @PutMapping(value = "/updatePerson")
    public String updatePerson(@RequestBody String request) {
        // 解析请求
        JSONObject obj = JSONObject.parseObject(request);
        JSONObject formData = JSONObject.parseObject(JSONObject.toJSONString(obj.get("form")));
        System.out.println(formData);
        basicService.updatePerson(
                Integer.parseInt(formData.get("userID").toString()),
                Float.parseFloat(formData.get("annual_inc").toString()),
                formData.get("addr_state").toString(),
                formData.get("emp_length").toString(),
                formData.get("home_ownership").toString(),
                formData.get("verification_status").toString()
        );
        return ApiResponse.okString("success");
    }

    @RequestMapping(value = "/getAllFinInfo", method = RequestMethod.GET)
    public String getAllFinInfo() {
        List<FinInfoPojo> finInfo = new ArrayList<>();
        int code = userService.getAllFinInfos(finInfo);
        return ApiResponse.allFinInfoResponse(code, finInfo);
    }

    @DeleteMapping(value = "/deleteFinInfoByid/{userID}")
    public String deleteFinInfoByid(@PathVariable int userID) {
        basicService.deleteFinInfo(userID);
        return ApiResponse.okString("已删除");
    }

    @PutMapping(value = "/updateFininfo")
    public String updateFininfo(@RequestBody String request) {
        // 解析请求
        JSONObject obj = JSONObject.parseObject(request);
        JSONObject formData = JSONObject.parseObject(JSONObject.toJSONString(obj.get("form")));
        System.out.println(formData);
        basicService.updateFinInfo(
                Integer.parseInt(formData.get("userID").toString()),
                Float.parseFloat(formData.get("dti").toString()),
                Float.parseFloat(formData.get("delinq_2yrs").toString()),
                Float.parseFloat(formData.get("open_acc").toString()),
                Float.parseFloat(formData.get("pub_rec").toString()),
                Float.parseFloat(formData.get("acc_now_delinq").toString()),
                Float.parseFloat(formData.get("tot_coll_amt").toString()),
                Float.parseFloat(formData.get("tot_cur_bal").toString()),
                Float.parseFloat(formData.get("revol_bal").toString()),
                Float.parseFloat(formData.get("revol_util").toString()),
                Float.parseFloat(formData.get("total_acc").toString()),
                Float.parseFloat(formData.get("total_pymnt").toString()),
                Float.parseFloat(formData.get("total_rec_late_fee").toString()),
                Float.parseFloat(formData.get("last_pymnt_amnt").toString()));
        return ApiResponse.okString("success");
    }

    @RequestMapping(value = "/getCount/{table}", method = RequestMethod.GET)
    public String getCount(@PathVariable(value = "table") String tableName) {
        long nums = userService.getTableCounts(tableName);
        return ApiResponse.okString("查询数据条数成功！",nums);
    }



}
