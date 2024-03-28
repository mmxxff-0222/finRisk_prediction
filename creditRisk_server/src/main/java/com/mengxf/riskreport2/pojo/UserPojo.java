package com.mengxf.riskreport2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserPojo
 *
 * @author Meng xf
 * @since 2024/3/20 10:50
 */
@Data
@AllArgsConstructor
@TableName(value = "user")
public class UserPojo {
    @TableId(value = "userID", type = IdType.AUTO)
    private int userID;
    @TableField(value = "userName")
    private String userName;
    @TableField(value = "password")
    private String passwprd;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "permission")
    private int permission; // 1:借款人 0:admin
}
