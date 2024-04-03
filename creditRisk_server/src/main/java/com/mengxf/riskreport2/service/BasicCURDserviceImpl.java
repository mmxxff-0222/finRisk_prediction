package com.mengxf.riskreport2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengxf.riskreport2.dao.BorrowerDao;
import com.mengxf.riskreport2.dao.FinInfoDao;
import com.mengxf.riskreport2.dao.LoanDao;
import com.mengxf.riskreport2.dao.UserDao;
import com.mengxf.riskreport2.pojo.BorrowerPojo;
import com.mengxf.riskreport2.pojo.FinInfoPojo;
import com.mengxf.riskreport2.pojo.LoanPojo;
import com.mengxf.riskreport2.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CURDserviceImpl
 *
 * @author Meng xf
 * @since 2024/3/20 14:11
 */

@Service
public class BasicCURDserviceImpl {

    @Autowired
    UserDao userDao;
    @Autowired
    LoanDao loanDao;
    @Autowired
    BorrowerDao borrowerDao;
    @Autowired
    FinInfoDao finInfoDao;
    /** 1
     * 添加用户
     * @param userName
     * @param password
     * @param phone
     * @param permission
     *
     * @eg:
     * userService.addUser("Tom Chen","123456","13331121958",2);
     */
    public void addUser(String userName, String password, String phone, int permission){
        long length = userDao.selectCount(new QueryWrapper<UserPojo>().isNotNull("userID"));
        int userID = (int)(length & 0xFFFFFFFF) + 1;
        userDao.insert(new UserPojo(userID,userName,password,phone,permission));
    }

    public void updateUser(int userID, String userName,String phone, int permission){
        UserPojo oldUser = userDao.selectById(userID);
        userDao.updateById(new UserPojo(userID,userName,oldUser.getPasswprd(),phone,permission));
    }

    public void deleteUser(int userID){
        // 外键删除 -loan
        QueryWrapper<LoanPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",userID);
        loanDao.delete(queryWrapper);

        finInfoDao.deleteById(userID);
        borrowerDao.deleteById(userID);
        userDao.deleteById(userID);
    }

    public void deleteLoan(int userID){
        QueryWrapper<LoanPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",userID);
        loanDao.delete(queryWrapper);
    }

    public void updateLoan(int loanID, int userID, float loanAmnt, float intRate, float installment, String term, String grade, String applicationType, String purpose) {
        loanDao.updateById(new LoanPojo(
                loanID,userID,purpose,
                applicationType,loanAmnt,term,
                intRate,installment,grade));
    }

    public void deletePerson(int userID) {
        QueryWrapper<BorrowerPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",userID);
        borrowerDao.delete(queryWrapper);
    }

    public void updatePerson(int userID, float annualInc, String addrState, String empLength, String homeOwnership, String verificationStatus) {
        borrowerDao.updateById(new BorrowerPojo(
                userID,empLength,addrState,
                homeOwnership,annualInc,verificationStatus
        ));
    }

    public void deleteFinInfo(int userID) {
        QueryWrapper<FinInfoPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",userID);
        finInfoDao.delete(queryWrapper);
    }

    public void updateFinInfo(int userID, float dti, float delinq2yrs, float openAcc, float pubRec, float accNowDelinq, float totCollAmt, float totCurBal, float revolBal, float revolUtil, float totalAcc, float totalPymnt, float totalRecLateFee, float lastPymntAmnt) {
        finInfoDao.updateById(new FinInfoPojo(
                userID, dti, delinq2yrs,openAcc,pubRec,
                accNowDelinq,totCollAmt, totCurBal,revolBal,
                revolUtil,totalAcc,totalPymnt,totalRecLateFee,lastPymntAmnt
        ));
    }
}
