package com.mengxf.riskreport2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxf.riskreport2.Utils.Const;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author Meng xf
 * @since 2024/3/20 10:57
 */
@Service
public class UserServiceImpl {
    @Autowired
    UserDao userDao;
    @Autowired
    LoanDao loanDao;
    @Autowired
    FinInfoDao finInfoDao;
    @Autowired
    BorrowerDao borrowerDao;

    /** 1
     *
     * @param name
     * @return 根据userName查询用户---模糊查询，只返回一名
     *
     * UserPojo userPojo = userService.findUserByName("Meng");
     * System.out.println(userPojo.getPhone());
     *
     */
    public UserPojo findUserByName(String name){
        UserPojo userPojo = userDao.selectOne(new QueryWrapper<UserPojo>().like("userName", name));
        if (userPojo != null){
            return userPojo;
        }
        return null;
    }
    // TODO 2、3、4将来改写成一个方法，Object
    /** 2
     * 同下
     * @param name
     * @param borrowerPojo
     * @return
     */
    public int findBorrowerByName(String name, BorrowerPojo borrowerPojo){
        UserPojo userPojo = findUserByName(name);
        if (userPojo != null){
            int userID = userPojo.getUserID();
            BorrowerPojo tmp = borrowerDao.selectById(userID);
            if (tmp != null){
                borrowerPojo.updateAttributes(tmp);
                return Const.SUCCESS; //查询到
            } else if (userPojo.getPermission() == Const.ADMIN) {
                return Const.NOT_NECESSARY; //管理人员无借款个人信息
            }
            return Const.EMPTY; //借款个人信息为空
        }
        return Const.NO_EXIST; //用户不存在
    }

    /** 3
     * 根据name查询finInfo财务状况信息
     * @param name
     * @param finInfo 接收健康状况信息
     * @return
     *
     * @eg:
     *         FinInfoPojo finInfo = new FinInfoPojo();
     *         int flag = userService.findFinHealthByName("Tom Chen",finInfo);
     *         if (flag == 1){
     *             System.out.println(finInfo);
     *         }
     *         System.out.println(Msg.findFinInfo(flag));
     *
     */
    public int findFinHealthByName(String name,FinInfoPojo finInfo){
        UserPojo userPojo = findUserByName(name);
        if (userPojo != null){
            FinInfoPojo tmp = finInfoDao.selectOne(new QueryWrapper<FinInfoPojo>().eq("userID",userPojo.getUserID()));

            if (tmp != null){
                finInfo.updateAttributes(tmp);
                return Const.SUCCESS; //查询到
            } else if (userPojo.getPermission() == Const.ADMIN) {
                return Const.NOT_NECESSARY; //管理人员无财务状况信息
            }
            return Const.EMPTY; //健康状况为空
        }
        return Const.NO_EXIST; //用户不存在
    }

    /** 4
     *
     * @param name
     * @param loans 如果数据库中查询到name的贷款记录，则将贷款信息存储到List范型loans中
     * @return -1表示用户不存在，-2表示查询的用户身份为管理员，其他表示根据name查询到的贷款记录数
     *
     * @eg:
     * List<LoanPojo> loans = new ArrayList<>();
     * int flag = userService.findLoanByName("Zosyqjs",loans);
     * if (flag>=0){
     *     System.out.println(loans);
     * }
     * System.out.println(Message.findLoan(flag));
     */
    public int findLoanByName(String name, List<LoanPojo> loans){
        UserPojo userPojo = findUserByName(name);
        if (userPojo != null){
            int userID = userPojo.getUserID();
            List<LoanPojo> res = loanDao.selectList(new QueryWrapper<LoanPojo>().eq("userID", userID));
            if (res.size() != 0){
                loans.addAll(res);
                return Const.SUCCESS; //查询到
            } else if (userPojo.getPermission() == Const.ADMIN) {
                return Const.NOT_NECESSARY; //管理员无贷款记录
            }
            return Const.EMPTY; //贷款信息为空
        }
        return Const.NO_EXIST; //查无此人
    }

    public int getAllUsers(List<UserPojo> users){
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("userID","userName","phone","permission");
        List<UserPojo> res = userDao.selectList(queryWrapper);
        if (res.size() != 0){
            res.stream().forEach(user ->user.setPasswprd("******"));//对密码加密显示
            users.addAll(res);
            return Const.SUCCESS;
        }
        return Const.EMPTY;
    }

    public int getUsers(List<UserPojo> users,int current, int size){
        Page<UserPojo> page = new Page<>(current,size);
        List<UserPojo> res = userDao.selectPage(page,null).getRecords();
        if (res.size() != 0){
            System.out.println(res.size());
            res.stream().forEach(user ->user.setPasswprd("******"));//对密码加密
            users.addAll(res);
            return Const.SUCCESS;
        }
        return Const.EMPTY;
    }

    public int getAllLoans(List<LoanPojo> loans){
        QueryWrapper<LoanPojo> queryWrapper = new QueryWrapper<>();
        List<LoanPojo> res = loanDao.selectList(queryWrapper);
        if (res.size() != 0){
            loans.addAll(res);
            return Const.SUCCESS;
        }
        return Const.EMPTY;
    }


    public int getAllBorrower(List<BorrowerPojo> persons) {
        QueryWrapper<BorrowerPojo> queryWrapper = new QueryWrapper<>();
        List<BorrowerPojo> res = borrowerDao.selectList(queryWrapper);
        if (res.size() != 0){
            persons.addAll(res);
            return Const.SUCCESS;
        }
        return Const.EMPTY;
    }

    public int getAllFinInfos(List<FinInfoPojo> finInfo) {
        QueryWrapper<FinInfoPojo> queryWrapper = new QueryWrapper<>();
        List<FinInfoPojo> res = finInfoDao.selectList(queryWrapper);
        if (res.size() != 0){
            finInfo.addAll(res);
            return Const.SUCCESS;
        }
        return Const.EMPTY;
    }

    public long getTableCounts(String tableName) {
        long len = 0;
        switch (tableName){
            case Const.BORROWER_TABLE:
                len = borrowerDao.selectCount(null);
                return len;
            case Const.USER_TABLE:
                len = userDao.selectCount(null);
                return len;
            case Const.FININFO_TABLE:
                len = finInfoDao.selectCount(null);
                return len;
            case Const.LOAN_TABLE:
                len = loanDao.selectCount(null);
                return len;
            default:
                return 0;
        }
    }
}
