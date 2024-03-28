package com.mengxf.riskreport2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mengxf.riskreport2.dao.UserDao;
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
}
