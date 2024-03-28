package com.mengxf.riskreport2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BorrowerPojo
 *
 * @author Meng xf
 * @since 2024/3/20 14:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "borrower")
public class BorrowerPojo {
    @TableId(value = "userID",type = IdType.NONE)
    private int userID;
    @TableField(value = "emp_length")
    private String emp_length;
    @TableField(value = "addr_state")
    private String addr_state;
    @TableField(value = "home_ownership")
    private String home_ownership;
    @TableField(value = "annual_inc")
    private float annual_inc;
    @TableField(value = "verification_status")
    private String verification_status;

    public void updateAttributes(BorrowerPojo newBo){
        this.setUserID(newBo.getUserID());
        this.setEmp_length(newBo.getEmp_length());
        this.setAddr_state(newBo.getAddr_state());
        this.setHome_ownership(newBo.getHome_ownership());
        this.setAnnual_inc(newBo.getAnnual_inc());
        this.setVerification_status(newBo.getVerification_status());
    }

}
